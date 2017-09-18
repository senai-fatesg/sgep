package br.com.ambientinformatica.fatesg.sgep.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.ambientjsf.util.UtilFacesRelatorio;
import br.com.ambientinformatica.fatesg.sgep.entidade.AlternativaQuestao;
import br.com.ambientinformatica.fatesg.sgep.entidade.ItemQuestaoTemplate;
import br.com.ambientinformatica.fatesg.sgep.entidade.QuestaoTemplate;
import br.com.ambientinformatica.fatesg.sgep.entidade.SessaoTemplate;
import br.com.ambientinformatica.fatesg.sgep.persistencia.QuestaoTemplateDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.SessaoTemplateDao;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.util.UtilException;

@Controller("SessaoControl")
@Scope("conversation")
public class SessaoControl implements Serializable {

	private static final long serialVersionUID = 1L;

	private String enunciado;

	private String titulo;
	
	private String filtroQuestaoProfessorOuDisciplina;

	private List<SessaoTemplate> sessoes = new ArrayList<SessaoTemplate>();

	private List<AlternativaQuestao> alternativas = new ArrayList<>();

	private SessaoTemplate sessaoTemplate = new SessaoTemplate();

	private List<ItemQuestaoTemplate> itensQuestao = new ArrayList<>();

	private List<QuestaoTemplate> questoes = new ArrayList<>();

	private QuestaoTemplate questaoTemplate = new QuestaoTemplate();

	@Autowired
	private SessaoTemplateDao sessaoDao;
	
	@Autowired
	private QuestaoTemplateDao questaoDao;

	@Autowired
	private QuestaoTemplateDao questaoTemplateDao;

	public void imprimir(ActionEvent evt) {
		SessaoTemplate sessaoImprimir = (SessaoTemplate) UtilFaces
				.getValorParametro(evt, "sesImprimir");

		Map<String, Object> parametros = new HashMap<String, Object>();
		// parametros.put("nomeInstituicao",
		// EmpresaLogadaControl.getEmpresa().getNome());
		// parametros.put("valorTotal", 55.5);
		List<SessaoTemplate> sessoesTeste = new ArrayList<SessaoTemplate>();
		sessoesTeste.add(sessaoImprimir);
		try {
			UtilFacesRelatorio.gerarRelatorioFaces("jasper/sessao.jasper",
					sessoesTeste, parametros);
		} catch (UtilException e) {
			UtilFaces.addMensagemFaces("Houve um erro ao gerar o Relatório: "
					+ e);
		}
	}

	public void alterarListasDeQuestoesSelecionadas(ActionEvent evt){
		listarQuestoes();
		for(int i = 0; i < sessaoTemplate.getItensQuestao().size(); i++){
			for(int j = 0; j < questoes.size(); j++){
				if(sessaoTemplate.getItensQuestao().get(i).getQuestaoTemplate().equals(questoes.get(j))){
					questoes.remove(j);
				}
			}
		}	
	}
	
	public void pesquisarQuestoes() {
		try {
			questoes = questaoDao.listarPorProfessorOuDisciplina(filtroQuestaoProfessorOuDisciplina);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
			questoes  = new ArrayList<QuestaoTemplate>();
		}
	}
	
	public void confirmar() {
		try {
			if (sessaoTemplate.getSessao().getTitulo() != null && !sessaoTemplate.getSessao().getTitulo().isEmpty() 
					&& sessaoTemplate.getSessao().getDescricao() != null 
					&& !sessaoTemplate.getSessao().getDescricao().isEmpty()) {
				sessaoDao.alterar(sessaoTemplate);
				sessaoTemplate = new SessaoTemplate();
				UtilFaces.addMensagemFaces("Sessão incluída com sucesso.");
			}else {
				UtilFaces.addMensagemFaces("Os campos título e descrição devem ser preenchidos.", FacesMessage.SEVERITY_WARN);
			}
		} catch (PersistenciaException e) {
			UtilFaces.addMensagemFaces(e.getMessage());
		}
	}

