package com.skt.zzapsinsa;

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
            //getrequest(urlStr);
            postrequest(urlStr);
        }
    }
    public void postrequest(String urlStr){

        //json 만들기
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("name", "donghyeon");
            jsonObject.put("age", "26");
        }catch(JSONException e){
            e.printStackTrace();
        }

        //System.out.println(jsonObject);
        //{"name":"donghyeon","age":"26"}

        try {
            MediaType JSON = MediaType.get("application/json; charset=utf-8");

            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("message", "my new message")
                            .build();

            Request request = new Request.Builder()
                    .url(urlStr)
                    .post(formBody)
                    .build();
            System.out.println(request);
            Response response = client.newCall(request).execute();
            response.body().string();

        }catch(Exception e){
            e.printStackTrace();
        }
        return;

        /*
        try{
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Key", "Value");

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream outputStream = new BufferedOutputStream(conn.getOutputStream());
            outputStream.write(jsonObject.toString().getBytes("utf-8"));
            //os.writeBytes(jsonObject.toString());
            outputStream.flush();
            outputStream.close();
            /*
            try(OutputStream os = conn.getOutputStream()){
                byte[] input = now_json.getBytes("utf-8");
                System.out.println(now_json);
                System.out.println("들어오나요?");
                System.out.println(input);
                os.write(input, 0, input.length);
            }*/
            /*
            try(BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))){
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while((responseLine = reader.readLine()) != null){
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }여기살려야함

             */
            /*
            int resCode = conn.getResponseCode();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line = null;
            while (true) {
                line = reader.readLine();
                if (line == null) break;

                output += line + "\n";
            }
            */
            //System.out.println(output); 정상출력. 확인.

            //reader.close();
        /*
            conn.disconnect();

        }catch(Exception e){
            e.printStackTrace();
        }
        여기살려야해*/
    }

    public void getrequest(String urlStr) {

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

            //System.out.println(output); //정상출력. 확인.

            reader.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    String bowlingJson(String player1, String player2) {
        return "{'winCondition':'HIGH_SCORE',"
                + "'name':'Bowling',"
                + "'round':4,"
                + "'lastSaved':1367702411696,"
                + "'dateStarted':1367702378785,"
                + "'players':["
                + "{'name':'" + player1 + "','history':[10,8,6,7,8],'color':-13388315,'total':39},"
                + "{'name':'" + player2 + "','history':[6,10,5,10,10],'color':-48060,'total':41}"
                + "]}";
    }
}