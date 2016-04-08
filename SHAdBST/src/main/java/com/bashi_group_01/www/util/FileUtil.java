package com.bashi_group_01.www.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.apache.http.util.EncodingUtils;
import android.os.Environment;
import android.text.TextUtils;

/**
 * 文件工具类�?? 
 *
 */
public class FileUtil {
	public static final String TAG = FileUtil.class.getSimpleName();
	
	public static final String PATH_DIVIDER = "&";
	
	public static final String ROOT = Environment.getExternalStorageDirectory().getPath();
	//主文件夹
	public static final String DIR_ICAMPUS = ROOT + "/icampus";
	public static final String DIR_BOOK = DIR_ICAMPUS + "/books";
	public static final String DIR_NEWS = DIR_ICAMPUS + "/news";
	public static final String DIR_IMAGES = DIR_ICAMPUS + "/image";
	public static final String DIR_STEP_IMAGES = DIR_IMAGES + "/step";

	/**
	 * 初始化文件结构�??
	 * 
	 * @return
	 */
	public static boolean initDir(){
		boolean b = false;
		File dirBook = new File(DIR_BOOK);
		if (!dirBook.exists()) {
			b = dirBook.mkdirs();
		}
		
		File dirNews = new File(DIR_NEWS);
		if (!dirNews.exists()) {
			b = dirNews.mkdirs();
		}
		
		File dirImg = new File(DIR_STEP_IMAGES);
		if (!dirImg.exists()) {
			b = dirImg.mkdirs();
		}
		
		return b;
	}
	
	
	/**
	 * 根据时间创建文件名�??
	 * @return “img_xxxxxxx.jpg�?
	 */
	public static String createImageFileName(){
		String name = "img_" + System.currentTimeMillis()+".jpg";
		return name;
	}
	
	/**
	 * 复制文件�?
	 * @param srcPath
	 * @param dstPath
	 */
	public static void copyFile(String srcPath, String dstPath){
		if (TextUtils.isEmpty(srcPath)||TextUtils.isEmpty(dstPath)) {
			return;
		}
		File fSrc = new File(srcPath);
		File fDst = new File(dstPath);
		FileInputStream fis;
		FileOutputStream fos;
		try {
			fis = new FileInputStream(fSrc);
			fos = new FileOutputStream(fDst);
			int len=0;  
	        byte[] buf=new byte[1024];  //每次读取1KB，限定每次读取大小，使得不会读取太多出现崩溃
	        while((len=fis.read(buf))!=-1) {    //如果每次读取的不为空
	        	fos.write(buf,0,len);     //每次按字节写入目标文�?
	        }  
	       fis.close();
	       fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fDst.delete();
		} catch (IOException e) {
			e.printStackTrace();
			fDst.delete();
		}
	}
	
	/**
	 * 读取书籍文件夹下�?有书籍�??
	 * 
	 * @return
	 */
	public static File[] getBooks(){
		File dirBook = new File(DIR_BOOK);
		File[] files = dirBook.listFiles();
		return files;
	}
	
	/**
	 * 读取文件全部内容�?
	 * 
	 * @param path
	 * @return
	 */
	public static String readfile(String path){
		String content = "";
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(path));
			int size = fis.available();
			byte[] buf = new byte[size];
			fis.read(buf);
			content = EncodingUtils.getString(buf, "UTF-8");
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
	
	/**
	 * 从文件中读对象�??
	 * 
	 * @param path
	 * @return Object
	 */
	public static Object readObject(File file){
		//读文件对�?
		Object object = null;
		ObjectInputStream ois = null;
		try{
			ois = new ObjectInputStream(new FileInputStream(file));
			object = ois.readObject();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(ois != null){
				try{
					ois.close();
					ois = null;
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		return object;
	}
	
	/**
	 * 把对象写入文件�??
	 * 
	 * @param path 路径
	 * @param seria 被写入对�?
	 */
	public static void writeObject(File file, Serializable seria){
		ObjectOutputStream oos = null;
		try{
			oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(seria);
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}finally
		{
			if(oos != null){
				try{
					oos.flush();
					oos.close();
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	
}
