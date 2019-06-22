package com.carolsum.jingle.net;

import android.util.Log;

import com.carolsum.jingle.helpers.ImageTypeHelper;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpClient {

    private String baseUrl = "http://118.89.20.188:3000";
    private String uploadImageUrl = "http://118.89.20.188/upload.php";
    private String getPictureBaseUrl = "http://118.89.20.188/";

    public static final MediaType jsonMediaType = MediaType.get("application/json; charset=utf-8");
    public static final MediaType imageMediaType = MediaType.parse("image/*; charset=utf-8");

    // gson 实例
    public static final Gson gson = new Gson();

    private final HashMap<String, List<Cookie>> cookieStore;
    OkHttpClient client;

    // 单例
    private static HttpClient instance = new HttpClient();

    private HttpClient() {
        cookieStore = new HashMap<>();
        client = new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url.host(), cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url.host());
                        return cookies != null ? cookies : new ArrayList<>();
                    }
                })
                .build();
    }

    public static HttpClient getInstance() {
        return instance;
    }

    public void clearCookie() {
        cookieStore.clear();
    }

    /**
     * get 请求
     * @param url
     * @throws IOException
     */
    public void get(String url, final Callback callback) throws IOException {
        Request request = new Request.Builder().url(baseUrl + url).build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * post 请求
     * @param url
     * @param json
     * @throws IOException
     */
    public void post(String url, String json, final Callback callback) throws IOException {
        RequestBody body = RequestBody.create(jsonMediaType, json);
        Request request = new Request.Builder()
                .url(baseUrl + url)
                .post(body)
//      .addHeader("Connection", "close")
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void put(String url, RequestBody body, final Callback callback) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 上传图片示例 ！！接口只是示例 ！！
     * @param uploadUrl
     * @param localPath
     * @return
     * @throws IOException
     */
    public void putImg(String uploadUrl, String localPath) throws IOException {
        File file = new File(localPath);
        RequestBody body = RequestBody.create(imageMediaType, file);
        put("/upload", body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("response", response.body().string());
            }
        });
    }

    /**
     * 同步上传图片
     * @param path 本地图片路径
     * @return
     */
    public String upload(String path) {
        File file = new File(path);
        if (file != null) {
            Log.d("HTTP: ", file.getAbsolutePath());
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("img", file.getName(), RequestBody.create(getMediaType(path), file))
                    .build();
            Request request = new Request.Builder()
                    .url(uploadImageUrl)
                    .post(requestBody)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * 异步上传图片
     */
    public void uploadAsync(String path, Callback callback) {
        File file = new File(path);
        if (file != null) {
            Log.d("HTTP: ", file.getAbsolutePath());
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("img", file.getName(), RequestBody.create(getMediaType(path), file))
                    .build();
            Request request = new Request.Builder()
                    .url(uploadImageUrl)
                    .post(requestBody)
                    .build();
            client.newCall(request).enqueue(callback);
        }
    }

    private MediaType getMediaType(String path) {
        return MediaType.parse(ImageTypeHelper.getMIMEType(path)+"; charset=utf-8");
    }
}
