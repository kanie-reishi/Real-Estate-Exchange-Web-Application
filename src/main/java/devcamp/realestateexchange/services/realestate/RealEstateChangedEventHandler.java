package devcamp.realestateexchange.services.realestate;
import com.fasterxml.jackson.databind.ObjectMapper;
import devcamp.realestateexchange.entity.realestate.RealEstate;
import devcamp.realestateexchange.event.RealEstateChangedEvent;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
@Service
public class RealEstateChangedEventHandler {
    @Autowired
    private RestHighLevelClient client;
    @EventListener
    public void handleRealEstateChangedEvent(RealEstateChangedEvent event) {
        RealEstate realestate = event.getEntity();

        // convert entity to JSON
        try {
            String json = new ObjectMapper().writeValueAsString(realestate);

            // create index request
            IndexRequest request = new IndexRequest("realesate_index");
            request.id(realestate.getId().toString());
            request.source(json);

            // index data into Elasticsearch
            IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
