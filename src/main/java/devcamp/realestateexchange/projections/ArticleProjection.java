package devcamp.realestateexchange.projections;
import java.util.Set;
import java.util.List;
public interface ArticleProjection {
    Integer getId();
    String getTitle();
    Long getViewNum();
    Long getLikeNum();
    Set<ReplyProjection> getReplies();

    interface ReplyProjection {
        Integer getId();
        String getContent();
        Long getLikeNum();
        ReplyProjection getParentReply();
        List<ReplyProjection> getChildrenReplies();
    }
}
