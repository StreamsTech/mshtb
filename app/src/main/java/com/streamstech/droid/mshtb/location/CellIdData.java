package com.streamstech.droid.mshtb.location;

import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;

public class CellIdData
{
	private int cellID;
	private int localAreaCode;
	private String operatorName;
	private String operatorCode;
	private String operatorISO;
	private String simCountryCode;
	private String simOperatorName;
	private String simSerialNo;
	private GeoLocation location;
	private int radius;
	private boolean status=true;
	
	public CellIdData(GsmCellLocation location,TelephonyManager tm, GeoLocation loc, int rad, boolean isSuccess)
	{
		this.cellID = location.getCid();
		this.localAreaCode = location.getLac();
		this.operatorName = tm.getNetworkOperatorName();
		this.operatorCode = tm.getNetworkOperator();
		this.operatorISO = tm.getNetworkCountryIso();
		this.simCountryCode = tm.getSimCountryIso();
		this.simOperatorName = tm.getSimOperatorName();
		this.simSerialNo = tm.getSimSerialNumber();
		this.location = loc;
		this.radius = rad;
		this.status = isSuccess;
	}
	
	public CellIdData(CdmaCellLocation location,TelephonyManager tm, GeoLocation loc, int rad, boolean isSuccess)
	{
		this.cellID = location.getSystemId();
		this.localAreaCode = location.getNetworkId();
		this.operatorName = tm.getNetworkOperatorName();
		this.operatorCode = tm.getNetworkOperator();
		this.operatorISO = tm.getNetworkCountryIso();
		this.simCountryCode = tm.getSimCountryIso();
		this.simOperatorName = tm.getSimOperatorName();
		this.simSerialNo = tm.getSimSerialNumber();
		this.location = loc;
		this.radius = rad;
		this.status = isSuccess;
	}
	
	
	public CellIdData()
	{
		
	}
	
	// for cell id
	public int getCellID()
	{
		return this.cellID;
	}
	
	// local area code
	public int getLocalAreaCode()
	{
		return this.localAreaCode;
	}
	
	// for operator name
	public String getOperatorName()
	{
		return this.operatorName;
	}
	
	// for operator code
	public String getOperatorCode()
	{
		return operatorCode;
	}
	
	// for operator ISO
	public String getOperatorISO()
	{
		return operatorISO;
	}
	
	// sim country code
	public String getSimCountryCode()
	{
		return simCountryCode;
	}
	
	// sim operator name
	public String getSimOperatorName()
	{
		return simOperatorName;
	}
	
	// sim serial number
	public String getSimSerialNo()
	{
		return simSerialNo;
	}
	
	// get geo location
	public GeoLocation getLocation()
	{
		return this.location;
	}
	
	// get radius
	public int getRadius()
	{
		return this.radius;
	}
	
	// get status
	public boolean getStatus()
	{
		return this.status;
	}
	
	public void setStatus(boolean status)
	{
		this.status = status;
	}
	
	@Override
	public String toString() {
		if (location != null)
			return "Status: " + status + ", Radius: " + radius + ", Location: " + location.toString();
		else
			return "No cell id data";

	}
}
