package parsExchangeRate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedHashSet;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import parsExchangeRate.parser.Parser;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Document doc;
		
		
		Parser parser = new Parser();
		try {
			System.out.println("Дата " + "USD " + "EUR ");
			doc = parser.getPage("https://www.audit-it.ru/currency/");
			Element kursHeader = parser.getElement();
			
			String kursHeaderText = kursHeader.text();
			System.out.println(kursHeader);
			Elements date = kursHeader.select("b");
			System.out.println(date);
			
		}catch(MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
