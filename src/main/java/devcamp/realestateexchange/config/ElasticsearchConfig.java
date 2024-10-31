package devcamp.realestateexchange.config;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

    @Value("${spring.elasticsearch.rest.uris}")
    private String elasticsearchUrl;

    @Value("${spring.elasticsearch.rest.username}")
    private String username;

    @Value("${spring.elasticsearch.rest.password}")
    private String password;

    @Value("${spring.elasticsearch.rest.port}")
    private int port;

    @Value("${elasticsearch.apikey}")
    private String apiKey;
    // Tạo một Bean RestClient
    // RestClient giúp chúng ta thao tác với Elasticsearch
   @Bean
    public RestClient client() {
        final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

        RestClientBuilder builder = RestClient.builder(new HttpHost(elasticsearchUrl, port, "http"))
                .setDefaultHeaders(new Header[]{new BasicHeader("Authorization", "ApiKey " + apiKey)});

        return builder.build();
    }
}
