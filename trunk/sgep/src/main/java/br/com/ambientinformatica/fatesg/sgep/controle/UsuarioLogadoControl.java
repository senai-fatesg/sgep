package br.com.ambientinformatica.fatesg.sgep.controle;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.fatesg.api.entidade.Colaborador;
import br.com.ambientinformatica.fatesg.api.entidade.EnumPapelUsuario;
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
	
	private String senha1;

	private String senha2;

	private String confirmarSenha;

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

	public boolean isAdmin(){
		return getUsuarioConfigurado().isContemPapel(EnumPapelUsuario.ADMIN);
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
			Colaborador colaborador = UsuarioLogadoControl.getUsuarioConfigurado();
			colaborador = colaboradorDao.consultarPorCpfSgep(colaborador.getCpfCnpj());
			if(!colaborador.getSenha().equals(UtilHash.gerarStringHash(confirmarSenha, Algoritimo.MD5)) 
					|| senha1.isEmpty() || senha2.isEmpty()){
				UtilFaces.addMensagemFaces("Campos não preenchidos, ou senha atual inválida ");    
			}else if(senha1.equals(senha2)){
				colaborador.setSenhaNaoCriptografada(senha1);
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

	public String getSenha1() {
		return senha1;
	}

	public void setSenha1(String senha1) {
		this.senha1 = senha1;
	}

	public String getSenha2() {
		return senha2;
	}

	public void setSenha2(String senha2) {
		this.senha2 = senha2;
	}

	public String getConfirmarSenha() {
		return confirmarSenha;
	}

	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}
	
	

}
