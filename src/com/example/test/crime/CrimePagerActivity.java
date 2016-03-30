package com.example.test.crime;

import java.util.ArrayList;
import java.util.UUID;

import com.example.test.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class CrimePagerActivity extends FragmentActivity{
	private ViewPager mViewPager;
	private ArrayList<Crime> mCrimes;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);
		
		mCrimes = CrimeLab.get(this).getCrimes();
		FragmentManager fm = getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
			public int getCount(){
				return mCrimes.size();
			}
			public Fragment getItem(int pos) {
				Crime crime = mCrimes.get(pos);
				return CrimeFragment.newInstance(crime.getId());
			}
		});
		UUID crimeId = (UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
		for(int i = 0; i < mCrimes.size();i++){
			if (mCrimes.get(i).getId().equals(crimeId)){
				mViewPager.setCurrentItem(i);
				setTitle(mCrimes.get(i).getTitle());
				break;
			}
		}

		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {

			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}
			
			@Override
			public void onPageScrollStateChanged(int pos) {
				Log.d("yangjian","yangjian pos = "+pos);
				
				Crime crime = mCrimes.get(mViewPager.getCurrentItem());
				if (crime.getTitle() != null){
					setTitle(crime.getTitle());
				}
			}
		});
	}
	
}
