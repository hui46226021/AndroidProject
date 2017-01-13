package com.jereibaselibrary.tools;

import android.util.Log;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * <b>数字处理类</b>
 * <br>
 * 
 * @author JerehSoft
 *
 * @version 1.0 BY 2014-06-12	格式化基础数据类型，共5个<br>
 * 
 */
public class JRNumberUtils {

	/**
	 * 格式化整型
	 * @param obj
	 * @return
	 */
	public static int formatInt(Object obj) {
		int num = 0;
		try {
			if (obj != null && obj.toString().length() > 0)
				num = Integer.parseInt(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	
	/**
	 * 格式化布尔类型
	 * @param obj
	 * @return
	 */
	public static int formatBool(Object obj) {
		try {
			String bool = obj.toString();
			if (bool != null && "true".equalsIgnoreCase(bool))
				return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 格式化Double类型
	 * @param obj
	 * @return
	 */
	public static double formatDouble(Object obj) {
		double num = 0;
		try {
			if (obj != null && obj.toString().length() > 0)
				num = Double.parseDouble(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	
	/**
	 * 格式化浮点类型
	 * @param obj
	 * @return
	 */
	public static float formatFloat(Object obj) {
		float num = 0;
		try {
			if (obj != null && obj.toString().length() > 0)
				num = Float.parseFloat(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	
	/**
	 * 格式化长整型
	 * @param obj
	 * @return
	 */
	public static long formatLong(Object obj) {
		long num = 0;
		try {
			if (obj != null && obj.toString().length() > 0)
				num = Long.parseLong(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}

		/***
		 * 返回格式化整型
		 * @param o
		 * @return
		 */
		public static int getFormatIntByBool(String bool)
		{
			try
			{
				return (bool.equalsIgnoreCase("true"))?1:0;
			}catch(Exception ex){return 0;}
		}
		/***
		 * 返回格式化整型
		 * @param o
		 * @return
		 */
	public static int getFormatInt(Object o)
	{
		try
		{
			return (o!=null&&!o.toString().equals(""))?Integer.parseInt(o.toString()):0;
		}catch(Exception ex){
			ex.getStackTrace();

			return 0;
		}
	}
	/**
	 * 返回格式化整型，为空时返回默认值
	 * @param o
	 * @param defaultValue
	 * @return
	 */
	public static int getFormatInt(Object o,int defaultValue)
	{
		try
		{
			return (o!=null&&!o.toString().equals(""))?Integer.parseInt(o.toString()):defaultValue;
		}catch(Exception ex){return defaultValue;}
	}
	/***
	 * 返回格式化DOUBLE型
	 * @param o
	 * @return
	 */
	public static double getFormatDouble(Object o)
	{
		try
		{
			return (o!=null&&!o.toString().equals(""))?Double.parseDouble(o.toString()):0;
		}catch(Exception ex){return 0.0;}
	}
	/**
	 * 数字转String 0返回""
	 * @Title: getFormatNumStr
	 * @Description: TODO
	 * @param @param o
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getFormatNumStr(Object o)
	{
		try
		{
			return (o != null && !o.toString().equals("")) ? o.toString(): "";
		}
		catch(Exception ex)
		{
			return "";
		}
	}
	/***
	 * 返回格式化DOUBLE型
	 * @param o
	 * @return
	 */
	public static int getFormatDoubleIntValye(Object o)
	{
		try
		{
			double v=(o!=null&&!o.toString().equals(""))?Double.parseDouble(o.toString()):0;
			return (int)v;
		}catch(Exception ex){return 0;}
	}
	/**
	 * 返回格式化DOUBLE型，为空时返回默认值
	 * @param o
	 * @param defaultValue
	 * @return
	 */
	public static double getFormatDouble(Object o,double defaultValue)
	{
		try
		{
			return (o!=null&&!o.toString().equals(""))?Double.parseDouble(o.toString()):defaultValue;
		}catch(Exception ex){return defaultValue;}
	}
	/***
	 * 返回格式化Float型
	 * @param o
	 * @return
	 */
	public static float getFormatFloat(Object o)
	{
		try
		{
			return (o!=null&&!o.toString().equals(""))?Float.parseFloat(o.toString()):0;
		}catch(Exception ex){return 0.0f;}
	}
	/***
	 * 返回格式化Float型
	 * @param o
	 * @return
	 */
	public static int getFormatFloatIntValue(Object o)
	{
		try
		{
			float v=(o!=null&&!o.toString().equals(""))?Float.parseFloat(o.toString()):0;
			return (int)v;
		}catch(Exception ex){return 0;}
	}
	/**
	 * 返回格式化Float型，为空时返回默认值
	 * @param o
	 * @param defaultValue
	 * @return
	 */
	public static float getFormatFloat(Object o,float defaultValue)
	{
		try
		{
			return (o!=null&&!o.toString().equals(""))?Float.parseFloat(o.toString()):defaultValue;
		}catch(Exception ex){return defaultValue;}
	}
	/***
	 * 返回格式化Float型
	 * @param o
	 * @return
	 */
	public static long getFormatLong(Object o)
	{
		try
		{
			return (o!=null&&!o.toString().equals(""))?Long.parseLong(o.toString()):0;
		}catch(Exception ex){return 0l;}
	}
	/**
	 * 返回格式化Float型，为空时返回默认值
	 * @param o
	 * @param defaultValue
	 * @return
	 */
	public static long getFormatLong(Object o,long defaultValue)
	{
		try
		{
			return (o!=null&&!o.toString().equals(""))?Long.parseLong(o.toString()):defaultValue;
		}catch(Exception ex){return defaultValue;}
	}
	public static double getBigDecimal(double value)
	{
		return new BigDecimal(value).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	// 将字母转换成数字
	public static int letterToNum(String input) {
		int outInt=0;
		try
		{
			outInt=getFormatInt16(input);
		}catch(NumberFormatException ex){
			ex.getStackTrace();
			StringBuffer sb=new StringBuffer();
			for (byte b : input.getBytes()) {
				sb.append(b-96);
			}
			outInt=getFormatInt16(sb.toString().replace("-", ""));
		}
		return outInt;
	}

	/**
	 * 转换小数，四舍五入保留设定位数小数
	 * @Title: getBigDecimal
	 * @Description: TODO
	 * @param @param value
	 * @param @param length
	 * @param @return
	 * @return double
	 * @throws
	 */
	public static double getBigDecimal(double value,int length)
	{
		BigDecimal   b   =   new   BigDecimal(value);
		double   f  =   b.setScale(length,   BigDecimal.ROUND_HALF_UP).doubleValue();
		return f;
	}

	/***
	 * 返回格式化整型
	 * @param o
	 * @return
	 */
	public static int getFormatInt16(Object o)
	{
		return (o!=null&&!o.toString().equals(""))?Integer.parseInt(o.toString(),16):0;
	}
	/**
	 *  判断数组中是否包含值，支持数值或字符串
	 * @Title: isInArrays
	 * @Description: TODO
	 * @param @param o
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean isInArrays(Object[] oArray,Object o)
	{
		for(int i = 0;i<oArray.length;i++)
		{
			if(oArray instanceof String[])
			{
				if(JRStringUtils.getFormatStr(oArray[i]).equals(JRStringUtils.getFormatStr(o)))
				{
					return true;
				}
			}else{
				if(getFormatFloat(oArray[i])==getFormatFloat(o))
				{
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * List是否包含对象
	 * @Title: isInList
	 * @Description: TODO
	 * @param @param list
	 * @param @param o
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean isInList(List<Object> list, Object o)
	{
		if(list==null||list.isEmpty()||o==null)
		{
			return false;
		}
		for(Object ol:list)
		{
//            System.out.println(o.toString()+">>>>>>>>"+ol.toString());
			if(ol instanceof String&&o instanceof String)
			{
				if(JRStringUtils.getFormatStr(ol).equals(JRStringUtils.getFormatStr(o)))
				{
					return true;
				}
			}else{
				if(getFormatFloat(ol)==getFormatFloat(o))
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 转换小数，保留小数点后length位
	 * @Title: getFormatDoublePrecise
	 * @Description: TODO
	 * @param @param o
	 * @param @param format  .####
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getFormatDoublePrecise(Object o,String format)
	{
		double value=getFormatDouble(o);
		DecimalFormat df = new DecimalFormat(format);
		return df.format(value);
	}
}
