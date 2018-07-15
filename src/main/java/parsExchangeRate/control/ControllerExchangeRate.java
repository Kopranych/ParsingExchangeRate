package parsExchangeRate.control;

import parsExchangeRate.model.ExchangeRate;

public interface ControllerExchangeRate {
	void getActualExchangeRate();

	void showExchangeRate(ExchangeRate exchanger);
}
