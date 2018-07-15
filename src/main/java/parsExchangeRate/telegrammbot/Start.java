package parsExchangeRate.telegrammbot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import parsExchangeRate.control.CurrencyEngine;
import parsExchangeRate.control.impl.ControllerExchangeRateTbotImpl;
import parsExchangeRate.telegrammbot.service.BotService;

public class Start {

	public static void main(String[] args) {
		ApiContextInitializer.init();
		CurrencyEngine currencyEngine = new CurrencyEngine();
		ControllerExchangeRateTbotImpl controller = new ControllerExchangeRateTbotImpl(currencyEngine);
//		TelegramLongPollingBot bot = new BotService();
//
//		String proxyHost = "138.201.6.102";
//		int proxyPort = 1080;
//		int timeout = 75 * 1000;
//
//		RequestConfig requestConfig = RequestConfig.custom().setProxy(new HttpHost(proxyHost, proxyPort)).setSocketTimeout(timeout).setConnectionRequestTimeout(timeout).setConnectTimeout(timeout).build();
//		bot.getOptions().setRequestConfig(requestConfig);

		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

		try {
			telegramBotsApi.registerBot(new BotService(controller));
		} catch (TelegramApiRequestException e) {
			e.printStackTrace();
		}
	}

}
