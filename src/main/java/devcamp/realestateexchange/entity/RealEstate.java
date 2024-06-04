package devcamp.realestateexchange.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.JoinColumn;
import javax.persistence.GenerationType;
import javax.persistence.FetchType;
@Entity
@Table(name = "real_estate")
public class RealEstate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "type")
    private Integer type;
    @Column(name = "request")
    private Integer request;
    @ManyToOne
    @JoinColumn(name = "province_id")
    @JsonBackReference
    private Province province;
    @ManyToOne
    @JoinColumn(name = "district_id")
    @JsonBackReference
    private District district;
    @ManyToOne
    @JoinColumn(name = "ward_id")
    @JsonBackReference
    private Ward ward;
    @ManyToOne
    @JoinColumn(name = "street_id")
    @JsonBackReference
    private Street street;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @JsonBackReference
    private Project project;
    @Column(name = "address")
    private String address;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "price_min")
    private BigDecimal priceMin;
    @Column(name = "price_time")
    private Integer priceTime;
    @Column(name = "date_create")
    private Date dateCreate;
    @Column(name = "acreage")
    private Double acreage;
    @Column(name = "direction")
    private Integer direction;
    @Column(name = "total_floors")
    private Integer totalFloors;
    @Column(name = "number_floors")
    private Integer numberFloors;
    @Column(name = "bath")
    private Integer bath;
    @Column(name = "apart_code")
    private String apartCode;
    @Column(name = "wall_area")
    private Double wallArea;
    @Column(name = "bedroom")
    private Integer bedroom;
    @Column(name = "balcony")
    private Integer balcony;
    @Column(name = "landscape_view")    
    private String landscapeView;
    @Column(name = "apart_loca")
    private Integer apartLoca;
    @Column(name = "apart_type")
    private Integer apartType;
    @Column(name = "furniture_type")
    private Integer furnitureType;
    @Column(name = "price_rent")
    private Integer priceRent;
    @Column(name = "return_rate")
    private Double returnRate;
    @Column(name = "legal_doc")
    private Integer legalDoc;
    @Column(name = "description")
    private String description;
    @Column(name = "width_y")
    private Integer widthY;
    @Column(name = "long_x")
    private Integer longX;
    @Column(name = "street_house")
    private Integer streetHouse;
    @Column(name = "FSBO")
    private Integer FSBO;
    @Column(name = "view_num")
    private Integer viewNum;
    @Column(name = "created_by")
    private Integer created_by;
    @Column(name = "updated_by")
    private Integer updated_by;
    @Column(name = "shape")
    private String shape;
    @Column(name = "distance2facade")
    private Integer distance2Facade;
    @Column(name = "adjacent_facade_num")
    private Integer adjacentFacadeNum;
    @Column(name = "adjacent_road")
    private String adjacentRoad;
    @Column(name = "alley_min_width")
    private Integer alleyMinWidth;
    @Column(name = "adjacent_alley_min_width")
    private Integer adjacentAlleyMinWidth;
    @Column(name = "factor")
    private Integer factor;
    @Column(name = "structure")
    private String structure;
    @Column(name = "DTSXD")
    private Integer DTSXD;
    @Column(name = "CLCL")
    private Integer CLCL;
    @Column(name = "CTXD_price")
    private Integer ctxdPrice;
    @Column(name = "CTXD_value")
    private Integer ctxdValue;
    @Column(name = "photo")
    private String photo;
    @Column(name = "lat")
    private Double lat;
    @Column(name = "lng")
    private Double lng;
    public RealEstate() {
    }
    public RealEstate(int id, String title, Integer type, Integer request, Province province, District district, Ward ward,
            Street street, Project project, String address, Customer customer, BigDecimal price, BigDecimal priceMin, Integer priceTime,
            Date dateCreate, Double acreage, Integer direction, Integer totalFloors, Integer numberFloors, Integer bath,
            String apartCode, Double wallArea, Integer bedroom, Integer balcony, String landscapeView, Integer apartLoca,
            Integer apartType, Integer furnitureType, Integer priceRent, Double returnRate, Integer legalDoc, String description,
            Integer widthY, Integer longX, Integer streetHouse, Integer fSBO, Integer viewNum, Integer created_by, Integer updated_by, String shape,
            Integer distance2Facade, Integer adjacentFacadeNum, String adjacentRoad, Integer alleyMinWidth,
            Integer adjacentAlleyMinWidth, Integer factor, String structure, Integer dTSXD, Integer cLCL, Integer ctxdPrice, Integer ctxdValue,
            String photo, Double lat, Double lng) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.request = request;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.street = street;
        this.project = project;
        this.address = address;
        this.customer = customer;
        this.price = price;
        this.priceMin = priceMin;
        this.priceTime = priceTime;
        this.dateCreate = dateCreate;
        this.acreage = acreage;
        this.direction = direction;
        this.totalFloors = totalFloors;
        this.numberFloors = numberFloors;
        this.bath = bath;
        this.apartCode = apartCode;
        this.wallArea = wallArea;
        this.bedroom = bedroom;
        this.balcony = balcony;
        this.landscapeView = landscapeView;
        this.apartLoca = apartLoca;
        this.apartType = apartType;
        this.furnitureType = furnitureType;
        this.priceRent = priceRent;
        this.returnRate = returnRate;
        this.legalDoc = legalDoc;
        this.description = description;
        this.widthY = widthY;
        this.longX = longX;
        this.streetHouse = streetHouse;
        FSBO = fSBO;
        this.viewNum = viewNum;
        this.created_by = created_by;
        this.updated_by = updated_by;
        this.shape = shape;
        this.distance2Facade = distance2Facade;
        this.adjacentFacadeNum = adjacentFacadeNum;
        this.adjacentRoad = adjacentRoad;
        this.alleyMinWidth = alleyMinWidth;
        this.adjacentAlleyMinWidth = adjacentAlleyMinWidth;
        this.factor = factor;
        this.structure = structure;
        DTSXD = dTSXD;
        CLCL = cLCL;
        this.ctxdPrice = ctxdPrice;
        this.ctxdValue = ctxdValue;
        this.photo = photo;
        this.lat = lat;
        this.lng = lng;
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
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public Integer getRequest() {
        return request;
    }
    public void setRequest(Integer request) {
        this.request = request;
    }
    public Province getProvince() {
        return province;
    }
    public void setProvince(Province province) {
        this.province = province;
    }
    public District getDistrict() {
        return district;
    }
    public void setDistrict(District district) {
        this.district = district;
    }
    public Ward getWard() {
        return ward;
    }
    public void setWard(Ward ward) {
        this.ward = ward;
    }
    public Street getStreet() {
        return street;
    }
    public void setStreet(Street street) {
        this.street = street;
    }
    public Project getProject() {
        return project;
    }
    public void setProject(Project project) {
        this.project = project;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public BigDecimal getPriceMin() {
        return priceMin;
    }
    public void setPriceMin(BigDecimal priceMin) {
        this.priceMin = priceMin;
    }
    public Integer getPriceTime() {
        return priceTime;
    }
    public void setPriceTime(Integer priceTime) {
        this.priceTime = priceTime;
    }
    public Date getDateCreate() {
        return dateCreate;
    }
    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }
    public Double getAcreage() {
        return acreage;
    }
    public void setAcreage(Double acreage) {
        this.acreage = acreage;
    }
    public Integer getDirection() {
        return direction;
    }
    public void setDirection(Integer direction) {
        this.direction = direction;
    }
    public Integer getTotalFloors() {
        return totalFloors;
    }
    public void setTotalFloors(Integer totalFloors) {
        this.totalFloors = totalFloors;
    }
    public Integer getNumberFloors() {
        return numberFloors;
    }
    public void setNumberFloors(Integer numberFloors) {
        this.numberFloors = numberFloors;
    }
    public Integer getBath() {
        return bath;
    }
    public void setBath(Integer bath) {
        this.bath = bath;
    }
    public String getApartCode() {
        return apartCode;
    }
    public void setApartCode(String apartCode) {
        this.apartCode = apartCode;
    }
    public Double getWallArea() {
        return wallArea;
    }
    public void setWallArea(Double wallArea) {
        this.wallArea = wallArea;
    }
    public Integer getBedroom() {
        return bedroom;
    }
    public void setBedroom(Integer bedroom) {
        this.bedroom = bedroom;
    }
    public Integer getBalcony() {
        return balcony;
    }
    public void setBalcony(Integer balcony) {
        this.balcony = balcony;
    }
    public String getLandscapeView() {
        return landscapeView;
    }
    public void setLandscapeView(String landscapeView) {
        this.landscapeView = landscapeView;
    }
    public Integer getApartLoca() {
        return apartLoca;
    }
    public void setApartLoca(Integer apartLoca) {
        this.apartLoca = apartLoca;
    }
    public Integer getApartType() {
        return apartType;
    }
    public void setApartType(Integer apartType) {
        this.apartType = apartType;
    }
    public Integer getFurnitureType() {
        return furnitureType;
    }
    public void setFurnitureType(Integer furnitureType) {
        this.furnitureType = furnitureType;
    }
    public Integer getPriceRent() {
        return priceRent;
    }
    public void setPriceRent(Integer priceRent) {
        this.priceRent = priceRent;
    }
    public Double getReturnRate() {
        return returnRate;
    }
    public void setReturnRate(Double returnRate) {
        this.returnRate = returnRate;
    }
    public Integer getLegalDoc() {
        return legalDoc;
    }
    public void setLegalDoc(Integer legalDoc) {
        this.legalDoc = legalDoc;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getWidthY() {
        return widthY;
    }
    public void setWidthY(Integer widthY) {
        this.widthY = widthY;
    }
    public Integer getLongX() {
        return longX;
    }
    public void setLongX(Integer longX) {
        this.longX = longX;
    }
    public Integer getStreetHouse() {
        return streetHouse;
    }
    public void setStreetHouse(Integer streetHouse) {
        this.streetHouse = streetHouse;
    }
    public Integer getFSBO() {
        return FSBO;
    }
    public void setFSBO(Integer fSBO) {
        FSBO = fSBO;
    }
    public Integer getViewNum() {
        return viewNum;
    }
    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }
    public Integer getCreated_by() {
        return created_by;
    }
    public void setCreated_by(Integer created_by) {
        this.created_by = created_by;
    }
    public Integer getUpdated_by() {
        return updated_by;
    }
    public void setUpdated_by(Integer updated_by) {
        this.updated_by = updated_by;
    }
    public String getShape() {
        return shape;
    }
    public void setShape(String shape) {
        this.shape = shape;
    }
    public Integer getDistance2Facade() {
        return distance2Facade;
    }
    public void setDistance2Facade(Integer distance2Facade) {
        this.distance2Facade = distance2Facade;
    }
    public Integer getAdjacentFacadeNum() {
        return adjacentFacadeNum;
    }
    public void setAdjacentFacadeNum(Integer adjacentFacadeNum) {
        this.adjacentFacadeNum = adjacentFacadeNum;
    }
    public String getAdjacentRoad() {
        return adjacentRoad;
    }
    public void setAdjacentRoad(String adjacentRoad) {
        this.adjacentRoad = adjacentRoad;
    }
    public Integer getAlleyMinWidth() {
        return alleyMinWidth;
    }
    public void setAlleyMinWidth(Integer alleyMinWidth) {
        this.alleyMinWidth = alleyMinWidth;
    }
    public Integer getAdjacentAlleyMinWidth() {
        return adjacentAlleyMinWidth;
    }
    public void setAdjacentAlleyMinWidth(Integer adjacentAlleyMinWidth) {
        this.adjacentAlleyMinWidth = adjacentAlleyMinWidth;
    }
    public Integer getFactor() {
        return factor;
    }
    public void setFactor(Integer factor) {
        this.factor = factor;
    }
    public String getStructure() {
        return structure;
    }
    public void setStructure(String structure) {
        this.structure = structure;
    }
    public Integer getDTSXD() {
        return DTSXD;
    }
    public void setDTSXD(Integer dTSXD) {
        DTSXD = dTSXD;
    }
    public Integer getCLCL() {
        return CLCL;
    }
    public void setCLCL(Integer cLCL) {
        CLCL = cLCL;
    }
    public Integer getCtxdPrice() {
        return ctxdPrice;
    }
    public void setCtxdPrice(Integer ctxdPrice) {
        this.ctxdPrice = ctxdPrice;
    }
    public Integer getCtxdValue() {
        return ctxdValue;
    }
    public void setCtxdValue(Integer ctxdValue) {
        this.ctxdValue = ctxdValue;
    }
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public Double getLat() {
        return lat;
    }
    public void setLat(Double lat) {
        this.lat = lat;
    }
    public Double getLng() {
        return lng;
    }
    public void setLng(Double lng) {
        this.lng = lng;
    }
    
}
