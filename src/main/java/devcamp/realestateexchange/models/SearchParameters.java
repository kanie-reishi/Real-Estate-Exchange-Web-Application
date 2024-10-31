package devcamp.realestateexchange.models;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// SearchParameters model class. This class is used to store the search parameters.
public class SearchParameters {
    String searchText;
    Integer size;
    Integer from;
    String sort;
    String order;
}
