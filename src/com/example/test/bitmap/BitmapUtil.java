package com.example.test.bitmap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore.Images;
import android.util.Log;

public class BitmapUtil {
	public static Options mOpts = new Options();
	public Info mInfo = new Info();
	private static Context mContext;
	private static final int TEXT_SIZE = 5;
	public static class Info{
		static String DateTime;
		static String Aperture;
		static String ExposureTime;
		static String Flash;
		static String FocalLength;
		static String ImageLength;
		static String ImageWidth;
		static String ISO;
		static String Make;
		static String Model;
		static String Orientation;
		static String WhiteBalance;
	}
	
	public BitmapUtil(Context context){
		mContext = context;
	}
	
	public Bitmap decode(String path){
		try {
			ExifInterface exifInterface =new ExifInterface(path);
			Info.DateTime = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
			Info.Aperture = exifInterface.getAttribute(ExifInterface.TAG_APERTURE);
			Info.ExposureTime = exifInterface.getAttribute(ExifInterface.TAG_EXPOSURE_TIME);
			Info.Flash = exifInterface.getAttribute(ExifInterface.TAG_FLASH);
			Info.FocalLength = exifInterface.getAttribute(ExifInterface.TAG_FOCAL_LENGTH);
			Info.ImageLength = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH);
			Info.ImageWidth = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
			Info.ISO = exifInterface.getAttribute(ExifInterface.TAG_ISO);
			Info.Make = exifInterface.getAttribute(ExifInterface.TAG_MAKE);
			Info.Model = exifInterface.getAttribute(ExifInterface.TAG_MODEL);
			Info.Orientation = exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION);
			Info.WhiteBalance = exifInterface.getAttribute(ExifInterface.TAG_WHITE_BALANCE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return BitmapFactory.decodeFile(path, mOpts);
	}
	
    public Bitmap compressImage(Bitmap image) {  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
        int options = 100;  
        while ( baos.toByteArray().length / 1024>20) {  //循环判断如果压缩后图片是否大于20kb,大于继续压缩         
            baos.reset();//重置baos即清空baos  
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中  
            options -= 10;//每次都减少10  
        } 
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中  
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片  
        return bitmap; 
    } 
    
    public Bitmap convertGreyImg(Bitmap img) {    
        int width = img.getWidth();         //获取位图的宽    
        int height = img.getHeight();       //获取位图的高    

        int []pixels = new int[width * height]; //通过位图的大小创建像素点数组    

        img.getPixels(pixels, 0, width, 0, 0, width, height);    
        int alpha = 0xFF << 24;     
        for(int i = 0; i < height; i++)  {    
            for(int j = 0; j < width; j++) {    
                int grey = pixels[width * i + j];    

                int red = ((grey  & 0x00FF0000 ) >> 16);    
                int green = ((grey & 0x0000FF00) >> 8);    
                int blue = (grey & 0x000000FF);    

                grey = (int)((float) red * 0.3 + (float)green * 0.59 + (float)blue * 0.11);    
                grey = alpha | (grey << 16) | (grey << 8) | grey;    
                pixels[width * i + j] = grey;    
            }    
        }
        Bitmap result = Bitmap.createBitmap(width, height, Config.RGB_565);    
        result.setPixels(pixels, 0, width, 0, 0, width, height);    
        return result;    
    } 
    
