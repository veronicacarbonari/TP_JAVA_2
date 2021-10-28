package dao;

import java.sql.ResultSet;
import clasesSistemaTuristico.Itinerario;


public interface ItinerarioDAO extends GenericDAO<Itinerario>  {

	public abstract Itinerario toItinerario(ResultSet resultados) throws Exception;

}
