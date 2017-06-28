package com.jrfunclibrary.fileupload.uploadimage;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.jereibaselibrary.application.JrApp;
import com.jereibaselibrary.image.JRBitmapUtils;
import com.jereibaselibrary.image.JRSetImage;
import com.jereibaselibrary.tools.JRFileUtils;
import com.jrfunclibrary.activity.ImageViewPageActivity;
import com.jrfunclibrary.fileupload.presenter.UploadImagePresenter;
import com.jrfunclibrary.fileupload.view.ImageUpLoadView;
import com.jrfunclibrary.model.AttachmentModel;
import com.jruilibrary.widget.NoScrollGridView;
import com.sh.zsh.jrfunclibrary.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhush on 17-6-12.
 * Email:405086805@qq.com
 * Ps: 上传图片控件
 */

public class UploadImageView extends NoScrollGridView implements ImageUpLoadView {

    Context mContext;
    String path = JRFileUtils.getRootAppDirctory(JrApp.getContext());
    ImageGridViewAdapter imageGridViewAdapter;
    List<AttachmentModel> imageList = new ArrayList<>();
    UploadImagePresenter uploadImagePresenter;
    boolean isShow;
    public UploadImageView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);

        mContext = paramContext;
        TypedArray ta = mContext.obtainStyledAttributes(paramAttributeSet,R.styleable.UploadImageView,0,0);
        try {
            isShow = ta.getBoolean(R.styleable.UploadImageView_is_show,false);

        } finally {
            ta.recycle();
        }
        initView();
    }


    void initView() {

        imageGridViewAdapter = new ImageGridViewAdapter();
        uploadImagePresenter = new UploadImagePresenter(this);
        setAdapter(imageGridViewAdapter);
        setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == imageList.size()) {
                    //添加图片按钮
                    addPictrues("");
                } else {
                    addPictrues(imageList.get(i).getUrl());
                }

            }
        });
    }


    /**
     * gridView适配器
     * Created by zhush on 2016/9/18.
     */
    class ImageGridViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return imageList.size() + 1;
        }

        @Override
        public Object getItem(int position) {
            if(position==imageList.size()){
                return null;
            }else {
                return imageList.get(position);
            }

        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {

                convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_image_layout, parent, false);
                holder = new ViewHolder();
                holder.networkImageView = (NetworkImageView) convertView.findViewById(R.id.add_image_push);
                holder.delete = (TextView) convertView.findViewById(R.id.delete);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            AttachmentModel attachmentModel = (AttachmentModel) getItem(position);

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageList.remove(position);
                    notifyDataSetChanged();
                }
            });
            if (attachmentModel != null) {

                JRSetImage.setNetWorkListViewImage(mContext, attachmentModel.getUrl(), holder.networkImageView, R.drawable.add_image_push);
                holder.delete.setVisibility(View.VISIBLE);

            } else {
                JRSetImage.setNetWorkListViewImage(mContext, "", holder.networkImageView, R.drawable.add_image_push);
                holder.delete.setVisibility(View.GONE);
            }

            return convertView;
        }

    }

    static class ViewHolder {
        NetworkImageView networkImageView;
        TextView delete;

    }

    public List<AttachmentModel> getImageList() {
        return imageList;
    }

    public void setImageList(List<AttachmentModel> imageList) {
        this.imageList.clear();
        if(imageList==null){
            return;
        }
        this.imageList.addAll(imageList);
        imageGridViewAdapter.notifyDataSetChanged();
    }

    /**
     * 弹出 照片选择器
     */
    public void addPictrues(final String url) {
        String[] items = null;
        if (TextUtils.isEmpty(url)) {
            items = new String[]{"从相册选择", "拍照", "取消"};
        } else {
            items = new String[]{"从相册选择", "拍照", "查看图片", "取消"};
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("上传图片");
        builder.setItems(items,
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        switch (which) {
                            case 0:
                                Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                                pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                ((Activity) (mContext)).startActivityForResult(pickIntent, REQUESTCODE_PICK);
                                break;
                            case 1:
                                Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                                if (JRFileUtils.isSDAvailable()) {
                                    File path1 = new File(path);
                                    File file = new File(path1, System.currentTimeMillis() + ".jpg");
                                    iamgeUri = Uri.fromFile(file);
                                    imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, iamgeUri);
                                    ((Activity) (mContext)).startActivityForResult(imageCaptureIntent, REQUESTCODE_CAMERA);
                                } else {
//                                    showMessage(getString(R.string.func_no_sd_card));
                                }

                                break;
                            case 2:

                                if(TextUtils.isEmpty(url)){
                                    break;
                                }

                                String[] imageList = new String[1];
                                imageList[0] = url;
                                Intent intent = new Intent(mContext, ImageViewPageActivity.class);
                                intent.putExtra(ImageViewPageActivity.IMAGE_LIST, imageList);
                                intent.putExtra(ImageViewPageActivity.IMAGE_INDEX, 0);
                                ((Activity) (mContext)).startActivity(intent);


                                break;
                            default:
                                break;
                        }
                    }
                });
        builder.create().show();
    }



    /**
     * 显示图片
     */
    public void showPictrues(final String url) {
        String[]  items = new String[]{"查看图片", "取消"};

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("查看图片");
        builder.setItems(items,
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        switch (which) {

                            case 0:

                                String[] imageList = new String[1];
                                imageList[0] = url;
                                Intent intent = new Intent(mContext, ImageViewPageActivity.class);
                                intent.putExtra(ImageViewPageActivity.IMAGE_LIST, imageList);
                                intent.putExtra(ImageViewPageActivity.IMAGE_INDEX, 0);
                                ((Activity) (mContext)).startActivity(intent);


                                break;
                            default:
                                break;
                        }
                    }
                });
        builder.create().show();
    }
    /*
* 添加图片
*/
    public   int REQUESTCODE_PICK = 59001;
    public   int REQUESTCODE_CAMERA = 59003;

    /**
     * 如果一个也面有多个 上传图片控件 需要设置 请求码
     * @param requestcode_pick
     * @param requestcode_camera
     */
    public void setResultCode(int requestcode_pick,int requestcode_camera ){
        REQUESTCODE_PICK = requestcode_pick;
        REQUESTCODE_CAMERA = requestcode_camera;
    }
    public static Uri iamgeUri;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if(requestCode==REQUESTCODE_PICK){
            if (data == null || data.getData() == null) {
                return;
            }
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), data.getData());
                bitmap = JRBitmapUtils.compressByBitmapSize(bitmap);
                uploadImagePresenter.uploadImage(bitmap,null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(requestCode==REQUESTCODE_CAMERA){
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), iamgeUri);
                bitmap = JRBitmapUtils.compressByBitmapSize(bitmap);
                uploadImagePresenter.uploadImage(bitmap,null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }







    @Override
    public void showProgress(String message) {

    }

    @Override
    public void closeProgress() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void getDetails(Object objcet) {

    }

    @Override
    public void uploadImageSuccess(Bitmap bitmap, View view, AttachmentModel attachmentModel) {
        imageList.add(attachmentModel);
        imageGridViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void uploadImageFail(View v, String errorMessage) {
        Toast.makeText(mContext,"上传失败",Toast.LENGTH_SHORT).show();
    }

}
