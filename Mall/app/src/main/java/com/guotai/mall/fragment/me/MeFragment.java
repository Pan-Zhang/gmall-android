package com.guotai.mall.fragment.me;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guotai.mall.Adapter.MeAdapter;
import com.guotai.mall.R;
import com.guotai.mall.activity.brower.BrowerActivity;
import com.guotai.mall.activity.collection.CollectionActivity;
import com.guotai.mall.activity.cropImage.CropImageActivity;
import com.guotai.mall.activity.help.HelpActivity;
import com.guotai.mall.activity.login.LoginActivity;
import com.guotai.mall.activity.managerAddress.ManagerActivity;
import com.guotai.mall.activity.myOrder.MyOrderActivity;
import com.guotai.mall.activity.mySuggest.MySuggestActivity;
import com.guotai.mall.activity.setting.SettingActivity;
import com.guotai.mall.base.BaseFragment;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.widget.BottomAnimDialog;
import com.guotai.mall.widget.CircleTransform;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ez on 2017/6/15.
 */

public class MeFragment extends BaseFragment<MePresent> implements IMefragment, View.OnClickListener{

    GridView me_grid;
    MeAdapter meAdapter;
    ImageView setting, avatar;
    LinearLayout myorder_ll, help_ll;
    Button wait_pay, wait_send, wait_receive, server;
    TextView username;
    BottomAnimDialog dialog;
    String mFilePath, mFile;
    private static final int RESULT_OK = -1;
    private static final int REQUEST_CAMERA = 0;
    private static final int FROM_PIC = 1;
    private static final int CROP_IMAGE = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_me, container, false);

        mFilePath = Common.AppPath();// 获取SD卡路径

        present = new MePresent(this);

        avatar = (ImageView) rootView.findViewById(R.id.avatar);
        Picasso.with(getActivity()).load(R.mipmap.head01).resize(200, 200).transform(new CircleTransform(getContext(), 3, R.color.colorWhite)).centerCrop().into(avatar);

        myorder_ll = (LinearLayout) rootView.findViewById(R.id.myorder_ll);
        myorder_ll.setOnClickListener(this);

        help_ll = (LinearLayout) rootView.findViewById(R.id.help_ll);
        help_ll.setOnClickListener(this);

        wait_pay = (Button) rootView.findViewById(R.id.wait_pay);
        wait_send = (Button) rootView.findViewById(R.id.wait_send);
        wait_receive = (Button) rootView.findViewById(R.id.wait_receive);
        server = (Button) rootView.findViewById(R.id.server);
        avatar = (ImageView) rootView.findViewById(R.id.avatar);
        avatar.setOnClickListener(this);
        wait_pay.setOnClickListener(this);
        wait_send.setOnClickListener(this);
        wait_receive.setOnClickListener(this);
        server.setOnClickListener(this);

        username = (TextView) rootView.findViewById(R.id.username);
        username.setOnClickListener(this);

        me_grid = (GridView) rootView.findViewById(R.id.me_grid);
        meAdapter = new MeAdapter(getActivity());
        me_grid.setAdapter(meAdapter);
        me_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(TextUtils.isEmpty(Common.getToken())){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }else {
                    switch (position) {
                        case 0:
                            startActivity(new Intent(getActivity(), ManagerActivity.class));
                            break;

                        case 1:
//                            startActivity(new Intent(getActivity(), SuggestActivity.class));
                            startActivity(new Intent(getActivity(), MySuggestActivity.class));
                            break;

                        case 2:
                            startActivity(new Intent(getActivity(), CollectionActivity.class));
                            break;

                        case 3:
                            startActivity(new Intent(getActivity(), BrowerActivity.class));
                            break;

                        case 4:
                            startActivity(new Intent(getActivity(), HelpActivity.class));
                            break;
                    }
                }

            }
        });

        setting = (ImageView) rootView.findViewById(R.id.setting);
        setting.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        String str = Common.getUser();
        if(TextUtils.isEmpty(str)){
            username.setText("注册/登录");
            Picasso.with(getContext()).load(R.mipmap.head01).resize(300, 300).centerCrop().into(avatar);
        }
        else{
            if(Common.isMobile(Common.getUser())){
                username.setText(str.replace(str.substring(3, 7), "****"));
            }
            else{
                username.setText(str);
            }

            if(TextUtils.isEmpty(Common.getAvatar())){
                Picasso.with(getContext()).load(R.mipmap.head01).resize(300, 300).centerCrop().transform(new CircleTransform(getContext())).into(avatar);
            }
            else{
                Picasso.with(getContext()).load(Common.getAvatar()).resize(300, 300).transform(new CircleTransform(getContext())).centerCrop().placeholder(R.mipmap.head01).into(avatar);
            }
        }
    }

    private void pickPicFromCam(){
        File file = new File(mFilePath);
        if(!file.exists()){
            file.mkdirs();
        }
        mFile = mFilePath + "/avatar.png";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
        Uri photoUri = Uri.fromFile(new File(mFile)); // 传递路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);// 更改系统默认存储路径
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void pickPicFromPic(){
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, FROM_PIC);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回数据
            if (requestCode == REQUEST_CAMERA) {
                CropImageActivity.uri = Uri.fromFile(new File(mFile));
            }
            else if(requestCode==FROM_PIC && data!=null){
                CropImageActivity.uri = data.getData();
            }
            startActivityForResult(new Intent(getContext(), CropImageActivity.class), CROP_IMAGE);
        }
        else if(resultCode == CROP_IMAGE){
            dialogUtils.showWaitDialog(getContext(), "上传头像...");
            String crop_path = data.getStringExtra("CROP_PATH");
            Map<String, String> map = new HashMap<>();
            map.put("UserID", Common.getUserID());

            Map<String, String> map_file = new HashMap<>();
            map_file.put("Imag", crop_path);
            present.uploadAvatar("api/Users/UploadHeadImage", map, map_file, getClass().getSimpleName());
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), MyOrderActivity.class);
        switch (view.getId()){
            case R.id.avatar:
                if(dialog==null){
                    dialog = new BottomAnimDialog(getContext(), "相册选取", "拍照", "取消");
                    dialog.setClickListener(new BottomAnimDialog.BottonAnimDialogListener() {
                        @Override
                        public void onItem1Listener() {
                            pickPicFromPic();
                            dialog.dismiss();
                        }

                        @Override
                        public void onItem2Listener() {
                            pickPicFromCam();
                            dialog.dismiss();
                        }

                        @Override
                        public void onItem3Listener() {
                            dialog.dismiss();
                        }
                    });
                }
                dialog.show();
                break;

            case R.id.setting:
            case R.id.username:
                if(TextUtils.isEmpty(Common.getToken())){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }else{
                    startActivity(new Intent(getActivity(), SettingActivity.class));
                }

                break;

            case R.id.myorder_ll:
                if(TextUtils.isEmpty(Common.getToken())){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }else{
                    intent.addFlags(0);
                    startActivity(intent);
                }
                break;

            case R.id.wait_pay:
                if(TextUtils.isEmpty(Common.getToken())){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }else{
                    intent.addFlags(0);
                    startActivity(intent);
                }
                break;

            case R.id.wait_receive:
                if(TextUtils.isEmpty(Common.getToken())){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }else{
                    intent.addFlags(2);
                    startActivity(intent);
                }
                break;

            case R.id.wait_send:
                if(TextUtils.isEmpty(Common.getToken())){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }else{
                    intent.addFlags(1);
                    startActivity(intent);
                }
                break;

            case R.id.server:
                if(TextUtils.isEmpty(Common.getToken())){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }else {
                    intent.addFlags(3);
                    startActivity(intent);
                }
                break;

            case R.id.help_ll:
                    startActivity(new Intent(getActivity(), HelpActivity.class));
                    break;
        }
    }

    @Override
    public void uploadSucce(boolean success) {
        dialogUtils.disMiss();
        if(success){
            if(TextUtils.isEmpty(Common.getAvatar())){
                Picasso.with(getContext()).load(R.mipmap.head01).resize(300, 300).centerCrop().transform(new CircleTransform(getContext())).into(avatar);
            }
            else{
                Picasso.with(getContext()).load(Common.getAvatar()).resize(300, 300).transform(new CircleTransform(getContext())).centerCrop().placeholder(R.mipmap.head01).into(avatar);
            }
            Common.showToastShort("上传头像成功");
        }
        else{
            Common.showToastShort("上传头像失败");
        }
    }
}
