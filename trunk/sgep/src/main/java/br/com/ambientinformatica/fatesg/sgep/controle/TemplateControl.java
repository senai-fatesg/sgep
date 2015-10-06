package br.com.ambientinformatica.fatesg.sgep.controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.fatesg.sgep.entidade.Sessao;
import br.com.ambientinformatica.fatesg.sgep.entidade.Template;
import br.com.ambientinformatica.fatesg.sgep.persistencia.SessaoDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.TemplateDao;

@Controller("TemplateControl")
public class TemplateControl {

	@Autowired
	private TemplateDao templateDao;

	@Autowired
	private SessaoDao sessaoDao;

	private List<Template> templates = new ArrayList<Template>();

	private Template templateSelecionada = new Template();

	private Sessao sessaoItem = new Sessao();

	@PostConstruct
	public void init() {
		listar(null);
	}

	public void confirmar(ActionEvent evt) {
		try {
			templateDao.alterar(templateSelecionada);
			listar(evt);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		} finally {
			limpar();
		}
	}

	public void listar(ActionEvent evt) {
		try {
			templates = templateDao.listar();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void excluir() {
		try {
			templateDao.excluirPorId(templateSelecionada.getId());
			templateSelecionada = new Template();
			templates = templateDao.listar();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void limpar() {
		templateSelecionada = new Template();
		sessaoItem = new Sessao();
	}

	@SuppressWarnings("finally")
	public List<Sessao> completarSessao(String titulo) {
		List<Sessao> listaSessoes = new ArrayList<Sessao>();
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

	public void adicionarSessao() {
		try {
			templateSelecionada.addSessao(sessaoItem);
		} catch (Exception e) {
			UtilFaces
					.addMensagemFaces("Ocorreu um erro inesperado ao adicionar a alternativa.\n"
							+ e.getMessage());
		} finally {
			sessaoItem = new Sessao();
		}
	}

	public void removerSessao() {
		try {
			this.templateSelecionada.removerSessao(sessaoItem);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Não foi possível remover a sessão.");
		} finally {
			sessaoItem = new Sessao();
		}
	}

	// Gets e Sets
	public TemplateDao getTemplateDao() {
		return templateDao;
	}

	public void setTemplateDao(TemplateDao templateDao) {
		this.templateDao = templateDao;
	}

	public SessaoDao getSessaoDao() {
		return sessaoDao;
	}

	public void setSessaoDao(SessaoDao sessaoDao) {
		this.sessaoDao = sessaoDao;
	}

	public List<Template> getTemplates() {
		return templates;
	}

	public void setTemplates(List<Template> templates) {
		this.templates = templates;
	}

	public Template getTemplateSelecionada() {
		return templateSelecionada;
	}

	public void setTemplateSelecionada(Template template) {
		this.templateSelecionada = template;
	}

	public Sessao getSessaoItem() {
		return sessaoItem;
	}

	public void setSessaoItem(Sessao sessaoItem) {
		this.sessaoItem = sessaoItem;
	}

}
