package com.xl.test.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xl.test.R;

public class TitleBar extends RelativeLayout implements OnClickListener {
    private static final String TAG = "TitleBar";
    private final int SHOWLOGO = 0;
    private final int SHOWBACK = 1;
    private final int NOTSHOW = 2;

    private TextView mLogoLeft;
    private TextView mLeftIcon;
    private TextView mTitle;
    private TextView mIconRight1;
    private TextView mIconRight2;
    private RelativeLayout mRl;
    private ImageView mBack;
    private TextView mIcon2Number;
    private ImageView mMainTileIcon; //主页的title图片
    float scale;

    private String logoName;
    private int logoSize;
    private int logoColor;
    private int logoIcon;
    private String titleText;
    private int titleColor;
    private int titleSize;
    private int icon1;
    private int icon2;
    private String icon2Name;
    private int icon2Size;
    private int icon2Color;
    private int backgroud;
    private String icon2NumberText;

    // private Paint mPaint;
    private TypedArray mTypedArray;
    private IconOnClickListener mListener;
    private TextView mPaddTop;


    public TitleBar(Context context) {
        this(context, null);

    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 将xml挂载到class上
        View.inflate(context, R.layout.title_bar, this);
        //获取定义的属性文件
        mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        //根据这个值,转换为对应的dp值
        scale = context.getResources().getDisplayMetrics().density;
        // 初始化view
        initView();

        initData();
    }

    public TextView getmIconRight2() {
        return mIconRight2;
    }


    /**
     * 初始化view
     */
    private void initView() {
        // 获取对应的id
        mLogoLeft = (TextView) findViewById(R.id.tv_logoLeft);
        mPaddTop = (TextView) findViewById(R.id.tv_paddtop);
        mTitle = (TextView) findViewById(R.id.tv_title);
        mIconRight2 = (TextView) findViewById(R.id.icon_right2);
        mIconRight1 = (TextView) findViewById(R.id.icon_right1);
        mRl = (RelativeLayout) findViewById(R.id.rl);
        mIcon2Number = (TextView) findViewById(R.id.tv_count);
        mLeftIcon = (TextView) findViewById(R.id.tv_left);
        mMainTileIcon = (ImageView)findViewById(R.id.iv_main_title);
        //mBack = (ImageView) findViewById(R.id.iv_back);

//mTitle.addTextChangedListener(this);


        mIconRight1.setOnClickListener(this);
        mIconRight2.setOnClickListener(this);
        mLogoLeft.setOnClickListener(this);
        mLeftIcon.setOnClickListener(this);
    }

    /**
     * 功能:初始化数据
     */
    private void initData() {
        // 获得xml里定义的属性,格式为 名称_属性名 后面是默认值
        logoName = mTypedArray.getString(R.styleable.TitleBar_logoName);
        logoSize = mTypedArray.getDimensionPixelSize(R.styleable.TitleBar_logoSize, 0);
        logoColor = mTypedArray.getColor(R.styleable.TitleBar_logoColor, 0);
        logoIcon = mTypedArray.getResourceId(R.styleable.TitleBar_logoIcon, 0);

        titleText = mTypedArray.getString(R.styleable.TitleBar_titleText);
        titleSize = mTypedArray.getDimensionPixelSize(R.styleable.TitleBar_titleSize, 0);
        titleColor = mTypedArray.getColor(R.styleable.TitleBar_titleColor, 0);
        //boolean titleShow = mTypedArray.getBoolean(R.styleable.TitleBar_titleShow, true);

        icon1 = mTypedArray.getResourceId(R.styleable.TitleBar_icon1, 0);

        icon2 = mTypedArray.getResourceId(R.styleable.TitleBar_icon2, 0);
        icon2Name = mTypedArray.getString(R.styleable.TitleBar_icon2Name);
        icon2Size = mTypedArray.getDimensionPixelSize(R.styleable.TitleBar_icon2Size, 0);
        icon2Color = mTypedArray.getColor(R.styleable.TitleBar_icon2Color, 0);
        icon2NumberText = mTypedArray.getString(R.styleable.TitleBar_icon2NumberText);

        backgroud = mTypedArray.getColor(R.styleable.TitleBar_backgroud, 0);


        if (backgroud != 0)
            mRl.setBackgroundColor(backgroud);

        if (logoName == null) {
            //没有设置文字,就显示图片
            if (logoIcon == 0) {
                mLogoLeft.setVisibility(INVISIBLE);
            } else {
                mLogoLeft.setVisibility(VISIBLE);
                //设置显示图片
                Drawable mLogoIcon = getResources().getDrawable(logoIcon);
                mLogoIcon.setBounds(0, 0, mLogoIcon.getMinimumWidth(), mLogoIcon.getMinimumHeight());
                mLogoLeft.setCompoundDrawables(mLogoIcon, null, null, null);
            }
        } else {
            mLogoLeft.setText(logoName);
            if (logoSize != 0)
                mLogoLeft.setTextSize(logoSize / scale);
            if (logoColor != 0)
                mLogoLeft.setTextColor(logoColor);
        }

        //设置显示title
        mTitle.setText(titleText);
        if (titleSize != 0)
            mTitle.setTextSize(titleSize / scale);
        if (titleColor != 0)
            mTitle.setTextColor(titleColor);

        if (icon1 != 0) {
            mIconRight1.setVisibility(VISIBLE);
            //设置显示图片
            Drawable drawable = getResources().getDrawable(icon1);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mIconRight1.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        } else {
            mIconRight1.setVisibility(INVISIBLE);
        }

        if (icon2Name == null) {
            //没有设置文字,就显示图片
            if (icon2 == 0) {
                mIconRight2.setVisibility(INVISIBLE);
            } else {
                mIconRight2.setVisibility(VISIBLE);
                //设置显示图片
                Drawable drawable = getResources().getDrawable(icon2);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                mIconRight2.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            }

        } else {
            //设置了文字
            mIconRight2.setText(icon2Name);
            if (icon2Size != 0)
                mIconRight2.setTextSize(icon2Size / scale);
            if (icon2Color != 0)
                mIconRight2.setTextColor(icon2Color);
        }

        //设置icon2的msg数量
        if (TextUtils.isEmpty(icon2NumberText)) {
            mIcon2Number.setVisibility(INVISIBLE);
        } else {
            int number = Integer.parseInt(icon2NumberText);
            if (number <= 0) {
                mIcon2Number.setVisibility(INVISIBLE);
            } else if (number > 99) {
                mIcon2Number.setVisibility(VISIBLE);
                mIcon2Number.setText("99");
            } else {
                mIcon2Number.setVisibility(VISIBLE);
                mIcon2Number.setText(icon2NumberText);
            }
        }


        // 为了保持以后使用该属性一致性,返回一个绑定资源结束的信号给资源
        mTypedArray.recycle();
    }


