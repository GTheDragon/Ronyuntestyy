package com.yy.rongyuntest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;

import com.yy.rongyuntest.R;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class RegisterActivity extends AppCompatActivity {
    private Button bt_sms;
    private TextView tv_phone;
    private String phone;
    private String country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initEvent();
    }


    private void initView() {
        bt_sms = (Button) findViewById(R.id.id_bt_sms);
        tv_phone = (TextView) findViewById(R.id.id_et_phone);
    }

    private void initEvent() {

        bt_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开注册页面
                RegisterPage registerPage = new RegisterPage();
                registerPage.setRegisterCallback(new EventHandler() {
                    public void afterEvent(int event, int result, Object data) {
                        // 解析注册结果
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            //短信验证
                            System.out.println("短信验证");
                            @SuppressWarnings("unchecked")
                            HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                            country = (String) phoneMap.get("country");
                            phone = (String) phoneMap.get("phone");
                            System.out.println("手机号：" + phone);
                            System.out.println("国家：" + country);
                            tv_phone.setText(phone);
                        } else if (result == SMSSDK.RESULT_ERROR) {  //验证错误
                            System.out.println("验证错误");
                            System.out.println("手机号：" + phone);
                            System.out.println("国家：" + country);
                        }
                    }
                });
                registerPage.show(RegisterActivity.this);


            }
        });

    }

}
