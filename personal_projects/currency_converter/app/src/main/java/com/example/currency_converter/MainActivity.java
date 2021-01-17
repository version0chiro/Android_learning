package com.example.currency_converter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Convert(View view) {
        EditText dollar = (EditText) findViewById(R.id.dollarText);
        double rupee = Double.parseDouble(dollar.getText().toString());
        rupee = rupee*73;
        Toast.makeText(this, "Ruppe="+Double.toString(rupee), Toast.LENGTH_SHORT).show();

    }
}