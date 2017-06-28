package com.jruilibrary.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
/**                          _ooOoo_
 *                          o8888888o
 *                         88" . "88
 *                         (| -_- |)
 *                          O\ = /O
 *                      ____/`---'\____
 *                    .   ' \\| |// `.
 *                     / \\||| : |||// \
 *                   / _||||| -:- |||||- \
 *                     | | \\\ - /// | |
 *                   | \_| ''\---/'' | |
 *                    \ .-\__ `-` ___/-. /
 *                 ___`. .' /--.--\ `. . __
 *              ."" '< `.___\_<|>_/___.' >'"".
 *             | | : `- \`.;`\ _ /`;.`/ - ` : | |
 *               \ \ `-. \_ __\ /__ _/ .-` / /
 *       ======`-.____`-.___\_____/___.-`____.-'======
 *                          `=---='
 *
 . * ............................................
 *              佛祖保佑             永无BUG
 *        佛曰:
 *                写字楼里写字间，写字间里程序员；
 *                程序人员写程序，又拿程序换酒钱。
 *                酒醒只在网上坐，酒醉还来网下眠；
 *                酒醉酒醒日复日，网上网下年复年。
 *                但愿老死电脑间，不愿鞠躬老板前；
 *                奔驰宝马贵者趣，公交自行程序员。
 *                别人笑我忒疯癫，我笑自己命太贱；
 *                不见满街漂亮妹，哪个归得程序员？
 */
/**
 * Created by zhush on 2017/6/28.
 * Email 405086805@qq.com
 * 倒计时空间
 */

public class ClockingView extends TextView {
    int hour;
    int minute;
    int sec;
    int totalSec;
    Timer timer = new java.util.Timer(true);
    TimerTask task= new TimerTask() {
        @Override
        public void run() {
            if(totalSec==0){
                timer.cancel();
                return;
            }
            totalSec--;
            getTimeStr(totalSec);
            ( (Activity)getContext()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setText(getTimeStr(totalSec));
                }
            });
        }
    };
    public ClockingView(Context context) {
        super(context);
    }

    public ClockingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }


    public String getTimeStr(int totalSec){
         hour =totalSec/3600;
         minute =totalSec/60;
         sec =totalSec%60;
        StringBuilder sb = new StringBuilder();
        if(hour!=0){
            sb.append(hour+"小时");
        }
        if(minute!=0){
            sb.append(minute+"分");
        }
        if(sec!=0){
            sb.append(sec+"秒");
        }
        return sb.toString();
    }

    /**
     * 设置总时间
     * @param totalSec
     */
    public void setTotalSec(int totalSec) {
        this.totalSec = totalSec;
    }

    /**
     * 开始计时
     */
    public void start(){
        timer.schedule(task, 0,1000);
    }

    /**
     * 停止计时
     */
    public void stop(){
        timer.cancel();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        new RuntimeException("该空间不能设置内部显示内容");
    }
}
