package br.com.ambientinformatica.fatesg.sgep.controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.fatesg.sgep.entidade.Area;
import br.com.ambientinformatica.fatesg.sgep.entidade.Cabecalho;
import br.com.ambientinformatica.fatesg.sgep.persistencia.CabecalhoDao;

@Controller("CabecalhoControl")
@Scope("conversation")
public class CabecalhoControl{

	private Cabecalho cabecalho  = new Cabecalho();
	
	@Autowired
	private CabecalhoDao cabecalhoDao;	
	
	private List<Cabecalho> cabecalhos = new ArrayList<Cabecalho>();
		
	
   @PostConstruct
   public void init(){
      listar(null);
   }
   
	public void confirmar(ActionEvent evt){
		try {
			cabecalhoDao.alterar(cabecalho);
         listar(evt);
         cabecalho = new Cabecalho();
		} catch (Exception e) {
		   UtilFaces.addMensagemFaces(e);
		}
	}

	public void listar(ActionEvent evt){
		try {
			cabecalhos = cabecalhoDao.listar();
		} catch (Exception e) {
		   UtilFaces.addMensagemFaces(e);
		}
	}

	public void excluir() {
		try {			
			cabecalhoDao.excluirPorId(cabecalho.getId());
			cabecalho = new Cabecalho();
			cabecalhos = cabecalhoDao.listar();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}	
	}
	
	public void limpar(){
		cabecalho = new Cabecalho();
	}

	public Cabecalho getCabecalho() {
		return cabecalho;
	}

	public List<Cabecalho> getCabecalhos() {
		return cabecalhos;
	}

	public void setCabecalho(Cabecalho cabecalho) {
		this.cabecalho = cabecalho;
	}		
}
