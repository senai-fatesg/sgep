package br.com.ambientinformatica.fatesg.sgep.controle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.ambientjsf.util.UtilFacesRelatorio;
import br.com.ambientinformatica.fatesg.api.entidade.Aluno;
import br.com.ambientinformatica.fatesg.api.entidade.Curso;
import br.com.ambientinformatica.fatesg.api.entidade.Disciplina;
import br.com.ambientinformatica.fatesg.api.entidade.Instituicao;
import br.com.ambientinformatica.fatesg.sgep.entidade.AlternativaQuestao;
import br.com.ambientinformatica.fatesg.sgep.entidade.EnumPeriodo;
import br.com.ambientinformatica.fatesg.sgep.entidade.ItemQuestaoTemplate;
import br.com.ambientinformatica.fatesg.sgep.entidade.Prova;
import br.com.ambientinformatica.fatesg.sgep.entidade.QuestaoProva;
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
import br.com.ambientinformatica.fatesg.sgep.persistencia.QuestaoProvaDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.QuestaoTemplateDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.SessaoProvaDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.SessaoTemplateDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.TemplateDao;

@Controller("ProvaControl")
@Scope("conversation")
public class ProvaControl {

	private Prova prova = new Prova();

	private Prova provaSelecionada = new Prova();

	private Prova provaAlterar = new Prova();

	private Prova provaExcluir;

	private ItemQuestaoTemplate itensProva = new ItemQuestaoTemplate();

	private Template template;

	private String pesquisa = new String();
	
	private String enunciado;

	private String tipoPesquisa = new String();

	private SessaoProva sessaoSelecionada = new SessaoProva();
	
	private SessaoTemplate sessaoTemplate = new SessaoTemplate();
	
	private QuestaoTemplate questaoSelecionada = new QuestaoTemplate();
	
	private QuestaoProva questaoProva = new QuestaoProva();
	
	private List<Prova> provas = new ArrayList<Prova>();
	
	private List<QuestaoTemplate> questoesTemplate = new ArrayList<>();
	
	private List<SessaoTemplate> sessoes = new ArrayList<>();

	private List<Template> templates = new ArrayList<>(); 
	
	private List<AlternativaQuestao> alternativas = new ArrayList<>();
	
	private List<Instituicao> instituicoes = new ArrayList<>();

	private List<Curso> cursos = new ArrayList<>();
	
	private List<Disciplina> disciplinas = new ArrayList<>();
	
	@Autowired
	private QuestaoTemplateDao questaoTemplateDao;
	
	@Autowired
	private QuestaoProvaDao questaoProvaDao;

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

	@PostConstruct
	public void init() {
		listarSessoes();
		listarInstituicoes();
		listarCursos();
		listarDisciplinas();
		listarQuestoes();
		try {
			provaDao.listar();
			listar();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Houve um erro ao listar Todos: " + e);
		}
	}

	public void imprimirProva(ActionEvent evt) {
		Prova provaImprimir = (Prova) UtilFaces.getValorParametro(evt, "sesImprimir");
		
		List<SessaoProva> sessoes = new ArrayList<SessaoProva>(provaImprimir.getSessoes());

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("data", provaImprimir.getData());
		parametros.put("nomeInstituicao", provaImprimir.getInstituicao().getNomeFantasia());
		parametros.put("nomeCurso", provaImprimir.getCurso().getDescricao());
		parametros.put("nomeDisciplina", provaImprimir.getDisciplina().getNome());
		parametros.put("periodo", provaImprimir.getPeriodo().getDescricao());

		try {
			UtilFacesRelatorio.gerarRelatorioFaces("jasper/prova2.jasper", sessoes, parametros);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Houve um erro ao gerar o Relatório: " + e);
		}
	}

