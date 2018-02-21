package org.kuax.simalb;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        ImageView iv = (ImageView)findViewById(R.id.imageView);
        setImage(iv);
    }

    private void setImage(ImageView iv) {
        Intent receivedIntent = getIntent();

        Uri imageURI = (Uri)receivedIntent.getExtras().get("image ID");
        iv.setImageURI(imageURI);
    }
}
