package com.geocoding.geocoding;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class GeocodingApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeocodingApplication.class, args);

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyDXv7dYOsnx4d8CaFy68HDYaiX4J4Pm290")
                .build();


        GeocodingResult[] results = null;


        try {
            results = GeocodingApi.geocode(context,
                    "sydney").await();


        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(results[0]));


        System.out.println(results.length);
        Geocode geocode = new Geocode();
        geocode.setLatitude(results[0].geometry.location.lat);
        geocode.setLongitude(results[0].geometry.location.lng);
        System.out.println(geocode);
    }


}
