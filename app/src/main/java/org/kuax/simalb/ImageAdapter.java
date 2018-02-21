package org.kuax.simalb;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by escor on 2018-02-07.
 */

public class ImageAdapter extends BaseAdapter {
    Context context;
    List<Uri> imgList;

    ImageAdapter(Context context, List<Uri> uris) {
        this.context = context;
        imgList = uris;
    }

    @Override
    public int getCount() {
        return imgList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView iv = null;

        Bitmap bmp = null;
        if (convertView == null) {
            bmp = BitmapFactory.decodeFile(imgList.get(position).toString());
            int dimension = getSquareCropDimensionForBitmap(bmp);
            bmp = ThumbnailUtils.extractThumbnail(bmp, dimension, dimension);

            //bmp = Bitmap.createScaledBitmap(bmp, 300, 300, C);

            /* Init ImageView */
            iv = new ImageView(context);
            iv.setAdjustViewBounds(true);
            iv.setImageBitmap(bmp);
            //iv.setClickable(true);
            //iv.setLayoutParams(new GridView.LayoutParams(400,400));
            //iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //iv.setPadding(0,0,0,0);

            /* add ClickListener */
            ImageClickListener imageViewClickListener = new ImageClickListener(context, imgList.get(position));
            iv.setOnClickListener(imageViewClickListener);
        } else {
            iv = (ImageView) convertView;
        }

        /* Show Image via Bitmap */
        //iv.setImageBitmap(bmp);
        //iv.setImageURI(Uri.parse(imgList.get(position).toString()));
        return iv;
    }

    public int getSquareCropDimensionForBitmap(Bitmap bitmap)
    {
        //use the smallest dimension of the image to crop to
        return Math.min(bitmap.getWidth(), bitmap.getHeight());
    }
}
