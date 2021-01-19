package com.example.jsonparsing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    public class WeatherDownloader extends AsyncTask<String,Void,String>{

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);

                String weatherInfo = jsonObject.getString("weather");

                Log.i("Website content",weatherInfo);

                JSONArray arr = new JSONArray(weatherInfo);

                for(int i=0;i<arr.length();i++){
                    JSONObject jsonPart = arr.getJSONObject(i);

                    String main=jsonPart.getString("main");

                    String description = jsonPart.getString("description");



                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... urls) {
            String results ="";
            URL url;

            HttpsURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection =(HttpsURLConnection) url.openConnection();

                InputStream reader = urlConnection.getInputStream();
                int data = reader.read();

                while(data !=-1){
                    char current = (char )data;
                    results += current;
                    data=reader.read();
                }

                return results;
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WeatherDownloader task = new WeatherDownloader();
        String results = null;
        task.execute("https://api.openweathermap.org/data/2.5/weather?q=Mumbai&appid=3273bb820374a1bda82f98fc63c15d46");
        //        try {
//            results = task.execute("https://api.openweathermap.org/data/2.5/weather?q=Mumbai&appid=3273bb820374a1bda82f98fc63c15d46").get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


    }
}