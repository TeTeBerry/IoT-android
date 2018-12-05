package iot.smart.water.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import iot.smart.water.R;
import iot.smart.water.bean.Member;
import iot.smart.water.net.InterfaceRequest;
import iot.smart.water.net.http.RequestCallback;
import iot.smart.water.utils.CommUtil;
import iot.smart.water.utils.SPUtil;

public class LoginActivity extends AppCompatActivity {

    private static final String DEFAULT_USERNAME = "admin";
    private static final String DEFAULT_PASSWORD = "123456";

    private EditText mNameEt;
    private TextInputEditText mPwdEt;
    private Button mLoginBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mNameEt = findViewById(R.id.nameEt);
        mPwdEt = findViewById(R.id.pwdEt);
        mLoginBtn = findViewById(R.id.loginBtn);

        mNameEt.setText(SPUtil.getUserName(this));

        mLoginBtn.setOnClickListener((View view) -> {

            CommUtil.hideSoftInput(this);

            String userName = mNameEt.getText().toString().trim();
            String password = mPwdEt.getText().toString().trim();

            // check username null
            if (TextUtils.isEmpty(userName)) {
                Snackbar.make(mLoginBtn, "username is null", Snackbar.LENGTH_SHORT).show();
                return;
            }

            // check password null
            if (TextUtils.isEmpty(password)) {
                Snackbar.make(mLoginBtn, "password is null", Snackbar.LENGTH_SHORT).show();
                return;
            }

            // just for test
//            if (DEFAULT_USERNAME.equals(userName) && DEFAULT_PASSWORD.equals(password)) {
//                Snackbar.make(mLoginBtn, R.string.login_success, Snackbar.LENGTH_SHORT).show();
//                startActivity(new Intent(this, ManagerActivity.class));
//                finish();
//            }

            Member member = new Member();
            member.setMembername(userName);
            member.setPassword(password);
            InterfaceRequest.login(member, Member.class, new RequestCallback<Member>() {
                @Override
                public void onSuccess(Member data) {
                    if (data != null && data.getMembername() != null) {
                        Log.d("tete", "username: " + data.getMembername());

                        SPUtil.saveUser(LoginActivity.this, data);
                        Snackbar.make(mLoginBtn, R.string.login_success, Snackbar.LENGTH_SHORT).show();

                        Intent intent = new Intent();
                        intent.putExtra("member", data);
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        Snackbar.make(mLoginBtn, "login fail: return message null", Snackbar.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailed(String msg) {
                    Log.d("tete", "fail msg => " + msg);
                    Snackbar.make(mLoginBtn, "login fail: " + msg, Snackbar.LENGTH_SHORT).show();
                }
            });

        });
    }
}
