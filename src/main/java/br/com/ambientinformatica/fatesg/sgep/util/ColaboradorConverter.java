package br.com.ambientinformatica.fatesg.sgep.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.fatesg.api.entidade.Colaborador;
import br.com.ambientinformatica.fatesg.sgep.persistencia.ColaboradorDao;
import br.com.ambientinformatica.jpa.util.FabricaAbstrata;

@FacesConverter("colaboradorConverter")
public class ColaboradorConverter implements Converter {

	private ColaboradorDao colaboradorDao = (ColaboradorDao) FabricaAbstrata
			.criarObjeto("colaboradorDao");

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component,
			Object value) {
		if (value == null || value.equals("")) {
			return "";
		} else {
			return String.valueOf(((Colaborador) value).getId());
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if (value != null && !value.trim().equals("")) {
			Colaborador colaborador = new Colaborador();
			try {
				long id = Long.parseLong(value);

//				try {
//					colaborador = colaboradorDao.consultar(id);
//				} catch (PersistenciaException e) {
//					e.printStackTrace();
//				}
			} catch (NumberFormatException exception) {
				UtilFaces
						.addMensagemFaces("Colaborador não é válido. Erro no Converter");
			}
			return colaborador;
		} else {
			return null;
		}
	}
}
