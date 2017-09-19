package jp.afami.android.sampleoekaki.api;

import android.app.Activity;

/**
 * Created by h.takahashi on 2017/06/16.
 */

public class ApiProductManager {

    public interface REQUEST_API_PRODUCT {
        String URL = ApiProductConfig.BASE_URL + "/product/";
        String INDEX = URL + "index.json";
        String FIND = URL + "find.json";
        String ADD = URL + "add.json";
        String DELETE = URL + "delete.json";
    }

    public interface PRODUCT_COLUMNS {
        String ID = "id";
        String NAME = "name";
        String PRICE = "price";
    }

    private Activity parent = null;

    // コンストラクタ
    public ApiProductManager(Activity activity) {
        parent = activity;
    }

    // リストの取得
    public void getList() {
        ApiProductIndex apiProductIndex = new ApiProductIndex(parent);
        String[] params = new String[]{
                REQUEST_API_PRODUCT.INDEX
        };
        apiProductIndex.execute(params);
    }

    // データの検索

    public void find(int id) {
        ApiProductFind apiProductFind = new ApiProductFind(parent);
        String[] params = new String[]{
                REQUEST_API_PRODUCT.FIND,
                "?",
                PRODUCT_COLUMNS.ID + "=" + id,
        };
        apiProductFind.execute(params);
    }

    // データの追加
    public void add(String name, String price) {
        ApiProductAdd apiProductAdd = new ApiProductAdd(parent);
        String[] params = new String[]{
                REQUEST_API_PRODUCT.ADD,
                "?",
                PRODUCT_COLUMNS.NAME + "=" + name,
                "&",
                PRODUCT_COLUMNS.PRICE + "=" + price
        };
        apiProductAdd.execute(params);
    }

    // データの削除
    public void delete(int id) {
        ApiProductDelete apiProductDelete = new ApiProductDelete(parent);
        String[] params = new String[]{
                REQUEST_API_PRODUCT.DELETE,
                "?",
                PRODUCT_COLUMNS.ID + "=" + id,
        };
        apiProductDelete.execute(params);
    }
}
