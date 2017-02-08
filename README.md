# ShProjectDemo
android 快速开发框架

## Android项目依赖框架
* jrbaselibrary   基础框架
* jrUIlibrary   UI框架
* jrFunclibrary   功能框架


## 依赖关系

![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/1.png)
##框架详细
###jrbaselibrary   基础框架  [API下载] (https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/doc/jrbase.rar)
封装 基础数据操作
* 1:缓存  （DiskLruCache，LruCache，SharedPreferences）
* 2:网络操作 (Retrofit 2.0,okhttp,兼容自有https证书）
* 3:数据库操作(litepal)
* 4:json解析
* 5:网络图片加载,图片处理  (glide)
* 6:工具类
* 7:数据绑定 （butterknife,Data binding）
* 8:数据加密 （DES,MD5,RSA）
* 9:依赖注入IOC （自定义）

###jrUIlibrary   UI控件  [API下载] (https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/doc/jrui.rar)
* TemplateTitleBar     顶部标题栏控件
* NoScrollListview     不可滑动的listView
* NoScrollGridView     不可滑动的GridView
* SquareLayout         宽度和高度一样layout
* LineFromView         表单行控件（封装各种控件）
* CircleImageView      圆型ImageView
* RoundCornerImageView 带圆角圆型ImageView
* IOSAlertDialog       仿IOS alert
* TimePickerView       仿IOS 仿IOS 时间选择器
* OptionsPickerView    仿IOS 选择器（最多可以3级联动）
* IOSSwitchButton      仿IOS SwitchButton
* SideslipListView     仿IOS 可左滑listView
* DragIndicatorView    消息提示小红点（仿QQ可拖动）


![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/IOSAlertDialog.gif)
![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/OptionsPickerView.gif)
![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/TimePickerView.gif)
![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/SideslipListView.gif)
* UISearchView         搜索框
* LetterListView       按字母分组listView


![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/LetterListView.gif)
* TouchPhotoView       图片查看器 （可放滑动/双击放大缩小）


![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/TouchPhotoView.gif)
* MyProgressDialog     等待Dialog
* CycleView            自动滚动banner 图


![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/CycleView.gif)
* SpinnerDialog        底部弹出菜单


![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/SpinnerDialog.gif)
* TabLayout            viewPage 关联 tab（官方）
http://www.jianshu.com/p/2b2bb6be83a8


![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/TabLayout1.gif)
![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/TabLayout2.gif)

* RefreshLayout        上拉刷新组件（官方）

![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/RefreshLayout.gif)
* Navigation View      抽屉导航（官方）

![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/Navigation.gif)
* JsBridge             H5原生交互webview（在其基础上 加入 屏蔽alert web 缓存 进度条 url监听）
https://github.com/lzyzsd/JsBridge
* formUtiks            表单映射工具
https://github.com/hui46226021/ShFormUtils

![](https://raw.githubusercontent.com/hui46226021/ShProjectDemo/master/tu/formUtiks.gif)
* TabRadioView            底部Tab菜单 带红点提示

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














