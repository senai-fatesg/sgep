package br.com.ambientinformatica.fatesg.sgep.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.fatesg.sgep.entidade.Sessao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.SessaoDao;

@Controller("SessaoControl")
@Scope("conversation")
public class SessaoControl implements Serializable {
	/**
	 * author Glaicon Reis
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private SessaoDao sessaoDao;

	private List<Sessao> sessoes = new ArrayList<Sessao>();

	private Sessao sessao = new Sessao();

	@PostConstruct
	public void init() {
		listar(null);
	}

	public void confirmar(ActionEvent evt) {
		try {
			sessaoDao.alterar(sessao);
			listar(evt);
			sessao = new Sessao();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void listar(ActionEvent evt) {
		try {
			sessoes = sessaoDao.listar();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void excluir() {
		try {
			sessaoDao.excluirPorId(sessao.getId());
			sessao = new Sessao();
			sessoes = sessaoDao.listar();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public List<Sessao> completarSessao(String titulo){
		List<Sessao> listaSessoes = sessaoDao.consultarPeloTitulo(titulo);
		if (listaSessoes.size() == 0) {
			UtilFaces.addMensagemFaces("Sessão não encontrada");
		}
		return listaSessoes;
	}
	
	public void limpar() {
		sessao = new Sessao();
	}

	public SessaoDao getSessaoDao() {
		return sessaoDao;
	}

	public void setSessaoDao(SessaoDao sessaoDao) {
		this.sessaoDao = sessaoDao;
	}

	public List<Sessao> getSessoes() {
		return sessoes;
	}

	public void setSessoes(List<Sessao> sessoes) {
		this.sessoes = sessoes;
	}

	public Sessao getSessao() {
		return sessao;
	}

	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}
	

}
