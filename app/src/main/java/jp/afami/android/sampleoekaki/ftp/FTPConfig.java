package jp.afami.android.sampleoekaki.ftp;

/**
 * Created by h.takahashi on 2017/06/17.
 */

public class FTPConfig {
    public static String HOST = "morijyobi.sakura.ne.jp";
    public static String REMOTE_DIR = "/home/morijyobi/www/upload";
    public static String ENCODE_TYPE = "UTF-8";

    interface AUTH_USER {
        String NAME = "morijyobi";
        String PASSWORD = "xkz2hruuzp";
    }

    // FTP通信の間隔（連打防止）
    public static int REQUEST_INTERVAL = 500;
}
