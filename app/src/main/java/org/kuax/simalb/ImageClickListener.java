package org.kuax.simalb;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import org.kuax.simalb.ImageActivity;

/**
 * Created by escor on 2018-02-08.
 */

public class ImageClickListener implements View.OnClickListener {
    Context context;
    Uri imageURI;

    public ImageClickListener(Context context, Uri imageURI) {
        this.context = context;
        this.imageURI = imageURI;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra("image ID", imageURI);
        context.startActivity(intent);
    }
}
