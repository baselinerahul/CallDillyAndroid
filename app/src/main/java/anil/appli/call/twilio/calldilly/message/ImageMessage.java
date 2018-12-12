package anil.appli.call.twilio.calldilly.message;//package com.example.guru.realstate.comm.message;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.os.AsyncTask;
//import android.util.Base64;
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
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import cam.gobble.palo3.gobbleup.comm.ObjectCaster;
//import cam.gobble.palo3.gobbleup.pojo.MessagePOJO;
//import cam.gobble.palo3.gobbleup.pojo.UserPOJO;
//
//
///**
// * Created by Palo12 on 04-05-2017.
// */
//public class ImageMessage extends AsyncTask<Object,Object,Object> {
//
//    private final UserPOJO userPOJO;
//    private Context context;
//
//    public ImageMessage(Context context, UserPOJO userPOJO){
//        super();
//        this.context = context;
//        this.userPOJO = userPOJO;
//    }
//
//    public UserPOJO getUserPOJO(){
//        return userPOJO;
//    }
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//        System.out.println("ImageMessage.onPreExecute()");
//    }
//
//    @Override
//    protected String doInBackground(Object... params) {
//        System.out.println("ImageMessage.doInBackground()");
//        MessageType requesttype = ObjectCaster.cast(MessageType.class, params[0]);
//        String urlPath = ObjectCaster.cast(String.class, params[1]);
//        String response = null;
//        if(requesttype == MessageType.POST) {
//            Object request = null;
//            if(params.length > 2) {
//                HashMap<String,String> convertedImages = getConvertedImages((HashMap<String, Bitmap>) params[2]);
//                MessagePOJO messagePOJO = new MessagePOJO();
//                messagePOJO.setImageList(convertedImages);
//                messagePOJO.setUserId(userPOJO.getId());
//                request = messagePOJO;
//            }
//            response = httpPostRequest(urlPath, request);
//            return response;
//        } else {
//            response = httpGetRequest(urlPath);
//            return response ;
//        }
//    }
//
//    private HashMap<String,String> getConvertedImages(HashMap<String, Bitmap> bitmapMap){
//        HashMap<String,String> imageList = new HashMap<String, String>();
//        for(Map.Entry entry: bitmapMap.entrySet()){
//            String imageName = (String) entry.getKey();
//            Bitmap fixBitmap = (Bitmap) entry.getValue();
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            fixBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//            byte[] byteArray = byteArrayOutputStream.toByteArray();
//            String ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
//            imageList.put(imageName, ConvertImage);
//        }
//        return imageList;
//    }
//
//    private String httpGetRequest(String urlPath){
//        System.out.println("ImageMessage.httpGetRequest()"+urlPath);
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
//    private String httpPostRequest(String urlPath, Object request) {
//        System.out.println("ImageMessage.httpPostRequest()");
//        // Create a new HttpClient and Post Header
//        HttpClient httpclient = new DefaultHttpClient();
//        HttpPost httppost = new HttpPost(urlPath);
//        httppost.addHeader("content-type", "application/json");
//        try {
//            if(request != null) {
//                Gson gson = new Gson();
//                httppost.setEntity(new StringEntity(gson.toJson(request)));
//            }
//            // Execute HTTP Post Request
//            HttpResponse response = httpclient.execute(httppost);
//            System.out.println("response"+response);
//            String entity = EntityUtils.toString(response.getEntity());
//            return entity;
//        } catch (IOException e) {
//            return null;
//        }
//    }
//
//    @Override
//    protected void onPostExecute(Object result) {
//        System.out.println("ImageMessage.onPostExecute()" + result);
//        if(result!=null){
//        MessagePOJO messagePOJO = ObjectCaster.jSONcast(MessagePOJO.class, (String) result);
//            if(messagePOJO!=null) {
//                String message = messagePOJO.getMessage();
//                //        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//            }
//       }
//    }
//}