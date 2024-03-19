package fp.trenes;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TrayectoTrenImpl implements TrayectoTren {
	private String codigoTren;
	private String nombreTrayecto;
	private TipoTren tipo;
	private List<Parada> paradas;

	public TrayectoTrenImpl(String codigoTren, String nombreTrayecto, TipoTren tipo, String origen, String llegada,
			LocalTime horaSalida, LocalTime horaLlegada) {

		checkCodigo(codigoTren);
		checkHoraSalida(horaSalida);
		checkHoraLlegada(horaLlegada);
		checkHorarios(horaSalida, horaLlegada);

		this.codigoTren = codigoTren;
		this.nombreTrayecto = nombreTrayecto;
		this.tipo = tipo;
		this.paradas = new ArrayList<Parada>();
		Parada origenP = new Parada(origen, null, horaSalida);
		Parada destinoP = new Parada(llegada, horaLlegada, null);
		this.paradas.add(origenP);
		this.paradas.add(destinoP);
	}

	private void checkCodigo(String codigoTren) {
		if (codigoTren.length() != 5) {
			throw new IllegalArgumentException("El código del tren tiene que tener 5 dígitos");
		}
	}

	private void checkHoraSalida(LocalTime horaSalida) {
		if (horaSalida == null) {
			throw new IllegalArgumentException("La hora de salida de la primera estación no puede ser null");
		}
	}

	private void checkHoraLlegada(LocalTime horaLlegada) {
		if (horaLlegada == null) {
			throw new IllegalArgumentException("La hora de llegada de la última estación no puede ser null");
		}
	}

	private void checkHorarios(LocalTime horaSalida, LocalTime horaLlegada) {
		if (!(horaSalida.isBefore(horaLlegada))) {
			throw new IllegalArgumentException(
					"La hora de salida de la primera estación debe ser anterior a la hora de "
							+ "llegada a la última estación");
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
		List<String> result = new ArrayList<String>();
		for (Parada p : paradas) {
			result.add(p.estacion());
		}
		return result;
	}

	public List<LocalTime> getHorasSalida() {
		List<LocalTime> result = new ArrayList<LocalTime>();
		for (Parada p : paradas) {
			result.add(p.horaSalida());
		}
		return result;
	}

	public List<LocalTime> getHorasLlegada() {
		List<LocalTime> result = new ArrayList<LocalTime>();
		for (Parada p : paradas) {
			result.add(p.horaLlegada());
		}
		return result;
	}

	public LocalTime getHoraLlegada() {
		return paradas.get(paradas.size() - 1).horaLlegada();
	}

	public LocalTime getHoraSalida() {
		return paradas.get(0).horaSalida();
	}

	public Duration getDuracionTrayecto() {
		return Duration.between(getHoraSalida(), getHoraLlegada());
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigoTren, nombreTrayecto, getHoraSalida());
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
		return Objects.equals(codigoTren, other.codigoTren) && Objects.equals(nombreTrayecto, other.nombreTrayecto)
				&& Objects.equals(getHoraSalida(), other.getHoraSalida());
	}

	@Override
	public String toString() {
		String result = nombreTrayecto + "-" + tipo + "(" + codigoTren + ")\n";
		for (Parada p : paradas) {
			String llegada = p.horaLlegada() != null ? p.horaLlegada().toString() : "";
			String salida = p.horaSalida() != null ? p.horaSalida().toString() : "";
			result += p.estacion() + "\t" + llegada + "\t" + salida + "\n";
		}
		return result;
	}

	// Otras operaciones
	public LocalTime getHoraSalida(String estacion) {
		LocalTime result = null;

		int pos = getEstaciones().indexOf(estacion);
		if (pos != -1 && pos != getEstaciones().size() - 1) {
			result = getHorasSalida().get(pos);
		}

		return result;
	}

	public LocalTime getHoraLlegada(String estacion) {
		LocalTime result = null;

		int pos = getEstaciones().indexOf(estacion);
		if (pos != -1 && pos != 0) {
			result = getHorasLlegada().get(pos);
		}

		return result;
	}

	
	private void checkPosicion(int posicion) {
		if (!(posicion >= 1 && posicion < getEstaciones().size())) {
			throw new IllegalArgumentException("Debe introducirse en una posición intermedia");
		}
	}
	
	private void checkHora(LocalTime horaLlegada, LocalTime horaSalida) {
		if (horaSalida.isBefore(horaLlegada)) {
			throw new IllegalArgumentException("La hora de salida no puede ser anterior a la hora de llegada");
		}
	}
	
	private void checkHoraPosteriorA(LocalTime hora, LocalTime referencia) {
		if (!hora.equals(referencia) && !hora.isAfter(referencia)) {
			throw new IllegalArgumentException(
					"La hora de salida es posterior a la hora de llegada de la estación siguiente");
		}
	}

	public void anadirEstacionIntermedia(int posicion, String estacion, LocalTime horaLlegada, 
			LocalTime horaSalida) {
		
		checkPosicion(posicion);
		checkHora(horaLlegada, horaSalida);
		
		Parada anterior = paradas.get(posicion-1);
		Parada posterior = paradas.get(posicion);
		
		checkHoraPosteriorA(horaLlegada, anterior.horaSalida());
		checkHoraPosteriorA(posterior.horaLlegada(), horaSalida);

		Parada actual = new Parada(estacion, horaLlegada, horaSalida);
		paradas.add(posicion, actual);
		
	}

	private void checkEliminarEstacion(String estacion) {
		int pos = getEstaciones().indexOf(estacion);
		if (pos == 0 || pos == getEstaciones().size() - 1 || pos == -1) {
			throw new IllegalArgumentException(
					"La estacion debe de estar en la lista y además no puede ser la primera ni la última");
		}
	}

	public void eliminarEstacionIntermedia(String estacion) {
		checkEliminarEstacion(estacion);
		int posicion = getEstaciones().indexOf(estacion);
		paradas.remove(posicion);
	}

	

	



}
