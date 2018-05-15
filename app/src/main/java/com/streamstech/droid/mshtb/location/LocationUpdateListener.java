package com.streamstech.droid.mshtb.location;

public interface LocationUpdateListener {
	void locationUpdatedinApplication(GPSLocationData location);
	void locationUpdatedFailed(String message);
}
