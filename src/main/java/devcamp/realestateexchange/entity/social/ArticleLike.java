package devcamp.realestateexchange.entity.social;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import devcamp.realestateexchange.entity.BaseEntity;
import devcamp.realestateexchange.entity.user.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "article_likes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleLike extends BaseEntity {
    // Quan hệ n-1 với Customer
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    // Quan hệ n-1 với Article
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;
}
