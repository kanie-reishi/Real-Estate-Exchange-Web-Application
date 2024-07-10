package devcamp.realestateexchange.entity.social;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import devcamp.realestateexchange.entity.user.UserReferenceEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "replies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reply extends UserReferenceEntity {
    // Nội dung
    @Column(name = "content")
    private String content;

    // Quan hệ n-1 với Article (Bài viết)
    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;

    // Lượt thích
    @Column(name = "like_num")
    private Long likeNum;

    // Reply cha
    @ManyToOne(fetch = FetchType.LAZY)
    private Reply parentReply;
    
    // Reply con
    @OneToMany(mappedBy = "parentReply", fetch = FetchType.LAZY)
    private List<Reply> childrenReplies;

    // Quan hệ 1-n với bảng reply_like, quan hệ n-n với bảng customer
    @OneToMany(mappedBy = "reply", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReplyLike> replyLikes;
    
}
