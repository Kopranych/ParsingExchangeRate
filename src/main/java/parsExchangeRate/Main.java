package parsExchangeRate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedHashSet;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import parsExchangeRate.model.ExchangeRate;
import parsExchangeRate.parser.Parser;

public class Main {
	
	private static final String urlAdress = "https://www.audit-it.ru/currency/";
	private static final String dateHeaderParagraph = "p";
	private static final String currencyQvery = "div[class=tek-moment]";
	private static final String currencyQveryElements = "div[class=rate]";
	private static final int indexUSD = 0;
	private static final int indexEUR = 1;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExchangeRate exchanger = new ExchangeRate();
		
		Parser parser = new Parser();
		try {
			
			Document doc = parser.getPage(urlAdress);
			Element dateHeader = parser.getElement(doc, dateHeaderParagraph );
			Element currencysParagraph = parser.getElement(doc, currencyQvery);
			Elements currencys = currencysParagraph.select(currencyQveryElements);
			
			exchanger.setDate(parser.getDateFromString(dateHeader.text()));
			exchanger.setUSD(parser.getCurrencyFromString(currencys.get(indexUSD).text()));
			exchanger.setEUR(parser.getCurrencyFromString(currencys.get(indexEUR).text()));
			exchanger.printExchangeRate();
			
			
			
		}catch(MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
