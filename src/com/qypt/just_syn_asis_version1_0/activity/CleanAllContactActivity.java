package com.qypt.just_syn_asis_version1_0.activity;

import com.qypt.just_syn_asis_version1_0.utils.ContactUtils;

import de.greenrobot.event.EventBus;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
/**
 * 
 * @author Administrator justson
 *
 */
public class CleanAllContactActivity extends Activity implements OnClickListener{

	 private Button cleanButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		overridePendingTransition(R.anim.bottom_out_alp_fade_in, 0);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clean);
		initView();
	}

	private void initView() {
		
		cleanButton = (Button) findViewById(R.id.clear_clean_button);
		cleanButton.setOnClickListener(this);
	}

	
	@Override
	protected void onPause() {
	
		super.onPause();
		overridePendingTransition(0, R.anim.top_out_fade_out);
	}
	
	@Override
	public void onClick(View v) {
		
		if(v.getId()==R.id.clear_clean_button)
		{
			if(ContactUtils.deleteAllContact(this))
			{
				Toast.makeText(this, "删除联系人成功！", Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(this, "删除联系人失败！", Toast.LENGTH_LONG).show();
			}
		}
	}
	
 
}
