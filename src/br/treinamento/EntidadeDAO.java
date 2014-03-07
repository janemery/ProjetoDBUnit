package br.treinamento;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class EntidadeDAO implements EntidadeDAOInterface {
	
	public Entidade alterar(Entidade entidade) throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("UPDATE entidade SET nome = ?, numero_Documento = ?, tipo_Documento = ?, data_inicial = ?, data_final = ?, email = ?, data_gravacao = ? where id = ?");
		PreparedStatement stmt = ConnectionFactory.getConnection().prepareStatement(builder.toString());
		int count = 1;
		stmt.setString(count++, entidade.getNome());
		if(entidade.getNumeroDocumento() != null) {
			stmt.setLong(count++, entidade.getNumeroDocumento());
		} else {
			stmt.setNull(count++, Types.BIGINT);
		}
		if(entidade.getTipoDocumento() != null) {
			stmt.setInt(count++, entidade.getTipoDocumento());
		} else {
			stmt.setNull(count++, Types.INTEGER);
		}
		if(entidade.getDataInicial() != null){
			stmt.setDate(count++, new Date(entidade.getDataInicial().getTime()));
		} else {
			stmt.setNull(count++, Types.DATE);
		}
		if(entidade.getDataFinal() != null){
			stmt.setDate(count++, new Date(entidade.getDataFinal().getTime()));
		} else {
			stmt.setNull(count++, Types.DATE);
		}
		if(entidade.getEmail() != null){
			stmt.setString(count++, entidade.getEmail());
		} else {
			stmt.setNull(count++, Types.VARCHAR);
		}
		stmt.setDate(count++, new Date(new java.util.Date().getTime()));
		stmt.setLong(count++, entidade.getId());
		stmt.executeUpdate();
		return entidade;
	}

	public void excluir(Entidade entidade) throws SQLException, Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("DELETE FROM entidade WHERE ID = ?");
		PreparedStatement stmt = ConnectionFactory.getConnection().prepareStatement(builder.toString());
		stmt.setLong(1, entidade.getId());
		stmt.executeUpdate();
	}

	public Entidade getById(Long id) throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT * FROM entidade WHERE ID = ?");
		PreparedStatement stmt = ConnectionFactory.getConnection().prepareStatement(builder.toString());
		stmt.setLong(1, id);
		ResultSet rs = stmt.executeQuery();
		if(!rs.next())
			return null;
		Entidade entidade = new Entidade();
		entidade.setId(rs.getLong("id"));
		entidade.setNome(rs.getString("nome"));
		entidade.setNumeroDocumento(rs.getLong("numero_documento"));
		entidade.setTipoDocumento(rs.getInt("tipo_documento"));
		entidade.setDataInicial(rs.getDate("data_inicial"));
		entidade.setDataFinal(rs.getDate("data_final"));
		entidade.setDataGravacao(rs.getDate("data_gravacao"));
		entidade.setEmail(rs.getString("email"));
		return entidade;
	}

	public int getQuantidadeRegistros() throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT COUNT(*) FROM entidade");
		PreparedStatement stmt = ConnectionFactory.getConnection().prepareStatement(builder.toString());
		ResultSet rs = stmt.executeQuery();
		rs.next();
		return rs.getInt(1);
	}

	public Entidade salvar(Entidade entidade) throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("INSERT INTO entidade VALUES (null, ?, ?, ?, ?, ?, ?, ?)");
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement stmt = conn.prepareStatement(builder.toString());
		int count = 1;
		stmt.setString(count++, entidade.getNome());
		if(entidade.getDataInicial() != null){
			stmt.setDate(count++, new Date(entidade.getDataInicial().getTime()));
		} else {
			stmt.setNull(count++, Types.DATE);
		}
		if(entidade.getDataFinal() != null){
			stmt.setDate(count++, new Date(entidade.getDataFinal().getTime()));
		} else {
			stmt.setNull(count++, Types.DATE);
		}
		if(entidade.getNumeroDocumento() != null) {
			stmt.setLong(count++, entidade.getNumeroDocumento());
		} else {
			stmt.setNull(count++, Types.BIGINT);
		}
		if(entidade.getTipoDocumento() != null) {
			stmt.setInt(count++, entidade.getTipoDocumento());
		} else {
			stmt.setNull(count++, Types.INTEGER);
		}
		if(entidade.getEmail() != null){
			stmt.setString(count++, entidade.getEmail());
		} else {
			stmt.setNull(count++, Types.VARCHAR);
		}
		stmt.setDate(count++, new Date(new java.util.Date().getTime()));
		stmt.executeUpdate();
		
		builder = new StringBuilder();
		builder.append("SELECT id FROM entidade WHERE nome = ?");
		stmt = ConnectionFactory.getConnection().prepareStatement(builder.toString());
		stmt.setString(1, entidade.getNome());
		ResultSet rs = stmt.executeQuery();
		rs.next();
		entidade.setId(rs.getLong(1));
		return entidade;
	}
	
	public List<Entidade> retrieveAll() throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT * FROM entidade");
		PreparedStatement stmt = ConnectionFactory.getConnection().prepareStatement(builder.toString());
		ResultSet rs = stmt.executeQuery();
		List<Entidade> lista = new ArrayList<Entidade>();
		while(rs.next()){
			Entidade entidade = new Entidade();
			entidade.setId(rs.getLong("id"));
			entidade.setNome(rs.getString("nome"));
			entidade.setNumeroDocumento(rs.getLong("numero_documento"));
			entidade.setTipoDocumento(rs.getInt("tipo_documento"));
			entidade.setDataInicial(rs.getDate("data_inicial"));
			entidade.setDataFinal(rs.getDate("data_final"));
			entidade.setDataGravacao(rs.getDate("data_gravacao"));
			entidade.setEmail(rs.getString("email"));
			lista.add(entidade);
		}
		return lista;
	}
	
	@Override
	public boolean verificarUnicidadeNome(Entidade entidade) throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT * FROM entidade WHERE nome like ?");
		PreparedStatement stmt = ConnectionFactory.getConnection().prepareStatement(builder.toString());
		stmt.setString(1, entidade.getNome());
		ResultSet rs = stmt.executeQuery();
		new java.util.Date(new Date(12312L).getTime());
		return rs.next();
	}
}