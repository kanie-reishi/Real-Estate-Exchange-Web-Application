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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Map;
@Service
public class RealEstateChangedEventHandler {
    @Autowired
    private RestHighLevelClient client;

    private static final Logger logger = LoggerFactory.getLogger(RealEstateChangedEventHandler.class);
    @EventListener
    public void handleRealEstateChangedEvent(RealEstateChangedEvent event) {
        RealEstate realestate = event.getEntity();

        // convert entity to JSON
        try {
            String json = new ObjectMapper().writeValueAsString(realestate);
            logger.info("JSON: {}", json);
            // Convert JSON string to Map
            Map<String, Object> map = new ObjectMapper().readValue(json, new TypeReference<Map<String, Object>>(){});

            // create index request
            IndexRequest request = new IndexRequest("realesate_index");
            request.id(realestate.getId().toString());
            request.source(map);

            // index data into Elasticsearch
            IndexResponse respones = client.index(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
