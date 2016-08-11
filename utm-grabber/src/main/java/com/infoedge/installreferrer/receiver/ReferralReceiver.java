package com.infoedge.installreferrer.receiver;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.infoedge.installreferrer.model.UtmSourceInfo;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by ashish on 2/8/16.
 */
public class ReferralReceiver extends BroadcastReceiver {
    private final static String PREFS_FILE_NAME = "ReferralParamsFile";
    public final static String[] EXPECTED_PARAMETERS = {"utm_source",
            "utm_medium", "utm_term", "utm_content", "utm_campaign"};

    @Override
    public void onReceive(Context context, Intent intent) {

        Map<String, String> referralParams = new HashMap<String, String>();
        if (intent == null) {
            return;
        }
        if (!intent.getAction().equals("com.android.vending.INSTALL_REFERRER")) { //$NON-NLS-1$
            return;
        }

        String referrer = intent.getStringExtra("referrer"); //$NON-NLS-1$
        if (referrer == null || referrer.length() == 0) {
            return;
        }

        try {
            referrer = URLDecoder.decode(referrer, "UTF-8"); //$NON-NLS-1$
        } catch (UnsupportedEncodingException e) {
            return;
        }
        try {
            String[] params = referrer.split("&"); // $NON-NLS-1$

            for (String param : params) {
                String[] pair = param.split("="); // $NON-NLS-1$

                if (pair.length == 1) {
                    referralParams.put(pair[0], "AndroidApp");
                } else if (pair.length == 2) {
                    referralParams.put(pair[0], pair[1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ReferralReceiver.storeReferralParams(context, referralParams);

    }

    public static void storeReferralParams(Context context,
                                           Map<String, String> params) {
        SharedPreferences storage = context.getSharedPreferences(
                ReferralReceiver.PREFS_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = storage.edit();
        for (String key : ReferralReceiver.EXPECTED_PARAMETERS) {
            String value = params.get(key);
            System.out.println("value:::::::::;"+value);
            if (value != null) {
                editor.putString(key, value);
            }
        }
        editor.commit();


    }


    public static UtmSourceInfo retrieveReferralParams1(Context context) {
        UtmSourceInfo utmSourceInfo = new UtmSourceInfo();
        SharedPreferences storage = context.getSharedPreferences(
                ReferralReceiver.PREFS_FILE_NAME, Context.MODE_PRIVATE);

        utmSourceInfo.setUtmSource(storage.getString(ReferralReceiver.EXPECTED_PARAMETERS[0], null));
        utmSourceInfo.setUtmMedium(storage.getString(ReferralReceiver.EXPECTED_PARAMETERS[1], null));
        utmSourceInfo.setUtmTerm(storage.getString(ReferralReceiver.EXPECTED_PARAMETERS[2], null));
        utmSourceInfo.setUtmContent(storage.getString(ReferralReceiver.EXPECTED_PARAMETERS[3], null));
        utmSourceInfo.setUtmCampaign(storage.getString(ReferralReceiver.EXPECTED_PARAMETERS[4], null));


        return utmSourceInfo;
    }



    public static void clearReferralFile(Context context) {
        try {
            SharedPreferences searcPref = context.getSharedPreferences(
                    PREFS_FILE_NAME, 0);
            SharedPreferences.Editor editor = searcPref.edit();
            editor.clear();
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
