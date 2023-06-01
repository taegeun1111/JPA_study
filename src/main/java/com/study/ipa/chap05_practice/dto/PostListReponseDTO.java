package com.study.ipa.chap05_practice.dto;

import lombok.*;

import java.util.List;

@Setter @Getter
@ToString @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostListReponseDTO {

    private int count; //총 게시물 수
    private PageResponseDTO pageInfo; //페이지 렌더링 정보

    //entity는 dto에서 사용하지 않는다.
    //entity의 스펙과 동일해도 새로운 dto를 설계해서 보내줘야 한다.
    private List<PostDetailResponseDTO> posts; //게시물 렌더링 정보
}









