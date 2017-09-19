package jp.afami.android.sampleoekaki;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;

import jp.afami.android.sampleoekaki.ftp.FTPManager;


public class SubActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1001;
    private FTPManager ftpManager;
    private TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        txtResult = (TextView) findViewById(R.id.txtResult);

        // 初回リスト表示
        ftpManager = new FTPManager(this);
        ftpManager.getList(txtResult);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnListSync:
                ftpManager.getList(txtResult);
                break;

            case R.id.btnSendImage:
                // ギャラリー呼び出し
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE);
                break;

            case R.id.btnGetImage:
                EditText etFileName = (EditText) findViewById(R.id.etFileName);
                String fileName = etFileName.getText().toString();
                String saveDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                ftpManager.getData(fileName, saveDir, fileName);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            // ギャラリーへのアクセス権限確認
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(this, permissions, 1);
                return;
            }

            // 画像のファイルパスを取得
            Uri uri = data.getData();
            String id = DocumentsContract.getDocumentId(data.getData());
            Cursor cursor = null;
            switch (uri.getAuthority()) {
                case "com.android.providers.media.documents":
                    //ギャラリーからの場合
                    String selection = "_id=?";
                    String[] selectionArgs = new String[]{id.split(":")[1]};
                    cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.Media.DATA}, selection, selectionArgs, null);
                    break;

                case "com.android.providers.downloads.documents":
                    // ダウンロードからの場合
                    Uri docUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                    cursor = getContentResolver().query(docUri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                    break;

                default:
                    // その他
                    break;
            }

            // ファイルパスを取得してサーバーに送信
            if (cursor.moveToFirst()) {
                final File file = new File(cursor.getString(0));
                Log.i(this.getLocalClassName(), "ファイルパス：" + file.getPath());
                ftpManager.sendData(file.getPath(), file.getName());
            }
            // リストを更新
            ftpManager.getList(txtResult);
        }
    }
}