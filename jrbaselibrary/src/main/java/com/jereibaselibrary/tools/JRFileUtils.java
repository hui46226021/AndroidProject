/*
 *   Copyright (C)  2016 android@19code.com
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.jereibaselibrary.tools;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.text.format.Formatter;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by zhush on 2017/1/13
 * E-mail 405086805@qq.com
 * PS
 closeIO 关闭IO流
 isFileExist 文件是否存在
 writeFile 将字符串写入到文件
 readFile 从文件中读取字符串
 copyFileFast 快速复制
 shareFile 分享文件
 zip zip压缩
 unzip zip解压
 formatFileSize 格式化文件大小
 Stream2File 将输入流写入到文件
 createFolder 创建文件夹
 createFolder 创建文件夹(支持覆盖已存在的同名文件夹)
 getFolderName 获取文件夹名称
 deleteFile 删除目录下的文件
 openImage 打开图片
 openVideo 打开视频
 openURL 打开URL
 getRootDirctory 获取手机根目录
 existSDCard  判断SD卡 是否存在
 */

public class JRFileUtils {
    public static void closeIO(Closeable... closeables) {
        if (null == closeables || closeables.length <= 0) {
            return;
        }
        for (Closeable cb : closeables) {
            try {
                if (null == cb) {
                    continue;
                }
                cb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean deleteFile(String filename) {
        return new File(filename).delete();
    }

    public static boolean isFileExist(String filePath,long size) {
        try {
            File file = new File(filePath);
            if(file.exists()&&file.length()==size){
                return true;
            }
        }catch (Exception e){

        }
        return false;

    }

    public static boolean writeFile(String filename, String content) {
        boolean isSuccess = false;
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(filename, false));
            bufferedWriter.write(content);
            isSuccess = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeIO(bufferedWriter);
        }
        return isSuccess;
    }

    public static String readFile(String filename) {
        File file = new File(filename);
        BufferedReader bufferedReader = null;
        String str = null;
        try {
            if (file.exists()) {
                bufferedReader = new BufferedReader(new FileReader(filename));
                str = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeIO(bufferedReader);
        }
        return str;
    }

    public static void copyFileFast(FileInputStream is, FileOutputStream os) throws IOException {
        FileChannel in = is.getChannel();
        FileChannel out = os.getChannel();
        in.transferTo(0, in.size(), out);
    }

    public static void shareFile(Context context, String title, String filePath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        Uri uri = Uri.parse("file://" + filePath);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(Intent.createChooser(intent, title));
    }

    public static void zip(InputStream is, OutputStream os) {
        GZIPOutputStream gzip = null;
        try {
            gzip = new GZIPOutputStream(os);
            byte[] buf = new byte[1024];
            int len;
            while ((len = is.read(buf)) != -1) {
                gzip.write(buf, 0, len);
                gzip.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeIO(is);
            closeIO(gzip);
        }
    }

    public static void unzip(InputStream is, OutputStream os) {
        GZIPInputStream gzip = null;
        try {
            gzip = new GZIPInputStream(is);
            byte[] buf = new byte[1024];
            int len;
            while ((len = gzip.read(buf)) != -1) {
                os.write(buf, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeIO(gzip);
            closeIO(os);
        }
    }

    public static String formatFileSize(Context context, long size) {
        return Formatter.formatFileSize(context, size);
    }

    public static void Stream2File(InputStream is, String fileName) {
        byte[] b = new byte[1024];
        int len;
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(new File(fileName));
            while ((len = is.read(b)) != -1) {
                os.write(b, 0, len);
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeIO(is);
            closeIO(os);
        }
    }

    /**
     * 获取手机跟目录
     */
    public static String getRootDirctory(){
            return  Environment.getExternalStorageDirectory().toString();
    }

    /**
     * 获取 项目在sdk的路径没有 目录则自动创建
     * @return
     */
    public static String getRootAppDirctory(Context context){
        String path = getRootDirctory()+"/"+JRAppUtils.getAppPackageName(context);
        createFolder(path);
        return path;
    }

    /**
     * 判断SD卡是否可用
     * @return
     */
    public static boolean isSDAvailable(){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return true;
        }else {
            return false;
        }
    }

    public static boolean createFolder(String filePath) {
        return createFolder(filePath, false);
    }

    public static boolean createFolder(String filePath, boolean recreate) {
        String folderName = getFolderName(filePath);
        if (folderName == null || folderName.length() == 0 || folderName.trim().length() == 0) {
            return false;
        }
        File folder = new File(folderName);
        if (folder.exists()) {
            if (recreate) {
                deleteFile(folderName);
                return folder.mkdirs();
            } else {
                return true;
            }
        } else {
            return folder.mkdirs();
        }
    }

    public static String getFolderName(String filePath) {
        if (filePath == null || filePath.length() == 0 || filePath.trim().length() == 0) {
            return filePath;
        }
        int filePos = filePath.lastIndexOf(File.separator);
        return (filePos == -1) ? "" : filePath.substring(0, filePos);
    }

    public static boolean deleteFiles(String folder) {
        if (folder == null || folder.length() == 0 || folder.trim().length() == 0) {
            return true;
        }
        File file = new File(folder);
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (!file.isDirectory()) {
            return false;
        }
        for (File f : file.listFiles()) {
            if (f.isFile()) {
                f.delete();
            } else if (f.isDirectory()) {
                deleteFile(f.getAbsolutePath());
            }
        }
        return file.delete();
    }

    public static void openImage(Context mContext, String imagePath) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(imagePath));
        intent.setDataAndType(uri, "image/*");
        mContext.startActivity(intent);
    }

    public static void openVideo(Context mContext, String videoPath) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(videoPath));
        intent.setDataAndType(uri, "video/*");
        mContext.startActivity(intent);
    }

    public static void openURL(Context mContext, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        mContext.startActivity(intent);
    }




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
            Uri path = Uri.fromFile(file);
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
            Uri path = Uri.fromFile(file);
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
