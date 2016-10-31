package br.com.ambientinformatica.fatesg.sgep.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.ambientinformatica.util.UtilLog;

public class UsuarioService implements UserDetailsService{

	private DataSource dataSource;

	private void registrarHistoricoLogin(Connection con, String cpfcnpj) throws SQLException{
		Date agora = new Date();
		PreparedStatement pstmtUsuario = con.prepareStatement("update colaborador set dataultimoacesso = ? where cpfcnpj = ?");
		pstmtUsuario.setTimestamp(1, new Timestamp(agora.getTime()));
		pstmtUsuario.setString(2, cpfcnpj);
		pstmtUsuario.execute();

		PreparedStatement pstmtHistorico = con.prepareStatement("insert into historicologin (id, data, colaborador_id) values ((select nextval('historico_login_seq')), ?, (select id from colaborador where cpfcnpj = ?))");
		pstmtHistorico.setTimestamp(1, new Timestamp(agora.getTime()));
		pstmtHistorico.setString(2, cpfcnpj);
		pstmtHistorico.execute();
	}

	@Override
	public UserDetails loadUserByUsername(String cpfcnpj) throws DataAccessException {
		try {
			Connection con = dataSource.getConnection();
			String sqlColaborador = "SELECT cpfcnpj AS username, senha as password, 'true' AS enabled FROM colaborador WHERE cpfcnpj = ?";

			String sqlPapeis = "select papeis as authority from colaborador_papeis where colaborador_id = (select id from colaborador where cpfcnpj = ?)";
			try{
				PreparedStatement pstmt = con.prepareStatement(sqlColaborador);
				pstmt.setString(1, cpfcnpj);
				ResultSet rs = pstmt.executeQuery();

				try {
					if (rs.next()) {
						List<GrantedAuthority> papeis = new ArrayList<GrantedAuthority>();
						PreparedStatement pstmtPapeis = con.prepareStatement(sqlPapeis);
						pstmtPapeis.setString(1, cpfcnpj);
						ResultSet rsPapeis = pstmtPapeis.executeQuery();
						UserDetails user;
						try {
							while (rsPapeis.next()) {
								papeis.add(new SimpleGrantedAuthority(rsPapeis.getString("authority")));
							}
							user = new UsuarioImpl(cpfcnpj, rs.getString("password"), rs.getBoolean("enabled"), true, true, true, papeis);
							registrarHistoricoLogin(con, cpfcnpj);
						} finally {
							rsPapeis.close();
						}
						return user;
					} else {
						throw new UsernameNotFoundException("Usuário " + cpfcnpj + " não encontrado");
					}
				} finally {
					rs.close();
				}
			}finally{
				con.close();
			}
		} catch (RuntimeException e) {
			UtilLog.getLog().error(e.getMessage(), e);
			throw new DataAccessExceptionImpl(e.getMessage(), e);
		} catch (SQLException e) {
			UtilLog.getLog().error(e.getMessage(), e);
			throw new DataAccessExceptionImpl(e.getMessage(), e);
		}
	}


	public DataSource getDataSource() {
		return dataSource;
	}


	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private static class DataAccessExceptionImpl extends DataAccessException{

		private static final long serialVersionUID = 1L;

		public DataAccessExceptionImpl(String msg, Exception e) {
			super(msg, e);
		}

	}

}