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

import devcamp.realestateexchange.dto.realestate.ProjectDto;

@Service
public class ProjectChangedEventHandler {
    @Autowired
    private RestClient client;

    private static final Logger logger = LoggerFactory.getLogger(ProjectChangedEventHandler.class);
    
    // handle project changed event, index data into Elasticsearch
    @EventListener
    public void handleProjectChangedEvent(ProjectChangedEvent event) {
        ProjectDto project = event.getDto();

        // convert entity to JSON
        try {
            String json = new ObjectMapper().writeValueAsString(project);
            logger.info("JSON: {}", json);

            // create index request
            Request request = new Request("PUT", "/project_index/_doc/" + project.getId());
            request.setJsonEntity(json);

            // index data into Elasticsearch
            Response response = client.performRequest(request);
            logger.info("Response: {}", response);
        } catch (Exception e) {
            logger.error("Error indexing project data", e);
        }
    }
}
