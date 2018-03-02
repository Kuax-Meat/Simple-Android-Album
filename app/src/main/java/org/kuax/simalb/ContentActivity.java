package org.kuax.simalb;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;

import org.kuax.simalb.util.MediaStoreQuery;

import java.util.ArrayList;
import java.util.List;

public class ContentActivity extends AppCompatActivity {
    RecyclerAdapter adapter;
    GridView grid;

    private String bID;
    private List<Uri> imgURIs;
    private List<String> uris_string;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        /* add my Toolbar */
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_menu_white);

        parseIntent();
        buildContent();
    }

    private void buildContent() {
        /* Send Query */
        List<Uri> imgUris = MediaStoreQuery.getImagesbyBID(this, bID);

        /* Build RecyclerView */
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new ContentRecyclerAdapter(getApplicationContext(), imgUris, R.layout.activity_content));
    }

    private void parseIntent() {
        bID = getIntent().getStringExtra("BucketID");
        //uris_string = getIntent().getStringArrayListExtra("ImageURIs");
    }
}
