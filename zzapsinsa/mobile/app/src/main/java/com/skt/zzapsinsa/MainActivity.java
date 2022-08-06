package com.skt.zzapsinsa;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.BreakIterator;

public class MainActivity extends AppCompatActivity {
    private Button btnCall;
    private TextView textView;
    private EditText input01;

    Handler handler = new Handler(Looper.getMainLooper());

    String output = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btnCall = (Button) findViewById(R.id.requestBtn);
        textView = (TextView) findViewById(R.id.textMsg);
        input01 = (EditText) findViewById(R.id.input01);

        // textView.setText("잘 기입이 되나요 ?"); <- 잘됨
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String now_url = input01.getText().toString();
                ConnectThread thread1 = new ConnectThread(now_url);
                thread1.run();
                try {
                    thread1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                textView.setText(output);
            }
        });
    }


    class ConnectThread extends Thread{
        String urlStr;

        public ConnectThread(String inStr){
            urlStr = inStr;
        }

        @Override
        public void run() {
            request(urlStr);
        }
    }

    public void request(String urlStr) {

        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            int resCode = conn.getResponseCode();
            //System.out.println(resCode);200
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = null;
            while (true) {
                line = reader.readLine();
                if (line == null) break;

                output += line + "\n";
            }

            //System.out.println(output); 정상출력. 확인.

            reader.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}