package parsExchangeRate.control.impl;

import java.io.IOException;
import java.net.MalformedURLException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import parsExchangeRate.control.ControllerExchangeRate;
import parsExchangeRate.control.CurrencyEngine;
import parsExchangeRate.model.ConstParser;
import parsExchangeRate.model.ExchangeRate;

public class ControllerExchangeRateTbotImpl implements ControllerExchangeRate {

	private CurrencyEngine currencyEngine;

	public void setCurrencyEngine(CurrencyEngine engine) {
		this.currencyEngine = engine;
	}

	public CurrencyEngine getCurrencyEngine() {
		return this.currencyEngine;
	}

	public ControllerExchangeRateTbotImpl(CurrencyEngine currencyEngine) {
		super();
		this.currencyEngine = currencyEngine;
	}

	@Override
	public void getActualExchangeRate() {
		try {
			// получаем веб страницу по getUrlAdress
			Document doc = currencyEngine.getParser().getPage(ConstParser.getUrlAdress());
			Element dateHeader = currencyEngine.getParser().getElement(doc, ConstParser.getDateHeaderParagraph());// получаем кусок страницы с датой и временем
			Elements timeElementBold = dateHeader.select(ConstParser.getTimeTegBold());// получаем время по тегу getTimeTegBold
			Element currencysParagraph = currencyEngine.getParser().getElement(doc, ConstParser.getCurrencyQvery());// получаем общее данные по валютам по тегу getCurrencyQvery
			Elements currencys = currencysParagraph.select(ConstParser.getCurrencyQveryElements());// получаем все элементы содержащие данные о валюте
			// сохраняем полученные данные о валюте
			currencyEngine.getExchanger().setTime(timeElementBold.text());
			currencyEngine.getExchanger().setDate(currencyEngine.getParser().getDateFromString(dateHeader.text()));
			// сохраняем USD и EUR по индексам элементов
			currencyEngine.getExchanger().setUSD(currencyEngine.getParser().getCurrencyFromString(currencys.get(ConstParser.getIndexUSD()).text()));
			currencyEngine.getExchanger().setEUR(currencyEngine.getParser().getCurrencyFromString(currencys.get(ConstParser.getIndexEUR()).text()));
			// записываем курс валют в базу данных
			long id = currencyEngine.getService().addExchangeRate(currencyEngine.getExchanger().getCurrentDate(), currencyEngine.getExchanger().getDate(), currencyEngine.getExchanger().getTime(), currencyEngine.getExchanger().getUSD(),
					currencyEngine.getExchanger().getEUR());
			ConstParser.setIdLastItemAdded(id);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Override
	public void showExchangeRate(ExchangeRate exchanger) {
		// TODO Auto-generated method stub

	}

}
