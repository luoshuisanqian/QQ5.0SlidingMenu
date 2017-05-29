package com.day0405.qq50slidingmenu170405;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.day0405.qq50slidingmenu170405.fragment.ContentFragment;

import static com.day0405.qq50slidingmenu170405.R.id.drawer_layout;

/**
 * 2013年谷歌IO大会推荐使用DrawerLayout代替SlidingMenu
 */
public class DrawerLayoutActivity extends AppCompatActivity {
    private Button button;//切换抽屉菜单按钮
    private DrawerLayout mDrawerLayout;//根视图

    private LinearLayout left_linear;//抽屉的父视图
    private ListView mDrawerList;//抽屉视图的listview

    //数据集
    private String[] menuList= {"NO.1：没有自定义属性SlidingMenu", "N02：有自定义属性有SlidingMenu",
                                "NO.3：抽屉式侧滑", "NO.4：QQ5.0伸缩式侧滑"};
    private ArrayAdapter<String> adapter;//

    private String mTitle;//获得应用最初的标题


    /**设置抽屉侧滑菜单监听事件
     * 1。mDrawerLayout.setDrawerListener(DrawerLayout.DrawerListener);
     * 2.ActionBarDrawerToggle是DrawerLayout.DrawerListener的具体实现类
     *    1。）改变android.R.id.home图标（构造方法）
     *    2。）Drawer拉出、隐藏、带有android.R.id.home动画效果（syncState()）
     *    3.）监听Drawer拉出、隐藏事件
     * 3。覆写ActionBarDrawerToggle的onDrawerOpend()和onDrawerClose()以监听抽屉拉出或隐藏事件
     * 4。覆写activity的onPostCreate()和onConfigurationChanged()方法
     */

    private ActionBarDrawerToggle mDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_layout);
        initViews();

    }

    private void initViews() {

        //根视图
        mDrawerLayout = (DrawerLayout) findViewById(drawer_layout);
        //抽屉的父视图
        left_linear = (LinearLayout) findViewById(R.id.left_linear);
        //listView
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(left_linear);
                if (isDrawerOpen) {//如果打开状态则关闭
                    mDrawerLayout.closeDrawer(left_linear);
                } else {//否则打开
                    mDrawerLayout.openDrawer(left_linear);
                }
            }
        });
        /**
         * 初始化adapter
         */
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuList);
        mDrawerList.setAdapter(adapter);


        /**
         * 初始化ActionBarDrawerToggle对象
         */
//        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close);


        mDrawerLayout.setDrawerListener(new MyListener());




        /**
         * 设置listView的item的点击事件，
         * 动态插入一个Fragment
         */
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //1.动态插入一个fragment到FrameLayout中
                ContentFragment contentFragment = new ContentFragment();
                //2.为了区别于每一个fragment都不一样，所以让当前的fragment携带相应的参数
                Bundle args = new Bundle();
                args.putString("text", menuList[position]);
                //让contetn携带上这个参数
                contentFragment.setArguments(args);


                /**
                 * 3.一个fragment创建好了，还需要一个fragmetnManager
                 */
                FragmentManager fragmentManager = getSupportFragmentManager();
                //4.用fragmentManager为当前gragment开启一个事物，，，，
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //5.因为要让当前的fragment替换掉原来的， 第一个参数指的是，把当前的fragment填充到哪个视图当中
                fragmentTransaction.replace(R.id.contetn_frame, contentFragment);
                //6。最后，当为一个fragment开启事物的时候，一定要点提交.commit()， 让当前的事物生效
                fragmentTransaction.commit();


                /**
                 * 当点击完相应的菜单项， 插入了一个新的fragment之后，当前的导航菜单就需要隐藏了
                 */
//                mDrawerLayout.closeDrawer(mDrawerList);
                mDrawerLayout.closeDrawer(left_linear);

            }
        });
    }


    /**
     * DrawerLayout的打开，关闭，监听事件
     */
    class MyListener implements DrawerLayout.DrawerListener {

        /**
         * 当抽屉滑动状态改变的时候被调用
         * 状态值是STATE_IDLE（闲置--0）, STATE_DRAGGING（拖拽的--1）, STATE_SETTLING（固定--2）中之一。
         * 抽屉打开的时候，点击抽屉，drawer的状态就会变成STATE_DRAGGING，然后变成STATE_IDLE
         */
        @Override
        public void onDrawerStateChanged(int newState) {

        }
        /**
         * 当抽屉被滑动的时候调用此方法
         * arg1 表示 滑动的幅度（0-1）
         */
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {

        }

        /**
         * 当一个抽屉被完全打开的时候被调用
         */
        @Override
        public void onDrawerOpened(View drawerView) {
            Toast.makeText(DrawerLayoutActivity.this, "完全打开", Toast.LENGTH_SHORT).show();

            /*getActionBar().setTitle("请选择");
            //重绘Actionbar上面的菜单 项
            invalidateOptionsMenu();当调用这个方法的时候，
            系统会自动调用一个方法 Call onPrepareOptionsMenu
            */
        }


        /**
         * 当一个抽屉完全关闭的时候调用此方法
         */
        @Override
        public void onDrawerClosed(View drawerView) {
            Toast.makeText(DrawerLayoutActivity.this, "完全关闭 ", Toast.LENGTH_SHORT).show();
            /*getActionBar().setTitle(mTitle);
            //重绘Actionbar上面的菜单 项
            invalidateOptionsMenu();*/
        }

    }


    /*getActionBar().setTitle("请选择");
            //重绘Actionbar上面的菜单 项
            invalidateOptionsMenu();当调用这个方法的时候，
            系统会自动调用一个方法 Call onPrepareOptionsMenu
            */
/*    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //1.获取抽屉菜单的打开状态
        boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(left_linear);
        //2.当抽屉打开，右上角图标显示
//        menu.findItem(R.id.action_search).setVisible(!isDrawerOpen)

        return super.onPrepareOptionsMenu(menu);
    }*/
}
