package com.day0405.qq50slidingmenu170405.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by huangming on 2017/5/28.
*/

public class FirSidingMenu extends HorizontalScrollView {
    private LinearLayout mWapper;//这个是固定的，因为HorizontalScrollView的第一个控件肯定是LinearLayout
    private ViewGroup mMenu; //因为我们的菜单栏肯定是ViewGroup的子类，不管它是LinearLayout还是Relativilayout
    private ViewGroup mContent; //内容区域

    //屏幕宽度，可以在构造方法中获取
    private int mScreenWidth;
    /**
     * menu与屏幕右侧的距离, 单位dp
     *
     * 然后再构造方法中转化成象素值
     */
    private int mMenuRightPadding = 50;

    /**
     * 由于onMeasure可能会被多次调用，这里设置一个boolean值
     */
    private boolean once = false;
    /**
     * Menu的宽度，在onLayout设置view的摆放位置时需要用到，滚动条的宽度
     * ， 内容区域的宽度就不用设置出来了，因为它就是屏幕的宽度
     */
    private int mMenuWidth;
    /**
     * 未使用自定义属性时，只会调用两个参数的方法
     * @param context
     * @param attrs
     */
    public FirSidingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        /**
         * 获取屏幕宽度
         */
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);//把返回值强转为WindowManager
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        /**
         * outMetrics经过getMetrics方法以后，它的宽高就给赋上值了
         */
        mScreenWidth = outMetrics.widthPixels;

        /**
         * 将leftMenu距离屏幕右侧的距离dp转为像素
         */
        mMenuRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());
    }

    /**
     * 设置子view的宽和高，设置自己的宽和高
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * onMeasure方法可以被多次调用，所以在这里设置boolean值，
         */

        if (!once) {
            //1.拿到HorizontalScroll里面的第一个元素mWapper, 它肯定是内部第一个元素
            mWapper = (LinearLayout) getChildAt(0);
            //2。通过mWapper拿到leftMenu侧滑菜单，它肯定是mWapper里面的第一个元素
            mMenu = (ViewGroup) mWapper.getChildAt(0);
            //3.拿到内容区域
            mContent = (ViewGroup) mWapper.getChildAt(1);
            //4.设置侧滑菜单leftMenu的宽度，高度默认是Match_Parent
            mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
            //5.设置内容区域mContent的宽度 == 屏幕的宽度
            mContent.getLayoutParams().width = mScreenWidth;

            /**
             * 最后设置自己的宽和高, == mMenu.getLayoutParams().width + mScreenWidth
             *  mWapper.getLayoutParams().width =mMenu.getLayoutParams().width + mScreenWidth;
             *
             *  但由于，mMenu和mContent都在mWapper里面且布局为LinearLayout width为match_match_parent自动填满
             *  所以，可以不用设置，
             */
        once = true;
        }
    }

    /**
     *当onMeasure测量之后 ，决定子放置的位置.
     */
    //我们需要显示的效果是，Content显示内容区域，mMenu缩到左边
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //1.通过设置偏移量，将menu隐藏
        /**
         * 为防止多次调用，加个判断，判断当前页面是否改变，
         * 改变则调用下面该去
         */
        if (changed) {
            this.scrollTo(mMenuWidth, 0);
        }

    }

    /**
     * 决定内部View的移动状态
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        //通过action的值来判断是按下、抬起、还是移动
        switch (action) {
            /**
             * 因为Horizental不做任何处理也能把Menu滑出来，和隐藏，
             * 所以这里唯一关注的是MotionEvent_UP,判断Menu移出来的
             * 宽度是否大于Menu的1/2
             */
            case MotionEvent.ACTION_UP:
                //scrollX是隐藏在左边的宽度,是整个左边Menu的宽度

                //并不是显示的宽度,而是隐藏在左侧的宽度
                int scrollX = (int) getScrollX();
                if(scrollX >= mMenuWidth / 2) {//继续隐藏

                    //1。不显示侧滑Menu, 因为 this.scrollTo(x, y);是瞬间完成的动作
                    //下面可以加个平滑的效果
                    this.smoothScrollTo(mMenuWidth, 0);

                    //此时菜单隐藏了
//                    isOpen = false;
                } else {//显示侧滑菜单
                    this.smoothScrollTo(0, 0);

                    //此时显示了
//                    isOpen = true;
                }

                return true;

        }
        return super.onTouchEvent(ev);
    }
}
