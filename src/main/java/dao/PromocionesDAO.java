package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import clasesSistemaTuristico.Promocion;

public interface PromocionesDAO extends GenericDAO<Promocion>  {
	
	public abstract int findIdByNombrePromocion(String nombrePromo);
	public abstract Promocion findByPromocion(String nombre);
	public abstract Promocion toPromocion(ResultSet resultados) throws SQLException;

}
