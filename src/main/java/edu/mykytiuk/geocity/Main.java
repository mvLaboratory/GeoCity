package edu.mykytiuk.geocity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        System.out.print("Please enter the file name: ");

        String fileName;

        //reading filename from the keyboard
        try (BufferedReader fileNameReader = new BufferedReader(new InputStreamReader(System.in))) {
            fileName = fileNameReader.readLine();
        }
        catch (IOException e) {
            System.err.println("Sorry. Error reading file name.");
            e.printStackTrace();
            return;
        }

        //create and initialize file reader
        CityReader cityReader = CityReader.getInstance(fileName);

        //iterate through the file content
        while (cityReader.hasNext()) {
            //get the next city from file content
            String city = cityReader.next();

            //get city location
            String cityLocation = new CityLocator(city).locate();

            //output
            System.out.print(city + ": ");
            System.out.println(cityLocation);
        }

    }
}
