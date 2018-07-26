package com.guotai.mall.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.guotai.mall.Adapter.HomeAdapter;
import com.guotai.mall.Adapter.HomeClickListener;
import com.guotai.mall.Adapter.HomePagerAdapter;
import com.guotai.mall.Adapter.HomePagerAdapter2;
import com.guotai.mall.Adapter.HorizontalAdapter;
import com.guotai.mall.R;
import com.guotai.mall.activity.CategorySearch.CategorySearchActivity;
import com.guotai.mall.activity.login.LoginActivity;
import com.guotai.mall.activity.message.MessageActivity;
import com.guotai.mall.activity.product.ProductActivity;
import com.guotai.mall.activity.qucode.QrCodeActivity;
import com.guotai.mall.activity.search.SearchActivity;
import com.guotai.mall.base.BaseFragment;
import com.guotai.mall.model.HotSpot;
import com.guotai.mall.model.HotspotInfoList;
import com.guotai.mall.model.News;
import com.guotai.mall.model.ProductEx;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.widget.HeaderLeftImage;
import com.guotai.mall.widget.HeaderRightImage;
import com.guotai.mall.widget.HomeHeadPager2;
import com.guotai.mall.widget.HorizontalListView;
import com.guotai.mall.widget.ViewPagerIndicator;
import com.guotai.mall.widget.VpSwipeRefreshLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ez on 2017/6/15.
 */

public class HomeFragment extends BaseFragment<HomePresent> implements IHomeFragment, ViewPager.OnPageChangeListener, View.OnClickListener, AbsListView.OnScrollListener {

    ListView home_lv;
    HomeAdapter homeAdapter;
    VpSwipeRefreshLayout refreshLayout;
    List<ProductEx> list = new ArrayList<ProductEx>();
    ViewPager viewPager;
    ViewPagerIndicator viewPagerIndicator;
    HomePagerAdapter homePagerAdapter;
    List<HotSpot> list_news;
    Button scan, message_btn;
    GoCategory goCategory;
    float HALF_WIDTH;
    TextView home_title;
    Button search_btn;
    TextView loadmore;
    int currentIndex = 2;
    LinearLayout head_ll;
    HorizontalListView horizontal;
    HorizontalAdapter horizontalAdapter;
    TextView title_tv1, title_tv2, title_tv3, content_tv1, content_tv2, content_tv3;
    HomeHeadPager2 pager1, pager2, pager3;
    HomePagerAdapter2 homePagerAdapter1, homePagerAdapter2, homePagerAdapter3;
    HeaderLeftImage image1;
    HeaderRightImage image2, image3;

    Handler handler = new Handler(new android.os.Handler.Callback(){

        @Override
        public boolean handleMessage(Message msg) {
            if(list_news.size()>0){
                int index = viewPager.getCurrentItem()+1;
                viewPager.setCurrentItem(index, true);
            }
            handler.sendEmptyMessageDelayed(0, 5000);
            return false;
        }
    });


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        present = new HomePresent(this);

        HALF_WIDTH = getResources().getDisplayMetrics().widthPixels/2;
        home_title = (TextView) rootView.findViewById(R.id.home_title);
        home_lv = (ListView) rootView.findViewById(R.id.home_lv);
        search_btn = (Button) rootView.findViewById(R.id.search_btn);
        search_btn.setOnClickListener(this);

        scan = (Button) rootView.findViewById(R.id.scan);
        scan.setOnClickListener(this);

        message_btn = (Button) rootView.findViewById(R.id.message_btn);
        message_btn.setOnClickListener(this);

        head_ll = (LinearLayout) rootView.findViewById(R.id.head_ll);

        initHeader();

