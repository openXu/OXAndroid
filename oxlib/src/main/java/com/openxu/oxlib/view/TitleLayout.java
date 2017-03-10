package com.openxu.oxlib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.openxu.oxlib.R;
import com.openxu.oxlib.utils.DensityUtil;
import com.openxu.oxlib.utils.LogUtil;


/**
 * autour: openXu
 * date: 2017/3/10 13:32
 * className: TitleLayout
 * version:
 * description: 通用的titlebar
 */
public class TitleLayout extends RelativeLayout {


    private String TAG = "TitleLayout";

    /*总容器*/
    private RelativeLayout titleLayout;              //title总布局
    /*左侧部分*/
    private LinearLayout ll_left;                    //左侧容器
    private ImageView iv_left_back, iv_left_icon;    //返回键、图标
    private TextView tv_left_text;                   //左侧文字
    /*中间*/
    private RelativeLayout rl_center;                //中间容器
    private TextView tv_center_text;                 //中间文字
    private ImageView iv_center_row;                 //文字点击箭头
    /*右侧*/
    private LinearLayout ll_right;                 //右侧容器
    private RelativeLayout rl_right_icon;   //右侧图标（可带气泡）
    private TextView tv_right_text, tv_right_pop;                   //右侧文字
    private ImageView iv_right_icon;    //菜单图标、气泡
    private LinearLayout ll_right_icon_content;       //其他菜单容器

    /**属性值*/
    private String textLeft, textcenter, textRight, numRightPop;  //文字
    private int iconBack, iconLeft, iconCenterRow, iconRight;  //图标

    private float titleHeight;                         //文字高度
    private float textSize;                            //文字大小
    private int textColor;                             //文字颜色
    private float textIconSpace;                           //item之间的间距
    private float leftSpace;                           //标题左侧的空隙
    private float rightSpace;                          //标题右侧的空隙
    private float centerRowSpace;                      //中间箭头和文字的距离

    private float leftUsed;
    private float rightUsed;
    private Paint textPaint;

    public TitleLayout(Context context) {
        this(context, null);
    }

