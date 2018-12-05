package iot.smart.water.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.king.zxing.CaptureActivity;
import com.king.zxing.Intents;

import iot.smart.water.R;
import iot.smart.water.bean.Member;
import iot.smart.water.net.InterfaceRequest;
import iot.smart.water.net.http.RequestCallback;
import iot.smart.water.utils.SPUtil;

public class HomeActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_LOGIN = 10001;
    private static final int REQUEST_CODE_REGISTER = 10002;

    private static final int REQUEST_CODE_SCAN = 1000;
    private static final int REQUEST_PERMISSION = 2000;

    private Button mLoginBtn;
    private Button mScanBtn;
    private TextView mRegisterTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mLoginBtn = findViewById(R.id.loginBtn);
        mScanBtn = findViewById(R.id.scanBtn);
        mRegisterTv = findViewById(R.id.registerTv);

        // register click listener
        mLoginBtn.setOnClickListener((View view) -> startActivityForResult(new Intent(this, LoginActivity.class), REQUEST_CODE_LOGIN));
        mRegisterTv.setOnClickListener((View view) -> startActivityForResult(new Intent(this, RegisterActivity.class), REQUEST_CODE_REGISTER));
        mScanBtn.setOnClickListener((View view) -> {
            // use camera need permission
            if (hasUseCameraPermission()) {
                // jump to scan qrcode page and get return data
                scanQRcode();
            } else {
                requestUseCameraPermission();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN) {
            if (null != data) {
                // get return data
                String token = data.getStringExtra(Intents.Scan.RESULT);
                if (!TextUtils.isEmpty(token)) {
                    // scanning QR code success
                    Log.d("tete", "qrcode info: " + token);
                    login(token);
                } else {
                    // scanning QR code fail
                    Snackbar.make(mScanBtn, "scanning QR code failed", Snackbar.LENGTH_LONG).show();
                }
            } else {
                // scanning QR code fail
                Snackbar.make(mScanBtn, "scanning QR code failed", Snackbar.LENGTH_LONG).show();
            }
        } else if (data != null && (requestCode == REQUEST_CODE_LOGIN || requestCode == REQUEST_CODE_REGISTER)) {
            Member member = (Member) data.getSerializableExtra("member");
            ManagerActivity.startManagerActivity(HomeActivity.this, member);
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // agree to use camera
                scanQRcode();
            } else {
                Snackbar.make(mScanBtn, "please enable camera permission", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private boolean hasUseCameraPermission() {
        return ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestUseCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION);
    }

    private void scanQRcode() {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    private void login(String token) {
        Member member = new Member();
        member.setMembername(SPUtil.getUserName(this));
        member.setToken(token);
        InterfaceRequest.login(member, Member.class, new RequestCallback<Member>() {
            @Override
            public void onSuccess(Member data) {
                if (data != null && data.getMembername() != null) {
                    Log.d("tete", "username: " + data.getMembername());

                    SPUtil.saveUser(HomeActivity.this, data);
                    Snackbar.make(mLoginBtn, R.string.login_success, Snackbar.LENGTH_SHORT).show();

                    ManagerActivity.startManagerActivity(HomeActivity.this, data);
                    finish();
                } else {
                    Snackbar.make(mLoginBtn, "login fail: please enter username and password to login", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(String msg) {
                Log.d("tete", "fail msg => " + msg);
                Snackbar.make(mLoginBtn, "login fail: " + msg, Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
