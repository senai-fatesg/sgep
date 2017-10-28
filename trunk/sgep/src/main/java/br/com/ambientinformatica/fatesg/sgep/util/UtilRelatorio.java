package br.com.ambientinformatica.fatesg.sgep.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.util.Collection;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import br.com.ambientinformatica.util.UtilException;
import br.com.ambientinformatica.util.UtilLog;
import br.com.ambientinformatica.util.UtilRecurso;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRCsvDataSource;


public class UtilRelatorio {
	
	public static void gerarRelatorio(String caminhoRelatorio, Object dados, Object dados2, Map<String, Object> parametros)
			throws UtilException {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
			String nomePdf = caminhoRelatorio.replaceAll("/", "_");
			response.setContentType("application/pdf");
			response.addHeader("Content-disposition", "inline; filename=\"" + nomePdf + ".pdf\"");
			ServletOutputStream out = response.getOutputStream();
			gerarStreamRelatorio(caminhoRelatorio, dados, dados2, parametros, out);
			out.close();
			context.responseComplete();
		} catch (IOException e) {
			UtilLog.getLog().error(String.format("ERRO AO GERAR RELATORIO : %s %s",
					new Object[] { "/" + caminhoRelatorio, e.getMessage() }), e);
			throw new UtilException(String.format("ERRO AO GERAR RELATORIO: %s]", new Object[] { caminhoRelatorio }),
					e);
		}
	}
	
	public static void gerarStreamRelatorio(String caminhoRelatorio, Object dados, Object dados2, Map<String, Object> parametros,
			OutputStream out) throws UtilException {
		UtilLog.getLog().info(String.format("Gerando relatorio. Caminho: %s Parametros: %s",
				new Object[] { caminhoRelatorio, parametros }));
		String diretorioRelatorio = caminhoRelatorio.substring(0, caminhoRelatorio.indexOf("/"));
		try {
			URL urlDir = UtilRecurso.getClassLoader().getResource(diretorioRelatorio);
			if (urlDir == null) {
				UtilLog.getLog()
						.error("Recurso: " + diretorioRelatorio + " inexistente no diretório de classes do projeto");
				throw new UtilException(String.format("ERRO AO GERAR RELATORIO [Recurso: %s INEXISTENTE]",
						new Object[] { diretorioRelatorio }));
			}
			parametros.put("SUBREPORT_DIR", urlDir.getPath() + "/");
			JasperPrint impressao = null;
			InputStream is = UtilRecurso.getClassLoader().getResourceAsStream(caminhoRelatorio);
			if (is == null) {
				UtilLog.getLog()
						.error(String.format("Recurso: %s INEXISTENTE", new Object[] { "/" + caminhoRelatorio }));
				throw new UtilException(String.format("ERRO AO GERAR RELATORIO [Recurso: %s INEXISTENTE]",
						new Object[] { caminhoRelatorio }));
			}
			if (dados instanceof Connection) {
				impressao = JasperFillManager.fillReport(is, parametros, (Connection) dados);
			} else if (dados instanceof Collection) {
				JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource((Collection) dados);
				impressao = JasperFillManager.fillReport(is, parametros, dataSource);
			} else if (dados instanceof String) {
				ByteArrayInputStream streamDados = new ByteArrayInputStream(((String) dados).getBytes());
				JRCsvDataSource dataSource = new JRCsvDataSource(streamDados);
				dataSource.setUseFirstRowAsHeader(true);
				impressao = JasperFillManager.fillReport(is, parametros, dataSource);
			} else {
				String msg = "o parametro dados deve ser do tipo String(csv), Collection ou Connection";
				UtilLog.getLog().error(msg);
				throw new UtilException(msg);
			}

			JasperExportManager.exportReportToPdfStream(impressao, out);
		} catch (RuntimeException e) {
			UtilLog.getLog().error(String.format("ERRO AO GERAR RELATORIO : %s %s",
					new Object[] { "/" + caminhoRelatorio, e.getMessage() }), e);
			throw new UtilException(String.format("ERRO AO GERAR RELATORIO: %s]", new Object[] { caminhoRelatorio }),
					e);
		} catch (JRException e) {
			UtilLog.getLog().error(String.format("ERRO AO GERAR RELATORIO : %s %s",
					new Object[] { "/" + caminhoRelatorio, e.getMessage() }), e);
			throw new UtilException(String.format("ERRO AO GERAR RELATORIO: %s]", new Object[] { caminhoRelatorio }),
					e);
		}
	}

}
