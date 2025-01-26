package devcamp.realestateexchange.event;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import devcamp.realestateexchange.dto.realestate.RealEstateDto;
@Service
public class RealEstateChangedEventHandler {
    @Autowired
    private RestClient client;

    private static final Logger logger = LoggerFactory.getLogger(RealEstateChangedEventHandler.class);
    // handle real estate changed event, index data into Elasticsearch
    @EventListener
    public void handleRealEstateChangedEvent(RealEstateChangedEvent event) {
        RealEstateDto realestate = event.getDto();

        // convert entity to JSON
        try {
            String json = new ObjectMapper().writeValueAsString(realestate);
            logger.info("JSON: {}", json);

            // create index request
            Request request = new Request("PUT", "/realestate_index/_doc/" + realestate.getId());
            request.setJsonEntity(json);

            // index data into Elasticsearch
            Response response = client.performRequest(request);
            logger.info("Response: {}", response);
        } catch (Exception e) {
            logger.error("Error indexing real estate data", e);
        }
    }
}
