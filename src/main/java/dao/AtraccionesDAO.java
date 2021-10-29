package dao;

import java.sql.ResultSet;
import clasesSistemaTuristico.Atraccion;
import clasesSistemaTuristico.Producto;

public interface AtraccionesDAO extends GenericDAO<Atraccion> {

	public abstract Atraccion findByAtraccion(String nombre);
	public abstract int findIdByNombreAtraccion(String nombreAtraccion);
	public abstract Producto toAtraccion(ResultSet resultados) throws Exception;
	
}