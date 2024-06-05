package devcamp.realestateexchange.models;
public class RealEstateSearchParameters {
    private Integer provinceId;
    private Integer districtId;
    private Double minPrice;
    private Double maxPrice;
    private Double minAcreage;
    private Double maxAcreage;
    private Integer bedroom;
    private String address;
    public Integer getProvinceId() {
        return provinceId;
    }
    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }
    public Integer getDistrictId() {
        return districtId;
    }
    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }
    public Double getMinPrice() {
        return minPrice;
    }
    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }
    public Double getMaxPrice() {
        return maxPrice;
    }
    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }
    public Double getMinAcreage() {
        return minAcreage;
    }
    public void setMinAcreage(Double minAcreage) {
        this.minAcreage = minAcreage;
    }
    public Double getMaxAcreage() {
        return maxAcreage;
    }
    public void setMaxAcreage(Double maxAcreage) {
        this.maxAcreage = maxAcreage;
    }
    public Integer getBedroom() {
        return bedroom;
    }
    public void setBedroom(Integer bedroom) {
        this.bedroom = bedroom;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    
}
