package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartamentoDao;
import model.entities.Departamento;

public class DepartamentoDaoJDBC implements DepartamentoDao {

	// Dependencia
	private Connection conn;

	public DepartamentoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void inserir(Departamento obj) {
		
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO department "
					+"(Name) "
					+"VALUES "
					+"(?)", Statement.RETURN_GENERATED_KEYS);
					
			st.setString(1, obj.getNome());
			
			int rowAffected = st.executeUpdate();
			
			if(rowAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
			}
			else {
				throw new DbException("Erro inesperado, nenhuma linha afetada");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			
		}
	}

	@Override
	public void atualizar(Departamento obj) {

		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					 "UPDATE department "
					+"SET Name = ? "
					+"WHERE Id = ?" );
					
			st.setString(1, obj.getNome());
			st.setInt(2, obj.getId());
			
		
			st.executeUpdate();
			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			
		}
	}

	@Override
	public void excluir(Integer id) {

		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM department WHERE Id = ?");
			
			st.setInt(1, id);
			
			int rows = st.executeUpdate();
			
			if(rows == 0) {
				throw new DbException("O id inserido não consta na tabela");
			}
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Departamento buscarPorId(Integer id) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"Select * from department where Id = ?");

			st.setInt(1, id);
			rs = st.executeQuery();

			if(rs.next()) {
				
				Departamento dep = new Departamento();
				dep.setId(rs.getInt("Id"));
				dep.setNome(rs.getString("Name"));
				
				return dep;
			}
			
			return null;
		} 
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}


	@Override
	public List<Departamento> buscarTodos() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					
					"Select * from department Order By Name");

			rs = st.executeQuery();
			
			List<Departamento> list = new ArrayList<>();
			
			while(rs.next()) {
				Departamento obj = new Departamento();
				obj.setId(rs.getInt("Id"));
				obj.setNome(rs.getString("Name"));
				list.add(obj);
			}
		
			return list;
		} 
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}



}
