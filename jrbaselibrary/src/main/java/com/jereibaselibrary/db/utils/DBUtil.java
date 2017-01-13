package com.jereibaselibrary.db.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.jereibaselibrary.tools.JRNumberUtils;
import com.jereibaselibrary.tools.JRStringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * <b>数据库工具类</b>
 * <br>
 * 
 * @author JerehSoft
 *
 * @version 1.0 BY 2014-06-10	实现建表语句<br>
 * 			1.1 BY 2014-06-12	实现对数据库的增、删、改、查操作，以及通过反射操作实体类，对实体类赋值<br>
 * 			1.2 BY 2014-06-12	保留建表方法，增删改查方法去掉，这个类只需要能够简单调用即可，增删改查参见DBDao<br>
 * 			1.3 BY 2014-06-26	去掉自定义主键，改用共用主键，但保留服务器数据库表主键<br>
 * 
 */
public class DBUtil {
	
	/**
	 * 通过实体类生成建表语句
	 * 
	 * @param cls	要建表的类
	 * @return	建表语句
	 */
	public static String getCreateSQL(Class<?> cls) {
		String sql = "";
		try {
			Field[] fields = cls.getDeclaredFields();
			String childSQL = "";
			Class<?> typeCls;
			String type = "VARCHAR";
			for (int i = 0; i < fields.length; i++) {
				if (!fields[i].getName().equalsIgnoreCase("primaryKey")) {
					typeCls = fields[i].getType();
					if (JRStringUtils.equals(type, true, "String")) {
						type = "VARCHAR";
					} else if (JRStringUtils.equals(typeCls.getSimpleName(), true, "Integer", "int")) {
						type = "INTEGER";
					} else if (JRStringUtils.equals(typeCls.getSimpleName(), true, "Boolean", "boolean")) {
						type = "VARCHAR";
					} else if (JRStringUtils.equals(typeCls.getSimpleName(), true, "Float", "float")) {
						type = "FLOAT";
					} else if (JRStringUtils.equals(typeCls.getSimpleName(), true, "Double", "double")) {
						type = "DOUBLE";
					} else if (JRStringUtils.equals(typeCls.getSimpleName(), true, "Long", "long")) {
						type = "VARCHAR";
					} else if (JRStringUtils.equals(typeCls.getSimpleName(), true, "byte[]")) {
						type = "BLOB";
					} else if (JRStringUtils.equals(typeCls.getSimpleName(), true, "List", "ArrayList")) {
						continue;
					}
					childSQL += JRStringUtils.replaceLowerCase(fields[i].getName()) + " " + type;
					
					if (i < fields.length - 1) {
						childSQL += ", ";
					}
					if (childSQL.endsWith(",")) {
						childSQL.substring(0, childSQL.length() - 2);
					}
				}
			}
			fields = null;
			sql = "CREATE TABLE IF NOT EXISTS " + JRStringUtils.insertXHX(cls.getSimpleName()) + "( ID INTEGER PRIMARY KEY AUTOINCREMENT, " + childSQL + " )";
			childSQL = null;
		} catch (Exception e) {
		}
		return sql;
	}
	
