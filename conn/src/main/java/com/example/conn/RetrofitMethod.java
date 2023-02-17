package com.example.conn;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitMethod {
    String TAG = "로그";

    private HashMap<String , Object> params = new HashMap<>();
    public RetrofitMethod setParams(String key , Object value){
        params.put(key,value);
        return this;
    }

    public void sendPost(String url , CallBackResult callback){
        ApiInterface apiInterface = new ApiClient().getApiClient().create(ApiInterface.class);
        Call<String> apiTest =  apiInterface.connPost(url , params);
        apiTest.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    callback.result(true , response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                try {
                    callback.result(false , "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                t.printStackTrace();
            }
        });
    }

    public void sendPostFile(String url , String filePath , CallBackResult callback){
        ApiInterface apiInterface = new ApiClient().getApiClient().create(ApiInterface.class);
        Call<String> apiTest =  apiInterface.connFilePost(url , stringToRequest(),pathToPartFile(filePath));
        apiTest.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    callback.result(true , response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                try {
                    callback.result(false , "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                t.printStackTrace();
            }
        });
    }

    public MultipartBody.Part pathToPartFile(String path) {
        if (path != null) {
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpeg"), new File(path));
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "img.png", fileBody);
            return filePart;
        }
        return null;
    }


    // Multipart 로 데이터도 동시에 보내서 요청
    public RequestBody stringToRequest() {
        RequestBody data = null;
        if (!params.isEmpty()){
            data = RequestBody.create(
                    MediaType.parse("multipart/form-data"), new Gson().toJson(
                            params.get("param")
                    )
            );
        }
        return data;
    }

    public void sendGet(String url , CallBackResult callback){
        ApiInterface apiInterface = new ApiClient().getApiClient().create(ApiInterface.class);
        Call<String> apiTest =  apiInterface.connGet(url , params);
        apiTest.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    callback.result(true , response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                try {
                    callback.result(false , "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                t.printStackTrace();
            }
        });
    }

    //1.정의
    public interface CallBackResult{
        // 메인에서 CommonMethod를 통해서 Callback<String> 인터페이스를 넘겨서 실행할때마다
        // 두개의 메소드가 오버라이드가 됨. ( onResponse , onFailure )==> 하나로 합치고싶음.
        public void result(boolean isResult , String data) throws Exception;
    }

    // 갤러리에서 가져온 이미지 패스가 URI형태로 실제 물리적인 주소가 x File로 만들 수 없음
    // ↓ 해당하는 메소드는 URI를 통해 실제 이미지 물리적 주소를 얻어오는 메소드
    //
    public String getRealPath(Uri uri, Context context) {
        String rtn = null; //리턴용
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int colum_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            rtn = cursor.getString(colum_index);
        }
        cursor.close();
        return rtn;
    }

    public File createFile(Context context) {
        String fileName = "LastProject" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File rtnFile = null; //IO 입출력 Exception이 발생하기 때문에 try{}catch 블럭킹이 생김

        try {
            rtnFile = File.createTempFile(fileName, ".jpg", storageDir);
        }catch (IOException e) {
            e.printStackTrace();
        }

        return rtnFile;
    }



}
