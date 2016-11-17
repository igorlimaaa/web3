package br.com.fuctura.relatorio;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;

public class Relatorio {

    public void gerarRelatorio(String nomeRelatorio, HashMap paramRel, List listaRel) throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();

        //Inicialmente,	instanciamos a classe HttpServletResponse para que o usuário possa visualizar o	relatório.
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        ServletContext sc = (ServletContext) context.getExternalContext().getContext();

        String relPath = sc.getRealPath("/");
        String imagemLogo = "C:\\Users\\igor_\\Documents\\NetBeansProjects\\SistemaVendaWeb\\relatorios\\logo.png";

        paramRel.put("imagemLogo", imagemLogo);
        paramRel.put("nomeSistema", "Sistema de Vendas");
        paramRel.put("REPORT_LOCALE", new Locale("pt", "BR"));

        // A classe JRBeanCollectionDataSource transforma o arrayList num dataSource
        JasperPrint print = null;
        JRBeanCollectionDataSource rel = new JRBeanCollectionDataSource(listaRel);

        // A classe JasperFillManager gera o relatório
        print = JasperFillManager.fillReport("C:\\Users\\igor_\\Documents\\NetBeansProjects\\SistemaVendaWeb\\relatorios\\" + nomeRelatorio + ".jasper", paramRel, rel);

        //Mostra o relatório no formato PDF
        response.setContentType("application/pdf");
        response.addHeader("Content-disposition", "attachment; filename=\"" + nomeRelatorio + ".pdf\"");
        JasperExportManager.exportReportToPdfStream(print, response.getOutputStream());
        ServletOutputStream responseStream = response.getOutputStream();
        responseStream.flush();
        responseStream.close();

        FacesContext.getCurrentInstance().renderResponse();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void gerarRelatorioSub(String nomeRelatorio, HashMap paramRel, List listaRel, List listaRelSub, String subNomeRelatorio, String tipo) {
        FacesContext context = FacesContext.getCurrentInstance();

        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
       
        String path = "C:\\Users\\igor_\\Documents\\NetBeansProjects\\SistemaVendaWeb\\relatorios\\";
        String imagemLogo = "/logo.png";

        paramRel.put("imagemLogo", path + imagemLogo);
        paramRel.put("nomeSistema", "Sistema de Vendas");
        paramRel.put("REPORT_LOCALE", new Locale("pt", "BR"));
        subNomeRelatorio = "C:\\Users\\igor_\\Documents\\NetBeansProjects\\SistemaVendaWeb\\relatorios\\" + subNomeRelatorio + ".jasper";
        paramRel.put("subNomeRelatorio", subNomeRelatorio);

        try {
            JRBeanCollectionDataSource rel = new JRBeanCollectionDataSource(listaRel);
            JRBeanCollectionDataSource relSub = new JRBeanCollectionDataSource(listaRelSub);
            paramRel.put("relSub", relSub);
            JasperPrint print = JasperFillManager.fillReport("C:\\Users\\igor_\\Documents\\NetBeansProjects\\SistemaVendaWeb\\relatorios\\" + 
                    nomeRelatorio + ".jasper", paramRel, rel);

            if (tipo.equals("PDF")) {
                response.setContentType("application/pdf");
                response.addHeader("Content-disposition", "attachment;	filename=\"" + nomeRelatorio + ".pdf\"");
                JasperExportManager.exportReportToPdfStream(print, response.getOutputStream());
            
            } else if (tipo.equals("XLS")) {
                response.setHeader("application/excel", "Content-Type");
                response.setContentType("application/excel");
                response.setHeader("Content-disposition", "attachment; filename=" + nomeRelatorio + ".xls");
                OutputStream oStream = null;

                try {
                    oStream = response.getOutputStream();
                    JExcelApiExporter xls = new JExcelApiExporter();
                    xls.setParameter(JExcelApiExporterParameter.JASPER_PRINT, print);
                    xls.setParameter(JExcelApiExporterParameter.OUTPUT_STREAM, oStream);
                    xls.setParameter(JExcelApiExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
                    xls.setParameter(JExcelApiExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
                    xls.setParameter(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
                    xls.setParameter(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
                    xls.setParameter(JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
                    xls.setParameter(JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
                    xls.setParameter(JExcelApiExporterParameter.OFFSET_X, 0);
                    xls.setParameter(JExcelApiExporterParameter.OFFSET_Y, 0);
                    xls.exportReport();
                    oStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JRException e) {
                    e.printStackTrace();
                } finally {
                    if (oStream != null) {
                        try {
                            oStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            ServletOutputStream responseStream = response.getOutputStream();
            responseStream.flush();
            responseStream.close();
            FacesContext.getCurrentInstance().renderResponse();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
