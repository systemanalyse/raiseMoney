package com.carolsum.jingle.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.carolsum.jingle.helpers.ImageTypeHelper;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private static String COOKIE_PREFS = "Cookie_";
    public static String getPictureBaseUrl = "http://118.89.20.188/";

    public static final MediaType jsonMediaType = MediaType.get("application/json; charset=utf-8");
    public static final MediaType imageMediaType = MediaType.parse("image/*; charset=utf-8");

    // gson 实例
    public static final Gson gson = new Gson();

    private final HashMap<String, List<Cookie>> cookieStore;
    OkHttpClient client;

    // 单例
    private static HttpClient instance = new HttpClient();

    private Context context;

    public void setContext(Context context) {
        this.context = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences(COOKIE_PREFS, Context.MODE_PRIVATE);
        String urlStr = sharedPreferences.getString("url","");
        String cookieStr = sharedPreferences.getString("set-cookie", "");
        if (!urlStr.equals("") && !cookieStr.equals("")) {
            HttpUrl url = HttpUrl.parse(urlStr);
            Cookie cookie = Cookie.parse(url, cookieStr);
            List<Cookie> cookies = new ArrayList<>();
            cookies.add(cookie);
            cookieStore.put(url.host(), cookies);
        }
    }

    private HttpClient() {
        cookieStore = new HashMap<>();
        client = new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        String host = url.host();
                        cookieStore.put(host, cookies);

                        // 将 userid 存到 sharePreferences 中
                        SharedPreferences.Editor editor = context.getSharedPreferences(COOKIE_PREFS, Context.MODE_PRIVATE).edit();
                        editor.putString("set-cookie", cookies.get(0).toString());
                        editor.putString("url", url.toString());
                        editor.commit();

                        if (cookies != null) {
                            for(Cookie cookie : cookies) {
                                Log.d("save cookie", cookie.toString());
                            }
                            Log.d("save finished", "");
                        }
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url.host());
                        if (cookies != null) {
                            for(Cookie cookie : cookies) {
                                Log.d("load cookie", cookie.toString());
                            }
                            Log.d("load finished", "");
                        }
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
        SharedPreferences.Editor editor = context.getSharedPreferences(COOKIE_PREFS, Context.MODE_PRIVATE).edit();
        editor.remove("url");
        editor.remove("set-cookie");
        editor.commit();
    }

    /**
     * get 请求
     * @param url
     * @throws IOException
     */
    public void getAsync(String url, final Callback callback) throws IOException {
        Request request = new Request.Builder().url(baseUrl + url).build();
        client.newCall(request).enqueue(callback);
    }

    public String get(String url) throws IOException {
        Request request = new Request.Builder().url(baseUrl + url).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
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
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void put(String url, String json, final Callback callback) throws IOException {
        RequestBody body = RequestBody.create(jsonMediaType, json);
        Request request = new Request.Builder()
                .url(baseUrl + url)
                .put(body)
                .build();
        client.newCall(request).enqueue(callback);
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
