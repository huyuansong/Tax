package cn.itcast.nsfw.complain.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;

import cn.itcast.core.dao.impl.BaseDaoImpl;
import cn.itcast.nsfw.complain.dao.ComplainDao;
import cn.itcast.nsfw.complain.entity.Complain;

public class ComplainDaoImpl extends BaseDaoImpl<Complain> implements ComplainDao {

	@Override
	public void updateStateByStateAndBeforeCompTime(String updateState, String whereState, Date compTime) {
		Query query = getCurrentSession().createQuery("UPDATE Complain SET state=? WHERE state=? AND compTime<?");
		query.setParameter(0, updateState);
		query.setParameter(1, whereState);
		query.setParameter(2, compTime);
		query.executeUpdate();
	}

	@Override
	public List<Object[]> getStatisticDataByYear(int year) {
		String sSQL = "select imonth,count(comp_id) " 
				+ " from tmonth left join complain"
				+ " on imonth=month(comp_time) and year(comp_time)=?"
				+ " group by imonth";
		SQLQuery query = getCurrentSession().createSQLQuery(sSQL);
		query.setParameter(0, year);
		return query.list();
	}

}
