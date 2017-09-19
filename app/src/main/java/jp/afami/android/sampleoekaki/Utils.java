package jp.afami.android.sampleoekaki;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * 各クラスで使いまわしたいメソッド
 */

public class Utils {

    // 呼び出し元
    private Context mParent;
    // プログレスバー
    private ProgressDialog mProgressDialog;

    /**
     * コンストラクタ
     *
     * @param parent
     */
    public Utils(Context parent) {
        mParent = parent;
    }

    /**
     * プログレスバーの表示
     *
     * @param title
     * @param description
     */
    public void progressShow(String title, String description) {
        mProgressDialog = new ProgressDialog(mParent);
        mProgressDialog.setTitle(title);
        mProgressDialog.setMessage(description);
        mProgressDialog.show();
    }

    /**
     * プログレスバーを閉じる
     */
    public void progressDismiss() {
        mProgressDialog.dismiss();
    }
}
