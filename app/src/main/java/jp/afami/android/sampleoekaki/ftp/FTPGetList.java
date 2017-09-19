package jp.afami.android.sampleoekaki.ftp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.TextView;

import jp.afami.android.sampleoekaki.R;


/**
 * Created by h.takahashi on 2017/06/17.
 */

public class FTPGetList extends AsyncTask<String, Integer, String> {

    private ProgressDialog mProgressDialog;
    private Context parent;
    private TextView txtResult;

    public FTPGetList(Context context, TextView _txtResult) {
        super();
        parent = context;
        txtResult = _txtResult;
        mProgressDialog = new ProgressDialog(parent);
        mProgressDialog.setTitle(parent.getResources().getString(R.string.dialog_title_ftp));
        mProgressDialog.setMessage(parent.getResources().getString(R.string.dialog_message_list_sync));
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMax(100);
    }

    @Override
    protected String doInBackground(String... params) {

        publishProgress(10);
        SystemClock.sleep(FTPConfig.REQUEST_INTERVAL);

        // サーバに接続
        FTPUtils mFtpUtil = new FTPUtils();
        mFtpUtil.connect(FTPConfig.HOST, FTPConfig.AUTH_USER.NAME, FTPConfig.AUTH_USER.PASSWORD, FTPConfig.ENCODE_TYPE);
        publishProgress(50);

        /*
         * データを取得
         * params[0]...ローカルファイルパス
         * params[1]...保存先リモートディレクトリ
         * params[2]...保存名
         */
        String ret = mFtpUtil.getList(FTPConfig.REMOTE_DIR);
        publishProgress(80);

        //接続を終了
        mFtpUtil.close();
        SystemClock.sleep(FTPConfig.REQUEST_INTERVAL);
        return ret;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog.show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mProgressDialog.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(String ret) {
        super.onPostExecute(ret);
        mProgressDialog.dismiss();
        txtResult.setText(ret);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        mProgressDialog.cancel();
    }
}
