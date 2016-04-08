package com.bashi_group_01.www.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;

import com.bashi_group_01.www.activity.R;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

public class ImageUtil {
	public static final String TAG = ImageUtil.class.getSimpleName();

	/**
	 * 压缩图片基准值，�?小边超过160px�?,对图片进行压缩�??
	 */
	public static final int BASE_SIZE_160 = 160;
	public static final int BASE_SIZE_320 = 320;
	public static final int BASE_SIZE_480 = 480;

	/**
	 * 获取压缩后图片�??
	 * 
	 * @param path
	 *            原图路径
	 * @return Bitmap
	 */
	public static Bitmap getResizedBitmap(String path, int baseSize) {
		Bitmap bitmap = null;
		// 图片参数�?
		BitmapFactory.Options options = new BitmapFactory.Options();
		// 只计算几何尺寸，不返回bitmap，不占内存�??
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);//图片的大小存储在options�?
		// 宽�?�高�?
		int w = options.outWidth;//得到图片的宽
		int h = options.outHeight;
		Log.i(TAG, "--img src,w:" + w + " h:" + h);
		// �?小边�?
		int min = w < h ? w : h;
		// 压缩比�??
		int rate = min / baseSize;
		if (rate <= 0) {
			rate = 1;
		}
		// 设置压缩参数�?
		options.inSampleSize = rate;
		options.inJustDecodeBounds = false;
		// 压缩�?
		bitmap = BitmapFactory.decodeFile(path, options);
		if (bitmap != null) {
			Log.i(TAG,
					"--img dst,w:" + bitmap.getWidth() + " h:"
							+ bitmap.getHeight());
		}
		return bitmap;
	}

	/**
	 * 获取相册图片路径�?
	 * 
	 * @param context
	 * @param uri
	 *            图片Uri
	 * @return path
	 */
	public static String getAlbumImagePath(Context context, Uri uri) {
		String path = "";
		// 字段�?
		String[] proj = { MediaStore.Images.Media.DATA };
		// 查询
		Cursor cursor = context.getContentResolver().query(uri, proj, null,
				null, null);
		// 字段名拿索引
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		// 通过索引拿数�?
		path = cursor.getString(column_index);
		cursor.close();
		return path;
	}

	/**
	 * 保存Bitmap
	 * 
	 * @param bitmap
	 *            保存对象
	 * @param dstPath
	 *            文件路径
	 * @param fileName
	 *            文件�?
	 */
	public void saveBitmap(Bitmap bitmap, String dstPath, String fileName) {
		if (bitmap == null) {
			return;
		}
		// 创建目录
		File dir = new File(dstPath);
		if (!dir.exists()) {
			boolean isSucc = dir.mkdirs();
			if (!isSucc) {
				return;
			}
		}
		File imgFile = new File(dstPath, fileName);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(imgFile);
			boolean isSucc = bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
					fos);
			if (isSucc) {
				fos.flush();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			imgFile.delete();
		} catch (IOException e) {
			e.printStackTrace();
			imgFile.delete();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void initImageLoader(Context context) {
//		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));//采用默认的配�?
		ImageLoader.getInstance().init(getImageConfig(context));
	}

	/**
	 * 获取ImageLoader设置参数�?
	 * 
	 * @param context
	 * @return ImageLoaderConfiguration
	 */
	public static ImageLoaderConfiguration getImageConfig(Context context) {
		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
				context);
		// 缓冲文件�?大尺寸�??
		builder.memoryCacheExtraOptions(480, 800); // max width, max
													// height，即保存的每个缓存文件的�?大长�?
		// 后台线程池，线程数量�?
		builder.threadPoolSize(3);// 线程池内加载的数�?
		// 线程的优先级�?
		builder.threadPriority(Thread.NORM_PRIORITY - 1);
		// 禁止缓冲同一张图片多种尺寸�??
		builder.denyCacheImageMultipleSizesInMemory();
		// 缓冲区大小，默认�?2M�?
		builder.memoryCacheSize(2 * 1024 * 1024);
		// 缓冲区大小和缓冲区图片缓冲策略�??
		builder.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)); // You
																				// can
																				// pass
																				// your
																				// own
																				// memory
																				// cache
																				// implementation/你可以�?�过自己的内存缓存实�?
		// TODO
		builder.tasksProcessingOrder(QueueProcessingType.LIFO);
		// 下载器BaseImageDownloader(context, 连接超时, 读取超时))【可以获取网络上的图片，设定连接超时时间和读时间�?
		builder.imageDownloader(new BaseImageDownloader(context, 5 * 1000,
				30 * 1000)); // connectTimeout (5 s), readTimeout (30 s)超时时间
		builder.writeDebugLogs();

		return builder.build();
	}

	/**
	 * 
	 * @return
	 */
	// public static DisplayImageOptions getDisplayOptions(){
	//
	// DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
	// //缩放类型
	// builder.imageScaleType(ImageScaleType.EXACTLY_STRETCHED);
	// //载入期间显示的图�?
	// builder.showImageOnLoading(R.drawable.ic_launcher);
	// //Uri为空或是错误的时候显示的图片
	// builder.showImageForEmptyUri(R.drawable.img_default_news);
	// //加载/解码过程中发生错误，显示的图�?
	// builder.showImageOnFail(R.drawable.img_default_news);
	// //设置图片的解码类�?
	// builder.bitmapConfig(Bitmap.Config.RGB_565);
	// return builder.build();
	// }
	
	public ImageLoaderConfiguration getConfig(Context c){
		ImageLoaderConfiguration cfg = new ImageLoaderConfiguration.Builder(c)
		//线程池线程数�?
	    .threadPoolSize(3)                      
	    //线程优先�?  
	    .threadPriority(Thread.NORM_PRIORITY - 1)  
	    //任务执行顺序
		.tasksProcessingOrder(QueueProcessingType.FIFO)   
	    //禁止同一张图片多种尺�?
	    .denyCacheImageMultipleSizesInMemory()  
	    //内存缓存图片的最大尺�?
	    .memoryCacheExtraOptions(200, 300)    
	    //缓冲区大�?
	    .memoryCacheSize(2 * 1024 * 1024)  
	    //图片下载�?
	    .imageDownloader(new BaseImageDownloader(c)) 
	    //打日�?
	    .writeDebugLogs()  
	    .build(); 
		
		return cfg;
	}
	
	public DisplayImageOptions getOpt(){
		DisplayImageOptions options = new DisplayImageOptions.Builder() 
	    .showImageOnLoading(R.drawable.ic_launcher)          // 加载中显示的图片  
	    .showImageForEmptyUri(R.drawable.ic_launcher)  // URI空显示的图片  
	    .showImageOnFail(R.drawable.ic_launcher)       // 加载失败显示的图�?  
//	    .resetViewBeforeLoading(false)	//是否重设View  
//	    .delayBeforeLoading(0)	//加载延时
	    .cacheInMemory(true)	//是否缓存内存  
//	    .cacheOnDisk(false)    //释放缓存SDcard 
//	    .preProcessor(null)  	//图片处理器（进缓存之前）
//	    .postProcessor(null)	//图片处理器（显示之前�?
//	    .extraForDownloader(null)	//下载器辅助类
	    .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)	//图片比例类型，Image生成Bitmap的类�?  
	    .bitmapConfig(Bitmap.Config.ARGB_4444)	//生成Bitmap的编码方�?  
