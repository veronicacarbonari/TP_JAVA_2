package dao;

public class DAOFactory {
	
	public static UsuariosDAOImpl getUsuariosDao() {
		return new UsuariosDAOImpl();
	}
	public static AtraccionesDAOImpl getAtraccionesDao() {
		return new AtraccionesDAOImpl();
	}
	public static PromocionesDAOImpl getPromocionesDao() {
		return new PromocionesDAOImpl();
	}

	public static ItinerarioDAOImpl getItinerariosDao() {
		return new ItinerarioDAOImpl();
	}
}
