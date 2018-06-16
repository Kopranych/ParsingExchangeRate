package parsExchangeRate;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.nodes.Document;
import org.junit.Test;

import parsExchangeRate.control.CurrencyEngine;
import parsExchangeRate.dbService.DBException;
import parsExchangeRate.dbService.DBService;
import parsExchangeRate.dbService.dataSet.ExchangeRateDataSet;
import parsExchangeRate.model.ConstParser;
import parsExchangeRate.parser.Parser;
import parsExchangeRate.resources.InterfaceController;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     * @throws IOException 
     * @throws MalformedURLException 
     */
    @Test
    public void shouldAnswerWithTrue() throws MalformedURLException, IOException
    {	
    	ArrayList<ExchangeRateDataSet> list;
    	DBService service = new CurrencyEngine(new InterfaceController()).getService();
		long tempId = ConstParser.getIdLastItemAdded();
    	try {
			list = (ArrayList<ExchangeRateDataSet>) service.getExchangeRateListRange(tempId, tempId - 10);
			
			Iterator itr = list.iterator();
	    	while(itr.hasNext()) {
	    		ExchangeRateDataSet tempDataSet = (ExchangeRateDataSet) itr.next();
	    		System.out.println(tempDataSet.toString());
	    	}
    	} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
        assertTrue( true );
    }
    
    
    
    public static class BuslavskiiVersionOfStepikOrgTaskResultCheck {
        public void main(String[] args) throws Exception {

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
            return ( (!a) & (!b) & c & b ) ^  ( a & b & (!c) & (!d) ) ^((!a) & b & (!c) & d)^((!a)& b &c&(!d))^(a & (!b)& (!c)& d)^(a&(!b)&c&(!d));
        }
    }




}
