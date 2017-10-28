package br.com.ambientinformatica.fatesg.sgep.persistencia;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thoughtworks.xstream.XStream;

import br.com.ambientinformatica.fatesg.api.entidade.Colaborador;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.util.UtilLog;

@Repository("colaboradorDao")
public class ColaboradorDaoService extends PersistenciaJpa<Colaborador> implements ColaboradorDao, Serializable {

	private Client client = ClientBuilder.newClient();

	private WebTarget target = client.target("http://inpai.com.br/corporatum/service/colaborador");

	private static final long serialVersionUID = 1L;

	@Override
	@Transactional
	public void incluir(Colaborador clb){
		Colaborador colaborador = new Colaborador();
		try{
			colaborador.setCelular(clb.getCelular());
			colaborador.setCep(clb.getCep());
			colaborador.setCpfCnpj(clb.getCpfCnpj());
			colaborador.setCursos(clb.getCursos());
			colaborador.setDataAlteracaoSenha(clb.getDataAlteracaoSenha());
			colaborador.setDataCriacao(clb.getDataCriacao());
			colaborador.setDataUltimoAcesso(clb.getDataUltimoAcesso());
			colaborador.setDisciplinas(clb.getDisciplinas());
			colaborador.setEmail(clb.getEmail());
			colaborador.setEndereco(clb.getEndereco());
			colaborador.setHistorico(clb.getHistorico());
			colaborador.setMunicipio(clb.getMunicipio());
			colaborador.setNome(clb.getNome());
			colaborador.setPapeis(clb.getPapeis());
			colaborador.setReservista(clb.getReservista());
			colaborador.setRg(clb.getRg());
			colaborador.setSenhaCriptografada(clb.getSenha());
			colaborador.setTelefone(clb.getTelefone());
			colaborador.setTipo(clb.getTipo());
			colaborador.setTipoSexo(clb.getTipoSexo());
			colaborador.setTituloEleitor(clb.getTituloEleitor());
			em.persist(colaborador);
		}catch(Exception e){
			UtilLog.getLog().info("Não foi possível persistir o colaborador. Log:\n "+ e.getMessage());
		}
	}

	public Colaborador consultarPorIdPaiSgep(int idPai){
		return (Colaborador) em.createQuery("FROM Colaborador as colab WHERE colab.idColaboradorPai = :idPai")
				.setParameter("idPai", idPai).getSingleResult();
	}

	@Override
	@RequestMapping("/colaborador")
	public Colaborador consultarPorCpf(String cpf) {
		String conteudo = target.path("/listarPorCPFNovo/" + cpf).request().get(String.class);
		return (Colaborador) new XStream().fromXML(conteudo);
	}

	@SuppressWarnings("unchecked")
	@Override
	@RequestMapping("/colaborador")
	public List<Colaborador> listarPorNome(String nome) {
		String conteudo = target.path("/listarPorNome/" + nome).request().get(String.class);
		return (List<Colaborador>) new XStream().fromXML(conteudo);
	}

	@Override
	public Colaborador consultarPorCpfSgep(String cpf) throws PersistenciaException{
		try {
			Query query = em.createQuery("select c from Colaborador c left join fetch c.municipio m WHERE c.cpfCnpj = :cpf");
			query.setParameter("cpf", cpf);
			query.setMaxResults(1);
			List<Colaborador> colaboradores = query.getResultList();
			if (colaboradores.isEmpty()) {
				return null;
			}else {
				return colaboradores.get(0);
			}
		} catch (Exception e) {
			UtilLog.getLog().error(e.getMessage(), e);
			throw new PersistenciaException("Erro ao consultar colaborador por cpf", e);
		}
	}

}
