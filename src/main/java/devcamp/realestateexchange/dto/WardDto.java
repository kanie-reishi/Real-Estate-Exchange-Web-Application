package devcamp.realestateexchange.dto;
public class WardDto {
    private Integer id;
    private String name;
    public WardDto() {
    }
    public WardDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
