package devcamp.realestateexchange.dto;

import java.math.BigDecimal;
import java.util.Date;
public class RealEstateDto {
    private int id;
    private String title;
    private int provinceId;
    private int districtId;
    private BigDecimal price;
    private double acreage;
    private Date dateCreate;
    private String photo;
    private String description;
    private String address;
    public RealEstateDto(int id, String title, int provinceId, int districtId, BigDecimal price, double acreage,
            Date dateCreate, String photo) {
        this.id = id;
        this.title = title;
        this.provinceId = provinceId;
        this.districtId = districtId;
        this.price = price;
        this.acreage = acreage;
        this.dateCreate = dateCreate;
        this.photo = photo;
    }
    public RealEstateDto() {
    }
    public RealEstateDto(int id, String title, int provinceId, int districtId, BigDecimal price, double acreage,
            Date dateCreate) {
        this.id = id;
        this.title = title;
        this.provinceId = provinceId;
        this.districtId = districtId;
        this.price = price;
        this.acreage = acreage;
        this.dateCreate = dateCreate;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getProvinceId() {
        return provinceId;
    }
    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
    public int getDistrictId() {
        return districtId;
    }
    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public double getAcreage() {
        return acreage;
    }
    public void setAcreage(double acreage) {
        this.acreage = acreage;
    }
    public Date getDateCreate() {
        return dateCreate;
    }
    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public RealEstateDto(int id, String title, int provinceId, int districtId, BigDecimal price, double acreage,
            Date dateCreate, String photo, String description, String address) {
        this.id = id;
        this.title = title;
        this.provinceId = provinceId;
        this.districtId = districtId;
        this.price = price;
        this.acreage = acreage;
        this.dateCreate = dateCreate;
        this.photo = photo;
        this.description = description;
        this.address = address;
    }
    
}
