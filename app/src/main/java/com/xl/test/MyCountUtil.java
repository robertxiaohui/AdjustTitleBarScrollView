package com.xl.test;

import android.os.CountDownTimer;
import android.util.Log;

/**
 * Created by Administrator on 2015/10/14.
 * 倒计时的类
 */
public class MyCountUtil extends CountDownTimer {
    private long oldTime = 0;
    private long nowTime;
    private long countDownInterval;

    /**
     * @param millisInFuture    倒计时总的时间
     * @param countDownInterval 倒计时时间间隔
     */
    public MyCountUtil(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.countDownInterval = countDownInterval;
        oldTime = millisInFuture / countDownInterval;
    }


    @Override
    public void onTick(long millisUntilFinished) {

        nowTime = millisUntilFinished / countDownInterval;
      /*  if (nowTime < oldTime) {
            oldTime = nowTime;
            Log.d("TAG", "倒计时: " + millisUntilFinished / countDownInterval);*/
            mListener.tickChange(millisUntilFinished);
    }

    @Override
    public void onFinish() {

    }


    private TickChangeListener mListener;

    public void setTickChangeListener(TickChangeListener listener) {
        this.mListener = listener;
    }

    public interface TickChangeListener {
        void tickChange(long millisUntilFinished);
    }
}
