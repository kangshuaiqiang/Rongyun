package demo.ksq.com.rongyun;


import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;

import java.io.IOException;
import java.security.MessageDigest;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 黑白 on 2017/11/16.
 */

public class GetToken {
    String str;

    android.os.Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Token obj = (Token) msg.obj;
            str = obj.getToken();
            getStr.getstr(str);
        }
    };

    private static GetStr getStr;

    interface GetStr {
        void getstr(String str);
    }

    public void initInter(GetStr getStr) {
        this.getStr = getStr;
    }

    public void GetRongCloudToken(String username) {
//        StringBuffer res = new StringBuffer();
        String url = "https://api.cn.ronghub.com/user/getToken.json";
        String App_Key = "lmxuhwagl02qd"; //开发者平台分配的 App Key。
        String App_Secret = "m2K79t0YozzbA";
        String Timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳，从 1970 年 1 月 1 日 0 点 0 分 0 秒开始到现在的秒数。
        String Nonce = String.valueOf(Math.floor(Math.random() * 1000000));//随机数，无长度限制。
        String Signature = sha1(App_Secret + Nonce + Timestamp);//数据签名。
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        final Gson gson = new Gson();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("userId", "1234");
        builder.add("name", "123");
        builder.add("portraitUri", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3958048636,3647327477&fm=27&gp=0.jpg");

        /*builder.add("App-Key", App_Key);
        builder.add("Timestamp", Timestamp);
        builder.add("Nonce", Nonce);
        builder.add("Signature", Signature);*/
//        builder.add("Content-Type", "application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(builder.build())
                .addHeader("App-Key", App_Key)
                .addHeader("Timestamp", Timestamp)
                .addHeader("Nonce", Nonce)
                .addHeader("Signature", Signature)
               /* .addHeader("userId", "123")
                .addHeader("name", "123")
                .addHeader("portraitUri", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3958048636,3647327477&fm=27&gp=0.jpg")*/
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Token token = gson.fromJson(string, Token.class);
                Message message = handler.obtainMessage();
                message.obj = token;
                handler.sendMessage(message);
            }
        });
/*
//        Logger.class(Signature);
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("App-Key", App_Key);
        httpPost.setHeader("Timestamp", Timestamp);
        httpPost.setHeader("Nonce", Nonce);
        httpPost.setHeader("Signature", Signature);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
        nameValuePair.add(new BasicNameValuePair("userId",username));
        HttpResponse httpResponse = null;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,"utf-8"));
            httpResponse = httpClient.execute(httpPost);
            BufferedReader br = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            String line = null;
            while ((line = br.readLine()) != null) {
                res.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        UserRespone userRespone = JSON.parseObject(res.toString(), UserRespone.class);
        Logger.i(userRespone.getCode()+"");
*/

    }

    //SHA1加密//http://www.rongcloud.cn/docs/server.html#通用_API_接口签名规则
    private static String sha1(String data) {
        StringBuffer buf = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(data.getBytes());
            byte[] bits = md.digest();
            for (int i = 0; i < bits.length; i++) {
                int a = bits[i];
                if (a < 0) a += 256;
                if (a < 16) buf.append("0");
                buf.append(Integer.toHexString(a));
            }
        } catch (Exception e) {

        }
        return buf.toString();
    }
}
