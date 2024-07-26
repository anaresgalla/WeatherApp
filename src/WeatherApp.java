/* retrieve weather data from API - this backend logic will fetch the latest weather
data from the external API and return it.
The GUI will display it to the user */

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Scanner;
import java.net.URL;

public class WeatherApp {
    // fetch weather data for a given location
    public static JSONObject getWeatherData(String locationName) {
        // get location coordinates using the geolocation API
        JSONArray locationData = getLocationData(locationName);

        // extract latitude and longitude data
        JSONObject location = (JSONObject) locationData.get(0);
        double latitude = (double) location.get("latitude");
        double longitude = (double) location.get("longitude");

        // build API request URL with location coordinates
        String urlString = "https://api.open-meteo.com/v1/forecast?" +
                "latitude=" + latitude + "&longitude=" + longitude +
                "&hourly=temperature_2m,relative_humidity_2m,weather_code,wind_speed_10m&timezone=America%2FSao_Paulo";

        try{
            // call api and get response
            HttpURLConnection conn = fetchApiResponse(urlString);

            // check for response status
            if(conn.getResponseCode() != 200){
                System.out.println("Error: Could not connect to API");
                return null;
            }

            // store result in JSON data
            StringBuilder resultJson = new StringBuilder();
            Scanner scanner = new Scanner(conn.getInputStream());
            while(scanner.hasNext()){
                // read and storre in the string builder
                resultJson.append(scanner.nextLine());
            }
            //close scanner
            scanner.close();
            // close url connection
            conn.disconnect();

            // parse through our data
            JSONParser parser = new JSONParser();
            JSONObject resultJsonObj = (JSONObject) parser.parse(String.valueOf(resultJson);


        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    // retrieves geographic coordinates for given location name
    public static JSONArray getLocationData(String locationName) {
        // replace any whitespace in location with + to adhere to API's request format
        locationName = locationName.replaceAll(" ", "+");

        // build API url with location parameter
        String urlString = " https://geocoding-api.open-meteo.com/v1/search?name=" +
                locationName + "&count=10&language=en&format=json";

        try {
            // call API and get response
            HttpURLConnection conn = fetchApiResponse(urlString);

            // check response status (200 = successful)
            if(conn.getResponseCode() != 200){
                System.out.println("Error: Could not connect to API");
                return null;
            }else{
                // store the API results
                StringBuilder resultJson = new StringBuilder();
                Scanner scanner = new Scanner(conn.getInputStream());

                // reading and storing the resulting JSON data into our string builder
                while(scanner.hasNext()){
                    resultJson.append(scanner.nextLine());
                }
                //close scanner
                scanner.close();

                // close url connection
                conn.disconnect();

                // parse the JSON string into a JSON object
                JSONParser parser = new JSONParser();
                JSONObject resultsJsonObj = (JSONObject) parser.parse(String.valueOf(resultJson));

                // get the list of location data the API generated from the location name
                JSONArray locationData = (JSONArray) resultsJsonObj.get("results");
                return locationData;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // could not find location
        return null;
    }

    // method defined as it will be called multiple times
    private static HttpURLConnection fetchApiResponse(String urlString) {
        try {
            // attempt to create connection
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //set request method to GET
            conn.setRequestMethod("GET");

            // connect to our API
            conn.connect();
            return conn;
        }catch(IOException e){
            e.printStackTrace();
        }

        // could not make connection
        return null;
    }
}