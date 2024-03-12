package dao;

// dependencias
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Jogador;

public class JogadorDAO extends DAO 
{	
	public JogadorDAO( ) 
	{
		super( );
		conectar( );
	} // end jogadorDAO ( )

	public void finalize( ) 
	{
		close( );
	} // end finalize
	
	public boolean insert( Jogador jogador ) 
	{
		boolean status = false;
		try 
		{  
			Statement st = conexao.createStatement();
			String sql = "INSERT INTO jogador (login, senha, sexo) "
				       + "VALUES ('" + jogador.getLogin() + "', '" + jogador.getSenha() + "', '" + jogador.getSexo() + "');";
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch ( SQLException u ) 
		{  
			throw new RuntimeException(u);
		} // end try catch
		return ( status );
	} // end insert ( )

	public Jogador get( int codigo ) 
	{
		Jogador jogador = null;
		
		try 
		{
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM jogador WHERE codigo=" + codigo;
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 jogador = new Jogador(rs.getInt("codigo"), rs.getString("login"), rs.getString("senha"), rs.getString("sexo").charAt(0));
	        }
	        st.close();
		} catch (Exception e) 
		{
			System.err.println(e.getMessage());
		} // end try catch 
		return ( jogador );
	} // end get ( )
	
	public List<Jogador> get( ) 
	{
		return get("");
	} // end get ( )

	public List<Jogador> getOrderByCodigo( ) 
	{
		return get("codigo");		
	} // end getOrderByCodigo ( )
	
	public List<Jogador> getOrderByLogin( ) 
	{
		return get("login");		
	} // end getOrderByLogin ( )
	
	public List<Jogador> getOrderBySexo( ) 
	{
		return get("sexo");		
	} // end getOrderBySexo ( )
	
	private List<Jogador> get( String orderBy ) 
	{	
		List<Jogador> jogadores = new ArrayList<Jogador>();
		
		try 
		{
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM jogador" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	           
	        while( rs.next( ) ) 
			{	            	
	        	Jogador u = new Jogador(rs.getInt("codigo"), rs.getString("login"), rs.getString("senha"), rs.getString("sexo").charAt(0));
	            jogadores.add(u);
	        } // end whiile
	        st.close();
		} catch ( Exception e ) 
		{
			System.err.println(e.getMessage());
		} // end try catch
		return ( jogadores );
	} // end get ( )

	public List<Jogador> getSexoMasculino( ) 
	{
		List<Jogador> jogadores = new ArrayList<Jogador>();
		
		try 
		{
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM jogador WHERE jogador.sexo LIKE 'M'";
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	           
	        while( rs.next( ) ) 
			{	            	
	        	Jogador u = new Jogador( rs.getInt("codigo"), rs.getString("login"), 
							rs.getString("senha"), rs.getString("sexo").charAt(0) );
	            jogadores.add(u);
	        } // end while
	        st.close();
		} catch ( Exception e ) 
		{
			System.err.println(e.getMessage());
		} // end try catch
		return ( jogadores );
	} // end getSexoMasculino ( )
	
	public boolean update( Jogador jogador ) 
	{
		boolean status = false;
		try 
		{  
			Statement st = conexao.createStatement();
			String sql = "UPDATE jogador SET login = '" + jogador.getLogin() + "', senha = '"  
				       + jogador.getSenha() + "', sexo = '" + jogador.getSexo() + "'"
					   + " WHERE codigo = " + jogador.getCodigo();
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch ( SQLException u ) 
		{  
			throw new RuntimeException(u);
		} // end try catch
		return status;
	} // end update ( )
	
	public boolean delete( int codigo ) 
	{
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "DELETE FROM jogador WHERE codigo = " + codigo;
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) 
		{  
			throw new RuntimeException(u);
		} // end try catch 
		return ( status );
	} // end delete ( )
	
	public boolean autenticar( String login, String senha ) 
	{
		boolean resp = false;
		
		try 
		{
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM jogador WHERE login LIKE '" + login + "' AND senha LIKE '" + senha  + "'";
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);
			resp = rs.next();
	        st.close();
		} catch ( Exception e ) 
		{
			System.err.println(e.getMessage());
		} // end try catch 
		return resp;
	} // end autenticar ( )

} // end class