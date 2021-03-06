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
	private static final String regulExpressionForDate = "\\d{2}\\.\\d{2}\\.\\d{2}";
	private static final String regulExpressionForCurrency = "\\d{2}\\.\\d{2}";
	
	
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
     * TIME_WAIT - время ожидания ответа от страницы
     * @throws MalformedURLException, IOException
     * @return возвращает нужную страницу в виде Document page 
     */
	public Document getPage(String url) throws MalformedURLException, IOException {		
		return page = Jsoup.parse(new URL(url),TIME_WAIT);
	}
	
	/**
     * разбирает страницу по нужному тегу запрашивает необходимую страницу по адресу в интернете
     *
     * @param Принеимает страницу page, и qvery - тег по которому разбирать страницу
     * @throws Exception
     * @return возвращает нужный кусок сраницы в виде Element element
     */
	public Element getElement(Document page, String qvery) throws Exception {
		if(!page.equals(null)) {
			return element = page.select(qvery).first();
		}
		throw new Exception("Нет элементов");
	}
	
	/**
     * разбирает строку содержащую дату через регулярное выражение regulExpressionForDate
     *
     * @param Принеимает строку String stringDate
     * @throws Exception
     * @return возвращает строку String с датой
     */
	public String getDateFromString(String stringDate) throws Exception {
		Pattern timePattern = Pattern.compile(regulExpressionForDate);
		Matcher matcher = timePattern.matcher(stringDate);
		if(matcher.find()) {//если нашел 
			return matcher.group();
		}
		throw new Exception("Нет данных");
	}
	
	/**
     * разбирает строку содержащую данные о валюте через регулярное выражение regulExpressionForCurrency
     *
     * @param Принеимает строку String stringCurrency
     * @throws Exception
     * @return возвращает строку String с данными валюты
     */
	public double getCurrencyFromString(String stringCurrency) throws Exception {
		Pattern timePattern = Pattern.compile(regulExpressionForCurrency);
		Matcher matcher = timePattern.matcher(stringCurrency);
		if(matcher.find()) {//если нашел 
			return Double.parseDouble(matcher.group());
		}
		throw new Exception("Нет данных");
	}

	
}
