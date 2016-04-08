package com.bashi_group_01.www.Recorder;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

import android.media.MediaRecorder;
import android.text.format.DateFormat;

public class AudioManager {

	private MediaRecorder mMediaRecorder;
	private String mDir;
	private String mCurrentFilePath;

	private static AudioManager mInstance;

	private boolean isPrepared;

	private AudioManager(String dir) {
		mDir = dir;
	}

	public interface AudioStateListener {
		void wellPrepared();
	}

	public AudioStateListener mListener;

	public void setOnAudioStateListener(AudioStateListener listener) {
		mListener = listener;
	}

	public static AudioManager getInstance(String dir) {

		if (mInstance == null) {

			synchronized (AudioManager.class) {
				if (mInstance == null) {
					mInstance = new AudioManager(dir);
				}
			}
		}
		return mInstance;
	}

	public void prepareAudio() {

		try {
			isPrepared = false;

			File dir = new File(mDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			String fileName = new DateFormat().format("yyyyMMdd_HHmmss",
					Calendar.getInstance(Locale.ENGLISH)) + ".amr";
			File file = new File(dir, fileName);
			
			mCurrentFilePath = file.getAbsolutePath();

			mMediaRecorder = new MediaRecorder();

			mMediaRecorder.setOutputFile(file.getAbsolutePath());
			mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
			mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			mMediaRecorder.prepare();
			mMediaRecorder.start();
			isPrepared = true;

			if (mListener != null) {
				mListener.wellPrepared();
			}

		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getVoiceLevel(int maxLevel) {

		if (isPrepared) {
			try {
				return maxLevel * mMediaRecorder.getMaxAmplitude() / 32768 + 1;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 1;
	}

	public void release() {
		mMediaRecorder.stop();
		mMediaRecorder.release();
		mMediaRecorder = null;
	}

	public void cancel() {
		release();
		if (mCurrentFilePath != null) {
			File file = new File(mCurrentFilePath);
			file.delete();
			mCurrentFilePath = null;
		}
	}

	public String getCurrentPath() {
		// TODO Auto-generated method stub
		return mCurrentFilePath;
	}

}
