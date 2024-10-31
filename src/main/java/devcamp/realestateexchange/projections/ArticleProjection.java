package devcamp.realestateexchange.projections;
import java.util.Set;
import java.util.List;
// Projection for Article. This projection is used to get the article with its replies from the database.
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
