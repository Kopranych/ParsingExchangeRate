package parsExchangeRate.model;


import java.util.Date;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

public class ExchangeRate {
	private ObjectProperty<Date> currentDate;
	private StringProperty date;
	private StringProperty time;
	private DoubleProperty USD;
	private DoubleProperty EUR;
	
	
	public String getDate() {
		return date.get();
	}
	
	public void setDate(String date) {
		this.date.set(date);
	}
	
	public Date getCurrentDate() {
		return new Date();
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate.set(currentDate);
	}

	public double getUSD() {
		return USD.get();
	}
	
	public void setUSD(double uSD) {
		USD.set(uSD);
	}
	
	public double getEUR() {
		return EUR.get();
	}
	
	public void setEUR(double eUR) {
		EUR.set(eUR);
	}
	
	public String getTime() {
		return time.get();
	}
	
	public void setTime(String time) {
		this.time.set(time);
	}
		
	public void printExchangeRate() {
		System.out.println("Текущее время " + getCurrentDate());
		System.out.println("Время   Дата       " + "USD      " + "EUR ");
		System.out.print(time.get()+ "   ");
		System.out.print(date.get() + "   ");
		System.out.print(USD.get() + "    ");
		System.out.println(EUR.get());
	}
		
}
