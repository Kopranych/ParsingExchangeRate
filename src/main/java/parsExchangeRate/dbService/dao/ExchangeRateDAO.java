package parsExchangeRate.dbService.dao;

import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;


import parsExchangeRate.dbService.dataSet.ExchangeRateDataSet;

/**
* Класс DAO предоставляет методы для работы с базой
* @author i.kopranov
* @date 30.04.2018
*/

public class ExchangeRateDAO {
	private Session session;

	/**
     * Конструктор для создания нового объекта DAO
     * @param Принеимает Session session позволяющую делать запросы к базе
     */
    public ExchangeRateDAO(Session session) {
        this.session = session;
    }

    /**
     * Запрашивает строку из таблицы базы по id
     *
     * @param Принеимает id нужной строки
     * @throws HibernateException
     * @return возвращает строку из таблицы в базе в виде обьекта ExchangeRateDataSet
     */
    public ExchangeRateDataSet get(long id) throws HibernateException {
        return (ExchangeRateDataSet) session.get(ExchangeRateDataSet.class, id);
    }
    
    /**
     * Запрашивает весь список таблицы
     *
     * @return возвращает весь список таблицы в виде Collection<ExchangeRateDataSet>
     */
    public Collection<ExchangeRateDataSet> getList(){
    	Criteria criteria = session.createCriteria(ExchangeRateDataSet.class);
    	return (Collection<ExchangeRateDataSet>)criteria.list();
    }
    
    /**
     * Запрашивает список из таблицы в диапозоне между firstId и secondId
     *
     * @param Принеимает firstId, secondId 
     * @return возвращает список из диапозона между firstId и secondId таблицы в виде Collection<ExchangeRateDataSet>
     */
    @SuppressWarnings("unchecked")
	public Collection<ExchangeRateDataSet> getRangeId(long firstId, long secondId){
    	Criteria criteria = session.createCriteria(ExchangeRateDataSet.class);
    	return (Collection<ExchangeRateDataSet>)criteria.add(Restrictions.between("id", firstId, secondId)).list();
    }

    /**
     * Запрашивает данные по конкретному значению usd
     *
     * @param Принеимает usd
     * @throws HibernateException
     * @return возвращает строку из таблицы в базе в виде обьекта ExchangeRateDataSet
     */
    public long getExchangeRateId(String usd) throws HibernateException {
        Criteria criteria = session.createCriteria(ExchangeRateDataSet.class);
        return ((ExchangeRateDataSet) criteria.add(Restrictions.eq("usd", usd)).uniqueResult()).getId();
    }

    /**
     * Вставляет данные в таблицу
     *
     * @param Принеимает systemDate - время и дата на компьютере, time - время полученное с веб сайта, 
     * date - дата полученная с веб сайта, usd, eur
     * @throws HibernateException
     * @return возвращает id строки вставленных данных в таблице в виде Long
     */
    public long inserExchangeRate(Date systemDate,String time, String date, double usd, double eur) throws HibernateException {
        return (Long) session.save(new ExchangeRateDataSet(systemDate, time, date, usd, eur));
    }

}
