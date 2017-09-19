package jp.afami.android.sampleoekaki.api;

/**
 * Created by h.takahashi on 2017/06/16.
 */

public class ApiProductConfig {
    public static String DOMAIN = "morijyobi.sakura.ne.jp";
    // APIプロジェクト名
    public static String PROJECT_NAME = "SampleApi";
    public static String BASE_URL = "http://" + DOMAIN + "/" + PROJECT_NAME;

    // API通信の間隔（連打防止）
    public static int REQUEST_INTERVAL = 500;
}