    public TitleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.title_layout, this, true);

        //title总布局
        titleLayout = (RelativeLayout) findViewById(R.id.titleLayout);
        /*左侧部分*/
        ll_left = (LinearLayout) findViewById(R.id.ll_left);
        iv_left_back = (ImageView) findViewById(R.id.iv_left_back);
        iv_left_icon = (ImageView) findViewById(R.id.iv_left_icon);
        tv_left_text = (TextView) findViewById(R.id.tv_left_text);
        /*中间*/
        rl_center = (RelativeLayout) findViewById(R.id.rl_center);
        iv_center_row = (ImageView) findViewById(R.id.iv_center_row);
        tv_center_text = (TextView) findViewById(R.id.tv_center_text);
        /*右侧*/
        ll_right = (LinearLayout) findViewById(R.id.ll_right);
        rl_right_icon = (RelativeLayout) findViewById(R.id.rl_right_icon);
        tv_right_text = (TextView) findViewById(R.id.tv_right_text);
        iv_right_icon = (ImageView) findViewById(R.id.iv_right_icon);
        tv_right_pop = (TextView) findViewById(R.id.tv_right_pop);
        ll_right_icon_content = (LinearLayout) findViewById(R.id.ll_right_icon_content);

        //获取自定义属性的值
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TitleView, defStyleAttr, 0);

        textLeft = ta.getString(R.styleable.TitleView_textLeft);
        textcenter = ta.getString(R.styleable.TitleView_textcenter);
        textRight = ta.getString(R.styleable.TitleView_textRight);
        iconBack = ta.getResourceId(R.styleable.TitleView_iconBack, 0);
        iconLeft = ta.getResourceId(R.styleable.TitleView_iconLeft, 0);
        iconCenterRow = ta.getResourceId(R.styleable.TitleView_iconCenterRow, 0);
        iconRight = ta.getResourceId(R.styleable.TitleView_iconRight, 0);

        textSize = ta.getInteger(R.styleable.TitleView_textSize, 1);
        titleHeight = ta.getDimension(R.styleable.TitleView_titleHeight, 0);
        textColor = ta.getColor(R.styleable.TitleView_textColor, Color.WHITE);
        textIconSpace = ta.getDimension(R.styleable.TitleView_textIconSpace, 0);
        leftSpace = ta.getDimension(R.styleable.TitleView_leftSpace, 0);
        rightSpace = ta.getDimension(R.styleable.TitleView_rightSpace, 0);
        centerRowSpace = ta.getDimension(R.styleable.TitleView_centerRowSpace, 0);

       /* LogUtil.i(TAG, "textLeft = "+textLeft);
        LogUtil.i(TAG, "textcenter = "+textcenter);
        LogUtil.i(TAG, "textRight = "+textRight);
        LogUtil.i(TAG, "iconBack = "+iconBack);
        LogUtil.i(TAG, "iconLeft = "+iconLeft);
        LogUtil.i(TAG, "iconCenterRow = "+iconCenterRow);
        LogUtil.i(TAG, "iconRight = "+iconRight);
        LogUtil.i(TAG, "iconRight = "+iconRight);
        LogUtil.i(TAG, "textSize = "+textSize);
        LogUtil.i(TAG, "titleHeight = "+titleHeight);
        LogUtil.i(TAG, "textColor = "+textColor);
        LogUtil.i(TAG, "textIconSpace = "+textIconSpace);
        LogUtil.i(TAG, "leftSpace = "+leftSpace);
        LogUtil.i(TAG, "rightSpace = "+rightSpace);
        LogUtil.i(TAG, "centerRowSpace = "+centerRowSpace);
*/
        ta.recycle();
        show();
    }

    public void show(){
        setParams();
        setGone();
        setVisibleAndLayout();
    }

    private void setParams(){
        //设置标题高度
        ViewGroup.LayoutParams params = titleLayout.getLayoutParams();
        if(params==null)
            return;

        params.height = (int)titleHeight;
        titleLayout.setLayoutParams(params);

        /*设置左边距*/
        RelativeLayout.LayoutParams rl_params = (RelativeLayout.LayoutParams) ll_left.getLayoutParams();
        rl_params.leftMargin = (int)leftSpace;
        ll_left.setLayoutParams(rl_params);
        /*设置右边距*/
        rl_params = (RelativeLayout.LayoutParams) ll_right.getLayoutParams();
        rl_params.rightMargin = (int)rightSpace;
        ll_right.setLayoutParams(rl_params);

        leftUsed = leftSpace;
        rightUsed = rightSpace;

        tv_left_text.setTextColor(textColor);
        tv_left_text.setTextSize(textSize);
        tv_center_text.setTextColor(textColor);
        tv_center_text.setTextSize(textSize);
        tv_right_text.setTextColor(textColor);
        tv_right_text.setTextSize(textSize);
        tv_right_pop.setTextColor(textColor);
        textPaint = new Paint();
        textPaint.setTextSize(textSize);
        LogUtil.d(TAG, "设置字体");
    }


    private void setGone(){
        tv_left_text.setVisibility(View.GONE);
        iv_left_back.setVisibility(View.GONE);
        iv_left_icon.setVisibility(View.GONE);
        ll_left.setVisibility(View.GONE);

        tv_center_text.setVisibility(View.GONE);
        iv_center_row.setVisibility(View.GONE);
        rl_center.setVisibility(View.GONE);

        tv_right_text.setVisibility(View.GONE);
        iv_right_icon.setVisibility(View.GONE);
        tv_right_pop.setVisibility(View.GONE);
        rl_right_icon.setVisibility(View.GONE);
        ll_right_icon_content.setVisibility(View.GONE);
        ll_right.setVisibility(View.GONE);
    }
    /**控制item显示*/
    private void setVisibleAndLayout(){
        LogUtil.d(TAG, "设置可见");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        if(!TextUtils.isEmpty(textLeft) || iconBack!=0 || iconLeft != 0){
            LogUtil.d(TAG, "左侧可见");
            ll_left.setVisibility(View.VISIBLE);
            if(!TextUtils.isEmpty(textLeft)){
                tv_left_text.setVisibility(View.VISIBLE);
                tv_left_text.setText(textLeft);
                tv_left_text.measure(0,0);
                LogUtil.d(TAG, "左侧文字可见"+ tv_left_text.getMeasuredWidth());
//                LogUtil.d(TAG, "左侧文字可见"+ FontUtil.getFontlength(textPaint, textLeft));
                leftUsed += tv_left_text.getMeasuredWidth();
            }
            if(iconBack!=0){
                iv_left_back.setVisibility(View.VISIBLE);
                iv_left_back.setImageResource(iconBack);

                BitmapFactory.decodeResource(getResources(), iconBack, options);
                int width = options.outWidth;
                LogUtil.d(TAG, "左侧返回可见"+width);
                leftUsed += width;
                iv_left_back.setOnClickListener(v->{
                    if(listener!=null)
                        listener.onClick(MENU_NAME.MENU_BACK);
                });
            }
            if(iconLeft!=0){
                iv_left_icon.setVisibility(View.VISIBLE);
                iv_left_icon.setImageResource(iconLeft);
                BitmapFactory.decodeResource(getResources(), iconLeft, options);
                int width = options.outWidth;
                LogUtil.d(TAG, "左侧图标可见"+width);
                leftUsed += width;
                iv_left_icon.setOnClickListener(v->{
                    if(listener!=null)
                        listener.onClick(MENU_NAME.MENU_LEFT_ICON);
                });
            }

            if(!TextUtils.isEmpty(textLeft) &&(iconLeft!=0 || iconBack!=0)){
                LinearLayout.LayoutParams ll_params = (LinearLayout.LayoutParams) tv_left_text.getLayoutParams();
                ll_params.leftMargin = (int)textIconSpace;
                leftUsed += textIconSpace;
                tv_left_text.setLayoutParams(ll_params);
            }

        }
        if(!TextUtils.isEmpty(textcenter) || iconCenterRow!=0){
            rl_center.setVisibility(View.VISIBLE);
            LogUtil.d(TAG, "中间可见");
            if(!TextUtils.isEmpty(textcenter)){
                LogUtil.d(TAG, "中间文字可见");
                tv_center_text.setVisibility(View.VISIBLE);
                tv_center_text.setText(textcenter);

                if(iconCenterRow!=0){
                    LogUtil.d(TAG, "中间箭头可见");
                    iv_center_row.setVisibility(View.VISIBLE);
                    iv_center_row.setImageResource(iconCenterRow);
                    RelativeLayout.LayoutParams rl_params = (RelativeLayout.LayoutParams) iv_center_row.getLayoutParams();
                    rl_params.leftMargin = (int)centerRowSpace;
                    iv_center_row.setLayoutParams(rl_params);
                    rl_center.setOnClickListener(v->{
                        if(listener!=null)
                            listener.onClick(MENU_NAME.MENU_CENTER);
                    });
                }
            }

        }
        if(!TextUtils.isEmpty(textRight) || iconRight!=0 || !TextUtils.isEmpty(numRightPop)){
            ll_right.setVisibility(View.VISIBLE);
            LogUtil.d(TAG, "右侧可见");
            if(!TextUtils.isEmpty(textRight)){
                tv_right_text.setVisibility(View.VISIBLE);
                tv_right_text.setText(textRight);
                tv_right_text.measure(0,0);
                LogUtil.d(TAG, "右侧文字可见"+tv_left_text.getMeasuredWidth());
//                LogUtil.d(TAG, "右侧文字可见"+FontUtil.getFontlength(textPaint, textRight));
                rightUsed += tv_left_text.getMeasuredWidth();
            }
            if(iconRight!=0){
                rl_right_icon.setVisibility(View.VISIBLE);
                iv_right_icon.setVisibility(View.VISIBLE);
                iv_right_icon.setImageResource(iconRight);
                rl_right_icon.setOnClickListener(v->{
                    if(listener!=null) {
                        listener.onClick(MENU_NAME.MENU_RIGHT_ICON);
                    }
                });
                LogUtil.d(TAG, "右侧图标可见");
                if(!TextUtils.isEmpty(numRightPop)){
                    tv_right_pop.setVisibility(View.VISIBLE);
                    tv_right_pop.setText(numRightPop+"");
                }
            }
            /*设置右侧menu图标和气泡的间距*/
            BitmapFactory.decodeResource(getResources(), iconRight, options);
            int width = options.outWidth;
            int height = options.outHeight;
            LogUtil.i(TAG, "右侧图标的宽高："+width+" * "+height);
            LinearLayout.LayoutParams  ll_params = (LinearLayout.LayoutParams) rl_right_icon.getLayoutParams();
            if(!TextUtils.isEmpty(numRightPop)){
                tv_right_pop.measure(0,0);
                LogUtil.d(TAG, "气泡大小"+tv_right_pop.getMeasuredWidth());
                int widthPop = tv_right_pop.getMeasuredWidth();
                int heightPop = tv_right_pop.getMeasuredHeight();
                LogUtil.i(TAG, "气泡的宽高："+widthPop+" * "+heightPop);
                ll_params.width = width + widthPop;
                ll_params.height = height + heightPop;
            }else{
                ll_params.width = width;
                ll_params.height = height;
            }

            rl_right_icon.setLayoutParams(ll_params);
            rightUsed += ll_params.width;

            if(!TextUtils.isEmpty(textRight) && iconRight!=0){
                ll_params = (LinearLayout.LayoutParams) rl_right_icon.getLayoutParams();
                ll_params.leftMargin = (int)textIconSpace;
                rl_right_icon.setLayoutParams(ll_params);
                rightUsed += textIconSpace;
            }
        }


        if(!TextUtils.isEmpty(textcenter)){
             /*避免中间的内容超出范围*/
            LogUtil.e(TAG, "左边占用："+leftUsed+"   右边占用："+rightUsed);
            float max = leftUsed>rightUsed?leftUsed:rightUsed;
            int widthRow = 0;
            if(iconCenterRow!=0){
                BitmapFactory.decodeResource(getResources(), iconCenterRow, options);
                widthRow = options.outWidth;
            }

            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            int centerTextMaxWidth = wm.getDefaultDisplay().getWidth()-
                    (int)(2*(max+textIconSpace)) -widthRow
                    -(int)(widthRow==0?0:centerRowSpace+widthRow);
//            LogUtil.e(TAG, "   剩余："+centerTextMaxWidth );
            tv_center_text.measure(0,0);
//            LogUtil.d(TAG, "中间标题长度："+tv_center_text.getMeasuredWidth());
            float centerTextWidth = tv_center_text.getMeasuredWidth() >centerTextMaxWidth?
                    centerTextMaxWidth:tv_center_text.getMeasuredWidth() ;

            RelativeLayout.LayoutParams rl_params = (RelativeLayout.LayoutParams) tv_center_text.getLayoutParams();
            rl_params.width = (int)centerTextWidth;
            tv_center_text.setLayoutParams(rl_params);
        }

    }


    /**点击事件类型*/
    public enum MENU_NAME{
        MENU_BACK,
        MENU_LEFT_ICON,
        MENU_CENTER,
        MENU_RIGHT_ICON,
    }
    public interface OnMenuClickListener{
        public void onClick(MENU_NAME menu);
    }
    private OnMenuClickListener listener;
    public void setOnMenuClickListener(OnMenuClickListener listener){
        this.listener = listener;
    }


    public TitleLayout setBackgroundColor1(int Color){
        titleLayout.setBackgroundColor(Color);
        return this;
    }

    /**设置内容*/

    public TitleLayout setTextLeft(String textLeft){
        this.textLeft = textLeft;
        return this;
    }
    public TitleLayout setTextcenter(String textcenter){
        this.textcenter = textcenter;
        return this;
    }
    public TitleLayout setTextRight(String textRight){
        this.textRight = textRight;
        return this;
    }

    public TitleLayout setIconBack(int iconBack){
        this.iconBack = iconBack;
        return this;
    }
    public TitleLayout setIconLeft(int iconLeft){
        this.iconLeft = iconLeft;
        return this;
    }
    public TitleLayout setIconCenterRow(int iconCenterRow){
        this.iconCenterRow = iconCenterRow;
        return this;
    }
    public TitleLayout setIconRight(int iconRight){
        this.iconRight = iconRight;
        return this;
    }
    public TitleLayout setNumRightPop(String numRightPop){
        this.numRightPop = numRightPop;
        return this;
    }
    public TitleLayout setTitleHeight(int titleHeight){
        this.titleHeight = DensityUtil.dip2px(getContext(), titleHeight);
        return this;
    }
    public TitleLayout setTextSize(int textSize){
        this.textSize = textSize;
        return this;
    }
    public TitleLayout setTextColor(int textColor){
        this.textColor = textColor;
        return this;
    }
    public TitleLayout setTextIconSpace(int textIconSpace){
        this.textIconSpace = DensityUtil.dip2px(getContext(), textIconSpace);
        return this;
    }
    public TitleLayout setLeftSpace(int leftSpace){
        this.leftSpace = DensityUtil.dip2px(getContext(), leftSpace);
        return this;
    }
    public TitleLayout setRightSpace(int rightSpace){
        this.rightSpace = DensityUtil.dip2px(getContext(), rightSpace);
        return this;
    }
    public TitleLayout setCenterRowSpace(int centerRowSpace){
        this.centerRowSpace = DensityUtil.dip2px(getContext(), centerRowSpace);
        return this;
    }









}
