package com.ualberta.fibbreaker;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.bakerj.infinitecards.InfiniteCardView;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import static android.graphics.Color.YELLOW;

public class FibActivity extends AppCompatActivity {
    private InfiniteCardView mFibView;
    private BaseAdapter mFib1, mFib2;
    private int[] resId = {R.mipmap.back1, R.mipmap.back2, R.mipmap.back3};
    private String[] fibNumbers = {"0"};
    private int[] colors = {YELLOW};
    private String number1,number2,response,operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fib);
        getSupportActionBar().hide();
        mFibView = findViewById(R.id.view);
        number1 = getIntent().getExtras().getString("INPUT1");
        number2 = getIntent().getExtras().getString("INPUT2");
        mFib1 = new MyFib(resId, fibNumbers, colors);
        mFib2 = new MyFib(resId, fibNumbers, colors);
        mFibView.setAdapter(mFib1);
        fibNumbers[0] = String.valueOf(Integer.valueOf(number1)+Integer.valueOf(number2));
        toGet text = new toGet();
        text.execute();
        initButton();
    }

    private void initButton() {
        findViewById(R.id.pre).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operator = "/pre";
                mFibView.setAdapter(mFib1);
                toGet text2 = new toGet();
                text2.execute();

            }
        });
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    operator = null;
                    mFibView.setAdapter(mFib1);
                    toGet text2 = new toGet();
                    text2.execute();
            }
        });
    }

    private static class MyFib extends BaseAdapter {
        private int[] resIds = {};
        private String[] fibNumbers = {};
        private int[] colors = {};

        MyFib(int[] resIds, String[] fibNumbers, int[] colors) {
            this.resIds = resIds;
            this.fibNumbers = fibNumbers;
            this.colors = colors;
        }

        @Override
        public int getCount() {
            return fibNumbers.length;
        }

        @Override
        public Integer getItem(int position) {
            return colors[0];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView fibNumberView;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout
                        .item_card, parent, false);
            }

            fibNumberView = (TextView) convertView.findViewById(R.id.fibNumber);
            fibNumberView.setText(fibNumbers[0]);
//            convertView.setBackgroundColor(colors[0]);
            convertView.setBackgroundResource(resIds[1]);
            return convertView;
        }
    }

    private class toGet extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... params) {
            HttpURLConnection conn = null;
            String urlLink = null;
            if (operator == null){
                urlLink = "http://192.168.0.15:8000/fibcal/" + number1 +"/"+number2;
            }
            else{
                urlLink = "http://192.168.0.15:8000/fibcal/" + number1 +"/"+number2 +"/pre";
            }
            response = "";
            try {
                URL url = new URL(urlLink);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line = br.readLine()) != null)
                        response += line;
                } else
                    response = "";
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            if(operator !=null){
                number2 = number1;
                number1 = response;
                fibNumbers[0] = number2;

            }
            else{
                number1 = number2;
                number2 = response;
                fibNumbers[0] = response;
            }
        }
    }
}


