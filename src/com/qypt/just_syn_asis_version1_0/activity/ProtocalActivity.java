package com.qypt.just_syn_asis_version1_0.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 
 * @author Administrator  justson
 *
 */
public class ProtocalActivity extends SynActivity implements OnClickListener {

	@Override
	protected void initView() {
		overridePendingTransition(R.anim.bottom_out_alp_fade_in, 0);
		setContentView(R.layout.activity_protocol);
		ImageView image=(ImageView) findViewById(R.id.left);
		TextView text=(TextView) findViewById(R.id.text);
		text.setText("协议");
		image.setOnClickListener(this);  
	}

	@Override
	protected void initViewData() {
		
	}
	
  @Override 
protected void onPause() {
	  overridePendingTransition(0, R.anim.top_out_fade_out);
	super.onPause();
}

@Override
public void onClick(View v) {
	
	
	this.finish();
}
	

}
