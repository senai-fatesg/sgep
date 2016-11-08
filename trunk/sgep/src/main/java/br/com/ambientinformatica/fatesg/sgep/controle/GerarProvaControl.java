package br.com.ambientinformatica.fatesg.sgep.controle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.fatesg.sgep.entidade.ItemQuestaoTemplate;
import br.com.ambientinformatica.fatesg.sgep.entidade.Prova;
import br.com.ambientinformatica.fatesg.sgep.entidade.Questao;
import br.com.ambientinformatica.fatesg.sgep.entidade.QuestaoTemplate;
import br.com.ambientinformatica.fatesg.sgep.entidade.SessaoProva;
import br.com.ambientinformatica.fatesg.sgep.entidade.SessaoTemplate;
import br.com.ambientinformatica.fatesg.sgep.entidade.Template;
import br.com.ambientinformatica.fatesg.sgep.persistencia.AlunoDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.ColaboradorDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.CursoDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.DisciplinaDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.InstituicaoDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.ProvaDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.QuestaoTemplateDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.SessaoProvaDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.SessaoTemplateDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.TemplateDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.UnidadeEnsinoDao;

@Controller("GerarProvaControl")
@Scope("conversation")
public class GerarProvaControl {
	
	private Prova prova = new Prova();

	private Prova provaSelecionada = new Prova();

	private Prova provaAlterar = new Prova();

	private ItemQuestaoTemplate itensProva = new ItemQuestaoTemplate();

	private Template template = new Template();

	private String pesquisa = new String();

	private String tipoPesquisa = new String();

	/**
	 * Lista todas as provaas cadastradas
	 */
	private List<Prova> provas = new ArrayList<Prova>();
	/**
	 * Lista as questões disponíveis para adicionar na prova
	 */
	private List<QuestaoTemplate> questoes = new ArrayList<QuestaoTemplate>();
	/**
	 * Lista as sessões disponíveis para adicionar na prova
	 */
	private List<SessaoTemplate> sessoes = new ArrayList<>();
	/**
	 * Sessao para apresentaçãoo das questoes
	 */
	private SessaoProva sessaoSelecionada = new SessaoProva();
	/**
	 * Vizualização da questão no dialog
	 */
	private QuestaoTemplate questaoSelecionada = new QuestaoTemplate();

	@Autowired
	private QuestaoTemplateDao questaoTemplateDao;

	@Autowired
	private ProvaDao provaDao;

	@Autowired
	private TemplateDao templateDao;

	@Autowired
	private ColaboradorDao colaboradorDao;

	@Autowired
	private InstituicaoDao instituicaoDao;

	@Autowired
	private CursoDao cursoDao;

	@Autowired
	private SessaoProvaDao sessaoProvaDao;

	@Autowired
	private SessaoTemplateDao sessaoTemplateDao;

	@Autowired
	private DisciplinaDao disciplinaDao;

	@Autowired
	private AlunoDao alunoDao;

	@Autowired
	private UnidadeEnsinoDao unidadeEnsinoDao;
	
	
	@PostConstruct
	public void init(){
		provas = listarProvas();
		questoes = listarQuestoes();
	}
	
	
	public List<Prova> listarProvas(){
		try {
			return provaDao.listar();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e.getMessage());
		}
		return Collections.emptyList();
	}
	
	public List<QuestaoTemplate> listarQuestoes(){
		try {
			return questaoTemplateDao.listarQuestoes();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e.getMessage());
		}
		return Collections.emptyList();
	}

	public Prova getProva() {
		return prova;
	}
	
	public void selecionarProva(Prova provaSelecionada){
		this.provaSelecionada = provaSelecionada;
	}

	public void setProva(Prova prova) {
		this.prova = prova;
	}

	public Prova getProvaSelecionada() {
		return provaSelecionada;
	}

	public void setProvaSelecionada(Prova provaSelecionada) {
		this.provaSelecionada = provaSelecionada;
	}

	public Prova getProvaAlterar() {
		return provaAlterar;
	}

	public void setProvaAlterar(Prova provaAlterar) {
		this.provaAlterar = provaAlterar;
	}

	public ItemQuestaoTemplate getItensProva() {
		return itensProva;
	}

	public void setItensProva(ItemQuestaoTemplate itensProva) {
		this.itensProva = itensProva;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public String getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}

	public String getTipoPesquisa() {
		return tipoPesquisa;
	}

	public void setTipoPesquisa(String tipoPesquisa) {
		this.tipoPesquisa = tipoPesquisa;
	}

	public List<Prova> getProvas() {
		return provas;
	}

	public void setProvas(List<Prova> provas) {
		this.provas = provas;
	}

	public List<QuestaoTemplate> getQuestoes() {
		return questoes;
	}

	public void setQuestoes(List<QuestaoTemplate> questoes) {
		this.questoes = questoes;
	}

	public List<SessaoTemplate> getSessoes() {
		return sessoes;
	}

	public void setSessoes(List<SessaoTemplate> sessoes) {
		this.sessoes = sessoes;
	}

	public SessaoProva getSessaoSelecionada() {
		return sessaoSelecionada;
	}

	public void setSessaoSelecionada(SessaoProva sessaoSelecionada) {
		this.sessaoSelecionada = sessaoSelecionada;
	}

	public QuestaoTemplate getQuestaoSelecionada() {
		return questaoSelecionada;
	}

	public void setQuestaoSelecionada(QuestaoTemplate questaoSelecionada) {
		this.questaoSelecionada = questaoSelecionada;
	}
	

}
