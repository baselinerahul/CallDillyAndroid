package anil1.appli2.call3.twilio.calldilly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import anil1.appli2.call3.twilio.calldilly.comm.Common;
import anil1.appli2.call3.twilio.calldilly.message.MessageManager;
import anil1.appli2.call3.twilio.calldilly.message.MessageType;
import anil1.appli2.call3.twilio.calldilly.pojo.UserPOJO;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Registation extends AppCompatActivity {

    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.editText2)
    EditText editText2;
    @BindView(R.id.editText3)
    EditText editText3;
    private Button btnLogin, btnSignup, submitRegistation;
    String part1, part2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registation);
        ButterKnife.bind(this);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        submitRegistation = (Button) findViewById(R.id.submitRegistation);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registation.this, Login.class);
                startActivity(intent);
            }
        });

        submitRegistation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidate()) {
                    UserPOJO userPOJO = new UserPOJO();
                    userPOJO.setEmail(editText.getText().toString());
                    userPOJO.setPassword(editText2.getText().toString());
                    userPOJO.setPhone(editText3.getText().toString());
                    userPOJO.setUsername(part1);
                    ReqLRegistation reqLRegistation = new ReqLRegistation();
                    reqLRegistation.execute(MessageType.POST, Common.RegistationURL, userPOJO);
                }
            }
        });

    }

    public boolean isValidate() {
        String email = editText.getText().toString();
        String password = editText2.getText().toString();
        String phone = editText3.getText().toString();
        // 004
        if (email.isEmpty()) {
            editText.setError("Please enter email");
            return false;
        }
        if (password.isEmpty()) {
            editText2.setError("Please enter Password");
            return false;
        }
        if (phone.isEmpty()) {
            editText3.setError("Please enter Phone");
            return false;
        }
        String[] parts = email.split("@");
        part1 = parts[0];
        return true;
    }


    class ReqLRegistation extends MessageManager {
        @Override
        public void onPostExecute(String result) {
            System.out.println(result);
            if (result == null) {
                finish();
            } else {
                System.out.println(result);
                Intent intent = new Intent(Registation.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }

}
