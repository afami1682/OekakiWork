package jp.afami.android.sampleoekaki.ftp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.Toast;

import jp.afami.android.sampleoekaki.R;

/**
 * Created by h.takahashi on 2017/06/17.
 */

public class FTPGetData extends AsyncTask<String, Integer, Boolean> {

    private ProgressDialog mProgressDialog;
    private Context parent;

    public FTPGetData(Context context) {
        super();
        parent = context;
        mProgressDialog = new ProgressDialog(parent);
        mProgressDialog.setTitle(parent.getResources().getString(R.string.dialog_title_ftp));
        mProgressDialog.setMessage(parent.getResources().getString(R.string.dialog_message_get));
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMax(100);
    }

    @Override
    protected Boolean doInBackground(String... params) {

        publishProgress(10);
        SystemClock.sleep(FTPConfig.REQUEST_INTERVAL);

        // サーバに接続
        FTPUtils mFtpUtil = new FTPUtils();
        mFtpUtil.connect(FTPConfig.HOST, FTPConfig.AUTH_USER.NAME, FTPConfig.AUTH_USER.PASSWORD, FTPConfig.ENCODE_TYPE);
        publishProgress(50);

        /*
         * データを送信
         * params[0]...FTPのディレクトリ名
         * params[1]...FTPのファイル名
         * params[2]...保存先ディレクトリ名
         * params[2]...保存先ファイル名
         */
        boolean ret = mFtpUtil.get(params[0], params[1], params[2], params[3]);
        publishProgress(80);

        // 接続を終了
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
    protected void onPostExecute(Boolean ret) {
        super.onPostExecute(ret);
        mProgressDialog.dismiss();
        if (ret) {
            Toast.makeText(parent, parent.getResources().getString(R.string.toast_download_ok), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(parent, parent.getResources().getString(R.string.toast_download_no), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        mProgressDialog.cancel();
    }
}
