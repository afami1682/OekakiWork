package jp.afami.android.sampleoekaki.api;

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import jp.afami.android.sampleoekaki.MainActivity;
import jp.afami.android.sampleoekaki.R;

/**
 * Created by h.takahashi on 2017/06/16.
 */

public class ApiProductIndex extends AsyncTask<String, Integer, String> {

    private TextView txtResult;
    private ProgressBar mProgressBar;

    public ApiProductIndex(Context context) {
        super();
        MainActivity activity = (MainActivity) context;
        txtResult = (TextView) activity.findViewById(R.id.txtResult);
        mProgressBar = (ProgressBar) activity.findViewById(R.id.progress);
    }

    @Override
    protected String doInBackground(String... params) {
        publishProgress(30);

        SystemClock.sleep(ApiProductConfig.REQUEST_INTERVAL);

        publishProgress(60);

        StringBuilder response = new StringBuilder();
        StringBuffer list = new StringBuffer();
        try {


            String strUrl = "";
            for (String param : params) {
                strUrl += param;
            }

            URL url = new URL(strUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "Shift-JIS"));

            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            try {
                JSONObject json = new JSONObject(response.toString());
                JSONArray products = json.getJSONArray("products");
                for (int i = 0; i < products.length(); i++) {
                    JSONObject product = products.getJSONObject(i);
                    list.append("管理ID: " + product.getString("id")).append(" ");
                    list.append("品名: " + product.getString("name")).append(" ");
                    list.append("価格: " + product.getString("price")).append("円\n");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        publishProgress(100);
        return list.toString();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressBar.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mProgressBar.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        txtResult.setText(s);
        mProgressBar.setVisibility(ProgressBar.GONE);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        mProgressBar.setVisibility(ProgressBar.GONE);
    }
}