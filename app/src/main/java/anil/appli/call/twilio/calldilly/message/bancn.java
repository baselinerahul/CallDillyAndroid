package anil.appli.call.twilio.calldilly.message;//package cam.gobble.palo3.gobbleup.comm.message;
//
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.graphics.drawable.ColorDrawable;
//import android.os.AsyncTask;
//
//import com.google.gson.Gson;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.util.EntityUtils;
//
//import java.io.IOException;
//
//import cam.gobble.palo3.gobbleup.R;
//import cam.gobble.palo3.gobbleup.comm.ObjectCaster;
//import cam.gobble.palo3.gobbleup.pojo.MessagePOJO;
//
//
///**
// * Created by Palo12 on 04-05-2017.
// */
//
//public class Message extends AsyncTask<Object, Object, String> {
//
//    private Context context;
//    private ProgressDialog progressdialog;
//
//    public Message() {
//    }
//    public Message(Context context) {
//        this();
//        this.context = context;
//    }
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//        System.out.println("Message.onPreExecute()");
//        if (context != null) {
//            progressdialog = ProgressDialog.show(context, null, null, true);
//            progressdialog.setContentView(R.layout.elemento_progress_splash);
//            progressdialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//            progressdialog.show();
//        }
//    }
//    @Override
//    protected String doInBackground(Object... params) {
//        System.out.println("Message.doInBackground()");
//        MessageType requesttype = ObjectCaster.cast(MessageType.class, params[0]);
//        String urlPath = ObjectCaster.cast(String.class, params[1]);
//        String response = null;
//        if (requesttype == MessageType.POST) {
//            Object request = null;
//            if (params.length > 2) {
//                request = params[2];
//            }
//            response = httpPostRequest(urlPath, request);
//            return response;
//        } else {
//            response = httpGetRequest(urlPath);
//            return response;
//        }
//    }
//    public String httpGetRequest(String urlPath) {
//        System.out.println("Message.httpGetRequest()" + urlPath);
//        // Create a new HttpClient and Post Header
//        HttpClient httpclient = new DefaultHttpClient();
//        HttpGet httpget = new HttpGet(urlPath);
//        try {
//            // Execute HTTP Post Request
//            HttpResponse response = httpclient.execute(httpget);
//            System.out.println("response" + response);
//            String entity = EntityUtils.toString(response.getEntity());
//            return entity;
//        } catch (IOException e) {
//            return null;
//        }
//    }
//
//    public String httpPostRequest(String urlPath, Object request) {
//        System.out.println("Message.httpPostRequest()");
//        // Create a new HttpClient and Post Header
//        HttpClient httpclient = new DefaultHttpClient();
//        HttpPost httppost = new HttpPost(urlPath);
//        httppost.addHeader("content-type", "application/json");
//        try {
//            if (request != null) {
//                Gson gson = new Gson();
//                httppost.setEntity(new StringEntity(gson.toJson(request)));
//            }
//            // Execute HTTP Post Request
//            HttpResponse response = httpclient.execute(httppost);
//            System.out.println("response" + response);
//            String entity = EntityUtils.toString(response.getEntity());
//            return entity;
//        } catch (IOException e) {
//            return null;
//        }
//    }
//    @Override
//    protected void onPostExecute(String result) {
//        System.out.println("SignUpMessage.onPostExecute()" + result);
//        if (context != null) {
//            progressdialog.dismiss();
//            try {
//                MessagePOJO messagePOJO = ObjectCaster.jSONcast(MessagePOJO.class, result);
//                String message = messagePOJO.getMessage();
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
//}