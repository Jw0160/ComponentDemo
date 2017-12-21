package com.common.jwrouterdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.common.router.Router;

public class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickShow(View view){
        Router.build("http://example.com/user/activity?id=123123&status=ok").skipInterceptors().go(this);
    }

    public void onBtn1(View view){
        Bundle lbundler = new Bundle();
        lbundler.putString("ids","asdasdsa");
        lbundler.putString("statu","ok0000000000");
        Router.build("maowo://activity?id=123123&status=okasdasd").with(lbundler).go(this);
    }
}
