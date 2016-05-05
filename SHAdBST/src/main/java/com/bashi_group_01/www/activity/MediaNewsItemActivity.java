package com.bashi_group_01.www.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
/**
 * ������Ƶ������;
 * @author share
 *
 */
public class MediaNewsItemActivity extends Activity {

	private final static int NETWORK_PARSE_ERROR = 0;
	private final static int VIDEO_FILE_ERROR = 1;
	private final static int VIDEO_STATE_BEGIN = 2;
	private final static int VIDEO_CACHE_FINISH = 3;
	private final static int VIDEO_UPDATE_SEEKBAR = 5;

	private Button playBtn, backBtn; // ���ڿ�ʼ����ͣ�İ�ť
	private SurfaceView videoSurfaceView; // ��ͼ�����������ڰ���Ƶ��ʾ����Ļ��
	private String url; // ��Ƶ���ŵ�ַ
	private MediaPlayer mediaPlayer; // �������ؼ�
	private int postSize = 0; // �����Ѳ���Ƶ��С
	private SeekBar seekbar; // �������ؼ�
	private boolean flag = true; // �����ж���Ƶ�Ƿ��ڲ�����
	private RelativeLayout opLy;
	private boolean display; // �����Ƿ���ʾ������ť
	private ProgressBar loadingVideoV;
	private TextView cacheT;
	private UpdateSeekBarR updateSeekBarR; // ���½�������

	private long mediaLength = 0, readSize = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// ȫ��
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // Ӧ������ʱ��������Ļ������������

		Intent intent = getIntent();
		String url1 = intent.getStringExtra("url");

		url = "http://task.84000.com.cn:7070/news" + url1; // ��Ƶ���ŵ�ַ
		// url
		// =Environment.getExternalStorageDirectory().getAbsolutePath()+"/9533522808.f4v.mp4";

		System.out.println("urlurlurlurlurlurlurlurl==="+url);

