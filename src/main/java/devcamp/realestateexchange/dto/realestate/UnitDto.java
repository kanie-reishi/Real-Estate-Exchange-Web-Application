package devcamp.realestateexchange.dto.realestate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// Class cha của các đối tượng liên quan đến đơn vị (Investor, Contractor, DesignUnit)
public class UnitDto {
    // id đơn vị
    private Integer id;
    // Tên đơn vị
    private String name;
    // Mô tả đơn vị
    private String description;
    // Địa chỉ đơn vị
    private String address;
    // Số điện thoại
    private String phone;
    // Số điện thoại 2
    private String phone2;
    // Fax
    private String fax;
    // Email
    private String email;
    // Website
    private String website;
    // Ghi chú
    private String note;
}
