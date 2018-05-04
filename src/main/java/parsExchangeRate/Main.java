package parsExchangeRate;

import java.io.IOException;
import java.net.MalformedURLException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import parsExchangeRate.model.ConstParser;
import parsExchangeRate.model.ExchangeRate;
import parsExchangeRate.parser.Parser;

public class Main {
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExchangeRate exchanger = new ExchangeRate();
		
		
		Parser parser = new Parser();
		try {
			
			Document doc = parser.getPage(ConstParser.getUrlAdress());
			Element dateHeader = parser.getElement(doc, ConstParser.getDateHeaderParagraph() );
			Elements timeElementBold = dateHeader.select(ConstParser.getTimeTegBold());
			Element currencysParagraph = parser.getElement(doc, ConstParser.getCurrencyQvery());
			Elements currencys = currencysParagraph.select(ConstParser.getCurrencyQveryElements());
			
			exchanger.setTime(timeElementBold.text());
			exchanger.setDate(parser.getDateFromString(dateHeader.text()));
			exchanger.setUSD(parser.getCurrencyFromString(currencys.get(ConstParser.getIndexUSD()).text()));
			exchanger.setEUR(parser.getCurrencyFromString(currencys.get(ConstParser.getIndexEUR()).text()));
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
