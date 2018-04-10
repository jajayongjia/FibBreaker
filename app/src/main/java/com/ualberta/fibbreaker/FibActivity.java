package com.ualberta.fibbreaker;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bakerj.infinitecards.AnimationTransformer;
import com.bakerj.infinitecards.CardItem;
import com.bakerj.infinitecards.InfiniteCardView;
import com.bakerj.infinitecards.ZIndexTransformer;
import com.bakerj.infinitecards.transformer.DefaultCommonTransformer;
import com.bakerj.infinitecards.transformer.DefaultTransformerToBack;
import com.bakerj.infinitecards.transformer.DefaultTransformerToFront;
import com.bakerj.infinitecards.transformer.DefaultZIndexTransformerCommon;

import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import static android.graphics.Color.YELLOW;

public class FibActivity extends AppCompatActivity {
    public InfiniteCardView mFibView;
    public BaseAdapter mFib1, mFib2;
    public int[] resId = {R.mipmap.pic1, R.mipmap.pic2, R.mipmap.pic3, R.mipmap
            .pic4, R.mipmap.pic5};
    public String[] fibNumbers = {"0"};
    public int[] colors = {YELLOW};
    public boolean mIsFib1 = true;
    public String number1,number2,response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fib);
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
                if (mIsFib1) {
                    setStyle1();
                    mFibView.bringCardToFront(mFib1.getCount() - 1);
                } else {
                    setStyle2();
                    mFibView.bringCardToFront(mFib2.getCount() - 1);
                }
            }
        });
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mFibView.setAdapter(mFib1);
                    toGet text2 = new toGet();
                    text2.execute();
            }
        });
        findViewById(R.id.change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFibView.isAnimating()) {
                    return;
                }
                mIsFib1 = !mIsFib1;
                if (mIsFib1) {
                    setStyle2();
                    mFibView.setAdapter(mFib1);
                } else {
                    setStyle1();
                    mFibView.setAdapter(mFib2);
                }
            }
        });
    }

    private void setStyle1() {
        mFibView.setClickable(true);
        mFibView.setAnimType(InfiniteCardView.ANIM_TYPE_FRONT);
        mFibView.setAnimInterpolator(new LinearInterpolator());
        mFibView.setTransformerToFront(new DefaultTransformerToFront());
        mFibView.setTransformerToBack(new DefaultTransformerToBack());
        mFibView.setZIndexTransformerToBack(new DefaultZIndexTransformerCommon());
    }

    private void setStyle2() {
        mFibView.setClickable(true);
        mFibView.setAnimType(InfiniteCardView.ANIM_TYPE_SWITCH);
        mFibView.setAnimInterpolator(new OvershootInterpolator(-18));
        mFibView.setTransformerToFront(new DefaultTransformerToFront());
        mFibView.setTransformerToBack(new AnimationTransformer() {
            @Override
            public void transformAnimation(View view, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
                int positionCount = fromPosition - toPosition;
                float scale = (0.8f - 0.1f * fromPosition) + (0.1f * fraction * positionCount);
                view.setScaleX(scale);
                view.setScaleY(scale);
                if (fraction < 0.5) {
                    view.setRotationX(180 * fraction);
                } else {
                    view.setRotationX(180 * (1 - fraction));
                }
            }

            @Override
            public void transformInterpolatedAnimation(View view, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
                int positionCount = fromPosition - toPosition;
                float scale = (0.8f - 0.1f * fromPosition) + (0.1f * fraction * positionCount);
                view.setTranslationY(-cardHeight * (0.8f - scale) * 0.5f - cardWidth * (0.02f *
                        fromPosition - 0.02f * fraction * positionCount));
            }
        });
        mFibView.setZIndexTransformerToBack(new ZIndexTransformer() {
            @Override
            public void transformAnimation(CardItem card, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
                if (fraction < 0.4f) {
                    card.zIndex = 1f + 0.01f * fromPosition;
                } else {
                    card.zIndex = 1f + 0.01f * toPosition;
                }
            }

            @Override
            public void transformInterpolatedAnimation(CardItem card, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {

            }
        });
    }

    private void setStyle3() {
        mFibView.setClickable(false);
        mFibView.setAnimType(InfiniteCardView.ANIM_TYPE_FRONT_TO_LAST);
        mFibView.setAnimInterpolator(new OvershootInterpolator(-8));
        mFibView.setTransformerToFront(new DefaultCommonTransformer());
        mFibView.setTransformerToBack(new AnimationTransformer() {
            @Override
            public void transformAnimation(View view, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
                int positionCount = fromPosition - toPosition;
                float scale = (0.8f - 0.1f * fromPosition) + (0.1f * fraction * positionCount);
                view.setScaleX(scale);
                view.setScaleY(scale);
                if (fraction < 0.5) {
                    view.setTranslationX(cardWidth * fraction * 1.5f);
                    view.setRotationY(-45 * fraction);
                } else {
                    view.setTranslationX(cardWidth * 1.5f * (1f - fraction));
                    view.setRotationY(-45 * (1 - fraction));
                }
            }

            @Override
            public void transformInterpolatedAnimation(View view, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
                int positionCount = fromPosition - toPosition;
                float scale = (0.8f - 0.1f * fromPosition) + (0.1f * fraction * positionCount);
                view.setTranslationY(-cardHeight * (0.8f - scale) * 0.5f - cardWidth * (0.02f *
                        fromPosition - 0.02f * fraction * positionCount));
            }
        });
        mFibView.setZIndexTransformerToBack(new ZIndexTransformer() {
            @Override
            public void transformAnimation(CardItem card, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
                if (fraction < 0.5f) {
                    card.zIndex = 1f + 0.01f * fromPosition;
                } else {
                    card.zIndex = 1f + 0.01f * toPosition;
                }
            }

            @Override
            public void transformInterpolatedAnimation(CardItem card, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {

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
            convertView.setBackgroundColor(colors[0]);
//            convertView.setBackgroundResource(resIds[position]);
            return convertView;
        }
    }

    private class toGet extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... params) {
            HttpURLConnection conn = null;
            String urlLink = "http://192.168.0.15:8000/fibcal/" + number1 +"/"+number2;
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
            number1 = number2;
            number2 = response;
            fibNumbers[0] = number2;
        }
    }



}


