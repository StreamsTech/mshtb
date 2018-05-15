package com.streamstech.droid.mshtb.location;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;

/**
 * TimeoutableLocationListner is implementation of LocationListener.
 * If onLocationChanged isn't called within XX mili seconds, automatically remove listener.
 *
 * @author amay077 - http://twitter.com/amay077
 */
public class TimeoutLocationListener implements LocationListener {
    //protected Timer timerTimeout = new Timer();
    protected LocationManager locaMan = null;
    private String provider;
    private TimeoutLisener timeoutListener;
    Handler handler = new Handler();

    /**
     * Initialize instance.
     *
     * @param locaMan the base of LocationManager, can't set null.
     * @param timeOutMS timeout elapsed (mili seconds)
     * @param timeoutListener if timeout, call onTimeouted method of this.
     */
    public TimeoutLocationListener(LocationManager locaMan, long timeOutMS,
            final TimeoutLisener timeoutListener, final String provider) {
        this.locaMan  = locaMan;
        this.provider = provider;
        this.timeoutListener = timeoutListener;
        handler.postDelayed(runnable, timeOutMS);
    }
    
    Runnable runnable = new Runnable() {
        public void run() {
        	if (timeoutListener != null) {
              timeoutListener.onTimeouted(TimeoutLocationListener.this, provider);
          }
          stopLocationUpdateAndTimer();
        }
    };


    @Override
    public void onLocationChanged(Location location) {
    	stopLocationUpdateAndTimer();
    	if (timeoutListener != null)
    		timeoutListener.foundLocation(location);
    }

    @Override
    public void onProviderDisabled(String s) { }

    @Override
    public void onProviderEnabled(String s) { }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) { }

    private void stopLocationUpdateAndTimer() {

    	try {
	    	if (locaMan != null)
	    		locaMan.removeUpdates(this);
	    	if (handler != null && runnable != null)
	    		handler.removeCallbacks(runnable);
	    } catch (Exception e) {
	    	e.printStackTrace();
		}
        handler = null;
        runnable = null;
    }

    public interface TimeoutLisener {
        void onTimeouted(LocationListener sender, String provider);
        void foundLocation(Location location);
    }
}