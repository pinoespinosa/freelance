package web.service;

public class SemaphoreEngine {

	private static int tiempo_rojo = 10;
	private static int tiempo_verde = 10;
	private static int tiempo_amarillo = 2;
	private static String color = "red";
	
	public SemaphoreEngine() {
		init();
	}

	public static int getTiempo_rojo() {
		return tiempo_rojo;
	}

	public static void setTiempo_rojo(int tiempo_rojo) {
		SemaphoreEngine.tiempo_rojo = tiempo_rojo;
	}

	public static int getTiempo_verde() {
		return tiempo_verde;
	}

	public static void setTiempo_verde(int tiempo_verde) {
		SemaphoreEngine.tiempo_verde = tiempo_verde;
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
						setColor("green");
						Thread.sleep(1000*tiempo_verde);
					} catch (Exception e) {
					}
					try {
						setColor("yellow");
						Thread.sleep(1000*tiempo_amarillo);
					} catch (Exception e) {
					}
					try {
						setColor("red");
						Thread.sleep(1000*tiempo_rojo);
					} catch (Exception e) {
					}

				}

			}
		};

		thread.start();

	}

	public static String getColor() {
		return color;
	}

	public static void setColor(String color) {
		SemaphoreEngine.color = color;
	}

}
