package example.aws.web.dto;

import example.aws.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class PutUpdateRequestDto {

    private Long id;
    private String title;
    private String content;
    private String author;

    @Builder
    public PutUpdateRequestDto(Long id, String title, String content, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
