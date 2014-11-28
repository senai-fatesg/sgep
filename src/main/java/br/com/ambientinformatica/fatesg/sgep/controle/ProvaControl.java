package br.com.ambientinformatica.fatesg.sgep.controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.fatesg.sgep.entidade.ItensProva;
import br.com.ambientinformatica.fatesg.sgep.entidade.Prova;
import br.com.ambientinformatica.fatesg.sgep.entidade.Questao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.ProvaDao;

@Controller("ProvaControl")
@Scope("conversation")
public class ProvaControl {
	@Autowired
	private ProvaDao provaDao;
	
	private List<Prova> provas = new ArrayList<Prova>();

	private Questao questao = new Questao();

	private Prova prova = new Prova();
	
	private Prova provaSelecionada = new Prova();
	
	private ItensProva itensProva = new ItensProva();

	

	
	public void addQuestao(Questao questao) {
		ItensProva novoItem = new ItensProva(prova, questao);
		if (questao.getItens().contains(novoItem)) {
			ItensProva item = questao.getItens().get(
					questao.getItens().indexOf(novoItem));
		} else {
			questao.getItens().add(novoItem);
		}
	}

	public void removerItem(ItensProva item) {
		questao.getItens().remove(item);
	}

	@PostConstruct
	public void init() {
		listar(null);
	}

	public void confirmar(ActionEvent evt) {
		try {
			provaDao.alterar(prova);
			listar(evt);
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
	
	public Prova getProva() {
		return prova;
	}

	public List<Prova> getProvas() {
		return provas;
	}

	public void setProva(Prova prova) {
		this.prova = prova;
	}

	public Questao getQuestao() {
		return questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

	public Prova getProvaSelecionada() {
		return provaSelecionada;
	}

	public void setProvaSelecionada(Prova provaSelecionada) {
		try {
	         if(provaSelecionada != null){
	            Prova p = provaDao.consultar(provaSelecionada.getId());
	            this.provaSelecionada = p;
	         }else{
	            this.provaSelecionada = null;
	         }
	      } catch (Exception e) {
	         UtilFaces.addMensagemFaces(e);
	      }
	}

	public ItensProva getItensProva() {
		return itensProva;
	}

	public void setItensProva(ItensProva itensProva) {
		this.itensProva = itensProva;
	}
	
}
