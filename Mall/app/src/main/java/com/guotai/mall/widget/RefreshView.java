package com.guotai.mall.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

import com.guotai.mall.R;

import java.util.Calendar;

/**
 * Created by ez on 2017/6/20.
 */

public class RefreshView extends LinearLayout {

    TextView status_tv, refresh_time;
    ProgressBar progressBar;
    Context context;
    float lastY;
    int SCREEN_HEIGHT;
    Scroller scroller;
    int mHeight = 200;
    public static final int PREPARE = 0;
    public static final int WILLREFRESH = 1;
    public static final int REFRESHING = 2;
    public static final int END = 3;
    private int status;
    private int delayTime = 2000;
    boolean inControl;//临界点过度控制，由listview或者其他部分下拉到顶，触发滑动效果时用到
    OnRefshing onRefshing;
    boolean isTopHidden;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

    public RefreshView(Context context) {
        super(context);
        init(context);
    }

    public RefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        SCREEN_HEIGHT = context.getResources().getDisplayMetrics().heightPixels;
        setOrientation(VERTICAL);

        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(HORIZONTAL);
        ll.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeight));
        ll.setGravity(Gravity.CENTER);
        progressBar = new ProgressBar(context);
        progressBar.setVisibility(View.GONE);
        ll.addView(progressBar);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(VERTICAL);
        linearLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        status_tv = new TextView(context);
        LayoutParams param = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        param.setMargins(20, 0, 30, 0);
        status_tv.setLayoutParams(param);
        status_tv.setTextColor(context.getResources().getColor(R.color.colorBlack));
        status_tv.setText("下拉刷新");
        status_tv.setTextSize(16);
        status_tv.setGravity(Gravity.CENTER);
        linearLayout.addView(status_tv);
        refresh_time = new TextView(context);
        refresh_time.setLayoutParams(param);

        refresh_time.setText("刷新时间：");
        refresh_time.setTextSize(12);
        refresh_time.setVisibility(View.GONE);
        linearLayout.addView(refresh_time);
        ll.addView(linearLayout);
        addView(ll);

        scrollTo(0, mHeight);
        scroller = new Scroller(context);
        status = PREPARE;
    }

    public void setDelayTime(int time){
        this.delayTime = time;
    }

    public void setOnRefshing(OnRefshing onRefshing){
        this.onRefshing = onRefshing;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams params = getChildAt(1).getLayoutParams();
        params.height = getMeasuredHeight();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = ev.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                View view = getChildAt(1);
                float dy = ev.getY()-lastY;
                Log.e("zp", "坐标："+ev.getY());
                //作用于下面的组件滑动到最顶端，重新分发事件响应刷新动作
                if(dy>0){
                    if(view instanceof ListView){
                        ListView lv = (ListView)view;
                        View c = lv.getChildAt(lv.getFirstVisiblePosition());
                        if(c!=null && c.getTop()==0 && !inControl){
                            inControl = true;
                            ev.setAction(MotionEvent.ACTION_CANCEL);
                            MotionEvent ev2 = MotionEvent.obtain(ev);
                            dispatchTouchEvent(ev);
                            ev2.setAction(MotionEvent.ACTION_DOWN);
                            return dispatchTouchEvent(ev2);
                        }
                    }else if(view instanceof ScrollView){
                        ScrollView sv = (ScrollView)view;
                        if(sv.getScrollY()==0 && !inControl){
                            inControl = true;
                            ev.setAction(MotionEvent.ACTION_CANCEL);
                            MotionEvent ev2 = MotionEvent.obtain(ev);
                            dispatchTouchEvent(ev);
                            ev2.setAction(MotionEvent.ACTION_DOWN);
                            return dispatchTouchEvent(ev2);
                        }
                    }else if(view instanceof GridView){
                        GridView gv = (GridView)view;
                        View c = gv.getChildAt(gv.getFirstVisiblePosition());
                        if(c!=null && c.getTop()==0 && !inControl){
                            inControl = true;
                            ev.setAction(MotionEvent.ACTION_CANCEL);
                            MotionEvent ev2 = MotionEvent.obtain(ev);
                            dispatchTouchEvent(ev);
                            ev2.setAction(MotionEvent.ACTION_DOWN);
                            return dispatchTouchEvent(ev2);
                        }
                    }
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_MOVE:
                View view = getChildAt(1);
                float dy = ev.getY()-lastY;
                Log.e("zp", "坐标1："+ev.getY());
                //下拉且下面组件为顶端时，阻断事件向下层传递，直接触发本身的touch事件
                if(dy>0){
                    if(view instanceof ListView){
                        ListView lv = (ListView)view;
                        View c = lv.getChildAt(lv.getFirstVisiblePosition());
                        if(c!=null && c.getTop()==0 && inControl){
                            return true;
                        }
                    }else if(view instanceof ScrollView){
                        ScrollView sv = (ScrollView)view;
                        if(sv.getScrollY()==0  && inControl){
                            return true;
                        }
                    }else if(view instanceof GridView){
                        GridView gv = (GridView)view;
                        View c = gv.getChildAt(gv.getFirstVisiblePosition());
                        if(c!=null && c.getTop()==0 && inControl){
                            return true;
                        }
                    }else if(view instanceof ViewPager){
                        return true;
                    }
                }
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                status = PREPARE;
                return true;

            case MotionEvent.ACTION_MOVE:
                //加入判断，防止多次刷新，同时也不影响界面的滑动
                if(status!=REFRESHING){
                    float disY = event.getY()-lastY;
                    lastY = event.getY();
                    Log.e("zp", "坐标2："+event.getY());
                    int ty = getScrollY();

                    scrollBy(0, -(int)disY/2);
                    if(getScrollY()<10){
                        status_tv.setText("松开手刷新");
                        status = WILLREFRESH;
                    }else{
                        status_tv.setText("下拉刷新");
                        status = PREPARE;
                    }

                    //作用于刷新动作上滑取消，重新分发事件，响应下面组件的滑动效果
                    if(disY<0 && mHeight == getScrollY()){
                        event.setAction(MotionEvent.ACTION_DOWN);
                        dispatchTouchEvent(event);
                        inControl = false;
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                if(status==PREPARE){
                    scroller.startScroll(0, getScrollY(), 0, mHeight-getScrollY(), 200);
                    status_tv.setText("下拉刷新");
                    status = END;
                }
                else if(status == WILLREFRESH){
                    status = REFRESHING;
                    scroller.startScroll(0, getScrollY(), 0, -getScrollY(), 200);
                    progressBar.setVisibility(View.VISIBLE);
                    status_tv.setText("正在刷新...");
                    if(onRefshing!=null){
                        onRefshing.refresh();
                    }
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            stopRefresh();
                        }
                    }, delayTime);
                }
                inControl = false;
                break;

            case MotionEvent.ACTION_CANCEL:
                if(scroller.isFinished()){
                    scroller.abortAnimation();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(0, scroller.getCurrY());
        }
        invalidate();
    }

    /**
     * scrollBy最终还是依靠scrollTo实现，这里的条件可以屏蔽上滑导致整个页面往上走，下面出现空白
     * 便于处理事件的重新分发过程
     * @param x
     * @param y
     */
    @Override
    public void scrollTo(int x, int y) {

        if (y > mHeight) {
            y = mHeight;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }

        isTopHidden = getScrollY() == mHeight;

    }

    private String getTime(){
        Calendar calendar=Calendar.getInstance();
        return "上次刷新:"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.DAY_OF_MONTH)+" "+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND);
    }

    public void setTime(String time){
        refresh_time.setVisibility(View.VISIBLE );
        refresh_time.setText(time);
    }

    public void startRefresh(){
        if(status!=REFRESHING){
            status = REFRESHING;
            scroller.startScroll(0, getScrollY(), 0, -getScrollY(), 200);
            status_tv.setText("正在刷新...");
            progressBar.setVisibility(View.VISIBLE);
            if(onRefshing!=null){
                onRefshing.refresh();
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    stopRefresh();
                }
            }, delayTime);
        }
    }

    public void stopRefresh(){
        if(status==REFRESHING){
            progressBar.setVisibility(View.GONE);
            scroller.startScroll(0, getScrollY(), 0, mHeight-getScrollY(), 200);
            status_tv.setText("下拉刷新");
            status = END;
            handler.removeCallbacksAndMessages(null);
        }
    }

    public interface OnRefshing{
        void refresh();
    }
}
