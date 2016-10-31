package br.com.ambientinformatica.fatesg.sgep.controle;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.fatesg.api.entidade.Colaborador;
import br.com.ambientinformatica.fatesg.sgep.persistencia.ColaboradorDao;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.util.UtilHash;
import br.com.ambientinformatica.util.UtilHash.Algoritimo;
import br.com.ambientinformatica.util.UtilLog;

@Controller("UsuarioLogadoControl")
@Scope("session")
public class UsuarioLogadoControl implements Serializable{

	private static final long serialVersionUID = 1L;

	private Colaborador colaborador = new Colaborador();

	private String senhaAlteracao;

	private String senhaAlteracaoNovamente;

	private String senhaAtual;

	@Autowired
	private ColaboradorDao colaboradorDao;

	@PostConstruct
	public void init() {
		try {
			consultarUsuarioLogado();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	private void consultarUsuarioLogado(){
		try {
			HttpServletRequest req = UtilFaces.getRequest();
			colaborador = colaboradorDao.consultarPorCpf(req.getUserPrincipal().getName());
		} catch(Exception e){
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void alterarSenhaDoUsuario(){
		try {
			if(!colaborador.getSenha().equals(UtilHash.gerarStringHash(senhaAtual, Algoritimo.MD5)) 
					|| senhaAlteracao.isEmpty() || senhaAlteracaoNovamente.isEmpty()){
				UtilFaces.addMensagemFaces("Campos não preenchidos, ou senha atual inválida ");    
			}else if(senhaAlteracao.equals(senhaAlteracaoNovamente)){
				colaborador.setSenhaNaoCriptografada(senhaAlteracao);
				colaboradorDao.alterar(colaborador);
				UtilFaces.addMensagemFaces("Senha alterada com sucesso ");
			}else{
				UtilFaces.addMensagemFaces("As senhas digitadas não conferem, digite novamente");
			}
		} catch (PersistenciaException e) {
			UtilLog.getLog().error(e.getMessage(), e);
			UtilFaces.addMensagemFaces("A senha não foi alterada");
		}
	}

	public static Colaborador getUsuarioConfigurado() {
		return (Colaborador) UtilFaces.getObjetoManagedBean("#{UsuarioLogadoControl.colaborador}");
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

}
