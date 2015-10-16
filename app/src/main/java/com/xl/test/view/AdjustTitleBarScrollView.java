package com.xl.test.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2015/10/14.
 * 滑动调节titlebar的背景颜色
 */
public class AdjustTitleBarScrollView extends ScrollView {
    public View mTitleBar;
    private int ImageHeight = 0;

    private static final String TAG = "AdjustTitleBarScrollVie";

    public AdjustTitleBarScrollView(Context context) {
        this(context, null);
    }

    public AdjustTitleBarScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ImageHeight = (int) (getContext().getResources().getDisplayMetrics().density * 300);
    }

    public void setImageHeight(int imageHeight){
        this.ImageHeight = (int) (getContext().getResources().getDisplayMetrics().density * imageHeight);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (t < 0) {
            setTitleBarBg(0);
        } else if (t < ImageHeight && t > 0) {
            int aplha = (int) (255.0 * t / ImageHeight);
            if (aplha < 25) {
                aplha = 0;
            }
            setTitleBarBg(aplha);
        } else if (t > ImageHeight) {
            setTitleBarBg(255);
        }
    }

    /**
     * 显示title浮动栏
     */
    public void setTitleBarBg(int alpha) {
        String s = Integer.toHexString(alpha);
        if (s.length() == 1) {
            s = "0" + s;
        } else if (s.length() == 0) {
            s = "00";
        }
        mTitleBar.setBackgroundColor(Color.parseColor("#" + s + "ffffff"));
    }

    /**
     * 将需要隐藏显示的view传入
     *
     * @param titleBar
     */
    public void setTitleBar(View titleBar) {
        this.mTitleBar = titleBar;
    }

}
