package com.real.stargh.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Printscreen {

    public static String getNewDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    public static Bitmap takePrintscreen(Activity activity) {
        // View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        // 获取屏幕长和高
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay()
                .getHeight();
        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();

        String getSDCardPath = Environment.getExternalStorageDirectory().toString();
        String SavePath = getSDCardPath + "/RealStar/ScreenImages";
//保存Bitmap
        try {
            File path = new File(SavePath);
//文件
            String str = getNewDate();
            String filepath = SavePath + "/" + str + ".png";
            File file = new File(filepath);
            if (!path.exists()) {
                path.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = null;
            fos = new FileOutputStream(file);
            if (null != fos) {
                b.compress(Bitmap.CompressFormat.PNG, 60, fos);
                fos.flush();
                fos.close();
                //分享图片
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                File filea = new File(filepath);
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(filea));
                shareIntent.setType("image/jpeg");
                activity.startActivity(Intent.createChooser(shareIntent, activity.getTitle()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

}
