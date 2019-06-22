package com.carolsum.jingle.helpers;

import android.graphics.BitmapFactory;

public class ImageTypeHelper {
    public static String getMIMEType(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        return options.outMimeType;
    }
}
