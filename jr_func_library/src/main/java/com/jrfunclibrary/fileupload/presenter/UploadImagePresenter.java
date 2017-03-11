package com.jrfunclibrary.fileupload.presenter;

import android.graphics.Bitmap;
import android.view.View;

import com.jereibaselibrary.application.JrApp;
import com.jereibaselibrary.image.JRBitmapUtils;
import com.jereibaselibrary.tools.JRLogUtils;
import com.jrfunclibrary.fileupload.FileUpload;
import com.jrfunclibrary.fileupload.view.ImageUpLoadView;
import com.jrfunclibrary.model.AttachmentModel;
import com.sh.zsh.jrfunclibrary.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;


/**
 * Created by zhush on 2016/10/8.
 * E-mail 405086805@qq.com
 */
public class UploadImagePresenter {
    private ImageUpLoadView imageUpLoadView;

    public UploadImagePresenter(ImageUpLoadView imageUpLoadView) {
        this.imageUpLoadView = imageUpLoadView;
    }


    public void uploadImage(final Bitmap bitmap, final View v) {

        imageUpLoadView.showProgress("正在上传");

        /**
         * 转换成文件
         *
         */
        File file= null;
        try {
            file = JRBitmapUtils.saveFile(bitmap, "image" + ".png");

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(file ==null){
            imageUpLoadView.uploadImageFail(v, JrApp.getContext().getString(R.string.func_no_sd_card));
        }

        /**
         * 上传
         */

        final FileUpload fileUpload = new FileUpload("http://218.104.140.92:10039/xgls/app/common/fileUploadAction.do");
        fileUpload.uploadFile(file, new FileUpload.FileUploadListener() {
            @Override
            public void fileUploadProgress(int pro) {
            }
            @Override
            public void uploadFileSuccess(String  responseStr) {
                JRLogUtils.i(responseStr);
                imageUpLoadView.closeProgress();
                AttachmentModel   attachmentModel = new AttachmentModel();

                try {
                    JSONObject jsonObject = new JSONObject(responseStr);
                    JSONObject attachmentInfo = jsonObject.getJSONObject("attachmentInfo");
                    attachmentModel.setOriginalName(attachmentInfo.getString("originalName"));
                    attachmentModel.setFileName(attachmentInfo.getString("fileName"));
                    attachmentModel.setPathName(attachmentInfo.getString("pathName"));
                    attachmentModel.setFileType(attachmentInfo.getString("fileType"));
                    attachmentModel.setRealPath(attachmentInfo.getString("realPath"));
                    attachmentModel.setCreateUserName(attachmentInfo.getString("createUserName"));
                    attachmentModel.setCreateDate(attachmentInfo.getString("createDate"));
                    attachmentModel.setBasePath(jsonObject.getString("basePath"));
                    attachmentModel.setFileSize(attachmentInfo.getLong("fileSize"));
                    attachmentModel.setAttachTypeId(attachmentInfo.getLong("attachTypeId"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                imageUpLoadView.uploadImageSuccess(bitmap,v,attachmentModel);
            }
            @Override
            public void uploadFileFail(String resultStr) {
                imageUpLoadView.closeProgress();
                imageUpLoadView.uploadImageFail(v,resultStr);
            }
        });
    }
}
