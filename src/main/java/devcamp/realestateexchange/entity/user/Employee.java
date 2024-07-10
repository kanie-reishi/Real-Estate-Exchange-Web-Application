package devcamp.realestateexchange.entity.user;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import devcamp.realestateexchange.entity.media.Photo;
import devcamp.realestateexchange.entity.social.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends UserReferenceEntity {
    // Họ
    @Column(name = "last_name")
    private String lastName;

    // Tên
    @Column(name = "first_name")
    private String firstName;

    // Chức vụ
    @Column(name = "title")
    private String title;

    // Chức vụ lịch sự
    @Column(name = "title_of_courtesy")
    private String titleOfCourtesy;

    // Ngày sinh
    @Column(name = "birth_date")
    private String BirthDate;

    // Ngày thuê
    @Column(name = "hire_date")
    private String hireDate;

    // Địa chỉ
    @Column(name = "address")
    private String address;

    // Thành phố
    @Column(name = "city")
    private String city;

    // Khu vực
    @Column(name = "region")
    private String region;

    // Mã bưu chính
    @Column(name = "postal_code")
    private String postalCode;

    // Quốc gia
    @Column(name = "country")
    private String country;

    // Số điện thoại nhà
    @Column(name = "home_phone")
    private String homePhone;

    // Quan hệ 1-1 với hình ảnh
    @OneToOne(cascade =  CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id")
    private Photo photo;

    // Ghi chú
    @Column(name = "notes")
    private String notes;

    // Quan hệ n-1 với Employee(Quản lý của nhân viên)
    @ManyToOne
    @JoinColumn(name = "reports_to")
    private Employee reportsTo;

    // Profile
    @Column(name = "profile")
    private String profile;

    // Mức độ quản lý
    @Column(name = "user_level")
    private int userLevel;
    
    // Quan hệ 1-n với bảng article
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<Article> createdArticles;

    // Quan hệ 1-n với bảng article
    @OneToMany(mappedBy = "updatedBy", fetch = FetchType.LAZY)
    private List<Article> updatedArticles;
}
