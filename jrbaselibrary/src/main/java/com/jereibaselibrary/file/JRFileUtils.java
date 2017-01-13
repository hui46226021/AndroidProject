package com.jereibaselibrary.file;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by zhush on 2017/1/12
 * E-mail zhush@jerei.com
 * PS   getFileSize      获取文件大小
 * 		getFileTypeName  获得文件类型名称
 * 	 	getFileType		 获得文件类型
 */

public class JRFileUtils {
	/**
	 *
	 * @param downSize
	 * @param totalSize
     * @return
     */
	  public static int getFileProcess(long downSize,long totalSize)
	    {
	    	int returnProcess=0;
	    	double process=(downSize/1024.0)/(totalSize/1024.0)*100;
	    	returnProcess=(int)process;
	    	return returnProcess;
	    }
	    /**
	     * 获取文件大小
	     * @param totalSize
	     * @return
	     */
	    public static String getFileSize(long totalSize)
	    {
	    	String sizeStr="0KB";
	    	if(totalSize/(1024*1024)>=1)
			{
	    		Double size=(totalSize/1024.0/1024.0);
	    		sizeStr=String.valueOf(size);
	    		if(sizeStr!=null&&sizeStr.length()>4)
	    		{
	    			sizeStr=sizeStr.substring(0,4);
	    		}
	    		sizeStr=sizeStr+"MB";
			}else
			{
				Double size=(totalSize/1024.0);
	    		sizeStr=String.valueOf(size);
	    		if(sizeStr!=null&&sizeStr.length()>4)
	    		{
	    			sizeStr=sizeStr.substring(0,4);
	    		}
	    		sizeStr=sizeStr+"KB";
			}
	    	return sizeStr;
	    }
	    /**
	     * 获得文件类型名称
	     * @param fileType
	     * @return
	     */
	    public static String getFileTypeName(String fileType)
	    {
	    	String typeName="未定义";
	    	if(fileType.equals("jpg")||fileType.equals("jpeg")||fileType.equals("gif")||fileType.equals("bmp")||fileType.equals("png"))
	        {
	    		typeName="图片文件";
	        }else if(fileType.equals("3gp")||fileType.equals("mp4"))
	        {
	        	typeName="视频文件";
	        }
	        else if(fileType.equals("mp3")||fileType.equals("wmv"))
	        {
	        	typeName="声音文件";
	        }else  if(fileType.equals("xls")||fileType.equals("xlsx"))
	        {
	        	typeName="Excel表格";
	        }else  if(fileType.equals("doc")||fileType.equals("docx"))
	        {
	        	typeName="Word文档";
	        }else  if(fileType.equals("pdf")||fileType.equals("pdf"))
	        {
	        	typeName="PDF文档";
	        }else  if(fileType.equalsIgnoreCase("txt"))
	        {
	        	typeName="TXT文本文档";
	        }
	    	return typeName;
	    	
	    }
	    /**
	     * 获得文件类型
	     * @param fileType
	     * @return
	     */
	    public static int getFileType(String fileType)
	    {
	    	int type=-1;
	    	if(fileType.equalsIgnoreCase("jpg")||fileType.equalsIgnoreCase("jpeg")||fileType.equalsIgnoreCase("gif")||fileType.equalsIgnoreCase("bmp")||fileType.equalsIgnoreCase("png"))
	        {
	    		type=1;
	        }else if(fileType.equalsIgnoreCase("3gp")||fileType.equalsIgnoreCase("mp4"))
	        {
	        	type=2;
	        }
	        else if(fileType.equalsIgnoreCase("mp3")||fileType.equalsIgnoreCase("wmv"))
	        {
	        	type=3;
	        }else  if(fileType.equalsIgnoreCase("xls")||fileType.equalsIgnoreCase("xlsx"))
	        {
	        	type=4;
	        }else  if(fileType.equalsIgnoreCase("doc")||fileType.equalsIgnoreCase("docx"))
	        {
	        	type=5;
	        }else  if(fileType.equalsIgnoreCase("ppt")||fileType.equalsIgnoreCase("pptx"))
	        {
	        	type=6;
	        }
	        else  if(fileType.equalsIgnoreCase("pdf"))
	        {
	        	type=7;
	        }
	        else  if(fileType.equalsIgnoreCase("txt"))
	        {
	        	type=8;
	        }
	    	return type;
	    	
	    }
		/**
		 * TODO 获取apk安装时间
		 * @param apkname
		 * @return
		 */
		public static  String getAPKData(String apkname,Context ctx)
		{
			  PackageManager pm = ctx.getPackageManager();
		 	  String p = "";
		 		for (ApplicationInfo info : pm.getInstalledApplications(0)) 
		 		{
		 			String pmm = pm.getApplicationLabel(info).toString();
		 			if (pmm.equals(apkname)) {
		 			p =	info.sourceDir;
		 			}
		 		}
		        File f = new File(p);  
		        Calendar cal = Calendar.getInstance();  
		        long time = f.lastModified();  
		         SimpleDateFormat formatter = new  
		         SimpleDateFormat("yyyy-MM-dd");  
		         cal.setTimeInMillis(time);  
			return (formatter.format(cal.getTime()));
		}
		/**
		 * TODO 获取文件创建时间(如果不存在，则返回1970-01-01)
		 * @param filename
		 * @return
		 */
		public static  String getFileData(String filename)
		{
	        File f = new File(filename);  
	        Calendar cal = Calendar.getInstance();  
	        long time = f.lastModified();  
	         SimpleDateFormat formatter = new  
	         SimpleDateFormat("yyyy-MM-dd");  
	         cal.setTimeInMillis(time);           
	         return(formatter.format(cal.getTime()));
		}
		/**
		 * 打开文件
		 * @param filePath
		 * @param apName
		 */
		public static void openFile(Context ctx,String filePath,String apName)
		{
			File file = new File(filePath);
			if (file.exists())  
			{
				Uri path = Uri.parse(filePath);
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(path, apName);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				try 
				{
					ctx.startActivity(intent);
	            } 
				catch (ActivityNotFoundException e){}
	        }
		}
		/**
		 * 打开文件
		 * @param filePath
		 * @param apName
		 */
		public static void openFile(Context ctx,String filePath)
		{
			File file = new File(filePath);
			if (file.exists())  
			{
				Uri path = Uri.parse(filePath);
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(path, getMIMEType(file));
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				try 
				{
					ctx.startActivity(intent);
	            } 
				catch (ActivityNotFoundException e){}
	        }
		}
		/**
		 * 打开文件
		 * @param filePath
		 * @param apName
		 */
		public static void openFile(Activity act,String filePath)
		{
			File file = new File(filePath);
			if (file.exists())  
			{
				Uri path = Uri.parse(filePath);
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(path, getMIMEType(file));
				intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
				try 
				{
					act.startActivity(intent);
	            } catch (ActivityNotFoundException e){
	            	Log.e("jrerror", e.toString());
	            }
	        }
		}
		/**Like功能**/
		public static int likeFUCByCounts(String str,String pattenStr)
		{
			int result=0;
			if(str!=null&&!str.equals(""))
			{
				Matcher m = Pattern.compile(pattenStr, Pattern.CASE_INSENSITIVE).matcher(str);
				while(m.find())
				{
					result++;
				}
			}
			return result;
		}
	    /**
	     * 文件是否存在
	     * @param filePath
	     * @return
	     */
	    public static boolean fileIsExists(String filePath)
	    {
	        try
	        {
	            File f=new File(filePath);
	            if(f.exists())
	            {
	                return true;
	            }
	        }catch(Exception ex){}
	        return false;
	    }
		/**
		 * 根据文件后缀名获得对应的MIME类型。
		 * @param file
		 */ 
	    public static String getMIMEType(File file) { 
		     
		    String type="*/*"; 
		    String fName = file.getName(); 
		    //获取后缀名前的分隔符"."在fName中的位置。 
		    int dotIndex = fName.lastIndexOf("."); 
		    if(dotIndex < 0){ 
		        return type; 
		    } 
		    /* 获取文件的后缀名*/ 
		    String end=fName.substring(dotIndex,fName.length()).toLowerCase(); 
		    if(end=="")return type; 
		    //在MIME和文件类型的匹配表中找到对应的MIME类型。 
		    for(int i=0;i<MIME_MapTable.length;i++){ //MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？ 
		        if(end.equals(MIME_MapTable[i][0])) 
		            type = MIME_MapTable[i][1]; 
		    }        
		    return type; 
		} 
		/**
		 * 根据文件后缀名获得对应的MIME类型。
		 * @param file
		 */ 
		public static String getMIMEType(String filePath) { 
	        File file = new File(filePath);
	        return getMIMEType(file); 
		} 
		
