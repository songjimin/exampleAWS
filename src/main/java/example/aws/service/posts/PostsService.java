package example.aws.service.posts;

import example.aws.domain.posts.Posts;
import example.aws.domain.posts.PostsRepository;
import example.aws.web.dto.PostsSaveRequestDto;
import example.aws.web.dto.PutUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    public Posts getPostsList(Long id) {
        return postsRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("No DATA id=" + id));
    }

    @Transactional
    public Posts updatePosts(Long id, PostsSaveRequestDto putUpdateRequestDto) {

        Posts existedPosts = postsRepository.findById(id)
                                            .orElseThrow(() -> new IllegalArgumentException("No DATA id="+ id));

        existedPosts.setTitle(putUpdateRequestDto.getTitle());
        existedPosts.setContent(putUpdateRequestDto.getContent());
        existedPosts.setAuthor(putUpdateRequestDto.getAuthor());

        return existedPosts;

    }
}