	/**
	 * 获得实体对象的对应属性的值
	 * 
	 * @param obj	要获取属性值的实体对象
	 * @param fieldName	  要获取的属性名
	 * @return
	 */
	public static String getFieldValue(Object obj, String fieldName) {
		try {
			Field field = obj.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			return JRStringUtils.format(field.get(obj));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 获得实体类的主键名
	 * 
	 * @param obj
	 * @return
	 */
	public static String getPrimaryKey(Object obj) {
		try {
			Field field = obj.getClass().getDeclaredField("primaryKey");
			field.setAccessible(true);
			return JRStringUtils.format(field.get(obj));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 遍历Cursor将数据赋值给实体对象
	 * 
	 * @param cursor
	 * @param cls
	 * @return
	 */
	public static List<Object> setBeanProps(Cursor cursor, Class<?> cls) {
		List<Object> list = new ArrayList<Object>();
		Object obj = null;
		try {
			Class<?> typeCls;
			Field[] fields = cls.getDeclaredFields();
			while (cursor.moveToNext()) {
				obj = cls.newInstance();
				int columnIndex;
				for (int i = 0; i < fields.length; i++) {
					fields[i].setAccessible(true);
					typeCls = fields[i].getType();
					try {
						columnIndex = cursor.getColumnIndexOrThrow(JRStringUtils.replaceLowerCase(fields[i].getName()));
						if (JRStringUtils.equals(typeCls.getSimpleName(), true, "String")) {
							fields[i].set(obj, JRStringUtils.format(cursor.getString(columnIndex)));
						} else if (JRStringUtils.equals(typeCls.getSimpleName(), true, "Integer", "int")) {
							fields[i].set(obj, cursor.getInt(columnIndex));
						} else if (JRStringUtils.equals(typeCls.getSimpleName(), true, "Boolean", "boolean")) {
							fields[i].set(obj, cursor.getString(columnIndex).equalsIgnoreCase("0") ? false : true);
						} else if (JRStringUtils.equals(typeCls.getSimpleName(), true, "Double", "double")) {
							fields[i].set(obj, cursor.getDouble(columnIndex));
						} else if (JRStringUtils.equals(typeCls.getSimpleName(), true, "Float", "float")) {
							fields[i].set(obj, cursor.getFloat(columnIndex));
						} else if (JRStringUtils.equals(typeCls.getSimpleName(), true, "Long", "long")) {
							fields[i].set(obj, cursor.getLong(columnIndex));
						} else if (JRStringUtils.equals(typeCls.getSimpleName(), true, "List", "ArrayList")) {
							
						}
					} catch (IllegalArgumentException e) {
						continue;
					}
				}
				list.add(obj);
				obj = null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 将实体对象转换成数据库可识别的ContentValues对象
	 * 去掉主键( 2个属性：primaryKey和主键ID )
	 * 
	 * @param conn
	 * @param obj
	 * @return
	 */
	public static ContentValues getBeanProps(SQLiteDatabase conn, Object obj) {
		ContentValues cv = new ContentValues();
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			Class<?> typeCls;
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				typeCls = fields[i].getType();
				if (!fields[i].getName().equalsIgnoreCase("primaryKey")) {
					if (JRStringUtils.equals(typeCls.getSimpleName(), true, "String")) {
						cv.put(JRStringUtils.replaceLowerCase(fields[i].getName()), JRStringUtils.format(fields[i].get(obj)));
					} else if (JRStringUtils.equals(typeCls.getSimpleName(), true, "Integer", "int")) {
						cv.put(JRStringUtils.replaceLowerCase(fields[i].getName()), JRNumberUtils.formatInt(fields[i].get(obj)));
					} else if (JRStringUtils.equals(typeCls.getSimpleName(), true, "Boolean", "boolean")) {
						cv.put(JRStringUtils.replaceLowerCase(fields[i].getName()), JRNumberUtils.formatBool(fields[i].get(obj)));
					} else if (JRStringUtils.equals(typeCls.getSimpleName(), true, "Double", "double")) {
						cv.put(JRStringUtils.replaceLowerCase(fields[i].getName()), JRNumberUtils.formatDouble(fields[i].get(obj)));
					} else if (JRStringUtils.equals(typeCls.getSimpleName(), true, "Float", "float")) {
						cv.put(JRStringUtils.replaceLowerCase(fields[i].getName()), JRNumberUtils.formatFloat(fields[i].get(obj)));
					} else if (JRStringUtils.equals(typeCls.getSimpleName(), true, "Long", "long")) {
						cv.put(JRStringUtils.replaceLowerCase(fields[i].getName()), JRNumberUtils.formatLong(fields[i].get(obj)));
					} else if (JRStringUtils.equals(typeCls.getSimpleName(), true, "byte[]")) {
						cv.put(JRStringUtils.replaceLowerCase(fields[i].getName()), (byte[]) fields[i].get(obj));
					} else if (JRStringUtils.equals(typeCls.getSimpleName(), true, "List", "ArrayList")) {
						
					} else {
						cv.put(JRStringUtils.replaceLowerCase(fields[i].getName()), JRStringUtils.format(fields[i].get(obj)));
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return cv;
	}
	
}
