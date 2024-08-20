package devcamp.realestateexchange.entity.media;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import devcamp.realestateexchange.entity.BaseEntity;
import devcamp.realestateexchange.entity.realestate.Project;
import devcamp.realestateexchange.entity.realestate.RealEstate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "video")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Video extends BaseEntity {
    // Tên video
    @Column(name = "name")
    private String name;

    // Url video
    @Column(name = "url")
    @NotEmpty(message = "Video url is required")
    private String url;

    // Loại video
    @Column(name = "type")
    private String type;

    // Kích thước video
    @Column(name = "size")
    private Long size;

    // Quan hệ n-1 với RealEstate
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "real_estate_id")
    @JsonIgnore
    private RealEstate realEstate;

    // Ghi chú
    @Column(name = "note")
    private String note;

    // Quan hệ n-1 với Project
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private Project project;
}
