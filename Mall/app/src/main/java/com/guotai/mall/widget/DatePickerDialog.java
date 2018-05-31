package com.guotai.mall.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.guotai.mall.R;
import com.guotai.mall.uitl.Common;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by zhangpan on 2018/4/30.
 */

public class DatePickerDialog extends Dialog {

    private final Context mContext;

    private DatePicker datePicker;
    private String dateStr;
    private int year, month, day;


    private DatePickerDialogListener mListener;

    public DatePickerDialog(Context context, String dateStr) {

        super(context, R.style.BottomAnimDialogStyle);
        this.mContext = context;
        this.dateStr = dateStr;
        String[] strs = dateStr.split("-");
        if(!TextUtils.isEmpty(dateStr) && strs.length>0){
            year = Integer.parseInt(strs[0]);
            if(strs[1].startsWith("0")){
                month = Integer.parseInt(strs[1].substring(1));
            }
            else{
                month = Integer.parseInt(strs[1]);
            }
            if(strs[2].startsWith("0")){
                day = Integer.parseInt(strs[2].substring(1));
            }
            else{
                day = Integer.parseInt(strs[2]);
            }
        }
        else{
            year = 2000;
            month = 1;
            day = 1;
        }
        initView();

    }

    private void initView() {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_datepicker, null);

        Window window = this.getWindow();
        if (window != null) { //设置dialog的布局样式 让其位于底部
            window.setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.y = Common.dip2px(mContext,10); //设置居于底部的距离
            window.setAttributes(lp);
        }

        Button ensure = (Button) view.findViewById(R.id.ensure);
        ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null){
                    mListener.ensure(dateStr);
                }
            }
        });
        Button cancel = (Button) view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        datePicker = (DatePicker) view.findViewById(R.id.dpPicker);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {

             @Override
             public void onDateChanged(DatePicker view, int year,
                                       int monthOfYear, int dayOfMonth) {
                 // 获取一个日历对象，并初始化为当前选中的时间
                 Calendar calendar = Calendar.getInstance();
                 calendar.set(year, monthOfYear, dayOfMonth);
                 SimpleDateFormat format = new SimpleDateFormat(
                                   "yyyy-MM-dd");
                 dateStr = format.format(calendar.getTime());
             }
        });

        setContentView(view);
    }

    public void setClickListener(DatePickerDialogListener listener) {
        this.mListener = listener;
    }

    public interface DatePickerDialogListener {
        void ensure(String date);
    }

}
