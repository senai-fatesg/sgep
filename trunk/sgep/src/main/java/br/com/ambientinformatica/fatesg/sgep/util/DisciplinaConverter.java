package br.com.ambientinformatica.fatesg.sgep.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ambientinformatica.fatesg.api.entidade.Disciplina;
import br.com.ambientinformatica.fatesg.sgep.persistencia.DisciplinaDao;
import br.com.ambientinformatica.jpa.util.FabricaAbstrata;

@FacesConverter("disciplinaConverter")
public class DisciplinaConverter implements Converter {

	private DisciplinaDao disciplinaDao = (DisciplinaDao) FabricaAbstrata.criarObjeto("disciplinaDao");
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent component,
			Object value) {
		if (value == null || value.equals("")) {
			return "";
		} else {
			return String.valueOf(((Disciplina) value).getId());
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if (value != null && !value.trim().equals("")) {
			Disciplina disciplina = new Disciplina();
			try {
				int id = Integer.parseInt(value);
				try {
					disciplina = disciplinaDao.consultar(id);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (NumberFormatException exception) {
				return null;
			}
			return disciplina;
		} else {
			return null;
		}
	}
}
