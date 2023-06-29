package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.VendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

public class VendedorDaoJDBC implements VendedorDao {

	// Dependencia
	private Connection conn;

	public VendedorDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void inserir(Vendedor obj) {
		
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO seller "
					+"(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+"VALUES "
					+"(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
					
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, (Date) new java.util.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartamento().getId());
		
			int rowAffected = st.executeUpdate();
			
			if(rowAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
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
	public void atualizar(Vendedor obj) {

		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					 "UPDATE seller "
					+"SET Name = ? , Email = ? , BirthDate = ?, BaseSalary = ? , DepartmentId = ? "
					+"WHERE Id = ?" );
					
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, (Date) new java.util.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartamento().getId());
			st.setInt(6, obj.getId());
		
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
			st = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");
			
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
	public Vendedor buscarPorId(Integer id) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"Select seller.*, department.Name as DepName " 
					+ "From seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id " 
					+ "WHERE seller.Id = ?");

			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				Departamento dep = instantiateDepartamento(rs);
				Vendedor seller = instantiateSeller(rs,dep);
				return seller;
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

	//Como os dados estão no banco
	private Vendedor instantiateSeller(ResultSet rs, Departamento dep) throws SQLException {
		Vendedor vendedor = new Vendedor();
		vendedor.setId(rs.getInt("Id"));
		vendedor.setName(rs.getString("Name"));
		vendedor.setEmail(rs.getString("Email"));
		vendedor.setBaseSalary(rs.getDouble("BaseSalary"));
		vendedor.setBirthDate(new java.util.Date(rs.getTimestamp("BirthDate").getTime())); 
		vendedor.setBirthDate(rs.getDate("BirthDate"));
		vendedor.setDepartamento(dep);
		return vendedor;
	}

	private Departamento instantiateDepartamento(ResultSet rs) throws SQLException {
		Departamento dep = new Departamento();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setNome(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Vendedor> buscarTodos() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"Select seller.*, department.Name as DepName " 
					+ "From seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id " 
					+ "Order By Name");

			rs = st.executeQuery();
			
			List<Vendedor> list = new ArrayList<>();
			Map<Integer, Departamento> map = new HashMap<>();
			
			while (rs.next()) {
			
				Departamento dep = map.get(rs.getInt("DepartmentId"));
				
				if(dep == null) {
					
					dep = instantiateDepartamento(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
						
				Vendedor seller = instantiateSeller(rs,dep);
				list.add(seller);
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

	@Override
	public List<Vendedor> buscarPorDepartamento(Departamento departamento) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"Select seller.*, department.Name as DepName " 
					+ "From seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id " 
					+ "WHERE DepartmentId = ? "
					+ "Order By Name ");

			st.setInt(1, departamento.getId());
			rs = st.executeQuery();
			
			List<Vendedor> list = new ArrayList<>();
			Map<Integer, Departamento> map = new HashMap<>();
			
			while (rs.next()) {
			
				Departamento dep = map.get(rs.getInt("DepartmentId"));
				
				if(dep == null) {
					
					dep = instantiateDepartamento(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
						
				Vendedor seller = instantiateSeller(rs,dep);
				list.add(seller);
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
