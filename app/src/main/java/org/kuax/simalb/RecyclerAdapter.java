package org.kuax.simalb;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import org.kuax.simalb.util.MediaStoreQuery;

import java.util.List;

/**
 * Created by escor on 2018-02-19.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    List<String> bName;
    List<String> bIDs;
    List<Uri> uris;
    Context c;
    int item_layout;

    RecyclerAdapter(Context context, List<Uri> URIs, List<String> bName, List<String> bIDs, int item_layout) {
        this.c = context;
        this.bName = bName;
        this.bIDs = bIDs;
        this.uris = URIs;
        this.item_layout = item_layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.folder_cardview_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        Bitmap bmp = BitmapFactory.decodeFile(uris.get(position).toString());
        bmp = ThumbnailUtils.extractThumbnail(bmp, 200, 200);
        int dimension = getSquareCropDimensionForBitmap(bmp);
        Drawable d = new BitmapDrawable(null, bmp);
        //Drawable drawable = Drawable.createFromPath(uris.get(position).toString());
        holder.image.setImageDrawable(d);
        //holder.image.setBackground(drawable);
        holder.title.setText(bName.get(position) + " " + MediaStoreQuery.getCount(c, bIDs.get(position)));
        ClickListener cl = new ClickListener(c, bIDs.get(position));
        holder.cardview.setOnClickListener(cl);
    }

    @Override
    public int getItemCount() {
        return this.bName.size();
    }

    public class ClickListener implements View.OnClickListener {
        Context x;
        String bID;

        public ClickListener(Context t, String tbID) {
            x = t;
            bID = tbID;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(x, ContentActivity.class);
            intent.putExtra("BucketID", bID);
            x.startActivity(intent);
        }
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

    private int getSquareCropDimensionForBitmap(Bitmap bitmap)
    {
        //use the smallest dimension of the image to crop to
        return Math.min(bitmap.getWidth(), bitmap.getHeight());
    }
}
