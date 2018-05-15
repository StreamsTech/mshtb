package com.streamstech.droid.mshtb.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.streamstech.droid.mshtb.gcm.GCMSettings;

public class PushDialogActivity extends Activity
{
	private Context context;
	private Bundle curreBundle = null;
	private AlertDialog alertDialog = null;
	private PowerManager pm;
	private WakeLock wl;
	private KeyguardManager km;
	private KeyguardLock kl;
	private LinearLayout alert;
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		context = this;
		curreBundle = getIntent().getExtras();
		
		if(curreBundle.getString(GCMSettings.NOTIFICATION_PAYLOAD)!= null)
		{
			showAlert(curreBundle.getString(GCMSettings.NOTIFICATION_PAYLOAD));
		}

	}
	
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		
		curreBundle = intent.getExtras();
		
//		if(curreBundle.getString(AppSettings.NOTIFICATION_DATA)!= null)
//		{
//			showAlert(curreBundle.getString(AppSettings.NOTIFICATION_TITTLE));
//		}
	}
	
	
	private void showAlert(final String alertdata)
	{
        if(alertDialog != null && alertDialog.isShowing())
        {
            alertDialog.dismiss();
            alertDialog = null;
        }

//        try
//        {
//			LayoutInflater layoutInflater = LayoutInflater.from(context);
//		    View view = layoutInflater.inflate(R.layout.activity_push_dialog, null);
//
//			AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AppTheme_PopupOverlay));
//			alertDialog = builder.create();
//	        //alertDialog  = new AlertDialog.Builder(context).create();
//	        alertDialog.setView(view);
//
//	        alertDialog.setTitle("WeatherSmart");
//	        //alertDialog.setMessage(model.getMessage());
//	       //adding text dynamically
//			TextView txtTitle = (TextView) view.findViewById(R.id.txtAlertTitle);
//			txtTitle.setText(model.getTitle());
//
//			TextView txtMessage = (TextView) view.findViewById(R.id.txtAlertMessage);
//			txtMessage.setText(model.getMessage());
//
//			alert = (LinearLayout) view.findViewById(R.id.alert);
//			if (model.getAlerttype().toLowerCase().equals("warning")) {
//				alert.setBackgroundResource(R.drawable.alertbg_warn_red);
//			} else if (model.getAlerttype().toLowerCase()
//					.equals("watch")) {
//				alert.setBackgroundResource(R.drawable.alertbg_watch_brown);
//			} else if (model.getAlerttype().toLowerCase()
//					.equals("advisory")) {
//				alert.setBackgroundResource(
//						R.drawable.alertbg_advisory_yellow);
//			}
//
//	        alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
//	           public void onClick(DialogInterface dialog, int which) {
//	               alertDialog.dismiss();
//	               alertDialog = null;
//	              finish();
//	           }
//	        });
//	        alertDialog.setCancelable(false);
//	        // Set the Icon for the Dialog
//	        //alertDialog.setIcon(R.drawable.ic_launcher_small);
//	        alertDialog.show();
//
//			if (android.os.Build.VERSION.SDK_INT < 23) {
//				alertDialog.getButton(alertDialog.BUTTON_NEUTRAL).setTextColor(getResources().getColor(R.color.saws_blue));
//			} else {
//				alertDialog.getButton(alertDialog.BUTTON_NEUTRAL).setTextColor(getResources().getColor(R.color.saws_blue, null));
//			}
//        }
//        catch (Exception e) {
//        	e.printStackTrace();
//		}
	}
	
	@Override
	public void onBackPressed() {
		//super.onBackPressed();
	}
	
	@Override
	public void finish() {
		super.finish();
	}
}