    public void setFull(boolean isFull) {
        if (isFull && android.os.Build.VERSION.SDK_INT > 18) {
            mPaddTop.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams params = mPaddTop.getLayoutParams();
            params.height = getStatusBarHeight();
            mPaddTop.setLayoutParams(params);
        } else {
            mPaddTop.setVisibility(View.GONE);
        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    @Override
    public void onClick(View v) {
        if (v == mIconRight1) {

            mListener.icon1Click();
        } else if (v == mIconRight2) {

            mListener.icon2Click();
        } else if (v == mLogoLeft) {
            mListener.backClick();
        }
        else if (v == mLeftIcon){
            mListener.leftIconClick();
        }

    }

    public void icon1Click() {
    }

    public void icon2Click() {
    }

    public void backClick() {
    }

    public void setIconOnClickListener(IconOnClickListener listener) {
        this.mListener = listener;
    }

    public interface IconOnClickListener {
        void icon1Click();

        void backClick();

        void icon2Click();

        void leftIconClick();
    }

    //给logo设置方法
    public void setLogoName(String name) {
        mLogoLeft.setText(name);
    }

    public void setLogoColor(int color) {
        mLogoLeft.setTextColor(color);
    }

    public void setLogoSize(int size) {
        mLogoLeft.setTextSize(size);
    }

    public void setLogoIcon(int id) {
        Drawable mLogoIcon = getResources().getDrawable(id);
        mLogoIcon.setBounds(0, 0, mLogoIcon.getMinimumWidth(), mLogoIcon.getMinimumHeight());
        mLogoLeft.setCompoundDrawables(mLogoIcon, null, null, null);
    }

    public void setLogoVisibility(int visibility) {
        mLogoLeft.setVisibility(visibility);
    }

    //给title设置方法
    public void setTitleName(String name) {
        mTitle.setText(name);
    }

    //设置titlebe背景
    public void setMainTitleIconVisibility(boolean show){
        if (show){
            mMainTileIcon.setVisibility(View.VISIBLE);
            mTitle.setVisibility(View.GONE);
        }
        else {
            mMainTileIcon.setVisibility(View.GONE);
            mTitle.setVisibility(View.VISIBLE);
        }
    }

    public void setTitleColor(int color) {
        mTitle.setTextColor(color);
    }

    public void setTitleSize(int size) {
        mTitle.setTextSize(size);
    }


    //给icon1设置方法
    /*public void setIcon1Name(String name){
        this.icon2Name
	}
	public void setIcon1Color(int color){
		mIconRight1.setTextColor(color);
	}
	public void setIcon1Size(float size){
		mIconRight1.setTextSize(size);
	}*/

    //给icon1设置方法
    public void setIcon2Name(String name) {
        mIconRight2.setText(name);
    }

    public void setIcon2Color(int color) {
        mIconRight2.setTextColor(color);
    }

    public void setIcon2Size(int size) {
        mIconRight2.setTextSize(size);
    }

    public void setIcon2Visibility(int visibility) {
        mIconRight2.setVisibility(visibility);
    }

    public void setIcon2Number(int number) {
        //设置icon2的msg数量
        if (number <= 0) {
            mIcon2Number.setVisibility(INVISIBLE);
        } else if (number > 99) {
            mIcon2Number.setVisibility(VISIBLE);
            mIcon2Number.setText("99+");
        } else {
            mIcon2Number.setVisibility(VISIBLE);
            mIcon2Number.setText(String.valueOf(number));
        }
    }
    public void setIcon2NumberVisibility(int visibility){
        mIcon2Number.setVisibility(visibility);
    }


    public void setLeftText(String text, int id, int imageSite, boolean isShow) {
        if (isShow) {
            mLeftIcon.setVisibility(VISIBLE);
            setLogoVisibility(INVISIBLE);
            mLeftIcon.setText(text);
            Drawable mLogoIcon = getResources().getDrawable(id);
            mLogoIcon.setBounds(0, 0, mLogoIcon.getMinimumWidth(), mLogoIcon.getMinimumHeight());

            switch (imageSite) {
                case 0:
                    mLeftIcon.setCompoundDrawables(mLogoIcon, null, null, null);
                    break;
                case 1:
                    mLeftIcon.setCompoundDrawables(null, mLogoIcon, null, null);
                    break;
                case 2:
                    mLeftIcon.setCompoundDrawables(null, null, mLogoIcon, null);
                    break;
                case 3:
                    mLeftIcon.setCompoundDrawables(null, null, null, mLogoIcon);
                    break;
            }
        } else {
            setLogoVisibility(VISIBLE);
            mLeftIcon.setVisibility(GONE);
        }

    }

}
