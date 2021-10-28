package app;

//import clasesSistemaTuristico.LectorDeArchivos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import clasesSistemaTuristico.Atraccion;
import clasesSistemaTuristico.Producto;
import clasesSistemaTuristico.Promocion;
import clasesSistemaTuristico.SistemaTuristico;
import clasesSistemaTuristico.Usuario;
import dao.AtraccionesDAOImpl;
import dao.DAOFactory;
import dao.PromocionesDAOImpl;
import dao.UsuariosDAOImpl;

public class App {

	public static void main(String[] args) throws IOException, SQLException {
		UsuariosDAOImpl usuarioDao = DAOFactory.getUsuariosDao();
		AtraccionesDAOImpl AtraccionDao = DAOFactory.getAtraccionesDao();
		PromocionesDAOImpl PromocionDao = DAOFactory.getPromocionesDao();

		ArrayList<Usuario> usuarios = (ArrayList<Usuario>) usuarioDao.findAll();
		ArrayList<Atraccion> atracciones = (ArrayList<Atraccion>) AtraccionDao.findAll();
		ArrayList<Promocion> promociones = (ArrayList<Promocion>) PromocionDao.findAll();

		ArrayList<Producto> productos = new ArrayList<Producto>();

		productos.addAll(atracciones);
		productos.addAll(promociones);

		for (int i = 0; i < usuarios.size(); i++) {
			new SistemaTuristico(productos, usuarios.get(i)).sugerirPreferencias();

		}
	}
}
