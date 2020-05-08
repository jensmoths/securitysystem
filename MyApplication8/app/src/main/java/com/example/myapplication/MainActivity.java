package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ClientHandler cl = new ClientHandler(this);
    //TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cl.start();
    }

    public void connect(View view) {
        // Do something in response to button
        System.out.println("pressed");
        EditText u = (EditText) findViewById(R.id.usernameText);
        EditText p = (EditText) findViewById(R.id.passwordText);
        cl.send(u.getText().toString(), p.getText().toString());
    }

    public void showMessage(final Object objectRead) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
               TextView tv = findViewById(R.id.textView);
                tv.setText((String) objectRead);
            }
        });
    }
}