		initVideoPlayer();// ��ʼ������
		setPalyerListener();// ������¼�
	}

	private void initVideoPlayer() {
		mediaPlayer = new MediaPlayer(); // ����һ������������
		updateSeekBarR = new UpdateSeekBarR(); // �������½���������

		setContentView(R.layout.main); // ���ز����ļ�

		seekbar = (SeekBar) findViewById(R.id.seekBar);// ������
		opLy = (RelativeLayout) findViewById(R.id.opLy);
		loadingVideoV = (ProgressBar) findViewById(R.id.loadingVideo);
		cacheT = (TextView) findViewById(R.id.cacheT);

		backBtn = (Button) findViewById(R.id.backBtn);
		playBtn = (Button) findViewById(R.id.playBtn);
		playBtn.setEnabled(false); // �ս����������䲻�ɵ��

		videoSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);

		videoSurfaceView.getHolder().setType(
				SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); // ������
		videoSurfaceView.getHolder().setKeepScreenOn(true); // ������Ļ����
		videoSurfaceView.getHolder().addCallback(new videoSurfaceView());// ���ü����¼�,���������surface������ɿ�ʼ������Ƶ
	}

	// ��Ƶ������ͼ�ص��¼�
	private class videoSurfaceView implements Callback { // ����󶨵ļ������¼�
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {// ������ɺ����
			// ����������Ƶ,ֱ�Ӳ���
			if (URLUtil.isNetworkUrl(url)) {
				try {
					mHandler.sendEmptyMessage(VIDEO_STATE_BEGIN);
				} catch (Exception e) {
					mHandler.sendEmptyMessage(NETWORK_PARSE_ERROR);
					e.printStackTrace();
				}
			} else {
				File videoFile = new File(url);
				if (videoFile.exists()) {
					readSize = mediaLength = videoFile.length();
					System.out.println("���Ǳ�����Ƶ,ֱ�Ӳ���!");
					mHandler.sendEmptyMessage(VIDEO_STATE_BEGIN);
				}
			}
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) { // activity���ù�pause���������浱ǰ����λ��
			if (mediaPlayer != null && mediaPlayer.isPlaying()) {
				postSize = mediaPlayer.getCurrentPosition();
				mediaPlayer.stop();
				flag = false;
				loadingVideoV.setVisibility(View.VISIBLE);
			}
		}
	}

	// ������Ƶ���߳�
	class PlayMovieT extends Thread {
		int post = 0;

		public PlayMovieT(int post) {
			this.post = post;
		}

		@Override
		public void run() {
			try {
				mediaPlayer.reset(); // �ظ�������Ĭ��
				mediaPlayer.setDataSource(url); // ���ò���·��
				mediaPlayer.setDisplay(videoSurfaceView.getHolder()); // ����Ƶ��ʾ��SurfaceView��
				mediaPlayer.setOnPreparedListener(new videoPreparedL(post)); // ���ü����¼�
				mediaPlayer.prepare(); // ׼������
			} catch (Exception e) {
				mHandler.sendEmptyMessage(VIDEO_FILE_ERROR);
			}
			super.run();
		}
	}

	// ��Ƶ������ͼ׼���¼�������
	class videoPreparedL implements OnPreparedListener {
		int postSize;

		public videoPreparedL(int postSize) {
			this.postSize = postSize;
		}

		@Override
		public void onPrepared(MediaPlayer mp) {// ׼�����
			loadingVideoV.setVisibility(View.GONE); // ׼����ɺ����ؿؼ�
			backBtn.setVisibility(View.GONE);
			opLy.setVisibility(View.GONE);

			display = false;
			if (mediaPlayer != null) {
				mediaPlayer.start(); // ��ʼ������Ƶ
			} else {
				return;
			}
			if (postSize > 0) { // ˵����;ֹͣ����activity���ù�pause�����������û����ֹͣ��ť��������ֹͣʱ��λ�ÿ�ʼ����
				mediaPlayer.seekTo(postSize); // ����postSize��Сλ�ô����в���
			}
			new Thread(updateSeekBarR).start(); // �����̣߳����½�����
		}
	}

	// ���ò���������
	private void setPalyerListener() {
		mediaPlayer
				.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {// ����ȥ����
					@Override
					public void onBufferingUpdate(MediaPlayer mp, int percent) {
					}
				});

		mediaPlayer
				.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // ��Ƶ�������
					@Override
					public void onCompletion(MediaPlayer mp) {
						flag = false;
						playBtn.setBackgroundResource(R.drawable.btn_play);
					}
				});
		// �����Ƶ�ڲ��ţ������mediaPlayer.pause();��ֹͣ������Ƶ����֮��mediaPlayer.start()
		// ��ͬʱ����ť����
		playBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mediaPlayer.isPlaying()) {
					playBtn.setBackgroundResource(R.drawable.btn_play);
					mediaPlayer.pause();
					postSize = mediaPlayer.getCurrentPosition();
				} else {
					if (flag == false) {
						flag = true;
						new Thread(updateSeekBarR).start();
					}
					mediaPlayer.start();
					playBtn.setBackgroundResource(R.drawable.btn_pause);
				}
			}
		});
		seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				int value = seekbar.getProgress() * mediaPlayer.getDuration()
						/ seekbar.getMax(); // �����������Ҫǰ����λ�����ݴ�С
				mediaPlayer.seekTo(value);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
			}
		});
		// �����Ļ���л��ؼ�����ʾ����ʾ��Ӧ�أ����أ�����ʾ
		videoSurfaceView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (display) {
					backBtn.setVisibility(View.GONE);
					opLy.setVisibility(View.GONE);
					display = false;
				} else {
					backBtn.setVisibility(View.VISIBLE);
					opLy.setVisibility(View.VISIBLE);
					videoSurfaceView.setVisibility(View.VISIBLE);

					// ���ò���Ϊȫ��
					/*
					 * ViewGroup.LayoutParams lp =
					 * videoSurfaceView.getLayoutParams(); lp.height =
					 * LayoutParams.FILL_PARENT; lp.width =
					 * LayoutParams.FILL_PARENT;
					 * videoSurfaceView.setLayoutParams(lp);
					 */
					display = true;
				}

			}
		});
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mediaPlayer.isPlaying()) {
					mediaPlayer.stop();
					mediaPlayer.release();
				}
				mediaPlayer = null;
				MediaNewsItemActivity.this.finish();
			}
		});
	}

	// handler
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case NETWORK_PARSE_ERROR:// ������Ϣ
				Toast.makeText(getApplicationContext(), "�������Ӵ���,������ɲ���!",
						Toast.LENGTH_LONG).show();
				loadingVideoV.setVisibility(View.GONE);
				break;
			case VIDEO_FILE_ERROR:// ������Ϣ
				Toast.makeText(getApplicationContext(), "��Ƶ�ļ�����,������ɲ���!",
						Toast.LENGTH_LONG).show();
				loadingVideoV.setVisibility(View.GONE);
				break;
			case VIDEO_STATE_BEGIN:// ��ʼ������Ƶ
				playMediaMethod();
				playBtn.setEnabled(true);
				playBtn.setBackgroundResource(R.drawable.btn_pause);
				break;
			case VIDEO_CACHE_FINISH:// ��Ƶ�������,ʹ�ñ����ļ�����
				Toast.makeText(getApplicationContext(),
						"��Ƶ�Ѿ��������,Ϊ��֤���ŵ�������,�����л��ɱ����ļ�����!", Toast.LENGTH_LONG)
						.show();
				postSize = mediaPlayer.getCurrentPosition();
				playMediaMethod();
				break;
			case VIDEO_UPDATE_SEEKBAR:// ���½�����
				if (mediaPlayer == null) {
					flag = false;
				} else {
					double cachepercent = readSize * 100.00 / mediaLength * 1.0;
					String s = String.format("δ����", cachepercent);

					if (mediaPlayer.isPlaying()) {
						flag = true;
						int position = mediaPlayer.getCurrentPosition();
						int mMax = mediaPlayer.getDuration();
						int sMax = seekbar.getMax();
						seekbar.setProgress(position * sMax / mMax);

						mMax = 0 == mMax ? 1 : mMax;
						double playpercent = position * 100.00 / mMax * 1.0;

						int i = position / 1000;
						int hour = i / (60 * 60);
						int minute = i / 60 % 60;
						int second = i % 60;

						s += String.format(" ��ǰ����:%02d:%02d:%02d [%.2f%%]",
								hour, minute, second, playpercent);
					} else {
						s += " ��Ƶ��ǰδ����!";
					}
					cacheT.setText(s);
				}
				break;
			default:
				break;
			}
		};
	};

	// ������Ƶ�ķ���
	private void playMediaMethod() {
		if (postSize > 0 && url != null) { // ˵����ֹͣ�� activity���ù�pause����������ֹͣλ�ò���
			new PlayMovieT(postSize).start();// ��postSizeλ�ÿ�ʼ��
			flag = true;
			int sMax = seekbar.getMax();
			int mMax = mediaPlayer.getDuration();
			seekbar.setProgress(postSize * sMax / mMax);

			loadingVideoV.setVisibility(View.GONE);
		} else {
			new PlayMovieT(0).start();// �����ǵ�һ�ο�ʼ����
		}
	}

	// ÿ��1�����һ�½������߳�
	class UpdateSeekBarR implements Runnable {
		@Override
		public void run() {
			mHandler.sendEmptyMessage(VIDEO_UPDATE_SEEKBAR);
			if (flag) {
				mHandler.postDelayed(updateSeekBarR, 1000);
			}
		}
	}

	@Override
	protected void onDestroy() { // activity���ٺ��ͷ���Դ
		super.onDestroy();
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
		System.gc();
	}

