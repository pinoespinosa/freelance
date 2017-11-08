package web.service;

import web.controller.ColorSemaforo;

public interface CardCounterService {

	String getHelloWorld();

	String updateMovingCars();

	String updateWaitingCars();

	void updateTimeMin(int time, ColorSemaforo color);

	void updateTimeMax(int time, ColorSemaforo color);

	String getColor();
}
