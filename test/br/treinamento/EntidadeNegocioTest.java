package br.treinamento;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EntidadeNegocioTest {

	private static Entidade entidade;
	private static EntidadeNegocio entidadeNegocio;
	private static EntidadeDAOInterface persistencia;

	@BeforeClass
	public static void setUp() {
		entidade = new Entidade();
		entidadeNegocio = new EntidadeNegocio();
		persistencia = EasyMock.createMock(EntidadeDAOInterface.class);
	}

	@Before
	public void methodSetup() {
		EasyMock.reset(persistencia);
	}

	@Test
	public void deveSalvarEntidade() {
		entidade.setNome("nomeOk");
		entidade.setNumeroDocumento(12345l);
		entidade.setTipoDocumento(2);

		entidadeNegocio.setPersistencia(persistencia);

		try {
			EasyMock.expect(persistencia.verificarUnicidadeNome(entidade)).andReturn(true);
			EasyMock.replay(persistencia);
			entidadeNegocio.salvar(entidade);
		} catch (Exception e) {
			fail("Já existe entidade cadastrada com este nome.");
		}

		EasyMock.verify(persistencia);
	}

}
