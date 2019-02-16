package com.feed.feedo.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.trend.progress.ProgressDialog;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Scanner;

// Async Task to make HTTP request of GET/ POST TYPE
public class ApiRequest extends AsyncTask<Void, Void, String> {

    public static final short GET = 1;
    public static final short POST = 2;
    private static final String TAG = ApiRequest.class.getName();

    private int statusCode;

    private String serviceUrl;

    private short headerType;

    private OnApiRequestCompletedListener listener;
    private ProgressDialog progressBar;
    private boolean hasToShowProgress = false;

    private int reqCode;
    private String inputdata;


    public ApiRequest(String serviceUrl, Context context,
                      short headerMethodType, String inputData,
                      OnApiRequestCompletedListener listener, boolean hasToShowProgress,
                      int reqCode) {
        Log.d("serviceUrl", serviceUrl);
        this.serviceUrl = serviceUrl;

        this.reqCode = reqCode;

        this.listener = listener;
        this.headerType = headerMethodType;
        this.inputdata = inputData == null ? "" : inputData;
        this.hasToShowProgress = hasToShowProgress;

        if (hasToShowProgress)
        {
            progressBar = new ProgressDialog(context);

            // progressBar.setBarColor(context.getResources().getColor(Constant.DEFAULT_PROGRESS_COLOR));
            progressBar.setCancelable(new ProgressDialog.OnCancel()
            {
                @Override
                public void onCancelDone()
                {
                    ApiRequest.this.listener.onApiRequestComplete(0, "", ApiRequest.this.reqCode);
                    ApiRequest.this.listener = null;
                    ApiRequest.this.cancel(true);
                }
            });

            progressBar.setTranslucent();
        }
    }

    private static String getResponseText(InputStream inStream) {
        return new Scanner(inStream).useDelimiter("\\A").next();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (hasToShowProgress) {
            // progressBar.show();
            progressBar.show();
        }

    }

    @Override
    protected String doInBackground(Void... params) {
        String data = "";
        Log.d(TAG, "ServerUrl:" + serviceUrl);
        HttpURLConnection urlConnection = null;

        try {
            // create connection
            URL urlToRequest = new URL(serviceUrl);
            urlConnection = (HttpURLConnection) urlToRequest
                    .openConnection();

            switch (headerType) {
                case ApiRequest.GET:
                    urlConnection.setRequestProperty("Accept",
                            "application/json;odata=verbose");

                    break;

                default:
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);
                    Log.d("inputdata", "inputdata:" + inputdata);

                    urlConnection.setRequestProperty("Accept", "application/json;odata=verbose");
                    urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
                    urlConnection.setRequestProperty("Content-Length", Integer.toString(inputdata.getBytes().length));

                    urlConnection.setRequestProperty("Content-Type", "application/json;odata=verbose");
                    urlConnection.setRequestProperty("Auth-Token", "e14343erhucb16d0-765565c-454545-acd83-b69878tye848fcdf3");

                    DataOutputStream dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
                    dataOutputStream.writeBytes(inputdata);
                    if (dataOutputStream != null) {
                        dataOutputStream.flush();
                    }
                    if (dataOutputStream != null) {
                        dataOutputStream.close();
                    }
                    dataOutputStream = null;
                    break;
            }

            urlConnection.setConnectTimeout(30000);
            urlConnection.setReadTimeout(100000);
            // handle issues
             statusCode = urlConnection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                Log.d("URL Data ", "HTTP_UNAUTHORIZED");
            } else if (statusCode != HttpURLConnection.HTTP_OK) {
                Log.d("URL Data ", "HTTP_NOTOK " + statusCode);
            }

            // create JSON object from content
            if (statusCode == HttpURLConnection.HTTP_OK || (statusCode > 200 && statusCode < 209)) {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                data = getResponseText(in);
            } else {
                InputStream in = new BufferedInputStream(urlConnection.getErrorStream());
                data = getResponseText(in);
            }
        } catch (MalformedURLException e) {
            data = "";
            Log.e("Error ", "MalformedURLException");
        } catch (SocketTimeoutException e) {
            data = "";
            Log.e("Error ", "SocketTimeoutException");
        } catch (IOException e) {

            for (int i = 0; i < e.getStackTrace().length; i++) {

                Log.d("Error ", "IOException " + data + e.getStackTrace()[i].toString());
            }
            data = "";

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }


        }
        return data;

    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (hasToShowProgress) {
            if (progressBar != null) {
                progressBar.dismiss();
            }
        }
        if (listener != null) {
            listener.onApiRequestComplete(statusCode, result, reqCode);
        }
    }

    public interface OnApiRequestCompletedListener {
        void onApiRequestComplete(int statusCode, String result, int reqCode);
    }
}
