package com.apps.customviews;

import com.apps.bhojnama.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class CustomProgressDialog extends ProgressDialog {
	private AnimationDrawable animation;

	public static ProgressDialog creator(Context context) {
		CustomProgressDialog dialog = new CustomProgressDialog(context);
		dialog.setIndeterminate(true);
		dialog.setCancelable(false);
		dialog.show();
		return dialog;
	}
	
	public static ProgressDialog creator2(Context context) {
		CustomProgressDialog dialog = new CustomProgressDialog(context);
		dialog.setIndeterminate(true);
		dialog.setCancelable(true);
		dialog.show();
		return dialog;
	}

	public CustomProgressDialog(Context context) {
		super(context);
	}

	public CustomProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_custom_progress_dialog);

		ImageView la = (ImageView) findViewById(R.id.animation);
		la.setBackgroundResource(R.anim.loading_frame);
		animation = (AnimationDrawable) la.getBackground();
	}

	@Override
	public void show() {
		super.show();
		animation.start();
	}

	@Override
	public void dismiss() {
		super.dismiss();
		animation.stop();
	}
}
