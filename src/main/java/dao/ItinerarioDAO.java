package dao;

import java.util.ArrayList;

import clasesSistemaTuristico.Itinerario;
import clasesSistemaTuristico.Producto;

public interface ItinerarioDAO extends GenericDAO<Itinerario>  {

	public abstract Itinerario toItinerario(ArrayList<Producto> p) throws Exception;
	
}
