package app.entities.Weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationAttributes {
    @JsonProperty("latitude")
    float latitude;
    @JsonProperty("longitude")
    float longitude;
    @JsonProperty("admin2")
    String Area;
    @JsonProperty("name")
    String city;
    public LocationAttributes() {
    }

    @Override
    public String toString() {
        return "city: " + city +
                "\n" + "Area: " + Area +
                "\n" + "latitude: " + latitude +
                "\n" + "longitude: " + longitude + "\n";
    }
}
