package com.streamstech.droid.mshtb.location;

import java.io.Serializable;

public class GeoLocation implements Serializable
{
	private static final long serialVersionUID = 1L;

    public static final String LOCATION_CELLID = "CELLID";
    public static final String LOCATION_NETWORK = "NETWORK";
    public static final String LOCATION_GPS = "GPS";
	private double longitude;
    private double latitude;
    private String address;
   
    public GeoLocation()
    {
    }

	public GeoLocation(double longitude, double latitude)
    {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public GeoLocation(double longitude, double latitude, String address)
    {
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }
    
    public void translate(double dLon, double dLat)
    {
        this.longitude += dLon;
        this.latitude += dLat;
    }

    public String toString()
    {
        return (longitude + "," + latitude);
    }

    public GeoLocation clone()
    {
        return new GeoLocation(longitude, latitude);
    }

    public boolean isSameLocation(GeoLocation geoLocation)
    {
        return (geoLocation.getLatitude() == latitude && geoLocation.getLongitude() == longitude);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
