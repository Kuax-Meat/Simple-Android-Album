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

import org.kuax.simalb.util.MediaStoreQuery;
import org.kuax.simalb.util.PermissionUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_PERMISSION_KEY = 1;
    private List<Uri> uris;
    private List<String> uris_string;
    private List<String> buckets;
    private List<String> bIDs;

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
        /*
        intent.putStringArrayListExtra("BucketName", (ArrayList<String>) t_bName);
        uris_string = new ArrayList<String>();
        for (int i = 0; i < t_Uris.size(); i++) {
            uris_string.add(t_Uris.get(i).toString());
        }
        intent.putStringArrayListExtra("ImageURIs", (ArrayList<String>) uris_string);
        */
        this.startActivity(intent);
        finish();
        /*
        ImageAdapter ia= new ImageAdapter(this, uris);
        GridView gv = (GridView)findViewById(R.id.gridview);
        gv.setAdapter(ia);
        */
    }

    public void query() {
        List<List<String>> tmp = MediaStoreQuery.fetchAllImages(getBaseContext());
        uris = new ArrayList<>(tmp.get(0).size());
        buckets = new ArrayList<>(tmp.get(0).size());
        bIDs = new ArrayList<>(tmp.get(0).size());

        for (int i = 0; i < tmp.get(0).size(); i++) {
            uris.add(Uri.parse(tmp.get(0).get(i)));
            buckets.add(tmp.get(1).get(i));
            bIDs.add(tmp.get(2).get(i));
        }
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
}
