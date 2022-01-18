package com.devartlab.ui.main.ui.devartlink.letsTalk.filesUpload;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Serializable;


public class VolleyFileObj implements Serializable {
    private String filePath = "", paramName;
    private int fileType;
    private Uri uri;
    private File file;

    //RequestsCodes
    public static int FILE_TYPE_IMAGE = 1001;
    public static int FILE_TYPE_PDF = 1002;


    public VolleyFileObj(String paramName , String filePath, int fileType) {
        this.paramName=paramName;
        this.filePath=filePath;
        this.fileType=fileType;
        this.file = new File(filePath);
        compressImage();
    }

    public Bitmap getResizedBitmap(File file) {
        int maxSize = 600;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),bmOptions);

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }
    public void compressImage() {
        if(fileType == FILE_TYPE_IMAGE) {
            File imageFile = new File(getFilePath());
            Bitmap bitmap = getResizedBitmap(imageFile);


            setBitmap(bitmap);
            OutputStream os;
            try {
                os = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                os.flush();
                os.close();
            } catch (Exception e) {
                Log.e("err_compress_image", e.getMessage());
            }
        }
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public int getFileType() {
        return fileType;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }




    private Bitmap bitmap;
    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Uri getUri() {
        return uri;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }
}

