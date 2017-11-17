package demo.ksq.com.rongyun;

import android.app.Application;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * Created by 黑白 on 2017/11/16.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RongIM.init(this);

//        GetToken.GetRongCloudToken("123");
        GetToken getToken = new GetToken();
        getToken.initInter(new GetToken.GetStr() {
            @Override
            public void getstr(String str) {
                RongIM.connect(str, new RongIMClient.ConnectCallback() {
                    @Override
                    public void onTokenIncorrect() {

                    }

                    @Override
                    public void onSuccess(String s) {

                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {

                    }
                });
            }
        });
        getToken.GetRongCloudToken("123");

        String token = "tuJ2sRyMb/9mGxquHubA4i4r3fQg7tbZo/ZgyUELUmI9CiNM5TZr6fsBbgYihG+gVQW4l+Q0sK6gxW7B/id8UA==";


    }
}
