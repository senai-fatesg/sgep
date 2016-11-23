package br.com.ambientinformatica.fatesg.sgep.util;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ambientinformatica.fatesg.api.entidade.Instituicao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.InstituicaoDao;

@FacesConverter("instituicaoConverter")
public class InstituicaoConverter implements Converter{
	
	private InstituicaoDao instituicaoDao = (InstituicaoDao)FabricaObjetoSpring.criarObjeto("instituicaoDao");
	List<Instituicao> listaInstituicoes = instituicaoDao.listarInstituicoes();

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {  
		if (value == null || value.equals("")) {  
			return "";  
		} else {  
			return String.valueOf(((Instituicao) value).getId());  
		}  
	}


	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && !value.trim().equals("")) {  
			Instituicao instituicao = new Instituicao();
			try {  
				int id = Integer.parseInt(value);  
					for(Instituicao pp: listaInstituicoes){
						if(pp.getId()==id){
							instituicao = pp;
						}
					}
			} catch(NumberFormatException exception) {  
				return null;
			}  
			return instituicao;  
		}else{
			return null;
		}
	}

}
