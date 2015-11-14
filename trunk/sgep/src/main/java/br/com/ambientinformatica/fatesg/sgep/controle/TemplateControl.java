package br.com.ambientinformatica.fatesg.sgep.controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.fatesg.sgep.entidade.SessaoTemplate;
import br.com.ambientinformatica.fatesg.sgep.entidade.Template;
import br.com.ambientinformatica.fatesg.sgep.persistencia.SessaoTemplateDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.TemplateDao;

@Controller("TemplateControl")
@Scope("conversation")
public class TemplateControl {

	@Autowired
	private TemplateDao templateDao;

	@Autowired
	private SessaoTemplateDao sessaoDao;

	private List<Template> templates = new ArrayList<Template>();

	private Template templateSelecionada = new Template();

	private SessaoTemplate sessaoItem = new SessaoTemplate();

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
		sessaoItem = new SessaoTemplate();
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

	public void adicionarSessao() {
		try {
			templateSelecionada.addSessao(sessaoItem);
		} catch (Exception e) {
			UtilFaces
					.addMensagemFaces("Ocorreu um erro inesperado ao adicionar a alternativa.\n"
							+ e.getMessage());
		} finally {
			sessaoItem = new SessaoTemplate();
		}
	}

	public void removerSessao() {
		try {
			this.templateSelecionada.removerSessao(sessaoItem);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Não foi possível remover a sessão.");
		} finally {
			sessaoItem = new SessaoTemplate();
		}
	}

	// Gets e Sets
	public TemplateDao getTemplateDao() {
		return templateDao;
	}

	public void setTemplateDao(TemplateDao templateDao) {
		this.templateDao = templateDao;
	}

	public SessaoTemplateDao getSessaoDao() {
		return sessaoDao;
	}

	public void setSessaoDao(SessaoTemplateDao sessaoDao) {
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

	public SessaoTemplate getSessaoItem() {
		return sessaoItem;
	}

	public void setSessaoItem(SessaoTemplate sessaoItem) {
		this.sessaoItem = sessaoItem;
	}

}
