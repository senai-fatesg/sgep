package br.com.ambientinformatica.fatesg.sgep.controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.fatesg.sgep.entidade.Prova;
import br.com.ambientinformatica.fatesg.sgep.persistencia.ProvaDao;

@Controller("ProvaControl")
@Scope("conversation")
public class ProvaControl{

	private Prova prova  = new Prova();
	
	@Autowired
	private ProvaDao provaDao;	
	
	private List<Prova> provas = new ArrayList<Prova>();
		
	
   @PostConstruct
   public void init(){
      listar(null);
   }
   
	public void confirmar(ActionEvent evt){
		try {
			provaDao.alterar(prova);
         listar(evt);
         prova = new Prova();
		} catch (Exception e) {
		   UtilFaces.addMensagemFaces(e);
		}
	}

	public void listar(ActionEvent evt){
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
	
	public void limpar(){
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
}
