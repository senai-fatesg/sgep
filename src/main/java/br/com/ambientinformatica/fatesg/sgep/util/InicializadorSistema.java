package br.com.ambientinformatica.fatesg.sgep.util;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ambientinformatica.fatesg.api.entidade.Colaborador;
import br.com.ambientinformatica.fatesg.api.entidade.EnumPapelUsuario;
import br.com.ambientinformatica.fatesg.api.entidade.EnumTipoColaborador;
import br.com.ambientinformatica.fatesg.api.entidade.EnumTipoSexo;
import br.com.ambientinformatica.fatesg.sgep.persistencia.ColaboradorDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.MunicipioDao;
import br.com.ambientinformatica.util.UtilLog;

@Service("inicializadorSistema")
public class InicializadorSistema {

	@Autowired
	private ColaboradorDao colaboradorDao;
	
	@Autowired
	private MunicipioDao municipioDao;
	
	@PostConstruct
	public void iniciar(){
		inicializarUsuarioAdmin();
	}
	
	private void inicializarUsuarioAdmin(){
		try {
			List<Colaborador> colaboradores = colaboradorDao.listar();
			if(colaboradores.isEmpty()){
				Colaborador colaborador = new Colaborador();
				colaborador.setIdColaboradorPai(1);
				colaborador.setNome("admin");
				colaborador.setCpfCnpj("111.111.111-11");
				colaborador.setRg("222222222");
				colaborador.setSenhaNaoCriptografada("123456");
				colaborador.setCep("74000-000");
				colaborador.setEmail("admin@admin.com.br");
				colaborador.setTelefone("(62)3333-3333");
				colaborador.setCelular("(62)99999-9999");
				colaborador.setMunicipio(municipioDao.consultarPorCodigoIBGE(5208707));
				colaborador.setTipo(EnumTipoColaborador.ADMINISTRATIVO);
				colaborador.setTipoSexo(EnumTipoSexo.MASCULINO);
				colaborador.setTituloEleitor("12345678");
				colaborador.setHistorico("44444444");
				colaborador.setReservista("55555555");
				colaborador.addPapel(EnumPapelUsuario.ADMIN);
				colaborador.addPapel(EnumPapelUsuario.USUARIO);
				colaboradorDao.incluir(colaborador);
				
				UtilLog.getLog().info("*** USUÁRIO administrador CRIADO com a senha 123456 ***");
			}
		} catch (Exception e) {
			UtilLog.getLog().error(e.getMessage(), e);
		}
	}
	
}
