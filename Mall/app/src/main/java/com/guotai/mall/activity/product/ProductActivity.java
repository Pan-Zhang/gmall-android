package com.guotai.mall.activity.product;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.guotai.mall.Adapter.ProAttrAdapter;
import com.guotai.mall.Adapter.ProductAdapter;
import com.guotai.mall.MainActivity;
import com.guotai.mall.MyApplication;
import com.guotai.mall.R;
import com.guotai.mall.activity.login.LoginActivity;
import com.guotai.mall.activity.makeOrder.MakeOrderActivity;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.model.Address;
import com.guotai.mall.model.CarPro;
import com.guotai.mall.model.OrderEx;
import com.guotai.mall.model.Product;
import com.guotai.mall.model.ProductDetail;
import com.guotai.mall.model.ProductEx;
import com.guotai.mall.model.ProductImage;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.widget.ProductBigImage;
import com.guotai.mall.widget.ViewPagerIndicator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by ez on 2017/6/16.
 */

public class ProductActivity extends BaseActivity<ProductPresent> implements IProductactivity, View.OnClickListener{

    public int ADD_TO_CAR = 0;
    public int BUY_NOW = 1;
    ViewPager product_vp;
    ProductAdapter productAdapter;
    TextView product_name, product_price, title;
    ImageView left_iv;
    Button my_collection, buycar;
    TextView add_to_car, buy_now;
    public static ProductEx product;
    private Dialog dialog;
    LinearLayout attr_ll, pic_ll;
    ViewPagerIndicator indicator;
    Button count;
    Map<String, Object> choose_attr;
    List<ProductImage> head_images;
    List<ProductImage> footer_images;
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        present = new ProductPresent(this);
        setContentView(R.layout.activity_product);
        if(product==null)finish();
        initTitle();
        init();
    }

    private void initTitle() {
        ImageView left_iv = (ImageView) findViewById(R.id.left_iv);
        left_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        left_iv.setVisibility(View.VISIBLE);
        title = (TextView) findViewById(R.id.title);
        title.setText(R.string.str_product_detail);
        ImageView right_iv = (ImageView) findViewById(R.id.right_iv);
        right_iv.setBackgroundResource(R.mipmap.share);
        right_iv.setOnClickListener(this);
    }

    public void init(){
        left_iv = (ImageView) findViewById(R.id.left_iv);
        left_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        product_vp = (ViewPager) findViewById(R.id.product_vp);
        head_images = new ArrayList<>();
        footer_images = new ArrayList<>();
        for(int i=0; i<product.ProductImage.size(); i++){
            ProductImage productImage = product.ProductImage.get(i);
            if(productImage.getImageCategoryID()==1){
                head_images.add(productImage);
            }
            else{
                footer_images.add(productImage);
            }
        }
        productAdapter = new ProductAdapter(this, (head_images==null || head_images.size()==0)?null:head_images);
        product_vp.setAdapter(productAdapter);
        product_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                indicator.setSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        indicator = (ViewPagerIndicator) findViewById(R.id.indicator);
        indicator.setLength(head_images.size());

        ImageView off_shelf = (ImageView) findViewById(R.id.off_shelf);
        if(product.ProductStatusID==2){
            off_shelf.setVisibility(View.VISIBLE);
        }
        else{
            off_shelf.setVisibility(View.GONE);
        }

        product_name = (TextView) findViewById(R.id.product_name);
        product_name.setText(product.ProductName);

        product_price = (TextView) findViewById(R.id.product_price);
        product_price.setText("¥" + Common.get2Digital(product.getPrice()));

        attr_ll = (LinearLayout) findViewById(R.id.attr_ll);

        int count = product.ProductPubAttr.size();
        if(count%2==0){
            for(int i=0; i<count/2; i++){
                Set set = product.ProductPubAttr.entrySet();
                Iterator iterator = set.iterator();
                int j=0;
                LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_attr, null);
                TextView attr_name = (TextView) linearLayout.findViewById(R.id.attr_name);
                TextView attr_number = (TextView) linearLayout.findViewById(R.id.attr_number);
                TextView attr_name_right = (TextView) linearLayout.findViewById(R.id.attr_name_right);
                TextView attr_number_right = (TextView) linearLayout.findViewById(R.id.attr_number_right);
                while (iterator.hasNext()) {
                    Map.Entry me = (Map.Entry) iterator.next();
                    if(j==i*2){
                        attr_name.setText(product.ProductAttrNames.get(String.valueOf(me.getKey()))+":");
                        attr_number.setText(String.valueOf(me.getValue()));
                    }
                    if(j==i*2+1){
                        attr_name_right.setText(product.ProductAttrNames.get(String.valueOf(me.getKey()))+":");
                        attr_number_right.setText(String.valueOf(me.getValue()));
                    }
                    j++;
                }
                attr_ll.addView(linearLayout);
            }
        }
        else{
            for(int i=0; i<count/2+1; i++){
                Set set = product.ProductPubAttr.entrySet();
                Iterator iterator = set.iterator();
                int j=0;
                LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_attr, null);
                TextView attr_name = (TextView) linearLayout.findViewById(R.id.attr_name);
                TextView attr_number = (TextView) linearLayout.findViewById(R.id.attr_number);
                TextView attr_name_right = (TextView) linearLayout.findViewById(R.id.attr_name_right);
                TextView attr_number_right = (TextView) linearLayout.findViewById(R.id.attr_number_right);
                while (iterator.hasNext()) {
                    Map.Entry me = (Map.Entry) iterator.next();
                    if(j==i*2){
                        attr_name.setText(product.ProductAttrNames.get(String.valueOf(me.getKey()))+":");
                        attr_number.setText(String.valueOf(me.getValue()));
                    }
                    if(j==i*2+1){
                        attr_name_right.setText(product.ProductAttrNames.get(String.valueOf(me.getKey()))+":");
                        attr_number_right.setText(String.valueOf(me.getValue()));
                    }
                    j++;
                }
                attr_ll.addView(linearLayout);
            }
        }

        pic_ll = (LinearLayout) findViewById(R.id.pic_ll);
        for(int i=0; i<footer_images.size(); i++){
            ProductBigImage productBigImage = new ProductBigImage(this);
            Picasso.with(this).load(footer_images.get(i).getImagePath()).resize(720, 720).centerInside().into(productBigImage);
            pic_ll.addView(productBigImage);
        }

        my_collection = (Button) findViewById(R.id.my_collection);
        my_collection.setOnClickListener(this);
        buycar = (Button) findViewById(R.id.buycar);
        buycar.setOnClickListener(this);
        add_to_car = (TextView) findViewById(R.id.add_to_car);
        add_to_car.setOnClickListener(this);
        buy_now = (TextView) findViewById(R.id.buy_now);
        buy_now.setOnClickListener(this);

    }

    public void show(final int type){
        if(dialog==null){
            dialog = new Dialog(this,R.style.ActionSheetDialogStyle);
        }
        View add_view = LayoutInflater.from(this).inflate(R.layout.layout_add_car, null);
        add_view.setMinimumWidth(10000);

        final ImageView image = (ImageView) add_view.findViewById(R.id.image);
        Picasso.with(this).load(product.FirstImage).resize(300, 300).centerInside().into(image);

        final TextView price_tv = (TextView) add_view.findViewById(R.id.price_tv);
        price_tv.setText(Common.get2Digital(product.getPrice()));

        ImageView close = (ImageView) add_view.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        ListView product_attr_lv = (ListView) add_view.findViewById(R.id.product_attr_lv);
        final ProAttrAdapter proAttrAdapter = new ProAttrAdapter(product, this);
        proAttrAdapter.setChooseProAttr(new ProAttrAdapter.ChooseProAttr() {
            @Override
            public void Click() {
                Map<String, String> choose_map = proAttrAdapter.getChoose_map();
                Set set = choose_map.entrySet();
                Iterator iterator = set.iterator();
                List<Map<String, Object>> all = product.ProductDetail;
                List<Map<String, Object>> tem = new ArrayList<>();

//                while (iterator.hasNext()) {
//                    Map.Entry me = (Map.Entry) iterator.next();
//                    if(TextUtils.isEmpty(String.valueOf(me.getValue()))){
//                        Common.showToastShort("请选择相关属性");
//                        return;
//                    }
//                }

                while (iterator.hasNext()) {
                    Map.Entry me = (Map.Entry) iterator.next();
                    for(int i=0; i<all.size(); i++){
                        Map<String, Object> m = all.get(i);
                        if(me.getValue().equals(m.get(me.getKey()))){
                            tem.add(m);
                        }
                    }
                    all = tem;
                    tem = new ArrayList<>();
                }
                if(all.size()>0){
                    choose_attr = all.get(0);
                    price_tv.setText(Common.get2Digital(Float.parseFloat(String.valueOf(choose_attr.get("Price")))));
                    List<Map<String, String>> list = (List<Map<String, String>>)choose_attr.get("ProductDetailImage");
                    Picasso.with(ProductActivity.this).load(list.get(0).get("ImagePath")).resize(300, 300).centerInside().into(image);
                }
            }
        });
        product_attr_lv.setAdapter(proAttrAdapter);

        if(proAttrAdapter.getCount()==0){
            choose_attr = product.ProductDetail.get(0);
        }
        if(product.ProductFlexAttr==null || product.ProductFlexAttr.size()==0){
            product_attr_lv.setVisibility(View.GONE);
        }

        count = (Button) add_view.findViewById(R.id.count);

        Button ensure = (Button) add_view.findViewById(R.id.ensure);
        ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> choose_map = proAttrAdapter.getChoose_map();
                Set set = choose_map.entrySet();
                Iterator iterator = set.iterator();
                List<Map<String, Object>> all = product.ProductDetail;
                List<Map<String, Object>> tem = new ArrayList<>();

                while (iterator.hasNext()) {
                    Map.Entry me = (Map.Entry) iterator.next();
                    if(TextUtils.isEmpty(String.valueOf(me.getValue()))){
                        Common.showToastShort("请选择相关属性");
                        return;
                    }
                }

                while (iterator.hasNext()) {
                    Map.Entry me = (Map.Entry) iterator.next();
                    for(int i=0; i<all.size(); i++){
                        Map<String, Object> m = all.get(i);
                        if(me.getValue().equals(m.get(me.getKey()))){
                            tem.add(m);
                        }
                    }
                    all = tem;
                    tem = new ArrayList<>();
                }
                if(type==1){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("UserID", Common.getUserID());
                    map.put("ProductID", product.ProductID);
                    map.put("ProductSubID", String.valueOf((int)Float.parseFloat(String.valueOf(all.get(0).get("ProductSubID")))));
                    map.put("Price", Common.get2Digital(product.getPrice()));
                    map.put("Qty", count.getText().toString());
                    present.addToCar("api/ShopCart/AddShopCart", map, ProductActivity.class.getSimpleName());
                }
                else{
                    present.getAddress("api/UserReceiver/GetUserReceiverList?UserID="+Common.getUserID(), ProductActivity.this.getClass().getSimpleName());

                }
            }
        });

        Button del = (Button) add_view.findViewById(R.id.del);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = Integer.parseInt(count.getText().toString())-1;
                if(num>0){
                    count.setText(String.valueOf(num));
                }
            }
        });

        Button add = (Button) add_view.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = Integer.parseInt(count.getText().toString())+1;
                if(num<100){
                    count.setText(String.valueOf(num));
                }
            }
        });

        dialog.setContentView(add_view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity( Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setAttributes(lp);
        dialog.show();
    }

    @Override
    public void refresh(ProductEx product) {
        this.product = product;
    }

    @Override
    public void addCar(boolean success) {
        if(success){
            dialog.dismiss();
            if(type==ADD_TO_CAR){

            }
            else if(type==BUY_NOW){
                MyApplication.getInstance().goCar = 1;
                finish();
            }
        }
        else{
            Common.showToastShort("添加失败");
        }
    }

    @Override
    public void updateAddress(List<Address> list) {
        if(list==null){
            Common.showToastShort("获取地址失败");
        }
        else{
            dialog.dismiss();
            List<CarPro> list_pro = new ArrayList<>();

            CarPro carPro = new CarPro();
            carPro.Qty = Integer.parseInt(count.getText().toString());
            carPro.FirstImage = product.FirstImage;
            carPro.ProductDescription = product.Description;
            carPro.setProductPrice(Float.parseFloat(String.valueOf(choose_attr.get("Price"))));
            carPro.ProductID = product.ProductID;
            carPro.ProductSubID = String.valueOf((int)Float.parseFloat(String.valueOf(choose_attr.get("ProductSubID"))));
            carPro.ProductName = product.ProductName;
            list_pro.add(carPro);

            MakeOrderActivity.list_pro = list_pro;
            MakeOrderActivity.list_address = list;
            startActivity(new Intent(ProductActivity.this, MakeOrderActivity.class));

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.right_iv:
                Intent share_intent = new Intent();

                share_intent.setAction(Intent.ACTION_SEND);

                share_intent.setType("text/plain");

                share_intent.putExtra(Intent.EXTRA_SUBJECT, "分享");

                share_intent.putExtra(Intent.EXTRA_TEXT, "http://www.equalcost.com");

                share_intent = Intent.createChooser(share_intent, "分享");

                startActivity(share_intent);
                break;

            case R.id.buy_now:
                type = BUY_NOW;
                if(TextUtils.isEmpty(Common.getUser())){
                    startActivity(new Intent(this, LoginActivity.class));
                }
                else if(product.ProductStatusID==2){
                    Common.showToastShort("该商品已下架");
                }
                else{
                    show(2);
                }
                break;

            case R.id.buycar:
                MyApplication.getInstance().goCar = 1;
                finish();
                break;

            case R.id.add_to_car:
                type = ADD_TO_CAR;
                if(TextUtils.isEmpty(Common.getUser())){
                    startActivity(new Intent(this, LoginActivity.class));
                }
                else if(product.ProductStatusID==2){
                    Common.showToastShort("该商品已下架");
                }
                else{
                    show(1);
                }

                break;

            case R.id.my_collection:
                if(product.ProductStatusID==2){
                    Common.showToastShort("该商品已下架");
                }
                else{
                    present.addToCollect(product, "api/Collect/AddCollect", getClass().getSimpleName());
                }
                break;
        }
    }
}
