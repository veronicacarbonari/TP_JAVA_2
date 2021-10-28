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

	public int delete(Itinerario itinerario) {
		try {
			int id = usuarioDao.findIdByNombreUsuario(itinerario.usuario.getNombre());
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

	public Itinerario findItinerarioByUsername(String nombreUsuario) {
		try {
			int id = usuarioDao.findIdByNombreUsuario(nombreUsuario);

			String sql = "SELECT * FROM ITINERARIO WHERE id_usuario = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultados = statement.executeQuery();

			
			Itinerario itinerario = new Itinerario();

			if (resultados.next()) {
				if (resultados.getInt(3) == 1) {
					System.out.println(atraccionDao.toAtraccion(resultados));
					itinerario.productos.add(atraccionDao.toAtraccion(resultados));
				} else {
					itinerario.productos.add(promoDao.toPromocion(resultados));

				}

			}

			return itinerario;

		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public Itinerario toItinerario(ResultSet resultados) throws Exception {
		Usuario user = usuarioDao.findById(resultados.getInt(2));
		return new Itinerario(user);

	}

	public List<Itinerario> findAll() {
		try {
			String sql = "SELECT * FROM ITINERARIO";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			List<Itinerario> itinerarios = new ArrayList<Itinerario>();
			while (resultados.next()) {
				itinerarios.add(toItinerario(resultados));
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
		boolean rta = false;
		int id = usuarioDao.findIdByNombreUsuario(user.getNombre());
			String sql = "SELECT count(*) AS TOTAL FROM ITINERARIO where id_usuario = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultados = statement.executeQuery();

			
			if (resultados.next()) {
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

}
