package fp.trenes.test;

import java.time.LocalTime;

import fp.trenes.TipoTren;
import fp.trenes.TrayectoTren;
import fp.trenes.TrayectoTrenImpl;

public class TestTrayectoTren {

	public static void main(String[] args) {
		TrayectoTren trayecto = new TrayectoTrenImpl("02471", "SEVILLA-MADRID", TipoTren.AV_CITY, "Sevilla-Sta. Justa",
				"Madrid-Puerta de Atocha", LocalTime.of(7, 0), LocalTime.of(10, 2));
		trayecto.anadirEstacionIntermedia(1, "PUERTOLLANO", LocalTime.of(8, 40), LocalTime.of(8, 41));
		trayecto.anadirEstacionIntermedia(1, "CORDOBA", LocalTime.of(7, 45), LocalTime.of(7, 50));
		System.out.println(trayecto);
	}

}
