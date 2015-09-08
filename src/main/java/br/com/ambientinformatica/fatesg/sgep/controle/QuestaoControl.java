package br.com.ambientinformatica.fatesg.sgep.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.fatesg.api.entidade.Colaborador;
import br.com.ambientinformatica.fatesg.sgep.entidade.EnumDificuldade;
import br.com.ambientinformatica.fatesg.sgep.entidade.EnumEstado;
import br.com.ambientinformatica.fatesg.sgep.entidade.Item;
import br.com.ambientinformatica.fatesg.sgep.entidade.Questao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.ColaboradorDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.QuestaoDao;

@Controller("QuestaoControl")
@Scope("conversation")
public class QuestaoControl implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private QuestaoDao questaoDao;

	private List<Questao> questoes = new ArrayList<Questao>();

	private Questao questao = new Questao();

	private Colaborador professor = new Colaborador();

	private List<Colaborador> professores = new ArrayList<Colaborador>();

	private Questao questaoSelecionada = new Questao();

	private Item item = new Item();

	@Autowired
	private ColaboradorDao colaboradorDao;

	@PostConstruct
	public void init() {
		listar(null);
	}

	public void confirmar(ActionEvent evt) {
		try {
			questaoDao.alterar(questao);
			listar(evt);
			questao = new Questao();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void listar(ActionEvent evt) {
		try {
			questoes = questaoDao.listar();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void excluir() {
		try {
			questaoDao.excluirPorId(questao.getId());
			questao = new Questao();
			questoes = questaoDao.listar();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public Colaborador completarColaboradores(String nome) {
		Colaborador colaborador = colaboradorDao.consultarPorCpf(nome);
		return colaborador;
	}

	public void adicionarItem() {
		try {
			if (item.getId() != null) {
			} else {
				if (questao.getItens().size() < 5) {
					item.setOrdem(questao.getItens().size());
					questao.addItem(item);
				} else {
					UtilFaces
							.addMensagemFaces("Atenção!\n Capacidade maxima de itens alcançada.");
				}
			}
			item = new Item();
		} catch (Exception e) {
			UtilFaces
					.addMensagemFaces("Ocorreu um erro inesperado ao adicionar a alternativa.\n"
							+ e.getMessage());
		}
	}

	public void removerItem(Item item) {
		try {
			this.questao.removerItem(item);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void view(){
		RequestContext.getCurrentInstance().openDialog("questao"); 
	}
	
	public List<SelectItem> getEstados() {
		return UtilFaces.getListEnum(EnumEstado.values());
	}

	public List<SelectItem> getDificuldades() {
		return UtilFaces.getListEnum(EnumDificuldade.values());
	}

	public void limpar() {
		questao = new Questao();
	}

	public List<Questao> getQuestoes() {
		return questoes;
	}

	public Questao getQuestao() {
		return questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
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

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Questao getQuestaoSelecionada() {
		return questaoSelecionada;
	}

	public void setQuestaoSelecionada(Questao questaoSelecionada) {
		try {
			if (questaoSelecionada != null) {
				Questao q = questaoDao.consultar(questaoSelecionada.getId());
				this.questaoSelecionada = q;
			} else {
				this.questaoSelecionada = null;
			}
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

}
