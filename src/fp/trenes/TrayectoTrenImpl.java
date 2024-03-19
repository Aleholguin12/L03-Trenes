package fp.trenes;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class TrayectoTrenImpl {
	
	private String codigoTren; 
	private String nombreTrayecto;
	private TipoTren tipo;
	private List<String> estaciones;
	private List<LocalTime> horasSalida;
	private List<LocalTime> horasLlegada;
	public TrayectoTrenImpl(String codigoTren, String nombreTrayecto, TipoTren tipo, List<String> estaciones,
			List<LocalTime> horasSalida, List<LocalTime> horasLlegada) {

		checkCodigo(codigoTren);
		checkHorasSalida(horasSalida);
		checkHorasLlegada(horasLlegada);
		checkHorarios(horasSalida, horasLlegada);
		
		this.codigoTren = codigoTren;
		this.nombreTrayecto = nombreTrayecto;
		this.tipo = tipo;
		this.estaciones = estaciones;
		this.horasSalida = horasSalida;
		this.horasLlegada = horasLlegada;
	}

	private void checkCodigo(String codigoTren) {
		if (codigoTren.length() != 5) {
			throw new IllegalArgumentException("El código del tren tiene que tener 5 dígitos");
		}
	}
	
	private void checkHorasSalida(List<LocalTime> horasSalida) {
		if (horasSalida.get(0) == null) {
			throw new IllegalArgumentException("La hora de salida de la primera estación no puede ser null");
		}
	}
	
	private void checkHorasLlegada(List<LocalTime> horasLlegada) {
		if (horasLlegada.get(horasLlegada.size() - 1) == null) {
			throw new IllegalArgumentException("La hora de llegada de la última estación no puede ser null");
		}
	}
	
	private void checkHorarios(List<LocalTime> horasSalida, List<LocalTime> horasLlegada) {
		if (!(horasSalida.get(0).isBefore(horasLlegada.get(horasLlegada.size() - 1)))) {
			throw new IllegalArgumentException("La hora de salida de la primera estación debe ser anterior a la hora de "
					+ "llegada a la última estación");
		}
	}
	
	private void checkPosicion(int posicion) {
		if (!(posicion >= 1 && posicion < estaciones.size())) {
			throw new IllegalArgumentException("Debe introducirse en una posición intermedia");
		}
	}
	
	private void checkHora (LocalTime horaLlegada, LocalTime horaSalida) {
		if (horaSalida.isBefore(horaLlegada)) {
			throw new IllegalArgumentException("La hora de salida no puede ser anterior a la hora de llegada");
		}
	}
	
	private void checkHoraLlegadaConRespectoEstAnt (LocalTime horaLlegada, String estacion) {
		if (horaLlegada.isBefore(horasSalida.get(estaciones.indexOf(estacion) - 1))) {
			throw new IllegalArgumentException("La hora de llegada es anterior a la hora de salida de la estación anterior");
		}
	}
	
	private void checkHoraSalidaConRespectoEstAnt (LocalTime horaSalida, String estacion) {
		if (horaSalida.isAfter(horasLlegada.get(estaciones.indexOf(estacion) + 1))) {
			throw new IllegalArgumentException("La hora de salida es posterior a la hora de llegada de la estación siguiente");
		}
	}
	
	private void checkEliminarEstacion(String estacion) {
		if (estaciones.indexOf(estacion) == 0 || estaciones.indexOf(estacion) == (estaciones.size() - 1) || !(estaciones.contains(estacion))) {
			throw new IllegalArgumentException("La estacion debe de estar en la lista y además no puede ser la primera ni la última");
		}
	}

	public String getCodigoTren() {
		return codigoTren;
	}

	public String getNombreTrayecto() {
		return nombreTrayecto;
	}

	public TipoTren getTipo() {
		return tipo;
	}

	public List<String> getEstaciones() {
		return estaciones;
	}

	public List<LocalTime> getHorasSalida() {
		return horasSalida;
	}

	public List<LocalTime> getHorasLlegada() {
		return horasLlegada;
	}
	
	public LocalTime getHoraSalida(String estacion) {
		return horasSalida.get(estaciones.indexOf(estacion));
	}
	
	public LocalTime getHoraLlegada(String estacion) {
		return horasLlegada.get(estaciones.indexOf(estacion));
	}
	
	public void anadirEstacionIntermedia(int posicion, String estacion, LocalTime horaLlegada, LocalTime horaSalida) {
		checkPosicion(posicion);
		checkHora (horaLlegada, horaSalida);
		checkHoraLlegadaConRespectoEstAnt (horaLlegada, estacion);
		checkHoraSalidaConRespectoEstAnt (horaSalida, estacion);
		
		estaciones.add(posicion, estacion);
		horasLlegada.add(posicion, horaLlegada);
		horasSalida.add(posicion, horaSalida);
	}
	
	public void eliminarEstacionIntermedia(String estacion) {
		checkEliminarEstacion(estacion);
		int posicion = estaciones.indexOf(estacion);
		estaciones.remove(estacion);
		horasSalida.remove(posicion);
		horasLlegada.remove(posicion);
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigoTren, horasSalida, nombreTrayecto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrayectoTrenImpl other = (TrayectoTrenImpl) obj;
		return Objects.equals(codigoTren, other.codigoTren) && Objects.equals(horasSalida, other.horasSalida)
				&& Objects.equals(nombreTrayecto, other.nombreTrayecto);
	}

	
	@Override
	public String toString() {
		return nombreTrayecto + "-" + tipo + " (" + codigoTren + ")\n" + estaciones.get(0) + "	" + " " +
			"	" + horasSalida.get(0) + "\n" + estaciones.get(estaciones.size() - 1) + "	" + horasLlegada.get(estaciones.size() - 1) + 
			"	" + " ";
	}
	
	

}
