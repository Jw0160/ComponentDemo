package com.common.common_base.image;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Base64;
import android.view.View;

import com.bumptech.glide.Glide;
import com.common.common_base.utils.util.LogUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

/**
 * ============================================================
 * <p>
 * copyright ZENG　HUI (c) 2014
 * <p>
 * author : HUI
 * <p>
 * version : 1.0
 * <p>
 * date created : On November 24, 2014 9:14:37
 * <p>
 * description : Pictures of tools
 * <p>
 * revision history :
 * <p>
 * ============================================================
 */
@SuppressLint("NewApi")
public class ImageUtil {
    private static final String TAG = "ImageUtil";

    /**
     * 将BitMap文件转换成Base64转码的字符串，默认压缩率一百
     *
     * @param compress 压缩率
     * @param bitmap   目标Bitmap文件
     * @return
     */
    @SuppressLint("NewApi")
    public static String getBase64Str(int compress, Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        if (bitmap == null) {
            LogUtils.e(TAG, "bit map can't null");
            return null;
        }
        if (compress <= 0 || compress > 100) {
            compress = 100;
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, compress, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        byte[] buffer = out.toByteArray();
        String photoData = new String(Base64.encodeToString(buffer,
                Base64.NO_WRAP));
        return photoData;
    }

    /**
     * 将BitMap文件转换成Base64转码的字符串，默认压缩率100
     *
     * @param bitmap
     * @return
     */
    public static String getBase64Str(Bitmap bitmap) {
        return getBase64Str(100, bitmap);
    }

    /**
     * Byte[]转Bitmap
     *
     * @param data byte数组
     * @return
     */
    public static Bitmap bytes2Bitmap(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    /**
     * Bitmap转Byte[]
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 根据一个新的高度来获得一个不改变长宽比的图片
     *
     * @param bitmap    the original image
     * @param newHeight new height
     * @return
     */
    public static Bitmap getBitmapByHeight(Bitmap bitmap, int newHeight) {
        return getBitmapByLength(bitmap, newHeight, 1);
    }

    /**
     * 根据一个新的宽度来获得一个不改变长宽比的图片
     *
     * @param bitmap   the original image
     * @param newWidth
     * @return
     */
    public static Bitmap getBitmapByWidth(Bitmap bitmap, int newWidth) {
        return getBitmapByLength(bitmap, newWidth, 1);
    }

    /**
     * 根据图片的缩放比来获取Bitmap
     *
     * @param bitmap
     * @param scale
     * @return
     */
    public static Bitmap getBitmapByScale(Bitmap bitmap, float scale) {
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        float scaleWidth = 0;
        float scaleHeight = 0;
        if (scale != -1) {
            scaleWidth = scale * width;
            scaleHeight = scale * height;
        }
        return getBitmapByNewScale(bitmap, (int) scaleWidth, (int) scaleHeight, (int) width, (int) height);
    }

    /**
     * @param bitmap
     * @param length
     * @param type
     * @return
     */
    private static Bitmap getBitmapByLength(Bitmap bitmap, int length, int type) {
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        float newHeight = 0;
        float newWidth = 0;
        if (type == 1) {
            newWidth = length;
            newHeight = (height * newWidth) / width;
        } else if (type == -1) {
            newHeight = length;
            newWidth = (width * newHeight) / height;
        } else {
            return bitmap;
        }
        float scaleWidth = newHeight / height;
        float scaleHeight = newWidth / width;
        return getBitmapByNewScale(bitmap, (int) scaleWidth, (int) scaleHeight, (int) width, (int) height);
    }

    /**
     * 根据缩放的宽高来获取新的Bitmap
     *
     * @param bitmap
     * @param scaleWidth  缩放后的宽度
     * @param scaleHeight 缩放后的高度
     * @return
     */
    private static Bitmap getBitmapByNewScale(Bitmap bitmap, int scaleWidth, int scaleHeight, int width, int height) {
        Bitmap newBitmap = null;
        // create matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width,
                height, matrix, true);
        if (newBitmap != bitmap) {
            bitmap.recycle();
            bitmap = null;
        }
        return newBitmap;
    }

    /**
     * base64转为bitmap
     *
     * @param base64Data
     * @return
     */
    @SuppressLint("NewApi")
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static void saveBitmapToFile(Bitmap bitmap, File file) {
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            // 第二个参数 是压缩率，如果不压缩是100，表示压缩率为0
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * 从资源文件中获取Bitmap
     *
     * @param resources
     * @param resourceId
     * @return
     */
    public static Bitmap getBitmapByResource(Resources resources, int resourceId) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeResource(resources, resourceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * Drawable转Bitmap
     */
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        return bd.getBitmap();
    }

    /**
     *  将两个bitmap对象整合并保存为一张图片
     *
     * @param background
     * @param foreground
     * @return Bitmap
     */
    public Bitmap combineBitmap(Bitmap background, Bitmap foreground) {
        // 第一张图片的宽高
        int bgWidth = background.getWidth();
        int bgHeight = background.getHeight();
        // 第二章图片的宽高
        int fgWidth = foreground.getWidth();
        int fgHeight = foreground.getHeight();
        // 创建一个新的bitmao 高度等于两张高度的总和 用来竖列拼接
        Bitmap newmap = Bitmap.createBitmap(bgWidth, bgHeight + fgHeight,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newmap);
        // 画上第一张图片
        canvas.drawBitmap(background, 0, 0, null);
        // 从第一张图片的下边开始画入第二张图片
        canvas.drawBitmap(foreground, 0, bgHeight, null);
        return newmap;
    }

    /**
     *  Bitmap旋转一定角度
     *
     * @param b
     * @param degrees
     * @return Bitmap
     */
    public static Bitmap rotate(Bitmap b, int degrees) {
        if (degrees != 0 && b != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) b.getWidth() / 2,
                    (float) b.getHeight() / 2);
            try {
                Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(),
                        b.getHeight(), m, true);
                return b2;// 正常情况下返回旋转角度的图
            } catch (OutOfMemoryError ex) {
                return b;// 内存溢出返回原图
            } finally {
                b.recycle();// 释放资源
            }
        }
        return b;
    }

    /**
     *  将图片存储到sdcard中
     *
     * @param targetBitmap
     * @param ImageName    no need ".jpg"
     * @param path
     * @param quality      void
     */
    public static void storeImageToSDCARD(Bitmap targetBitmap,
                                          String ImageName, String path, int quality) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        File imagefile = new File(file, ImageName + ".jpg");
        if (quality < 0 || quality > 100) {
            quality = 80;
        }
        try {
            imagefile.createNewFile();
            FileOutputStream fos = new FileOutputStream(imagefile);
            targetBitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据View的大小画一个Bitmap
     *
     * @param view View
     * @return Bitmap
     */
    public static Bitmap getBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.layout(view.getLeft(), view.getTop(), view.getRight(),
                view.getBottom());
        view.draw(canvas);
        return bitmap;
    }

    /**
     *  给Bitmap 画一个圆角
     *
     * @param bitmap
     * @param roundPx
     * @return Bitmap
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        if (bitmap == null) {
            return bitmap;
        }
        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            final RectF rectF = new RectF(rect);
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
            return output;
        } catch (OutOfMemoryError e) {
            // handle exception
            System.gc();
            return null;
        }
    }

    /**
     * 获取一张有水印的Bitmap
     *
     * @param src
     * @param watermark
     * @param title
     * @return
     */
    public static Bitmap watermarkBitmap(Bitmap src, Bitmap watermark,
                                         String title) {
        if (src == null) {
            return null;
        }
        int w = src.getWidth();
        int h = src.getHeight();
        // 需要处理图片太大造成的内存超过的问题,这里我的图片很小所以不写相应代码了
        Bitmap newb = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newb);
        cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
        Paint paint = new Paint();
        // 加入图片
        if (watermark != null) {
            int ww = watermark.getWidth();
            int wh = watermark.getHeight();
            paint.setAlpha(50);
            cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, paint);// 在src的右下角画入水印
        }
        // 加入文字
        if (title != null) {
            String familyName = "宋体";
            Typeface font = Typeface.create(familyName, Typeface.BOLD);
            TextPaint textPaint = new TextPaint();
            textPaint.setColor(Color.RED);
            textPaint.setTypeface(font);
            textPaint.setTextSize(22);
            // 这里是自动换行的
            StaticLayout layout = new StaticLayout(title, textPaint, w,
                    Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
            layout.draw(cv);
            // 文字就加左上角算了
            // cv.drawText(title,0,40,paint);
        }
        cv.save(Canvas.ALL_SAVE_FLAG);// 保存
        cv.restore();// 存储
        return newb;
    }

    /**
     * 压缩目标图片，压缩率和宽高属性保持默认
     *
     * @param origiFilePath
     * @param compressFilePath
     */
    public static void compressImageFile(String origiFilePath, String compressFilePath) {
        compressImageFile(-1, -1, -1,
                origiFilePath, compressFilePath);
    }

    /**
     * 压缩目标图片，压缩率保持默认，最小边属性自定义
     *
     * @param origiFilePath    原图片URI
     * @param compressFilePath 压缩后图片URI
     */
    public static void compressImageFile(int minSideLength,
                                         String origiFilePath, String compressFilePath) {
        compressImageFile(minSideLength, minSideLength, -1,
                origiFilePath, compressFilePath);
    }

    /**
     * 压缩目标图片
     *
     * @param compressQuality  压缩质量 10~100
     * @param origiFilePath    原图片URI
     * @param compressFilePath 压缩后图片URI
     */
    public static void compressImageFile(int minSideLength,
                                         int compressQuality, String origiFilePath, String compressFilePath) {
        compressImageFile(minSideLength, minSideLength, compressQuality,
                origiFilePath, compressFilePath);
    }

    /**
     * 根据一张图片的宽高压缩目标URI,并保存到另一个URI
     *
     * @param compressWidth    目标压缩尺寸宽度
     * @param compressHeight   目标压缩尺寸高度
     * @param compressQuality  压缩质量 10~100
     * @param originFilePath   原图片URI
     * @param compressFilePath 压缩后图片URI
     */
    public static void compressImageFile(int compressWidth, int compressHeight,
                                         int compressQuality, String originFilePath, String compressFilePath) {
        int minSideLength = 0;
        int size = 0;
        int quality;
        if (compressWidth != -1 && compressHeight != -1) {
            minSideLength = compressWidth > compressHeight ? compressHeight
                    : compressWidth;
            size = compressWidth * compressHeight;
        }
        if (compressWidth == -1 && compressHeight != -1) {
            minSideLength = compressHeight;
            size = compressHeight * compressHeight;
        }
        if (compressWidth != -1 && compressHeight == -1) {
            minSideLength = compressWidth;
            size = compressWidth * compressWidth;
        }
        if (compressWidth == -1 && compressHeight == -1) {
            minSideLength = 800;
            size = 800 * 800;
        }
        if (compressQuality < 0 || compressQuality > 100) {
            quality = 80;
        } else {
            quality = compressQuality;
        }
        Bitmap bitmap = null;
        OutputStream out = null;
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(originFilePath, options);
            // Calculate inSampleSize
            options.inSampleSize = computeSampleSize(options, minSideLength,
                    size);
            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(originFilePath, options);
            File compressFile = new File(compressFilePath);
            if (!compressFile.exists()) {
                boolean result = compressFile.createNewFile();
                LogUtils.d(TAG, "Target " + compressFilePath
                        + " not exist, create a new one " + result);
            }
            out = new FileOutputStream(compressFile);
            boolean result = bitmap.compress(Bitmap.CompressFormat.JPEG,
                    quality, out);
            LogUtils.d(TAG, "Compress bitmap " + (result ? "succeed" : "failed"));
        } catch (Exception e) {
            LogUtils.e(TAG, e.toString());
        } finally {
            if (bitmap != null){
                bitmap.recycle();
            }
            try {
                if (out != null){
                    out.close();
                }
            } catch (IOException ignore) {
            }
        }
    }

    /**
     * 获取压缩的size
     *
     * @param options        属性对象
     * @param minSideLength  最小边的长度
     * @param maxNumOfPixels 最大像素数 即width * height
     * @return
     */
    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    /**
     * 获取初始化的size
     *
     * @param options        属性对象
     * @param minSideLength  最小边的长度
     * @param maxNumOfPixels 最大像素数 即width * height
     * @return
     */
    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    /**
     * Bitmap转Drawable
     */
    @SuppressWarnings("deprecation")
    public static Drawable bitmap2Drawable(Bitmap bitmap) {
        return new BitmapDrawable(bitmap);
    }

    /**
     * 为资源图片增加一个灰度
     */
    public static Drawable toGrayscale(Drawable drawable) {
        drawable.mutate();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(cm);
        drawable.setColorFilter(cf);
        return drawable;
    }

    /**
     * 得到bitmap的大小
     */
    @SuppressLint("NewApi")
    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {// API
            // 12
            return bitmap.getByteCount();
        }
        // 在低版本中用一行的字节x高度
        return bitmap.getRowBytes() * bitmap.getHeight(); // earlier version
    }

    /**
     * 根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换
     *
     * @param context
     * @param imageUri
     * @return
     */
    @TargetApi(19)
    public static String getImageAbsolutePathByUri(Activity context,
                                                   Uri imageUri) {
        if (context == null || imageUri == null){
            return null;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }
            } else if (isDownloadsDocument(imageUri)) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri))
                return imageUri.getLastPathSegment();
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }

    /**
     * 调用系统裁剪，获取头像
     *
     * @param activity    上下文
     * @param uri         目标文件URI
     * @param outPutX     输出宽度
     * @param outPutY     输出高度
     * @param requestCode 请求码
     */
    public static void startRandomPhotoZoom(Activity activity, Uri uri, int outPutX, int outPutY,
                                            int requestCode) {
        if (uri == null) {
            return;
        }
        // Call the Android system comes with a picture clipping page
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // set crop is true
        intent.putExtra("crop", "true");
        // outputX outputY (Is wide high cut images)
        if (outPutX > 0 && outPutY > 0) {
            intent.putExtra("outputX", outPutX);
            intent.putExtra("outputY", outPutY);
        } else {
            intent.putExtra("outputX", 180);
            intent.putExtra("outputY", 180);
        }
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * Cut out pictures call system of cutting function
     * 调用系统裁剪，采用Uri形式
     *
     * @param activity    上下文
     * @param uri         目标文件URI
     * @param requestCode 请求码
     */
    public static void startPhotoZoom(Activity activity, Uri uri,
                                      int requestCode) {
        startPhotoZoom(activity, uri, requestCode, -1, -1, -1, -1);
    }

    /**
     * Cut out pictures call system of cutting function
     * 调用系统裁剪，采用Uri形式
     *
     * @param activity    上下文
     * @param uri         目标文件URI
     * @param aspectX     输出宽度比例
     * @param aspectY     输出高度比例
     * @param requestCode 请求码
     */
    public static void startPhotoZoom(Activity activity, Uri uri,
                                      int requestCode, int aspectX, int aspectY) {
        startPhotoZoom(activity, uri, requestCode, aspectX, aspectY, -1, -1);
    }

    /**
     * Cut out pictures call system of cutting function
     * 调用系统裁剪，采用Uri形式
     *
     * @param activity    上下文
     * @param uri         目标文件URI
     * @param aspectX     输出宽度比例
     * @param aspectY     输出高度比例
     * @param requestCode 请求码
     * @param outPutX     输出宽度
     * @param outPutY     输出高度
     */
    public static void startPhotoZoom(Activity activity, Uri uri,
                                      int requestCode, int aspectX, int aspectY, int outPutX, int outPutY) {
        // Call the Android system comes with a picture clipping page
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (uri == null) {
            return;
        }
        intent.setDataAndType(uri, "image/*");
        // set crop is true
        intent.putExtra("crop", "true");
        // aspectX aspectY (Is wide high proportion)
        if (aspectX >= 0 && aspectY >= 0) {
            intent.putExtra("aspectX", aspectX);
            intent.putExtra("aspectY", aspectY);
        }
        // outputX outputY (Is wide high cut images)
        if (outPutX >= 0 && outPutY >= 0) {
            intent.putExtra("scale", true);
            intent.putExtra("outputX", outPutX);
            intent.putExtra("outputY", outPutY);
        } else {
            intent.putExtra("outputX", 480);
            intent.putExtra("outputY", 480);
        }
        //修该参数设置
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 根据图片url获取图片对象
     *
     * @param context
     * @param url
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getBitmapFromUrl(Context context, String url, int width, int height) {
        Bitmap myBitmap = null;
        try {
            myBitmap = Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .centerCrop()
                    .into(width, height)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return myBitmap;
    }

    /**
     * @param urlpath
     * @return Bitmap
     * 根据图片url获取图片对象
     */
    public static Bitmap getBitmapFromUrl(String urlpath) {
        Bitmap map = null;
        try {
            URL url = new URL(urlpath);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream in;
            in = conn.getInputStream();
            map = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 将bitmap处理为圆形
     *
     * @author Stanny
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        Bitmap output = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    /**
     * 返回一个透明的Bitmap
     *
     * @param sourceImg
     * @param number    透明度 范围是0-100，0表示完全透明即完全看不到。
     * @return
     */
    public static Bitmap getTransparentBitmap(Bitmap sourceImg, int number) {
        int[] argb = new int[sourceImg.getWidth() * sourceImg.getHeight()];
        sourceImg.getPixels(argb, 0, sourceImg.getWidth(), 0, 0, sourceImg
                .getWidth(), sourceImg.getHeight());// 获得图片的ARGB值
        number = number * 255 / 100;
        for (int i = 0; i < argb.length; i++) {
            argb[i] = (number << 24) | (argb[i] & 0x00FFFFFF);
        }
        sourceImg = Bitmap.createBitmap(argb, sourceImg.getWidth(), sourceImg
                .getHeight(), Bitmap.Config.ARGB_8888);
        return sourceImg;
    }
}
