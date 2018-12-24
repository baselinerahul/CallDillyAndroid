package anil.appli.call.twilio.calldilly;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import anil.appli.call.twilio.calldilly.comm.Common;
import anil.appli.call.twilio.calldilly.message.MessageManager;
import anil.appli.call.twilio.calldilly.message.MessageType;
import anil.appli.call.twilio.calldilly.pojo.UserPOJO;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Login extends AppCompatActivity implements
        View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;
    @BindView(R.id.editTextEmail)
    EditText editTextEmail;
    @BindView(R.id.editTextPassword)
    EditText editTextPassword;

    private GoogleApiClient mGoogleApiClient;
    private SignInButton btnSignIn;


//    ImageView gmailLogin;
    @BindView(R.id.facebooklogin)
    LoginButton facebooklogin;

    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.submitLogin)
    Button submitLogin;
    @BindView(R.id.fb)
    Button fb;
    ProgressDialog mProgressDialog;

    private Button btnLogin, btnSignup;
    CallbackManager callbackManager;
    private static final String EMAIL = "email";
    AccessToken accessToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        btnSignIn = (SignInButton) findViewById(R.id.gmailLogin);
        btnSignIn.setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        btnSignIn.setScopes(gso.getScopeArray());
        btnSignIn.setBackgroundResource(R.mipmap.google);
        accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        callbackManager = CallbackManager.Factory.create();
        facebooklogin.setReadPermissions(Arrays.asList(EMAIL));
        facebooklogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                accessToken = loginResult.getAccessToken();
                getUserProfile(accessToken);
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });


        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup = (Button) findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Registation.class);
                startActivity(intent);
            }
        });

        submitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidate()) {
                    UserPOJO userPOJO = new UserPOJO();
                    userPOJO.setEmail(editTextEmail.getText().toString());
                    userPOJO.setPassword(editTextPassword.getText().toString());
                    ReqLogin reqLogin = new ReqLogin();
                    reqLogin.execute(MessageType.POST, Common.LoginURL, userPOJO);
                }
            }
        });
        //  AccessContact();

    }

//    private void AccessContact()
//    {
//        List<String> permissionsNeeded = new ArrayList<String>();
//        final List<String> permissionsList = new ArrayList<String>();
//        if (!addPermission(permissionsList, android.Manifest.permission.READ_CONTACTS))
//            permissionsNeeded.add("Read Contacts");
//        if (!addPermission(permissionsList, android.Manifest.permission.WRITE_CONTACTS))
//            permissionsNeeded.add("Write Contacts");
//        if (permissionsList.size() > 0) {
//            if (permissionsNeeded.size() > 0) {
//                String message = "You need to grant access to " + permissionsNeeded.get(0);
//                for (int i = 1; i < permissionsNeeded.size(); i++)
//                    message = message + ", " + permissionsNeeded.get(i);
//                showMessageOKCancel(message,
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
//                                        REQUEST_MULTIPLE_PERMISSIONS);
//                            }
//                        });
//                return;
//            }
//            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
//                    REQUEST_MULTIPLE_PERMISSIONS);
//
//            return;
//        }
//
//    }

//    private boolean addPermission(List<String> permissionsList, String permission) {
//        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
//            permissionsList.add(permission);
//
//            if (!shouldShowRequestPermissionRationale(permission))
//                return false;
//        }
//        return true;
//    }
//
//    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
//        new AlertDialog.Builder(Login.this)
//                .setMessage(message)
//                .setPositiveButton("OK", okListener)
//                .setNegativeButton("Cancel", null)
//                .create()
//                .show();
//    }


    class ReqLogin extends MessageManager {
        @Override
        public void onPostExecute(String result) {
            System.out.println(result);
            if (result == null) {
                finish();
            } else {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String message = jsonObject.get("message").toString();
                    String sucees = jsonObject.get("success").toString();
                    if (sucees == "1") {
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Login.this, message, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception c) {
                    System.out.println(c);
                }

            }
        }
    }


    public boolean isValidate() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        if (email.isEmpty()) {
            editTextEmail.setError("Please enter your email address");
            return false;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Please enter your Password");
            return false;
        }
        return true;
    }



    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            String name = object.getJSONObject("name").toString();
                            System.out.println(name);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    public void fbclick(View view) {
        if (view == fb) {
            facebooklogin.performClick();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }


    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.e(TAG, "display name: " + acct.getDisplayName());
            String personName = acct.getDisplayName();
            //      String personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();
            Log.e(TAG, "Name: " + personName + ", email: " + email
            );

            System.out.println(personName);
            System.out.println(email);
        } else {
            System.out.println("eeeee");
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("please wait...");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    @Override
    public void onClick(View v) {
        signIn();
    }
}
