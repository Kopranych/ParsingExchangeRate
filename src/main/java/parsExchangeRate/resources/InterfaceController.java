package parsExchangeRate.resources;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import parsExchangeRate.MainApp;
import parsExchangeRate.control.CurrencyEngine;
import parsExchangeRate.dbService.DBException;
import parsExchangeRate.dbService.DBService;
import parsExchangeRate.dbService.dataSet.ExchangeRateDataSet;
import parsExchangeRate.model.ConstParser;
import parsExchangeRate.model.ExchangeRate;

public class InterfaceController {

    @FXML
    private Label USD;
    @FXML
    private Label EUR;
    @FXML
    private Label date;
    @FXML
    private TextArea usdArea;
    @FXML
    private TextArea eurArea;
    @FXML
    private TextArea dateArea;
   
    // Ссылка на главное приложение.
    private MainApp mainApp;
    private CurrencyEngine currencyEngine;
    

    /**
     * Конструктор.
     * Конструктор вызывается раньше метода initialize().
     */
    public InterfaceController() {
    }

    /**
     * Инициализация класса-контроллера. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {

    }

    /**
     * Вызывается главным приложением, которое даёт на себя ссылку.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    public void setCurrencyEngene(CurrencyEngine currencyEngine) {
    	this.currencyEngine = currencyEngine;
    }
    
    public void showExchangeRate(ExchangeRate exchanger) {
    	usdArea.setText(Double.toString(exchanger.getUSD()));
    	eurArea.setText(Double.toString(exchanger.getEUR()));
    	dateArea.setText(exchanger.getCurrentDate().toString());
    }
    
    @FXML
    private void getActualExchangeRate() {
    	
    	try {
			//получаем веб страницу по getUrlAdress
			Document doc = currencyEngine.getParser().getPage(ConstParser.getUrlAdress());
			Element dateHeader = currencyEngine.getParser().getElement(doc, ConstParser.getDateHeaderParagraph() );//получаем кусок страницы с датой и временем
			Elements timeElementBold = dateHeader.select(ConstParser.getTimeTegBold());//получаем время по тегу getTimeTegBold
			Element currencysParagraph = currencyEngine.getParser().getElement(doc, ConstParser.getCurrencyQvery());//получаем общее данные по валютам по тегу getCurrencyQvery
			Elements currencys = currencysParagraph.select(ConstParser.getCurrencyQveryElements());//получаем все элементы содержащие данные о валюте
			//сохраняем полученные данные о валюте
			currencyEngine.getExchanger().setTime(timeElementBold.text());
			currencyEngine.getExchanger().setDate(currencyEngine.getParser().getDateFromString(dateHeader.text()));
			//сохраняем USD и EUR по индексам элементов 
			currencyEngine.getExchanger().setUSD(currencyEngine.getParser().getCurrencyFromString(currencys.get(ConstParser.getIndexUSD()).text()));
			currencyEngine.getExchanger().setEUR(currencyEngine.getParser().getCurrencyFromString(currencys.get(ConstParser.getIndexEUR()).text()));
			currencyEngine.getExchanger().printExchangeRate();//выводим данные в консоль для пользователя
			//записываем курс валют в базу данных
			long id = currencyEngine.getService().addExchangeRate(currencyEngine.getExchanger().getCurrentDate(),
					currencyEngine.getExchanger().getDate(), currencyEngine.getExchanger().getTime(),  currencyEngine.getExchanger().getUSD(), currencyEngine.getExchanger().getEUR());
			ConstParser.setIdLastItemAdded(id);
			ExchangeRateDataSet exchangeRatePreview = currencyEngine.getService().getExchangeRateById(id - 1);
			
			if(exchangeRatePreview.getUsd()>currencyEngine.getExchanger().getUSD()) {
				System.out.println("Доллар опустился до " + currencyEngine.getExchanger().getUSD());
			}else
				if(exchangeRatePreview.getUsd()<currencyEngine.getExchanger().getUSD()) {
					System.out.println("Доллар поднялся до " + currencyEngine.getExchanger().getUSD());
				}
				else {
					System.out.println("Доллар не изменился " + currencyEngine.getExchanger().getUSD());
				}
			
			showExchangeRate(currencyEngine.getExchanger());
			
			
		}catch(MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
    }
    
    
    
    @FXML
    private void getChart() {
    	try {
    		DBService service = currencyEngine.getService();
    		long tempId = ConstParser.getIdLastItemAdded();
    		ArrayList<ExchangeRateDataSet> list = 
    				(ArrayList<ExchangeRateDataSet>) service
    				.getExchangeRateListRange(tempId - 90, tempId - 55 );
    			
    		System.out.println(service.getMaxMinValueEurUsd(ConstParser.getIndexEUR()).toString());
    		System.out.println(service.getMaxMinValueEurUsd(ConstParser.getIndexUSD()).toString());
    			Iterator itr = list.iterator();
//    	    	while(itr.hasNext()) {
//    	    		ExchangeRateDataSet tempDataSet = (ExchangeRateDataSet) itr.next();
//    	    		System.out.println(tempDataSet.toString());
//    	    	}
       		mainApp.showCharts(list);
    	} catch (DBException e) {
			
			e.printStackTrace();
		}
    	
    	
    	
    }
}
