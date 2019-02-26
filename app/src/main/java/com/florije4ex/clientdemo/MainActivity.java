package com.florije4ex.clientdemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickOps(View view) {
        Log.v("10086", "In clickOps function");
        MyTask myTask = new MyTask();
        myTask.execute();
    }

    private class MyTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("http://10.0.2.2:8080/ping").build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String content= null;
            try {
                assert response != null;
                assert response.body() != null;
                content = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.v("10086", s);
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            Gson gson = new Gson();
            PingPong pp = gson.fromJson(s, PingPong.class);
            Log.v("10086", pp.message);
        }
    }
}
