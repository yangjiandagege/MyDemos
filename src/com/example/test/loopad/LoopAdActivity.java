package com.example.test.loopad;

import java.util.ArrayList;
import java.util.List;

import com.example.test.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class LoopAdActivity extends Activity {
	private ViewPager viewPager;
	private List<Ad> adlist = new ArrayList<Ad>();
	private TextView tvText;
	private LinearLayout linearDot;
	private boolean isBack;
	
	private Handler loopHandler = new Handler() {
		public void handleMessage(Message msg) {
			viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
			loopHandler.sendEmptyMessageDelayed(0, 3000);
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initListener();
		initData();
	}

	private void initView() {
		setContentView(R.layout.activity_loop_ad);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		tvText = (TextView) findViewById(R.id.tvTest);
		linearDot = (LinearLayout) findViewById(R.id.dot);
	}

	private void initListener() {
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				updateTextAndDot();
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,int positionOffsetPixels) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub
			}
		});
	}

	private void initData() {
		// add data
		adlist.add(new Ad(R.drawable.a, "有家公寓"));
		adlist.add(new Ad(R.drawable.b, "猪八戒网"));
		adlist.add(new Ad(R.drawable.c, "喵喵论坛"));
		initDots();

		viewPager.setAdapter(new MyViewPager());
		int centerValue = Integer.MAX_VALUE / 2;
		int value = centerValue % adlist.size();
		viewPager.setCurrentItem(centerValue - value);

		updateTextAndDot();

		loopHandler.sendEmptyMessageDelayed(0, 3000);
	}

	private void updateTextAndDot() {
		int currentPage = viewPager.getCurrentItem() % adlist.size();
		tvText.setText(adlist.get(currentPage).getText());

		for (int i = 0; i < linearDot.getChildCount(); i++) {
			linearDot.getChildAt(i).setEnabled(i == currentPage);
		}
	}

	private void initDots() {
		for (int i = 0; i < adlist.size(); i++) {
			View view=new View(this);
			LayoutParams params=new LayoutParams(8,8);
			if(i!=0){
				params.leftMargin=5;
			}
			
			view.setLayoutParams(params);
			view.setBackgroundResource(R.drawable.selecter_dot);
			linearDot.addView(view);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	class MyViewPager extends PagerAdapter {

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			Ad adImage = adlist.get(position % adlist.size());
			View view = View.inflate(LoopAdActivity.this, R.xml.adapter, null);
			ImageView image = (ImageView) view.findViewById(R.id.image);
			image.setImageResource(adImage.getImageId());
			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// super.destroyItem(container, position, object);
			container.removeView((View) object);
		}
	}
}