//	    .decodingOptions(null)  //解码参数
//	    .displayer(new SimpleBitmapDisplayer()) 	// default 可以设置动画，比如圆角或者渐�?  
//	    .handler(new Handler())	//
	    .build();  
		
		return options;
	}

	private void Cfg(Context c){
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(c)
		//SD卡缓存图片的�?大尺�?  
//	    .discCacheExtraOptions(480, 800, null)
		//任务执行�?
//	    .taskExecutor(executor)
		//内存缓存的任务执行器
//	    .taskExecutorForCachedImages(executorForCachedImages) 
		//任务执行顺序
		.tasksProcessingOrder(QueueProcessingType.FIFO)   
		//线程池线程数�?
	    .threadPoolSize(3)                      
	    //线程优先�?  
	    .threadPriority(Thread.NORM_PRIORITY - 1)    
	    //禁止同一张图片多种尺�?
	    .denyCacheImageMultipleSizesInMemory()  
	    //内存缓存图片的最大尺�?
	    .memoryCacheExtraOptions(200, 300)    
	    //缓冲区大�?
	    .memoryCacheSize(2 * 1024 * 1024)  
	    //缓冲区大小，占App内存百分�?
//	    .memoryCacheSizePercentage(13)               
	    //缓冲区进出策�?
//	    .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
	    //图片下载�?
	    .imageDownloader(new BaseImageDownloader(c)) 
	    //图片编码�?
//	    .imageDecoder(new BaseImageDecoder(false))
	    //显示选项
	    .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default  
	    //打日�?
	    .writeDebugLogs()  
	    .build();  
	}
	
	public void displayOptions(){
		DisplayImageOptions options = new DisplayImageOptions.Builder() 
//	    .showStubImage(R.drawable.ic_stub)          // 加载中显示的图片  
//	    .showImageForEmptyUri(R.drawable.ic_empty)  // URI空显示的图片  
//	    .showImageOnFail(R.drawable.ic_error)       // 加载失败显示的图�?  
	    .resetViewBeforeLoading(false)	//是否重设View  
	    .delayBeforeLoading(0)	//加载延时
	    .cacheInMemory(false)	//是否缓存内存  
	    .cacheOnDisk(false)    //释放缓存SDcard 
	    .preProcessor(null)  	//图片处理器（进缓存之前）
	    .postProcessor(null)	//图片处理器（显示之前�?
	    .extraForDownloader(null)	//下载器辅助类
	    .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)	//图片比例类型，Image生成Bitmap的类�?  
	    .bitmapConfig(Bitmap.Config.ARGB_4444)	//生成Bitmap的编码方�?  
	    .decodingOptions(null)  //解码参数
	    .displayer(new SimpleBitmapDisplayer()) 	// default 可以设置动画，比如圆角或者渐�?  
	    .handler(new Handler())	//
	    .build();  
	}
	
	

}
