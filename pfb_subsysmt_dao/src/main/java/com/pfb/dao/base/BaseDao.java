package com.pfb.dao.base;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.pfb.common.page.PageQueryParam;
@Repository
public class BaseDao<T> extends DaoSupport {
	private Class<T> mapper;
	private SqlSession sqlQuerySession;
	private SqlSession sqlSession;
	
	public BaseDao() {
		super();
	}
	public void setMapper(Class<T> mapper){
		this.mapper = mapper;
	}
	public BaseDao (Class<T> clazz){
//		Class<?> clazz = this.getClass();
//		Method[] ms = clazz.getMethods();
//		Method m =null;
//		for(Method o : ms){
//			if("add".equals(o.getName())){
//				m = o;break;
//			}
//		}
//		mapper = m.getParameterTypes()[0];
		mapper = clazz;
	}
	
    @Autowired(required = false)
    public final void setSqlQuerySessionFactory(@Qualifier("querySqlSessionFactory")SqlSessionFactory sqlSessionFactory) {
    	if(sqlQuerySession==null){
    		sqlQuerySession = new SqlSessionTemplate(sqlSessionFactory);
    	}
    }

    public final SqlSession getSqlQuerySession() {
        return this.sqlQuerySession;
    }

    @Autowired(required = false)
    public final void setSqlSessionFactory(@Qualifier("sqlSessionFactory")SqlSessionFactory sqlSessionFactory) {
    	if(sqlSession==null){
    		sqlSession = new SqlSessionTemplate(sqlSessionFactory);
    	}
    }

    /**
     * Users should use this method to get a SqlSession to call its statement methods
     * This is SqlSession is managed by spring. Users should not commit/rollback/close it
     * because it will be automatically done.
     * 
     * @return Spring managed thread safe SqlSession 
     */
    public final SqlSession getSqlSession() {
        return this.sqlSession;
    }

    /**
     * {@inheritDoc}
     */
    @Override
	protected void checkDaoConfig() {
        Assert.notNull(this.sqlSession, "Property 'sqlSessionFactory' or 'sqlSessionTemplate' are required");
    }
    
    /**
	 * 添加
	 * 
	 * createTime:下午1:36:58
	 * @author yuzengjia@gintong.com
	 * @param t 封装新添加数据的对象
	 * @return 对应的主键
	 */
	public T add(T t){
		this.getSqlSession().insert(mapper.getName()+".insert", t);
		return t;
	}
	
	/**
	 * 根据主键查询
	 * 
	 * createTime:下午1:37:05
	 * @author yuzengjia@gintong.com
	 * @param id 主键标识
	 * @return 对应的对象
	 */
	@SuppressWarnings("unchecked")
	public T getById(Long id){
		if(id == null || id.longValue() <= 0){
			return null;
		}
		return (T)this.getSqlQuerySession().selectOne(mapper.getName()+".getById", id);
	}
	
	/**
	 * 根据主键删除
	 * 
	 * createTime:下午1:37:24
	 * @author yuzengjia@gintong.com
	 * @param id 主键标识
	 * @return 影响行数
	 */
	public int delById(Long id){
		return this.getSqlSession().delete(mapper.getName()+".delById", id);
	}
	
	/**
	 * 根据编号删除
	 * 
	 * createTime:下午1:37:24
	 * @author yuzengjia@gintong.com
	 * @param id 主键标识
	 * @return 影响行数
	 */
	public int delByNum(String num){
		return this.getSqlSession().delete(mapper.getName()+".delByNum", num);
	}
	
	/**
	 * 更新
	 * 
	 * createTime:下午1:37:35
	 * @author yuzengjia@gintong.com
	 * @param t 封装新数据的对象
	 * @return 影响行数
	 */
	public int update(T t){
		return this.getSqlSession().update(mapper.getName()+".update", t);
	}
    
    /**
     * 分页时总记录数
     * 
     * createTime:下午1:01:38
     * @author yuzengjia@gintong.com
     * @param pageQueryParam
     * @return
     */
    public int getRecordCount(PageQueryParam<T> pageQueryParam) {
    	if(pageQueryParam.getPageEnable()){
    		T queryParam = pageQueryParam.getQueryParam();
    		return (Integer)this.getSqlQuerySession().selectOne(mapper.getName()+".getRecordCount", queryParam);
    	}
    	return 0;
	}
    
    /**
     * 分页记录
     * 
     * createTime:下午1:02:54
     * @author yuzengjia@gintong.com
     * @param pageQueryParam
     * @return
     */
	public List<T> queryListForPage(PageQueryParam<T> pageQueryParam) {
		T queryParam = pageQueryParam.getQueryParam(); 
		if(pageQueryParam.getPageEnable()){
			RowBounds bounds = new RowBounds(pageQueryParam.getStartRow(), pageQueryParam.getPageSize());
			return this.getSqlQuerySession().selectList(mapper.getName()+".queryListForPage", queryParam, bounds);
		}else{
			return this.getSqlQuerySession().selectList(mapper.getName()+".queryListForPage", queryParam);
		}
	}
	
	public List<T> queryListByParam(T queryParam) {
		return this.getSqlQuerySession().selectList(mapper.getName()+".queryListForPage", queryParam);
	}

}