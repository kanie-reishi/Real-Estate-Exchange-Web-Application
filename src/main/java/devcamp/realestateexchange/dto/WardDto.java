package devcamp.realestateexchange.dto;
public class WardDto {
    private int id;
    private String name;
    public WardDto() {
    }
    public WardDto(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
