package com.jruilibrary.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Button;
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
 * 倒计时按钮
 */

public class ClockingButton extends Button {

    int totalSec;
    String clockingText = "重新发送";
    String text;
    Timer timer = new Timer(true);
    TimerTask task= new TimerTask() {
        @Override
        public void run() {
            if(totalSec==0){
                stop();
                setEnabled(true);
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
    public ClockingButton(Context context) {
        super(context);
    }

    public ClockingButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }


    public String getTimeStr(int totalSec){
        String str=null;
        if(totalSec<10){
            str = "0"+totalSec;
        }else {
            str= totalSec+"";
        }
        return clockingText+"("+str+")";
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
        setEnabled(false);
        text = getText()+"";
        timer.schedule(task, 0,1000);
    }

    /**
     * 停止计时
     */
    public void stop(){
        setText(text);
        timer.cancel();
    }

    public void setClockingText(String clockingText) {
        this.clockingText = clockingText;
    }
}
