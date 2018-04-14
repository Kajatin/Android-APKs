package com.example.rkajatin.weather;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.rkajatin.weather.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import static com.example.rkajatin.weather.utilities.NetworkUtils.Weather.CURRENT;
import static com.example.rkajatin.weather.utilities.NetworkUtils.Weather.HOURLY;
import static com.example.rkajatin.weather.utilities.NetworkUtils.Weather.DAILY;

public class MainActivity extends Activity {

    private TextView tvURLDisplay;
    private TextView tvWeatherData;
    private TextView tvWeatherDate;
    private TextView tvTemperature;
    private TextView tvWeatherDescription;
    private TextView tvWeatherInCity;
    private Button btQueryWeather;
    private TableLayout tlMainWeatherData;
    private ImageView ivWeatherIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvURLDisplay = (TextView) findViewById(R.id.tv_url_display);
        tvWeatherData = (TextView) findViewById(R.id.tv_weather_data);
        tvWeatherDate = (TextView) findViewById(R.id.tv_weather_date);
        tvTemperature = (TextView) findViewById(R.id.tv_temperature);
        tvWeatherDescription = (TextView) findViewById(R.id.tv_weather_description);
        tvWeatherInCity = (TextView) findViewById(R.id.tv_weather_in_city);
        btQueryWeather = (Button) findViewById(R.id.bt_query_weather);
        tlMainWeatherData = (TableLayout) findViewById(R.id.tl_main_weather_data);
        ivWeatherIcon = (ImageView) findViewById(R.id.iv_weather_icon);

        tvURLDisplay.setVisibility(View.INVISIBLE);
        tvWeatherData.setVisibility(View.INVISIBLE);
        tlMainWeatherData.setVisibility(View.INVISIBLE);
        btQueryWeather.setVisibility(View.VISIBLE);

        btQueryWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btQueryWeather.setVisibility(View.INVISIBLE);
                fetchWeatherData("Aalborg", CURRENT, 9);
            }
        });
    }

    private void fetchWeatherData(String inCity, NetworkUtils.Weather freq, int interval) {
        URL weatherFetchURL = NetworkUtils.buildURL(inCity, freq, interval);
        tvURLDisplay.setVisibility(View.VISIBLE);
        tvURLDisplay.setText(weatherFetchURL.toString());
        switch(freq) {
            case CURRENT:
                new FetchCurrentWeatherData().execute(weatherFetchURL);
                break;
            case HOURLY:
                new FetchHourlyWeatherData().execute(weatherFetchURL);
                break;
            case DAILY:
                new FetchDailyWeatherData().execute(weatherFetchURL);
                break;
        }
    }

    public class FetchCurrentWeatherData extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... urls) {
            String weatherData = null;
            try {
                weatherData = NetworkUtils.fetchWeatherDataFromURL(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return weatherData;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s != null && !s.equals("")) {
                try {
                    JSONObject jsonWeatherObj = new JSONObject(s);
                    JSONArray jsonDataArray = jsonWeatherObj.getJSONArray("data");
                    JSONObject jsonData = jsonDataArray.getJSONObject(0);
                    String cityName = jsonData.getString("city_name");
                    String currentDate = jsonData.getString("ob_time");
                    String temperature = jsonData.getString("temp");
                    JSONObject jsonWeather = jsonData.getJSONObject("weather");
                    String iconID = jsonWeather.getString("icon");
                    String weatherDescription = jsonWeather.getString("description");

                    tlMainWeatherData.setVisibility(View.VISIBLE);
                    tvWeatherDate.setText(currentDate);
                    tvTemperature.setText(temperature);
                    tvWeatherDescription.setText(weatherDescription);
                    tvWeatherInCity.setText(cityName);
                    if(iconID.equals("c03n"))
                        ivWeatherIcon.setImageResource(R.drawable.c03n);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                super.onPostExecute(s);
            }
        }
    }

    public class FetchHourlyWeatherData extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... urls) {
            String weatherData = null;
            try {
                weatherData = NetworkUtils.fetchWeatherDataFromURL(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return weatherData;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s != null && !s.equals("")) {
                try {
                    JSONObject jsonWeatherObj = new JSONObject(s);
                    String cityName = jsonWeatherObj.getString("city_name");
                    JSONArray jsonDataArray = jsonWeatherObj.getJSONArray("data");
                    JSONObject jsonData = jsonDataArray.getJSONObject(0);
                    String currentDate = jsonData.getString("datetime");
                    String temperature = jsonData.getString("temp");
                    JSONObject jsonWeather = jsonData.getJSONObject("weather");
                    String iconID = jsonWeather.getString("icon");
                    String weatherDescription = jsonWeather.getString("description");

                    tlMainWeatherData.setVisibility(View.VISIBLE);
                    tvWeatherDate.setText(currentDate);
                    tvTemperature.setText(temperature);
                    tvWeatherDescription.setText(weatherDescription);
                    tvWeatherInCity.setText(cityName);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                super.onPostExecute(s);
            }
        }
    }

    public class FetchDailyWeatherData extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... urls) {
            String weatherData = null;
            try {
                weatherData = NetworkUtils.fetchWeatherDataFromURL(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return weatherData;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s != null && !s.equals("")) {
                try {
                    JSONObject jsonWeatherObj = new JSONObject(s);
                    String cityName = jsonWeatherObj.getString("city_name");
                    JSONArray jsonDataArray = jsonWeatherObj.getJSONArray("data");
                    JSONObject jsonData = jsonDataArray.getJSONObject(0);
                    String currentDate = jsonData.getString("datetime");
                    String temperature = jsonData.getString("temp");
                    JSONObject jsonWeather = jsonData.getJSONObject("weather");
                    String iconID = jsonWeather.getString("icon");
                    String weatherDescription = jsonWeather.getString("description");

                    tlMainWeatherData.setVisibility(View.VISIBLE);
                    tvWeatherDate.setText(currentDate);
                    tvTemperature.setText(temperature);
                    tvWeatherDescription.setText(weatherDescription);
                    tvWeatherInCity.setText(cityName);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                super.onPostExecute(s);
            }
        }
    }
}
