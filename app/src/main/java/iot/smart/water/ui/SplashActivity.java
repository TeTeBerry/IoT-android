package iot.smart.water.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;

import iot.smart.water.bean.Member;
import iot.smart.water.net.InterfaceRequest;
import iot.smart.water.net.http.RequestCallback;
import iot.smart.water.utils.SPUtil;

public class SplashActivity extends AppCompatActivity {

    // choose to jump to the original page or new page
    private boolean origin = false;
    // choose to jump to scan page
    private boolean justScan = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // go to homepage after 2s
//        new Handler().postDelayed(() -> {
//            if (origin) {
//                startActivity(new Intent(this, MainActivity.class));
//            } else {
//                if (justScan) {
//                    startActivity(new Intent(this, ManagerActivity.class));
//                } else {
//                    startActivity(new Intent(this, HomeActivity.class));
//                }
//            }
//            finish();
//        }, 2000);

        new Handler().postDelayed(this::autoLogin, 2000);
    }

    private void autoLogin() {
        Member member = new Member();
        Pair<String, String> pair = SPUtil.getUserInfo(this);
        member.setMembername(pair.first);
        member.setPassword(pair.second);
        InterfaceRequest.login(member, Member.class, new RequestCallback<Member>() {
            @Override
            public void onSuccess(Member data) {
                if (data != null) {
                    Log.d("tete", "auto login success\n" + data.toString());
                    data.setPassword(pair.second);
                    SPUtil.saveUser(SplashActivity.this, data);
                    ManagerActivity.startManagerActivity(SplashActivity.this, data);
                } else {
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                }
                finish();
            }

            @Override
            public void onFailed(String msg) {
                Log.d("tete", "fail msg => " + msg);
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                finish();
            }
        });
    }
}
