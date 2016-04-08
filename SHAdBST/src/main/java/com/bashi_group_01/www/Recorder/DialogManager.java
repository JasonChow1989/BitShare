package com.bashi_group_01.www.Recorder;

import com.bashi_group_01.www.activity.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

public class DialogManager {

	private Dialog mDialog;
	private ImageView mVoice;
	private Context mcontext;

	public DialogManager(Context context) {
		this.mcontext = context;
	}

	public void showRecordingDialog() {
		mDialog = new Dialog(mcontext, R.style.Theme_AudioDialog);
		LayoutInflater inflater = LayoutInflater.from(mcontext);
		View view = inflater.inflate(R.layout.dialog_recording, null);
		mDialog.setContentView(view);
		mVoice = (ImageView) mDialog.findViewById(R.id.recording_voice);
		mDialog.show();
	}

	public void recording() {

	}

	public void wantToCancel() {

	}

	public void tooshort() {
	}

	public void dissDialog() {
		if (mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
			mDialog = null;
		}
	}

	public void updateVoiceLevel(int level) {

		if (mDialog != null && mDialog.isShowing()) {

			int resId = mcontext.getResources().getIdentifier("v0" + level,
					"drawable", mcontext.getPackageName());
			mVoice.setImageResource(resId);
		}
	}
}
