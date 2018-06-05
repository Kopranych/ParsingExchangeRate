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
        controller.setCurrencyEngene(currencyEngine);
        Thread myThread = new Thread(currencyEngine);
        myThread.start();
    }
    
    @Override
    public void stop() {
    	currencyEngine.notify();
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
