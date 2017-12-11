package web.service;

import spring.SemaforoConfig;

public class SemaphoreEngine {

	private static int tiempo_verde_1 = 10;
	private static int tiempo_verde_2 = 10;
	private static int tiempo_amarillo_1 = 2;
	private static int tiempo_amarillo_2 = 2;
	private static int tiempo_rojo_doble = 2;

	private static String color1 = "red";
	private static String color2 = "red";

	public SemaphoreEngine() {
		init();
	}

	public static void init() {

		Thread thread = new Thread() {
			public void run() {

				while (true) {

					try {

						setColor1("green");
						setColor2("red");
						Thread.sleep(1000 * tiempo_verde_1);

						setColor1("yellow");
						setColor2("red");
						Thread.sleep(1000 * tiempo_amarillo_1);

						setColor1("red");
						setColor2("red");
						Thread.sleep(1000 * tiempo_rojo_doble);

						setColor1("red");
						setColor2("green");
						Thread.sleep(1000 * tiempo_verde_2);

						setColor1("red");
						setColor2("yellow");
						Thread.sleep(1000 * tiempo_amarillo_2);

						setColor1("red");
						setColor2("red");
						Thread.sleep(1000 * 2);

					} catch (Exception e) {
						// TODO: handle exception
					}

				}

			}
		};

		thread.start();

	}

	public static String getColor1() {
		return color1;
	}

	public static void setColor1(String color1) {
		SemaphoreEngine.color1 = color1;
	}

	public static String getColor2() {
		return color2;
	}

	public static void setColor2(String color2) {
		SemaphoreEngine.color2 = color2;
	}

	public void configSemaforos(SemaforoConfig config) {
		tiempo_verde_1 = config.getTiempo_verde_1();
		tiempo_verde_2 = config.getTiempo_verde_2();
		tiempo_amarillo_1 = config.getTiempo_amarillo_1();
		tiempo_amarillo_2 = config.getTiempo_amarillo_2();
		tiempo_rojo_doble = config.getTiempo_rojo_doble();
		
	}

	public SemaforoConfig getConfigSemaforos() {
		
		SemaforoConfig sem = new SemaforoConfig();
		sem.setTiempo_amarillo_1(tiempo_amarillo_1);
		sem.setTiempo_amarillo_2(tiempo_amarillo_2);
		sem.setTiempo_rojo_doble(tiempo_rojo_doble);
		sem.setTiempo_verde_1(tiempo_verde_1);
		sem.setTiempo_verde_2(tiempo_verde_2);
		return sem;
	}

}
