package br.com.ambientinformatica.fatesg.sgep.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ambientinformatica.fatesg.sgep.entidade.Template;
import br.com.ambientinformatica.fatesg.sgep.persistencia.TemplateDao;
import br.com.ambientinformatica.jpa.util.FabricaAbstrata;

@FacesConverter("templateConverter")
public class TemplateConverter implements Converter {

	private TemplateDao templateDao = (TemplateDao) FabricaAbstrata.criarObjeto("templateDao");
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent component,
			Object value) {
		if (value == null || value.equals("")) {
			return "";
		} else {
			return String.valueOf(((Template) value).getId());
		}
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if (value != null && !value.trim().equals("")) {
			Template template = new Template();
			try {
				int id = Integer.parseInt(value);
				try {
					template = templateDao.consultar(id);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (NumberFormatException exception) {
				return null;
			}
			return template;
		} else {
			return null;
		}
	}
}
