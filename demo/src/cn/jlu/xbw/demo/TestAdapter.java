package cn.jlu.xbw.demo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TestAdapter extends BaseAdapter {
 
	private Context mContext;
	LayoutInflater mLI = null;
	List<String> datas = null;

	public TestAdapter(Context context, List<String> datas) {
		this.mContext = context;
		this.datas = datas;
		mLI = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (datas == null)
			this.datas = new ArrayList<String>();
	}
	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public String getItem(int index) {
		if (index >= 0 && index < datas.size())
			return datas.get(index);
		else
			return null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (position < 0 || position > datas.size())
			return null;

		View v = convertView;
		if (v == null) {
			v = mLI.inflate(R.layout.item, null);
		} 
		TextView v_name = (TextView) v.findViewById(R.id.textView1);
		v_name.setText(datas.get(position));
		return v;
	}

}