/*	// ���߲���ʱ��̨�����ļ�,�����´�ֱ�Ӳ���
	class CacheNetFileR implements Runnable {
		@Override
		public void run() {
			System.out.println("------------��ʼ������Ƶ��!");
			FileOutputStream out = null;
			InputStream is = null;
			try {
				HttpURLConnection httpConnection = (HttpURLConnection) new URL(
						url).openConnection();
				String cacheUrl = Environment.getExternalStorageDirectory()
						.getAbsolutePath()
						+ "/Cache/"
						+ url.substring(url.lastIndexOf("/") + 1);
				File cacheFile = new File(cacheUrl);
				boolean isNeedCache = false;
				if (cacheFile.exists()) {
					readSize = mediaLength = cacheFile.length();
					// �Ƚ��������ú�,��ȻgetContentLength֮���Ѿ���������,������������
					httpConnection.setRequestProperty("User-Agent", "NetFox");
					httpConnection.setRequestProperty("RANGE", "bytes="
							+ readSize + "-");// �Ӷϵ㴦�����ȡ�ļ�
					if (httpConnection.getContentLength() == readSize) {// ��Ƶ�Ѿ��ɹ��������
						url = cacheUrl;
						isNeedCache = false;
					} else {
						isNeedCache = true;
					}
				} else {
					cacheFile.getParentFile().mkdirs();
					cacheFile.createNewFile();
					isNeedCache = true;
				}
				mHandler.sendEmptyMessage(VIDEO_STATE_BEGIN);// ��ʼ������Ƶ
				if (isNeedCache) {// ��Ҫ������Ƶ
					out = new FileOutputStream(cacheFile, true);

					is = httpConnection.getInputStream();
					mediaLength = httpConnection.getContentLength();
					if (-1 == mediaLength) {
						System.out.println("-------------��Ƶ�ļ�����ʧ��,���ܳɹ���ȡ�ļ���С!");
						return;
					}
					mediaLength += readSize;

					byte buf[] = new byte[4 * 1024];
					int size = 0;

					while ((size = is.read(buf)) != -1) {// �����ļ�
						System.out.println("--------------�����ļ�����:" + size);
						try {
							out.write(buf, 0, size);
							readSize += size;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					url = cacheUrl;// ��url�滻�ɱ���
					mHandler.sendEmptyMessage(VIDEO_CACHE_FINISH);// ��Ƶ�������,����ǰ��Ƶ�л��ɲ��ű��ص��ļ�
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
					}
				}
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}*/
}
