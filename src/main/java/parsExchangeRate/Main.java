package parsExchangeRate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import parsExchangeRate.dbService.DBException;
import parsExchangeRate.dbService.DBService;
import parsExchangeRate.dbService.dataSet.ExchangeRateDataSet;
import parsExchangeRate.model.ConstParser;
import parsExchangeRate.model.ExchangeRate;
import parsExchangeRate.parser.Parser;

public class Main {
	
	
	
	public static void main(String[] args) {
		DBService service = new DBService();
		service.printConnectInfo();
		ExchangeRate exchanger = new ExchangeRate();
		Parser parser = new Parser();
		boolean isRun = true;
		
		
		
		while(isRun) {
			
				try {
					//получаем веб страницу по getUrlAdress
					Document doc = parser.getPage(ConstParser.getUrlAdress());
					Element dateHeader = parser.getElement(doc, ConstParser.getDateHeaderParagraph() );//получаем кусок страницы с датой и временем
					Elements timeElementBold = dateHeader.select(ConstParser.getTimeTegBold());//получаем время по тегу getTimeTegBold
					Element currencysParagraph = parser.getElement(doc, ConstParser.getCurrencyQvery());//получаем общее данные по валютам по тегу getCurrencyQvery
					Elements currencys = currencysParagraph.select(ConstParser.getCurrencyQveryElements());//получаем все элементы содержащие данные о валюте
					//сохраняем полученные данные о валюте
					exchanger.setTime(timeElementBold.text());
					exchanger.setDate(parser.getDateFromString(dateHeader.text()));
					//сохраняем USD и EUR по индексам элементов 
					exchanger.setUSD(parser.getCurrencyFromString(currencys.get(ConstParser.getIndexUSD()).text()));
					exchanger.setEUR(parser.getCurrencyFromString(currencys.get(ConstParser.getIndexEUR()).text()));
					exchanger.printExchangeRate();//выводим данные в консоль для пользователя
					//записываем курс валют в базу данных
					long id = service.addExchangeRate(exchanger.getCurrentDate(),
							exchanger.getDate(), exchanger.getTime(),  exchanger.getUSD(), exchanger.getEUR());
					ExchangeRateDataSet exchangeRatePreview = service.getExchangeRateById(id - 1);
					if(exchangeRatePreview.getUsd()>exchanger.getUSD()) {
						System.out.println("Доллар опустился до " + exchanger.getUSD());
					}else
						if(exchangeRatePreview.getUsd()<exchanger.getUSD()) {
							System.out.println("Доллар поднялся до " + exchanger.getUSD());
						}
						else {
							System.out.println("Доллар не изменился " + exchanger.getUSD());
						}
					List<ExchangeRateDataSet> list = null;
					list = service.getExchangeRateListRange(1L, 5L);
					
					for(ExchangeRateDataSet ds:list) {
//						
						System.out.println(ds.toString());
					}
					
					Thread.sleep(ConstParser.getTimeout());
					
				}catch(MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			
		}
	}

}
