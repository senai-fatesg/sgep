package br.com.ambientinformatica.fatesg.sgep.controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.fatesg.sgep.entidade.Questao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.QuestaoDao;

@Controller("QuestganoControl")
@Scope("conversation")
public class QuestaoControl {
	@Autowired
	private QuestaoDao questaoDao;

	private List<Questao> questoes = new ArrayList<Questao>();

	private Questao questao = new Questao();

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
		
}
