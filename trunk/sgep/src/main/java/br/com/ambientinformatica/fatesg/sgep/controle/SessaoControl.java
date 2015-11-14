package br.com.ambientinformatica.fatesg.sgep.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.ambientjsf.util.UtilFacesRelatorio;
import br.com.ambientinformatica.fatesg.sgep.entidade.SessaoTemplate;
import br.com.ambientinformatica.fatesg.sgep.persistencia.SessaoTemplateDao;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.util.UtilException;

@Controller("SessaoControl")
@Scope("conversation")
public class SessaoControl implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SessaoTemplateDao sessaoDao;

	private List<SessaoTemplate> sessoes = new ArrayList<SessaoTemplate>();

	private SessaoTemplate sessao = new SessaoTemplate();

	private SessaoTemplate sessaoSelecionada = new SessaoTemplate();

	@PostConstruct
	public void init() {
		listar(null);
	}

	public void imprimir(ActionEvent evt){
		SessaoTemplate sessaoImprimir = (SessaoTemplate) UtilFaces.getValorParametro(evt, "sesImprimir");
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		//		parametros.put("nomeInstituicao", EmpresaLogadaControl.getEmpresa().getNome());
		//		parametros.put("valorTotal", 55.5);
		List<SessaoTemplate> sessoesTeste = new ArrayList<SessaoTemplate>();
		sessoesTeste.add(sessaoImprimir);
			try {
				UtilFacesRelatorio.gerarRelatorioFaces("jasper/sessao.jasper", sessoesTeste, parametros);
			} catch (UtilException e) {
			UtilFaces.addMensagemFaces("Houve um erro ao gerar o Relatório: "+ e);
			}
	}
	
	public void confirmar(ActionEvent evt) {
		try {
			sessaoDao.alterar(sessao);
			listar(evt);
			sessao = new SessaoTemplate();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void alterarSessaoSelecionada(ActionEvent evt) {
		sessaoSelecionada = (SessaoTemplate) UtilFaces.getValorParametro(evt, "sessaoSelecionada");
		try {
			sessaoDao.alterar(sessaoSelecionada);
			listar(evt);
			sessaoSelecionada = new SessaoTemplate();
		} catch (PersistenciaException e) {
			UtilFaces
					.addMensagemFaces("Houve um erro ao alterar a Sessão Selecionada.");
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
			sessaoDao.excluirPorId(sessaoSelecionada.getId());
			sessaoSelecionada = new SessaoTemplate();
			sessoes = sessaoDao.listar();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	@SuppressWarnings("finally")
	public List<SessaoTemplate> completarSessao(String titulo) {
		List<SessaoTemplate> listaSessoes = new ArrayList<SessaoTemplate>();
		try {
			listaSessoes = sessaoDao.consultarPeloTitulo(titulo);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Erro ao completar a Sessão");
		} finally {
			if (listaSessoes.size() == 0) {
				UtilFaces.addMensagemFaces("Sessão não encontrada");
			}
			return listaSessoes;
		}
	}
	
	public void editarSessao(ActionEvent evt) {
		sessaoSelecionada = (SessaoTemplate) UtilFaces.getValorParametro(evt, "sesEditar");
	}

	public void limpar() {
		sessaoSelecionada = new SessaoTemplate();
	}

	public SessaoTemplateDao getSessaoDao() {
		return sessaoDao;
	}

	public void setSessaoDao(SessaoTemplateDao sessaoDao) {
		this.sessaoDao = sessaoDao;
	}

	public List<SessaoTemplate> getSessoes() {
		return sessoes;
	}

	public void setSessoes(List<SessaoTemplate> sessoes) {
		this.sessoes = sessoes;
	}

	public SessaoTemplate getSessao() {
		return sessao;
	}

	public void setSessao(SessaoTemplate sessao) {
		this.sessao = sessao;
	}

	public SessaoTemplate getSessaoSelecionada() {
		return sessaoSelecionada;
	}

	public void setSessaoSelecionada(SessaoTemplate sessaoSelecionada) {
		this.sessaoSelecionada = sessaoSelecionada;
	}

}
