package com.xl.test.act;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.xl.test.MyCountUtil;


/**
 * 底部View自动隐藏和消失listview(其他ListView可以继承该类，如CtripBottomRefreshListView类等)
 *
 * @author zhiwen.nan
 * @Date 2013-9-28 下午3:35:15
 */
public class BottomFloatListView extends ListView implements OnScrollListener {

    public View mBottomBar;
    public View mTitleBar;
    private int mCurrentScrollState;
    private boolean bIsMoved = false;
    private boolean bIsDown = false;
    private int mDeltaY;
    private float mMotionY;
    private int oldFirstVisibleItem = 0;
    private Handler mHandler = new Handler();
    private static final String TAG = "BottomFloatListView";
    private int mHeight;
    private int height;
    private long totalTime = 1000;
    private long countDownInterval = 10;


    public BottomFloatListView(Context context) {
        this(context, null);
        super.setOnScrollListener(this);
    }

    public BottomFloatListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        super.setOnScrollListener(this);
    }

    public BottomFloatListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        super.setOnScrollListener(this);
    }


    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        showBottomViewOnBottom(visibleItemCount, totalItemCount, firstVisibleItem);

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        hideBottomViewOnScrollStateChanged(view, scrollState);

    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        float y = ev.getY();
        float x = ev.getX();
        Log.d("FloatListView", "onTouchEvent" + "" + x + "" + y);
        int action = ev.getAction() & MotionEvent.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                action_down(y);
                break;
            case MotionEvent.ACTION_MOVE:
                mDeltaY = (int) (y - mMotionY);
                bIsMoved = true;
                //移动的时候，要移除掉显示bottomView的消息
                mHandler.removeCallbacks(showBottomBarRunnable);
                //补齐action_down事件，因为有的时候，action_down 事件没有执行
                action_down(y);
                break;
            case MotionEvent.ACTION_UP:
                bIsMoved = false;
                bIsDown = false;
                if (!bIsMoved && !bIsDown) {
                    // 如果屏幕上什么没做，则过2s之后要显示bottomView
                    mHandler.postDelayed(showBottomBarRunnable, 4000);
                }
                if (mDeltaY < 0) { //下滑影藏
                    hideBottomBar();
                    hideTitleBar();
                } else {  //上滑显示
                    showBottomBar();
                    showTitleBar();
                }

                bIsMoved = false;
                break;

        }

        return super.onTouchEvent(ev);
    }


    /**
     * 滑动到顶部时，要隐藏bottomView
     *
     * @param view
     * @param scrollState
     */
    private void hideBottomViewOnScrollStateChanged(AbsListView view, int scrollState) {
        mCurrentScrollState = scrollState;
        if (view != null) {
            if (view.getFirstVisiblePosition() == 0 && scrollState == SCROLL_STATE_IDLE) {
                hideBottomBar();
                Log.d(TAG, "hide bottom view");
            }
        }
    }


    private void action_down(float y) {
        bIsDown = true;
        mMotionY = y;
        Log.d(TAG, "action down execed");
        mHandler.removeCallbacks(showBottomBarRunnable);
    }

    /**
     * 显示底部浮动栏
     */
    public void showBottomBar() {

        if (mBottomBar != null && mBottomBar.getVisibility() == View.GONE) {
            mBottomBar.setVisibility(View.INVISIBLE);
            Animation translateAnimation = new TranslateAnimation(mBottomBar.getLeft(), mBottomBar.getLeft(), getHeight(mBottomBar), 0);
            translateAnimation.setDuration(500);
            translateAnimation.setInterpolator(new OvershootInterpolator(0.6f));
            mBottomBar.startAnimation(translateAnimation);
            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mBottomBar.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    /**
     * 隐藏浮动底部栏
     */
    private void hideBottomBar() {


        if (mBottomBar != null && mBottomBar.getVisibility() == View.VISIBLE) {
            Animation translateAnimation = new TranslateAnimation(mBottomBar.getLeft(), mBottomBar.getLeft(), 0, getHeight(mBottomBar));
            translateAnimation.setDuration(1000);
            translateAnimation.setInterpolator(new OvershootInterpolator(0.6f));
            mBottomBar.startAnimation(translateAnimation);
            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mBottomBar.setVisibility(View.GONE);
                }
            });
        }
    }

    /**
     * 显示title浮动栏
     */
    public void showTitleBar() {

     /*   if (mTitleBar != null && mTitleBar.getVisibility() == View.GONE) {
            mTitleBar.setVisibility(View.INVISIBLE);
            Animation translateAnimation = new TranslateAnimation(mTitleBar.getLeft(), mTitleBar.getLeft(), 0, getHeight(mTitleBar));
            translateAnimation.setDuration(totalTime);
            translateAnimation.setInterpolator(new OvershootInterpolator(0.6f));
            mTitleBar.startAnimation(translateAnimation);
            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mTitleBar.setVisibility(View.VISIBLE);
                }
            });
        }*/

        MyCountUtil myCountUtil = new MyCountUtil(totalTime, countDownInterval);
        myCountUtil.setTickChangeListener(new MyCountUtil.TickChangeListener() {
            @Override
            public void tickChange(long millisUntilFinished) {
               /* mHeight = (int) millisUntilFinished / 1000 * height;
                setHight(mHeight);*/
                mHeight = height - (int)( millisUntilFinished / (double)totalTime * height);
                Log.d("TAG", "倒计时: " + millisUntilFinished / countDownInterval+"  高度: "+mHeight+"  mi: "+millisUntilFinished / (double)totalTime);
                setHight(mHeight);
            }
        });
        myCountUtil.start();
    }

    /**
     * 隐藏浮title部栏
     */
    private void hideTitleBar() {

/*

        if (mTitleBar != null && mTitleBar.getVisibility() == View.VISIBLE) {
            Animation translateAnimation = new TranslateAnimation(mTitleBar.getLeft(), mTitleBar.getLeft(), getHeight(mTitleBar), 0);
            translateAnimation.setDuration(totalTime);
            translateAnimation.setInterpolator(new OvershootInterpolator(0.6f));
            mTitleBar.startAnimation(translateAnimation);
            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mTitleBar.setVisibility(View.GONE);
                }
            });
        }
*/

        //mHeight = getHeight(mTitleBar);
        MyCountUtil myCountUtil = new MyCountUtil(totalTime, countDownInterval);
        myCountUtil.setTickChangeListener(new MyCountUtil.TickChangeListener() {
            @Override
            public void tickChange(long millisUntilFinished) {
                mHeight =(int)( millisUntilFinished / (double)totalTime * height);
                Log.d("TAG", "倒计时: " + millisUntilFinished / countDownInterval+"  高度: "+mHeight+"  mi: "+millisUntilFinished / (double)totalTime);
                setHight(mHeight);
            }
        });
        myCountUtil.start();
}

    /**
     * 滑动到底部时直接显示bottomView
     *
     * @param visibleItemCount
     * @param totalItemCount
     * @param firstVisibleItem
     */
    private void showBottomViewOnBottom(int visibleItemCount, int totalItemCount, int firstVisibleItem) {

        Log.d(TAG, "visible bottem item count:" + "firstVisibleItem:" + firstVisibleItem + "oldFirstVisibleItem:" + oldFirstVisibleItem + mBottomBar);
        if (getLastVisiblePosition() == totalItemCount - 1 && mCurrentScrollState != SCROLL_STATE_IDLE) {
            showBottomBar();
        }
    }

    private Runnable showBottomBarRunnable = new Runnable() {

        @Override
        public void run() {
            showBottomBar();
            showTitleBar();
        }

    };


    /**
     * 将需要隐藏显示的view传入
     *
     * @param bottomBar
     */
    public void setBottomBar(View bottomBar) {
        this.mBottomBar = bottomBar;
    }

    /**
     * 将需要隐藏显示的view传入
     *
     * @param titleBar
     */
    public void setTitleBar(View titleBar) {
        this.mTitleBar = titleBar;
        this.height = getHeight(titleBar);
    }

    public int getHeight(View mBottomBar2) {
        int w = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int h = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        mBottomBar2.measure(w, h);
        int height = mBottomBar2.getMeasuredHeight();
        int width = mBottomBar2.getMeasuredWidth();

        return height;
    }

    public void setHight(int height) {
        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) mTitleBar.getLayoutParams(); //取控件textView当前的布局参数
        linearParams.height = height;// 控件的高强制设成20
        mTitleBar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件</pre>
    }

}















