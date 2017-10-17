package br.com.ambientinformatica.fatesg.sgep.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.fatesg.api.entidade.Colaborador;
import br.com.ambientinformatica.fatesg.api.entidade.EnumPapelUsuario;
import br.com.ambientinformatica.fatesg.api.entidade.EnumTipoColaborador;
import br.com.ambientinformatica.fatesg.api.entidade.EnumTipoSexo;
import br.com.ambientinformatica.fatesg.api.entidade.EnumUf;
import br.com.ambientinformatica.fatesg.api.entidade.Municipio;
import br.com.ambientinformatica.fatesg.sgep.persistencia.ColaboradorDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.MunicipioDao;
import br.com.ambientinformatica.util.AmbientException;
import br.com.ambientinformatica.util.UtilCpf;
import br.com.ambientinformatica.util.UtilHash;
import br.com.ambientinformatica.util.UtilHash.Algoritimo;

@Controller("ColaboradorControl")
@Scope("conversation")
public class ColaboradorControl implements Serializable {

	private static final long serialVersionUID = 1L;

	private Colaborador colaborador = new Colaborador();

	private Colaborador colaboradorAlterar = new Colaborador();

	private String nome;

	private String cpf;

	private String senha1;

	private String senha2;

	private String confirmarSenha;

	private EnumPapelUsuario papel;

	@Autowired
	private ColaboradorDao colaboradorDao;

	@Autowired
	private MunicipioDao municipioDao;

	private EnumUf uf;

	private List<Colaborador> colaboradores = new ArrayList<Colaborador>();

	private String filtroGlobal = "";

	private SelectItem tipo = new SelectItem();

	private List<Municipio> municipios = new ArrayList<>();

	@PostConstruct
	public void init() {
		if(colaborador != null && colaborador.getMunicipio() != null){
			uf = colaborador.getMunicipio().getUf();
		}else{
			uf = EnumUf.GO;
		}
		atualizarMunicipios();
	}

