package parsExchangeRate.model;

public class ExchangeRate {
	private String date;
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
	
	public void printExchangeRate() {
		System.out.println("Дата       " + "USD      " + "EUR ");
		System.out.print(date + "   ");
		System.out.print(USD + "    ");
		System.out.println(EUR);
	}
	
}
