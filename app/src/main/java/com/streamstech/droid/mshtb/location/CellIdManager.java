//package com.streamstech.droid.mshtb.location;
//
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.location.Address;
//import android.location.Geocoder;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.support.v4.content.ContextCompat;
//import android.telephony.PhoneStateListener;
//import android.telephony.SignalStrength;
//import android.telephony.TelephonyManager;
//import android.telephony.gsm.GsmCellLocation;
//import com.google.gson.JsonObject;
//import java.io.IOException;
//import java.net.HttpURLConnection;
//import java.util.List;
//import java.util.Locale;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//import com.streamstech.droid.mshtb.data.CellIDResponse;
//import com.streamstech.droid.mshtb.io.EndpointInterface;
//import com.streamstech.droid.mshtb.io.Paths;
//import com.streamstech.droid.mshtb.util.MSHTBApplication;
//
//public class CellIdManager  extends PhoneStateListener{
//	private TelephonyManager telephonyManager = null;
//	private GsmCellLocation location;
//	private Context context = null;
//	private CellInfoUpdateListener cellInfoListener = null;
//
//	private int iSignalStrength = 0;
//	private int lastLogCellID = -1;
//
//	private static CellIdManager insCellIdManager;
//
//	public static CellIdManager getInstance(Context context)
//	{
//		if (insCellIdManager == null)
//			insCellIdManager = new CellIdManager(context);
//		return insCellIdManager;
//	}
//
//	protected CellIdManager(Context context) {
//		this.context = context;
//		telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//		telephonyManager.listen(this, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS | PhoneStateListener.LISTEN_CELL_LOCATION | PhoneStateListener.LISTEN_SERVICE_STATE);
//	}
//
//	private boolean isGSM = false;
//
//	public void getCityName(final GPSLocationData gpsLocationData, final String source) {
//		new AsyncTask<Void, Integer, List<Address>>() {
//			@Override
//			protected List<Address> doInBackground(Void... arg0) {
//				// Locale.ENGLISH is still using the device default language
//				//Geocoder coder = new Geocoder(context, Locale.ENGLISH);
//				Geocoder coder = new Geocoder(context, Locale.US);
//				List<Address> results = null;
//				try {
//					results = coder.getFromLocation(gpsLocationData.getLocation().getLatitude(), gpsLocationData.getLocation().getLongitude(), 1);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				return results;
//			}
//
//			@Override
//			protected void onPostExecute(List<Address> results) {
//				if (results != null) {
//					//listener.onFinished(results);
//					// Now upload to server
//					// Complete address
//					Address obj = results.get(0);
//					String add = obj.getAddressLine(0);
//					add = add + "\n" + obj.getLocality();
//					add = add + "\n" + obj.getCountryName();
////					add = add + "\n" + obj.getCountryCode();
////					add = add + "\n" + obj.getAdminArea();
////					add = add + "\n" + obj.getPostalCode();
////					add = add + "\n" + obj.getSubAdminArea();
//
//					//add = add + "\n" + obj.getSubThoroughfare();
//					upload(gpsLocationData, source, add);
//				}
//				else
//					upload(gpsLocationData, source, "");
//			}
//		}.execute();
//	}
//
//	public void logCellIDInformation(final GPSLocationData gpsLocationData, final String source)
//	{
//		getCityName(gpsLocationData, source);
//	}
//
//	private void upload(final GPSLocationData gpsLocationData, final String source, final String locationame)
//	{
//		if (telephonyManager != null) {
//			try {
//				if (telephonyManager.getCellLocation() instanceof GsmCellLocation) {
//					isGSM = true;
//					location = (GsmCellLocation) telephonyManager.getCellLocation();
//					if (location != null && this.iSignalStrength != 0) {
//						System.out.println("Get Cell ID: " + location.getCid());
//
//						String mobileNetworktype = getNetworkType();
//						String mobileCountry = telephonyManager.getNetworkCountryIso().toUpperCase();
//
//						// Don't want to upload same cellid again & again
////						if (lastLogCellID == location.getCid())
////							return;
//
//						// Comma separated fields
//						// Cellid, signal strength, lac
//						Retrofit client = new Retrofit.Builder()
//								.baseUrl(Paths.CELLID_SERVER_URL)
//								.addConverterFactory(GsonConverterFactory.create())
//								.build();
//
//						String IMEI = "NOT_PERMITTED";
//						if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)
//							IMEI = telephonyManager.getDeviceId();
//
//						JsonObject dataset = new JsonObject();
//						dataset.addProperty("application", getApplicationName());
//						dataset.addProperty("IMEI", IMEI);
//						dataset.addProperty("appversion", getApplicationVersion());
//
//						EndpointInterface service = client.create(EndpointInterface.class);
//
//						retrofit2.Call<CellIDResponse> call = service.addcellidlocation(
//								gpsLocationData.getLocation().getLongitude(),
//								gpsLocationData.getLocation().getLatitude(),
//								(location.getCid() % 65536), telephonyManager.getNetworkOperator().substring(0, 3),
//								telephonyManager.getNetworkOperator().substring(3), this.iSignalStrength,
//								location.getLac(), getCompleteDeviceName(), dataset.toString(),
//								source, gpsLocationData.getAccuracy(), gpsLocationData.getBearing(),
//								gpsLocationData.getSpeed() * 3.6f, mobileNetworktype,
//								getInternetProvider(), getNetworkOperatorName().toUpperCase(),
//								locationame, getDeviceBrand(), getDeviceModel(), getDeviceOS(),
//								"ANDROID", mobileCountry.toUpperCase(),
//								MSHTBApplication.getInstance().getTime(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
//
//						call.enqueue(new retrofit2.Callback<CellIDResponse>() {
//							@Override
//							public void onResponse(retrofit2.Call<CellIDResponse> call, retrofit2.Response<CellIDResponse> response) {
//
//								if (response.isSuccessful() && response.code() != HttpURLConnection.HTTP_NO_CONTENT) {
//									if (response.body().isSuccess()) {
//										System.out.println("Cell id logged");
//										lastLogCellID = location.getCid();
//									} else
//										System.out.println("Cell id logging failed");
//								} else {
//									System.out.println("Error in cell id logging response: " + response.message());
//								}
//							}
//
//							@Override
//							public void onFailure(retrofit2.Call<CellIDResponse> call, Throwable t) {
//								t.printStackTrace();
//							}
//						});
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	/**
//	 * Asynchronous method
//	 * Will pass data via listener
//	 */
//	public void getCellIDLocation() {
//
//		if (telephonyManager != null) {
//			try {
//				if (telephonyManager.getCellLocation() instanceof GsmCellLocation) {
//					isGSM = true;
//					location = (GsmCellLocation) telephonyManager.getCellLocation();
//					if (location != null) {
//
//						Retrofit client = new Retrofit.Builder()
//								.baseUrl(Paths.CELLID_SERVER_URL)
//								.addConverterFactory(GsonConverterFactory.create())
//								.build();
//
//						EndpointInterface service = client.create(EndpointInterface.class);
//
//						retrofit2.Call<CellIDResponse> call = service.getcellidlocation((location.getCid() % 65536),
//								telephonyManager.getNetworkOperator().substring(0, 3), telephonyManager.getNetworkOperator().substring(3),
//								this.iSignalStrength, location.getLac());
//
//						call.enqueue(new retrofit2.Callback<CellIDResponse>() {
//							@Override
//							public void onResponse(retrofit2.Call<CellIDResponse> call, retrofit2.Response<CellIDResponse> response) {
//
//								if (response.isSuccessful() && response.code() != HttpURLConnection.HTTP_NO_CONTENT) {
//									if (response.body().isSuccess()) {
//										System.out.println("Cell id location found");
//										GeoLocation loc = new GeoLocation(response.body().getLongitude(),
//												response.body().getLatitude());
//										if (isGSM) {
//											if (getCellInfoListener() != null) {
//												getCellInfoListener()
//														.updateCellInformation(new CellIdData(
//																location, telephonyManager, loc, response.body().getRadius(), true));
//											}
//										}
//									} else
//										System.out.println("Cell id location not found");
//								} else {
//									System.out.println("Error in cell id response: " + response.message());
//								}
//							}
//
//							@Override
//							public void onFailure(retrofit2.Call<CellIDResponse> call, Throwable t) {
//								System.out.println(t.getMessage());
//							}
//						});
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	private String getCompleteDeviceName() {
//		String manufacturer = Build.MANUFACTURER;
//		String model = Build.MODEL;
//		if (model.startsWith(manufacturer)) {
//			return capitalize(model);
//		}
//		return capitalize(manufacturer) + " " + model;
//    }
//
//	private String capitalize(String s) {
//		if (s == null || s.length() == 0) {
//			return "";
//		}
//		char first = s.charAt(0);
//		return !Character.isUpperCase(first) ? Character.toUpperCase(first) + s.substring(1) : s;
//	}
//
//	private String getDeviceBrand() {
//        return Build.BRAND;
//    }
//
//	private String getDeviceModel() {
//        return Build.MODEL;
//    }
//
//	private String getDeviceOS() {
//		return Build.VERSION.RELEASE + " (API " + Integer.toString(Build.VERSION.SDK_INT) + ")";
//    }
//
//	private String getApplicationName() {
//		int stringId = context.getApplicationInfo().labelRes;
//		return context.getString(stringId);
//	}
//
//	private String getApplicationVersion()
//	{
//		try {
//			return context.getPackageManager().getPackageInfo(context.getApplicationInfo().packageName, 0).versionName;
//		}
//		catch (PackageManager.NameNotFoundException e) {
//			e.printStackTrace();
//			return "UNKNOWN";
//		}
//	}
//
//	public void setCellInfoListener(CellInfoUpdateListener cellInfoListener) {
//		this.cellInfoListener = cellInfoListener;
//	}
//
//	private String getInternetProvider() {
//		String str = "";
//		int is = getConnectivityStatus();
//		if (is == 1) {
//			str = "MOBILE " + getMobileGeneration();
//			return str;
//		} else if (is == 2) {
//			return "WIFI";
//		} else {
//			return str;
//		}
//	}
//
//	public String getMobileGeneration() {
//		try {
//			int type = telephonyManager.getNetworkType();
//			if (type == TelephonyManager.NETWORK_TYPE_1xRTT) {
//				return "";
//			}
//			else if (type == TelephonyManager.NETWORK_TYPE_CDMA) {
//				return "";
//			}
//			else if (type == TelephonyManager.NETWORK_TYPE_EDGE) {
//				return "EDGE (2.75G)";
//			}
//			else if (type == TelephonyManager.NETWORK_TYPE_EHRPD) {
//				return "";
//			}
//			else if (type == TelephonyManager.NETWORK_TYPE_EVDO_0) {
//				return "";
//			}
//			else if (type == TelephonyManager.NETWORK_TYPE_EVDO_A) {
//				return "";
//			}
//			else if (type == TelephonyManager.NETWORK_TYPE_EVDO_B) {
//				return "";
//			}
//			else if (type == TelephonyManager.NETWORK_TYPE_GPRS) {
//				return "GPRS (2.5G)";
//			}
//			else if (type == TelephonyManager.NETWORK_TYPE_HSDPA) {
//				return "HSDPA (3G+)";
//			}
//			else if (type == TelephonyManager.NETWORK_TYPE_HSPA) {
//				return "HSPA (3G+)";
//			}
//			else if (type == TelephonyManager.NETWORK_TYPE_HSPAP) {
//				return "HSPA+ (3.75G)";
//			}
//			else if (type == TelephonyManager.NETWORK_TYPE_HSUPA) {
//				return "HSUPA (3G+)";
//			}
//			else if (type == TelephonyManager.NETWORK_TYPE_IDEN) {
//				return "";
//			}
//			else if (type == TelephonyManager.NETWORK_TYPE_LTE) {
//				return "LTE (4G)";
//			}
//			else if (type == TelephonyManager.NETWORK_TYPE_UMTS) {
//				return "UMTS (3G)";
//			}
//			return "";
//		} catch (Exception e) {
//			return "";
//		}
//	}
//
//	public int getConnectivityStatus() {
//		NetworkInfo activeNetwork = ((ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
//		if (activeNetwork != null) {
//			if (activeNetwork.getType() == 1) {
//				return 2;
//			}
//			if (activeNetwork.getType() == 0) {
//				return 1;
//			}
//		}
//		return 0;
//	}
//
//	// Mobile network type
//	private String getNetworkType() {
//		int nt = -1;
//		try {
//			nt = telephonyManager.getNetworkType();
//		} catch (Exception e) {
//			return "";
//		}
//
////		try {
////			nt = Util.getIntBySlot(this.context, "getNetworkTypeGemini", 0);
////		} catch (Util.GeminiMethodNotFoundException e2) {
////			e2.printStackTrace();
////			try {
////				nt = Util.getIntBySlot(this.context, "getNetworkType", 0);
////			} catch (Util.GeminiMethodNotFoundException e1) {
////				e1.printStackTrace();
////			}
////		}
//		if (nt == TelephonyManager.NETWORK_TYPE_1xRTT) {
//			return "1xRTT";
//		}
//		else if (nt == TelephonyManager.NETWORK_TYPE_CDMA) {
//			return "CDMA";
//		}
//		else if (nt == TelephonyManager.NETWORK_TYPE_EDGE) {
//			return "EDGE";
//		}
//		else if (nt == TelephonyManager.NETWORK_TYPE_EHRPD) {
//			return "eHRPD";
//		}
//		else if (nt == TelephonyManager.NETWORK_TYPE_EVDO_0) {
//			return "EVDO_0";
//		}
//		else if (nt == TelephonyManager.NETWORK_TYPE_EVDO_A) {
//			return "EVDO_A";
//		}
//		else if (nt == TelephonyManager.NETWORK_TYPE_EVDO_B) {
//			return "EVDO_B";
//		}
//		else if (nt == TelephonyManager.NETWORK_TYPE_GPRS) {
//			return "GPRS";
//		}
//		else if (nt == TelephonyManager.NETWORK_TYPE_HSDPA) {
//			return "HSDPA";
//		}
//		else if (nt == TelephonyManager.NETWORK_TYPE_HSPA) {
//			return "HSPA";
//		}
//		else if (nt == TelephonyManager.NETWORK_TYPE_HSPAP) {
//			return "HSPA+";
//		}
//		else if (nt == TelephonyManager.NETWORK_TYPE_HSUPA) {
//			return "HSUPA";
//		}
//		else if (nt == TelephonyManager.NETWORK_TYPE_IDEN) {
//			return "iDen";
//		}
//		else if (nt == TelephonyManager.NETWORK_TYPE_LTE) {
//			return "LTE";
//		}
//		else if (nt == TelephonyManager.NETWORK_TYPE_UMTS) {
//			return "UMTS";
//		}
//		return "UNKNOWN";
//	}
//
//	private String getNetworkOperatorName()
//	{
//		if (telephonyManager != null) {
//			String carrierName = telephonyManager.getNetworkOperatorName();
//			System.out.printf(carrierName);
//
//			carrierName = telephonyManager.getSimOperatorName();
//			System.out.printf(carrierName);
//
//			return carrierName;
//		}
//		else
//		{
//			return "Unknown";
//		}
//	}
//
//	private String getPhoneNos() {
//		String str = getPhoneNo(0);
//		String str1 = getPhoneNo(1);
//		if (str.length() == 0 || str1.length() == 0) {
//			return str;
//		}
//		return new StringBuilder(String.valueOf(str)).append(" (SIM 1)\n").append(str1).append(" (SIM 2)").toString();
//	}
//
//	private String getPhoneNo(int SlotNo) {
//		String str = "";
//		if (SlotNo == 0) {
//			try {
//				str = telephonyManager.getLine1Number().toString();
//			} catch (Exception e) {
//				return "";
//			}
//		}
////		try {
////			str = Util.getStringBySlot(this.context, "getLine1NumberGemini", SlotNo);
////		} catch (Util.GeminiMethodNotFoundException e2) {
////			e2.printStackTrace();
////			try {
////				str = Util.getStringBySlot(this.context, "getLine1Number", SlotNo);
////			} catch (Util.GeminiMethodNotFoundException e1) {
////				e1.printStackTrace();
////			}
////		}
//		if (str == null) {
//			return "";
//		}
//		return str;
//	}
//
//	@Override
//	public void onSignalStrengthsChanged(SignalStrength signalStrength) {
//		super.onSignalStrengthsChanged(signalStrength);
//
//		if (signalStrength.isGsm()) {
//			if (signalStrength.getGsmSignalStrength() != 99)
//			{
//				int val = -113 + (2 * signalStrength.getGsmSignalStrength());
//				iSignalStrength = val;
//			}
//			else
//				iSignalStrength = signalStrength.getGsmSignalStrength();
//
//			//System.out.println("Signal strength change: " + iSignalStrength);
//			//telephonyManager.listen(MyListener, PhoneStateListener.LISTEN_NONE);
//		} else {
//			int val = -113 + (2 * signalStrength.getCdmaDbm());
//			iSignalStrength = val;
//			//telephonyManager.listen(MyListener, PhoneStateListener.LISTEN_NONE);
//		}
//	}
//
//	public void removeListener() {
//		setCellInfoListener(null);
//
//	}
//
//	public CellInfoUpdateListener getCellInfoListener() {
//		return cellInfoListener;
//	}
//}
