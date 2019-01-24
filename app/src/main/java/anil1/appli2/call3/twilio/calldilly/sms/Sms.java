package anil1.appli2.call3.twilio.calldilly.sms;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import anil1.appli2.call3.twilio.calldilly.R;

import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import anil1.appli2.call3.twilio.calldilly.message.MessageManager;
import anil1.appli2.call3.twilio.calldilly.message.MessageType;
import anil1.appli2.call3.twilio.calldilly.pojo.SmsPOJO;
import okhttp3.OkHttpClient;


public class Sms extends AppCompatActivity {
    private EditText mTo;
    private EditText mBody;
    private Button mSend;
    private OkHttpClient mClient = new OkHttpClient();
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        mTo = (EditText) findViewById(R.id.txtNumber);
        mBody = (EditText) findViewById(R.id.txtMessage);
        mSend = (Button) findViewById(R.id.btnSend);
        mContext = getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsPOJO smsPOJO = new SmsPOJO();
                smsPOJO.setTo(mTo.getText().toString());
                smsPOJO.setBody(mBody.getText().toString());
                SendMessage reqLogin = new SendMessage();
                reqLogin.execute(MessageType.POST, "https://calldilly.herokuapp.com/sms.php", smsPOJO);
            }
        });
    }

    class SendMessage extends MessageManager {
        @Override
        public void onPostExecute(String result) {
            System.out.println(result);
            if (result == null) {
                finish();
            } else {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    onBackPressed();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }

}
