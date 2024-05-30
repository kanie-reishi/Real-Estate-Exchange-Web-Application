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
@Entity
@Table(name = "real_estate")
public class RealEstate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "type")
    private int type;
    @Column(name = "request")
    private int request;
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
    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonBackReference
    private Project project;
    @Column(name = "address")
    private String address;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "price_min")
    private BigDecimal priceMin;
    @Column(name = "price_time")
    private int priceTime;
    @Column(name = "date_create")
    private Date dateCreate;
    @Column(name = "acreage")
    private double acreage;
    @Column(name = "direction")
    private int direction;
    @Column(name = "total_floors")
    private int totalFloors;
    @Column(name = "number_floors")
    private int numberFloors;
    @Column(name = "bath")
    private int bath;
    @Column(name = "apart_code")
    private String apartCode;
    @Column(name = "wall_area")
    private double wallArea;
    @Column(name = "bedroom")
    private int bedroom;
    @Column(name = "balcony")
    private int balcony;
    @Column(name = "landscape_view")    
    private String landscapeView;
    @Column(name = "apart_loca")
    private int apartLoca;
    @Column(name = "apart_type")
    private int apartType;
    @Column(name = "furniture_type")
    private int furnitureType;
    @Column(name = "price_rent")
    private int priceRent;
    @Column(name = "return_rate")
    private double returnRate;
    @Column(name = "legal_doc")
    private int legalDoc;
    @Column(name = "description")
    private String description;
    @Column(name = "width_y")
    private int widthY;
    @Column(name = "long_x")
    private int longX;
    @Column(name = "street_house")
    private int streetHouse;
    @Column(name = "FSBO")
    private int FSBO;
    @Column(name = "view_num")
    private int viewNum;
    @Column(name = "created_by")
    private int created_by;
    @Column(name = "updated_by")
    private int updated_by;
    @Column(name = "shape")
    private String shape;
    @Column(name = "distance2facade")
    private int distance2Facade;
    @Column(name = "adjacent_facade_num")
    private int adjacentFacadeNum;
    @Column(name = "adjacent_road")
    private String adjacentRoad;
    @Column(name = "alley_min_width")
    private int alleyMinWidth;
    @Column(name = "adjacent_alley_min_width")
    private int adjacentAlleyMinWidth;
    @Column(name = "factor")
    private int factor;
    @Column(name = "structure")
    private String structure;
    @Column(name = "DTSXD")
    private int DTSXD;
    @Column(name = "CLCL")
    private int CLCL;
    @Column(name = "CTXD_price")
    private int ctxdPrice;
    @Column(name = "CTXD_value")
    private int ctxdValue;
    @Column(name = "photo")
    private String photo;
    @Column(name = "lat")
    private double lat;
    @Column(name = "lng")
    private double lng;
    public RealEstate() {
    }
    public RealEstate(int id, String title, int type, int request, Province province, District district, Ward ward,
            Street street, Project project, String address, Customer customer, BigDecimal price, BigDecimal priceMin, int priceTime,
            Date dateCreate, double acreage, int direction, int totalFloors, int numberFloors, int bath,
            String apartCode, double wallArea, int bedroom, int balcony, String landscapeView, int apartLoca,
            int apartType, int furnitureType, int priceRent, double returnRate, int legalDoc, String description,
            int widthY, int longX, int streetHouse, int fSBO, int viewNum, int created_by, int updated_by, String shape,
            int distance2Facade, int adjacentFacadeNum, String adjacentRoad, int alleyMinWidth,
            int adjacentAlleyMinWidth, int factor, String structure, int dTSXD, int cLCL, int ctxdPrice, int ctxdValue,
            String photo, double lat, double lng) {
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
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public int getRequest() {
        return request;
    }
    public void setRequest(int request) {
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
    public int getPriceTime() {
        return priceTime;
    }
    public void setPriceTime(int priceTime) {
        this.priceTime = priceTime;
    }
    public Date getDateCreate() {
        return dateCreate;
    }
    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }
    public double getAcreage() {
        return acreage;
    }
    public void setAcreage(double acreage) {
        this.acreage = acreage;
    }
    public int getDirection() {
        return direction;
    }
    public void setDirection(int direction) {
        this.direction = direction;
    }
    public int getTotalFloors() {
        return totalFloors;
    }
    public void setTotalFloors(int totalFloors) {
        this.totalFloors = totalFloors;
    }
    public int getNumberFloors() {
        return numberFloors;
    }
    public void setNumberFloors(int numberFloors) {
        this.numberFloors = numberFloors;
    }
    public int getBath() {
        return bath;
    }
    public void setBath(int bath) {
        this.bath = bath;
    }
    public String getApartCode() {
        return apartCode;
    }
    public void setApartCode(String apartCode) {
        this.apartCode = apartCode;
    }
    public double getWallArea() {
        return wallArea;
    }
    public void setWallArea(double wallArea) {
        this.wallArea = wallArea;
    }
    public int getBedroom() {
        return bedroom;
    }
    public void setBedroom(int bedroom) {
        this.bedroom = bedroom;
    }
    public int getBalcony() {
        return balcony;
    }
    public void setBalcony(int balcony) {
        this.balcony = balcony;
    }
    public String getLandscapeView() {
        return landscapeView;
    }
    public void setLandscapeView(String landscapeView) {
        this.landscapeView = landscapeView;
    }
    public int getApartLoca() {
        return apartLoca;
    }
    public void setApartLoca(int apartLoca) {
        this.apartLoca = apartLoca;
    }
    public int getApartType() {
        return apartType;
    }
    public void setApartType(int apartType) {
        this.apartType = apartType;
    }
    public int getFurnitureType() {
        return furnitureType;
    }
    public void setFurnitureType(int furnitureType) {
        this.furnitureType = furnitureType;
    }
    public int getPriceRent() {
        return priceRent;
    }
    public void setPriceRent(int priceRent) {
        this.priceRent = priceRent;
    }
    public double getReturnRate() {
        return returnRate;
    }
    public void setReturnRate(double returnRate) {
        this.returnRate = returnRate;
    }
    public int getLegalDoc() {
        return legalDoc;
    }
    public void setLegalDoc(int legalDoc) {
        this.legalDoc = legalDoc;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getWidthY() {
        return widthY;
    }
    public void setWidthY(int widthY) {
        this.widthY = widthY;
    }
    public int getLongX() {
        return longX;
    }
    public void setLongX(int longX) {
        this.longX = longX;
    }
    public int getStreetHouse() {
        return streetHouse;
    }
    public void setStreetHouse(int streetHouse) {
        this.streetHouse = streetHouse;
    }
    public int getFSBO() {
        return FSBO;
    }
    public void setFSBO(int fSBO) {
        FSBO = fSBO;
    }
    public int getViewNum() {
        return viewNum;
    }
    public void setViewNum(int viewNum) {
        this.viewNum = viewNum;
    }
    public int getCreated_by() {
        return created_by;
    }
    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }
    public int getUpdated_by() {
        return updated_by;
    }
    public void setUpdated_by(int updated_by) {
        this.updated_by = updated_by;
    }
    public String getShape() {
        return shape;
    }
    public void setShape(String shape) {
        this.shape = shape;
    }
    public int getDistance2Facade() {
        return distance2Facade;
    }
    public void setDistance2Facade(int distance2Facade) {
        this.distance2Facade = distance2Facade;
    }
    public int getAdjacentFacadeNum() {
        return adjacentFacadeNum;
    }
    public void setAdjacentFacadeNum(int adjacentFacadeNum) {
        this.adjacentFacadeNum = adjacentFacadeNum;
    }
    public String getAdjacentRoad() {
        return adjacentRoad;
    }
    public void setAdjacentRoad(String adjacentRoad) {
        this.adjacentRoad = adjacentRoad;
    }
    public int getAlleyMinWidth() {
        return alleyMinWidth;
    }
    public void setAlleyMinWidth(int alleyMinWidth) {
        this.alleyMinWidth = alleyMinWidth;
    }
    public int getAdjacentAlleyMinWidth() {
        return adjacentAlleyMinWidth;
    }
    public void setAdjacentAlleyMinWidth(int adjacentAlleyMinWidth) {
        this.adjacentAlleyMinWidth = adjacentAlleyMinWidth;
    }
    public int getFactor() {
        return factor;
    }
    public void setFactor(int factor) {
        this.factor = factor;
    }
    public String getStructure() {
        return structure;
    }
    public void setStructure(String structure) {
        this.structure = structure;
    }
    public int getDTSXD() {
        return DTSXD;
    }
    public void setDTSXD(int dTSXD) {
        DTSXD = dTSXD;
    }
    public int getCLCL() {
        return CLCL;
    }
    public void setCLCL(int cLCL) {
        CLCL = cLCL;
    }
    public int getCtxdPrice() {
        return ctxdPrice;
    }
    public void setCtxdPrice(int ctxdPrice) {
        this.ctxdPrice = ctxdPrice;
    }
    public int getCtxdValue() {
        return ctxdValue;
    }
    public void setCtxdValue(int ctxdValue) {
        this.ctxdValue = ctxdValue;
    }
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public double getLat() {
        return lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }
    public double getLng() {
        return lng;
    }
    public void setLng(double lng) {
        this.lng = lng;
    }
    
}
