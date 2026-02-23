package app.entities.Weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeeklyForcast {

    @JsonProperty("hourly")
    private HourlyForecast hourly;

    // REQUIRED by Jackson
    public WeeklyForcast() {}

    public HourlyForecast getHourly() {
        return hourly;
    }

    public void getWeeklyForecast() {

        int hoursPerWeek = 7 * 24;
        int units = hourly.getTemperature().size();


        for (int i = 0; i < units; i++) {

            if(i % 24 == 0){
                System.out.println("\n" + "Day:" + (i/24)+1);
            }
            System.out.println(
                    this.getHourly().getTime().get(i) + " -> " +
                            this.getHourly().getTemperature().get(i) + "°C, rain: " +
                            this.getHourly().getRain().get(i) + " mm"
            );
        }
    }
}