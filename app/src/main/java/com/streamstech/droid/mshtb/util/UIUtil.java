package com.streamstech.droid.mshtb.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by AKASH-LAPTOP on 8/7/2017.
 */

public class UIUtil {
    private static SweetAlertDialog sweetAlertDialog;
    public static void showInfoDialog(final Context context, final int type, final String title, final String message)
    {
        //SweetAlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AppTheme_PopupOverlay));
        SweetAlertDialog pDialog = new SweetAlertDialog(context, type);
        if (type == SweetAlertDialog.PROGRESS_TYPE)
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));

        pDialog.setTitleText(title);
        pDialog.setContentText(message);
//        pDialog.show();

        if(!((Activity) context).isFinishing())
        {
            if (pDialog != null) {
                pDialog.show();
            }
        }
    }

    public static void showInfoDialog(final Context context, final int type, final String title, final String message,
                                      final SweetAlertDialog.OnDismissListener onDismissListener)
    {
        SweetAlertDialog pDialog = new SweetAlertDialog(context, type);
        if (type == SweetAlertDialog.PROGRESS_TYPE)
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));

        pDialog.setOnDismissListener(onDismissListener);
        pDialog.setTitleText(title);
        pDialog.setContentText(message);
        if(!((Activity) context).isFinishing())
        {
            if (pDialog != null) {
                pDialog.show();
            }
        }
    }

    public static void showSweetProgress(final Context context, final String message,
                                         final boolean cancelable,
                                         SweetAlertDialog.OnSweetClickListener cancelListener) {

        if (sweetAlertDialog != null)
        {
            sweetAlertDialog.dismiss();
            sweetAlertDialog = null;
        }
        sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        sweetAlertDialog.setTitleText(message);
        sweetAlertDialog.setCancelable(cancelable);
        sweetAlertDialog.setCancelClickListener(cancelListener);
        sweetAlertDialog.show();
    }

    public static void hideSweetProgress() {
        if (sweetAlertDialog != null) {
            sweetAlertDialog.dismiss();
            sweetAlertDialog = null;
        }
    }

    public static void displayError(Context context, String message){
        UIUtil.showInfoDialog(context, SweetAlertDialog.WARNING_TYPE, "Error", String.format("%s information missing", message));
    }
}
