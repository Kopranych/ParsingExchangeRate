package parsExchangeRate.dbService.dataSet;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Date;


@Entity
@Table(name = "exchangerate")
public class ExchangeRateDataSet implements Serializable {

	private static final long serialVersionUID = -6310690968186546660L;
	
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "systemdate", unique = false, updatable = false)
    private Date systemDate;
    
    @Column(name = "time", unique = false, updatable = false)
    private String time;
    
    @Column(name = "date", unique = false, updatable = false)
    private String date;
    
    @Column(name = "usd", unique = false, updatable = false)
    private double usd;
    
    @Column(name = "eur", unique = false, updatable = false)
    private double eur;

    //Important to Hibernate!
    @SuppressWarnings("UnusedDeclaration")
    public ExchangeRateDataSet() {
    }

    
    
    
	@SuppressWarnings("unused")
	public ExchangeRateDataSet(long id, Date systemDate, String date, String time, double usd, double eur) {
		super();
		this.id = id;
		this.systemDate = systemDate;
		this.date = date;
		this.time = time;
		this.usd = usd;
		this.eur = eur;
	}
    

	@SuppressWarnings("unused")
	public ExchangeRateDataSet(Date systemDate, String date, String time,  double usd, double eur) {
		super();
		this.id = -1;
		this.systemDate = systemDate;
		this.date = date;
		this.time = time;
		this.usd = usd;
		this.eur = eur;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Date getSystemDate() {
		return systemDate;
	}

	public void setSystemDate(Date systemDate) {
		this.systemDate = systemDate;
	}

	public double getUsd() {
		return usd;
	}

	public void setUsd(double usd) {
		this.usd = usd;
	}

	public double getEur() {
		return eur;
	}

	public void setEur(double eur) {
		this.eur = eur;
	}

	@Override
    public String toString() {
        return "ExchangeRateDataSet{" +
                "id=" + id +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", usd='" + usd + '\'' +
                ", eur='" + eur + '\'' +
                '}';
    }
	
}
