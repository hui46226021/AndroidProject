package com.jereibaselibrary.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;


import com.jereibaselibrary.tools.JRStringUtils;

import java.util.ArrayList;
import java.util.List;


/***
 * 数据库实体基本类
 * 1、表的创建
 * 2、数据库的基本操作
 * @ClassName: JEREHDBService 
 * @Description: TODO
 * @author Guo Jingbing
 * @date 2013-2-17 下午2:29:05
 */
public class JEREHDBService 
{
	/**
	 * 查询总条数
	 * @param ctx 上下文
	 * @param cls 类即表名
	 * **/
	public static int count(Context ctx,Class<?> cls)
	{
		int count=0;
		JEREHDBHepler helper=new JEREHDBHepler(ctx);
		SQLiteDatabase conn=helper.getConnection();
		Cursor cursor=null;
		try
		{
			cursor=helper.commonQuery(conn,"SELECT count(0) FROM "+ JRStringUtils.insertXHX(cls.getSimpleName())+"");
			while(cursor!=null&&cursor.moveToNext())
			{
				count=cursor.getInt(0);
			}
		}catch(Exception ex){}
		finally
		{
			conn.close();
			if(cursor!=null)
			{
				cursor.close();
			}
		}
		return count;
	}
	/**
	 * 查询总条数
	 * @param ctx 上下文
	 * @param sql SQL语句
	 * **/
	public static int count(Context ctx,String sql)
	{
		int count=0;
		JEREHDBHepler helper=new JEREHDBHepler(ctx);
		SQLiteDatabase conn=helper.getConnection();
		Cursor cursor=null;
		try
		{
			cursor=helper.commonQuery(conn,sql);
			int i=0;
			while(cursor.moveToNext())
			{
				i++;
			}
			count=i;
		}catch(Exception ex){}
		finally
		{
			conn.close();
			if(cursor!=null)
			{
				cursor.close();
			}
		}
		return count;
	}
	/**
	 * 根据SQL语句查询
	 * @param ctx
	 * @param cls
	 * @param sql
	 * @return
	 */
	public static List<?> list(Context ctx,Class<?> cls) 
	{
		List<Object> list=new ArrayList<Object>();
		JEREHDBHepler helper=new JEREHDBHepler(ctx);
		SQLiteDatabase conn=helper.getConnection();
		Cursor cursor=null;
		try
		{
			cursor=helper.commonQuery(conn,"SELECT * FROM "+JRStringUtils.insertXHX(cls.getSimpleName())+"");
			list=new DBUtils().SQLSetter(helper,conn,cursor,cls,true);
		}catch(Exception ex){}
		finally
		{
			if(conn!=null){conn.close();}
			if(cursor!=null){cursor.close();}
		}
		return list;
	}
	/**
	 * 根据SQL语句查询
	 * @param ctx
	 * @param cls
	 * @param sql
	 * @return
	 */
	public static List<?> list(Context ctx,Class<?> cls,String sql)
	{
		List<Object> list=new ArrayList<Object>();
		JEREHDBHepler helper=new JEREHDBHepler(ctx);
		SQLiteDatabase conn=helper.getConnection();
		Cursor cursor=null;
		try
		{

			cursor=helper.commonQuery(conn,sql);
			list=new DBUtils().SQLSetter(helper,conn,cursor,cls,true);
		}catch(Exception ex)
		{
			Log.e(">>>>>>>","");
		}
		finally
		{
			if(conn!=null){conn.close();}
			if(cursor!=null){cursor.close();}
		}
		return list;
	}


