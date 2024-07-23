package devcamp.realestateexchange.dto.social;
import java.util.Set;
import java.util.stream.Collectors;

import devcamp.realestateexchange.entity.social.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private Long viewNum;
    private Long likeNum;
    private Set<ReplyDto> replies;

    public ArticleDto(Article article){
        this.viewNum = article.getViewNum();
        this.likeNum = article.getLikeNum();
        this.replies = article.getReplies().stream().map(
            reply -> reply.toDto()
        ).collect(Collectors.toSet());
    }
}
