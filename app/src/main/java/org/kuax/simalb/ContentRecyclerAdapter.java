package org.kuax.simalb;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.kuax.simalb.util.MediaStoreQuery;

import java.util.List;

/**
 * Created by escor on 2018-03-02.
 */

public class ContentRecyclerAdapter extends RecyclerView.Adapter<ContentRecyclerAdapter.ViewHolder> {
    List<Uri> uris;
    Context c;
    int item_layout;

    ContentRecyclerAdapter(Context c, List<Uri> imgUris, int layout) {
        this.c = c;
        uris = imgUris;
        item_layout = layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_cardview_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Bitmap bmp = BitmapFactory.decodeFile(uris.get(position).toString());
        bmp = ThumbnailUtils.extractThumbnail(bmp, 200, 200);
        int dimension = getSquareCropDimensionForBitmap(bmp);
        Drawable d = new BitmapDrawable(null, bmp);
        //Drawable drawable = Drawable.createFromPath(uris.get(position).toString());
        holder.image.setImageDrawable(d);
        //holder.image.setBackground(drawable);
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return this.uris.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        CardView cardview;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.folderCover);
            cardview = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

    private int getSquareCropDimensionForBitmap(Bitmap bitmap)
    {
        //use the smallest dimension of the image to crop to
        return Math.min(bitmap.getWidth(), bitmap.getHeight());
    }
}
