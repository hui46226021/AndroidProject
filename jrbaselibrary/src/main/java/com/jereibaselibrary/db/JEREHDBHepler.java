
package com.jereibaselibrary.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



/**
 * 数据库操作类
 * 
 * @ClassName: JEREHDBHepler
 * @Description: TODO
 * @author Guo Jingbing
 * @date 2013-9-9 下午2:00:31
 */
public class JEREHDBHepler extends SQLiteOpenHelper {
	private static final String name = "itcast"; //数据库名称
	private static final int version = 2; //数据库版本
	@Override
	public void onCreate(SQLiteDatabase db) {
		initTable(db);// 初始化数据库结构
	}

	/** 构造函数,用于访问数据库 */
	public JEREHDBHepler(Context context) {
		super(context, DBUtils.DB_NAME, null, DBUtils.DB_VERSION);
	}

	/**
	 * 删除数据库
	 * 
	 * @param context
	 * @return
	 */
	public void deleteDataBase(Context context) {
		try {
			context.deleteDatabase(DBUtils.DB_NAME);
		} catch (Exception ex) {
		}
	}

	/** 获得数据库访问连接 **/
	public SQLiteDatabase getConnection() {
		SQLiteDatabase db = null;
		try {
			db = this.getReadableDatabase();// 当磁盘空间没有剩余时以只读方式打开
		} catch (Exception ex) {
			Log.e("jrerror", ex.toString());
			return null;
		}
		return db;
	}

	/** 按SQL语句查询结果集 **/
	public Cursor commonQuery(SQLiteDatabase db, String sql) {
		return db.rawQuery(sql, null);
	}

	/**
	 * 执行SQL语句 包括 创建、删除、更新
	 * **/
	public boolean commonExecSQL(SQLiteDatabase db, String sql) {
		boolean result = false;
		try {
			db.execSQL(sql);
			result = true;
		} catch (Exception ex) {
		}
		return result;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// 更新数据库表结构
		dropTable(db);
		initTable(db);
	}

	/** 创建表并且初始化数据 **/
	public void initTable(SQLiteDatabase db) {


//		commonExecSQL(db, DBUtils.getCreateSQL(TrainModel.class));//培训
//		commonExecSQL(db, DBUtils.getCreateSQL(PrductTypeModel.class));//产品类型
//		commonExecSQL(db, DBUtils.getCreateSQL(PrductModel.class));//产品
//		commonExecSQL(db,DBUtils.getCreateSQL(DataDireModel.class));//文件目录
//		commonExecSQL(db,DBUtils.getCreateSQL(DataFileModel.class));//文件
//		commonExecSQL(db,DBUtils.getCreateSQL(UserModel.class));//用户
//		commonExecSQL(db,DBUtils.getCreateSQL(TrueExamModel.class));//正式考试
//		commonExecSQL(db,DBUtils.getCreateSQL(PrductImgModel.class));// 产品图片
//		commonExecSQL(db,DBUtils.getCreateSQL(RelatedResModel.class)); //相关推荐
//		commonExecSQL(db,DBUtils.getCreateSQL(PrductParamModel.class));//参数信息
//		commonExecSQL(db,DBUtils.getCreateSQL(StudyTypeModel.class));//学习类型

	}

	/**
	 * 删除表
	 * @Title: dropTable
	 * @Description: TODO
	 * @param @param db
	 * @return void
	 * @throws
	 */
	public void dropTable(SQLiteDatabase db) {

//		commonExecSQL(db,DBUtils.getDropSQL(TrainModel.class));//培训
//		commonExecSQL(db,DBUtils.getDropSQL(PrductTypeModel.class));//产品类型
//		commonExecSQL(db,DBUtils.getDropSQL(PrductModel.class));//产品
//		commonExecSQL(db,DBUtils.getDropSQL(DataDireModel.class));//文件目录
//		commonExecSQL(db,DBUtils.getDropSQL(DataFileModel.class));//文件
//		commonExecSQL(db,DBUtils.getDropSQL(UserModel.class));//用户
//		commonExecSQL(db,DBUtils.getDropSQL(TrueExamModel.class));//正式考试
//		commonExecSQL(db,DBUtils.getDropSQL(PrductImgModel.class));// 产品图片
//		commonExecSQL(db,DBUtils.getDropSQL(RelatedResModel.class)); //相关推荐
//		commonExecSQL(db,DBUtils.getDropSQL(PrductParamModel.class));//参数信息
//		commonExecSQL(db,DBUtils.getDropSQL(StudyTypeModel.class));//学习类型
	}
}
