package com.maks.flickrapplication;

import android.os.AsyncTask;

/**
 * Created by maks on 5/1/17.
 */

enum DownloadStatus {
    IDLE, PROCESSING, NOT_INITIALIZED, FAILED_OR_EMPTY, OK
}

public class GetRawData extends AsyncTask<String, Void, String> {

    public static final String TAG = "GetRawData";

    private DownloadStatus mDownloadStatus;

    public GetRawData() {
        this.mDownloadStatus = DownloadStatus.IDLE;
    }

    @Override
    protected String doInBackground(String... params) {
        return null;
    }
}
