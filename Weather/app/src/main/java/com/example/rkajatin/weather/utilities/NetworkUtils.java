package com.example.rkajatin.weather.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import static com.example.rkajatin.weather.utilities.NetworkUtils.Weather.CURRENT;

public class NetworkUtils {

    public enum Weather {
        CURRENT,
        HOURLY,
        DAILY
    }

    private final static String WEATHER_BASE_URL = "https://api.weatherbit.io/v2.0/";
    private final static String PARAM_CITY       = "city";
    private final static String PARAM_LANG       = "lang";
    private final static String WEATHER_LANG     = "en";
    private final static String PARAM_UNITS      = "units";
    private final static String WEATHER_UNITS    = "M";
    private final static String PARAM_KEY        = "key";
    private final static String WEATHER_KEY      = "3948972e25064110a2622651d635d88a";

    public static URL buildURL(String weatherInCity, Weather freq, int interval) {
        Uri weatherURI = null;

        switch(freq) {
            case CURRENT:
                weatherURI = Uri.parse(WEATHER_BASE_URL+"current").buildUpon()
                        .appendQueryParameter(PARAM_CITY, weatherInCity)
                        .appendQueryParameter(PARAM_LANG, WEATHER_LANG)
                        .appendQueryParameter(PARAM_UNITS, WEATHER_UNITS)
                        .appendQueryParameter(PARAM_KEY, WEATHER_KEY)
                        .build();
                break;
            case HOURLY:
                weatherURI = Uri.parse(WEATHER_BASE_URL+"forecast/hourly").buildUpon()
                        .appendQueryParameter(PARAM_CITY, weatherInCity)
                        .appendQueryParameter(PARAM_LANG, WEATHER_LANG)
                        .appendQueryParameter(PARAM_UNITS, WEATHER_UNITS)
                        .appendQueryParameter("hours", Integer.toString(interval))
                        .appendQueryParameter(PARAM_KEY, WEATHER_KEY)
                        .build();
                break;
            case DAILY:
                weatherURI = Uri.parse(WEATHER_BASE_URL+"forecast/daily").buildUpon()
                        .appendQueryParameter(PARAM_CITY, weatherInCity)
                        .appendQueryParameter(PARAM_LANG, WEATHER_LANG)
                        .appendQueryParameter(PARAM_UNITS, WEATHER_UNITS)
                        .appendQueryParameter("days", Integer.toString(interval))
                        .appendQueryParameter(PARAM_KEY, WEATHER_KEY)
                        .build();
                break;
            default: break;
        }

        URL weatherURL = null;
        try {
            weatherURL = new URL(weatherURI.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return weatherURL;
    }

    public static String fetchWeatherDataFromURL(URL url) throws IOException {
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        try {
            InputStream ins = urlConnection.getInputStream();

            Scanner scanner = new Scanner(ins);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
