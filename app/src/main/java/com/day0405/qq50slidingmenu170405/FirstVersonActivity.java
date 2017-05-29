package com.day0405.qq50slidingmenu170405;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

/**
 * 第一个版本的侧滑菜单 ，没有自定义属性的
 */

public class FirstVersonActivity extends AppCompatActivity {

    private RelativeLayout line1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 隐藏ActionBar
         */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_first_verson);

        //第一个item
        line1 = (RelativeLayout) findViewById(R.id.line1);
        line1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstVersonActivity.this, SecondVersionActivity.class));
            }
        });
    }
}
