//package com.sh.shprojectdemo.ui;
//
//import android.content.pm.ActivityInfo;
//import android.content.res.Configuration;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//
//import com.android.tedcoder.wkvideoplayer.view.MediaController;
//import com.android.tedcoder.wkvideoplayer.view.SuperVideoPlayer;
//import com.jereibaselibrary.tools.JRDensityUtil;
//import com.jrfunclibrary.base.activity.BaseActivity;
//import com.sh.shprojectdemo.R;
//
//
///**
// * Created by zhush on 2016/11/19
// * E-mail 405086805@qq.com
// * PS  视频播放
// * 注意  要在 manifest.xml 设置  android:configChanges="keyboardHidden|orientation"
// */
//
//public class ShVideoActivity extends BaseActivity implements View.OnClickListener {
//
//    private SuperVideoPlayer mSuperVideoPlayer;
//    private View mPlayBtnView;
//    private String path; //本地视频路径
//    public static final String FILEPATH_KEY = "path_key";
//
//    private TextView textView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_shvideo);
//
//
//        path = getIntent().getStringExtra(FILEPATH_KEY);
//        mSuperVideoPlayer = (SuperVideoPlayer) findViewById(R.id.video_player_item_1);
//        mPlayBtnView = findViewById(R.id.play_btn);
//        mPlayBtnView.setOnClickListener(this);
//        mSuperVideoPlayer.setVideoPlayCallback(mVideoPlayCallback);
//
//        textView = (TextView) findViewById(R.id.vido_text);
//    }
//
//    /**
//     * 播放器的回调函数
//     */
//    private SuperVideoPlayer.VideoPlayCallbackImpl mVideoPlayCallback = new SuperVideoPlayer.VideoPlayCallbackImpl() {
//        /**
//         * 播放器关闭按钮回调
//         */
//        @Override
//        public void onCloseVideo() {
//            mSuperVideoPlayer.close();//关闭VideoView
//            mPlayBtnView.setVisibility(View.VISIBLE);
//            mSuperVideoPlayer.setVisibility(View.GONE);
//            resetPageToPortrait();
//        }
//
//        /**
//         * 播放器横竖屏切换回调
//         */
//        @Override
//        public void onSwitchPageType() {
//            if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) { //横屏
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                mSuperVideoPlayer.setPageType(MediaController.PageType.SHRINK);
//
//            } else {//竖屏
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                mSuperVideoPlayer.setPageType(MediaController.PageType.EXPAND);
//
//            }
//        }
//
//
//        /**
//         * 播放完成回调
//         */
//        @Override
//        public void onPlayFinish() {
//
//        }
//    };
//
//    @Override
//    public void onClick(View view) {
//        mPlayBtnView.setVisibility(View.GONE);
//        mSuperVideoPlayer.setVisibility(View.VISIBLE);
//        mSuperVideoPlayer.setAutoHideController(false);
//
//
//        //播放本地视频
//        //        File f = new File(path);
////        if (!f.exists()) {
////            Log.e("mmm","没有文件");
////            return;
////        }
////        Uri uri = Uri.fromFile(f);
//        //播放网络视频
//      Uri uri = Uri.parse("http://ic.snssdk.com/neihan/video/playback/?video_id=7fe54ca68fc64af0aca1f5f43cd6e256&quality=360p&line=0&is_gif=0");
//        mSuperVideoPlayer.loadAndPlay(uri, 0);
//
//    }
//
//    /***
//     * 旋转屏幕之后回调
//     *
//     * @param newConfig newConfig
//     */
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        if (null == mSuperVideoPlayer) return;
//        /***
//         * 根据屏幕方向重新设置播放器的大小
//         */
//        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
////            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
////                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
////            getWindow().getDecorView().invalidate();
////            float height = DensityUtil.getWidthInPx(this);
////            float width = DensityUtil.getHeightInPx(this);
////            mSuperVideoPlayer.getLayoutParams().height = (int) width;
////            mSuperVideoPlayer.getLayoutParams().width = (int) height;
//
//            textView.setVisibility(View.GONE);
//        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
////            final WindowManager.LayoutParams attrs = getWindow().getAttributes();
////            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
////            getWindow().setAttributes(attrs);
////            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
////            float width = DensityUtil.getWidthInPx(this);
////            float height = DensityUtil.dip2px(this, 200.f);
////            mSuperVideoPlayer.getLayoutParams().height = (int) height;
////            mSuperVideoPlayer.getLayoutParams().width = (int) width;
//            textView.setVisibility(View.VISIBLE);
//        }
//    }
//
//    /***
//     * 恢复屏幕至竖屏
//     */
//    private void resetPageToPortrait() {
//        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            mSuperVideoPlayer.setPageType(MediaController.PageType.SHRINK);
//        }
//    }
//
//
//    /**
//     * 横屏
//     */
//    public void horizontalScreen() {
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        mSuperVideoPlayer.setPageType(MediaController.PageType.EXPAND);
//    }
//
//    @Override
//    public void onBackPressed() {
//
//        if(JRDensityUtil.isHorizontalScreen(this)){//横屏
//            resetPageToPortrait();//切换到竖屏
//        }else {
//            super.onBackPressed();
//        }
//
//
//    }
//}
