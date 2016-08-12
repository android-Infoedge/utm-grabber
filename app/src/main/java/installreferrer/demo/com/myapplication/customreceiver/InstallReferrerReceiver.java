package installreferrer.demo.com.myapplication.customreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.infoedge.installreferrer.receiver.ReferralReceiver;

/**
 * Created by ashish on 2/8/16.
 */
public class InstallReferrerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            new ReferralReceiver().onReceive(context,intent);

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