	public void pesquisarPorTitulo(){
		try {
			sessoes = sessaoDao.pesquisarPorTituloOuDescricao(titulo);
			sessaoTemplate.getItensQuestao();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public List<QuestaoTemplate> listarQuestoes(){
		try {
			return questoes = questaoTemplateDao.listarQuestoes();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e.getMessage());
		}
		return null;
	}


	public void excluir() {
		try {
			sessaoDao.excluirPorId(sessaoTemplate.getId());
			sessaoTemplate = new SessaoTemplate();
			sessoes = sessaoDao.listar();
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("/sgep/sessao.jsf");
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

	public QuestaoTemplate consultarAlternativasQuestao(QuestaoTemplate questao){
		try {
			if (questao != null) {
				QuestaoTemplate questaoTemp = questaoTemplateDao.consultarAlternativasQuestao(questao);
				alternativas = new ArrayList<>();
				for (AlternativaQuestao alternativaQuestao : questaoTemp.getQuestao().getAlternativas()) {
					alternativas.add(alternativaQuestao);
				}
				RequestContext.getCurrentInstance().execute("PF('dlgAlternativa').show();");
			}
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e.getMessage());
		}
		return null;
	}

	public QuestaoTemplate consultarEnunciadoQuestao(QuestaoTemplate questao){
		try {
			if (questao != null) {
				QuestaoTemplate questaoTemp = questaoTemplateDao.consultarEnunciadoQuestao(questao);
				enunciado = questaoTemp.getQuestao().getEnunciado();
				RequestContext.getCurrentInstance().execute("PF('dlgEnunciado').show();");
			}
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e.getMessage());
		}
		return null;
	}

	public void adicionarItemQuestao(QuestaoTemplate questaoTemplate){
		if (questaoTemplate.getQuestao().getId() != null) {
			try {
				sessaoTemplate.addItemQuestao(questaoTemplate);
			} catch (Exception e) {
				UtilFaces.addMensagemFaces(e.getMessage(), FacesMessage.SEVERITY_WARN);
			}
			questaoTemplate = new QuestaoTemplate();
		}else{
			UtilFaces.addMensagemFaces("Selecione uma questão para adicionar!", FacesMessage.SEVERITY_ERROR);
		}
	}

	public void removerItemQuestao(ItemQuestaoTemplate item){
		sessaoTemplate.removeQuestao(item);
	}

	public void selecionarQuestao(QuestaoTemplate questaoTemplate){
		this.questaoTemplate = questaoTemplate;
	}

	public void limpar() {
		sessaoTemplate = new SessaoTemplate();
		questaoTemplate =  new QuestaoTemplate();
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

	public SessaoTemplate getSessaoTemplate() {
		return sessaoTemplate;
	}

	public void setSessao(SessaoTemplate sessaoTemplate) {
		this.sessaoTemplate = sessaoTemplate;
	}

	public String getEnunciado() {
		return enunciado;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}

	public List<AlternativaQuestao> getAlternativas() {
		return alternativas;
	}

	public void setAlternativas(List<AlternativaQuestao> alternativas) {
		this.alternativas = alternativas;
	}

	public List<ItemQuestaoTemplate> getItensQuestao() {
		return itensQuestao;
	}

	public void setItensQuestao(List<ItemQuestaoTemplate> itensQuestao) {
		this.itensQuestao = itensQuestao;
	}

	public QuestaoTemplate getQuestaoTemplate() {
		return questaoTemplate;
	}

	public void setQuestaoTemplate(QuestaoTemplate questaoTemplate) {
		this.questaoTemplate = questaoTemplate;
	}

	public List<QuestaoTemplate> getQuestoes() {
		return questoes;
	}

	public void setQuestoes(List<QuestaoTemplate> questoes) {
		this.questoes = questoes;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getFiltroQuestaoProfessorOuDisciplina() {
		return filtroQuestaoProfessorOuDisciplina;
	}

	public void setFiltroQuestaoProfessorOuDisciplina(String filtroQuestaoProfessorOuDisciplina) {
		this.filtroQuestaoProfessorOuDisciplina = filtroQuestaoProfessorOuDisciplina;
	}


}
