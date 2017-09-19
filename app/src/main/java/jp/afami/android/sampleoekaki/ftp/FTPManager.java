package jp.afami.android.sampleoekaki.ftp;

import android.app.Activity;
import android.widget.TextView;

/**
 * Created by h.takahashi on 2017/06/17.
 */

public class FTPManager {

    private Activity parent = null;

    // コンストラクタ
    public FTPManager(Activity activity) {
        parent = activity;
    }

    // データ一覧取得
    public void getList(TextView txtResult) {
        FTPGetList ftPgetList = new FTPGetList(parent, txtResult);
        ftPgetList.execute();
    }

    // データの送信
    public boolean sendData(String filePath, String saveName) {
        FTPSendData ftpSendData = new FTPSendData(parent);
        String[] params = new String[]{
                filePath,
                FTPConfig.REMOTE_DIR,
                saveName
        };
        ftpSendData.execute(params);
        return true;
    }

    // データの取得
    public boolean getData(String remoteFileName, String saveDirName, String saveFileName) {
        FTPGetData ftpGetData = new FTPGetData(parent);
        String[] params = new String[]{
                FTPConfig.REMOTE_DIR,
                remoteFileName,
                saveDirName,
                saveFileName
        };
        ftpGetData.execute(params);
        return true;
    }
}