        View footer = LayoutInflater.from(getContext()).inflate(R.layout.layout_footer, null);
        loadmore = (TextView) footer.findViewById(R.id.loadmore);
        loadmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadmore.setText("正在加载...");
                present.loaddata("api/Product/GetProductList?idxPage="+currentIndex+"&sizePage=20", true, getClass().getSimpleName());
            }
        });
        home_lv.addFooterView(footer);

        homeAdapter = new HomeAdapter(this.getContext(), list);
        home_lv.setAdapter(homeAdapter);
        home_lv.setOnScrollListener(this);
        present.loadhead("api/Hotspot/GetHotspotList", getClass().getSimpleName());
        present.loaddata("api/Product/GetProductList?idxPage=1&sizePage=20", false, getClass().getSimpleName());
        homeAdapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener(){

            @Override
            public void OnClick(View view) {
                int position = Integer.parseInt(view.getTag().toString());
                final ProductEx product = list.get(position);
                dialogUtils.showWaitDialog(getContext());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        present.GetDetail(Common.getProductDetailURL(product.ProductID, Common.getUserID()), getClass().getSimpleName());
                    }
                }, 100);
            }
        });
        refreshLayout = (VpSwipeRefreshLayout) rootView.findViewById(R.id.refresh);
        refreshLayout.setColorSchemeResources(R.color.colorWhite);
        refreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        refreshLayout.setProgressBackgroundColor(R.color.colorApp);
        refreshLayout.setProgressViewEndTarget(true, 200);
        refreshLayout.setVpSwipDraging(new VpSwipeRefreshLayout.VpSwipDraging() {
            @Override
            public void draging(boolean isDraging) {
                if(isDraging){
                    head_ll.setVisibility(View.INVISIBLE);
                }
                else{
                    head_ll.setVisibility(View.VISIBLE);
                }
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                present.loadhead("api/Hotspot/GetHotspotList", getClass().getSimpleName());
                currentIndex = 2;
                present.loaddata("api/Product/GetProductList?idxPage=1&sizePage=20", false, getClass().getSimpleName());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                        head_ll.setVisibility(View.VISIBLE);
                    }
                }, 10000);
            }
        });
        handler.sendEmptyMessageDelayed(0, 5000);
        return rootView;
    }

    HomeClickListener homeClickListener = new HomeClickListener() {
        @Override
        public void OnClick(HotspotInfoList hotspotInfoList) {
            dialogUtils.showWaitDialog(getContext());
            present.getData("api/Product/GetProductList?idxPage=1&sizePage=20&ProductLableID="+hotspotInfoList.HotspotLinkUrl, hotspotInfoList, HomeFragment.this.getClass().getSimpleName());
        }
    };

    private void initHeader() {
        View header = LayoutInflater.from(getContext()).inflate(R.layout.layout_header, null);
        viewPager = (ViewPager) header.findViewById(R.id.head_pager);
        viewPagerIndicator = (ViewPagerIndicator) header.findViewById(R.id.indicator);
        list_news = new ArrayList<HotSpot>();
        homePagerAdapter = new HomePagerAdapter(getContext(), null);
        homePagerAdapter.setHomeClickListener(homeClickListener);
        viewPager.setAdapter(homePagerAdapter);
        viewPager.addOnPageChangeListener(this);

        image1 = (HeaderLeftImage) header.findViewById(R.id.header_left);
        image2 = (HeaderRightImage) header.findViewById(R.id.header_right_top);
        image3 = (HeaderRightImage) header.findViewById(R.id.header_right_bottom);

        horizontal = (HorizontalListView) header.findViewById(R.id.horizontal);
        horizontal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                homeClickListener.OnClick(horizontalAdapter.list.get(position));
            }
        });
        horizontalAdapter = new HorizontalAdapter(getContext(), null);
        horizontal.setAdapter(horizontalAdapter);

        title_tv1 = (TextView) header.findViewById(R.id.title_tv1);
        title_tv2 = (TextView) header.findViewById(R.id.title_tv2);
        title_tv3 = (TextView) header.findViewById(R.id.title_tv3);

        content_tv1 = (TextView) header.findViewById(R.id.content_tv1);
        content_tv2 = (TextView) header.findViewById(R.id.content_tv2);
        content_tv3 = (TextView) header.findViewById(R.id.content_tv3);

        pager1 = (HomeHeadPager2) header.findViewById(R.id.pager1);
        homePagerAdapter1 = new HomePagerAdapter2(getContext(), null);
        homePagerAdapter1.setHomeClickListener(homeClickListener);
        pager1.setAdapter(homePagerAdapter1);

        pager2 = (HomeHeadPager2) header.findViewById(R.id.pager2);
        homePagerAdapter2 = new HomePagerAdapter2(getContext(), null);
        homePagerAdapter2.setHomeClickListener(homeClickListener);
        pager2.setAdapter(homePagerAdapter2);

        pager3 = (HomeHeadPager2) header.findViewById(R.id.pager3);
        homePagerAdapter3 = new HomePagerAdapter2(getContext(), null);
        homePagerAdapter3.setHomeClickListener(homeClickListener);
        pager3.setAdapter(homePagerAdapter3);

        home_lv.addHeaderView(header);
    }

    @Override
    public void refresh(List<ProductEx> list, List<HotSpot> list_news, boolean more) {
        refreshLayout.setRefreshing(false);
        head_ll.setVisibility(View.VISIBLE);
        loadmore.setText("点击加载更多");
        if(list!=null){
            if(more){
                ++currentIndex;
                this.list.addAll(list);
            }
            else{
                this.list = list;
            }
            homeAdapter.update(this.list);
        }
        if(list_news!=null){
            this.list_news = list_news;
            for(int i=0; i<list_news.size(); i++){
                HotSpot hotSpot = list_news.get(i);
                List<HotspotInfoList> hotspotInfoListList = hotSpot.HotspotInfoList;
                if(hotSpot.DispalayOrder==1){
                    if(homePagerAdapter==null){
                        homePagerAdapter = new HomePagerAdapter(getContext(), hotspotInfoListList);
                        viewPager.setAdapter(homePagerAdapter);
                    }else{
                        homePagerAdapter.update(hotspotInfoListList);
                    }
                    viewPagerIndicator.setLength(hotspotInfoListList.size());
                }
                if(hotSpot.DispalayOrder==2){
                    final HotspotInfoList hotspotInfoList = hotspotInfoListList.get(0);
                    image1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            homeClickListener.OnClick(hotspotInfoList);
                        }
                    });

                    Picasso.with(getContext()).load(hotspotInfoList.ImagePath).resize(720, 720).centerCrop().into(image1);
                }
                if(hotSpot.DispalayOrder==3){
                    final HotspotInfoList hotspotInfoList = hotspotInfoListList.get(0);
                    DisplayMetrics dm = getResources().getDisplayMetrics();
                    int width = dm.widthPixels/2-Common.dip2px(getContext(), 3);
                    int height = (width-Common.dip2px(getContext(), 6))/2;
                    Picasso.with(getContext()).load(hotspotInfoList.ImagePath).resize(width, height).centerCrop().into(image2);
                    image2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            homeClickListener.OnClick(hotspotInfoList);
                        }
                    });
                }
                if(hotSpot.DispalayOrder==4){
                    final HotspotInfoList hotspotInfoList = hotspotInfoListList.get(0);
                    DisplayMetrics dm = getResources().getDisplayMetrics();
                    int width = dm.widthPixels/2-Common.dip2px(getContext(), 3);
                    int height = (width-Common.dip2px(getContext(), 6))/2;
                    Picasso.with(getContext()).load(hotspotInfoList.ImagePath).resize(width, height).centerCrop().into(image3);
                    image3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            homeClickListener.OnClick(hotspotInfoList);
                        }
                    });
                }
                if(hotSpot.DispalayOrder==5){
                    horizontalAdapter.update(hotspotInfoListList);
                }
                if(hotSpot.DispalayOrder==6){
                    title_tv1.setText(hotSpot.HotspotTypeName);
                    content_tv1.setText(hotSpot.HotspotTypeDesc);
                    homePagerAdapter1.update(hotspotInfoListList);
                }
                if(hotSpot.DispalayOrder==7){
                    title_tv2.setText(hotSpot.HotspotTypeName);
                    content_tv2.setText(hotSpot.HotspotTypeDesc);
                    homePagerAdapter2.update(hotspotInfoListList);
                }
                if(hotSpot.DispalayOrder==8){
                    title_tv1.setText(hotSpot.HotspotTypeName);
                    content_tv1.setText(hotSpot.HotspotTypeDesc);
                    homePagerAdapter3.update(hotspotInfoListList);
                }
            }
        }
    }

    @Override
    public void refresh(List<ProductEx> list, boolean succ, HotspotInfoList hotspotInfoList) {
        dialogUtils.disMiss();
        if(succ){
            CategorySearchActivity.list_product = list;
            Intent intent = new Intent(getContext(), CategorySearchActivity.class);
            intent.putExtra("title", hotspotInfoList.HotspotName);
            startActivity(intent);
        }
        else{
            Common.showToastShort("无相关信息");
        }
    }

    @Override
    public void GotoDetail(ProductEx product) {
        dialogUtils.disMiss();
        if(product!=null){
            ProductActivity.product = product;
            startActivity(new Intent(getContext(), ProductActivity.class));
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        viewPagerIndicator.setSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.scan:
                startActivity(new Intent(getActivity(), QrCodeActivity.class));
                break;

            case R.id.message_btn:
                if(TextUtils.isEmpty(Common.getToken())){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }else{
                    startActivity(new Intent(getActivity(), MessageActivity.class));
                }
                break;

            case R.id.search_btn:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
        }

    }

    public void setGoCategory(GoCategory goCategory){
        this.goCategory = goCategory;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        int y = getScrollY(absListView);

        float alpha = y/HALF_WIDTH;
        if(alpha<0.9){
            home_title.setAlpha(alpha);
        }
        else{
            home_title.setAlpha((float)0.9);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeMessages(0);
    }

    public int getScrollY(AbsListView absListView) {
        View c = absListView.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = absListView.getFirstVisiblePosition();
        int top = c.getTop();
        return -top + firstVisiblePosition * c.getHeight() ;
    }

    public interface GoCategory{
        void Click();
    }

}
