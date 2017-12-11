package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.SemaforoConfig;
import web.controller.ColorSemaforo;

@Service
public class CardCounterServiceImpl implements CardCounterService {

	@Autowired
	SemaphoreEngine semaforo;

	float cant1=0;
	float cant2=0;
	
	@Override
	public String getHelloWorld() {
		return "Holaa";
	}


	@Override
	public String updateWaitingCars() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColor(String col) {
		if (col.equals("1"))
			return semaforo.getColor1();
		else
			return semaforo.getColor2();
			
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCant(String string) {
		if (string.equals("1"))
			return cant1+"";
		else
			return cant2+"";
	}



	@Override
	public void updateMovingCars(String string, String cong) {
		if (string.equals("1"))
			cant1 = Float.parseFloat(cong);
		else
			cant2 = Float.parseFloat(cong);

	}


	@Override
	public void configSemaforos(SemaforoConfig config) {
		semaforo.configSemaforos(config);
		
	}


	@Override
	public SemaforoConfig getConfigSemaforos() {
		return semaforo.getConfigSemaforos();
	}



}
