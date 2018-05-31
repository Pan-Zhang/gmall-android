package com.guotai.mall.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.guotai.mall.Adapter.ReasonAdapter;
import com.guotai.mall.R;
import com.guotai.mall.model.ReturnReason;
import com.guotai.mall.uitl.Common;

import java.util.List;

/**
 * Created by zhangpan on 2018/4/30.
 */

public class BackMoneyReasonDialog extends Dialog implements View.OnClickListener {

    private final Context mContext;
    private List<ReturnReason> list;
    private EnsureClickListener ensureClickListener;
    int pos;

    public BackMoneyReasonDialog(Context context, List<ReturnReason> list) {

        super(context, R.style.BottomAnimDialogStyle);
        this.mContext = context;
        this.list = list;
        initView();

    }

    public void setEnsureClickListener(EnsureClickListener ensureClickListener){
        this.ensureClickListener = ensureClickListener;
    }


    private void initView() {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_backmoney_reason, null);

//        Window window = this.getWindow();
//        if (window != null) { //设置dialog的布局样式 让其位于底部
//            window.setGravity(Gravity.BOTTOM);
//            WindowManager.LayoutParams lp = window.getAttributes();
//            lp.y = Common.dip2px(mContext,10); //设置居于底部的距离
//            window.setAttributes(lp);
//        }

        ImageView close = (ImageView) view.findViewById(R.id.close);
        close.setOnClickListener(this);
        ListView reason_lv = (ListView) view.findViewById(R.id.reason_lv);
        final ReasonAdapter reasonAdapter = new ReasonAdapter(mContext, list);
        reason_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                for(int i=0; i<list.size(); i++){
                    if(i==position){
                        list.get(i).choosed = true;
                    }
                    else{
                        list.get(i).choosed = false;
                    }
                }
                reasonAdapter.notifyDataSetChanged();
            }
        });
        reason_lv.setAdapter(reasonAdapter);
        Button ensure = (Button) view.findViewById(R.id.ensure);
        ensure.setOnClickListener(this);
        setContentView(view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.close:
                dismiss();
                break;

            case R.id.ensure:
                if(ensureClickListener!=null){
                    ensureClickListener.ensure(pos);
                }
                dismiss();
                break;
        }
    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        getWindow().setAttributes(layoutParams);
    }

    public interface EnsureClickListener{
        void ensure(int pos);
    }
}
