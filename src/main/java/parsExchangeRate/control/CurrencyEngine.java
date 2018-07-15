package parsExchangeRate.control;

import java.io.IOException;
import java.net.MalformedURLException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import parsExchangeRate.dbService.DBService;
import parsExchangeRate.dbService.dataSet.ExchangeRateDataSet;
import parsExchangeRate.model.ConstParser;
import parsExchangeRate.model.ExchangeRate;
import parsExchangeRate.parser.Parser;
import parsExchangeRate.resources.InterfaceController;

public class CurrencyEngine implements Runnable {

	private DBService service = new DBService();
	private ExchangeRate exchanger = new ExchangeRate();
	private Parser parser = new Parser();
	private boolean isRun = true;
	private InterfaceController controller;

	public DBService getService() {
		return service;
	}

	public ExchangeRate getExchanger() {
		return exchanger;
	}

	public Parser getParser() {
		return parser;
	}

	public void stopCurrencyEngine() {
		this.isRun = false;
	}

	public CurrencyEngine(InterfaceController controller) {
		super();
		this.controller = controller;
	}

	public CurrencyEngine() {
		super();
	}

	@Override
	public void run() {
		service.printConnectInfo();

		while (isRun) {

			try {
				ConstParser.setMaxValueEur(service.getMaxMinValueEurUsd(ConstParser.getIndexEUR()));
				ConstParser.setMinValueUsd(service.getMaxMinValueEurUsd(ConstParser.getIndexUSD()));
				// получаем веб страницу по getUrlAdress
				Document doc = parser.getPage(ConstParser.getUrlAdress());
				Element dateHeader = parser.getElement(doc, ConstParser.getDateHeaderParagraph());// получаем кусок страницы с датой и временем
				Elements timeElementBold = dateHeader.select(ConstParser.getTimeTegBold());// получаем время по тегу getTimeTegBold
				Element currencysParagraph = parser.getElement(doc, ConstParser.getCurrencyQvery());// получаем общее данные по валютам по тегу getCurrencyQvery
				Elements currencys = currencysParagraph.select(ConstParser.getCurrencyQveryElements());// получаем все элементы содержащие данные о валюте
				// сохраняем полученные данные о валюте
				exchanger.setTime(timeElementBold.text());
				exchanger.setDate(parser.getDateFromString(dateHeader.text()));
				// сохраняем USD и EUR по индексам элементов
				exchanger.setUSD(parser.getCurrencyFromString(currencys.get(ConstParser.getIndexUSD()).text()));
				exchanger.setEUR(parser.getCurrencyFromString(currencys.get(ConstParser.getIndexEUR()).text()));
				exchanger.printExchangeRate();// выводим данные в консоль для пользователя
				// записываем курс валют в базу данных
				long id = service.addExchangeRate(exchanger.getCurrentDate(), exchanger.getDate(), exchanger.getTime(), exchanger.getUSD(), exchanger.getEUR());
				ExchangeRateDataSet exchangeRatePreview = service.getExchangeRateById(id - 1);
				ConstParser.setIdLastItemAdded(id);

				if (exchangeRatePreview.getUsd() > exchanger.getUSD()) {
					System.out.println("Доллар опустился до " + exchanger.getUSD());
				} else if (exchangeRatePreview.getUsd() < exchanger.getUSD()) {
					System.out.println("Доллар поднялся до " + exchanger.getUSD());
				} else {
					System.out.println("Доллар не изменился " + exchanger.getUSD());
				}
				controller.showExchangeRate(exchanger);
				Thread.sleep(ConstParser.getTimeout());

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			} catch (Exception e) {

				e.printStackTrace();
			}

		}
	}

}
