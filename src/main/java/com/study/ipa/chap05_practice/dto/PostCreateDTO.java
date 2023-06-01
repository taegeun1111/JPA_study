package com.study.ipa.chap05_practice.dto;

import com.study.ipa.chap05_practice.entity.Post;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreateDTO {
    @NotBlank
    @Size(min = 2, max = 5)
    private String writer;

    @NotBlank
    @Size(min = 1, max = 20)
    private String title;


    private String content;


    private List<String> hashTags;


    //dto를 엔터티로 변환하는 메서드
    //HashTag는 읽기전용이기 때문에 넣지 않는다.
    public Post toEntity(){
        return Post.builder()
                .writer(this.writer)
                .content(this.content)
                .title(this.title)
                .build();
    }

}
