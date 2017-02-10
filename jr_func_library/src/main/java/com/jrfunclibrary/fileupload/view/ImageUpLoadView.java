package com.jrfunclibrary.fileupload.view;

import android.graphics.Bitmap;
import android.view.View;

import com.jrfunclibrary.base.view.BaseView;
import com.jrfunclibrary.model.AttachmentModel;


/**
 * Created by zhush on 2016/10/8.
 * E-mail zhush@jerei.com
 * 图片上传视图
 */
public interface ImageUpLoadView extends BaseView {
    /**
     * 上传图片成功
     */
    public void uploadImageSuccess(Bitmap bitmap, View view, AttachmentModel attachmentModel);

    /**
     * 上传图片失败
     */
    public void uploadImageFail(View v,String errorMessage);

}
