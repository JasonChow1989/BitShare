package com.bashi_group_01.www.Recorder;

import com.bashi_group_01.www.Recorder.AudioManager.AudioStateListener;
import com.bashi_group_01.www.activity.R;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class AudioRecorderButton extends Button implements AudioStateListener {

	private static final int DISTANCE_Y_CANCEL = 50;
	private static final int STATE_NORMAL = 1;
	private static final int STATE_RECORDING = 2;
	private static final int STATE_WANT_TO_CANCEL = 3;

	private int mCurState = STATE_NORMAL;

	private boolean isRecording;

	private DialogManager mDialogManager;

	private AudioManager mAudioManager;

	private float mTime;
	private boolean mReady;

	public AudioRecorderButton(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public AudioRecorderButton(Context context, AttributeSet attrs) {
		super(context, attrs);

		mDialogManager = new DialogManager(getContext());
		String dir = Environment.getExternalStorageDirectory().getPath()
				+ "/imooc_recorder_audios";
		mAudioManager = AudioManager.getInstance(dir);
		mAudioManager.setOnAudioStateListener(this);

		setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				mReady = true;
				mAudioManager.prepareAudio();
				return false;
			}
		});
	}
	
	public interface AudioFinishRecorderListener {
		void onFinish(float seconds, String filePath);
	}

	private AudioFinishRecorderListener mListener;

	public void setAudioFinishRecorderListener(
			AudioFinishRecorderListener listener) {
		mListener = listener;
	}
	

	private Runnable mGetVoiceLevelRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (isRecording) {
				try {
					Thread.sleep(100);
					mTime += 0.1f;
					mHandler.sendEmptyMessage(MSG_VOICE_CHANGED);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};

	private static final int MSG_AUDIO_PREPARED = 0X110;
	private static final int MSG_VOICE_CHANGED = 0X111;
	private static final int MSG_DIALOG_DIMISS = 0X112;

	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_AUDIO_PREPARED:
				mDialogManager.showRecordingDialog();
				isRecording = true;
				new Thread(mGetVoiceLevelRunnable).start();
				break;

			case MSG_VOICE_CHANGED:
				mDialogManager.updateVoiceLevel(mAudioManager.getVoiceLevel(7));
				break;

			case MSG_DIALOG_DIMISS:
				mDialogManager.dissDialog();
				break;
			}
		}
	};

	@Override
	public void wellPrepared() {
		// TODO Auto-generated method stub
		mHandler.sendEmptyMessage(MSG_AUDIO_PREPARED);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int action = event.getAction();
		int x = (int) event.getX();
		int y = (int) event.getY();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			isRecording = true;
			changeState(STATE_RECORDING);
			break;
		case MotionEvent.ACTION_MOVE:
			if (isRecording) {
				if (wantToCancel(x, y)) {
					changeState(STATE_WANT_TO_CANCEL);
				} else {
					changeState(STATE_RECORDING);
				}
			}
			break;

		case MotionEvent.ACTION_UP:
			if (!mReady) {
				reset();
				return super.onTouchEvent(event);
			}

			if (!isRecording || mTime < 0.6f) {
				mDialogManager.tooshort();
				mAudioManager.cancel();
				mHandler.sendEmptyMessageDelayed(MSG_DIALOG_DIMISS, 1300);
			}else if (mCurState == STATE_RECORDING) {
				// relese;
				mDialogManager.dissDialog();
				mAudioManager.release();

				if (mListener != null) {
					mListener.onFinish(mTime, mAudioManager.getCurrentPath());
				}
			} else if (mCurState == STATE_WANT_TO_CANCEL) {
				// CANCEL
				mDialogManager.dissDialog();
				mAudioManager.cancel();
			}

			reset();
			break;
		}
		return super.onTouchEvent(event);
	}

	/**
	 * »Ö¸´±êÖ¾Î»
	 * 
	 */

	private void reset() {
		// TODO Auto-generated method stub
		isRecording = false;
		mReady = false;
		mTime = 0;
		changeState(STATE_NORMAL);
	}

	private boolean wantToCancel(int x, int y) {
		// TODO Auto-generated method stub
		if (x < 0 || x > getWidth()) {
			return true;
		}
		if (y < -DISTANCE_Y_CANCEL || y > getHeight() + DISTANCE_Y_CANCEL) {
			return true;
		}

		return false;
	}

	private void changeState(int state) {
		// TODO Auto-generated method stub

		if (mCurState != state) {
			mCurState = state;
			switch (state) {
			case STATE_NORMAL:
				setText(R.string.str_recorder_normal);
				break;

			case STATE_RECORDING:
				setText(R.string.str_recorder_recording);
				if (isRecording) {
					mDialogManager.recording();
				}
				break;

			case STATE_WANT_TO_CANCEL:
				setText(R.string.str_recorder_want_cancel);
				mDialogManager.wantToCancel();
				break;
			}

		}

	}

}
