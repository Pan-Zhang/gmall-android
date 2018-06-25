package com.guotai.mall.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guotai.mall.R;
import com.guotai.mall.model.Complaint;
import com.guotai.mall.model.Suggestion;

import java.util.List;

/**
 * Created by zhangpan on 2018/6/22.
 */

public class SuggestDetailAdapter extends MyAdapter<Suggestion.SuggestionDetail> {

    public SuggestDetailAdapter(Context context, List<Suggestion.SuggestionDetail> list) {
        super(context, list);
    }

    @Override
    View createView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_suggest_detail_item, null);
        }
        Suggestion.SuggestionDetail suggestionDetail = list.get(position);
        TextView answer = (TextView) convertView.findViewById(R.id.answer);
        TextView time = (TextView) convertView.findViewById(R.id.time);
        TextView content = (TextView) convertView.findViewById(R.id.content);

        if(suggestionDetail.ReplySrc==1){
            answer.setText("客服回复");
        }
        else{
            answer.setText("客户回复");
        }

        time.setText(suggestionDetail.CreateTime);

        content.setText(suggestionDetail.Content);

        return convertView;
    }
}
