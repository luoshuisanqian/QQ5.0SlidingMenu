package com.day0405.qq50slidingmenu170405;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.day0405.qq50slidingmenu170405.view.ThirdSlidingMenu;

/**
 * 抽屉式侧滑菜单
 */

public class ThirdVersionActivity extends AppCompatActivity {
    private ThirdSlidingMenu mLeftMenu;
    private RelativeLayout line3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        //侧滑Layout
        mLeftMenu = (ThirdSlidingMenu) findViewById(R.id.id_menu);

        //第三个item
        line3 = (RelativeLayout) findViewById(R.id.line3);
        line3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThirdVersionActivity.this, FourVersionActivity.class));
            }
        });
    }


    public void toggleMenu(View view) {
        mLeftMenu.toggle();
    }
}
