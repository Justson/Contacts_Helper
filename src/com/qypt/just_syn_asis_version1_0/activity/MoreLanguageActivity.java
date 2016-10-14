package com.qypt.just_syn_asis_version1_0.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 
 * @author Administrator justson
 *more language
 */
public class MoreLanguageActivity  extends Activity implements OnClickListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.bottom_out_alp_fade_in, 0);
		setContentView(R.layout.activity_more_language);
		initView();
	}

	private void initView() {
		
		TextView text=(TextView) findViewById(R.id.text);
		text.setText("设置");
		ImageView image=(ImageView) findViewById(R.id.left);
		image.setOnClickListener(this);
	}

	
	@Override
	protected void onPause() {
		overridePendingTransition(0, R.anim.top_out_fade_out);
		super.onPause();
	}

	@Override
	public void onClick(View v) {

		if(v.getId()==R.id.left)
		{
			this.finish();
		}
	}
}
