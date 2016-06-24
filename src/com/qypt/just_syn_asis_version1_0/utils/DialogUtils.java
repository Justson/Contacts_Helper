package com.qypt.just_syn_asis_version1_0.utils;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.GridLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qypt.just_syn_asis_version1_0.activity.R;
import com.qypt.just_syn_asis_version1_0.custom_view.CircularProgress;

public class DialogUtils {

	private ProgressDialog mProgressDialog;
	private Context context;
	private Dialog mDialog;
	private static Dialog mDialog_;
	private static Dialog mDialogs;

	public DialogUtils() {

	}

	public DialogUtils(Context context) {
		this.context = context;
		mDialog = new Dialog(context, R.style.loading);
	}

	public void showDialogToRemindUser(Context context, int type) {

	}

	public void showDialogToProgress(Context context, String message) {
		if (context == null)
			return;
		mProgressDialog = new ProgressDialog(context, 2);
		mProgressDialog.setTitle("提示");
		mProgressDialog.setMessage(message);
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
	}

	public void dismissDialogToProgress() {
		mProgressDialog.dismiss();
	}

	// 静态Dialog
	public static void alertDialog_(Context context, String message) {

		LayoutInflater mLayoutInflater = (LayoutInflater) context
				.getSystemService("layout_inflater");
		View view = mLayoutInflater.inflate(R.layout.dialog_tips, null);
		final AlertDialog dialog = new AlertDialog.Builder(context, 2).create();
		dialog.setView(view);

		TextView text = (TextView) view
				.findViewById(R.id.content_dialog_textview);
		if (text != null) {
			text.setText(message);
		}
		Button button = (Button) view.findViewById(R.id.confir_dialog_button);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				dialog.dismiss();
			}
		});

		dialog.show();
		mLayoutInflater = null;
	}

	// 显示dialog
	public void showDialogMessage(String msg) {

		View view = LayoutInflater.from(context).inflate(
				R.layout.dialog_progress, null);
		Animator mAnimator = AnimatorInflater.loadAnimator(context,
				R.animator.rotate);
		TextView textview = (TextView) view.findViewById(R.id.dialog_textview);
		textview.setText(msg);
		ImageView image = (ImageView) view.findViewById(R.id.dialog_progress_);
		mAnimator.setInterpolator(new DecelerateInterpolator());
		mAnimator.setTarget(image);
		mAnimator.start();
		mDialog.setContentView(view, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));
		mDialog.setCancelable(false);
		mDialog.show();
	}

	public void dismissDialog_() {
		mDialog.dismiss();
	}

	/**
	 * 可以回调的dialog 注意这里是写死的，因为findviewbyId 固定了textview
	 * 
	 * @param view
	 * @param context
	 * @param mOnClickResultCallBack
	 */
	public static void CanCallBackDialog(View view, String msg,
			Context context, final OnClickResultCallBack mOnClickResultCallBack) {
		if (view == null || context == null || mOnClickResultCallBack == null)
			return;

		if (mDialog_ != null) {
			mDialog_.show();
			return;
		}
		mDialog_ = new Dialog(context, R.style.loading);
		mDialog_.setContentView(view);
		mDialog_.setCancelable(true);
		TextView text = (TextView) mDialog_
				.findViewById(R.id.confir_dialog_button);
		TextView content = (TextView) mDialog_
				.findViewById(R.id.content_dialog_textview);
		content.setText(msg);
		text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Log.i("Info", "in dialog");
				mDialog_.dismiss();
				mOnClickResultCallBack.onResultCallBack();
			}
		});

		mDialog_.show();
	}

	public static void moreColorProgress(Context context) {
		 
			mDialogs = new Dialog(context,R.style.dialogstyle);
			CircularProgress mCircularProgress = new CircularProgress(context);
			ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(120, 120);
			LinearLayout ll=new LinearLayout(context);
			LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(120, 120);
			ll.addView(mCircularProgress,llp);
			mDialogs.setContentView(ll);
			mDialogs.setCancelable(false);
	 

		
		mDialogs.show();
	}

	public static void moreColorDismiss() {
		if(mDialogs!=null)
		mDialogs.dismiss();
	}

	/**
	 * 点击事件的回调
	 * 
	 * @author Administrator
	 * 
	 */
	public interface OnClickResultCallBack {

		public void onResultCallBack();

	}

}
