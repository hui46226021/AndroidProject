# ShProjectDemo
android 快速开发框架

## Android项目依赖框架
* jrbaselibrary   基础框架
* jrUIlibrary   UI框架
* jrFunctionlibrary   功能框架


## 依赖关系
jrFunctionlibrary --> 依赖  jrUIlibrary  -->依赖 jrbaselibrary

##框架详细
###jrbaselibrary   基础框架
封装 主要分为
* 1:缓存  （DiskLruCache，LruCache，SharedPreferences）
* 2:网络操作 (Retrofit 2.0,okhttp）
* 3:数据库操作(litepal)
* 4:json解析
* 5:网络图片加载,图片处理  (glide)
* 6:工具类
* 7:数据绑定 （butterknife,Data binding）

###jrUIlibrary   UI框架
* RefreshLayout        上拉刷新组件
* TemplateTitleBar     顶部标题栏控件
* NoScrollListview     不可滑动的listView
* NoScrollGridView     不可滑动的GridView
* IOSAlertDialog       仿IOS alert
* TimePickerView       仿IOS 仿IOS 时间选择器
* IOSSwitchButton      仿IOS SwitchButton
* DragIndicatorView    消息提示小红点（可拖动）
* TouchPhotoView       图片查看器 （可放滑动/双击放大缩小）
* MyProgressDialog     等待Dialog
* UISearchView         搜索框
* SquareLayout         宽度和高度一样layout
* CircleImageView      圆型ImageView







