# ProjectDemo
android 开发框架  项目结构采用MVP 架构

## Android项目依赖框架
* jrbaselibrary   基础框架
* jrUIlibrary   UI框架
* jrFunclibrary   功能框架


## 依赖关系

![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/1.png)
## 框架详细
### jrbaselibrary   基础框架  [API下载](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/doc/jrbase.rar)
封装 基础数据操作
* 1:缓存  （[DiskLruCache]( http://blog.csdn.net/guolin_blog/article/details/28863651?utm_source=tuicool&utm_medium=referral)，LruCache，[SharedPreferences](https://github.com/hui46226021/AndroidProject/blob/master/jrbaselibrary/src/main/java/com/jereibaselibrary/cache/SharedPreferencesTool.java),[本地文件缓存](https://github.com/hui46226021/AndroidProject/blob/master/jrbaselibrary/src/main/java/com/jereibaselibrary/cache/AppFileCache.java)）
* 2:网络操作 ([Retrofit 2.0](http://zhush.xyz/2017/01/01/Retrofit2.0/),okhttp,兼容自有https证书）
* 3:[数据库操作](http://www.jianshu.com/p/557682e0a9f0)(litepal)
* 4:[json解析](http://zhush.xyz/2017/03/18/JSON%E8%A7%A3%E6%9E%90/)
* 5:[网络图片加载,图片处理](http://blog.csdn.net/fancylovejava/article/details/44747759)  (glide)
* 6:[工具类](https://github.com/hui46226021/ShProjectDemo/tree/master/jrbaselibrary/src/main/java/com/jereibaselibrary/tools)
* 7:数据绑定 （butterknife,[Data binding](http://www.jianshu.com/p/b1df61a4df77))）
* 8:[数据加密 （DES,MD5,RSA）](https://github.com/hui46226021/ShProjectDemo/blob/master/jrbaselibrary/src/main/java/com/jereibaselibrary/encryption/EncryptUtils.java)
* 9:依赖注入IOC

### jrUIlibrary   UI控件  [API下载](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/doc/jrui.rar)
* [TemplateTitleBar](https://github.com/hui46226021/ShProjectDemo/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/LoginActivity.java)     顶部标题栏控件
* NoScrollListview     不可滑动的listView
* NoScrollGridView     不可滑动的GridView
* [SquareLayout](https://github.com/hui46226021/ShProjectDemo/blob/master/app/src/main/java/com/sh/shprojectdemo/adapter/SystemGridViewAdapter.java)         宽度和高度一样layout
* [CircleImageView](https://github.com/hui46226021/ShProjectDemo/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/LoginActivity.java)      圆型ImageView
* [RoundCornerImageView](https://github.com/hui46226021/ShProjectDemo/blob/master/app/src/main/res/layout/item_list.xml) 带圆角圆型ImageView
* [IOSAlertDialog](https://github.com/hui46226021/ShProjectDemo/blob/master/jr_func_library/src/main/java/com/jrfunclibrary/base/activity/BaseActivity.java)       仿IOS alert
* [TimePickerView ](http://zhush.xyz/2017/03/17/Android-PickerView/)      仿IOS 仿IOS 时间选择器
* [OptionsPickerView](http://zhush.xyz/2017/03/17/Android-PickerView/)    仿IOS 选择器（最多可以3级联动）
* [IOSSwitchButton](https://github.com/hui46226021/ShProjectDemo/blob/master/jr_ui_library/src/main/java/com/jruilibrary/widget/IOSSwitchButton.java)      仿IOS SwitchButton
* [SideslipListView](https://github.com/hui46226021/ShProjectDemo/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/UserListActivity.java)     仿IOS 可左滑listView
* [DragIndicatorView](https://github.com/hui46226021/ShProjectDemo/blob/master/app/src/main/java/com/sh/shprojectdemo/adapter/TestAdapter.java)    消息提示小红点（仿QQ可拖动）
* [UISearchView](https://github.com/hui46226021/ShProjectDemo/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/LetterListViewActivity.java)         搜索框
* [LetterListView](https://github.com/hui46226021/ShProjectDemo/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/LetterListViewActivity.java)       按字母分组listView
* [TouchPhotoView](https://github.com/hui46226021/ShProjectDemo/blob/master/jr_func_library/src/main/java/com/jrfunclibrary/activity/ImageViewPageActivity.java)       图片查看器 （可放滑动/双击放大缩小）
* [MyProgressDialog](https://github.com/hui46226021/ShProjectDemo/blob/master/jr_func_library/src/main/java/com/jrfunclibrary/base/activity/BaseActivity.java)     等待Dialog
* [CycleView](https://github.com/hui46226021/ShProjectDemo/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/fragment/HomeFragment.java)            自动滚动banner 图
* [SpinnerDialog](https://github.com/hui46226021/ShProjectDemo/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/fragment/HomeFragment.java)        底部弹出菜单
* [TabLayout](http://www.jianshu.com/p/2b2bb6be83a8)            viewPage 关联 tab（官方）
* [RefreshLayout](https://github.com/hui46226021/ShProjectDemo/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/UserListActivity.java)        上拉刷新组件（官方）
* [Navigation View](https://github.com/hui46226021/ShProjectDemo/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/fragment/HomeFragment.java)     抽屉导航（官方）
* [JsBridge](https://github.com/lzyzsd/JsBridge)             H5原生交互webview（在其基础上 加入 屏蔽alert web 缓存 进度条 url监听）
* [formUtiks](http://zhush.xyz/2017/03/17/ShFormLayout/)            表单映射工具 重点 重点 重点
* [TabRadioView ](https://github.com/hui46226021/ShProjectDemo/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/MainActivity.java)           底部Tab菜单 带红点提示
* [LayerListView](https://github.com/hui46226021/ShProjectDemo/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/layerListViewActivity.java)           带Tab层级listview


### jrFunclibrary   封装通用功能（与业务逻辑无关）
* [版本检查更新](https://github.com/hui46226021/AndroidProject/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/WelcomeActivity.java)
* 图片选择/拍摄上传  （包含压缩 水印）
* [音频录制](https://github.com/hui46226021/AndroidProject/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/AudioRecordActivity.java)
* [视频录制  压缩](https://github.com/hui46226021/AndroidProject/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/VideoRecordActivity.java)（模块化可分离）
* [视频播放](https://github.com/hui46226021/AndroidProject/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/ShVideoActivity.java)（模块化可分离）
* pdf阅读 （模块化可分离）
* 二维码扫描 生成
* 文件下载
* [启动页](https://github.com/hui46226021/AndroidProject/blob/master/jr_func_library/src/main/java/com/jrfunclibrary/bootpage/BootActivity.java)
* [基础 页面视图](https://github.com/hui46226021/AndroidProject/tree/master/jr_func_library/src/main/java/com/jrfunclibrary/base)
* 图形解锁
* [掉线提醒](https://github.com/hui46226021/AndroidProject/blob/master/jr_func_library/src/main/java/com/jrfunclibrary/base/activity/BaseActivity.java)


## 图片预览 点击图片可跳转到 相应代码

[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/IOSAlertDialog.gif)](https://github.com/hui46226021/ShProjectDemo/blob/master/jr_func_library/src/main/java/com/jrfunclibrary/base/activity/BaseActivity.java)
[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/OptionsPickerView.gif)](http://zhush.xyz/2017/03/17/Android-PickerView/)
[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/TimePickerView.gif)](http://zhush.xyz/2017/03/17/Android-PickerView/)
[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/SideslipListView.gif)](https://github.com/hui46226021/ShProjectDemo/blob/master/app/src/main/java/com/sh/shprojectdemo/adapter/TestAdapter.java)


[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/LetterListView.gif)](https://github.com/hui46226021/ShProjectDemo/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/LetterListViewActivity.java)
[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/TouchPhotoView.gif)](https://github.com/hui46226021/ShProjectDemo/blob/master/jr_func_library/src/main/java/com/jrfunclibrary/activity/ImageViewPageActivity.java)
[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/CycleView.gif)](https://github.com/hui46226021/ShProjectDemo/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/fragment/HomeFragment.java)
[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/SpinnerDialog.gif)](https://github.com/hui46226021/ShProjectDemo/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/fragment/HomeFragment.java)


[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/TabLayout1.gif)](http://www.jianshu.com/p/2b2bb6be83a8)
[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/TabLayout2.gif)](http://www.jianshu.com/p/2b2bb6be83a8)
[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/RefreshLayout.gif)](https://github.com/hui46226021/ShProjectDemo/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/UserListActivity.java)
[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/Navigation.gif)](https://github.com/hui46226021/ShProjectDemo/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/fragment/HomeFragment.java)


[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/formUtiks.gif)](https://github.com/hui46226021/ShProjectDemo/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/SettingActivity.java)
[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/TabRadioView.gif)](https://github.com/hui46226021/ShProjectDemo/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/MainActivity.java)
[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/LayerListView.gif)](https://github.com/hui46226021/ShProjectDemo/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/layerListViewActivity.java)
[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/audio_recording.gif)](https://github.com/hui46226021/AndroidProject/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/AudioRecordActivity.java)



[![](https://raw.githubusercontent.com/hui46226021/ShPhotoSelector/master/2.gif)]()
[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/videorecording1.png)](https://github.com/hui46226021/AndroidProject/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/VideoRecordActivity.java)
[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/videorecording2.png)](https://github.com/hui46226021/AndroidProject/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/VideoRecordActivity.java)
[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/videorecording3.gif)](https://github.com/hui46226021/AndroidProject/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/VideoRecordActivity.java)


[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/baidumap.gif)](https://github.com/hui46226021/AndroidProject/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/MapHomeActivity.java)
[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/baidu_navigation.gif)](https://github.com/hui46226021/AndroidProject/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/MapHomeActivity.java)
[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/fenxiang.gif)]()
[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/donghua.gif)]()




# 相关连接
* 项目架构采用MVP [http://blog.csdn.net/lmj623565791/article/details/46596109](http://blog.csdn.net/lmj623565791/article/details/46596109)
* 百度地图封装 [https://github.com/hui46226021/ShBaiduMapSDK](https://github.com/hui46226021/ShBaiduMapSDK)
* 第三方登录分享 [https://github.com/hui46226021/ShUmengSdk](https://github.com/hui46226021/ShUmengSdk)
* 腾讯IM封装 [https://github.com/hui46226021/ShIMLibrary](https://github.com/hui46226021/ShIMLibrary)
* 极光推送封装 [https://github.com/hui46226021/ShJpushSdk](https://github.com/hui46226021/ShJpushSdk)
* 视频播放器 [https://github.com/hui46226021/ShVideoPlay](https://github.com/hui46226021/ShVideoPlay)
* 视频录制压缩 [https://github.com/hui46226021/ShVideoDemo](https://github.com/hui46226021/ShVideoDemo)
* 二维码扫描 [https://github.com/hui46226021/ShQrCode)
* 图片选择器 [https://github.com/hui46226021/ShPhotoSelector)
* 阿里巴巴Java开发手册 [阿里巴巴Java开发手册](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/doc/阿里巴巴Java开发手册.pdf)


# 体验 Demo


![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/down.png)








