package com.example.test.location;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import com.example.test.R;
import android.app.Activity; 
import android.content.Context; 
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location; 
import android.location.LocationListener; 
import android.location.LocationManager; 
import android.os.Bundle; 
import android.util.Log; 
import android.widget.TextView; 
 
public class LocationActivity extends Activity { 
 
    private final String TAG = "GpsExample"; 
     
    private TextView        mGpsInfo; 
    private int mNumOfSatellites;
    private String mStrNmea;
    private long mTime;
    LocationManager mGpsManager = null;
    Location        mLocation = null;
    @Override 
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_location); 
 
        mGpsInfo      = (TextView)this.findViewById(R.id.TextView_GpsInfo); 
        mGpsManager  = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE); 
        mLocation    = mGpsManager.getLastKnownLocation(LocationManager.GPS_PROVIDER); 
 
        printGpsLocation(); 
        if( mLocation == null ) {  
            mGpsInfo.setText("暂无GPS有效信息"); 
        } 
        mGpsManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, new GpsLocationListener()); 
        
        mGpsManager.addGpsStatusListener(statusListener);
        mGpsManager.addNmeaListener(nmeaListener);
    } 
   
    private final GpsStatus.Listener statusListener = new GpsStatus.Listener() {
        public void onGpsStatusChanged(int event) {
            Log.w("TestGps", "now gps status changed" + event);
            
            GpsStatus status = mGpsManager.getGpsStatus(null);
            if (status == null) {
            	mNumOfSatellites = 0;
            } else if (event == GpsStatus.GPS_EVENT_SATELLITE_STATUS) {
                    int maxSatellites = status.getMaxSatellites();
                    Iterator<GpsSatellite> it = status.getSatellites().iterator();
                    int count = 0;
                    while (it.hasNext() && count <= maxSatellites) {
                            GpsSatellite s = it.next();
                            count++;
                    }
                    mNumOfSatellites = count;
            }
            printGpsLocation();
        }
    };
    
    private final GpsStatus.NmeaListener nmeaListener = new GpsStatus.NmeaListener() {
        public void onNmeaReceived(long timestamp, String nmea) {
        	Log.w("TestGps", "now Nmea received");
        	mStrNmea = nmea;
        	mTime = timestamp;
        	 printGpsLocation();
        }
};
    
    public void printGpsLocation() 
    { 
        if( mLocation != null ) 
        { 
            /*
            location.getAccuracy();    精度 
            location.getAltitude();    高度 : 海拔
            location.getBearing();     导向
            location.getSpeed();       速度
            location.getLatitude();    纬度
            location.getLongitude();   经度
            location.getTime();        UTC时间 以毫秒计
            */ 
        	SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        	//dateFormat.setTimeZone(TimeZone.getDefault());
        	String time=dateFormat.format(new Date(mLocation.getTime()));
            mGpsInfo.setText("Accuracy : " + mLocation.getAccuracy() + 
                    "\nAltitude : " + mLocation.getAltitude() + 
                    "\nBearing : " + mLocation.getBearing() + 
                    "\nSpeed : " + mLocation.getSpeed() + 
                    "\nLatitude ：" + mLocation.getLatitude() + 
                    "\nLongitude : " + mLocation.getLongitude() +  
                    "\nTime : " + time +
                    "\nNumber of satellites : "+mNumOfSatellites+
                    "\nNmea : "+mStrNmea+
                    "\nTime1 : "+dateFormat.format(new Date(mTime))); 
        } 
    } 
     
    public class GpsLocationListener implements LocationListener 
    { 
        public void onLocationChanged(Location location) { 
            printGpsLocation(); 
        } 
 
        public void onProviderDisabled(String provider) { 
            Log.d(TAG, "ProviderDisabled : " + provider); 
        } 
 
        public void onProviderEnabled(String provider) { 
            Log.d(TAG, "ProviderEnabled : " + provider); 
        } 
 
        public void onStatusChanged(String provider, int status, Bundle extras) { 
            Log.d(TAG, "StatusChanged : " + provider + status); 
        } 
    } 
} 
