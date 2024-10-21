package devcamp.realestateexchange.services.media;

import org.springframework.stereotype.Service;

import devcamp.realestateexchange.dto.social.ReplyDto;
import devcamp.realestateexchange.projections.ArticleProjection.ReplyProjection;

@Service
public class ArticleService {
    public ReplyDto convertReplyProjectionToDto(ReplyProjection replyProjection) {
        ReplyDto replyDto = new ReplyDto();
        replyDto.setId(replyProjection.getId());
        replyDto.setContent(replyProjection.getContent());
        return replyDto;
    }
}
