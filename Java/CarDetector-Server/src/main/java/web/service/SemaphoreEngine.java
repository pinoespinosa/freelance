package web.service;

public class SemaphoreEngine {

	private static int tiempo_verde_1 = 10;
	private static int tiempo_verde_2 = 10;

	private static int tiempo_amarillo = 2;

	private static String color1 = "red";
	private static String color2 = "red";

	public SemaphoreEngine() {
		init();
	}



	public static int getTiempo_amarillo() {
		return tiempo_amarillo;
	}

	public static void setTiempo_amarillo(int tiempo_amarillo) {
		SemaphoreEngine.tiempo_amarillo = tiempo_amarillo;
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
						Thread.sleep(1000 * tiempo_amarillo);

						setColor1("red");
						setColor2("red");
						Thread.sleep(1000 * 2);

						

						setColor1("red");
						setColor2("green");
						Thread.sleep(1000 * tiempo_verde_2);

						setColor1("red");
						setColor2("yellow");
						Thread.sleep(1000 * tiempo_amarillo);

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



}
