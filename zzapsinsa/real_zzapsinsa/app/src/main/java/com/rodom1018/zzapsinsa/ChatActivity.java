package com.rodom1018.zzapsinsa;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatActivity extends AppCompatActivity {

    private EditText question;
    private Button ask;
    private TextView answer;

    Handler handler = new Handler(Looper.getMainLooper());

    String now_question = "";
    String now_answer = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        question = (EditText) findViewById(R.id.question);
        ask = (Button) findViewById(R.id.ask);
        answer = (TextView) findViewById(R.id.answer);

        // textView.setText("잘 기입이 되나요 ?"); <- 잘됨
        ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                now_question = question.getText().toString();
                ConnectThread thread1 = new ConnectThread();
                thread1.run();
                try {
                    thread1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                answer.setText(now_answer);
            }
        });
    }

    class ConnectThread extends Thread {
        /*
        String urlStr;

        public ConnectThread(String inStr) {
            urlStr = inStr;
        }*/

        @Override
        public void run() {
            postrequest("http://10.0.2.2:8000/zzapsinsa");
        }

        public void postrequest(String urlStr) {

            try {
                MediaType JSON = MediaType.get("application/json; charset=utf-8");

                OkHttpClient client = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder()
                        .add("question", now_question)
                        .build();

                Request request = new Request.Builder()
                        .url(urlStr)
                        .post(formBody)
                        .build();

                Response response = client.newCall(request).execute();
                System.out.println(response.body().string());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
