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
import br.com.ambientinformatica.fatesg.sgep.persistencia.AreaDao;

@Controller("AreaControl")
@Scope("conversation")
public class AreaControl{

	private Area area  = new Area();
	
	@Autowired
	private AreaDao areaDao;	
	
	private List<Area> areas = new ArrayList<Area>();
		
	
   @PostConstruct
   public void init(){
      listar(null);
   }
   
	public void confirmar(ActionEvent evt){
		try {
			areaDao.alterar(area);
         listar(evt);
         area = new Area();
		} catch (Exception e) {
		   UtilFaces.addMensagemFaces(e);
		}
	}

	public void listar(ActionEvent evt){
		try {
			areas = areaDao.listar();
		} catch (Exception e) {
		   UtilFaces.addMensagemFaces(e);
		}
	}

	public void excluir() {
		try {			
			areaDao.excluirPorId(area.getId());
			area = new Area();
			areas = areaDao.listar();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}	
	}
	
	public void limpar(){
		area = new Area();
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public List<Area> getAreas() {
		return areas;
	}	
		
}
