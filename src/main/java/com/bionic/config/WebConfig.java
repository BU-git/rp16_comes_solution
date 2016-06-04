package com.bionic.config;

import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsViewResolver;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sasha Chepurnoi
 */

@Configuration
@EnableWebMvc
@ComponentScan("com.bionic.controllers")
@EnableAspectJAutoProxy
public class WebConfig {

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver
                = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/view/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public JasperReportsViewResolver getJasperReportsViewResolver() {
        JasperReportsViewResolver resolver = new JasperReportsViewResolver();
        resolver.setPrefix("classpath:/reports/");
        resolver.setSuffix(".jrxml");
        resolver.setReportDataKey("datasource");
        Map<String,Object> exporterParameters = new HashMap<>();
        exporterParameters.put("net.sf.jasperreports.engine.export.JRXlsExporterParameter.IS_DETECT_CELL_TYPE", Boolean.TRUE);
        resolver.setExporterParameters(exporterParameters);
        resolver.setViewNames("rpt_*");
        resolver.setViewClass(JasperReportsMultiFormatView.class);
        resolver.setOrder(0);
        return resolver;
    }
}
