package parsExchangeRate.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Класс парсит страницу с помощью фреймворка Jsoup
* @author i.kopranov
* @date 30.04.2018
*/

public class Parser {
	private Document page;
	private static final int TIME_WAIT = 8000;
	private Element element;
	private static final String regulExpression = "\\d{2}\\.\\d{2}\\.\\d{2}";
	private Pattern timePattern = Pattern.compile(regulExpression);
	
	/**
     * Конструктор для создания нового объекта Parser
     */
	public Parser() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
     * Метод который запрашивает необходимую страницу по адресу в интернете
     *
     * @param Принеимает строку с адресом страницы которую необходимо получить
     * @throws MalformedURLException, IOException
     * @return возвращает нужную страницу в виде Document page 
     */
	public Document getPage(String url) throws MalformedURLException, IOException {		
		return page = Jsoup.parse(new URL(url),TIME_WAIT);
	}
	
	
	public Element getElement() {
		if(!page.equals(null)) {
			return element = page.select("p").first();
		}
		return null;
	}
	
	public String getDate(String stringDate) {
		Matcher matcher = timePattern.matcher(stringDate);
		if(matcher.find()) {//если нашел 
			return matcher.group();
		}
		return null;
	}

	
}
