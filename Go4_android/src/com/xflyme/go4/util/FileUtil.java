package com.xflyme.go4.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;
import android.text.TextUtils;

import com.xflyme.go4.PropertyConstant;

/**
 * 
 * @Description:文件工具类
 * @author:
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014年8月12日
 */
public class FileUtil {

	/**
	 * sd卡是否存在
	 * 
	 * @return
	 */
	public static boolean isSdCardExist() {
		return Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	

	/**
	 * 遍历文件夹下所有文件
	 * 
	 * @param filePath
	 */
	public static List<String> getFileList(String filePath) {
		List<String> filelist = new ArrayList<String>();
		File dir = new File(filePath);
		File[] files = dir.listFiles();
		if (files == null)
			return null;
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				getFileList(files[i].getAbsolutePath());
			} else {
				String strFileName = files[i].getAbsolutePath().toLowerCase();
				CLog.d("strFileName==", strFileName);
				filelist.add(files[i].getAbsolutePath());
			}
		}

		return filelist;
	}

	/**
	 * 读取文本内容
	 * 
	 * @param filePath
	 * @return
	 */
	public static String readFileToText(String filePath) {

		InputStream inputStream = null;
		BufferedReader buffreader = null;
		String content = null; // 文件内容字符串
		
		if (TextUtils.isEmpty(filePath)) {
			return null;
		}
		File file = new File(filePath);
		if (file.isDirectory()) {
			CLog.d("readFileToText", "The filePath is a directory.");
		} else {
			try {
				inputStream = new FileInputStream(file);
				InputStreamReader inputreader = new InputStreamReader(
						inputStream);
				buffreader = new BufferedReader(inputreader);
				String line = null;
				// 分行读取
				while ((line = buffreader.readLine()) != null) {
					content += line + "\n";
				}

			} catch (java.io.FileNotFoundException e) {
				CLog.d("readFileToText", "The filePath doesn't exist.");
			} catch (IOException e) {
				CLog.d("readFileToText", e.getMessage());
			} finally {
				if (buffreader != null) {

					try {
						buffreader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		}
		return content;
	}

}