		public static final String[][] MIME_MapTable={ 
	            //{后缀名，MIME类型} 
	            {".3gp",    "video/3gpp"}, 
	            {".apk",    "application/vnd.android.package-archive"}, 
	            {".asf",    "video/x-ms-asf"}, 
	            {".avi",    "video/x-msvideo"}, 
	            {".bin",    "application/octet-stream"}, 
	            {".bmp",    "image/bmp"}, 
	            {".c",  "text/plain"}, 
	            {".class",  "application/octet-stream"}, 
	            {".conf",   "text/plain"}, 
	            {".cpp",    "text/plain"}, 
	            {".doc",    "application/msword"}, 
	            {".docx",   "application/vnd.openxmlformats-officedocument.wordprocessingml.document"}, 
	            {".xls",    "application/vnd.ms-excel"},  
	            {".xlsx",   "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"}, 
	            {".exe",    "application/octet-stream"}, 
	            {".gif",    "image/gif"}, 
	            {".gtar",   "application/x-gtar"}, 
	            {".gz", "application/x-gzip"}, 
	            {".h",  "text/plain"}, 
	            {".htm",    "text/html"}, 
	            {".html",   "text/html"}, 
	            {".jar",    "application/java-archive"}, 
	            {".java",   "text/plain"}, 
	            {".jpeg",   "image/jpeg"}, 
	            {".jpg",    "image/jpeg"}, 
	            {".js", "application/x-javascript"}, 
	            {".log",    "text/plain"}, 
	            {".m3u",    "audio/x-mpegurl"}, 
	            {".m4a",    "audio/mp4a-latm"}, 
	            {".m4b",    "audio/mp4a-latm"}, 
	            {".m4p",    "audio/mp4a-latm"}, 
	            {".m4u",    "video/vnd.mpegurl"}, 
	            {".m4v",    "video/x-m4v"},  
	            {".mov",    "video/quicktime"}, 
	            {".mp2",    "audio/x-mpeg"}, 
	            {".mp3",    "audio/x-mpeg"}, 
	            {".mp4",    "video/mp4"}, 
	            {".mpc",    "application/vnd.mpohun.certificate"},        
	            {".mpe",    "video/mpeg"},   
	            {".mpeg",   "video/mpeg"},   
	            {".mpg",    "video/mpeg"},   
	            {".mpg4",   "video/mp4"},    
	            {".mpga",   "audio/mpeg"}, 
	            {".msg",    "application/vnd.ms-outlook"}, 
	            {".ogg",    "audio/ogg"}, 
	            {".pdf",    "application/pdf"}, 
	            {".png",    "image/png"}, 
	            {".pps",    "application/vnd.ms-powerpoint"}, 
	            {".ppt",    "application/vnd.ms-powerpoint"}, 
	            {".pptx",   "application/vnd.openxmlformats-officedocument.presentationml.presentation"}, 
	            {".prop",   "text/plain"}, 
	            {".rc", "text/plain"}, 
	            {".rmvb",   "audio/x-pn-realaudio"}, 
	            {".rtf",    "application/rtf"}, 
	            {".sh", "text/plain"}, 
	            {".tar",    "application/x-tar"},    
	            {".tgz",    "application/x-compressed"},  
	            {".txt",    "text/plain"}, 
	            {".wav",    "audio/x-wav"}, 
	            {".wma",    "audio/x-ms-wma"}, 
	            {".wmv",    "audio/x-ms-wmv"}, 
	            {".wps",    "application/vnd.ms-works"}, 
	            {".xml",    "text/plain"}, 
	            {".z",  "application/x-compress"}, 
	            {".zip",    "application/x-zip-compressed"}, 
	            {"",        "*/*"}   
	        };

}
