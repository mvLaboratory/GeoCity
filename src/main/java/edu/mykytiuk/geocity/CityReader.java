package edu.mykytiuk.geocity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CityReader {
    private static CityReader instance;
    private static int currentCity = 0;
    private String fileName;
    private ArrayList<String> cityList;

    public static CityReader getInstance(String fileName) {
        if (instance == null) {
            return new CityReader(fileName);
        }
        return instance;
    }

    private CityReader(String fileName) {
        //initialize class variables
        this.fileName = fileName;
        cityList = new ArrayList<String>();

        //read file
        read();
    }

    /**
     * Read from file and store it context in ArrayList
     */
    private void read() {
        try(BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {
            while (fileReader.ready()) {
                cityList.add(fileReader.readLine());
            }
        }
        catch (IOException e) {
            System.err.println("Sorry. Can't read this file: " + fileName);
            e.printStackTrace();
        }
    }

    /**
     * Check if there another city for read
     *
     * @return true if there is another city
     */
    public boolean hasNext() {
        return currentCity < cityList.size();
    }

    /**
     * Returns next city from the list
     *
     * @return city name
     */
    public String next() {
        String temp = cityList.get(currentCity);
        currentCity++;
        return temp;
    }
}
