package br.treinamento;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EntidadeDAODBUnitTest  {

	private EntidadeDAOInterface dao;

	protected IDatabaseConnection getConnection()  {
		try {
			return new DatabaseConnection(ConnectionFactory.getConnection());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	protected IDataSet getDataSet() {
		try {
			return new FlatXmlDataSetBuilder().build(new FileInputStream("./test/datasets/dataset.xml"));
		} catch (DataSetException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Before
	public void setup() throws Exception {
		dao = new EntidadeDAO();
		DatabaseOperation.CLEAN_INSERT.execute(getConnection(), getDataSet());
	}
	
	@Test
	public void testConsulta()  {
		int total = 0;
		try {
			total = dao.getQuantidadeRegistros();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertEquals(5, total);
	}
	
	@Test
	public void testDeleteById() {
		Entidade entidade = new Entidade();
		entidade.setId(21l);
		try {
			dao.excluir(entidade);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			int total = dao.getQuantidadeRegistros();
			Assert.assertEquals(4, total);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testAtualizacao() {
		EntidadeDAO dao = new EntidadeDAO();
		
		Entidade entidade = null;
		try {
			entidade = dao.getById(22L);
			Assert.assertEquals("TESTE 3", entidade.getNome());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		entidade.setNome("NOVO NOME TESTE");
		
		try {
			dao.alterar(entidade);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			entidade = dao.getById(22L);
			Assert.assertEquals("NOVO NOME TESTE", entidade.getNome());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
}
