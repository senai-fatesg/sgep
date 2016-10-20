package br.com.ambientinformatica.fatesg.sgep.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.fatesg.api.entidade.Colaborador;
import br.com.ambientinformatica.fatesg.sgep.entidade.AlternativaQuestao;
import br.com.ambientinformatica.fatesg.sgep.entidade.EnumAlternativa;
import br.com.ambientinformatica.fatesg.sgep.entidade.EnumDificuldade;
import br.com.ambientinformatica.fatesg.sgep.entidade.EnumEstado;
import br.com.ambientinformatica.fatesg.sgep.entidade.QuestaoTemplate;
import br.com.ambientinformatica.fatesg.sgep.persistencia.ColaboradorDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.QuestaoTemplateDao;

@Controller("QuestaoControl")
@Scope("conversation")
public class QuestaoControl implements Serializable {

	private static final long serialVersionUID = 1L;


	private List<QuestaoTemplate> questoes = new ArrayList<QuestaoTemplate>();

	private Colaborador professor = new Colaborador();

	private List<Colaborador> professores = new ArrayList<Colaborador>();

	private QuestaoTemplate questaoSelecionada = new QuestaoTemplate();

	private AlternativaQuestao item = new AlternativaQuestao();

	private boolean rdbAutoComplete;
	
	private String nomeProfessor;
	
	@Autowired
	private QuestaoTemplateDao questaoDao;
	
	@Autowired
	private ColaboradorDao colaboradorDao;

	@PostConstruct
	public void init() {
		//listar();
	}

	public void confirmar() {
		try {
			questaoDao.alterar(questaoSelecionada);
			listar();
			questaoSelecionada = new QuestaoTemplate();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void listar() {
		try {
			questoes = questaoDao.listar();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void excluir() {
		try {
			questaoDao.excluirPorId(questaoSelecionada.getIdQuestaoTemplate());
			questaoSelecionada = new QuestaoTemplate();
			questoes = questaoDao.listar();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void carregarQuestao() {
		try {
			this.questaoSelecionada = questaoDao.carregarQuestao(questaoSelecionada);
		} catch (Exception e) {
			
			UtilFaces.addMensagemFaces("Falha ao carregar objetos");
		}
	}
	
	
	public List<Colaborador> completarColaboradores(String nome) {
		List<Colaborador> colaboradores = colaboradorDao.listarPorNome(nome); 
		return colaboradores;
	}

	public void adicionarItem() {
		try {
			if (!item.getDescricao().isEmpty() || item.getDescricao() == "") {
				if (questaoSelecionada.getQuestao().getAlternativas().size() < 5) {
					item.setOrdem(questaoSelecionada.getQuestao()
							.getAlternativas().size());
					questaoSelecionada.getQuestao().addItem(item);
				} else {
					UtilFaces
							.addMensagemFaces("Atenção!\n Capacidade maxima de itens alcançada.");
				}
			}
		} catch (Exception e) {
			UtilFaces
					.addMensagemFaces("Não foi possivel adicionar a alternativa.\n"
							+ e.getMessage());
		} finally {
			item = new AlternativaQuestao();
		}
	}

	public void editarItem(AlternativaQuestao alternativa) {
		try {
			this.item = alternativa;
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void removerItem(AlternativaQuestao alternativa) {
		try {
			this.questaoSelecionada.getQuestao().removerItem(alternativa);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public List<SelectItem> getEstados() {
		return UtilFaces.getListEnum(EnumEstado.values());
	}

	public List<SelectItem> getDificuldades() {
		return UtilFaces.getListEnum(EnumDificuldade.values());
	}

	public void limpar() {
		questaoSelecionada = new QuestaoTemplate();
		item = new AlternativaQuestao();
	}

	public void limparItem() {
		item = new AlternativaQuestao();
	}

	public List<QuestaoTemplate> getQuestoes() {
		return questoes;
	}

	public Colaborador getProfessor() {
		return professor;
	}

	public void setProfessor(Colaborador professor) {
		this.professor = professor;
	}

	public List<Colaborador> getProfessores() {
		return professores;
	}

	public void setProfessores(List<Colaborador> professores) {
		this.professores = professores;
	}

	public AlternativaQuestao getItem() {
		return item;
	}

	public void setItem(AlternativaQuestao item) {
		this.item = item;
	}

	public QuestaoTemplate getQuestaoSelecionada() {
		return questaoSelecionada;
	}

	public void setQuestaoSelecionada(QuestaoTemplate questaoSelecionada) {
		this.questaoSelecionada = questaoSelecionada;
	}

	public String getNomeProfessor() {
		return nomeProfessor;
	}

	public void setNomeProfessor(String nomeProfessor) {
		this.nomeProfessor = nomeProfessor;
	}

	public boolean isRdbAutoComplete() {
		return rdbAutoComplete;
	}

	public void setRdbAutoComplete(boolean rdbAutoComplete) {
		this.rdbAutoComplete = rdbAutoComplete;
	}

	public List<SelectItem> getAlternativas() {
		return UtilFaces.getListEnum(EnumAlternativa.values());
	}
	
	

}
