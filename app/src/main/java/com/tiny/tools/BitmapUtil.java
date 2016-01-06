package com.tiny.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by ${Tiny} on 2016/1/3.
 */
public class BitmapUtil {

    /**
     * @param imgName
     * @return
     */
    public static Bitmap getBitmapFromPath(String imgName) {
        String sExtendSdcardDir = "";
        String sAppRoot = "";

        String sdcardRoot = Environment.getExternalStorageDirectory() == null ? "" : Environment.getExternalStorageDirectory().getAbsolutePath();
        Log.d("Tiny", "sdcardRoot --" + sdcardRoot);

        if (sdcardRoot != null) {
            sExtendSdcardDir = sdcardRoot;
            sAppRoot = sExtendSdcardDir.concat("/TINY");
        }

        String sCache = sAppRoot.concat("/cache");

        File file = new File(sCache);
        if (!file.exists()) {
            file.mkdirs();
        }
        Log.d("Tiny", "file path --" + file.getAbsolutePath());

        File image = new File(file.getAbsolutePath(), imgName);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
        return bitmap;
    }
}
