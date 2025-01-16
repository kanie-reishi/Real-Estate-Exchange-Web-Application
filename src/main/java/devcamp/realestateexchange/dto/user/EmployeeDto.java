package devcamp.realestateexchange.dto.user;

import java.util.List;

import devcamp.realestateexchange.dto.social.ArticleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private Integer id;
    private String lastName;
    private String firstName;
    private String title;
    private String titleOfCourtesy;
    private String birthDate;
    private String hireDate;
    private String address;
    private String city;
    private String region;
    private String postalCode;
    private String country;
    private String homePhone;
    private String photoUrl;
    private String notes;
    private Integer reportsTo;
    private Integer userLevel;
    private List<ArticleDto> createArticles;
    private List<ArticleDto> updateArticles;
}
