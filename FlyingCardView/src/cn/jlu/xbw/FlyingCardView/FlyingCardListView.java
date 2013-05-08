package cn.jlu.xbw.FlyingCardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.BaseAdapter;
import android.widget.ScrollView;

/**
 * 仿Google+的ListView，上滑时具有卡片动画效果
 * 
 * @author esperer.xu
 * 
 */
public class FlyingCardListView extends ScrollView {

	private LinearCardList listView;
	private Context mContext;

	public FlyingCardListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView();
	}

	public FlyingCardListView(Context context) {
		super(context);
		mContext = context;
		initView();
	}

	private void initView() {
		listView = new LinearCardList(mContext);
		listView.setLayoutParams(new LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT));
		listView.setOrientation(1); // vertical
		addView(listView);
	}

	public void setAdapter(BaseAdapter adpater) {
		listView.setAdapter(adpater);
	}

	public void loadMore(BaseAdapter moreAdapter) {
		listView.loadMore(moreAdapter);
	}

	public BaseAdapter getAdpater() {
		return listView.getAdpater();
	}

	@SuppressLint("NewApi")
	@Override
	protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
			boolean clampedY) {
		super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
		listView.onScroll(scrollY);
	}

}
