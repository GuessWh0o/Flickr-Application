package com.maks.flickrapplication;

import android.os.AsyncTask;
import android.util.Log;
import android.util.StringBuilderPrinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by GuessWh0o on 06.01.2017.
 * Email: developerint97@gmail.com
 */

enum DownloadStatus {
    IDLE, PROCESSING, NOT_INITIALIZED, FAILED_OR_EMPTY, OK
}

public class GetRawData extends AsyncTask<String, Void, String> {

    public static final String TAG = "GetRawData";

    private DownloadStatus mDownloadStatus;

    private final IOnDownloadComplete mCallback;

    interface IOnDownloadComplete {
        void onDownloadComplete(String data, DownloadStatus status);
    }

    public GetRawData(IOnDownloadComplete callback) {
        this.mDownloadStatus = DownloadStatus.IDLE;
        mCallback = callback;
    }


    void runInSameThread(String s) {
        Log.d(TAG, "runInSameThread: STARTS");

       // onPostExecute(doInBackground(s));
        if(mCallback != null) {
            mCallback.onDownloadComplete(doInBackground(s), mDownloadStatus);
        }

        Log.d(TAG, "runInSameThread: ENDS");
    }

    @Override
    protected void onPostExecute(String s) {
        if (mCallback != null) {
            mCallback.onDownloadComplete(s, mDownloadStatus);
        } else {
            Log.d(TAG, "onPostExecute: ends");
        }
    }

    @Override
    protected String doInBackground(String... strings) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        if (strings == null) {
            mDownloadStatus = DownloadStatus.NOT_INITIALIZED;
            return null;
        }

        try {
            mDownloadStatus = DownloadStatus.PROCESSING;
            URL url = new URL(strings[0]);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int response = connection.getResponseCode();
            Log.d(TAG, "doInBackground: The response code is: " + response);

            StringBuilder result = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

//            String line;
//            while(null != (line = reader.readLine())){
//                result.append(line).append("\n");
//            }

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                result.append(line).append("\n");
            }

            mDownloadStatus = DownloadStatus.OK;
            return result.toString();
        } catch (MalformedURLException ex) {
            Log.e(TAG, "doInBackground: Invalid URL" + ex.getMessage());
        } catch (IOException ex) {
            Log.e(TAG, "doInBackground: IO Exception" + ex.getMessage());
        } catch (SecurityException ex) {
            Log.e(TAG, "doInBackground: Security Exception" + ex.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;

        return null;
    }

}
