package parsExchangeRate.telegrammbot.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import parsExchangeRate.control.impl.ControllerExchangeRateTbotImpl;
import parsExchangeRate.model.ExchangeRate;

public class BotService extends TelegramLongPollingBot {
	private ControllerExchangeRateTbotImpl controller;
	private ExchangeRate exchanger;
	private static final String WELCOME_BOT = "Привет! Я покажу тебе текущий курс валют \n " + "нажми на кнопку 'GET' ";

	public BotService(ControllerExchangeRateTbotImpl controller) {
		super();
		this.controller = controller;
	}

	@Override
	public void onUpdateReceived(Update update) {
		Message message = update.getMessage();
		if (message != null & message.hasText())
			switch (message.getText()) {
			case "GET":
				controller.getActualExchangeRate();
				exchanger = controller.getCurrencyEngine().getExchanger();
				String messageTranslator = "Date " + LocalDate.now() + "\n" + "USD " + exchanger.getUSD() + " EUR " + exchanger.getEUR();
				sendMsg(message.getChatId().toString(), messageTranslator);
				break;
			case "/start":
				sendMsg(message.getChatId().toString(), WELCOME_BOT);
			}

	}

	/**
	 * Метод для настройки сообщения и его отправки.
	 * 
	 * @param chatId id чата
	 * @param s      Строка, которую необходимот отправить в качестве сообщения.
	 */
	public synchronized void sendMsg(String chatId, String s) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.enableMarkdown(true);
		sendMessage.setChatId(chatId);
		sendMessage.setText(s);
		try {
			setButtons(sendMessage);
			sendMessage(sendMessage);
		} catch (TelegramApiException e) {
//            log.log(Level.SEVERE, "Exception: ", e.toString());
			e.printStackTrace();
		}
	}

	@Override
	public String getBotUsername() {
		// TODO Auto-generated method stub
		return "Kopranych_exchangerBot";
	}

	@Override
	public String getBotToken() {
		// TODO Auto-generated method stub
		return "630036571:AAFA-XAls7pZNhX-B1IBxb8IREOJhOT8V7Y";
	}

	public synchronized void setButtons(SendMessage sendMessage) {
		// Создаем клавиуатуру
		ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
		sendMessage.setReplyMarkup(replyKeyboardMarkup);
		replyKeyboardMarkup.setSelective(true);
		replyKeyboardMarkup.setResizeKeyboard(true);
		replyKeyboardMarkup.setOneTimeKeyboard(false);

		// Создаем список строк клавиатуры
		List<KeyboardRow> keyboard = new ArrayList<>();

		// Первая строчка клавиатуры
		KeyboardRow keyboardFirstRow = new KeyboardRow();
		// Добавляем кнопки в первую строчку клавиатуры
		keyboardFirstRow.add(new KeyboardButton("GET"));
		keyboardFirstRow.add(new KeyboardButton("Help"));

//		// Вторая строчка клавиатуры
//		KeyboardRow keyboardSecondRow = new KeyboardRow();
//		// Добавляем кнопки во вторую строчку клавиатуры
//		keyboardSecondRow.add(new KeyboardButton("Помощь"));

		// Добавляем все строчки клавиатуры в список
		keyboard.add(keyboardFirstRow);
//		keyboard.add(keyboardSecondRow);
		// и устанваливаем этот список нашей клавиатуре
		replyKeyboardMarkup.setKeyboard(keyboard);
	}

}
