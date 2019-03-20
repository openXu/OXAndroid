package com.openxu.oxlib.view.stocknew;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import com.openxu.oxlib.utils.DensityUtil;
import com.openxu.oxlib.view.chart.anim.AngleEvaluator;

public class VedioButton extends View {

    private String TAG = "VedioButton";

    private int color_out = Color.parseColor("#ffffff");//圆环颜色
    private int color_inner = Color.parseColor("#ff0000");  //中间圆圈颜色
    protected int width_out = DensityUtil.dip2px(getContext(), 1);//白色圆环宽度
    protected int ring_space = DensityUtil.dip2px(getContext(), 6);//白色和红色距离
    protected float zoomScale = .8f;//最小缩放占比
    private int animDuration = 1500;//动画执行时间
    /**计算*/
    protected int outRadius;     //外圈半径
    protected int innerRadius;   //内圈半径
    protected PointF centerPoint;//中心坐标

    private Paint paint;
    private long startTime;
    private float animPro;
    private ValueAnimator anim;
    private boolean started;   //是否正在录制

    public VedioButton(Context context) {
        this(context, null);
    }
    public VedioButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public VedioButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint = new Paint();
        paint.setAntiAlias(true);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!started) {
                    startTime = System.currentTimeMillis();
                    if(listener!=null)
                        listener.start();
                    startAnimation();
                }else{
                    if(anim!=null)
                        anim.cancel();
                    if(listener!=null)
                        listener.stop();
                    animPro = 0;
                    invalidate();
                }
                started = !started;
            }
        });
    }

    private Listener listener;
    public void setListener(Listener listener){
        this.listener = listener;
    }
    public interface Listener{
        void start();
        void stop();
        void progress(int time);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        outRadius = Math.min(widthSize, heightSize)/2;
        innerRadius = outRadius - ring_space;
        centerPoint = new PointF(widthSize/2, heightSize/2);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(color_out);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(width_out);
        canvas.drawCircle(centerPoint.x, centerPoint.y, outRadius - width_out/2, paint);

        paint.setColor(color_inner);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerPoint.x, centerPoint.y, innerRadius-(1-zoomScale)*innerRadius*animPro , paint);
    }


    protected synchronized void startAnimation() {
        if(anim!=null)
            anim.cancel();
        anim = ValueAnimator.ofObject(new AngleEvaluator(), 0f, 1f);
        anim.setInterpolator(new AccelerateInterpolator());  //匀速
        anim.addUpdateListener(animation -> {
            animPro = (float)animation.getAnimatedValue();
            if(listener!=null)
                listener.progress((int)(System.currentTimeMillis() - startTime));
            invalidate();
        });
        anim.setDuration(animDuration);
        anim.setRepeatMode(ValueAnimator.REVERSE); //反方向重复
        anim.setRepeatCount(10000);
        anim.start();
    }



}
