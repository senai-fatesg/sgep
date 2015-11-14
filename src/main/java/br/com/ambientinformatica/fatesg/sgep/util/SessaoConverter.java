package br.com.ambientinformatica.fatesg.sgep.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ambientinformatica.fatesg.sgep.entidade.SessaoTemplate;
import br.com.ambientinformatica.fatesg.sgep.persistencia.SessaoTemplateDao;
import br.com.ambientinformatica.jpa.util.FabricaAbstrata;

@FacesConverter("sessaoConverter")
public class SessaoConverter implements Converter {

	private SessaoTemplateDao sessaoDao = (SessaoTemplateDao) FabricaAbstrata
			.criarObjeto("sessaoDao");

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component,
			Object value) {
		if (value == null || value.equals("")) {
			return "";
		} else {
			return String.valueOf(((SessaoTemplate) value).getId());
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if (value != null && !value.trim().equals("")) {
			SessaoTemplate funcionario = new SessaoTemplate();
			try {
				int id = Integer.parseInt(value);
				try {
					funcionario = sessaoDao.consultar(id);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (NumberFormatException exception) {
				return null;
			}
			return funcionario;
		} else {
			return null;
		}
	}
}