	public void alterar(){
		try {
			colaboradorDao.alterar(colaboradorAlterar);
			limpar();
			UtilFaces.addMensagemFaces("Cadastro salvo com sucesso!");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void excluir(ActionEvent evt) {
		try {
			colaboradorDao.excluirPorId(colaborador.getId());
			colaborador = new Colaborador();
			colaboradores = colaboradorDao.listar();
			UtilFaces.addMensagemFaces("Operação realizada com sucesso!");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}
	public void novoColaborador(){
		colaborador = new Colaborador();
		RequestContext context = RequestContext.getCurrentInstance(); 
		context.execute("PF('dlg1').show();");	
	}

	public void listar() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("colaborador", new Colaborador());
			colaboradores = colaboradorDao.listar();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void limpar() {
		colaborador = new Colaborador();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("colaboradorlista.jsf");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void cadastrarNovoUsuario(){
		colaborador = new Colaborador();
		colaboradorAlterar = new Colaborador();
		RequestContext.getCurrentInstance().execute("PF('usuario').show()");
	}

	public void editarColaborador(ActionEvent evt){
		setColaboradorAlterar((Colaborador) UtilFaces.getValorParametro(evt, "colaborador"));
		setUf(colaboradorAlterar.getMunicipio().getUf());
	}

	public void limparConsulta() {
		filtroGlobal = "";
		try {
			colaboradores = colaboradorDao.listar();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}
	
	 public void alterarSenhaNovo() throws AmbientException{
		 String senhaAtualCripto = UtilHash.gerarStringHash(confirmarSenha, Algoritimo.MD5);
		 Colaborador pessoaLogada = UsuarioLogadoControl.getUsuarioConfigurado();
	      if(senhaAtualCripto.equals(pessoaLogada.getSenha())){
	         if(senha1.equals(senha2)){
	        	 pessoaLogada.setSenhaNaoCriptografada(senha2);
	        	 colaboradorDao.alterar(pessoaLogada);
	            UtilFaces.addMensagemFaces("Senha Alterada com sucesso!");
	         }else{
	            UtilFaces.addMensagemFaces("As senhas digitadas devem ser iguais.");
	         }
	      }else{
	         UtilFaces.addMensagemFaces("Senha não confere.");
	      }

	   }

	
	
	public String alterarSenha() {
		try {
			String senhaAtualCripto = UtilHash.gerarStringHash(confirmarSenha, Algoritimo.MD5);
			Colaborador pessoaLogada = UsuarioLogadoControl.getUsuarioConfigurado();
			if (senhaAtualCripto.equals(pessoaLogada.getSenha())) {
				if (senha1 != null && senha1.equals(senha2)) {
					pessoaLogada.setSenhaNaoCriptografada(senha1);
					pessoaLogada.setAlterarSenha(true);
					pessoaLogada.setConfirmado(true);
					colaboradorDao.alterar(pessoaLogada);
					UtilFaces.addMensagemFaces("Senha alterada com sucesso!");
					return "inicio.secima";
				} else {
					UtilFaces.addMensagemFaces("Senhas diferentes!", FacesMessage.SEVERITY_ERROR);
				}
			} else {
				UtilFaces.addMensagemFaces("Senha atual incorreta!", FacesMessage.SEVERITY_ERROR);
			}
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
		return "inicio.jsf";
	}

	public void confirmar() {
		try {
			String cpf = colaborador.getCpfCnpj();
			//if (UtilCpf.validarCpf(cpf)) { //COMENTADO PORQUE ESSE VALIDADOR ESTÁ INVALIDANDO TODO O CPF
				colaborador.addPapel(EnumPapelUsuario.USUARIO);
				colaborador.setSenhaNaoCriptografada("123456");
				colaborador.setConfirmado(true);
				colaboradorDao.alterar(colaborador);
				UtilFaces.addMensagemFaces("Operação realizada com sucesso");
				limpar();
				
			//} 
				//else {
				//UtilFaces.addMensagemFaces("CPF Inválido"); //COMENTADO PORQUE ESSE VALIDADOR ESTÁ INVALIDANDO TODO O CPF
			//}
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void adicionarPapel(){
		try{
			colaboradorAlterar.addPapel(papel);
		}catch(Exception e){
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void removerPapel(ActionEvent evt){
		try{
			colaboradorAlterar.removerPapel((EnumPapelUsuario) UtilFaces.getValorParametro(evt, "papel"));
			UtilFaces.addMensagemFaces("Papel removido!");
		}catch(Exception e){
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void buscarColaboradoPorCpf(){
		Colaborador novoColaborador;
		try {
			novoColaborador = colaboradorDao.consultarPorCpfSgep(cpf);
			if (novoColaborador == null) {
				//consulta a base do corporatum
				novoColaborador = colaboradorDao.consultarPorCpf(cpf);
				if (novoColaborador != null) {
					colaborador.setIdColaboradorPai(novoColaborador.getId());
					colaborador.setCpfCnpj(novoColaborador.getCpfCnpj());
					colaborador.setNome(novoColaborador.getNome());
					colaborador.setRg(novoColaborador.getRg());
					colaborador.setTipoSexo(novoColaborador.getTipoSexo());
					colaborador.setTituloEleitor(novoColaborador.getTituloEleitor());
					colaborador.setReservista(novoColaborador.getReservista());
					colaborador.setTipo(novoColaborador.getTipo());
					colaborador.setHistorico(novoColaborador.getHistorico());
					colaborador.setTelefone(novoColaborador.getTelefone());
					colaborador.setCelular(novoColaborador.getCelular());
					colaborador.setEmail(novoColaborador.getEmail());
					colaborador.setCep(novoColaborador.getCep());
					colaborador.setEndereco(novoColaborador.getEndereco());
					colaborador.setMunicipio(novoColaborador.getMunicipio());
					atualizarUfMunicipioColaborador();
				}else {
					colaborador = new Colaborador();
					UtilFaces.addMensagemFaces("Usuário não cadastrado. Preencha todos os campos", FacesMessage.SEVERITY_WARN);
				}
			}else {
				UtilFaces.addMensagemFaces("Usuário já cadastrado ", FacesMessage.SEVERITY_WARN);
			}
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Erro ao consultar o colaborador, verifique o CPF informado ", FacesMessage.SEVERITY_ERROR);
		}
	}

	public void atualizarMunicipios(){
		municipios =  municipioDao.listarPorUf(uf, null);
	}
	
	private void atualizarUfMunicipioColaborador(){
		uf = colaborador.getMunicipio().getUf();
		municipios =  municipioDao.listarPorUf(uf, null);
	}

	public List<SelectItem> getUfs(){
		return UtilFaces.getListEnum(EnumUf.values());
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public String getSenha1() {
		return senha1;
	}

	public void setSenha1(String senha1) {
		this.senha1 = senha1;
	}

	public String getSenha2() {
		return senha2;
	}

	public void setSenha2(String senha2) {
		this.senha2 = senha2;
	}

	public String getConfirmarSenha() {
		return confirmarSenha;
	}

	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public EnumPapelUsuario getPapel() {
		return papel;
	}

	public void setPapel(EnumPapelUsuario papel) {
		this.papel = papel;
	}

	public List<Colaborador> getColaboradores() {
		return colaboradores;
	}

	public List<SelectItem> getTiposSexo() {
		return UtilFaces.getListEnum(EnumTipoSexo.values());
	}

	public List<SelectItem> getTiposColaboradores() {
		return UtilFaces.getListEnum(EnumTipoColaborador.values());
	}

	public List<SelectItem> getPapeis(){
		return UtilFaces.getListEnum(EnumPapelUsuario.values());
	}

	public String getFiltroGlobal() {
		return filtroGlobal;
	}

	public void setFiltroGlobal(String filtroGlobal) {
		this.filtroGlobal = filtroGlobal;
	}

	public EnumUf getUf() {
		return uf;
	}

	public void setUf(EnumUf uf) {
		this.uf = uf;
	}

	public List<Municipio> getMunicipios() {
		return municipios;
	}

	public void setMunicipios(List<Municipio> municipios) {
		this.municipios = municipios;
	}

	public Colaborador getColaboradorAlterar() {
		return colaboradorAlterar;
	}

	public void setColaboradorAlterar(Colaborador colaboradorAlterar) {
		this.colaboradorAlterar = colaboradorAlterar;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
