# ShProjectDemo
android 开发框架

## Android项目依赖框架
* jrbaselibrary   基础框架
* jrUIlibrary   UI框架
* jrFunclibrary   功能框架


## 依赖关系

![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/1.png)
## 框架详细
### jrbaselibrary   基础框架  [API下载](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/doc/jrbase.rar)
封装 基础数据操作
* 1:缓存  （DiskLruCache，LruCache，SharedPreferences,本地文件缓存） http://blog.csdn.net/guolin_blog/article/details/28863651?utm_source=tuicool&utm_medium=referral
* 2:网络操作 (Retrofit 2.0,okhttp,兼容自有https证书）
* 3:数据库操作(litepal)   http://www.jianshu.com/p/557682e0a9f0
* 4:json解析
* 5:网络图片加载,图片处理  (glide) http://blog.csdn.net/fancylovejava/article/details/44747759
* 6:工具类
* 7:数据绑定 （butterknife,Data binding） http://www.jianshu.com/p/b1df61a4df77
* 8:数据加密 （DES,MD5,RSA）
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
* [Navigation View ]((https://github.com/hui46226021/ShProjectDemo/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/fragment/HomeFragment.java))     抽屉导航（官方）
* [JsBridge](https://github.com/lzyzsd/JsBridge)             H5原生交互webview（在其基础上 加入 屏蔽alert web 缓存 进度条 url监听）
* [formUtiks](http://zhush.xyz/2017/03/17/ShFormLayout/)            表单映射工具 重点 重点 重点
* [TabRadioView ](https://github.com/hui46226021/ShProjectDemo/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/MainActivity.java)           底部Tab菜单 带红点提示
* [LayerListView](https://github.com/hui46226021/ShProjectDemo/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/layerListViewActivity.java)           带Tab层级listview


[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/IOSAlertDialog.gif)](https://github.com/hui46226021/ShProjectDemo/blob/master/jr_func_library/src/main/java/com/jrfunclibrary/base/activity/BaseActivity.java)
[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/OptionsPickerView.gif)](http://zhush.xyz/2017/03/17/Android-PickerView/)
[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/TimePickerView.gif)](http://zhush.xyz/2017/03/17/Android-PickerView/)
[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/SideslipListView.gif)](https://github.com/hui46226021/ShProjectDemo/blob/master/app/src/main/java/com/sh/shprojectdemo/adapter/TestAdapter.java)


[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/LetterListView.gif)](https://github.com/hui46226021/ShProjectDemo/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/LetterListViewActivity.java)
[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/TouchPhotoView.gif)](https://github.com/hui46226021/ShProjectDemo/blob/master/jr_func_library/src/main/java/com/jrfunclibrary/activity/ImageViewPageActivity.java)
[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/CycleView.gif)](https://github.com/hui46226021/ShProjectDemo/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/fragment/HomeFragment.java)
[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/SpinnerDialog.gif)](https://github.com/hui46226021/ShProjectDemo/blob/master/app/src/main/java/com/sh/shprojectdemo/ui/fragment/HomeFragment.java)


[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/TabLayout1.gif)]((http://www.jianshu.com/p/2b2bb6be83a8) )
[![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/TabLayout2.gif)]((http://www.jianshu.com/p/2b2bb6be83a8) )
![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/RefreshLayout.gif)
![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/Navigation.gif)


![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/formUtiks.gif)
![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/TabRadioView.gif)
![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/LayerListView.gif)


###jrFunclibrary   封装通用功能（与业务逻辑无关）
* 版本检查更新
* 图片选择/拍摄上传  （包含压缩 水印）
* 音频录制
* 视频录制  压缩
* 视频播放
* pdf阅读
* 二维码扫描 生成
* 文件下载
* 启动页
* 基础 页面视图
* 图形解锁
* 掉线提醒














