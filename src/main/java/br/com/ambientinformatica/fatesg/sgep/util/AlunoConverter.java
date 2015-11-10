package br.com.ambientinformatica.fatesg.sgep.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ambientinformatica.fatesg.api.entidade.Aluno;
import br.com.ambientinformatica.fatesg.sgep.persistencia.AlunoDao;
import br.com.ambientinformatica.jpa.util.FabricaAbstrata;

@FacesConverter("alunoConverter")
public class AlunoConverter implements Converter {

	private AlunoDao alunoDao = (AlunoDao) FabricaAbstrata.criarObjeto("alunoDao");
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent component,
			Object value) {
		if (value == null || value.equals("")) {
			return "";
		} else {
			return String.valueOf(((Aluno) value).getId());
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if (value != null && !value.trim().equals("")) {
			Aluno aluno = new Aluno();
			try {
				int id = Integer.parseInt(value);
				try {
					aluno = alunoDao.consultar(id);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (NumberFormatException exception) {
				return null;
			}
			return aluno;
		} else {
			return null;
		}
	}
}
