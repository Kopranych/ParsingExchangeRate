package parsExchangeRate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import parsExchangeRate.dbService.DBException;
import parsExchangeRate.dbService.DBService;
import parsExchangeRate.dbService.dataSet.ExchangeRateDataSet;
import parsExchangeRate.model.ConstParser;
import parsExchangeRate.model.ExchangeRate;
import parsExchangeRate.parser.Parser;

public class Main {
	
	
	
	public static void main(String[] args) {
//		DBService service = new DBService();
//		service.printConnectInfo();
//		ExchangeRate exchanger = new ExchangeRate();
//		Parser parser = new Parser();
//		boolean isRun = true;
		
		List<Number> num;
		List<Integer> in = Arrays.asList(2,3,4);
		
		System.out.println(isPowerOfTwo(0));

            boolean ffff = booleanExpression(false, false, false, false);
            boolean ffft = booleanExpression(false, false, false, true);
            boolean fftf = booleanExpression(false, false, true, false);
            boolean fftt  = booleanExpression(false, false, true, true); //Searching for this

            boolean ftff = booleanExpression(false, true, false, false);
            boolean ftft = booleanExpression(false, true, false, true); //Searching for this
            boolean fttf = booleanExpression(false, true, true, false); //Searching for this
            boolean fttt = booleanExpression(false, true, true, true);

            boolean tfff = booleanExpression(true, false, false, false);
            boolean tfft = booleanExpression(true, false, false, true); //Searching for this
            boolean tftf = booleanExpression(true, false, true, false); //Searching for this
            boolean tftt = booleanExpression(true, false, true, true);

            boolean ttff = booleanExpression(true, true, false, false); //Searching for this
            boolean ttft = booleanExpression(true, true, false, true);
            boolean tttf = booleanExpression(true, true, true, false);
            boolean tttt = booleanExpression(true, true, true, true);

            System.out.println("ffff " + ffff);
            System.out.println("ffft " + ffft);
            System.out.println("fftf " + fftf);
            System.out.println("fftt " + fftt + " <- ");

            System.out.println("ftff " + ftff);
            System.out.println("ftft " + ftft + " <- ");
            System.out.println("fttf " + fttf + " <- ");
            System.out.println("fttt " + fttt);

            System.out.println("tfff " + tfff);
            System.out.println("tfft " + tfft + " <- ");
            System.out.println("tftf " + tftf + " <- ");
            System.out.println("tftt " + tftt);

            System.out.println("ttff " + ttff + " <- ");
            System.out.println("ttft " + ttft);
            System.out.println("tttf " + tttf);
            System.out.println("tttt " + tttt);


            boolean allCorrectAreTrue = fftt & ftft & fttf & tfft & tftf & ttff;

            boolean allIncorrectAreTrue = ffff | ffft | fftf | ftff | fttt |  tfff | tftt | ttft | tttf | tttt;

            boolean correctAnswerCondition = allCorrectAreTrue & !allIncorrectAreTrue;

            if (correctAnswerCondition) {
                System.out.println(" You have found the correct answer!!! :) ");
            } else {
                System.out.println(" Correct answer still to be found... :( ");
            }


        }

        @Test
        public static boolean booleanExpression(boolean a, boolean b, boolean c, boolean d) {
            return ( (!a) & (!b) & c & d ) ^  ( a & b & (!c) & (!d) ) ^((!a) & b & (!c) & d)^((!a)& b &c&(!d))^(a & (!b)& (!c)& d)^(a&(!b)&c&(!d));
        }
    
        public static boolean isPowerOfTwo(int value) {
            int temp = Math.abs(value);
            boolean isPowerOfTwo = false;
            if(temp == 1) {
            	isPowerOfTwo = true;
            }else if(temp == 0){
            	isPowerOfTwo = false;
            }else {
            	while(temp != 2){
            		temp = temp/2;
            		if(temp%2!=0)
            		{
            			isPowerOfTwo = false;
            			break;
            		}
            	}
            	isPowerOfTwo = true;
            	
            }     
            return isPowerOfTwo; // you implementation here
        }
        
        
		
//		while(isRun) {
//			
//				try {
//					//получаем веб страницу по getUrlAdress
//					Document doc = parser.getPage(ConstParser.getUrlAdress());
//					Element dateHeader = parser.getElement(doc, ConstParser.getDateHeaderParagraph() );//получаем кусок страницы с датой и временем
//					Elements timeElementBold = dateHeader.select(ConstParser.getTimeTegBold());//получаем время по тегу getTimeTegBold
//					Element currencysParagraph = parser.getElement(doc, ConstParser.getCurrencyQvery());//получаем общее данные по валютам по тегу getCurrencyQvery
//					Elements currencys = currencysParagraph.select(ConstParser.getCurrencyQveryElements());//получаем все элементы содержащие данные о валюте
//					//сохраняем полученные данные о валюте
//					exchanger.setTime(timeElementBold.text());
//					exchanger.setDate(parser.getDateFromString(dateHeader.text()));
//					//сохраняем USD и EUR по индексам элементов 
//					exchanger.setUSD(parser.getCurrencyFromString(currencys.get(ConstParser.getIndexUSD()).text()));
//					exchanger.setEUR(parser.getCurrencyFromString(currencys.get(ConstParser.getIndexEUR()).text()));
//					exchanger.printExchangeRate();//выводим данные в консоль для пользователя
//					//записываем курс валют в базу данных
//					long id = service.addExchangeRate(exchanger.getCurrentDate(),
//							exchanger.getDate(), exchanger.getTime(),  exchanger.getUSD(), exchanger.getEUR());
//					ExchangeRateDataSet exchangeRatePreview = service.getExchangeRateById(id - 1);
//					if(exchangeRatePreview.getUsd()>exchanger.getUSD()) {
//						System.out.println("Доллар опустился до " + exchanger.getUSD());
//					}else
//						if(exchangeRatePreview.getUsd()<exchanger.getUSD()) {
//							System.out.println("Доллар поднялся до " + exchanger.getUSD());
//						}
//						else {
//							System.out.println("Доллар не изменился " + exchanger.getUSD());
//						}
//					List<ExchangeRateDataSet> list = null;
//					list = service.getExchangeRateListRange(1L, 5L);
//					
//					for(ExchangeRateDataSet ds:list) {
////						
//						System.out.println(ds.toString());
//					}
//					
//					Thread.sleep(ConstParser.getTimeout());
//					
//				}catch(MalformedURLException e) {
//					e.printStackTrace();
//				} catch (IOException e) {
//					
//					e.printStackTrace();
//				} catch (Exception e) {
//					
//					e.printStackTrace();
//				}
//			
//		}
	

}
