package com.geocoding.geocoding;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.PlaceAutocompleteRequest;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.AutocompletePrediction;
import com.google.maps.model.ComponentFilter;
import com.google.maps.model.PlaceDetails;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class PlaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeocodingApplication.class, args);

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyDXv7dYOsnx4d8CaFy68HDYaiX4J4Pm290")
                .build();

        //constrain the results to be in Canada
        ComponentFilter componentFilter = new ComponentFilter("country", "CA");
        PlaceAutocompleteRequest.SessionToken sessionToken = new PlaceAutocompleteRequest.SessionToken();

        //1.non-exist address
        callGoogleService("\\\\", componentFilter, context, sessionToken);

        //2. ambiguous address
        callGoogleService("sydney", componentFilter, context, sessionToken);

        //3.valid postal code
        callGoogleService("H4V1R9", componentFilter, context, sessionToken);

        //4.invalid postal code
        callGoogleService("Z11111", componentFilter, context, sessionToken);

        //5.valid street number+street name
        callGoogleService("4200 Dorchester Boulevard", componentFilter, context, sessionToken);

        //6.invalid street number+street name
        callGoogleService("54200 Dorchester Boulevard", componentFilter, context, sessionToken);

        //7.street name
        callGoogleService("Dorchester Boulevard", componentFilter, context, sessionToken);


    }

    private static void callGoogleService(String input, ComponentFilter componentFilter, GeoApiContext context, PlaceAutocompleteRequest.SessionToken sessionToken) {
        try {
            AutocompletePrediction[] placesSearchResults = null;
            PlaceAutocompleteRequest placeAutocompleteRequest = PlacesApi.placeAutocomplete(context, input, sessionToken);
            placeAutocompleteRequest.components(componentFilter);
            placesSearchResults = placeAutocompleteRequest.await();
            printoutResult(context, input, placesSearchResults);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printoutResult(GeoApiContext context, String input, AutocompletePrediction[] autocompletePredictions) throws InterruptedException, ApiException, IOException {
        System.out.println("************************************************");
        System.out.println("Input is: " + input);
        if (autocompletePredictions.length != 0) {
            System.out.println("Found " + autocompletePredictions.length + " results:");
            for (AutocompletePrediction autocompletePrediction : autocompletePredictions) {
                System.out.println(autocompletePrediction.description);

                //Here you can get the detail information about this place, like street view, business name, review, etc.
//                Gson gson = new GsonBuilder().setPrettyPrinting().create();
//                PlaceDetails placeDetails = PlacesApi.placeDetails(context, autocompletePrediction.placeId).await();
//                System.out.println(gson.toJson(placeDetails));
            }
        } else {
            System.out.println("No result found for this input.");
        }
        System.out.println();
    }
}


