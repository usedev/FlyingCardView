package cn.jlu.xbw.demo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.jlu.xbw.FlyingCardView.FlyingCardListView;

public class MainActivity extends Activity {

	FlyingCardListView testview;
	Button btn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);

		testview = (FlyingCardListView) findViewById(R.id.testview);
		initList();
		btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				List<String> moredatas = new ArrayList<String>();
				for (char i = '1'; i <= '9'; i++) {
					moredatas.add(String.valueOf(i));
				}
				TestAdapter moreadapter = new TestAdapter(MainActivity.this,
						moredatas);
				testview.loadMore(moreadapter);
			}
		});
	}

	private void initList() {
		List<String> datas = new ArrayList<String>();
		for (char i = 'A'; i <= 'Z'; i++) {
			datas.add(String.valueOf(i));
		}
		TestAdapter adapter = new TestAdapter(this, datas);
		testview.setAdapter(adapter);
	}

}
