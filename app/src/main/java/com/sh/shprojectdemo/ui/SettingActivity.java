package com.sh.shprojectdemo.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jereibaselibrary.image.JRBitmapUtils;
import com.jereibaselibrary.image.JRSetImage;
import com.jrfunclibrary.base.activity.BaseFormActivity;
import com.jrfunclibrary.fileupload.presenter.UploadImagePresenter;
import com.jrfunclibrary.fileupload.view.ImageUpLoadView;
import com.jrfunclibrary.model.AttachmentModel;
import com.jruilibarary.widget.CircleImageView;
import com.jruilibarary.widget.TemplateTitleBar;
import com.jruilibarary.widget.lineformview.LineFromView;
import com.jruilibarary.widget.lineformview.ViewData;
import com.jruilibrary.form.annotation.FormInjection;

import com.sh.shprojectdemo.R;
import com.sh.shprojectdemo.common.cache.TemporaryCache;
import com.sh.shprojectdemo.model.User;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhush on 2017/2/3
 * E-mail zhush@jerei.com
 * PS  设置页面
 */

public class SettingActivity extends BaseFormActivity implements ImageUpLoadView{

    @InjectView(R.id.template)
    TemplateTitleBar template;
    @InjectView(R.id.headImage)
    ImageView headImage;
    @FormInjection(name = "name",message = "姓名")
    @InjectView(R.id.name)
    LineFromView name;
    @FormInjection(name = "company")
    @InjectView(R.id.company)
    LineFromView company;
    @FormInjection(name = "department")
    @InjectView(R.id.department)
    LineFromView department;
    @FormInjection(name = "birthday")
    @InjectView(R.id.birthday)
    LineFromView birthday;
    @FormInjection(name = "sex")
    @InjectView(R.id.sex)
    LineFromView sex;
    @FormInjection(name = "sign",isNull = true)
    @InjectView(R.id.sign)
    EditText sign;

    UploadImagePresenter uploadImagePresenter;

    public   String imageUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.inject(this);
        initFormInjection();
        uploadImagePresenter = new UploadImagePresenter(this);
        intView();
        initData();
    }

    void intView(){

        template.setMoreTextContextAction("提交", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
        String url = "http://ww2.sinaimg.cn/bmiddle/43a39d58gw1ebqjvjr5onj20ea0e1ach";
        JRSetImage.setNetWorkImage(this, url, headImage);
        headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPictrues(true);
            }
        });
        ArrayList<ViewData> companys = new ArrayList<>();
        companys.add( new ViewData("百度",1));
        companys.add( new ViewData("阿里",2));
        company.setSelectedListener(new LineFromView.SelectedListener() {
            @Override
            public void pvOptions(String key, Object value) {
                ArrayList<ViewData> departments = new ArrayList<>();
                if(((int)value)==1){
                    departments.add( new ViewData("地图",11));
                    departments.add( new ViewData("买假药",12));
                }
                if(((int)value)==2){
                    departments.add( new ViewData("淘宝",21));
                    departments.add( new ViewData("天猫",22));
                }
                department.setpvOptionsList(departments);
            }
        });
        company.setpvOptionsList(companys);
    }
    void initData(){
        User user = TemporaryCache.getUserSession();
        objectToForm(user);
    }

    void submit(){
        User user = (User) formToObjectAndCheck(User.class);
        if(user!=null){
            user.setHeadImage(imageUrl);
        }
        showMessage("提交成功  "+user.toString());
    }

    @Override
    public void getPhonePhoto(Bitmap bitmap) {

        bitmap=  JRBitmapUtils.watermarkBitmap(bitmap,null,"水印") ;
        uploadImagePresenter.uploadImage(bitmap,headImage);
    }

    @Override
    public void uploadImageSuccess(Bitmap bitmap, View view, AttachmentModel attachmentModel) {
        headImage.setImageBitmap(bitmap);
        imageUrl = attachmentModel.getUrl();
    }

    @Override
    public void uploadImageFail(View v) {
        showMessage("上传失败");
    }
}
