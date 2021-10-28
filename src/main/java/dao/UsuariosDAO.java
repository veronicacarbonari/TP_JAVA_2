package dao;

import java.sql.ResultSet;

import clasesSistemaTuristico.Usuario;

public interface UsuariosDAO extends GenericDAO<Usuario>{

	public abstract int findIdByNombreUsuario(String nombreUsuario);
	public abstract Usuario findByUsername(String nombre);
	public abstract Usuario toUser(ResultSet resultados) throws Exception;

}