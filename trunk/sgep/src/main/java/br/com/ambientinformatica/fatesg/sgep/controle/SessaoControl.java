package br.com.ambientinformatica.fatesg.sgep.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
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

	private List<SessaoTemplate> sessoes = new ArrayList<SessaoTemplate>();
	
	private List<AlternativaQuestao> alternativas = new ArrayList<>();

	private SessaoTemplate sessao = new SessaoTemplate();

	private SessaoTemplate sessaoSelecionada = new SessaoTemplate();
	
	private List<ItemQuestaoTemplate> itensQuestao = new ArrayList<>();
	
	private List<QuestaoTemplate> questoesTemplate = new ArrayList<>();
	
	private List<QuestaoTemplate> questoes = new ArrayList<>();
	
	private QuestaoTemplate questaoTemplate = new QuestaoTemplate();
	
	@Autowired
	private SessaoTemplateDao sessaoDao;
	
	@Autowired
	private QuestaoTemplateDao questaoTemplateDao;

	@PostConstruct
	public void init() {
		listar();
		listarQuestoes();
	}

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

	public void confirmar(ActionEvent evt) {
		sessaoSelecionada = (SessaoTemplate) UtilFaces.getValorParametro(evt, "sessaoSelecionada");
		try {
			sessaoDao.alterar(sessaoSelecionada);
			limpar();
			listar();
			UtilFaces.addMensagemFaces("Sessão incluída com sucesso.");
		} catch (PersistenciaException e) {
			UtilFaces.addMensagemFaces(e.getMessage());
		}
	}

	public void listar() {
		try {
			sessoes = sessaoDao.listar();
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
	
	public void adicionarItemQuestao(){
		sessao.addItemQuestao(questaoTemplate);
		questaoTemplate = new QuestaoTemplate();
	}
	
	public void removerItemQuestao(ItemQuestaoTemplate item){
		sessao.removeQuestao(item);
	}
	
	public void novaSessao(){
		sessaoSelecionada = new SessaoTemplate();
		RequestContext context = RequestContext.getCurrentInstance(); 
		context.execute("PF('vCadSessao').show();");	
	}

	public void editarSessao(ActionEvent evt) {
		sessaoSelecionada = (SessaoTemplate) UtilFaces.getValorParametro(evt,
				"sesEditar");
	}
	
	public void selecionarQuestao(QuestaoTemplate questaoTemplate){
		this.questaoTemplate = questaoTemplate;
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

	public List<QuestaoTemplate> getQuestoesTemplate() {
		return questoesTemplate;
	}

	public void setQuestoesTemplate(List<QuestaoTemplate> questoesTemplate) {
		this.questoesTemplate = questoesTemplate;
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
	

}
