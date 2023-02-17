package com.example.androidhms.customer.common;

import android.os.AsyncTask;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class SendNumber {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String fcmKey = "AAAA5wiiJi4:APA91bGg4wl8hKdQ_hoUEhIHd3-pU2-7sqqjoRmM2ZA77lL4qVE4Tpnz578Zuzvcnfw7V2wKOjdEJbFupyregXKnmo2sqFIHcUbt2sxkUa1AHbHSBozeT3yUnwTzRyLUIIElv0qTn_9z";

    public static void sendPushNotification(String token) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    OkHttpClient client = new OkHttpClient();
                    JSONObject json = new JSONObject();
                    JSONObject dataJson = new JSONObject();
                    dataJson.put("content", "진료실로 입장해주세요");
                    dataJson.put("title", "모바일 번호표");
                    json.put("data", dataJson);
                    json.put("to", token);
                    RequestBody body = RequestBody.create(JSON, json.toString());
                    Request request = new Request.Builder()
                            .header("Authorization", "key=" + fcmKey)
                            .url("https://fcm.googleapis.com/fcm/send")
                            .post(body)
                            .build();
                    client.newCall(request).execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

}
