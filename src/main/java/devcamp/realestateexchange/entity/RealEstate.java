package devcamp.realestateexchange.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "real_estate")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedEntityGraph(name = "RealEstate.photos",
    attributeNodes = @NamedAttributeNode("photos"))
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id")
    @JsonBackReference
    private Province province;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    @JsonBackReference
    private District district;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ward_id")
    @JsonBackReference
    private Ward ward;
    @ManyToOne(fetch = FetchType.LAZY)
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
    @OneToMany(mappedBy = "realEstate", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Photo> photos;
    @Column(name = "lat")
    private Double lat;
    @Column(name = "lng")
    private Double lng;
}
