package com.example.test.video;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.example.test.R;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoViewActivity extends Activity implements OnClickListener
{
	private VideoView videoView;

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.btnPlay:
				videoView.setVideoURI(Uri.parse("file:///sdcard/video.3gp"));
				videoView.setMediaController(new MediaController(this));
				videoView.start();
				break;

			case R.id.btnStop:
				videoView.stopPlayback();
				break;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_videoview);
		Button btnPlay = (Button) findViewById(R.id.btnPlay);
		Button btnStop = (Button) findViewById(R.id.btnStop);
		videoView = (VideoView) findViewById(R.id.videoView);
		btnPlay.setOnClickListener(this);
		btnStop.setOnClickListener(this);
		
		InputStream is = getResources().openRawResource(R.raw.video);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("/sdcard/video.3gp");
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
}