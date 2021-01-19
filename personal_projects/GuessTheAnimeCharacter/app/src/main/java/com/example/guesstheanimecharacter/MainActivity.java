package com.example.guesstheanimecharacter;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    TextView winLabel;
    ImageView img;
    String correctAns;
    List<String> urlStrings = new ArrayList<>();
    List<String> nameStrings = new ArrayList<>();

    public class DownloadHtml extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {

            String result = ""; //output

            URL url; //link

            HttpURLConnection urlConnection = null; //browser
            try{
                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data !=-1){

                    char current = (char)data ;

                    result +=current;
                    data = reader.read();
                }

                return result;
            }
            catch (Exception e){
                e.printStackTrace();
                return "FAILED";
            }
//            Log.i("URL",strings[0]);


        }
    }

    public class ImageDownloader extends AsyncTask<String,Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {
            try{
                URL url = new URL(urls[0]);

                HttpURLConnection connection = (HttpsURLConnection) url.openConnection();

                connection.connect();

                InputStream inputStream = connection.getInputStream();

                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
                return myBitmap;

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
        button1 = (Button) findViewById(R.id.option1);
        button2 = (Button) findViewById(R.id.option2);
        button3 = (Button) findViewById(R.id.option3);
        button4 = (Button) findViewById(R.id.option4);
        img = (ImageView) findViewById(R.id.imageView);
        winLabel = (TextView) findViewById(R.id.winLabel);
        DownloadHtml htmlTask = new DownloadHtml();
        String results = null;
        try{
            results = htmlTask.execute("https://www.fandomspot.com/strongest-anime-characters/").get();
        }catch (Exception  e){
            e.printStackTrace();
        }

        Pattern p = Pattern.compile("img src=(.*?)>");

        Matcher m = p.matcher(results);

        while (m.find()){

            String[] temp = m.group(1).split(" alt=");
//            System.out.println(Arrays.toString(temp));
            try {
                if(temp.length>0){
                    urlStrings.add(temp[0]);
                    nameStrings.add(temp[1]);
                }

            }
            catch (Exception e){
                System.out.println(Arrays.toString(temp));
            }
        }
        nameStrings = nameStrings.subList(0,50);
        urlStrings=urlStrings.subList(1,51);
        updateImage();
    }

    public void updateImage(){
        Random random = new Random();
        int diceRoll = random.nextInt(50);
        int rightOption = random.nextInt(4)+1;
        String url = urlStrings.get(diceRoll);
        correctAns = (nameStrings.get(diceRoll).split(" ")[0]+" "+nameStrings.get(diceRoll).split(" ")[1]);
        correctAns = correctAns.substring(1);
        url = url.substring(1,url.length());
        Bitmap myImage;
        List<String> options = new ArrayList<>();
        for(int i =1;i<5;i++){
            int randomOption= random.nextInt(50);
            if(i==rightOption){
                options.add(correctAns);
            }
            else if(randomOption!= diceRoll){
                options.add((nameStrings.get(randomOption).split(" ")[0]+" "+nameStrings.get(randomOption).split(" ")[1]));
            }
            else{
                i--;
            }

        }

        button1.setText((options.get(0)));
        button2.setText((options.get(1)));
        button3.setText((options.get(2)));
        button4.setText((options.get(3)));
        ImageDownloader task = new ImageDownloader();
        try{
            myImage = task.execute(url).get();
            img.setImageBitmap(myImage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    public void submitAnswer(View view) {
//        ImageDownloader task = new ImageDownloader();


        try{
            Button pressedButton = (Button) view;
            String submittedAnswer = (String) pressedButton.getText();
            System.out.println(submittedAnswer);
            if(submittedAnswer==correctAns){
                Toast.makeText(this, "Right Answer", Toast.LENGTH_LONG).show();
                winLabel.setText("Right Answer!");
            }
            else{
                winLabel.setText("Length of submission"+Integer.toString(submittedAnswer.length())+":"+Integer.toString(correctAns.length()));
                Toast.makeText(this, "Wrong Answer, Correct answer is "+ correctAns, Toast.LENGTH_LONG).show();
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        updateImage();

    }
}