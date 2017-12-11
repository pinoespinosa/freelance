package web.service;

import spring.SemaforoConfig;
import web.controller.ColorSemaforo;

public interface CardCounterService {

	String getHelloWorld();

	void updateMovingCars(String string, String congestion);

	String updateWaitingCars();

	void updateTimeMin(int time, ColorSemaforo color);

	void updateTimeMax(int time, ColorSemaforo color);

	String getColor(String sem);

	String getCant(String string);

	void configSemaforos(SemaforoConfig config);

	SemaforoConfig getConfigSemaforos();
}
