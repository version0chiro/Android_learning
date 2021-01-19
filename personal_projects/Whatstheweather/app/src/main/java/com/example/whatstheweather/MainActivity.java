package com.example.whatstheweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    TextView cityLabel;
    String city;
    TextView answerView;
    String answer="";

    public class APIcall extends AsyncTask<String,Void,String>{

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONObject jsonObject = new JSONObject(s);

                String weatherInfo = jsonObject.getString("weather");

                JSONArray arr = new JSONArray(weatherInfo);

                for(int i=0;i<arr.length();i++){
                    JSONObject jsonPart = arr.getJSONObject(i);

                    String main=jsonPart.getString("main");

                    String description = jsonPart.getString("description");

                    String temp = main+":"+description;

                    answer+=temp;
                }
                Log.i("final answer",answer);
                if(answer==null){
                    answerView.setText("Some error occured");

                }
                else{
                    answerView.setText(answer);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... urls) {
            String results = "";
            URL url;
            HttpsURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpsURLConnection) url.openConnection();

                InputStream reader = urlConnection.getInputStream();

                int data = reader.read();

                while(data!=-1){
                    char current = (char) data;
                    results+=current;
                    data = reader.read();
                }

            return results;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityLabel = (TextView) findViewById(R.id.cityTextBox);
        answerView = (TextView) findViewById(R.id.Answer);

    }

    public void Go(View view) {
        answer = "";
        city= cityLabel.getText().toString();
            city = city.toLowerCase();

            APIcall task = new APIcall();

            task.execute("https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=3273bb820374a1bda82f98fc63c15d46");



    }
}