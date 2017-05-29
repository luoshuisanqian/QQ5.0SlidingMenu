package com.day0405.qq50slidingmenu170405.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.day0405.qq50slidingmenu170405.R;

/**
 * Created by huangming on 2017/4/5.
 */

public class TwoSlidingMenu extends HorizontalScrollView {

    private LinearLayout mWrapper;//这个是固定的，意思就是说HorizontalScrollView内部有个LinearLayout
    private ViewGroup mMenu; //因为我们的菜单栏肯定是ViewGroup的子类，不管它是LinearLayout还是Relativilayout
    private ViewGroup mContent; //内容区域

    //屏幕宽度，可以在方法中获取
    private int mScreenWidth;
    /**
     * menu与屏幕右侧的距离, 单位dp
     *
     * 然后再方法中转化成象素值
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
     * 标识当前侧滑菜单状态，是否展开与关闭，
     * 然后公布三个方法， opMenu, closeMenu
     * 默认false,
     */
    private boolean isOpen;


    /**
     * 未使用自定义属性时，只会调用两个参数的方法
     * @param context
     * @param attrs
     */
    public TwoSlidingMenu(Context context, AttributeSet attrs) {
//        super(context, attrs);
        /**
         * 因为使用自定义属性，这里调用三个参数的方法
         * 没有的话传0
         */
        this(context, attrs, 0);

    }


    /**
     * 第一个参数啥时候调用呢，当我们new一个的时候，
     * 这里我们去让它调用两个参数的构造方法
     */
    public TwoSlidingMenu(Context context) {
//        super(context);
        this(context, null);
    }

    /**
     * 自定义属性：允许用户设置菜单离屏幕右边的边距
     * 1。先在value文件夹new一个xml叫attr.xml添加自定义属性
     * 2。其后在布局文件Layout里面添加命名空间
     */

    /** 当使用了自定义属性时，会使用此构造方法。
     * 我们把使用了两个参数的方法，拷贝到三个参数的方法，
     * 然后再让两个个参数的构造 方法，调用三个参数的构造方法
     *
     * @param context
     * @param attrs    定义样式
     * @param defStyleAttr
     */

    public TwoSlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        /**
         * 这里还有件非常重要的事，就是获取在xml的自定义属性值，
         * 然后赋值给mRightPadding成员变量
         */
        //使用TypedArray这个类获取自定义属性，一定要recycle()释放一下
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TwoSlidingMenu, defStyleAttr, 0);

        //2.获取自定义属性数量
        int n = a.getIndexCount();
        //2.1：for循环
        for(int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            //2.2:判断属性， 这里只有一个attr属性，
            switch (attr) {
                case R.styleable.TwoSlidingMenu_rightPadding:
                    //如果是的话，给右边距赋值
                    //如果没有写值的话会有个默认值，为50dp
                    //如果写了则直接读取attr的自定义属性值
                mMenuRightPadding = a.getDimensionPixelOffset(attr,
                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50,
                                context.getResources().getDisplayMetrics()));
                break;
            }
        }




        a.recycle();












        //把返回值强转为WindowManager
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        //获取屏幕宽度
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;

        /**
         * 把menu菜单距离右边的padding值，转化成px
         * ,如果要把sp转px， 则改TypedValue.COMPLEX_UNIT_DIP
         */


        /**
         * 如果是自定义属性值，下面的方法就不需要了
         */

        //通过下面的方法，就可以把50dp转化成象素值
//        mMenuRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50,
//                context.getResources().getDisplayMetrics());

    }



    /**设置子View的宽和高，设置自己的宽和高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        /**
         * 如果是第一次调用，就设置子View的宽和高
         */
        if(!once) {
            //1。getChildAt(0)这个是固定的，因为HorizentalScrollView内部也就一个元素
            mWrapper = (LinearLayout) getChildAt(0);
            //2。然后通过LinearLayout拿到第一个元素Menu
            mMenu = (ViewGroup) mWrapper.getChildAt(0);
            //3.拿到LinearLayout的第二个元素 ，即Content内容
            mContent = (ViewGroup) mWrapper.getChildAt(1);

            /**
             * 设置子View的宽和高
             */
            //1.高度肯定是match_parent,不需要设置
            //侧滑Menu的宽度为====屏幕的高度 - 距离右边的padding
            mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
            //2.设置Content内容的宽度
            mContent.getLayoutParams().width = mScreenWidth;


            //设置自己的宽和高
            /**
             * 由此可以得到，mWapper的宽度，即LinearLayout的高度为====mMenu + mContent
             */

//        mWrapper.getLayoutParams().width = mMenu + mContent;
            once = true;

        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    /**
     *当onMeasure测量之后 ，决定子放置的位置.
     */
    //我们需要显示的效果是，Content显示内容区域，mMenu缩到左边
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        /**
         * 通过设置偏移量，将Menu隐藏
         */
        //因为是滚动条，有个onScroll方法，X轴移动Menu的宽度
        /**
         * 为防止多次调用，加个判断，判断当前页面是否改变，
         * 改变则调用下面该去
         */
        if(changed) {
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

                //并不是显示的宽度
                int scrollX = (int) getScrollX();
                if(scrollX >= mMenuWidth / 2) {

                    //1。不显示侧滑Menu, 因为 this.scrollTo(x, y);是瞬间完成的动作
                    //下面可以加个平滑的效果
                    this.smoothScrollTo(mMenuWidth, 0);

                    //此时菜单隐藏了
                    isOpen = false;
                } else {
                    this.smoothScrollTo(0, 0);

                    //此时显示了
                    isOpen = true;
                }

                return true;

        }

        return super.onTouchEvent(ev);

    }


    /**
     * 展开侧滑菜单
     */
    public void openMenu() {
      //如果此时是开着的
        if(isOpen) {
            return;
        } else {//否则打开
            this.smoothScrollTo(0, 0);
            isOpen = true;
        }
    }



    /**
     * 关闭侧滑菜单
     */
    public void closeMenu() {
        if(!isOpen) {
            return;
        } else {//否则关闭菜单
            this.smoothScrollTo(mMenuWidth, 0);
            isOpen = false;
        }
    }


    /**
     * 切换菜单, 如果是打开，就关闭它，如果是关闭，就打开它
     */
    public void toggle() {
        if(isOpen) {
            closeMenu();
        } else {
            openMenu();
        }
    }


    /**
     * 无论是自动滚动，还是手动滚动，都会调用这个方法
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //调用属性
    }
}
