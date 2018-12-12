package anil.appli.call.twilio.calldilly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Registation extends AppCompatActivity {

    private Button btnLogin, btnSignup, submitRegistation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registation);
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
    }
}
