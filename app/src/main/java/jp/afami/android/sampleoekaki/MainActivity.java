package jp.afami.android.sampleoekaki;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import jp.afami.android.sampleoekaki.api.ApiProductManager;

public class MainActivity extends AppCompatActivity {

    /**
     * Created by h.takahashi on 2017/06/16.
     */

    private ApiProductManager mApiProductManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // API通信管理クラス
        mApiProductManager = new ApiProductManager(this);

        // 初回表示
        mApiProductManager.getList();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            // 検索ボタン
            case R.id.btnFind:
                EditText etFindId = (EditText) findViewById(R.id.etFindId);

                // 値の入力チェック
                if (!TextUtils.isEmpty(etFindId.getText())) {
                    // キーボード非表示
                    hideKeyboard(etFindId);
                    // データ検索
                    mApiProductManager.find(Integer.valueOf(etFindId.getText().toString()));
                    // 入力初期化
                    etFindId.setText(null);
                }
                break;

            // 追加ボタン
            case R.id.btnAdd:
                EditText etAddName = (EditText) findViewById(R.id.etAddName);
                EditText etAddPrice = (EditText) findViewById(R.id.etAddPrice);
                String name = etAddName.getText().toString();
                String price = etAddPrice.getText().toString();

                // 値の入力チェック
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(price)) {
                    // キーボード非表示
                    hideKeyboard(etAddName);
                    hideKeyboard(etAddPrice);
                    // データ追加
                    mApiProductManager.add(name, price);
                    // 入力初期化
                    etAddName.setText(null);
                    etAddPrice.setText(null);
                    // リスト更新
                    mApiProductManager.getList();
                }
                break;

            // 削除ボタン
            case R.id.btnDelete:
                EditText etDeleteId = (EditText) findViewById(R.id.etDeleteId);

                // 値の入力チェック
                if (!TextUtils.isEmpty(etDeleteId.getText())) {
                    // キーボード非表示
                    hideKeyboard(etDeleteId);
                    // データ削除
                    mApiProductManager.delete(Integer.valueOf(etDeleteId.getText().toString()));
                    // 入力初期化
                    etDeleteId.setText(null);
                    // リスト更新
                    mApiProductManager.getList();
                }
                break;

            // 更新ボタン
            case R.id.btnSync:
                mApiProductManager.getList();
                break;

            // 画像送信
            case R.id.btnSendImage:
                Intent i = new Intent(this, SubActivity.class);
                startActivity(i);
                break;
        }
    }

    // Androidキーボードを非表示にする
    private boolean hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        return true;
    }
}