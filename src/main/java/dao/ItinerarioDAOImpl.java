package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import clasesSistemaTuristico.Itinerario;
import clasesSistemaTuristico.Producto;
import clasesSistemaTuristico.Promocion;
import clasesSistemaTuristico.Usuario;
import jdbc.ConnectionProvider;

public class ItinerarioDAOImpl implements ItinerarioDAO {

	AtraccionesDAOImpl atraccionDao = DAOFactory.getAtraccionesDao();
	PromocionesDAOImpl promoDao = DAOFactory.getPromocionesDao();
	UsuariosDAOImpl usuarioDao = DAOFactory.getUsuariosDao();

	public int insert(Usuario user, Producto p) {

		try {

			int idUsuario = usuarioDao.findIdByNombreUsuario(user.getNombre());

			String sql = "INSERT INTO ITINERARIO (id_usuario, id_tipo_producto, id_item) VALUES (?, ?, ?)";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setInt(1, idUsuario);

			if (p.esPromocion()) {
				int idPromo = promoDao.findIdByNombrePromocion(p.getNombre());
				statement.setInt(2, 2);
				statement.setInt(3, idPromo);
			} else {
				int idAtraccion = atraccionDao.findIdByNombreAtraccion(p.getNombre());
				statement.setInt(2, 1);
				statement.setInt(3, idAtraccion);
			}

			int rows = statement.executeUpdate();

			return rows;

		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int delete(Usuario user) {
		try {

			int id = usuarioDao.findIdByNombreUsuario(user.getNombre());
			String sql = "DELETE FROM ITINERARIO WHERE id_usuario = ?";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			int rows = statement.executeUpdate();

			return rows;

		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public Itinerario findItinerarioByUser(Usuario user) {
		try {
			int id = usuarioDao.findIdByNombreUsuario(user.getNombre());

			String sql = "SELECT * FROM ITINERARIO WHERE id_usuario = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultados = statement.executeQuery();

			ArrayList<Producto> atracciones = findAtraccionesItinerarioByUser(user);
			ArrayList<Producto> promos = findPromosItinerarioByUser(user);

			ArrayList<Producto> productos = new ArrayList<Producto>();
			productos.addAll(atracciones);
			productos.addAll(promos);
			
			return toItinerario(productos);

		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public ArrayList<Producto> findAtraccionesItinerarioByUser(Usuario user) {

		try {
			int id = usuarioDao.findIdByNombreUsuario(user.getNombre());

			String sql = "SELECT nombre, cupo, costo, tiempo, TIPODEATRACCION.tipo_atraccion  FROM ITINERARIO JOIN ATRACCIONES JOIN TIPODEATRACCION  WHERE ITINERARIO.id_tipo_producto = ATRACCIONES.id_tipo_producto AND ITINERARIO.id_item = ATRACCIONES.id_atraccion AND ATRACCIONES.id_tipo_atraccion = TIPODEATRACCION.id_tipo_atraccion AND ITINERARIO.id_usuario = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultados = statement.executeQuery();
			ArrayList<Producto> atracciones = new ArrayList<Producto>();

			if (resultados.next()) {

				atracciones.add(atraccionDao.toProductoAtraccion(resultados));

			}

			return atracciones;

		} catch (Exception e) {
			throw new MissingDataException(e);
		}

	}

	public ArrayList<Producto> findPromosItinerarioByUser(Usuario user) {

		try {
			int id = usuarioDao.findIdByNombreUsuario(user.getNombre());

			String sql = "SELECT PROMOCIONES.id_promo, PROMOCIONES.id_tipo_producto, PROMOCIONES.tipo_promo, PROMOCIONES.nombre, PROMOCIONES.id_tipo_atraccion, PROMOCIONES.descuento, PROMOCIONES.precio  FROM PROMOCIONES JOIN ITINERARIO  WHERE ITINERARIO.id_tipo_producto = PROMOCIONES.id_tipo_producto AND ITINERARIO.id_item = PROMOCIONES.id_promo AND ITINERARIO.id_usuario = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultados = statement.executeQuery();
			ArrayList<Producto> promos = new ArrayList<Producto>();

			if (resultados.next()) {

				promos.add(promoDao.toPromocion(resultados));

			}

			return promos;

		} catch (Exception e) {
			throw new MissingDataException(e);
		}

	}

	public Itinerario toItinerario(ArrayList<Producto> p) throws Exception {
		Itinerario itinerario = new Itinerario(p);
		return itinerario;

	}

	public List<Itinerario> findAll() {
		try {
			String sql = "SELECT distinct id_usuario FROM ITINERARIO";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			List<Itinerario> itinerarios = new ArrayList<Itinerario>();
			while (resultados.next()) {
				Usuario user = usuarioDao.findById(resultados.getInt(1));

				itinerarios.add(findItinerarioByUser(user));

			}

			return itinerarios;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int countAll() {
		int total = 0;

		try {
			String sql = "SELECT count(*) AS TOTAL FROM ITINERARIO";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			if (resultados.next()) {
				total = resultados.getInt("TOTAL");
			}

			return total;

		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public boolean existe(Usuario user) {
		try {
			boolean rta;
			int id = usuarioDao.findIdByNombreUsuario(user.getNombre());
			String sql = "SELECT *  FROM ITINERARIO where id_usuario = ?;";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultados = statement.executeQuery();

			if (!resultados.next()) {
				rta = false;
			} else {
				rta = true;
			}
			return rta;

		} catch (Exception e) {
			throw new MissingDataException(e);
		}

	}

	public int update(Itinerario t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int insert(Itinerario t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int delete(Itinerario t) {
		// TODO Auto-generated method stub
		return 0;
	}

}