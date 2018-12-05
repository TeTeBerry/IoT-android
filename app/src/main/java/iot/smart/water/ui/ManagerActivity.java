package iot.smart.water.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import iot.smart.water.R;
import iot.smart.water.bean.Member;
import iot.smart.water.net.InterfaceRequest;
import iot.smart.water.utils.SPUtil;

public class ManagerActivity extends AppCompatActivity {

    private TextView mNameTv;
    private TextView mTimeTv;
    private TextView mPointsTv;
    private TextView mFlowTv;
    private SwitchCompat mSwitch;
    private Button mLogoutBtn;

    private Dialog mLogoutDialog;

    private Member mMember;
    private boolean mConnectFirebase;
    private DatabaseReference mReference;
    private DatabaseReference mStatus;
    private DatabaseReference mDnt;
    private long mValue;

    public static void startManagerActivity(Activity activity, Member member) {
        Intent intent = new Intent(activity, ManagerActivity.class);
        intent.putExtra("member", member);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        mMember = (Member) getIntent().getSerializableExtra("member");
        if (mMember == null) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }

        mNameTv = findViewById(R.id.nameTv);
        mNameTv.setText(mMember.getMembername());

        mTimeTv = findViewById(R.id.timeTv);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        mTimeTv.setText(sdf.format(new Date(mMember.getCreation_dt())));

        mPointsTv = findViewById(R.id.pointsTv);
        mPointsTv.setText(String.valueOf(mMember.getCredit()));

        mSwitch = findViewById(R.id.switchCompat);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!mConnectFirebase) {
                    Snackbar.make(mLogoutBtn, R.string.connect_wait, Snackbar.LENGTH_LONG).show();
                    mSwitch.setChecked(false);
                } else {
                    mValue = isChecked ? 1 : 0;
                    Log.d("tete", "set mValue: " + mValue);
                    mStatus.setValue(mValue);
                }
            }
        });

        mLogoutBtn = findViewById(R.id.logoutBtn);
        mLogoutBtn.setOnClickListener((View view) -> {
            showLogoutDialog();
        });

        initDatabase();
    }

    private void initDatabase() {
        mReference = FirebaseDatabase.getInstance().getReference();
        //通过键名，获取数据库实例对象的子节点对象
        mStatus = mReference.child("Relay_STATUS");
        mDnt = mReference.child("dnt");

        //注册子第一个节点对象数据变化的监听者对象
        mStatus.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mConnectFirebase = true;
                //数据库数据变化时调用此方法
                try {
                    mValue = dataSnapshot.getValue(Long.class);
                    Log.d("tete", "get firebase relay_status: " + mValue);
                    mSwitch.setChecked(mValue != 0);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("tete", "get firebase relay_status error: " + e.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("tete", "get firebase relay_status onCancelled");
            }
        });

        //注册子第二个节点对象数据变化的监听者对象
        mDnt.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data = dataSnapshot.toString();
                Log.d("tete", "firebase data: " + data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showLogoutDialog() {
        if (mLogoutDialog == null) {
            mLogoutDialog = new Dialog(this, R.style.BottomDialog);
            View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_bottom, null);
            TextView contentTv = contentView.findViewById(R.id.contentTv);
            contentTv.setText(R.string.logout_detail);
            Button confirmBtn = contentView.findViewById(R.id.confirmBtn);
            Button cancelBtn = contentView.findViewById(R.id.cancelBtn);

            confirmBtn.setOnClickListener((View view) -> {
                if (mLogoutDialog != null) {
                    mLogoutDialog.dismiss();
                }
                logout();
            });

            cancelBtn.setOnClickListener((View view) -> {
                if (mLogoutDialog != null) {
                    mLogoutDialog.dismiss();
                }
            });

            mLogoutDialog.setContentView(contentView);
            ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
            layoutParams.width = getResources().getDisplayMetrics().widthPixels;
            contentView.setLayoutParams(layoutParams);

            mLogoutDialog.setCanceledOnTouchOutside(false);
            mLogoutDialog.getWindow().setGravity(Gravity.BOTTOM);
            mLogoutDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        }

        mLogoutDialog.show();
    }

    private void logout() {
        InterfaceRequest.logout();
        SPUtil.clearUserSeesion(this);
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }



}
