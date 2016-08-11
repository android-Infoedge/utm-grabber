package installreferrer.demo.com.myapplication;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.infoedge.installreferrer.model.UtmSourceInfo;
import com.infoedge.installreferrer.receiver.ReferralReceiver;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.button:
                showSourceInfo();
                break;

        }
    }

    private void showSourceInfo() {

        TextView textView=(TextView) findViewById(R.id.tv_source_info);
        textView.setVisibility(View.VISIBLE);
        UtmSourceInfo utmSourceInfo=ReferralReceiver.retrieveReferralParams1(getApplicationContext());

        if(utmSourceInfo.getUtmSource()==null&& utmSourceInfo.getUtmCampaign()==null&&utmSourceInfo.getUtmContent()==null&&utmSourceInfo.getUtmMedium()==null
                &&utmSourceInfo.getUtmTerm()==null){
         textView.setText("Campaign info not available");
         return;
        }
        textView.setText("UTM SOURCE = "+utmSourceInfo.getUtmSource()+"\n"+"\n"+"UTM MEDIUM = "+utmSourceInfo.getUtmMedium()+"\n"+"\n" +"UTM TERM = "+utmSourceInfo.getUtmTerm()+"\n"+"\n"+"UTM CONTENT = "
                +utmSourceInfo.getUtmContent()+"\n"+"\n"+"UTM CAMPAIGN  = "+utmSourceInfo.getUtmCampaign());

    }
}
