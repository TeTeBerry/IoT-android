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
import iot.smart.water.utils.HashUtil;
import iot.smart.water.utils.SPUtil;

public class RegisterActivity extends AppCompatActivity {

    private EditText mNameEt;
    private EditText mRoomEt;
    private EditText mMailEt;
    private EditText mTelEt;
    private TextInputEditText mPwdEt;
    private TextInputEditText mConfirmEt;
    private Button mRegisterBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mNameEt = findViewById(R.id.nameEt);
        mRoomEt = findViewById(R.id.roomEt);
        mMailEt = findViewById(R.id.mailEt);
        mTelEt = findViewById(R.id.telEt);
        mPwdEt = findViewById(R.id.pwdEt);
        mConfirmEt = findViewById(R.id.confirmEt);
        mRegisterBtn = findViewById(R.id.registerBtn);

        mRegisterBtn.setOnClickListener((View view) -> {

            CommUtil.hideSoftInput(this);

            String userName = mNameEt.getText().toString().trim();
            String userRoom = mRoomEt.getText().toString().trim();
            String userMail = mMailEt.getText().toString().trim();
            String userTel = mTelEt.getText().toString().trim();
            String password = mPwdEt.getText().toString().trim();
            String confirm = mConfirmEt.getText().toString().trim();

            // check username null
            if (TextUtils.isEmpty(userName)) {
                Snackbar.make(mRegisterBtn, "username is null", Snackbar.LENGTH_SHORT).show();
                return;
            }

            // check user room null
            if (TextUtils.isEmpty(userRoom)) {
                Snackbar.make(mRegisterBtn, "room is null", Snackbar.LENGTH_SHORT).show();
                return;
            }

            // check user mail null
            if (TextUtils.isEmpty(userMail)) {
                Snackbar.make(mRegisterBtn, "mail is null", Snackbar.LENGTH_SHORT).show();
                return;
            }

            // TODO check valid mail

            // check user tel null
            if (TextUtils.isEmpty(userTel)) {
                Snackbar.make(mRegisterBtn, "tel is null", Snackbar.LENGTH_SHORT).show();
                return;
            }

            // TODO check valid tel

            // check password null
            if (TextUtils.isEmpty(password)) {
                Snackbar.make(mRegisterBtn, "password is null", Snackbar.LENGTH_SHORT).show();
                return;
            }

            // check password confirm null
            if (TextUtils.isEmpty(confirm)) {
                Snackbar.make(mRegisterBtn, "please confirm your password", Snackbar.LENGTH_SHORT).show();
                return;
            }

            // check password consistent
            if (!password.equals(confirm)) {
                Snackbar.make(mRegisterBtn, "inconsistent password!", Snackbar.LENGTH_SHORT).show();
                return;
            }

            Member member = new Member();
            member.setMembername(userName);
            member.setRoom(userRoom);
            member.setEmail(userMail);
            member.setTel(userTel);
            member.setPassword(HashUtil.get(password));
            InterfaceRequest.register(member, Member.class, new RequestCallback<Member>() {
                @Override
                public void onSuccess(Member data) {
                    if (data != null) {
                        Log.d("tete", "register success:\n" + data.toString());
                        data.setPassword(HashUtil.get(password));
                        SPUtil.saveUser(RegisterActivity.this, data);
                        Snackbar.make(mRegisterBtn, R.string.register_success, Snackbar.LENGTH_SHORT).show();

                        Intent intent = new Intent();
                        intent.putExtra("member", data);
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        Snackbar.make(mRegisterBtn, "register fail: return null", Snackbar.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailed(String msg) {
                    Log.d("tete", "fail msg => " + msg);
                    Snackbar.make(mRegisterBtn, "register fail: " + msg, Snackbar.LENGTH_SHORT).show();
                }
            });

        });
    }
}
