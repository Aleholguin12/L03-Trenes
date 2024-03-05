package fp.trenes;

import java.time.LocalTime;
import java.util.List;

public interface TrayectoTren {
	String getCodigoTren(); 
	String getNombreTrayecto();
	TipoTren getTipo();
	List<String> getEstaciones();
	List<LocalTime> getHorasSalida();
	List<LocalTime> getHorasLlegada();
	LocalTime getHoraSalida(String estacion);
	LocalTime getHoraLlegada(String estacion);
	void anadirEstacionIntermedia(int posicion, String estacion, LocalTime horaLlegada, LocalTime horaSalida);
	void eliminarEstacionIntermedia(String estacion);
}
