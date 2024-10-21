package devcamp.realestateexchange.entity.user;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import devcamp.realestateexchange.entity.media.Photo;
import devcamp.realestateexchange.entity.social.Article;
import devcamp.realestateexchange.entity.social.ArticleLike;
import devcamp.realestateexchange.entity.social.Reply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer extends UserReferenceEntity {
    // Tên khách hàng
    @Column(name = "contact_name")
    private String fullName;

    // Chức vụ
    @Column(name = "contact_title")
    private String contactTitle;

    // Địa chỉ
    @Column(name = "address")
    private String address;

    // Số điện thoại
    @Column(name = "mobile")
    private String phone;

    // Email
    @Column(name = "email")
    private String email;

    // Ghi chú
    @Column(name = "note")
    private String note;
    
    // Quan hệ 1-1 với hình ảnh
    @OneToOne(cascade =  CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id")
    private Photo photo;

    // Quan hệ 1-n với bảng article_like
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Set<ArticleLike> articleLikes;

    // Quan hệ 1-n với bảng article
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Set<Article> articles;

    // Quan hệ 1-n với bảng reply
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Set<Reply> replies;
}
