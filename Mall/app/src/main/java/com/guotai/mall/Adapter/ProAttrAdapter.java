package com.guotai.mall.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.guotai.mall.R;
import com.guotai.mall.model.ProductDetail;
import com.guotai.mall.model.ProductEx;
import com.guotai.mall.widget.FlowLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by zhangpan on 2018/4/6.
 */

public class ProAttrAdapter extends BaseAdapter {

    private ProductEx productEx;
    private Context context;
    private Map<String, String> choose_map;
    private Map<String, List<String>> attr_map;
    private boolean init_bool=true;
    private ChooseProAttr chooseProAttr;

    public ProAttrAdapter(ProductEx productEx, Context context) {
        this.productEx = productEx;
        this.context = context;
        attr_map = new LinkedHashMap<>();
        choose_map = new LinkedHashMap<>();
        Set set = productEx.ProductFlexAttr.entrySet();
        Iterator iterator = set.iterator();
        int i=0;
        while (iterator.hasNext()) {
            Map.Entry me = (Map.Entry) iterator.next();
            if(i==0){
                List<String> l = (List<String>)me.getValue();
                choose_map.put(String.valueOf(me.getKey()), l.get(i));
                attr_map.put(String.valueOf(me.getKey()), null);
            }
            else{
                choose_map.put(String.valueOf(me.getKey()), "");
                attr_map.put(String.valueOf(me.getKey()), null);
            }
            i++;
        }
    }

    public void setChooseProAttr(ChooseProAttr chooseProAttr){
        this.chooseProAttr = chooseProAttr;
    }

    public Map<String, String> getChoose_map(){
        return choose_map;
    }

    @Override
    public int getCount() {
        return choose_map.size();
    }

    @Override
    public Object getItem(int position) {
        return choose_map.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_attr_item, null);
        }

        TextView attr_name = (TextView) convertView.findViewById(R.id.attr_name);
        attr_name.setText(String.valueOf(productEx.ProductAttrNames.get(getKeyByPos(choose_map, position))));

        List<String> attrValue_list = new ArrayList<>();
        initView(position, attrValue_list);

        if(chooseProAttr!=null){
            chooseProAttr.Click();
        }

        FlowLayout flowLayout = (FlowLayout) convertView.findViewById(R.id.attr_fl);
        flowLayout.removeAllViews();
        for (int i = 0; i < attrValue_list.size(); i++) {
            TextView tv = (TextView) LayoutInflater.from(context).inflate(
                    R.layout.search_label_tv, flowLayout, false);
            tv.setText(attrValue_list.get(i));
            tv.setTag(i);
            if(attrValue_list.get(i).equals(getValueByPos(choose_map, position))){
                tv.setTextColor(context.getResources().getColor(R.color.colorApp));
            }
            else {
                tv.setTextColor(context.getResources().getColor(R.color.colorGray));
            }
            final String str = tv.getText().toString();
            //点击事件
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    choose_map.put(getKeyByPos(choose_map, position), attr_map.get(getKeyByPos(choose_map, position)).get((int)v.getTag()));
                    Set set = choose_map.entrySet();
                    Iterator iterator = set.iterator();
                    int i = 0;
                    while (iterator.hasNext()) {
                        Map.Entry me = (Map.Entry) iterator.next();
                        if(i>position){
                            me.setValue("");
                        }
                        i++;
                    }
                    notifyDataSetChanged();
                }
            });
            flowLayout.addView(tv);
        }
        return convertView;
    }

    private void initView(int position, List<String> attrValue_list){
        String attr = "";
        if(position==0){

            attr = getKeyByPos(choose_map, 0);
            for(int i=0; i<productEx.ProductDetail.size(); i++){
                Map<String, Object> detail = productEx.ProductDetail.get(i);
                if(!TextUtils.isEmpty(String.valueOf(detail.get(attr))) && !attrValue_list.contains(detail.get(attr))){
                    attrValue_list.add(String.valueOf(detail.get(attr)));
                }
            }
            attr_map.put(getKeyByPos(choose_map, 0), attrValue_list);
        }
        else{
            List<Map<String, Object>> all = productEx.ProductDetail;
            for(int i=0; i<position; i++){
                String value = getValueByPos(choose_map, i);
                List<Map<String, Object>> tem = new ArrayList<>();
                for(int j=0; j<all.size(); j++){
                    Map<String, Object> detail = all.get(j);
                    if(detail.get(getKeyByPos(choose_map, i)).equals(value)){
                        tem.add(detail);
                    }
                }
                all = tem;
            }
            attr = getKeyByPos(choose_map, position);
            for(Map<String, Object> detail : all){
                if(detail.get(attr)!=null && !attrValue_list.contains(detail.get(attr))){
                    attrValue_list.add(String.valueOf(detail.get(attr)));
                }
            }
            attr_map.put(getKeyByPos(choose_map, position), attrValue_list);
            if(TextUtils.isEmpty(getValueByPos(choose_map, position)) && attrValue_list.size()>0){
                choose_map.put(getKeyByPos(choose_map, position), attrValue_list.get(0));
            }
        }
    }

//    private String getFieldValueByFieldName(String fieldName, Object object) {
//        try {
//            Field field = object.getClass().getDeclaredField(fieldName);
//            //设置对象的访问权限，保证对private的属性的访问
//            field.setAccessible(true);
//            return  (String)field.get(object);
//        } catch (Exception e) {
//
//            return null;
//        }
//    }

    private String getValueByPos(Map<String, String> map, int position){
        Set set = map.entrySet();
        Iterator iterator = set.iterator();
        int i=0;
        while (iterator.hasNext()) {
            Map.Entry me = (Map.Entry) iterator.next();
            if(i==position) {
                return (String)me.getValue();
            }
            i++;
        }
        return null;
    }

    private String getKeyByPos(Map<String, String> map, int position){
        Set set = map.entrySet();
        Iterator iterator = set.iterator();
        int i=0;
        while (iterator.hasNext()) {
            Map.Entry me = (Map.Entry) iterator.next();
            if(i==position) {
                return (String)me.getKey();
            }
            i++;
        }
        return null;
    }

    public interface ChooseProAttr{
        void Click();
    }
}
