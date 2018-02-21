package org.kuax.simalb;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by escor on 2018-02-19.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    List<String> bName;
    List<Uri> uris;
    Context c;
    int item_layout;

    RecyclerAdapter(Context context, List<String> bName, List<Uri> URIs, int item_layout) {
        this.c = context;
        this.bName = bName;
        this.uris = URIs;
        this.item_layout = item_layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.folder_cardview_item, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        //Bitmap bmp = BitmapFactory.decodeFile(uris.get(position).toString());
        //int dimension = getSquareCropDimensionForBitmap(bmp);
        //bmp = ThumbnailUtils.extractThumbnail(bmp, dimension, dimension);
        Drawable drawable = Drawable.createFromPath(uris.get(position).toString());
        //Drawable d = new BitmapDrawable(null, bmp);
        holder.image.setBackground(drawable);
        holder.title.setText(bName.get(position));
    }

    @Override
    public int getItemCount() {
        return this.bName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        CardView cardview;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.folderCover);
            title = (TextView) itemView.findViewById(R.id.nameTxt);
            cardview = (CardView) itemView.findViewById(R.id.card_view);
        }
    }


    public int getSquareCropDimensionForBitmap(Bitmap bitmap)
    {
        //use the smallest dimension of the image to crop to
        return Math.min(bitmap.getWidth(), bitmap.getHeight());
    }
}