	/**
	 * 根据分页查询
	 * @param ctx
	 * @param cls
	 * @param where  条件
	 * @param orderBy 排序
	 * @param rows 行数
     * @param page 页数
     * @return
     */
	public static List<?> listByPage(Context ctx,Class<?> cls,String where,String orderBy,int rows,int page)
	{
		List<Object> list=new ArrayList<Object>();
		JEREHDBHepler helper=new JEREHDBHepler(ctx);
		SQLiteDatabase conn=helper.getConnection();
		Cursor cursor=null;

		if(TextUtils.isEmpty(where)){
			where = " 1=1 ";
		}
		try
		{
			String sql = "SELECT * FROM "+JRStringUtils.insertXHX(cls.getSimpleName())+" where "+where+" order by "+orderBy
					+" Limit "+rows+" Offset "+(page-1)*rows;
//			String sql = "SELECT * FROM "+JEREHCommonStrTools.replaceXHX(cls.getSimpleName());
			cursor=helper.commonQuery(conn,sql);
			list=new DBUtils().SQLSetter(helper,conn,cursor,cls,true);
		}catch(Exception ex)
		{
			Log.e(">>>>>>>","");
		}
		finally
		{
			if(conn!=null){conn.close();}
			if(cursor!=null){cursor.close();}
		}
		return list;
	}
    /**
     * 更新自主选择是否连带保存子表
     * @Title: saveOrUpdate 
     * @Description: TODO
     * @param @param ctx
     * @param @param obj
     * @param @param isInsub
     * @param @return    
     * @return boolean   
     * @throws
     */
    public static boolean saveOrUpdate(Context ctx,Object obj,boolean isInsub)
    {
        boolean result=false;
        JEREHDBHepler helper=new JEREHDBHepler(ctx);
        SQLiteDatabase conn=null;
        try
        {
            conn=helper.getConnection();
            String primaryKeyName=DBUtils.getPrimaryKey(obj);
            String primaryKeyValue=DBUtils.getFieldValue(obj,primaryKeyName);
            if(load(ctx,obj,primaryKeyValue)!=null)
            {
                String whereClause=JRStringUtils.insertXHX(primaryKeyName)+"=?";
                String[] whereArgs={primaryKeyValue};
                int i=conn.update(JRStringUtils.insertXHX(obj.getClass().getSimpleName()),new DBUtils().SQLGetter(conn,obj,isInsub), whereClause, whereArgs);
                result=(i>0?true:false);
            }else
            {
                result=save(ctx,obj,isInsub);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        finally
        {
            conn.close();
        }
        return result;
    }
    /**
     * 默认不保存子表
     * @Title: saveOrUpdate 
     * @Description: TODO
     * @param @param ctx
     * @param @param obj
     * @param @return    
     * @return boolean   
     * @throws
     */
    public static boolean saveOrUpdate(Context ctx,Object obj)
    {
        return saveOrUpdate(ctx,obj,false);
    }
	/**新增**/
	private static boolean save(Context ctx,Object obj,boolean isInsub)
	{
		boolean result=false;
		JEREHDBHepler helper=new JEREHDBHepler(ctx);
		SQLiteDatabase conn=helper.getConnection();
		try
		{
			long i=conn.insert(JRStringUtils.insertXHX(obj.getClass().getSimpleName()),null,new DBUtils().SQLGetter(conn,obj,isInsub));
			result=(i>0?true:false);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		finally
		{
			conn.close();
		}
		return result;
	}
	/***
	 * 查询一条
	 * @param ctx
	 * @param cls
	 * @param id
	 * @return
	 */
	public static Object load(Context ctx,Object obj,String id)
	{
	    Class<?> cls = obj.getClass();
		JEREHDBHepler helper=new JEREHDBHepler(ctx);
		SQLiteDatabase conn=helper.getConnection();
		Cursor cursor=null;
		Object reObj = null;
		try
		{
//Log.i("Systemout", ">>>>>>>>>>>>>>"+"SELECT * FROM "+JEREHCommonStrTools.replaceXHX(cls.getSimpleName())+" WHERE "+JEREHCommonStrTools.replaceXHX(DBUtils.getPrimaryKey(obj))+"='"+id+"'");
			cursor=helper.commonQuery(conn,"SELECT * FROM "+JRStringUtils.insertXHX(cls.getSimpleName())+" WHERE "+JRStringUtils.insertXHX(DBUtils.getPrimaryKey(obj))+"='"+id+"'");
			List<Object> list=new DBUtils().SQLSetter(helper,conn,cursor,cls,true);
			if(list!=null&&!list.isEmpty())
			{
			    reObj=list.get(0);
			}
			list=null;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		finally
		{
			conn.close();
			if(cursor!=null)
			{
				cursor.close();
			}
		}
		return reObj;
	}
	/**
	 * 获得单独一条
	 * @param ctx
	 * @param cls
	 * @param sql
	 * @return
	 */
	public static Object singleLoadBySQL(Context ctx,Class<?> cls,String sql)
	{
		Object obj=null;
		JEREHDBHepler helper=new JEREHDBHepler(ctx);
		SQLiteDatabase conn=helper.getConnection();
		Cursor cursor=null;
		try
		{
			cursor=helper.commonQuery(conn,sql);
			List<Object> list=new DBUtils().SQLSetter(helper,conn,cursor,cls,false);
			if(list!=null&&!list.isEmpty())
			{
				obj=list.get(0);
			}
			list=null;
		}catch(Exception ex){}
		finally
		{
		    if(conn!=null)
		    {
	            conn.close();
	            if(cursor!=null)
	            {
	                cursor.close();
	                cursor=null;
	            }
	            conn=null;
		    }
		}
		return obj;
	}
	/***
	 * 删除一条
	 * @param ctx
	 * @param cls
	 * @param primaryKey
	 * @param primaryKeyValue
	 * @return
	 */
	public static boolean delete(Context ctx, Class<?> cls, String primaryKey, String primaryKeyValue)
	{

		JEREHDBHepler helper=new JEREHDBHepler(ctx);
		SQLiteDatabase conn=helper.getConnection();
		boolean isDelete =false;
		try
		{

			 isDelete=helper.commonExecSQL(conn,"DELETE FROM "+JRStringUtils.insertXHX(cls.getSimpleName())
					+" WHERE "+primaryKey+"='"+primaryKeyValue+"'");

		}catch(Exception ex)
		{

		}
		finally
		{
			conn.close();
		}
		return isDelete;
	}
	/***
	 * 删除一条
	 * @param ctx
	 * @param cls
	 * @param primaryKey
	 * @param primaryKeyValue
	 * @return
	 */
	public static boolean delete(Context ctx,Class<?> cls,String primaryKey,int primaryKeyValue)
	{
		boolean isDelete = false;
		JEREHDBHepler helper=new JEREHDBHepler(ctx);
		SQLiteDatabase conn=helper.getConnection();
		try
		{

			 isDelete=helper.commonExecSQL(conn,"DELETE FROM "+JRStringUtils.insertXHX(cls.getSimpleName())
					+" WHERE "+primaryKey+"="+primaryKeyValue+"");

		}catch(Exception ex)
		{

		}
		finally
		{
			conn.close();
		}
		return isDelete;
	}
	/***
	 * 删除所有
	 * @param ctx
	 * @param cls
	 * @return
	 */
	public static boolean deleteAll(Context ctx,Class<?> cls)
	{
		boolean isDelete=false;
		JEREHDBHepler helper=new JEREHDBHepler(ctx);
		SQLiteDatabase conn=helper.getConnection();
		try
		{

			 isDelete=helper.commonExecSQL(conn,"DELETE FROM "+JRStringUtils.insertXHX(cls.getSimpleName())+"");

		}catch(Exception ex)
		{

		}
		finally
		{
			conn.close();
		}
		return isDelete;
	}
	/***
	 * 根据SQL语句来删除
	 * @param ctx
	 * @param sql
	 * @return
	 */
	public static boolean deleteAll(Context ctx,String sql)
	{
		boolean result=false;
		JEREHDBHepler helper=new JEREHDBHepler(ctx);
		SQLiteDatabase conn=helper.getConnection();
		try
		{
			result=helper.commonExecSQL(conn,sql);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			conn.close();
		}
		return result;
	}
	/***
	 * 根据SQL语句来删除
	 * @param ctx
	 * @param sql
	 * @return
	 */
	public static boolean delete(Context ctx,String sql)
	{
		boolean result=false;
		JEREHDBHepler helper=new JEREHDBHepler(ctx);
		SQLiteDatabase conn=helper.getConnection();
		try
		{
			result=helper.commonExecSQL(conn,sql);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			conn.close();
		}
		return result;
	}

}
