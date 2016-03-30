package com.example.test.camera;

import com.example.test.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class SystemCameraActivity extends Activity implements OnClickListener
{
	public ImageView imageView;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_system_camera);
		Button btnTakePicture = (Button) findViewById(R.id.btnTakePicture);
		btnTakePicture.setOnClickListener(this);
		imageView = (ImageView) findViewById(R.id.imageview);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if (requestCode == 1){
			if (resultCode == Activity.RESULT_OK){
				Bitmap cameraBitmap = (Bitmap) data.getExtras().get("data");
				imageView.setImageBitmap(cameraBitmap);				
				MediaStore.Images.Media.insertImage(getContentResolver(), cameraBitmap, null, null);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View view){
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, 1);
	}
}
