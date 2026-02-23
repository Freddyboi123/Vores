package app.utils.WeatherApiHandler;

import app.entities.Weather.GeoData;
import app.entities.Weather.LocationAttributes;
import app.entities.Weather.WeeklyForcast;
import app.utils.ApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WeatherApi {


    ApiService apiService = new ApiService();
    ObjectMapper objectMapper = new ObjectMapper();


    public LocationAttributes getLocation(String city, String area) {
        String geoUrl = "https://geocoding-api.open-meteo.com/v1/search?" +
                "name=€" +
                "&count=10" +
                "&language=en" +
                "&format=json";

        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
        geoUrl = geoUrl.replace("€", encodedCity);
        JsonNode response = apiService.getApiData(geoUrl);

        GeoData geoData = null;
        try{
            geoData = objectMapper.treeToValue(response, GeoData.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        LocationAttributes finalLocation = null; 
        for (LocationAttributes locationAttributes : geoData.getAttributes()){
            if (locationAttributes.getArea().equalsIgnoreCase(area)){
                finalLocation = locationAttributes;
            }
        }
        return finalLocation;
    }


    public WeeklyForcast getWeather(float latitude, float longitude) {
        String url = "https://api.open-meteo.com/v1/forecast"
                + "?latitude=€"
                + "&longitude=€1"
                + "&hourly=temperature_2m,rain";

        String realLatitude = String.valueOf(latitude);
        String realLongitude = String.valueOf(longitude);

        url = url.replace("€",realLatitude);
        url = url.replace("€1",realLongitude);

        JsonNode response = apiService.getApiData(url);

        try {
            return objectMapper.treeToValue(response, WeeklyForcast.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    public WeeklyForcast getWeatherByCity(String city, String area) {
        LocationAttributes locationAttributes = getLocation(city, area);
        WeeklyForcast todaysForcast = getWeather(locationAttributes.getLatitude(),locationAttributes.getLongitude());
        return todaysForcast;
    }

}


