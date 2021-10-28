package dao;

import java.sql.ResultSet;
import clasesSistemaTuristico.Atraccion;

public interface AtraccionesDAO extends GenericDAO<Atraccion> {

	public abstract Atraccion findByAtraccion(String nombre);
	public abstract int findIdByNombreAtraccion(String nombreAtraccion);
	public abstract Atraccion toAtraccion(ResultSet resultados) throws Exception;
	
}
