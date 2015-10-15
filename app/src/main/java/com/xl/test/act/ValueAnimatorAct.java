package com.xl.test.act;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xl.test.MyCountUtil;
import com.xl.test.R;

/**
 * Created by Administrator on 2015/10/14.
 */
public class ValueAnimatorAct extends Activity {
    private LinearLayout viewById;
    private Button mRunAnimator;
    private AdjustTitleBarScrollView mScrollview;
    private boolean isDown = false;
    private int mHeight;
   private int height;
    private long totalTime = 1000;
    private long countDownInterval = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);

        viewById = (LinearLayout) findViewById(R.id.title_bar);
        mRunAnimator = (Button) findViewById(R.id.run);
        mScrollview = (AdjustTitleBarScrollView) findViewById(R.id.my_scrollview);
        height = getHeight(viewById);
        mScrollview.setTitleBar(viewById);

        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ValueAnimatorAct.this,"点击了",Toast.LENGTH_SHORT).show();
            }
        });

        mRunAnimator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDown){
                    verticalRunDown();
                    isDown = false;
                }else {
                    verticalRunUp();
                    isDown = true;
                }
            }
        });
    }

    public void verticalRunDown()
    {
       /* ValueAnimator animator = ValueAnimator.ofFloat(0, getHeight(viewById));
        animator.setTarget(viewById);
        animator.setDuration(1000).start();
//      animator.setInterpolator(value)
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                viewById.setTranslationY((Float) animation.getAnimatedValue());
                float h= (Float)animation.getAnimatedValue();
                //setHight((int) h);
            }
        });
*/
        MyCountUtil myCountUtil = new MyCountUtil(totalTime, countDownInterval);
        myCountUtil.setTickChangeListener(new MyCountUtil.TickChangeListener() {
            @Override
            public void tickChange(long millisUntilFinished) {
                mHeight = height - (int)( millisUntilFinished / (double)totalTime * height);
                Log.d("TAG", "倒计时: " + millisUntilFinished / countDownInterval+"  高度: "+mHeight+"  mi: "+millisUntilFinished / (double)totalTime);
                setHight(mHeight);
            }
        });
        myCountUtil.start();
    }

    public void verticalRunUp()
    {
      /*  ValueAnimator animator = ValueAnimator.ofFloat(getHeight(viewById), 0);
        animator.setTarget(viewById);
        animator.setDuration(1000).start();
//      animator.setInterpolator(value)
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                viewById.setTranslationY((Float) animation.getAnimatedValue());
                float h= (Float)animation.getAnimatedValue();
              //  setHight((int)h);
            }
        });
*/
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

    public void setHight(int height){
        RelativeLayout.LayoutParams linearParams =(RelativeLayout.LayoutParams) viewById.getLayoutParams(); //取控件textView当前的布局参数
        linearParams.height = height;// 控件的高强制设成20
        viewById.setLayoutParams(linearParams); //使设置好的布局参数应用到控件</pre>
    }

    public int getHeight(View mBottomBar2){
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mBottomBar2.measure(w, h);
        int height = mBottomBar2.getMeasuredHeight();
        int width = mBottomBar2.getMeasuredWidth();

        return height;
    }

}
