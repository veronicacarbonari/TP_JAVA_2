package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import clasesSistemaTuristico.TipoDeAtraccion;
import clasesSistemaTuristico.Usuario;
import jdbc.ConnectionProvider;

public class UsuariosDAOImpl implements UsuariosDAO {

	public int insert(Usuario usuario) {
		try {
			String sql = "INSERT INTO USUARIO (nombre, id_tipo_atraccion, tiempo, presupuesto) VALUES (?, ?, ?, ?)";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, usuario.getNombre());
			statement.setObject(2, usuario.getIdTipoAtraccion());
			statement.setDouble(3, usuario.getTiempo());
			statement.setInt(4, usuario.getPresupuesto());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int update(Usuario usuario) {
		try {
			String sql = "UPDATE USUARIO SET tiempo = ?, presupuesto = ? WHERE nombre = ?";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setDouble(1, usuario.getTiempo());
			statement.setInt(2, usuario.getPresupuesto());
			statement.setString(3, usuario.getNombre());
			int rows = statement.executeUpdate();

			return rows;

		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int delete(Usuario usuario) {
		try {
			String sql = "DELETE FROM USUARIO WHERE nombre = ?";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, usuario.getNombre());
			int rows = statement.executeUpdate();

			return rows;

		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public List<Usuario> findAll() {
		try {
			String sql = "SELECT * FROM USUARIO AS U " + "JOIN TIPODEATRACCION AS TA "
					+ "WHERE U.id_tipo_atraccion = TA.id_tipo_atraccion";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			List<Usuario> usuarios = new ArrayList<Usuario>();
			while (resultados.next()) {
				usuarios.add(toUser(resultados));
			}

			return usuarios;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public Usuario findByUsername(String usuario) {
		try {
			String sql = "SELECT * FROM USUARIO \r\n" + "JOIN TIPODEATRACCION\r\n"
					+ "where TIPODEATRACCION.id_tipo_atraccion = USUARIO.id_tipo_atraccion  AND usuario.nombre = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, usuario);
			ResultSet resultados = statement.executeQuery();

			Usuario user = null;

			if (resultados.next()) {
				user = toUser(resultados);
			}

			return user;

		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public Usuario findById(int id) {
		try {
			String sql = "SELECT * FROM USUARIO AS U " + "JOIN TIPODEATRACCION AS TA "
					+ "WHERE U.id_tipo_atraccion = TA.id_tipo_atraccion AND usuario.id_usuario = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultados = statement.executeQuery();

			Usuario user = null;

			if (resultados.next()) {
				user = toUser(resultados);
			}

			return user;

		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int countAll() {
		int total = 0;

		try {
			String sql = "SELECT count(*) AS TOTAL FROM USUARIO";
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

	public Usuario toUser(ResultSet resultados) throws Exception {

		return new Usuario(resultados.getString(2), TipoDeAtraccion.valueOf((String) resultados.getObject(7)),
				resultados.getDouble(4), resultados.getInt(5));

	}

	public int findIdByNombreUsuario(String nombreUsuario) {
		try {
			String sql = "SELECT id_usuario FROM USUARIO WHERE nombre = ?";

			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, nombreUsuario);
			ResultSet resultados = statement.executeQuery();
			int id_usuario = resultados.getInt(1);

			return id_usuario;

		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
}
