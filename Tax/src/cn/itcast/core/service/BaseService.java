package cn.itcast.core.service;

import java.io.Serializable;
import java.util.List;

import cn.itcast.core.util.PageResult;
import cn.itcast.core.util.QueryHelper;

public interface BaseService<T> {

	//新增
	public void save(T entity);
	//更新
	public void update(T entity);
	//根据id删除
	public void delete(Serializable id);
	//查询列表
	public List<T> findObjects();
	//根据id查询
	public T findObjectById(Serializable id);
	
	//查询列表
	@Deprecated
	public List<T> findObjects(String hql, List<Object> parameters);
	//查询列表-查询助手queryHelper
	public List<T> findObjects(QueryHelper queryHelper);
	//分页查询列表-查询助手queryHelper
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo, int pageSize);

}
