package com.qypt.just_syn_asis_version1_0.adapter;

import java.util.List;

import android.content.Context;
import android.widget.TextView;

import com.qypt.just_syn_asis_version1_0.activity.R;
import com.qypt.just_syn_asis_version1_0.model.SynSQLiteBean;
/**
 * 
 * @author Administrator justson
 * 
 */
public class OprationAdapter extends CommAdapter<SynSQLiteBean> {

	public OprationAdapter(Context context, List<SynSQLiteBean> list) {
		super(context, list);
	}

	@Override
	protected void changedDataViewHolder(ViewHolder mViewHolder,
			SynSQLiteBean t, int position) {
		
		TextView time=mViewHolder.getView(R.id.time_opretion);
		TextView local=mViewHolder.getView(R.id.local_opretion);
		TextView cloud=mViewHolder.getView(R.id.cloud_opretion);
		time.setText(t.getTime().trim());
		local.setText(t.getLocalNumber().trim());
		cloud.setText(t.getCloudNumber().trim());
		
		
	}

	@Override
	public int getLayoutResId() {
		return R.layout.listview_item_operation;
	}

}
