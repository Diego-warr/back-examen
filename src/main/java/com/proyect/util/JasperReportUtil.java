package com.proyect.util;

import com.proyect.entity.User;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JasperReportUtil {

    public static byte[] exportReportToPdf(Connection connection, String urlFile, Map<String, Object> parameters) throws Throwable {
        try {
            if (parameters == null) parameters = new HashMap<>();

            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(urlFile);
            JasperReportsContext jasperReportsContext = DefaultJasperReportsContext.getInstance();

            JRPropertiesUtil jrPropertiesUtil = JRPropertiesUtil.getInstance(jasperReportsContext);
            jrPropertiesUtil.setProperty("net.sf.jasperreports.query.executer.factory.plsql", "net.sf.jasperreports.engine.query.PlSqlQueryExecuterFactory");

            JasperPrint jasperPrint = null;

            if (connection != null && !connection.isClosed()) {
                jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
            }

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {

            throw new Throwable(e);
        }
    }


    public byte[] createPdfReport(final List<User> employees, String encargado) throws JRException, IOException {
        // Fetching the .jrxml file from the resources folder.

        File file = ResourceUtils.getFile("classpath:reports/cherry.jrxml");

        final InputStream stream = new FileInputStream(file);

        //return stream.readAllBytes();

        // Compile the Jasper report from .jrxml to .japser
        final JasperReport report = JasperCompileManager.compileReport(stream);

        // Fetching the employees from the data source.
        final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(employees);

        // Adding the additional parameters to the pdf.
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("encargado", encargado);

        // Filling the report with the employee data and additional parameters information.
        final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

        // Users can change as per their project requrirements or can take it as request input requirement.
        // For simplicity, this tutorial will automatically place the file under the "c:" drive.
        // If users want to download the pdf file on the browser, then they need to use the "Content-Disposition" technique.
        final String filePath = "\\";
        // Export the report to a PDF file.
        return JasperExportManager.exportReportToPdf(print);
    }
}
