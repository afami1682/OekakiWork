package jp.afami.android.sampleoekaki.api;

/**
 * Created by h.takahashi on 2017/06/16.
 */

public class ApiDrawingConfig {
    public static String DOMAIN = "morijyobi.sakura.ne.jp";
    // APIプロジェクト名
    public static String PROJECT_NAME = "SampleApi";
    public static String BASE_URL = "http://" + DOMAIN + "/" + PROJECT_NAME;

    public static String API_APP_KEY = "3232984d216fcdc38d2e624ca62eb65d8a3eb3e30c6b57cf31c9468202de6d54";
    public static String API_CLIENT_KEY = "bb21f929576a3a54f2e8f57e5d88fb7a2bb2b5c126ca5931808bc44db164d394";

    // API通信の間隔（連打防止）
    public static int REQUEST_INTERVAL = 500;
}
