package org.kuax.simalb.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by escor on 2018-02-23.
 */

public class MediaStoreQuery {

    public static List<List<String>> fetchAllImages(Context c) {
        List<String> bIDs;
        List<String> bNames;
        List<String> iURIs;

        List<List<String>> result2 = new ArrayList<>(3);
        String tmp = "";

        /* Order By Bucket ID */
        String orderBy = MediaStore.Video.Media.BUCKET_ID;
        String[] projection = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_ID
        };

        Cursor imageCursor = c.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                MediaStore.Images.Media.BUCKET_ID);

        ArrayList<Uri> result = new ArrayList<>(imageCursor.getCount());
        bNames = new ArrayList<>(imageCursor.getCount());
        bIDs = new ArrayList<>(imageCursor.getCount());
        iURIs = new ArrayList<>(imageCursor.getCount());
        int dataColumnIndex = imageCursor.getColumnIndex(projection[0]);
        int bnColumnIndex = imageCursor.getColumnIndex(projection[1]);
        int bid = imageCursor.getColumnIndex(projection[2]);

        int i = -1;
        if (imageCursor == null) {

        } else if (imageCursor.moveToFirst()) {
            do {
                String filePath = imageCursor.getString(dataColumnIndex);
                String bucket = imageCursor.getString(bnColumnIndex);
                String bID = imageCursor.getString(bid);

                Uri imageUri = Uri.parse(filePath);
                iURIs.add(filePath);
                result.add(imageUri);
                bNames.add(bucket);
                bIDs.add(bID);

            } while(imageCursor.moveToNext());
        } else {

        }
        imageCursor.close();

        result2.add(iURIs);
        result2.add(bNames);
        result2.add(bIDs);
        return result2;
    }

    public static int getCount(Context c, String bID) {
        String[] projection = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.BUCKET_ID
        };

        String[] mClause = { bID };

        /* Construct Query */
        Cursor tmp = c.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                MediaStore.Images.Media.BUCKET_ID + " = ?",
                mClause,
                null
        );

        if (tmp != null)
            return tmp.getCount();
        // tmp is null
        return -1;
    }

    public static List<List<String>> getFolder(Context c) {
        String[] projection = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_ID
        };

        /* Construct Query */
        Cursor tmp = c.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                MediaStore.Images.Media.BUCKET_ID + ", " + MediaStore.Images.Media.DATE_MODIFIED + " DESC"
        );
        List<List<String>> result = new ArrayList<>(3);
        List<String> bNames = new ArrayList<>(tmp.getCount());
        List<String> bIDs = new ArrayList<>(tmp.getCount());
        List<String> iURIs = new ArrayList<>(tmp.getCount());

        int dataColumnIndex = tmp.getColumnIndex(projection[0]);
        int bnColumnIndex = tmp.getColumnIndex(projection[1]);
        int bid = tmp.getColumnIndex(projection[2]);

        if (tmp == null) {

        } else if (tmp.moveToFirst()) {
            do {
                String filePath = tmp.getString(dataColumnIndex);
                String bucket = tmp.getString(bnColumnIndex);
                String bID = tmp.getString(bid);

                iURIs.add(filePath);
                bNames.add(bucket);
                bIDs.add(bID);

            } while(tmp.moveToNext());
        } else {

        }
        tmp.close();

        result.add(iURIs);
        result.add(bNames);
        result.add(bIDs);
        return result;
    }

    public static List<Uri> getImagesbyBID(Context c, String bID) {
        //List<String> iURIs;
        String tmp = "";
        String[] mClause = { bID };

        /* Order By Bucket ID */
        String orderBy = MediaStore.Video.Media.BUCKET_ID;
        String[] projection = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.BUCKET_ID
        };

        Cursor imageCursor = c.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                MediaStore.Images.Media.BUCKET_ID + " = ?",
                mClause,
                MediaStore.Images.Media.DATE_MODIFIED + " DESC");

        ArrayList<Uri> result = new ArrayList<>(imageCursor.getCount());
        //iURIs = new ArrayList<>(imageCursor.getCount());
        int dataColumnIndex = imageCursor.getColumnIndex(projection[0]);

        int i = -1;
        if (imageCursor == null) {

        } else if (imageCursor.moveToFirst()) {
            do {
                String filePath = imageCursor.getString(dataColumnIndex);
                Uri imageUri = Uri.parse(filePath);
                //iURIs.add(filePath);
                result.add(imageUri);

            } while(imageCursor.moveToNext());
        } else {

        }
        imageCursor.close();

        return result;
    }
}
