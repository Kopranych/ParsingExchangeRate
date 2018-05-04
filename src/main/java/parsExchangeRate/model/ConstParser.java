package parsExchangeRate.model;

public class ConstParser {
	
	private	static ConstParser instance;
	
	
	private static final String urlAdress = "https://www.audit-it.ru/currency/";
	private static final String dateHeaderParagraph = "p";
	private static final String currencyQvery = "div[class=tek-moment]";
	private static final String currencyQveryElements = "div[class=rate]";
	private static final String timeTegBold = "b";
	private static final int indexUSD = 0;
	private static final int indexEUR = 1;
	
	
	public static String getUrlAdress() {
		return urlAdress;
	}

	public static String getDateHeaderParagraph() {
		return dateHeaderParagraph;
	}

	public static String getCurrencyQvery() {
		return currencyQvery;
	}

	public static String getCurrencyQveryElements() {
		return currencyQveryElements;
	}

	public static String getTimeTegBold() {
		return timeTegBold;
	}

	public static int getIndexUSD() {
		return indexUSD;
	}

	public static int getIndexEUR() {
		return indexEUR;
	}

	private ConstParser() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static synchronized ConstParser getInstance() {
		if(instance == null) {
			instance = new ConstParser();
		}
		return instance;
	}
	
}
