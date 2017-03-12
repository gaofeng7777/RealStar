package com.real.stargh;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.real.stargh.utils.AUStar;
import com.real.stargh.utils.SMGStar;
import com.real.stargh.utils.Youtu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "MainActivity";
    private ImageView iv_a;
    private ImageView iv_b;
    private ImageView loading;
    private AnimationDrawable animationDrawable;

    private String APP_ID = "10071091";
    private String SECRET_ID = "AKIDXyDG6lnb9HakEf6xnSvDJExnWzgEikhR";
    private String SECRET_KEY = "gARWTEoqncJSTIvcBYist4AILKDvRI2O";
    private Bitmap bitmap = null;

    private int tag_iv = 0;// 0 iv_a  1 iv_b
    private int beauty_a = 60;//a魅力值
    private int age_a = 0;//a年龄
    private int beauty_b = 60;//b魅力值
    private int age_b = 0;//b年龄

    private Uri imageUri;
    private Uri uri;
    private long time = 0;
    String imagePath = null;
    private Uri data_b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_a = (ImageView) findViewById(R.id.iv_a);
        findViewById(R.id.album_a).setOnClickListener(this);
        findViewById(R.id.camera_a).setOnClickListener(this);
        iv_b = (ImageView) findViewById(R.id.iv_b);
        findViewById(R.id.album_b).setOnClickListener(this);
        findViewById(R.id.camera_b).setOnClickListener(this);
        findViewById(R.id.btn).setOnClickListener(MainActivity.this);

        loading = (ImageView) findViewById(R.id.loading);
        animationDrawable = (AnimationDrawable) loading.getBackground();

        ViewGroup layout = (ViewGroup) ((Activity) this).getWindow().getDecorView().findViewById(android.R.id.content);
        String appId = AUStar.getMetaDataValue("appId", null, getApplicationContext());
        new SMGStar(MainActivity.this, appId, layout, false).start();


        if (savedInstanceState!=null) {
            String patha = (String) savedInstanceState.get("ImagePatha");

            String pathb = (String) savedInstanceState.get("ImagePathb");

            if (new File(getExternalCacheDir(), patha) != null){
                try {
                    if (Build.VERSION.SDK_INT < 24) {
                        imageUri = Uri.fromFile(new File(getExternalCacheDir(), patha));
                    }
                    File file = PhotoUtil.getFileFromMediaUri(this, imageUri);
                    Bitmap photoBmp = PhotoUtil.getBitmapFormUri(this, imageUri);//通过uri把转化成BitMap
                    int degree = PhotoUtil.getBitmapDegree(file.getAbsolutePath());//读取图片的旋转的角度
                    this.bitmap = PhotoUtil.rotateBitmapByDegree(photoBmp, degree);//把图片旋转为正的方向
                    iv_a.setImageBitmap(this.bitmap);//把图片设置在iv中
                    Log.i(TAG, "==拍照返回————压缩后a大小===" + this.bitmap.getByteCount());

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

            if (new File(getExternalCacheDir(), pathb) != null){
                try {
                    if (Build.VERSION.SDK_INT < 24) {
                        imageUri = Uri.fromFile(new File(getExternalCacheDir(), pathb));
                    }
                    File file = PhotoUtil.getFileFromMediaUri(this, imageUri);
                    Bitmap photoBmp = PhotoUtil.getBitmapFormUri(this, imageUri);//通过uri把转化成BitMap
                    int degree = PhotoUtil.getBitmapDegree(file.getAbsolutePath());//读取图片的旋转的角度
                    this.bitmap = PhotoUtil.rotateBitmapByDegree(photoBmp, degree);//把图片旋转为正的方向
                    iv_b.setImageBitmap(this.bitmap);//把图片设置在iv中

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn: {
                if (iv_a.getDrawable() != null && iv_b.getDrawable() != null) {

                    loading.setVisibility(View.VISIBLE);
                    animationDrawable.start();

                    analyzePicture();

//                    Bitmap bitmap_a = drawableToBitmap((BitmapDrawable) iv_a.getDrawable());
//                    Bitmap bitmap_b = drawableToBitmap((BitmapDrawable) iv_b.getDrawable());

                    /*
                        拍照或选择的图片转成数组，用于传递
                     */
//                    ByteArrayOutputStream output_a = new ByteArrayOutputStream();
//                    bitmap_a.compress(Bitmap.CompressFormat.JPEG, 100, output_a);
//                    byte[] bytea = output_a.toByteArray();
//
//                    ByteArrayOutputStream output_b = new ByteArrayOutputStream();
//                    bitmap_b.compress(Bitmap.CompressFormat.JPEG, 100, output_b);
//                    byte[] byteb = output_b.toByteArray();
                    /*
                    传值
                     */
//                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
//
//                    intent.putExtra("bytea", bytea);//a图片
//                    intent.putExtra("age_a", age_a);//a年龄
//                    intent.putExtra("beauty_a", beauty_a);//a的魅力值
//
//                    intent.putExtra("byteb", byteb);//b图片
//                    intent.putExtra("age_b", age_b);//b年龄
//                    intent.putExtra("beauty_b", beauty_b);//b的魅力值

//                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, R.string.selfportrait, Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case R.id.album_a:
                tag_iv = 0;
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
                break;
            case R.id.album_b:
                tag_iv = 1;
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
                break;
            case R.id.camera_a:
                openCamera("realstara.jpg", 1);//打开相机
                break;
            case R.id.camera_b:
                openCamera("realstarb.jpg", 3);//打开相机
                break;
        }

    }

    private void openCamera(String imagePath, int tag) {
        File file = new File(getExternalCacheDir(), imagePath);
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(this, "com.real.star.file", file);
        } else {
            imageUri = Uri.fromFile(file);
        }
        //启动相机
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        startActivityForResult(intent, 1);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, tag);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    try {
                        File file = PhotoUtil.getFileFromMediaUri(this, imageUri);
                        Bitmap photoBmp = PhotoUtil.getBitmapFormUri(this, imageUri);//通过uri把转化成BitMap
                        int degree = PhotoUtil.getBitmapDegree(file.getAbsolutePath());//读取图片的旋转的角度
                        this.bitmap = PhotoUtil.rotateBitmapByDegree(photoBmp, degree);//把图片旋转为正的方向
                        iv_a.setImageBitmap(this.bitmap);//把图片设置在iv中
                        Log.i(TAG, "==拍照返回————压缩后a大小===" + this.bitmap.getByteCount());

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case 2:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);//4.4以上的手机
                        setBitmap();
                    } else {
                        Log.i(TAG,"----4.4以下的手机data-----"+data.getData());
                        uri = data.getData();
                        handleImageBeforeKitKat(data);//4.4以下
                        setBitmap();

                    }
                }
                break;
            case 3:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        Log.i(TAG, "==拍照返回————————压缩前b大小===" + bitmap.getByteCount());
                        File file = PhotoUtil.getFileFromMediaUri(this, imageUri);
                        Bitmap photoBmp = PhotoUtil.getBitmapFormUri(this, imageUri);//通过uri把转化成BitMap
                        int degree = PhotoUtil.getBitmapDegree(file.getAbsolutePath());//读取图片的旋转的角度
                        bitmap = PhotoUtil.rotateBitmapByDegree(photoBmp, degree);//把图片旋转为正的方向
                        iv_b.setImageBitmap(bitmap);//把图片设置在iv中
                        Log.i(TAG, "==拍照返回————————压缩后b大小===" + bitmap.getByteCount());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }

    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitKat(Intent data) {
        uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                Uri uri1 = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(uri1, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap photoBmp = null;//通过uri把转化成BitMap
            try {
//                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//                Log.i(TAG, "==相册返回————————压缩前大小===" + bitmap.getByteCount());
                File file = PhotoUtil.getFileFromImagePath(this, imagePath);
                photoBmp = PhotoUtil.getBitmapFormUri(this, uri);
                int degree = PhotoUtil.getBitmapDegree(file.getAbsolutePath());//读取图片的旋转的角度
                this.bitmap = PhotoUtil.rotateBitmapByDegree(photoBmp, degree);//把图片旋转为正的方向
                Log.i(TAG, "==相册返回————————压缩后大小===" + this.bitmap.getByteCount());
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(this, R.string.imagefail, Toast.LENGTH_SHORT).show();
        }
    }

    public void setBitmap() {
        if (tag_iv == 0) {
            iv_a.setImageBitmap(this.bitmap);
        } else {
            iv_b.setImageBitmap(this.bitmap);
        }
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor query = getContentResolver().query(uri, null, selection, null, null);
        if (query != null) {
            if (query.moveToFirst()) {
                path = query.getString(query.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            query.close();
        }
        return path;
    }

    private void analyzePicture() {

        final Bitmap bitmap_a = drawableToBitmap((BitmapDrawable) iv_a.getDrawable());
        final Bitmap bitmap_b = drawableToBitmap((BitmapDrawable) iv_b.getDrawable());

        ByteArrayOutputStream output_a = new ByteArrayOutputStream();
        bitmap_a.compress(Bitmap.CompressFormat.JPEG, 100, output_a);
        final byte[] bytea = output_a.toByteArray();

        ByteArrayOutputStream output_b = new ByteArrayOutputStream();
        bitmap_b.compress(Bitmap.CompressFormat.JPEG, 100, output_b);
        final byte[] byteb = output_b.toByteArray();

            try {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Youtu faceYoutu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY, Youtu.API_YOUTU_END_POINT);
                            final JSONObject respose_a = faceYoutu.DetectFace(bitmap_a, 1);
                            final JSONObject respose_b = faceYoutu.DetectFace(bitmap_b, 1);
                            Log.d(TAG, respose_a.toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    String errorcode_a = respose_a.optString("errorcode");
                                    String errorcode_b = respose_b.optString("errorcode");
                                    if (errorcode_a.equals("-1101")||errorcode_b.equals("-1101")) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(MainActivity.this, R.string.facefail, Toast.LENGTH_LONG).show();
                                                animationDrawable.stop();
                                                loading.setVisibility(View.GONE);
                                            }
                                        });
                                    } else {
                                        try {
                                            JSONArray face_a = respose_a.getJSONArray("face");
                                            JSONObject jsonObject_a = face_a.getJSONObject(0);
                                            beauty_a = jsonObject_a.optInt("beauty");
                                            age_a = jsonObject_a.optInt("age");

                                            JSONArray face_b = respose_b.getJSONArray("face");
                                            JSONObject jsonObject_b = face_b.getJSONObject(0);
                                            beauty_b = jsonObject_b.optInt("beauty");
                                            age_b = jsonObject_b.optInt("age");

                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {

                                                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                                                    intent.putExtra("bytea", bytea);//a图片
                                                    intent.putExtra("age_a", age_a);//a年龄
                                                    intent.putExtra("beauty_a", beauty_a);//a的魅力值
                                                    intent.putExtra("byteb", byteb);//b图片
                                                    intent.putExtra("age_b", age_b);//b年龄
                                                    intent.putExtra("beauty_b", beauty_b);//b的魅力值

                                                    animationDrawable.stop();
                                                    loading.setVisibility(View.GONE);

                                                    startActivity(intent);
                                                }
                                            });
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } catch (Exception e) {
                Log.e("Exception", e.getMessage(), e);
            }

    }

    private Bitmap drawableToBitmap(BitmapDrawable drawable) {
        BitmapDrawable bd = drawable;
        Log.i(TAG, "---drawableToBitmap-----" + bd.getBitmap().getByteCount());
        return bd.getBitmap();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - time > 3000) {
                Toast.makeText(this, "Press again to exit the app", Toast.LENGTH_SHORT).show();
                time = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("ImagePatha", "realstara.jpg");
        outState.putString("ImagePathb", "realstarb.jpg");
    }
}