    public Bitmap returnBitmapFromUrl(String url) {  
        URL myFileUrl = null;  
        Bitmap bitmap = null;  
        try {  
            myFileUrl = new URL(url);  
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        }  
        try {  
            HttpURLConnection conn = (HttpURLConnection) myFileUrl .openConnection();  
            conn.setDoInput(true);  
            conn.connect();  
            InputStream is = conn.getInputStream();  
            bitmap = BitmapFactory.decodeStream(is);  
            is.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  

        return bitmap;  
    } 
    
    public Drawable resizeImage(Bitmap bitmap, int w, int h) {  
    	  
        // load the origial Bitmap  
        Bitmap BitmapOrg = bitmap;  
  
        int width = BitmapOrg.getWidth();  
        int height = BitmapOrg.getHeight();  
        int newWidth = w;  
        int newHeight = h;  
  
        // calculate the scale  
        float scaleWidth = ((float) newWidth) / width;  
        float scaleHeight = ((float) newHeight) / height;  
  
        // create a matrix for the manipulation  
        Matrix matrix = new Matrix();  
        // resize the Bitmap  
        matrix.postScale(scaleWidth, scaleHeight);  
        // if you want to rotate the Bitmap  
        // matrix.postRotate(45);  
  
        // recreate the new Bitmap  
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width, height, matrix, true);  
  
        // make a Drawable from Bitmap to allow to set the Bitmap  
        // to the ImageView, ImageButton or what ever  
        return new BitmapDrawable(resizedBitmap);
    } 
    
	public Bitmap drawTextToBitmap(Bitmap bmp, String text) {
        android.graphics.Bitmap.Config bmpConfig = bmp.getConfig();  
        int textSize = bmp.getWidth()/25;
        if(bmpConfig == null) {  
        	bmpConfig = android.graphics.Bitmap.Config.ARGB_8888;  
        }  
        Bitmap myBmp = bmp.copy(bmpConfig, true);  
        Canvas canvas = new Canvas(myBmp);  

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);  
        paint.setColor(Color.RED); 
        paint.setTextSize(textSize);
        paint.setDither(true); 
        paint.setFilterBitmap(true);
        Rect bounds = new Rect();
        paint.setTextAlign(Paint.Align.RIGHT);
        paint.getTextBounds(text, 0, text.length(), bounds); 
        canvas.drawText(text, bmp.getWidth(), bmp.getHeight(), paint);
        return myBmp;  
    }
	
	public Bitmap adjustPhotoRotation(Bitmap bmp, final int orientationDegree) {
	    Matrix m = new Matrix();
	    m.setRotate(orientationDegree, (float) bmp.getWidth() / 2, (float) bmp.getHeight() / 2);
	    try {
			Bitmap bmpTmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), m, true);
			return bmpTmp;
	    } catch (OutOfMemoryError e) {
	    	e.printStackTrace();
	    }
	    return null;
	}
	
	public void savePhoto(Context context,Bitmap bmp,String path){
        try {
            FileOutputStream out = new FileOutputStream(path);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        try{
//            if(Info.Orientation != null){
//                ExifInterface distExif = new ExifInterface(path);
//                distExif.setAttribute(ExifInterface.TAG_ORIENTATION,Info.Orientation);
//                distExif.setAttribute(ExifInterface.TAG_DATETIME,Info.DateTime);
//                distExif.setAttribute(ExifInterface.TAG_WHITE_BALANCE,Info.WhiteBalance);
//                distExif.setAttribute(ExifInterface.TAG_FLASH,Info.Flash);
//                distExif.setAttribute(ExifInterface.TAG_MAKE,Info.Make);
//                distExif.setAttribute(ExifInterface.TAG_APERTURE,Info.Aperture);
//                distExif.setAttribute(ExifInterface.TAG_FOCAL_LENGTH,Info.FocalLength);
//                distExif.setAttribute(ExifInterface.TAG_EXPOSURE_TIME,Info.ExposureTime);
//                distExif.setAttribute(ExifInterface.TAG_ISO,Info.ISO);
//                distExif.setAttribute(ExifInterface.TAG_IMAGE_LENGTH,Info.ImageLength);
//                distExif.setAttribute(ExifInterface.TAG_IMAGE_WIDTH,Info.ImageWidth);
//                distExif.setAttribute(ExifInterface.TAG_MODEL,Info.Model);
//                distExif.saveAttributes();
//            }
//        }catch(IOException e){
//            e.printStackTrace();
//        }
        File file = new File(path);
        Uri baseUri = Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(Images.Media.DATA, file.getAbsolutePath());
        values.put(Images.Media.SIZE, file.length());
        context.getContentResolver().insert(baseUri, values);
        context.getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" +file.getAbsolutePath())));
	}
}
