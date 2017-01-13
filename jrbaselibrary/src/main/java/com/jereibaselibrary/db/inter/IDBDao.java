package com.jereibaselibrary.db.inter;

import java.util.List;

/**
 * <b>对数据库接口</b>
 * <br>
 * 
 * @author JerehSoft
 *
 * @version	1.0 BY 2014-06-12	定义了操作数据库的增删改查方法<br>
 * 			1.1 BY 2014-06-18	增加清空表方法<br>
 * 			1.2 BY 2014-06-28	增加是否存在方法<br>
 * 			1.3 BY 2014-06-29	增加查询最大值方法<br>
 * 
 */
public interface IDBDao {

	/**
	 * 查询一条数据
	 * 
	 * @param cls
	 * @param sql
	 * @return
	 */
	public Object findSingleData(Class<?> cls, String sql);
	
	/**
	 * 查询列表
	 * 
	 * @param cls
	 * @param sql
	 * @return
	 */
	public List<?> findAllData(Class<?> cls, String sql);
	
	/**
	 * 查询数量
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int getCount(String sql, String[] params);
	
	/**
	 * 查询最大值
	 * 
	 * @param cls
	 * @return
	 */
	public int getMax(Class<?> cls);
	
	/**
	 * 查询是否存在
	 * 
	 * @param obj
	 * @return
	 */
	public boolean exists(Object obj);
	
	/**
	 * 插入数据
	 * 
	 * @param obj
	 * @return
	 */
	public long insert(Object obj);
	
	/**
	 * 更新数据
	 * 
	 * @param obj
	 * @return
	 */
	public boolean update(Object obj);
	
	/**
	 * 删除数据
	 * 
	 * @param obj
	 * @return
	 */
	public boolean delete(Object obj);
	
	/**
	 * 清空表数据
	 * 
	 * @param cls
	 * @return
	 */
	public boolean truncate(Class<?> cls);
}
