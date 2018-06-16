package parsExchangeRate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import parsExchangeRate.control.CurrencyEngine;
import parsExchangeRate.dbService.DBService;
import parsExchangeRate.dbService.dataSet.ExchangeRateDataSet;
import parsExchangeRate.model.ConstParser;
import parsExchangeRate.model.ExchangeRate;
import parsExchangeRate.parser.Parser;
import parsExchangeRate.resources.InterfaceController;

public class MainApp extends Application {

	private Stage primaryStage;
    private BorderPane rootLayout;
    private CurrencyEngine currencyEngine;
    private Thread threadEngine;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("ExchangeRate");

        initRootLayout();

        InterfaceController controller = showFaceApp();
        currencyEngine = new CurrencyEngine(controller);
        controller.setCurrencyEngene(currencyEngine);
        threadEngine = new Thread(currencyEngine);
        threadEngine.start();
    }
    
    @Override
    public void stop() {
    	
    	currencyEngine.stopCurrencyEngine();
    	threadEngine.notifyAll();
    }

    /**
     * Инициализирует корневой макет.
     */
    public void initRootLayout() {
        try {
            // Загружаем корневой макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("resources/RootLayout.fxml"));
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
            loader.setLocation(MainApp.class.getResource("resources/Scene.fxml"));
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

    public void showCharts(List<ExchangeRateDataSet> list) {
    	
//    	try {
//    		FXMLLoader loader = new FXMLLoader();
//        	loader.setLocation(MainApp.class.getResource("resources/DialogChart.fxml"));
//			AnchorPane page = (AnchorPane)loader.load();
			
			// Создаём диалоговое окно Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Chart");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);

	        
	        NumberAxis x = new NumberAxis();
	        NumberAxis y = new NumberAxis();
	        //область для линейного графика(полотно)
	        LineChart<Number, Number> numberLineChart = new LineChart<Number, Number>(x,y);
	        numberLineChart.setTitle("ExchangeRate");
	        //серия данных будет хранится тут для отображения несколькиз графиков на одном полотне
	        XYChart.Series seriesUSD = new XYChart.Series();
	        XYChart.Series seriesEUR = new XYChart.Series();
	        seriesEUR.setName("EUR");
	        seriesUSD.setName("USD");
	        //список данных для построения графика
	        ObservableList<XYChart.Data> dataUSD = FXCollections.observableArrayList();
	        ObservableList<XYChart.Data> dataEUR = FXCollections.observableArrayList();
	        
	        for(ExchangeRateDataSet dataSet:list){
	        	dataUSD.add(new XYChart.Data(dataSet.getId(),dataSet.getUsd()));
	        	dataEUR.add(new XYChart.Data(dataSet.getId(),dataSet.getEur()));
	        }

	        seriesUSD.setData(dataUSD);
	        seriesEUR.setData(dataEUR);

	        Scene scene = new Scene(numberLineChart, 600,600);
	        numberLineChart.getData().add(seriesUSD);
	        numberLineChart.getData().add(seriesEUR);
	        dialogStage.setScene(scene);

	        
	     // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
	        dialogStage.showAndWait();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
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
