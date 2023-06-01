package com.study.ipa.chap05_practice;

import com.study.ipa.chap05_practice.dto.*;
import com.study.ipa.chap05_practice.entity.HashTag;
import com.study.ipa.chap05_practice.entity.Post;
import com.study.ipa.chap05_practice.repository.HashTagRepository;
import com.study.ipa.chap05_practice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional //JPA 레파지토리는 반드시 트랜잭션 어노테이션 필수
public class PostService {

    private final PostRepository postRepository;
    private final HashTagRepository hashTagRepository;

    public PostListReponseDTO getPosts(PageDTO dto) {

        //Pageable객체 생성
        PageRequest pageable = PageRequest.of(
                dto.getPage() - 1,
                dto.getSize(),
                Sort.by("createDate").descending()
        );

        //데이터베이스에서 게시물 목록 조회
        Page<Post> posts = postRepository.findAll(pageable);

        //게시물 정보만 꺼내기
        List<Post> postList = posts.getContent();

        List<PostDetailResponseDTO> detailList
                = postList.stream()
                .map(post ->
                        new PostDetailResponseDTO(post)
                )
                .collect(Collectors.toList());

        //DB에서 조회한 정보를 JSON형태에 맞는 DTO로 변환
        PostListReponseDTO responseDTO = PostListReponseDTO.builder()
                .count(detailList.size()) //총 게시물 수가 아니라 조회된 게시물 수
                .pageInfo(new PageResponseDTO<Post>(posts))
                .posts(detailList)
                .build();
        return responseDTO;
    }

    public PostDetailResponseDTO getDetail(Long id) {
        Post postEntity = postRepository.findById(id).orElseThrow(
                () -> new RuntimeException(
                        id + "번 게시물이 존재하지 않습니다.")
        );
        return new PostDetailResponseDTO(postEntity);
    }

    public PostDetailResponseDTO insert(final PostCreateDTO dto)
            throws RuntimeException{


        //게시물 저장
        Post saved = postRepository.save(dto.toEntity());

        //해시태그 저장
        List<String> hashTags = dto.getHashTags();
        if (hashTags != null && hashTags.size() > 0) {
            hashTags.forEach(ht -> {
                HashTag savedTag = hashTagRepository.save(
                        HashTag.builder()
                        .tagName(ht)
                        .post(saved)
                        .build()
                );
                saved.addHashTag(savedTag);
            });
        }

        return new PostDetailResponseDTO(saved);
    }
}
