package org.kuax.simalb;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import org.kuax.simalb.util.PermissionUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_PERMISSION_KEY = 1;
    private List<Uri> uris;
    private List<String> uris_string;
    private List<String> buckets;
    private List<String> bIDs;
    //private int[] count = new int[50];
    private int EXTERNAL_STORAGE_PERMISSIONS = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        /* Check Permissions */
        if (PermissionUtil.isStoragePermissionsGranted(this)) {
            initialize();
        } else {
            PermissionUtil.requestPermissions(this, EXTERNAL_STORAGE_PERMISSIONS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    private List<Uri> fetchAllImages() {
        String tmp = "";
        /* Order By Bucket ID */
        String orderBy = MediaStore.Video.Media.BUCKET_ID;
        String[] projection = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_ID
        };

        Cursor imageCursor = getBaseContext().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                MediaStore.Images.Media.BUCKET_ID);

        ArrayList<Uri> result = new ArrayList<>(imageCursor.getCount());
        buckets = new ArrayList<>(imageCursor.getCount());
        bIDs = new ArrayList<>(imageCursor.getCount());
        int dataColumnIndex = imageCursor.getColumnIndex(projection[0]);
        int bnColumnIndex = imageCursor.getColumnIndex(projection[1]);
        int bid = imageCursor.getColumnIndex(projection[2]);
        //Log.println(Log.DEBUG, "HELLO", projection[1]);

        int i = -1;
        if (imageCursor == null) {
            // Error 발생
            // 적절하게 handling 해주세요
        } else if (imageCursor.moveToFirst()) {
            do {
                String filePath = imageCursor.getString(dataColumnIndex);
                String bucket = imageCursor.getString(bnColumnIndex);
                String bID = imageCursor.getString(bid);
                if (!tmp.equals(bID)) {
                    tmp = bID;
                    i++;
                }
                /*
                count[i]++;
                */
                //Log.println(Log.DEBUG, "HELLO", imageCursor.getString(bnColumnIndex));
                Uri imageUri = Uri.parse(filePath);
                result.add(imageUri);
                buckets.add(bucket);
                bIDs.add(bID);

            } while(imageCursor.moveToNext());
        } else {
            // imageCursor가 비었습니다.
        }
        imageCursor.close();

        return result;
    }

    public void initialize() {
        query();
        /*
        for (int i = 0; i < buckets.size(); i++) {
            Log.println(Log.DEBUG, "SHOW_FILES_NAME", "Bucket ID: \"" + bIDs.get(i).toString() +"\", Folder:\"" + buckets.get(i).toString() + "\", Path: \"" + uris.get(i).toString()); //+ "\"" + ", Photo Count : " + count[0] + " " + count[1] + " " + count[2]);
        }*/

        List<String> t_bName = new ArrayList<String>();
        List<Uri> t_Uris = new ArrayList<Uri>();

        String temp = new String();

        for (int x = 0; x < buckets.size(); x++) {
            if (x == 0) {
                temp = buckets.get(x);
                t_bName.add(buckets.get(x));
                t_Uris.add(uris.get(x));
            }

            if (temp.equals(buckets.get(x)))
                continue;
            else {
                temp = buckets.get(x);
                t_bName.add(buckets.get(x));
                t_Uris.add(uris.get(x));
            }
        }

        Intent intent = new Intent(this, FolderViewActivity.class);
        intent.putStringArrayListExtra("BucketName", (ArrayList<String>) t_bName);
        uris_string = new ArrayList<String>();
        for (int i = 0; i < t_Uris.size(); i++) {
            uris_string.add(t_Uris.get(i).toString());
        }
        intent.putStringArrayListExtra("ImageURIs", (ArrayList<String>) uris_string);
        this.startActivity(intent);
        finish();
        /*
        ImageAdapter ia= new ImageAdapter(this, uris);
        GridView gv = (GridView)findViewById(R.id.gridview);
        gv.setAdapter(ia);
        */
    }

    public void query() {
        uris = fetchAllImages();
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 12:
                boolean gotPermission = grantResults.length > 0;

                for (int result : grantResults) {
                    gotPermission &= result == PackageManager.PERMISSION_GRANTED;
                }

                if (gotPermission) {
                    initialize();
                } else {
                    Toast.makeText(this, "Permission was denied!", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /* Deprecated
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    final List<Uri> uris = fetchAllImages();
                    Log.println(Log.DEBUG, "hello", uris.get(0).toString());

                    //ImageView imgView = (ImageView) findViewById(R.id.imageView3);
                    //imgView.setImageURI(Uri.parse(uris.get(0).toString()));

                    ImageAdapter ia= new ImageAdapter(this, uris);
                    GridView gv = (GridView)findViewById(R.id.gridview);
                    gv.setAdapter(ia);
                } else {

                }
                return;
            }
        }
    } */
}
