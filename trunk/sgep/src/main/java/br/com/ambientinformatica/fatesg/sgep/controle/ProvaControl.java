package br.com.ambientinformatica.fatesg.sgep.controle;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.primefaces.event.DragDropEvent;
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
import br.com.ambientinformatica.fatesg.sgep.persistencia.QuestaoTemplateDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.SessaoProvaDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.SessaoTemplateDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.TemplateDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.UnidadeEnsinoDao;

@Controller("ProvaControl")
@Scope("conversation")
public class ProvaControl {

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
	public void init() {
		listarSessoes();
		try {
			provaDao.listar();
			listar();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Houve um erro ao listar Todos: " + e);
		}
	}

	public void imprimirProva(ActionEvent evt) {
		Prova provaImprimir = (Prova) UtilFaces.getValorParametro(evt,
				"sesImprimir");

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("nomeInstituicao", "Senai");
		parametros.put("nomeCurso", "ADS");
		parametros.put("nomeAluno", "Lucas");
		parametros.put("nomeDisciplina", "Teste");
		parametros.put("data", new Date());
		parametros.put("periodo", 2);

		try {
			UtilFacesRelatorio.gerarRelatorioFaces("jasper/prova.jasper",
					provaImprimir.getSessoes(), parametros);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Houve um erro ao gerar o Relatório: "
					+ e);
		}
	}

	public void confirmar() {
		try {
			provaDao.alterar(provaSelecionada);
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
			if(isInstituicaoJaCadastrado(prova.getInstituicao())){
				novaInstituicao.setChaveInstituicaoCorporatum(prova.getInstituicao().getId());
				novaInstituicao.setCnpj(prova.getInstituicao().getCnpj());
				novaInstituicao.setDescricao(prova.getInstituicao().getDescricao());
				novaInstituicao.setInscricaoEstadual(prova.getInstituicao().getInscricaoEstadual());
				novaInstituicao.setNomeFantasia(prova.getInstituicao().getNomeFantasia());
				novaInstituicao.setRazaoSocial(prova.getInstituicao().getRazaoSocial());
				instituicaoDao.alterar(novaInstituicao);
				prova.setInstituicao(novaInstituicao);
			}
			if(isCursoJaCadastrado(prova.getCurso())){
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
				cursoDao.alterar(novoCurso);
				prova.setCurso(novoCurso);
			}
			if(isDisciplinaJaCadastrado(prova.getDisciplina())){
				novaDisciplina.setChaveDisciplinaCorporatum(prova.getDisciplina().getId());
				novaDisciplina.setCargaHoraria(prova.getDisciplina().getCargaHoraria());
				novaDisciplina.setNome(prova.getDisciplina().getNome());
				novaDisciplina.setCodigo(prova.getDisciplina().getCodigo());
				disciplinaDao.alterar(novaDisciplina);
				prova.setDisciplina(novaDisciplina);
			}
			provaDao.alterar(prova);
			listar();
			UtilFaces.addMensagemFaces("Operação realizada com sucesso");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}
	private boolean isInstituicaoJaCadastrado(Instituicao instituicao) {
		return instituicaoDao.consultar(instituicao.getId()) != null;
	}
	private boolean isCursoJaCadastrado(Curso curso) {
		return cursoDao.consultar(curso.getId()) != null;
	}
	private boolean isDisciplinaJaCadastrado(Disciplina disciplina) {
		return disciplinaDao.consultar(disciplina.getId()) != null;
	}

	public void listar() {
		try {
			provas = provaDao.listar();
			limpar();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void listarSessoes(){
		sessoes = sessaoTemplateDao.listar();
		System.out.println(sessoes.toString());
	}
	
	public List<QuestaoTemplate> listarQuestoes(){
		try {
			return questoes = questaoTemplateDao.listar();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
		return null;
	}

	public void excluir() {
		try {
			provaDao.excluirPorId(prova.getId());
			prova = new Prova();
			provas = provaDao.listar();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void limpar() {
		prova = new Prova();
		questaoSelecionada = new QuestaoTemplate();
		provaSelecionada = new Prova();
	}

	public void onQuestaoDrop(DragDropEvent ddEvent) {
		QuestaoTemplate questao = ((QuestaoTemplate) ddEvent.getData());
		questoes.remove(questao);

		for (SessaoProva sessao : provaSelecionada.getSessoes()) {
			if (sessao
					.getSessao()
					.getTitulo()
					.equalsIgnoreCase(
							getSessaoSelecionada().getSessao().getTitulo())) {
				int indice = provaSelecionada.getSessoes().indexOf(sessao);
				provaSelecionada.getSessoes().get(indice)
				.addQuestao(converterQuestao(questao));
			}
		}
	}
	
	public void carregaSelecao() {
		System.out.println("Valeu falou " + getSessaoSelecionada());
	}

	@SuppressWarnings("finally")
	public List<Instituicao> completarInstituicao(String nome) {
		List<Instituicao> listaInstituicao = new ArrayList<>();
		try {
			listaInstituicao = instituicaoDao.listarPorNome(nome);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Erro ao buscar a Instituição.");
		} finally {
			if (listaInstituicao.size() == 0) {
				UtilFaces.addMensagemFaces("Instituição não encontrada.");
			}
			return listaInstituicao;
		}
	}

	@SuppressWarnings("finally")
	public List<Curso> completarCurso(String nome) {
		List<Curso> listaCurso = new ArrayList<Curso>();
		try {
			listaCurso = cursoDao.listarPorNome(nome);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Erro ao buscar o Curso");
		} finally {
			if (listaCurso.size() == 0) {
				UtilFaces.addMensagemFaces("Curso não encontrado.");
			}
			return listaCurso;
		}
	}

	@SuppressWarnings("finally")
	public List<Disciplina> completarDisciplina(String nome) {
		List<Disciplina> listaDisciplina = new ArrayList<>();
		try {
			listaDisciplina = disciplinaDao.listarPorNome(nome);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Erro ao buscar Disciplina.");
		} finally {
			if (listaDisciplina.size() == 0) {
				UtilFaces.addMensagemFaces("Disciplina não encontrada.");
			}
			return listaDisciplina;
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
				sessaoPro.getSessao().setDescricao(
						sessaoTem.getSessao().getDescricao());
				sessaoPro.getSessao().setTitulo(
						sessaoTem.getSessao().getTitulo());

				for (ItemQuestaoTemplate item : sessaoTem.getItemQuestao()) {
					sessaoPro.addQuestao(converterQuestao(item.getQuestao()));
				}
				provaSelecionada.addSessao(sessaoPro);
			}
		} catch (Exception e) {
			UtilFaces
			.addMensagemFaces("Não foi possivel comverter template em prova."
					+ e.getMessage());
		}
	}

	/**
	 * Converte uma QuesÃ£o Template para QuestÃ£o Prova.
	 */
	@SuppressWarnings("finally")
	public QuestaoProva converterQuestao(QuestaoTemplate item) {
		QuestaoProva questaoProva = new QuestaoProva();
		try {
			questaoProva.getQuestao()
			.setAssunto(item.getQuestao().getAssunto());
			questaoProva.getQuestao().setEnunciado(
					item.getQuestao().getEnunciado());
			questaoProva.getQuestao().setResposta(
					item.getQuestao().getResposta());
			questaoProva.getQuestao().setProfessor(
					item.getQuestao().getProfessor());
			questaoProva.getQuestao().setDisciplina(
					item.getQuestao().getDisciplina());
			questaoProva.getQuestao().setEstado(item.getQuestao().getEstado());
			questaoProva.getQuestao().setDificuldade(
					item.getQuestao().getDificuldade());
			questaoProva.getQuestao().setObjetiva(
					item.getQuestao().getObjetiva());
			for (AlternativaQuestao alternativaQuestao : item.getQuestao()
					.getAlternativas()) {
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

	public void pesquisarQuestao() {
		try {

			String p = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("pesQuestao");
			questoes = questaoTemplateDao.consultarPor(pesquisa, tipoPesquisa);
			System.out.println(questoes);
		} catch (Exception e) {
			UtilFaces
			.addMensagemFaces("Não foi possivel consultar as questões.");
		} finally {
			pesquisa = new String();
			tipoPesquisa = new String();
		}
	}

	public List<SelectItem> getPeriodos() {
		return UtilFaces.getListEnum(EnumPeriodo.values());
	}

	public void editarProva(Prova provaAlterar){
		this.prova = provaAlterar;
	}

	// Gets e Sets
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

	public List<QuestaoTemplate> getQuestoes() {
		return questoes;
	}

	public void setQuestoes(List<QuestaoTemplate> questoes) {
		this.questoes = questoes;
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

}
