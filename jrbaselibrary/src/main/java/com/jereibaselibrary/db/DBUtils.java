package com.jereibaselibrary.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.jereibaselibrary.tools.JRNumberUtils;
import com.jereibaselibrary.tools.JRStringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/***
 * 数据库操作工具类
 * @ClassName: DBUtils 
 * @Description: TODO
 * @author Guo Jingbing
 * @date 2013-3-12 上午11:14:27
 */
public class DBUtils 
{
	public final static String DB_NAME="SERVEC_DB";
	public static int DB_VERSION=6;
	/**
	 * 遍历Cursor
	 * @param cursor 
	 * @param cls 类名
	 * @param delay 是否延迟加载[ List 属性]
	 * @return
	 */
	public List<Object> SQLSetter(JEREHDBHepler helper, SQLiteDatabase conn, Cursor cursor, Class<?> cls, boolean delay)
	{


		delay = false;//禁止所有加载子list
		/****
		 * 兼容如下类型
		 * 1\String
		 * 2\int
		 * 3\double
		 * 4\float
		 * 5\long
		 * 6\List
		 */
		List<Object> list=new ArrayList<Object>();
		Object obj=null;
		try
		{
			Class<?> typeClass;
			Field[] fields=cls.getDeclaredFields();
			while(cursor.moveToNext())
			{
				obj=cls.newInstance();
				int c;
				for(int i=0;i<fields.length;i++)
				{
					fields[i].setAccessible(true);
					typeClass =fields[i].getType();
					try
					{
	                    c=cursor.getColumnIndexOrThrow(JRStringUtils.replaceLowerCase(fields[i].getName()));
	                    if(typeClass.getSimpleName().equalsIgnoreCase("String"))
	                    {
	                        fields[i].set(obj,JRStringUtils.getFormatStr(cursor.getString(c))); 
	                    }else if(typeClass.getSimpleName().equalsIgnoreCase("Integer")||typeClass.getSimpleName().equalsIgnoreCase("int"))
	                    {
	                        fields[i].set(obj,cursor.getInt(c)); 
	                    }else if(typeClass.getSimpleName().equalsIgnoreCase("Boolean")||typeClass.getSimpleName().equalsIgnoreCase("boolean"))
	                    {
	                        fields[i].set(obj,JRStringUtils.getFormatStr(cursor.getString(c)).equalsIgnoreCase("0")?false:true); 
	                    }else if(typeClass.getSimpleName().equalsIgnoreCase("Double")||typeClass.getSimpleName().equalsIgnoreCase("double"))
	                    {
	                        fields[i].set(obj,cursor.getDouble(c)); 
	                    }else if(typeClass.getSimpleName().equalsIgnoreCase("Float")||typeClass.getSimpleName().equalsIgnoreCase("float"))
	                    {
	                        fields[i].set(obj,cursor.getFloat(c)); 
	                    }else if(typeClass.getSimpleName().equalsIgnoreCase("Long")||typeClass.getSimpleName().equalsIgnoreCase("long"))
	                    {
	                        fields[i].set(obj,cursor.getLong(c)); 
	                    }else if(typeClass.getSimpleName().equalsIgnoreCase("List"))
	                    {
	                        if(delay)
	                        {
	                            /***
	                             * 得到List夹杂的类名,使用递归来完成所有子表的加载
	                             * 得到主键名称以及主键的值,如果为空则获取
	                             * 根据主键的生成子表查询语句
	                             */
	                            String genericClassName=getGenericClassName(fields[i]);
	                            Class<?> childCls=Class.forName(genericClassName);
	                            String primaryKeyName=obj.getClass().getDeclaredField(fields[i].getName()+"PrimaryKey").getName();
	                            Field field=obj.getClass().getDeclaredField(primaryKeyName);
	                            field.setAccessible(true);
	                            String sql="";
	                            if(field.getClass().getSimpleName().equalsIgnoreCase("Integer"))
	                            {
	                                int primaryKeyValue= JRNumberUtils.getFormatInt(field.get(obj));
	                                if(primaryKeyValue==0)
	                                {
	                                    c=cursor.getColumnIndexOrThrow(JRStringUtils.replaceLowerCase(primaryKeyName));
	                                    primaryKeyValue=cursor.getInt(c);
	                                }
	                                sql="SELECT * FROM "
	                                    +JRStringUtils.insertXHX(childCls.getSimpleName())+" WHERE foreign_key="+primaryKeyValue+"";
	                            }else
	                            {
	                                String primaryKeyValue=JRStringUtils.getFormatStr(field.get(obj));
	                                if(primaryKeyValue.equalsIgnoreCase(""))
	                                {
	                                    c=cursor.getColumnIndexOrThrow(JRStringUtils.replaceLowerCase(primaryKeyName));
	                                    primaryKeyValue=cursor.getString(c);
	                                }
	                                sql="SELECT * FROM "
	                                    +JRStringUtils.insertXHX(childCls.getSimpleName())+" WHERE foreign_key='"+primaryKeyValue+"'";
	                            }
	                            Cursor childCursor=helper.commonQuery(conn,sql);
	                            fields[i].set(obj,SQLSetter(helper,conn,childCursor,childCls,delay));
	                        } 
	                    }
	                    else
	                    {
	                        fields[i].set(obj,cursor.getString(c));  
	                    }
					}catch(IllegalArgumentException e){
					    continue;
					}
				}
				list.add(obj);
				obj=null;
			}
		}catch(Exception ex){
			ex.printStackTrace();

		}
		return list;
	}
	/**
	 * 遍历Cursor
	 * @param delay 是否执行子表插入
	 * @return
	 */
	public ContentValues SQLGetter(SQLiteDatabase conn,Object obj,boolean delay)
	{
		ContentValues cv = new ContentValues();
		try
		{
			Field[] fields=obj.getClass().getDeclaredFields();
			Class<?> typeClass;
			for(int i=0;i<fields.length;i++)
			{
				fields[i].setAccessible(true);
				typeClass=fields[i].getType();
				if(typeClass.getSimpleName().equalsIgnoreCase("String"))
				{
					cv.put(JRStringUtils.replaceLowerCase(fields[i].getName()),JRStringUtils.getFormatStr(fields[i].get(obj))); 
				}else if(typeClass.getSimpleName().equalsIgnoreCase("Integer")||typeClass.getSimpleName().equalsIgnoreCase("int"))
				{
					cv.put(JRStringUtils.replaceLowerCase(fields[i].getName()),JRNumberUtils.getFormatInt(fields[i].get(obj)));
				}else if(typeClass.getSimpleName().equalsIgnoreCase("Boolean")||typeClass.getSimpleName().equalsIgnoreCase("boolean"))
				{
					cv.put(JRStringUtils.replaceLowerCase(fields[i].getName()),
							JRNumberUtils.getFormatIntByBool(JRStringUtils.getFormatStr(fields[i].get(obj))));
				}else if(typeClass.getSimpleName().equalsIgnoreCase("Double")||typeClass.getSimpleName().equalsIgnoreCase("double"))
				{
					cv.put(JRStringUtils.replaceLowerCase(fields[i].getName()),JRNumberUtils.getFormatDouble(fields[i].get(obj)));
				}else if(typeClass.getSimpleName().equalsIgnoreCase("Float")||typeClass.getSimpleName().equalsIgnoreCase("float"))
				{
					cv.put(JRStringUtils.replaceLowerCase(fields[i].getName()),JRNumberUtils.getFormatFloat(fields[i].get(obj)));
				}else if(typeClass.getSimpleName().equalsIgnoreCase("Long")||typeClass.getSimpleName().equalsIgnoreCase("long"))
				{
					cv.put(JRStringUtils.replaceLowerCase(fields[i].getName()),JRNumberUtils.getFormatLong(fields[i].get(obj)));
				}else if(typeClass.getSimpleName().equalsIgnoreCase("byte[]"))
				{
					cv.put(JRStringUtils.replaceLowerCase(fields[i].getName()),(byte[])fields[i].get(obj)); 
				}else if(typeClass.getSimpleName().equalsIgnoreCase("List"))
				{
					/***
					 * 如果选择插入子表
					 */
					if(delay)
					{
						/***
						 * 1、得到子表表名
						 * 2、得到字段外键字段的值
						 * 3、删除原有数据
						 * 4、执行插入
						 */
						List<?> childList=(List<?>)fields[i].get(obj);
						String primaryKeyName=obj.getClass().getDeclaredField(fields[i].getName()+"PrimaryKey").getName();
						Field field=obj.getClass().getDeclaredField(primaryKeyName);
						field.setAccessible(true);
                        if(childList!=null&&!childList.isEmpty())
                        {
                            //删除子表数据
                            conn.delete(JRStringUtils.insertXHX(childList.get(0).getClass().getSimpleName()),null,null);
                        }
						if(field.getType().getSimpleName().equalsIgnoreCase("Integer")||field.getType().getSimpleName().equalsIgnoreCase("int"))
						{
							int primaryKeyValue=JRNumberUtils.getFormatInt(field.get(obj));
							Field childField=null;
							for(int j=0;childList!=null&&!childList.isEmpty()&&j<childList.size();j++)
							{
								childField=childList.get(j).getClass().getDeclaredField("foreignKey");
								childField.setAccessible(true);
								childField.set(childList.get(j),primaryKeyValue);
								conn.insert(JRStringUtils.insertXHX(childList.get(j).getClass().getSimpleName()),null,SQLGetter(conn,childList.get(j),delay));
								childField=null;
							}
						}else
						{
							String primaryKeyValue=JRStringUtils.getFormatStr(field.get(obj));
							Field childField=null;
							for(int j=0;childList!=null&&!childList.isEmpty()&&j<childList.size();j++)
							{
								childField=childList.get(j).getClass().getDeclaredField("foreignKey");
								childField.setAccessible(true);
								childField.set(childList.get(j),primaryKeyValue);
								conn.insert(JRStringUtils.insertXHX(childList.get(j).getClass().getSimpleName()),null,SQLGetter(conn,childList.get(j),delay));
								childField=null;
							}
						}
					}
				}else
				{
					cv.put(JRStringUtils.replaceLowerCase(fields[i].getName()),JRStringUtils.getFormatStr(fields[i].get(obj))); 
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return cv;
	}

	/**
	 * 获取类名
	 * @param field
	 * @return
	 */
	public static String getGenericClassName(Field field)
	{
		try 
		{
			String type=field.getGenericType().toString();
			return type.substring(type.lastIndexOf("<")+1,type.lastIndexOf(">"));
		} catch (Exception e) {return "";}
	}
	/***
	 * 获得主键名称
	 * @param obj
	 * @return
	 */
	public static String getPrimaryKey(Object obj)
	{
		try
		{
			Field field=obj.getClass().getDeclaredField("primaryKey");
			field.setAccessible(true);
			return JRStringUtils.getFormatStr(field.get(obj));
		}catch(Exception ex){return "id";}
	}
	/***
	 * 获得主键值
	 * @param obj
	 * @return
	 */
	public static String getPrimaryKeyValue(Object obj)
	{
		try
		{
			Field field=obj.getClass().getDeclaredField(getPrimaryKey(obj));
			field.setAccessible(true);
			return JRStringUtils.getFormatStr(field.get(obj));
		}catch(Exception ex){return "id";}
	}
	/**
	 * 获得主键值
	 * @param obj 需要操作的对象
	 * @param keyName 主键名称
	 * @return
	 */
	public static String getFieldValue(Object obj,String keyName)
	{
		try
		{
			Field field=obj.getClass().getDeclaredField(keyName);
			field.setAccessible(true);
			return JRStringUtils.getFormatStr(field.get(obj));
		}catch(Exception ex){return "";}
	}
	/**
	 * 设置字段值
	 * @param obj 需要操作的对象
	 * @param keyName 键名称
	 * @param value   键值
	 * @return
	 */
	public static void setFieldValue(Object obj,String keyName,Object value)
	{
		try
		{
			Field field=obj.getClass().getDeclaredField(keyName);
			field.setAccessible(true);
			field.set(obj, value);
		}catch(Exception ex){}
	}
	/**
	 * 追加字段值(仅限String)
	 * @param obj 需要操作的对象
	 * @param keyName 键名称
	 * @param value   键值
	 * @return
	 */
	public static void setFieldValueAppned(Object obj,String keyName,String value)
	{
		try
		{
			Field field=obj.getClass().getDeclaredField(keyName);
			field.setAccessible(true);
			field.set(obj,((String)field.get(obj))+value);
		}catch(Exception ex){}
	}
	/***
	 * 创建SQL语句
	 * @param cls
	 * @return
	 */
	public static String getCreateSQL(Class<?> cls)
	{
		String createSql="";
		try
		{
			Field[] fields=cls.getDeclaredFields();
			String childCreateSQL="";
			Class<?> typeClass;
			String type="VARCHAR";
			for(int i=0;i<fields.length;i++)
			{
				if(!fields[i].getName().equalsIgnoreCase("id"))
				{
					typeClass=fields[i].getType();;
					if(typeClass.getSimpleName().equalsIgnoreCase("String"))
					{
						type="VARCHAR"; 
					}else if(typeClass.getSimpleName().equalsIgnoreCase("Integer")||typeClass.getSimpleName().equalsIgnoreCase("int"))
					{
						type="INTEGER"; 
					}else if(typeClass.getSimpleName().equalsIgnoreCase("Boolean")||typeClass.getSimpleName().equalsIgnoreCase("boolean"))
					{
						type="VARCHAR"; 
					}else if(typeClass.getSimpleName().equalsIgnoreCase("Float")||typeClass.getSimpleName().equalsIgnoreCase("float"))
					{
						type="FLOAT"; 
					}else if(typeClass.getSimpleName().equalsIgnoreCase("Double")||typeClass.getSimpleName().equalsIgnoreCase("double"))
					{
						type="DOUBLE"; 
					}else if(typeClass.getSimpleName().equalsIgnoreCase("Long")||typeClass.getSimpleName().equalsIgnoreCase("long"))
					{
						type="VARCHAR"; 
					}else if(typeClass.getSimpleName().equalsIgnoreCase("byte[]"))
					{
						type="BLOB"; 
					}
					if(i==fields.length-1)
					{
						childCreateSQL+=JRStringUtils.replaceLowerCase(fields[i].getName())+" "+type;
					}else
					{
						childCreateSQL+=JRStringUtils.replaceLowerCase(fields[i].getName())+" "+type+",";
					}
					if(childCreateSQL.endsWith(",")){
						childCreateSQL.substring(0, childCreateSQL.length()-2);
					}
				}
			}
			fields=null;
			createSql="CREATE TABLE IF NOT EXISTS "+JRStringUtils.insertXHX(cls.getSimpleName())+"(id VARCHAR PRIMARY KEY,"+childCreateSQL+")";
			childCreateSQL=null;
		}catch(Exception ex){}
		return createSql;
	}
    /***
     * DROP SQL语句
     * @param cls
     * @return
     */
    public static String getDropSQL(Class<?> cls)
    {
        return "DROP TABLE IF EXISTS "+JRStringUtils.insertXHX(cls.getSimpleName());
    }
}
