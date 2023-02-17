package com.example.androidhms.util;

import android.os.AsyncTask;

import com.example.androidhms.staff.vo.ChatVO;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class SendPush {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String FCM_KEY = "AAAA5wiiJi4:APA91bGg4wl8hKdQ_hoUEhIHd3-pU2-7sqqjoRmM2ZA77lL4qVE4Tpnz578Zuzvcnfw7V2wKOjdEJbFupyregXKnmo2sqFIHcUbt2sxkUa1AHbHSBozeT3yUnwTzRyLUIIElv0qTn_9z";

    public static void sendPushNotification(String token, ChatVO vo, String key, String title) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    OkHttpClient client = new OkHttpClient();
                    JSONObject json = new JSONObject();
                    JSONObject dataJson = new JSONObject();
                    dataJson.put("content", vo.getContent());
                    dataJson.put("name", vo.getName());
                    dataJson.put("key", key);
                    dataJson.put("title", title);
                    json.put("data", dataJson);
                    json.put("to", token);
                    RequestBody body = RequestBody.create(JSON, json.toString());
                    Request request = new Request.Builder()
                            .header("Authorization", "key=" + FCM_KEY)
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
