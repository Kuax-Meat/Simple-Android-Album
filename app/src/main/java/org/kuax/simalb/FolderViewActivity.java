package org.kuax.simalb;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class FolderViewActivity extends AppCompatActivity {
    RecyclerAdapter adapter;
    GridView grid;

    private List<String> bName;
    private List<String> uris_string;
    private List<Uri> uris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_view);

        parseIntent();
        buildContent();
    }

    private void buildContent() {
        /*
        grid = (GridView) findViewById(R.id.gv15);
        adapter = new RecyclerAdapter(this, bName, uris);
        grid.setAdapter(adapter);
        */
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(new RecyclerAdapter(getApplicationContext(), bName, uris, R.layout.activity_folder_view));
        Log.println(Log.DEBUG, "FolderViewActivity.java", "This is FolderViewActivity.java");
    }

    private void parseIntent() {
        bName = getIntent().getStringArrayListExtra("BucketName");
        uris_string = getIntent().getStringArrayListExtra("ImageURIs");
        uris = new ArrayList<Uri>();
        for (int i = 0; i < uris_string.size(); i++) {
            uris.add(i, Uri.parse(uris_string.get(i)));
        }

        for (int i = 0; i < bName.size(); i++) {
            Log.println(Log.DEBUG, "SHOW_FILES_NAME", "Folder:\"" + bName.get(i).toString() + "\", Path: \"" + uris.get(i).toString());
        }
    }
}
