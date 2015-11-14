package br.com.ambientinformatica.fatesg.sgep.controle;

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
import br.com.ambientinformatica.fatesg.api.entidade.Aluno;
import br.com.ambientinformatica.fatesg.api.entidade.Curso;
import br.com.ambientinformatica.fatesg.api.entidade.Disciplina;
import br.com.ambientinformatica.fatesg.api.entidade.Instituicao;
import br.com.ambientinformatica.fatesg.sgep.entidade.ItemQuestaoTemplate;
import br.com.ambientinformatica.fatesg.sgep.entidade.Prova;
import br.com.ambientinformatica.fatesg.sgep.entidade.QuestaoTemplate;
import br.com.ambientinformatica.fatesg.sgep.entidade.Template;
import br.com.ambientinformatica.fatesg.sgep.persistencia.AlunoDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.ColaboradorDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.CursoDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.DisciplinaDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.InstituicaoDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.ProvaDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.TemplateDao;

@Controller("ProvaControl")
@Scope("conversation")
public class ProvaControl {

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
	private DisciplinaDao disciplinaDao;

	@Autowired
	private AlunoDao alunoDao;

	private List<Prova> provas = new ArrayList<Prova>();

	private List<QuestaoTemplate> questoes = new ArrayList<QuestaoTemplate>();

	private QuestaoTemplate questao = new QuestaoTemplate();

	private Prova prova = new Prova();

	private Prova provaSelecionada = new Prova();

	private ItemQuestaoTemplate itensProva = new ItemQuestaoTemplate();

	private Template template = new Template();

	@PostConstruct
	public void init() {
		try {
			// colaboradorDao.listarTodos();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Houve um erro ao listar Todos: " + e);
		}
		listar(null);
	}

	public void imprimirProva(ActionEvent evt) {
		Map<String, Object> parametros = new HashMap<String, Object>();
		// parametros.put("nomeInstituicao",
		// EmpresaLogadaControl.getEmpresa().getNome());
		// parametros.put("valorTotal", 55.5);

		try {
			// UtilFacesRelatorio.gerarRelatorioFaces("jasper/prova.jasper",
			// prova.getItens(), parametros);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Houve um erro ao gerar o Relatório: "
					+ e);
		}
	}

	public void confirmar(ActionEvent evt) {
		try {
			provaDao.alterar(prova);
			listar(evt);
			UtilFaces.addMensagemFaces("Operação realizada com sucesso");
			prova = new Prova();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void listar(ActionEvent evt) {
		try {
			provas = provaDao.listar();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
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
	}

	@SuppressWarnings("finally")
	public List<Template> completarTemplate() {
		List<Template> listaTemplates = new ArrayList<Template>();
		try {
			listaTemplates = templateDao.consultarPelaDescricao(template
					.getDescricao());
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Erro ao buscar o Template.");
		} finally {
			if (listaTemplates.size() == 0) {
				UtilFaces.addMensagemFaces("Template não encontrado.");
			}
			return listaTemplates;
		}
	}

	@SuppressWarnings("finally")
	public List<Instituicao> completarInstituicao() {
		List<Instituicao> listaInstituicao = new ArrayList<Instituicao>();
		try {
			listaInstituicao = instituicaoDao.listarPorNome(provaSelecionada
					.getInstituicao().getNomeFantasia());
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
	public List<Curso> completarCurso() {
		List<Curso> listaCurso = new ArrayList<Curso>();
		try {
			listaCurso = cursoDao.listarPorNome(provaSelecionada.getCurso()
					.getNome());
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
	public List<Disciplina> completarDisciplina() {
		List<Disciplina> listaDisciplina = new ArrayList<Disciplina>();
		try {
			listaDisciplina = disciplinaDao.listarPorNome(provaSelecionada
					.getDisciplina().getNome());
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
	public List<Aluno> completarAluno() {
		List<Aluno> listaAluno = new ArrayList<Aluno>();
		try {
			listaAluno = alunoDao.listarPorNome(provaSelecionada.getAluno()
					.getNome());
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Erro ao buscar Aluno");
		} finally {
			if (listaAluno.size() == 0) {
				UtilFaces.addMensagemFaces("Aluno não encontrado");
			}
			return listaAluno;
		}
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

	public QuestaoTemplate getQuestao() {
		return questao;
	}

	public void setQuestao(QuestaoTemplate questao) {
		this.questao = questao;
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

}