	public void confirmar() {
		try {
			provaDao.alterar(prova);
			listar();
			UtilFaces.addMensagemFaces("Operação realizada com sucesso");
			provaSelecionada = new Prova();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void confirmarNovaProva(){
		Instituicao novaInstituicao = new Instituicao();
		Curso novoCurso = new Curso();
		Disciplina novaDisciplina = new Disciplina();
		try {
			if(!isInstituicaoJaCadastrado(prova.getInstituicao())){
				novaInstituicao.setChaveInstituicaoCorporatum(prova.getInstituicao().getId());
				novaInstituicao.setCnpj(prova.getInstituicao().getCnpj());
				novaInstituicao.setDescricao(prova.getInstituicao().getDescricao());
				novaInstituicao.setInscricaoEstadual(prova.getInstituicao().getInscricaoEstadual());
				novaInstituicao.setNomeFantasia(prova.getInstituicao().getNomeFantasia());
				novaInstituicao.setRazaoSocial(prova.getInstituicao().getRazaoSocial());
				prova.setInstituicao(novaInstituicao);
			}else {
				prova.setInstituicao(instituicaoDao.consultarPorChaveInstituicaoCorporatum(prova.getInstituicao().getId()));
			}
			if(!isCursoJaCadastrado(prova.getCurso())){
				novoCurso.setChaveCursoCorporatum(prova.getCurso().getId());
				novoCurso.setCodigo(prova.getCurso().getCodigo());
				novoCurso.setDescricao(prova.getCurso().getDescricao());
				novoCurso.setCargaHoraria(prova.getCurso().getCargaHoraria());
				novoCurso.setDtInicio(prova.getCurso().getDtInicio());
				novoCurso.setDtTermino(prova.getCurso().getDtTermino());
				novoCurso.setModalidade(prova.getCurso().getModalidade());
				novoCurso.setNome(prova.getCurso().getNome());
				novoCurso.setSigla(prova.getCurso().getSigla());
				novoCurso.setTurno(prova.getCurso().getTurno());

				//UnidadeEnsino unidadeEnsido = unidadeEnsinoDao.consultar(prova.getCurso().getUnidadeEnsino().getId());
				//novoCurso.setUnidadeEnsino(prova.getCurso().getUnidadeEnsino());
				prova.setCurso(novoCurso);
			}else {
				prova.setCurso(cursoDao.consultarPorChaveCursoCorporatum(prova.getCurso().getId()));
			}
			if(!isDisciplinaJaCadastrado(prova.getDisciplina())){
				novaDisciplina.setChaveDisciplinaCorporatum(prova.getDisciplina().getId());
				novaDisciplina.setCargaHoraria(prova.getDisciplina().getCargaHoraria());
				novaDisciplina.setNome(prova.getDisciplina().getNome());
				novaDisciplina.setCodigo(prova.getDisciplina().getCodigo());
				prova.setDisciplina(novaDisciplina);
			}else {
				prova.setDisciplina(disciplinaDao.consultarPorChaveDisciplinaCorporatum(prova.getDisciplina().getId()));
			}
			provaDao.alterar(prova);
			listar();
			limpar();
			UtilFaces.addMensagemFaces("Operação realizada com sucesso");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("É necessário o preenchimento de todos os campos", FacesMessage.SEVERITY_ERROR);
		}
	}
	private boolean isInstituicaoJaCadastrado(Instituicao instituicao) {
		return instituicaoDao.consultarPorChaveInstituicaoCorporatum(instituicao.getId()) != null;
	}
	private boolean isCursoJaCadastrado(Curso curso) {
		return cursoDao.consultarPorChaveCursoCorporatum(curso.getId()) != null;
	}
	private boolean isDisciplinaJaCadastrado(Disciplina disciplina) {
		return disciplinaDao.consultarPorChaveDisciplinaCorporatum(disciplina.getId()) != null;
	}

	public void listar() {
		try {
			provas = provaDao.listarProvas();
			limpar();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void listarSessoes(){
		sessoes = sessaoTemplateDao.listar();
	}

	public List<QuestaoTemplate> listarQuestoes(){
		try {
			return questoesTemplate = questaoTemplateDao.listar();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
		return null;
	}

	public List<Template> listarTemplates(){
		try {
			return templates = templateDao.listar();
		} catch (Exception e) {
		}
		return null;
	}

	public void excluir() {
		try {
			provaDao.excluirPorId(provaExcluir.getId());
			prova = new Prova();
			provas = provaDao.listar();
			UtilFaces.addMensagemFaces("Atividade excluída!");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void limpar() {
		prova = new Prova();
		questaoSelecionada = new QuestaoTemplate();
		provaSelecionada = new Prova();
		sessaoTemplate = new SessaoTemplate();
	}

	public void carregaSelecao() {
		System.out.println("Valeu falou " + getSessaoSelecionada());
	}

	public void listarInstituicoes() {
		try {
			instituicoes = instituicaoDao.listarInstituicoes();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Erro ao buscar a Instituição.");
		}
	}

	@SuppressWarnings("finally")
	public List<Curso> listarCursos() {
		try {
			return cursos = cursoDao.listarCursos();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Erro ao buscar o Curso");
		} finally {
			if (cursos.size() == 0) {
				UtilFaces.addMensagemFaces("Curso não encontrado.");
			}
			return null;
		}
	}

	@SuppressWarnings("finally")
	public List<Disciplina> listarDisciplinas() {
		try {
			return disciplinas = disciplinaDao.listarDisciplinas();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Erro ao buscar Disciplina.");
		} finally {
			if (disciplinas.size() == 0) {
				UtilFaces.addMensagemFaces("Disciplina não encontrada.");
			}
			return null;
		}
	}

	@SuppressWarnings("finally")
	public List<Aluno> completarAluno(String nome) {
		List<Aluno> listaAluno = new ArrayList<Aluno>();
		try {
			listaAluno = alunoDao.listarPorNome(nome);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Erro ao buscar Aluno");
		} finally {
			if (listaAluno.size() == 0) {
				UtilFaces.addMensagemFaces("Aluno não encontrado");
			}
			return listaAluno;
		}
	}

	/**
	 * Preenche todo o objeto do tipo Template e o converte em um objeto do tipo
	 * Prova.
	 */
	public void preencherProva() {
		try {
			template = templateDao.carregarTemplate(template);

			for (SessaoTemplate sessaoTem : template.getSessoes()) {
				SessaoProva sessaoPro = new SessaoProva();
				sessaoPro.getSessao().setDescricao(sessaoTem.getSessao().getDescricao());
				sessaoPro.getSessao().setTitulo(sessaoTem.getSessao().getTitulo());

				for (ItemQuestaoTemplate item : sessaoTem.getItensQuestao()) {
					sessaoPro.addQuestao(converterQuestao(item.getQuestaoTemplate()));
				}
				prova.addSessao(sessaoPro);
			}
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Não foi possivel comverter template em prova."
					+ e.getMessage());
		}
	}

	/**
	 * Converte uma Questão Template para Questão Prova.
	 */
	@SuppressWarnings("finally")
	public QuestaoProva converterQuestao(QuestaoTemplate item) {
		QuestaoProva questaoProva = new QuestaoProva();
		try {
			questaoProva.getQuestao().setAssunto(item.getQuestao().getAssunto());
			questaoProva.getQuestao().setEnunciado(item.getQuestao().getEnunciado());
			questaoProva.getQuestao().setResposta(item.getQuestao().getResposta());
			questaoProva.getQuestao().setProfessor(item.getQuestao().getProfessor());
			questaoProva.getQuestao().setDisciplina(item.getQuestao().getDisciplina());
			questaoProva.getQuestao().setEstado(item.getQuestao().getEstado());
			questaoProva.getQuestao().setDificuldade(item.getQuestao().getDificuldade());
			questaoProva.getQuestao().setObjetiva(item.getQuestao().getObjetiva());
			for (AlternativaQuestao alternativaQuestao : item.getQuestao().getAlternativas()) {
				AlternativaQuestao alternativa = new AlternativaQuestao();
				alternativa.setDescricao(alternativaQuestao.getDescricao());
				alternativa.setOrdem(alternativaQuestao.getOrdem());
				alternativa.setResposta(alternativaQuestao.getResposta());
				questaoProva.getQuestao().addItem(alternativa, false);
			}
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Falha ao converter Questão.");
		} finally {
			return questaoProva;
		}
	}
	
	public void confirmarNovaSessaoProva(){
		try {
				SessaoProva sessaoPro = new SessaoProva();
				sessaoPro.getSessao().setDescricao(sessaoTemplate.getSessao().getDescricao());
				sessaoPro.getSessao().setTitulo(sessaoTemplate.getSessao().getTitulo());

				for (ItemQuestaoTemplate item : sessaoTemplate.getItensQuestao()) {
					sessaoPro.addQuestao(converterQuestao(item.getQuestaoTemplate()));
				}
				prova.addSessao(sessaoPro);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Erro "
					+ e.getMessage());
		}
		
	}

	public void pesquisarQuestao() {
		try {
			String p = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("pesQuestao");
			questoesTemplate = questaoTemplateDao.consultarPor(pesquisa, tipoPesquisa);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Não foi possivel consultar as questões.");
		} finally {
			pesquisa = new String();
			tipoPesquisa = new String();
		}
	}

	public List<SelectItem> getPeriodos() {
		return UtilFaces.getListEnum(EnumPeriodo.values());
	}

	public void editarProva(Prova provaAlterar){
		this.prova = provaDao.consultar(provaAlterar.getId());
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("gerarProva.jsf");
		} catch (IOException e) {
			UtilFaces.addMensagemFaces(e.getMessage());
		}
		
	}
	
	public void novaSessao(){
		sessaoTemplate = new SessaoTemplate();
		RequestContext context = RequestContext.getCurrentInstance(); 
		context.execute("PF('vCadSessao').show();");	
	}
	
	public void selecionarQuestao(QuestaoTemplate questaoTemplate){
		this.questaoSelecionada = questaoTemplate;
	}
	
	public void adicionarItemQuestao(){
		if (questaoSelecionada.getQuestao().getId() != null) {
			try {
				sessaoTemplate.addItemQuestao(questaoSelecionada);
			} catch (Exception e) {
				UtilFaces.addMensagemFaces(e.getMessage(), FacesMessage.SEVERITY_WARN);
			}
			questaoSelecionada = new QuestaoTemplate();
		}else{
			UtilFaces.addMensagemFaces("Selecione uma questão para adicionar!", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void removerItemQuestao(ItemQuestaoTemplate item){
		sessaoTemplate.removeQuestao(item);
	}
	
	public QuestaoTemplate consultarAlternativasQuestao(QuestaoTemplate questaoTemplate){
		try {
			if (questaoProva != null) {
				QuestaoTemplate questaoTemp = questaoTemplateDao.consultarAlternativasQuestao(questaoTemplate);
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

	public QuestaoTemplate consultarEnunciadoQuestao(QuestaoTemplate questaoTemplate){
		try {
			if (questaoTemplate != null) {
				QuestaoTemplate questaoTemp = questaoTemplateDao.consultarEnunciadoQuestao(questaoTemplate);
				enunciado = questaoTemp.getQuestao().getEnunciado();
				RequestContext.getCurrentInstance().execute("PF('dlgEnunciado').show();");
			}
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e.getMessage());
		}
		return null;
	}

	public Prova getProva() {
		return prova;
	}

	public List<Prova> getProvas() {
		return provas;
	}

	public void setProva(Prova prova) {
		this.prova = prova;
	}

	public void setProvas(List<Prova> provas) {
		this.provas = provas;
	}

	public QuestaoTemplate getQuestao() {
		return questaoSelecionada;
	}

	public void setQuestao(QuestaoTemplate questao) {
		this.questaoSelecionada = questao;
	}

	public Prova getProvaSelecionada() {
		return provaSelecionada;
	}

	public void setProvaSelecionada(Prova provaSelecionada) {
		try {
			if (provaSelecionada != null) {
				Prova p = provaDao.consultar(provaSelecionada.getId());
				this.provaSelecionada = p;
			} else {
				this.provaSelecionada = null;
			}
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
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

	public List<SessaoTemplate> getSessoes() {
		return sessoes;
	}

	public void setSessoes(List<SessaoTemplate> sessoes) {
		this.sessoes = sessoes;
	}

	public Prova getProvaAlterar() {
		return provaAlterar;
	}

	public void setProvaAlterar(Prova provaAlterar) {
		this.provaAlterar = provaAlterar;
	}

	public Prova getProvaExcluir() {
		return provaExcluir;
	}

	public void setProvaExcluir(Prova provaExcluir) {
		this.provaExcluir = provaExcluir;
	}

	public List<Template> getTemplates() {
		return templates;
	}

	public void setTemplates(List<Template> templates) {
		this.templates = templates;
	}

	public List<Instituicao> getInstituicoes() {
		return instituicoes;
	}

	public void setInstituicoes(List<Instituicao> instituicoes) {
		this.instituicoes = instituicoes;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}

	public QuestaoProva getQuestaoProva() {
		return questaoProva;
	}

	public void setQuestaoProva(QuestaoProva questaoProva) {
		this.questaoProva = questaoProva;
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

	public SessaoTemplate getSessaoTemplate() {
		return sessaoTemplate;
	}

	public void setSessaoTemplate(SessaoTemplate sessaoTemplate) {
		this.sessaoTemplate = sessaoTemplate;
	}

	public List<QuestaoTemplate> getQuestoesTemplate() {
		return questoesTemplate;
	}

	public void setQuestoesTemplate(List<QuestaoTemplate> questoesTemplate) {
		this.questoesTemplate = questoesTemplate;
	}
	
	
}
