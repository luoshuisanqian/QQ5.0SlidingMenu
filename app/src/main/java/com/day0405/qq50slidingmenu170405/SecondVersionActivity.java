package com.day0405.qq50slidingmenu170405;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * 自定义属性变量的侧滑菜单
 */
import com.day0405.qq50slidingmenu170405.view.TwoSlidingMenu;

public class SecondVersionActivity extends AppCompatActivity {
    private TwoSlidingMenu mLeftMenu;
    private RelativeLayout line2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        //侧滑Layout
        mLeftMenu = (TwoSlidingMenu) findViewById(R.id.id_menu);

        //第二个item
        line2 = (RelativeLayout) findViewById(R.id.line2);
        line2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecondVersionActivity.this, ThirdVersionActivity.class));
            }
        });
    }


    public void toggleMenu(View view) {
        mLeftMenu.toggle();
    }
}
