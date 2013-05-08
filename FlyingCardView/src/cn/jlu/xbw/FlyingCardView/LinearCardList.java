package cn.jlu.xbw.FlyingCardView;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.BaseAdapter;

/**
 * LinearLayout式的ListView
 * 
 * @author esperer.xu
 * 
 */
public class LinearCardList extends android.widget.LinearLayout
		implements OnGlobalLayoutListener {

	private android.widget.BaseAdapter adapter;
	private OnClickListener onClickListener = null;
	final int heightPx = getContext().getResources().getDisplayMetrics().heightPixels;
	final int widthPx = getContext().getResources().getDisplayMetrics().widthPixels;
	private List<View> viewList;
	private List<Boolean> viewShow;
	private int viewListSize;
	private int itemHeight;
	private int SUM;
	private Animation animation;

	public LinearCardList(Context context) {
		super(context);
		viewList = new ArrayList<View>();
		viewShow = new ArrayList<Boolean>();
		// ViewTree监控器， 主要就靠这个了
		getViewTreeObserver().addOnGlobalLayoutListener(this);
	}

	private void loadViews() {
		int count = adapter.getCount();
		for (int i = 0; i < count; i++) {
			View v = adapter.getView(i, null, null);
			v.setOnClickListener(this.onClickListener);
			viewList.add(v);
			viewShow.add(false);
		}
		viewListSize = viewList.size();
	}
	
	//对上层提供“加载更多”
	public void loadMore(BaseAdapter moreAdapter){
		int count = moreAdapter.getCount();
		for (int i = 0; i < count; i++) {
			View v = moreAdapter.getView(i, null, null);
			v.setOnClickListener(this.onClickListener);
			viewList.add(v);
			viewShow.add(false);
		}
		viewListSize = viewList.size();
	}

	// 算出一屏多少个item，初始展示
	private void initItems() {
		getItemHeight();
		SUM = heightPx / itemHeight;
		for (int i = 0; i < SUM && i < viewListSize; i++) {
			addView(viewList.get(i));
			viewShow.set(i, true);
			onGlobalLayout();
		}
	}

	// 获取一个item多高
	private void getItemHeight() {
		int w = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		View v = adapter.getView(0, null, null);
		v.measure(w, h);
		itemHeight = v.getMeasuredHeight();
	}

	public void clearAllDatas() {
		this.viewList = new ArrayList<View>();
		this.viewShow = new ArrayList<Boolean>();
		this.viewListSize = 0;
		this.removeAllViews();
	}

	public LinearCardList(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public android.widget.BaseAdapter getAdpater() {
		return adapter;
	}

	public void setAdapter(android.widget.BaseAdapter adpater) {
		this.adapter = adpater;
		loadViews();
		initItems();
	}

	public void setOnclickLinstener(OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}

	// 根据ScrollView的滚动位置确定该有哪个item可见了
	public void onScroll(int height) {
		int pos = SUM + height / itemHeight + 1;
		if (pos > viewListSize - 1)
			return;
		if (!viewShow.get(pos)) {
			addView(viewList.get(pos));
			viewShow.set(pos, true);
		}
	}

	@Override
	public void onGlobalLayout() {
		// 监控视图树变化，当onScroll中addView时，触发,播放动画
		int childToShow = getChildCount() - 1;
		final View child = getChildAt(childToShow);
		animation = new CardAnimation(60f, 0, widthPx / 2, itemHeight / 2, -heightPx);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// 产生一个延时而已，要不item就连接在一起了
				Animation waitanim = new AlphaAnimation(1f, 1f);
				waitanim.setDuration(100);
				child.startAnimation(waitanim);
			}
		});
		child.startAnimation(animation);
	}
}