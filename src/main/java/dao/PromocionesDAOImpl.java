package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import clasesSistemaTuristico.Atraccion;
import clasesSistemaTuristico.Promocion;
import clasesSistemaTuristico.PromocionAbsoluta;
import clasesSistemaTuristico.PromocionAxB;
import clasesSistemaTuristico.PromocionPorcentual;
import jdbc.ConnectionProvider;

public class PromocionesDAOImpl implements PromocionesDAO {

	private ArrayList<Atraccion> atracciones = new ArrayList<Atraccion>();
	private ArrayList<Promocion> promociones = new ArrayList<Promocion>();

	public ArrayList<Promocion> findAll() {

		try {
			String sql = "SELECT * FROM PROMOCIONES";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			while (resultados.next()) {

				promociones.add(toPromocion(resultados));

			}

			return promociones;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public Promocion findByPromocion(String nombrePromo) {
		try {
			String sql = "SELECT * FROM PROMOCIONES WHERE nombre = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, nombrePromo);
			ResultSet resultados = statement.executeQuery();

			Promocion promocion = null;

			if (resultados.next()) {
				promocion = toPromocion(resultados);
			}

			return promocion;

		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int countAll() {
		int total = 0;

		try {
			String sql = "SELECT count(*) AS TOTAL FROM PROMOCIONES";
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

	public ArrayList<Atraccion> findAtraccionesDePromocion(String sql) {
		ArrayList<Atraccion> a = null;
		try {
			a = new ArrayList<Atraccion>();
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet results = statement.executeQuery();

			AtraccionesDAOImpl AtraccionDao = DAOFactory.getAtraccionesDao();

			while (results.next()) {
				a.add(AtraccionDao.toAtraccion(results));

			}
		} catch (Exception e) {
			throw new MissingDataException(e);
		}

		return a;
	}

	public Promocion toPromocion(ResultSet resultados) {
		this.atracciones.clear();
		Promocion rta = null;
		Promocion rta1 = null;
		Promocion rta2 = null;
		try {

			if (resultados.getString(3).equals("PromocionAxB")) {

				String sql = "select A.nombre, A.cupo, A.costo, A.tiempo, TA.tipo_atraccion from PROMOCIONES as P "
						+ "JOIN TIPODEATRACCION AS TA ON TA.id_tipo_atraccion = a.id_tipo_atraccion "
						+ "JOIN ATRACCIONES_DE_PROMOS AS PA ON PA.id_promo = P.id_promo "
						+ "JOIN ATRACCIONES AS A ON PA.id_atraccion = a.id_atraccion "
						+ "WHERE P.tipo_promo LIKE 'PromocionAxB';";

				ArrayList<Atraccion> a = findAtraccionesDePromocion(sql);

				rta = new PromocionAxB(a, resultados.getString(4));

			} else if (resultados.getString(3).equals("PromocionPorcentual")) {

				String sql = "select A.nombre, A.cupo, A.costo, A.tiempo, TA.tipo_atraccion from PROMOCIONES as P "
						+ "JOIN TIPODEATRACCION AS TA ON TA.id_tipo_atraccion = a.id_tipo_atraccion "
						+ "JOIN ATRACCIONES_DE_PROMOS AS PA ON PA.id_promo = P.id_promo "
						+ "JOIN ATRACCIONES AS A ON PA.id_atraccion = a.id_atraccion "
						+ "WHERE P.tipo_promo LIKE 'PromocionPorcentual';";

				ArrayList<Atraccion> a = findAtraccionesDePromocion(sql);

				rta1 = new PromocionPorcentual(a, resultados.getString(4), resultados.getInt(6));

			} else if (resultados.getString(3).equals("PromocionAbsoluta")) {
				// System.out.println("entró en absolut");
				String sql = "select A.nombre, A.cupo, A.costo, A.tiempo, TA.tipo_atraccion from PROMOCIONES as P "
						+ "JOIN TIPODEATRACCION AS TA ON TA.id_tipo_atraccion = a.id_tipo_atraccion "
						+ "JOIN ATRACCIONES_DE_PROMOS AS PA ON PA.id_promo = P.id_promo "
						+ "JOIN ATRACCIONES AS A ON PA.id_atraccion = a.id_atraccion "
						+ "WHERE P.tipo_promo LIKE 'PromocionAbsoluta';";

				ArrayList<Atraccion> a = findAtraccionesDePromocion(sql);

				rta2 = new PromocionAbsoluta(a, resultados.getString(4), resultados.getInt(7));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rta != null ? rta : rta1 != null ? rta1 : rta2;

	}

	public int update(Promocion p) {
		try {
			AtraccionesDAOImpl AtraccionDao = DAOFactory.getAtraccionesDao();
			ArrayList<Atraccion> arrayAtracciones = p.getAtracciones();
			for (Atraccion a : arrayAtracciones) {
				a.agregarVisitantes(1);
				AtraccionDao.update(a);
			}

		} catch (Exception e) {
			throw new MissingDataException(e);
		}
		return 0;

	}

	public int delete(Promocion p) {
		try {
			String sql = "DELETE FROM PROMOCIONES WHERE nombre = ?";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, p.getNombre());
			int rows = statement.executeUpdate();

			return rows;

		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int insert(Promocion p) {
		try {
			if (p.getClass().getSimpleName().equals("PromocionPorcentual")) {
				insertPromoPorcentual(p);
			} else if (p.getClass().getSimpleName().equals("PromocionAbsoluta")) {
				insertPromoAbsoluta(p);
			} else {
				insertPromoAxB(p);

			}

		} catch (Exception e) {
			throw new MissingDataException(e);
		}
		return 0;

	}

	public int insertPromoPorcentual(Promocion p) {
		try {
			String sql = "INSERT INTO PROMOCIONES (id_tipo_producto, tipo_promo, nombre, id_tipo_atraccion, descuento, precio) VALUES (?, ?, ?, ?, ?, ?)";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, p.getIdTipoProducto(p));
			String tipoPromo = p.getClass().getSimpleName();
			statement.setString(2, tipoPromo);
			statement.setString(3, p.getNombre());
			statement.setInt(4, p.getTipo().equals("AVENTURA") ? 1 : p.getTipo().equals("PAISAJE") ? 2 : 3);
			statement.setInt(5, ((PromocionPorcentual) p).getPorcentaje());

			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	@SuppressWarnings("unlikely-arg-type")
	public int insertPromoAbsoluta(Promocion p) {
		try {
			String sql = "INSERT INTO PROMOCIONES (id_tipo_producto, tipo_promo, nombre, id_tipo_atraccion, descuento, precio) VALUES (?, ?, ?, ?, ?, ?)";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, p.getIdTipoProducto(p));
			String tipoPromo = p.getClass().getSimpleName();
			statement.setString(2, tipoPromo);
			statement.setString(3, p.getNombre());
			statement.setInt(4, p.getTipo().equals("AVENTURA") ? 1 : p.getTipo().equals("PAISAJE") ? 2 : 3);

			statement.setInt(6, ((PromocionAbsoluta) p).getCostoFijo());

			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	@SuppressWarnings("unlikely-arg-type")
	public int insertPromoAxB(Promocion p) {
		try {
			String sql = "INSERT INTO PROMOCIONES (id_tipo_producto, tipo_promo, nombre, id_tipo_atraccion, descuento, precio) VALUES (?, ?, ?, ?, ?, ?)";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, p.getIdTipoProducto(p));
			String tipoPromo = p.getClass().getSimpleName();
			statement.setString(2, tipoPromo);
			statement.setString(3, p.getNombre());
			statement.setInt(4, p.getTipo().equals("AVENTURA") ? 1 : p.getTipo().equals("PAISAJE") ? 2 : 3);

			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int findIdByNombrePromocion(String nombrePromo) {
		try {
			String sql = "SELECT id_promo FROM PROMOCIONES WHERE nombre = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, nombrePromo);
			ResultSet resultados = statement.executeQuery();

			int id_promo = resultados.getInt(1);

			return id_promo;

		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
}
