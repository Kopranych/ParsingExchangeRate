package parsExchangeRate.dbService.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;


import parsExchangeRate.dbService.dataSet.ExchangeRateDataSet;

public class ExchangeRateDAO {
	private Session session;

    public ExchangeRateDAO(Session session) {
        this.session = session;
    }

    public ExchangeRateDataSet get(long id) throws HibernateException {
        return (ExchangeRateDataSet) session.get(ExchangeRateDataSet.class, id);
    }
    
    public List<ExchangeRateDataSet> getList(){
    	return (List<ExchangeRateDataSet>)session.createCriteria(ExchangeRateDataSet.class).list();
    }

    public long getExchangeRateId(String usd) throws HibernateException {
        Criteria criteria = session.createCriteria(ExchangeRateDataSet.class);
        return ((ExchangeRateDataSet) criteria.add(Restrictions.eq("usd", usd)).uniqueResult()).getId();
    }

    public long inserExchangeRate(Date systemDate,String time, String date, double usd, double eur) throws HibernateException {
        return (Long) session.save(new ExchangeRateDataSet(systemDate, time, date, usd, eur));
    }
//    
//    public List<ExchangeRateDataSet> getExchengeRateAll(){
//    	
//    }
    
}
