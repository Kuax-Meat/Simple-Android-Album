package org.kuax.simalb;

import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import org.kuax.simalb.util.MediaStoreQuery;

import java.util.ArrayList;
import java.util.List;

public class FolderViewActivity extends AppCompatActivity {
    RecyclerAdapter adapter;
    GridView grid;

    private List<String> bName;
    private List<String> bIDs;
    private List<Uri> imgURIs;
    private List<String> uris_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_view);

        /* add my Toolbar */
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_menu_white);

        //parseIntent();
        buildContent();
    }

    private void buildContent() {
        /* Send Query */
        List<List<String>> tmp = MediaStoreQuery.getFolder(this);
        bName = new ArrayList<>(tmp.get(0).size());
        bIDs = new ArrayList<>(tmp.get(0).size());
        imgURIs = new ArrayList<>(tmp.get(0).size());

        /* Remove Duplicate */
        String cur = "";
        for (int i = 0; i < tmp.get(0).size(); i++) {
            if (i == 0) {
                imgURIs.add(Uri.parse(tmp.get(0).get(i)));
                bName.add(tmp.get(1).get(i));
                bIDs.add(tmp.get(2).get(i));
                cur = bIDs.get(0);
            }
            if (cur.equals(tmp.get(2).get(i)))
                continue;
            imgURIs.add(Uri.parse(tmp.get(0).get(i)));
            bName.add(tmp.get(1).get(i));
            bIDs.add(tmp.get(2).get(i));
            cur = tmp.get(2).get(i);
        }

        /* Build RecyclerView */
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new RecyclerAdapter(getApplicationContext(), imgURIs, bName, bIDs, R.layout.activity_folder_view));
    }

    private void parseIntent() {
        bName = getIntent().getStringArrayListExtra("BucketName");
        uris_string = getIntent().getStringArrayListExtra("ImageURIs");
        imgURIs = new ArrayList<Uri>();
        for (int i = 0; i < uris_string.size(); i++) {
            imgURIs.add(i, Uri.parse(uris_string.get(i)));
        }
    }
}
