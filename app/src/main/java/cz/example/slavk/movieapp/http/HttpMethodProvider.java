package cz.example.slavk.movieapp.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Provide implementation for http methods sending
 * Created by Daniel Slavík on 12/01/2016.
 */
public class HttpMethodProvider {

    private static final String LOG_CLASS_NAME = HttpMethodProvider.class.getSimpleName();

    public static Bitmap downloadImageGET(final String urlStr) {

        return sendHttpRequestGET(urlStr, new InputStreamConverter<Bitmap>() {
            @Override
            public Bitmap convert(InputStream inputStream) {
                return BitmapFactory.decodeStream(inputStream);
            }
        });
    }

    public static String downloadJsonGET(final String urlStr) {

        return sendHttpRequestGET(urlStr, new InputStreamConverter<String>() {
            @Override
            public String convert(InputStream inputStream) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuffer buffer = new StringBuffer();
                try {
                    while ((line = reader.readLine()) != null) {
                        // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                        // But it does make debugging a *lot* easier if you print out the completed
                        // buffer for debugging.
                        buffer.append(line + "\n");
                    }
                } catch (IOException e) {
                    Log.e(LOG_CLASS_NAME, "Error reading inputStream", e);
                    return null;
                }
                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                return buffer.toString();
            }
        });
    }

    private static <T> T sendHttpRequestGET(final String urlStr, InputStreamConverter<T> converter) {

        Log.d(LOG_CLASS_NAME,"Performing request to " + urlStr);
        URL url;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            Log.e(LOG_CLASS_NAME, "Malformed url", e);
            return null;
        }

        // Will contain the raw JSON response as a string.
        String jsonString = null;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setAllowUserInteraction(false);
            urlConnection.setInstanceFollowRedirects(true);

            urlConnection.connect();
            if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.e(LOG_CLASS_NAME, "Http request for url " + urlStr + " not successful: " + urlConnection.getResponseCode());
                return null;
            }
            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            return converter.convert(inputStream);
        } catch (Exception e) {
            Log.e(LOG_CLASS_NAME, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_CLASS_NAME, "Error closing stream", e);
                }
            }
        }
    }


}
