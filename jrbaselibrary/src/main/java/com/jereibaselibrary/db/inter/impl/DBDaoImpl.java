package com.jereibaselibrary.db.inter.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.jereibaselibrary.db.JEREHDBHepler;
import com.jereibaselibrary.db.inter.IDBDao;
import com.jereibaselibrary.db.utils.DBUtil;
import com.jereibaselibrary.tools.JRStringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * <b>对数据库操作类</b>
 * <br>
 * 
 * <br>
 * getWritableDatabase()可以以读写方式打开数据库，当不再需要时要及时close()释放资源。<br>
 * 所以不能在实例化类的同时实例化SQLiteDatabase，避免在同一个Activity中操作数据库2次或2次以上。<br>
 * 因为每使用一次都会close()一次，下次再使用就会报空指针<br>
 * <br>
 * 
 * @author JerehSoft
 *
 * @version 1.0 BY 2014-06-12	实现对数据的增删改查<br>
 * 			1.1 BY 2014-06-25	修改使用DB操作数据库时NullPointerException<br>
 * 			1.2 BY 2014-06-28	实现查询是否存在<br>
 * 			1.3 BY 2014-06-29	实现查询最大值<br>
 * 
 */
public class DBDaoImpl implements IDBDao {

	private JEREHDBHepler helper;
	private SQLiteDatabase db;
	
	public DBDaoImpl(Context ctx) {
		this.helper = new JEREHDBHepler(ctx);
//		this.db = this.helper.getWritableDatabase();// 不能在这里初始化
	}
	
	@Override
	public Object findSingleData(Class<?> cls, String sql) {
		Cursor cursor = null;
		Object obj = null;
		db = this.helper.getConnection();
		try {
			cursor = this.execQuery(db, sql, null);
			List<Object> list = DBUtil.setBeanProps(cursor, cls);
			if (list != null && !list.isEmpty()) {
				obj = list.get(0);
			}
			list = null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(db, cursor);
		}
		return obj;
	}
	
	@Override
	public List<?> findAllData(Class<?> cls, String sql) {
		List<?> list = new ArrayList<Object>();
		Cursor cursor = null;
		db = this.helper.getConnection();
		try {
			cursor = this.execQuery(db, sql, null);
			list = DBUtil.setBeanProps(cursor, cls);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(db, cursor);
		}
		return list;	
	}
	
	@Override
	public int getCount(String sql, String[] params) {
		int count = 0;
		Cursor cursor = null;
		db = this.helper.getConnection();
		try {
			cursor = this.execQuery(db, sql, params);
			if (cursor != null && cursor.getCount() > 0) {
				count = cursor.getCount();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(db, cursor);
		}
		return count;
	}
	
	@Override
	public int getMax(Class<?> cls) {
		Cursor cursor = null;
		db = this.helper.getConnection();
		try {
			cursor = this.execQuery(db, "SELECT * FROM " + JRStringUtils.insertXHX(cls.getSimpleName()), null);
			if (cursor != null && cursor.getCount() > 0) {
				return cursor.getCount();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(db, cursor);
		}
		return 0;
	}

	@Override
	public long insert(Object obj) {
		try {
			db = this.helper.getConnection();
			ContentValues values = DBUtil.getBeanProps(db, obj);
			long id = db.insert(JRStringUtils.insertXHX(obj.getClass().getSimpleName()), null, values);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(db, null);
		}
		return -1;
	}
	
	@Override
	public boolean update(Object obj) {
		boolean succ = false;
		try {
			db = this.helper.getConnection();
			ContentValues values = DBUtil.getBeanProps(db, obj);
			String primaryKey = DBUtil.getPrimaryKey(obj);
			int id = db.update(JRStringUtils.insertXHX(obj.getClass().getSimpleName()), values,
					JRStringUtils.replaceLowerCase(primaryKey) + " = ? ",
					new String[]{ DBUtil.getFieldValue(obj, primaryKey) });
			succ = id > 0 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(db, null);
		}
		return succ;
	}
	
	@Override
	public boolean delete(Object obj) {
		boolean succ = false;
		try {
			db = this.helper.getConnection();
			String primaryKey = DBUtil.getPrimaryKey(obj);
			int id = db.delete(JRStringUtils.insertXHX(obj.getClass().getSimpleName()),
					JRStringUtils.replaceLowerCase(primaryKey) + " = ? ",
					new String[]{ DBUtil.getFieldValue(obj, primaryKey) });
			succ = id > 0 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(db, null);
		}
		return succ;
	}
	
	@Override
	public boolean truncate(Class<?> cls) {
		try {
			db = this.helper.getConnection();
			db.execSQL("DELETE FROM " + JRStringUtils.insertXHX(cls.getSimpleName()));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(db, null);
		}
		return false;
	}
	
	/**
	 * 使用完数据库必须关闭
	 */
	public void close() {
		db.close();
		db = null;
	}
	
	/**
	 * 关闭数据库
	 * @param db
	 */
	public void close(SQLiteDatabase db, Cursor cursor) {
		if (db != null)
			db.close();
		if (cursor != null)
			cursor.close();
	}
	
	/**
	 * 按照SQL语句查询结果集
	 * 
	 * @param db
	 * @param sql
	 * @param args
	 * @return
	 */
	public Cursor execQuery(SQLiteDatabase db, String sql, String[] args) {
		return db.rawQuery(sql, args);
	}

	@Override
	public boolean exists(Object obj) {
		boolean exists = false;
		try {
			db = this.helper.getConnection();
			String primaryKey = DBUtil.getPrimaryKey(obj);
			Cursor c = db.query(JRStringUtils.insertXHX(obj.getClass().getSimpleName()), null,
					JRStringUtils.replaceLowerCase(primaryKey) + " = ? ",
					new String[]{ DBUtil.getFieldValue(obj, primaryKey) }, null, null, null);
			if (c.getCount() > 0)
				exists = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(db, null);
		}
		return exists;
	}
	
}
