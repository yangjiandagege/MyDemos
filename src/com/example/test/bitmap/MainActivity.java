package com.example.test.bitmap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.DecimalFormat;

import com.example.test.R;
import com.example.test.bitmap.TouchImageView.OnTouchImageViewListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	String mDate = "2016/2/24 07:54:3";
	TouchImageView mImgSrc;

	 static Context mContext;
	 static final String FILE_NAME = "earth.jpg";
	 public static final String PICTURE_MIME = "image/*";
	 public static final int REQUEST_OPEN = 1;
	 Bitmap mBmp;
	 BitmapUtil mBmpUtil;
	 File mFile;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
        mContext = this;
        mImgSrc = (TouchImageView)findViewById(R.id.imageView);
        mImgSrc.setOnTouchImageViewListener(new OnTouchImageViewListener() {
    		@Override
    		public void onMove() {
    			
    		}
    	});
        mBmpUtil = new BitmapUtil(mContext);
        int earthId = getResources().getIdentifier("earth", "drawable", this.getPackageName());
        mImgSrc.setImageResource(earthId);
        mBmp = BitmapFactory.decodeResource(getResources(), earthId);
    }
    

    
    private void setImageBitmap(Bitmap bmp){
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		LayoutParams params = mImgSrc.getLayoutParams();
		params.width = screenWidth;
		params.height = (bmp.getHeight()*screenWidth)/bmp.getWidth();
		mImgSrc.setLayoutParams(params);
		mImgSrc.setImageBitmap(bmp);
    }
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		switch (requestCode) {
		case REQUEST_OPEN:
			if (resultCode == Activity.RESULT_OK) {
				Uri uri = intent.getData();
				String path = "";

				if (uri != null) {
					if (uri.toString().toLowerCase().startsWith("content://")) {
						path = "file://" + getRealPathFromURI(uri);
					} else {
						path = uri.toString();
					}
					URI file_uri = URI.create(path);
					if (file_uri != null) {
						File picture = new File(file_uri);
						if (picture.exists()) {
							try {
								mBmp = mBmpUtil.decode(picture.getAbsolutePath());
								setImageBitmap(mBmp);
							} catch (Exception e) {
							}
						}
					}
				}
			}
			break;
		}
	}
    
	public String getRealPathFromURI(Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(contentUri, proj, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();

		return cursor.getString(column_index).replace(" ", "%20");
	}
	
    private static void saveFile(int id, String path){
		InputStream is = mContext.getResources().openRawResource(id);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(path);
			byte[] buffer = new byte[8192];
			int count = 0;
			while ((count = is.read(buffer)) >= 0){
				fos.write(buffer, 0, count);
			}
			fos.close();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	private String getSaveDir() {
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + '/' + getString(R.string.app_name) + '/';

		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		return path;
	}
	
	public void showDisplayInfo(View view){
		DisplayMetrics dm = getResources().getDisplayMetrics();
		float density = dm.density;
		int densityDpi = dm.densityDpi;
		int widthPixels=dm.widthPixels;  
	    int heightPixels=dm.heightPixels; 
	    float xdpi = dm.xdpi;
	    float ydpi = dm.ydpi;
	    
		String info = "density : "+density+"\n";
		info += "densityDpi : "+densityDpi+"\n";
		info += "widthPixels : "+widthPixels+"\n";
		info += "heightPixels : "+heightPixels+"\n";
		info += "xdpi : "+xdpi+"\n";
		info += "ydpi : "+ydpi+"\n";
		
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Display info"); 
        builder.setIcon(R.drawable.ic_launcher);
        builder.setMessage(info); 
        builder.setNeutralButton("忽略", null);  
        builder.create().show();
	}
	
	public void selectPicture(View view){
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setDataAndType(Uri.fromFile(new File(getSaveDir())),PICTURE_MIME);
		startActivityForResult(Intent.createChooser(intent,
				getString(R.string.open_prompt_title)), REQUEST_OPEN);
	}
	
	public void compressImage(View view){
		Bitmap res = mBmpUtil.compressImage(mBmp);
		setImageBitmap(res);
	}
	
	public void convertGreyImage(View view){
		Bitmap res = mBmpUtil.convertGreyImg(mBmp);
		setImageBitmap(res);
	}
	
	public void addTimeToImage(View view){
		if(BitmapUtil.Info.DateTime == null){
			Log.e("yangjian","Date time is not eixst !");
		}else{
			Bitmap res = mBmpUtil.drawTextToBitmap(mBmp,BitmapUtil.Info.DateTime);
			setImageBitmap(res);
		}
	}

	public void adjustPhotoRotation(View view){
		Bitmap res = mBmpUtil.adjustPhotoRotation(mBmp,90);
		setImageBitmap(res);
	}
	
	public void savePhoto(View view){
        java.util.Random rand=new java.util.Random();
        int i=rand.nextInt(1000);
        Log.d("yangjian","yangjian path = "+mContext.getFilesDir().toString()+"/"+i+".jpg");
		mBmpUtil.savePhoto(mContext,mBmp,mContext.getFilesDir().toString()+"/"+i+".jpg");
	}
}
