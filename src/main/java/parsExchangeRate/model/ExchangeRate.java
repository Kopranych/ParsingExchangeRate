package parsExchangeRate.model;

public class ExchangeRate {
	private String date;
	private String time;
	private double USD;
	private double EUR;
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getUSD() {
		return USD;
	}
	public void setUSD(double uSD) {
		USD = uSD;
	}
	public double getEUR() {
		return EUR;
	}
	public void setEUR(double eUR) {
		EUR = eUR;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
		
	public void printExchangeRate() {
		System.out.println("Время   Дата       " + "USD      " + "EUR ");
		System.out.print(time + "   ");
		System.out.print(date + "   ");
		System.out.print(USD + "    ");
		System.out.println(EUR);
	}
		
}
