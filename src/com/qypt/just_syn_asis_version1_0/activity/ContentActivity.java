package com.qypt.just_syn_asis_version1_0.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ContentActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		overridePendingTransition(R.anim.bottom_out_alp_fade_in, 0);
		setContentView(R.layout.activity_content);
		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {

		TextView text = (TextView) findViewById(R.id.text);
		text.setText("设置");
		ImageView left = (ImageView) findViewById(R.id.left);
		left.setOnClickListener(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		
		super.onPause();
		overridePendingTransition(R.anim.top_out_fade_out, 0);
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.left) {
			this.finish();
		}

	}
}
