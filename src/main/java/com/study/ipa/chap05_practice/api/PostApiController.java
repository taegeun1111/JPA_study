package com.study.ipa.chap05_practice.api;

import com.study.ipa.chap05_practice.PostService;
import com.study.ipa.chap05_practice.dto.PageDTO;
import com.study.ipa.chap05_practice.dto.PostCreateDTO;
import com.study.ipa.chap05_practice.dto.PostDetailResponseDTO;
import com.study.ipa.chap05_practice.dto.PostListReponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostApiController {

    private final PostService postService;

    // 리소스 : 게시물 (Post)
/*
     게시물 목록 조회:  /posts       - GET
     게시물 개별 조회:  /posts/{id}  - GET
     게시물 등록:      /posts       - POST
     게시물 수정:      /posts/{id}  - PATCH
     게시물 삭제:      /posts/{id}  - DELETE
 */

    @GetMapping
    public ResponseEntity<?> list(PageDTO pageDTO) {
        log.info("/api/v1/posts?page={}&size={}", pageDTO.getPage(), pageDTO.getSize());

        PostListReponseDTO dto = postService.getPosts(pageDTO);

        return ResponseEntity
                .ok()
                .body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        log.info(".api/vi1/posts{} GET");

        try {
            PostDetailResponseDTO dto = postService.getDetail(id);
            return ResponseEntity.ok().body(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> create(
            //dto값 검증
            @Validated @RequestBody PostCreateDTO dto
            , BindingResult result //검증 에러 정보를 가진 객체
    ) {
        log.info("/api/vi/post POST! - payload : {}");

        if (dto == null) {
            return ResponseEntity
                    .badRequest()
                    .body("게시물 정보를 전달해주세요");
        }
        if (result.hasErrors()) { //입력값 검증에 걸림
            //모든 에러 정보
            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach(err -> {
                log.warn("invalid client data - {}", err.toString());
            });
            return ResponseEntity
                    .badRequest()
                    .body(fieldErrors);
        }

        //save도중 오류가 발생할 수 있기 때문에 service에서 RuntimeException을 던지고
        //controller에서 오류를 받아서 500번대 에러를 호출해준다.
        try {
            PostDetailResponseDTO responseDTO = postService.insert(dto);
            return ResponseEntity
                    .ok()
                    .body(responseDTO);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity
                    .internalServerError()
                    .body("서버 터짐 원인" +e.getMessage());
        }
    }

}
