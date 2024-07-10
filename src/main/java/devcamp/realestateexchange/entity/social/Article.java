package devcamp.realestateexchange.entity.social;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import devcamp.realestateexchange.entity.user.UserReferenceEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "article")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Article extends UserReferenceEntity{
    // Số lượt xem
    @Column(name = "view_num")
    private Long viewNum;

    // Tiêu đề
    @Column(name = "title")
    private String title;

    // Số lượt thích
    @Column(name = "like_num")
    private Long likeNum;

    // Quan hệ 1-n với bảng article_like, quan hệ n-n với bảng customer
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ArticleLike> articleLikes;
    
    // Quan hệ 1-n với bảng Reply
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Reply> replies;
}
