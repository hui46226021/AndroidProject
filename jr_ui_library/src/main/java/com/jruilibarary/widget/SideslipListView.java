package com.jruilibarary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sh.zsh.jr_ui_library.R;

public class SideslipListView extends ListView {
	private int mScreenWidth;	// 屏幕宽度
	private int mDownX;			// 按下点的x值
	private int mDownY;			// 按下点的y值
	private int mDeleteBtnWidth;// 删除按钮的宽度
	private boolean isShow;
	private boolean isDeleteShown;	// 删除按钮是否正在显示
	
	private ViewGroup mPointChild;	// 当前处理的item
	private LinearLayout.LayoutParams mLayoutParams;	// 当前处理的item的LayoutParams
	
	public SideslipListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SideslipListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		// 获取屏幕宽度
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		mScreenWidth = dm.widthPixels;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {


		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				performActionDown(ev);
				break;
			case MotionEvent.ACTION_MOVE:
//				if(isSystem)
//					return super.onTouchEvent(ev);
//				else
					return performActionMove(ev);
			case MotionEvent.ACTION_UP:
//				if(isSystem)
//					return super.onTouchEvent(ev);
//				else
//
//				break;
				performActionUp();
		}

		return super.onTouchEvent(ev);
	}
//	private boolean isSystem;
	// 处理action_down事件
	private void performActionDown(MotionEvent ev) {
		//如果 没有子view 不做任何操作
		if( getChildAt(pointToPosition(mDownX, mDownY)
				- getFirstVisiblePosition())==null||((ViewGroup) getChildAt(pointToPosition(mDownX, mDownY)
				- getFirstVisiblePosition())).getChildAt(1)==null){
			return ;
		}
//		int	downX = (int) ev.getX();
//		int	downY = (int) ev.getY();
		// 获取当前点的item
//		ViewGroup	child = (ViewGroup) getChildAt(pointToPosition(downX, downY)
//				- getFirstVisiblePosition());
//		TextView textView;
//		if(child!=null)
//			textView = (TextView) child.findViewById(R.id.name);
//		else
//			return;
//		if(textView==null)
//			return;
//		if(textView.getText().equals("好友消息")){
//			isSystem = true;
//			return;
//		}else{
//			isSystem = false;
//		}

		if(isDeleteShown) {
			turnToNormal();
			isShow = true;
		}else{
			isShow = false;
		}
		try {
			mDownX = (int) ev.getX();
			mDownY = (int) ev.getY();
			// 获取当前点的item
			mPointChild = (ViewGroup) getChildAt(pointToPosition(mDownX, mDownY)
					- getFirstVisiblePosition());
			// 获取删除按钮的宽度
			mDeleteBtnWidth = mPointChild.getChildAt(1).getLayoutParams().width;
			mLayoutParams = (LinearLayout.LayoutParams) mPointChild.getChildAt(0)
					.getLayoutParams();
			// 为什么要重新设置layout_width 等于屏幕宽度
			// 因为match_parent时，不管你怎么滑，都不会显示删除按钮
			// why？ 因为match_parent时，ViewGroup就不去布局剩下的view
			mLayoutParams.width = mScreenWidth;
			mPointChild.getChildAt(0).setLayoutParams(mLayoutParams);
		}catch (Exception e){
			//不知道为啥 突然就想 加个try catch
			e.printStackTrace();
		}

	}

	// 处理action_move事件
	private boolean performActionMove(MotionEvent ev) {
		try {

		int nowX = (int) ev.getX();
		int nowY = (int) ev.getY();
		if(Math.abs(nowX - mDownX) > Math.abs(nowY - mDownY)) {
			// 如果向左滑动
			if(nowX < mDownX) {
				// 计算要偏移的距离
				int scroll = (nowX - mDownX) / 2;
				// 如果大于了删除按钮的宽度， 则最大为删除按钮的宽度
				if(-scroll >= mDeleteBtnWidth*2) {
					scroll = -mDeleteBtnWidth;
				}
				// 重新设置leftMargin
				mLayoutParams.leftMargin = scroll;
				mPointChild.getChildAt(0).setLayoutParams(mLayoutParams);
			}

			return true;
		}
		}catch (Exception e){

		}
		return super.onTouchEvent(ev);
	}

	// 处理action_up事件
	private void performActionUp() {
		// 偏移量大于button的一半，则显示button
		// 否则恢复默认\\
		try {
		if(-mLayoutParams.leftMargin >= mDeleteBtnWidth/2 ) {
			mLayoutParams.leftMargin = -mDeleteBtnWidth*2;
			isDeleteShown = true;
		}else if(-mLayoutParams.leftMargin>mDeleteBtnWidth/10){
			mLayoutParams.leftMargin = 0;
			mPointChild.getChildAt(0).setLayoutParams(mLayoutParams);
			isDeleteShown = true;
		}else{
			turnToNormal();
		}

		mPointChild.getChildAt(0).setLayoutParams(mLayoutParams);
		}catch (Exception e){

		}
	}


	/**
	 * 变为正常状态
	 */
	public void turnToNormal() {
		try {
			mLayoutParams.leftMargin = 0;
			mPointChild.getChildAt(0).setLayoutParams(mLayoutParams);
			isDeleteShown = false;
		}catch (Exception e){

		}

	}
	
	/**
	 * 当前是否可点击
	 * @return 是否可点击
	 */
	public boolean canClick() {
		return (!isDeleteShown)&&!isShow;
	}
}
