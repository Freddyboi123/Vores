package app.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;

public class ApiService
{
static ObjectMapper objectMapper = new ObjectMapper();
    public ApiService() {}

    public static JsonNode getApiData (String url)
    {
        try
        {
            JsonNode node = objectMapper.readTree(new URI(url).toURL());
            return node;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
