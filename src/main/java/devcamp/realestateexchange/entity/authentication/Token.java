package devcamp.realestateexchange.entity.authentication;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import devcamp.realestateexchange.entity.BaseEntity;

import java.util.Date;

@Entity
@Table(name = "token")
@Getter
@Setter
public class Token extends BaseEntity {

    // Token
    @Column(length = 1000)
    private String token;

    // Ngày hết hạn
    private Date tokenExpDate;
}