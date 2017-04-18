package com.sayadev.finalproject.House;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sayadev.finalproject.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Rudwan on 2017-03-26.
 */

public class TempOut extends Fragment {

    private ForecastQuery forecast;

    private ProgressBar weatherProgress;

    private TextView currentTempText;
    private TextView minTempText;
    private TextView maxTempText;

    private ImageView weatherStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_temprature_outside, container, false);

        final Bundle data = this.getArguments();

        weatherProgress = (ProgressBar) view.findViewById(R.id.progressBar);

        currentTempText = (TextView) view.findViewById(R.id.currentTempText);
        minTempText = (TextView) view.findViewById(R.id.minTempText);
        maxTempText = (TextView) view.findViewById(R.id.maxTempText);

        weatherStatus = (ImageView) view.findViewById(R.id.weatherImage);

        forecast = new ForecastQuery();
        forecast.execute();

        return view;
    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {

        private String currentTemp;
        private String minTemp;
        private String maxTemp;
        private String iconName;
        private String iconFileName;
        private Bitmap weatherImage;
        private static final String OttawaWeatherURL = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";

        private boolean fileExists(String fileName){
            final File file = getActivity().getApplicationContext().getFileStreamPath(fileName);
            return file.exists();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            weatherStatus.setImageBitmap(weatherImage);

            String cText = currentTemp + "\u2103";
            String minText = minTemp + "\u2103";
            String maxText = maxTemp + "\u2103";

            currentTempText.setText(cText);
            minTempText.setText(minText);
            maxTempText.setText(maxText);

            weatherProgress.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            weatherProgress.setVisibility(View.VISIBLE);
            weatherProgress.setProgress(values[0]);

            FileInputStream fis;
            if(iconFileName != null && getActivity() != null) {
                if (fileExists(iconFileName)) {
                    Log.i("Info", iconFileName + " was found");
                    try {
                        File f = getActivity().getFileStreamPath(iconFileName);
                        fis = new FileInputStream(f);
                        weatherImage = BitmapFactory.decodeStream(fis);
                    } catch (FileNotFoundException e) {
                        Log.e("Error", "Could not find image " + iconFileName);
                    }
                }
            }
        }

        @Override
        protected String doInBackground(String... params) {
            URL url;
            XmlPullParser parser;
            int progress = 0;

            try {
                url = new URL(OttawaWeatherURL);
                HttpURLConnection conn;
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();

                parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(conn.getInputStream(), null);
                parser.nextTag();

                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    String name = parser.getName();
                    if (name.equals("temperature")) {
                        currentTemp = parser.getAttributeValue(null, "value");
                        minTemp = parser.getAttributeValue(null, "min");
                        maxTemp = parser.getAttributeValue(null, "max");
                    } else if(name.equals("weather")) {
                        iconName = parser.getAttributeValue(null, "icon");
                    }

                    progress += 0.1;
                    publishProgress(progress);
                }

            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }

            publishProgress(100);

            String imageUrl = "http://openweathermap.org/img/w/" + iconName + ".png";
            iconFileName = "image" + iconName + ".PNG";

            Bitmap image = HTTPUtils.getImage(imageUrl);
            weatherImage = image;
            FileOutputStream outputStream;
            try {
                outputStream = getActivity().openFileOutput( iconFileName, Context.MODE_PRIVATE);
                if(image != null)
                    image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);

                outputStream.flush();
                outputStream.close();
            } catch (NullPointerException | IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
