package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.controller.ColorSemaforo;

@Service
public class CardCounterServiceImpl implements CardCounterService {

	@Autowired
	SemaphoreEngine semaforo;

	@Override
	public String getHelloWorld() {
		return "Holaa";
	}

	@Override
	public String updateMovingCars() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateWaitingCars() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColor() {
		return semaforo.getColor();
	}

	@Override
	public void updateTimeMin(int time, ColorSemaforo color) {

		switch (color) {
		
		case green:
			break;

		case red:
			break;

		case yellow:
			break;

		}

	}

	@Override
	public void updateTimeMax(int time, ColorSemaforo color) {

		switch (color) {
		
		case green:
			semaforo.setTiempo_verde(time);
			break;

		case red:
			semaforo.setTiempo_rojo(time);
			break;

		case yellow:
			semaforo.setTiempo_amarillo(time);
			break;

		}
		
	}

}
