package com.xl.test.act;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2015/10/14.
 * 滑动调节titlebar的背景颜色
 */
public class AdjustTitleBarScrollView extends ScrollView implements OnScrollListener {
    public View mTitleBar;
    public String mColor = "ffffff";

    private static final String TAG = "AdjustTitleBarScrollVie";

    public AdjustTitleBarScrollView(Context context) {
        this(context, null);
    }

    public AdjustTitleBarScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (oldt < 256 * 2 && oldt > 0) {
            int i = oldt / 2 % 256;
            String s = Integer.toHexString(i);
            if (s.length() == 1) {
                s = "0" + s;
            } else if (s.length() == 0) {
                s = "00";
            }
            Log.d(TAG, "l: " + l + "  t: " + t + "  oldl: " + oldl + "  oldt: " + oldt + "  color: " + s + "ffff22");
            setTitleBarBg(Color.parseColor("#" + s + mColor));
        }
    }


    /**
     * 显示title浮动栏
     */
    private void setTitleBarBg(int color) {
        if (mTitleBar != null)
            mTitleBar.setBackgroundColor(color);
    }

    /**
     * 将需要隐藏显示的view传入
     *
     * @param titleBar
     */
    public void setTitleBar(View titleBar) {
        this.mTitleBar = titleBar;
    }

    /**
     * 设置titlebar的 颜色
     *
     * @param color color格式:RRGGBB
     */
    public void setTitleBarColor(String color) {
        if (color != null) {
            this.mColor = color;
        } else {
            this.mColor = "ffffff";
        }
    }

}
