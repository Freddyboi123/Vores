package app.utils.devTools;

import app.entities.Weather.LocationAttributes;
import app.entities.Weather.WeeklyForcast;
import app.utils.WeatherApiHandler.WeatherApi;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadsClass {
    ExecutorService executor = Executors.newFixedThreadPool(2);
    WeatherApi weatherApi = new WeatherApi();



    public Callable<Integer> task() {
        return () -> {
            Thread.sleep(1000);
            return 42;
        };
    }

    public Callable<WeeklyForcast> getWeather() {
        return () -> {
            return weatherApi.getWeatherByCity("Højby","Odsherred Kommune");
        };
    }

}


