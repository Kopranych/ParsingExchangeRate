package parsExchangeRate;

import java.io.IOException;
import java.net.MalformedURLException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import parsExchangeRate.control.CurrencyEngine;
import parsExchangeRate.dbService.DBService;
import parsExchangeRate.dbService.dataSet.ExchangeRateDataSet;
import parsExchangeRate.model.ConstParser;
import parsExchangeRate.model.ExchangeRate;
import parsExchangeRate.parser.Parser;
import parsExchangeRate.view.InterfaceController;

public class MainApp extends Application {

	private Stage primaryStage;
    private BorderPane rootLayout;
    private CurrencyEngine currencyEngine;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("ExchangeRate");

        initRootLayout();

        InterfaceController controller = showFaceApp();
        currencyEngine = new CurrencyEngine(controller);
        Thread myThread = new Thread(currencyEngine);
        myThread.start();
    }
    
    @Override
    public void stop() {
    	currencyEngine.stopCurrencyEngine();
    }

    /**
     * Инициализирует корневой макет.
     */
    public void initRootLayout() {
        try {
            // Загружаем корневой макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Показывает интерфейс в корневом макете 
     */
    public InterfaceController showFaceApp() {
    	InterfaceController controller = null;
        try {
            // Загружаем интерфейс.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Scene.fxml"));
            AnchorPane interfaceApp = (AnchorPane) loader.load();

            // Помещаем интерфейс в центр корневого макета.
            rootLayout.setCenter(interfaceApp);
            
            // Даём контроллеру доступ к главному приложению.
            controller = loader.getController();
            controller.setMainApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return controller;
    }

    public void currencyExchangeRateReceivingEngine(InterfaceController controller) {
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
					controller.showExchangeRate(exchanger);
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
    
    /**
     * Возвращает главную сцену.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }


	public static void main(String[] args) {
		launch(args);
	}
}
