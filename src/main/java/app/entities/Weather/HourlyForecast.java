package app.entities.Weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HourlyForecast {

    @JsonProperty("time")
    private List<String> time;

    @JsonProperty("temperature_2m")
    private List<Float> temperature;

    @JsonProperty("rain")
    private List<Float> rain;


    public List<String> getTime() {
        return time;
    }
    public List<Float> getTemperature() {
        return temperature;
    }
    public List<Float> getRain() {
        return rain;
    }
}
