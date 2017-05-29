package com.day0405.qq50slidingmenu170405;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.day0405.qq50slidingmenu170405.view.FourSlidingMenu;

/**
 * QQ5.0伸缩式侧滑
 */

public class FourVersionActivity extends AppCompatActivity {
    private FourSlidingMenu mLeftMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);

        //侧滑Layout
        mLeftMenu = (FourSlidingMenu) findViewById(R.id.id_menu);
    }


    public void toggleMenu(View view) {
        mLeftMenu.toggle();
    }
}
