package com.common.jwrouterdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.common.annotation.InjectParam;
import com.common.annotation.Route;
import com.common.common_base.utils.KLog;
import com.common.jwrouterdemo.R;
import com.common.router.Router;

@Route(value = "http://example.com/user/activity", interceptors = "MyInterceptor")
public class Main2Activity extends AppCompatActivity{
    @InjectParam
    String id = "0000";
    @InjectParam(key = "status")
    String sts = "default";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Router.injectParams(this);
        Bundle mExtras = getIntent().getExtras();
        id = mExtras.getString("id", id);
        KLog.e("id:" + id + ", status:" + sts);
    }
}
