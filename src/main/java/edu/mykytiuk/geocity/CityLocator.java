package edu.mykytiuk.geocity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class CityLocator {
    private final String apiURL = "https://maps.googleapis.com/maps/api/geocode/json?sensor=false&address=";
    private String city;

    public CityLocator(String city) {
        this.city = city;
    }

    /**
     * Send request to server and parse answer into city location
     *
     * @return location of the city
     */
    public String locate() {
        //send request & reading response
        String jsonLocaleString = readSiteResponse(apiURL + city);
        final JSONObject jsonLocale = new JSONObject(jsonLocaleString);

        //get response status
        String readStatus = jsonLocale.getString("status");
        //checking is status = OK
        if (readStatus.equals("OK")) {
            //parsing response & return location
            JSONObject location = jsonLocale.getJSONArray("results").getJSONObject(0);
            location = location.getJSONObject("geometry");
            location = location.getJSONObject("location");
            final double lng = location.getDouble("lng");
            final double lat = location.getDouble("lat");

            return "{" + lat + "," + lng + "}";
        }
        else {
            System.err.println("Sorry! Something is wrong with city name: " + city);
            return "{" + 0 + "," + 0 + "}";
        }
    }

    /**
     * Gets city location from Google API
     *
     * @return json text, response from server
     */
    private String readSiteResponse(String url) {
        try(BufferedReader urlReader = new BufferedReader(new InputStreamReader(new URL(url).openStream(), Charset.forName("UTF-8")))) {
            final StringBuilder responseBuilder = new StringBuilder();
            int responseChar;
            while ((responseChar = urlReader.read()) != -1) {
                responseBuilder.append((char) responseChar);
           }
           return responseBuilder.toString();
        }
        catch (MalformedURLException e) {
            System.err.println("Sorry! Something is wrong with city name. URL: " + url + "does not work.");
            e.printStackTrace();
        }
        catch (IOException e) {
            System.err.println("Sorry! Some wrong with reading from URL: " + url + ".");
            e.printStackTrace();
        }
        return "";
    }
}
