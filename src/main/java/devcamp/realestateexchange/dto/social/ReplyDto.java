package devcamp.realestateexchange.dto.social;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDto {
    private Integer id;
    private String content;
    private List<ReplyDto> childrenReplies;
    private Long likeNum;
    private Integer parentReply;
    private Integer articleId;
    private Integer createdBy;
    private Integer updatedBy;
    private Date createdAt;
    private Date updatedAt;
}
