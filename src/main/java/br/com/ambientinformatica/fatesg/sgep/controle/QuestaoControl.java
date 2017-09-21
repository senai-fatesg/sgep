package br.com.ambientinformatica.fatesg.sgep.controle;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.fatesg.api.entidade.Colaborador;
import br.com.ambientinformatica.fatesg.api.entidade.Disciplina;
import br.com.ambientinformatica.fatesg.sgep.entidade.AlternativaQuestao;
import br.com.ambientinformatica.fatesg.sgep.entidade.EnumAlternativa;
import br.com.ambientinformatica.fatesg.sgep.entidade.EnumDificuldade;
import br.com.ambientinformatica.fatesg.sgep.entidade.EnumEstado;
import br.com.ambientinformatica.fatesg.sgep.entidade.QuestaoTemplate;
import br.com.ambientinformatica.fatesg.sgep.persistencia.ColaboradorDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.DisciplinaDao;
import br.com.ambientinformatica.fatesg.sgep.persistencia.QuestaoTemplateDao;

@Controller("QuestaoControl")
@Scope("conversation")

public class QuestaoControl implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<QuestaoTemplate> questoes = new ArrayList<QuestaoTemplate>();

	private Colaborador professor = new Colaborador();

	private List<Colaborador> professores = new ArrayList<Colaborador>();

	private QuestaoTemplate questaoSelecionada = new QuestaoTemplate();

	private AlternativaQuestao item = new AlternativaQuestao();

	private boolean rdbUsrLogadoSelecionado;

	private boolean isAlternativaEdicao = false;

	private String nomeProfessor = "";
	
	private static final int CAPACIDADE_MAXIMA_ALTERNATIVAS = 5;

	@Autowired
	private QuestaoTemplateDao questaoDao;

	@Autowired
	private ColaboradorDao colaboradorDao;
	
	@Autowired
	private DisciplinaDao disciplinaDao;
	
	private String professorOuDisciplina;
	
	@PostConstruct
	public void init() {
	//	this.listar();
	}
	
	public void pesquisar() {
		try {
			questoes = questaoDao.listarPorProfessorOuDisciplina(professorOuDisciplina);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
			questoes  = new ArrayList<QuestaoTemplate>();
		}
	}

	public void confirmar() {
		try {
			if (this.isQuestaoValida(questaoSelecionada.getQuestao().getAlternativas().size())) {
				if(isRdbUsrLogadoSelecionado()){
					questaoSelecionada.getQuestao().setProfessor(this.getColaboradorLogado());
				}else {
					Colaborador colaborador = colaboradorDao.consultarPorCpfSgep(questaoSelecionada.getQuestao().getProfessor().getCpfCnpj());
					if (colaborador != null) {
						questaoSelecionada.getQuestao().setProfessor(colaborador);
					}
				}
				questaoSelecionada.getQuestao().setImagem(questaoSelecionada.getQuestao().getImagem());
				
				Disciplina disciplinaCorporatumNoSgep = disciplinaDao.consultarPorChaveDisciplinaCorporatum(questaoSelecionada.getQuestao().getDisciplina().getId());
				if(disciplinaCorporatumNoSgep == null){
					disciplinaCorporatumNoSgep = new Disciplina();
					disciplinaCorporatumNoSgep.setCargaHoraria(questaoSelecionada.getQuestao().getDisciplina().getCargaHoraria());
					disciplinaCorporatumNoSgep.setChaveDisciplinaCorporatum(questaoSelecionada.getQuestao().getDisciplina().getId());
					disciplinaCorporatumNoSgep.setCodigo(questaoSelecionada.getQuestao().getDisciplina().getCodigo());
					disciplinaCorporatumNoSgep.setNome(questaoSelecionada.getQuestao().getDisciplina().getNome());
					disciplinaDao.incluir(disciplinaCorporatumNoSgep);
				}
				
				
				questaoSelecionada.getQuestao().setDisciplina(disciplinaCorporatumNoSgep);
				
				questaoDao.alterar(questaoSelecionada);
				this.listar();
				questaoSelecionada = new QuestaoTemplate();
				RequestContext.getCurrentInstance()
				.showMessageInDialog(new FacesMessage("SGEP", "Questão gravada com sucesso!"));
				RequestContext.getCurrentInstance().execute("fecharDlgQuestao();");
			}
		} catch (Exception e) {
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage("SGEP", e.getMessage()));
		}
	}

	private boolean isColaboradorValidoPreenchido() {
		return questaoSelecionada.getQuestao().getProfessor() != null;
	}

	private Colaborador getColaboradorLogado() {
		Colaborador colaboradorLogado = colaboradorDao //consultar na base do sgep
				.consultarPorCpfSgep(UsuarioLogadoControl.getUsuarioConfigurado().getCpfCnpj());
		return colaboradorLogado;
	}

	public boolean verificarSalvarDadosPreenchidosPerdidos() {

		return isAlternativaEdicao() || this.item.getDescricao() != null;
	}
	
	//consultar idpai base sgep
	private boolean isProfessorJaCadastrado(Colaborador colaborador) {

		Colaborador colab = colaboradorDao.consultarPorCpfSgep(colaborador.getCpfCnpj());
		questaoSelecionada.getQuestao().setProfessor(colab != null ? colab : new Colaborador());
		return colab != null;
	}


	public void listar() {
		try {
			questoes = questaoDao.listar();
		} catch (Exception e) {
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage("SGEP", e.getMessage()));
		}
	}

	public void excluir() {
		try {
			questaoDao.excluirPorId(questaoSelecionada.getId());
			questaoSelecionada = new QuestaoTemplate();
			questoes = questaoDao.listar();
			FacesContext.getCurrentInstance().getExternalContext().
			redirect("/sgep/questao.jsf");
		} catch (Exception e) {
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage("SGEP", e.getMessage()));
		}
	}

	public void carregarQuestao() {
		try {
			this.questaoSelecionada = questaoDao.carregarQuestao(questaoSelecionada);
		} catch (Exception e) {
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage("SGEP", "Falha ao carregar objetos"));
		}
	}

	public List<Colaborador> completarColaboradores(String nome) {
		List<Colaborador> colaboradores = colaboradorDao.listarPorNome(nome);
		return colaboradores;
	}
	
	public List<Disciplina> completarDisciplinas(String nome) {
		List<Disciplina> disciplinas = disciplinaDao.listarPorNome(nome);
		return disciplinas;
	}

	public void adicionarItem() {
		try {
			if (isAlternativaValida()) {
				item.setOrdem(questaoSelecionada.getQuestao().getAlternativas(), this.isAlternativaEdicao);
				questaoSelecionada.getQuestao().addItem(item, isAlternativaEdicao);
				this.setAlternativaEdicao(false);
				this.compareToAlternativas();
			}
		} catch (Exception e) {
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage("SGEP", "Não foi possivel adicionar a alternativa.\n" + e.getMessage()));
		} finally {
			item = new AlternativaQuestao();
		}
	}

	private void compareToAlternativas() {
		List<AlternativaQuestao> alternativas = questaoSelecionada.getQuestao().getAlternativas();
		Comparator<AlternativaQuestao> comparator = new Comparator<AlternativaQuestao>() {
			public int compare(AlternativaQuestao o1, AlternativaQuestao o2) {
				return o1.getOrdem().compareTo(o2.getOrdem());
			};
		};

		Collections.sort(alternativas,comparator);
	}

	private boolean isAlternativaValida() {
		if(this.getItem().getDescricao().isEmpty()){
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage("SGEP", "O campo descrição da alternativa é de preenchimento obrigatório!"));
			return false;
		}
		if (this.isCapacidadeMaximaPreenchida(questaoSelecionada.getQuestao().getAlternativas().size())) {
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage("SGEP", "Atenção!\n Capacidade maxima de itens alcançada."));
			return false;
		}
		return true;
	}

	public boolean isCapacidadeMaximaPreenchida(int quantidadeQuestao) {
		return quantidadeQuestao == CAPACIDADE_MAXIMA_ALTERNATIVAS && !isAlternativaEdicao;
	}

	private boolean isQuestaoValida(int quantidadeQuestao) {
		if(!this.isColaboradorValidoPreenchido() && !isRdbUsrLogadoSelecionado()){
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage("SGEP", "É necessário preencher um colaborador válido!"));
			UtilFaces.setFocusComponente("completUsuario");
			return false;
		}

		if (!this.isCamposObrigatoriosPreenchidos()) {
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage("SGEP", "Campo(s) obrigatório(s) não informado(s)!"));
			return false;
		}

		if (!this.isCapacidadeMaximaPreenchida(quantidadeQuestao)) {
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage("SGEP", "É necessário adicionar " + CAPACIDADE_MAXIMA_ALTERNATIVAS + " alternativas para a questão!"));
			return false;
		}
		return true;
	}

	private boolean isCamposObrigatoriosPreenchidos() {
		return questaoSelecionada.getQuestao().getEnunciado() != ""
				&& questaoSelecionada.getQuestao().getAssunto() != ""
				&& questaoSelecionada.getQuestao().getEstado() != null && questaoSelecionada.getQuestao().getEstado().hashCode() > 0
				&& questaoSelecionada.getQuestao().getDificuldade()!= null && questaoSelecionada.getQuestao().getDificuldade().hashCode() > 0
				&& questaoSelecionada.getQuestao().getResposta() != null && questaoSelecionada.getQuestao().getResposta().hashCode() > 0;
	}

	public void editarItem(AlternativaQuestao alternativa) {
		try {
			this.item = alternativa;
			this.isAlternativaEdicao = true;
		} catch (Exception e) {
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage("SGEP", e.getMessage()));
		}
	}

	public void removerItem(AlternativaQuestao alternativa) {
		try {
			this.questaoSelecionada.getQuestao().removerItem(alternativa);
		} catch (Exception e) {
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage("SGEP", e.getMessage()));
		}
	}
	
	public void processarImagem(FileUploadEvent uploadEvent) {
        try {
            questaoSelecionada.getQuestao().setImagem(uploadEvent.getFile().getContents());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

	public StreamedContent getImage() {
	    if (questaoSelecionada.getQuestao().getImagem() == null) {
	        return new DefaultStreamedContent(); 
	    } else {
	        return new DefaultStreamedContent(new ByteArrayInputStream(questaoSelecionada.getQuestao().getImagem()), "image/png"); 
	    }
	}
	
	public List<SelectItem> getEstados() {
		return UtilFaces.getListEnum(EnumEstado.values());
	}

	public List<SelectItem> getDificuldades() {
		return UtilFaces.getListEnum(EnumDificuldade.values());
	}

	public void limpar() {
		questaoSelecionada = new QuestaoTemplate();
		item = new AlternativaQuestao();
	}

	public void limparItem() {
		item = new AlternativaQuestao();
	}

	public List<QuestaoTemplate> getQuestoes() {
		return questoes;
	}

	public Colaborador getProfessor() {
		return professor;
	}

	public void setProfessor(Colaborador professor) {
		this.professor = professor;
	}

	public List<Colaborador> getProfessores() {
		return professores;
	}

	public void setProfessores(List<Colaborador> professores) {
		this.professores = professores;
	}

	public AlternativaQuestao getItem() {
		return item;
	}

	public void setItem(AlternativaQuestao item) {
		this.item = item;
	}

	public QuestaoTemplate getQuestaoSelecionada() {
		return questaoSelecionada;
	}

	public void setQuestaoSelecionada(QuestaoTemplate questaoSelecionada) {
		this.questaoSelecionada = questaoSelecionada;
	}

	public String getNomeProfessor() {
		return nomeProfessor;
	}

	public void setNomeProfessor(String nomeProfessor) {
		this.nomeProfessor = nomeProfessor;
	}

	public boolean isRdbUsrLogadoSelecionado() {
		return rdbUsrLogadoSelecionado;
	}

	public void setRdbUsrLogadoSelecionado(boolean rdbUsrLogadoSelecionado) {
		this.rdbUsrLogadoSelecionado = rdbUsrLogadoSelecionado;
	}

	public List<SelectItem> getAlternativas() {
		return UtilFaces.getListEnum(EnumAlternativa.values());
	}

	public boolean isAlternativaEdicao() {
		return isAlternativaEdicao;
	}

	public void setAlternativaEdicao(boolean isAlternativaEdicao) {
		this.isAlternativaEdicao = isAlternativaEdicao;
	}

	public String getProfessorOuDisciplina() {
		return professorOuDisciplina;
	}

	public void setProfessorOuDisciplina(String professorOuDisciplina) {
		this.professorOuDisciplina = professorOuDisciplina;
	}

	
	}