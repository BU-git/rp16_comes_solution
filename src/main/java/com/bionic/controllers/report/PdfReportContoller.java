package com.bionic.controllers.report;

import com.bionic.dao.UserDao;
import com.bionic.model.User;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

/**
 * @author Dima Budko
 */


@Controller
@RequestMapping(value = "summary/{year}/{number}")
public class PdfReportContoller {

    @Autowired
    private UserDao dao;

    @RequestMapping(value = "/Period.xls",method = RequestMethod.GET)
    public ModelAndView generatePdfReport(ModelMap modelMap, ModelAndView modelAndView, @PathVariable("year") int year,@PathVariable("number") int number) {
        ArrayList<Data> dataBeanList = new ArrayList<>();
        Data data1 = new Data();
        Data total = new Data();
        data1.setRides("First ride");
        data1.setTotalDays(10);
        Integer totalDays = 0;
        Integer totalTimes = 0 ;
        Double allowences = 0.0;
        totalDays = totalDays + data1.getTotalDays();
        data1.setTotalTimes(45);
        totalTimes = totalTimes + data1.getTotalTimes();
        data1.setAllowences(500.0);
        allowences = allowences + data1.getAllowences();
        dataBeanList.add(data1);
        data1 = new Data();
        data1.setRides("Second ride");
        data1.setTotalDays(5);
        totalDays = totalDays + data1.getTotalDays();
        data1.setTotalTimes(30);
        totalTimes = totalTimes + data1.getTotalTimes();
        data1.setAllowences(400.0);
        allowences = allowences + data1.getAllowences();
        dataBeanList.add(data1);
        data1 = new Data();
        data1.setRides("Third ride");
        data1.setTotalDays(13);
        totalDays = totalDays + data1.getTotalDays();
        data1.setTotalTimes(40);
        totalTimes = totalTimes + data1.getTotalTimes();
        data1.setAllowences(450.0);
        allowences = allowences + data1.getAllowences();
        dataBeanList.add(data1);
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataBeanList,false);
       //  Object[][] data = {{"Berne", new Integer(22), "Bill Ott", "250 - 20th Ave."}};
        modelMap.put("datasource", beanColDataSource);
        modelMap.put("format", "xls");
        modelMap.put("period","week 1-4");
        modelMap.put("name","John Doe");
        modelMap.put("contractHours",40);
        modelMap.put("totalDays",totalDays);
        modelMap.put("totalTimes",totalTimes);
        modelMap.put("allowences",allowences);
        modelAndView = new ModelAndView("rpt_Period", modelMap);
        return modelAndView;
    }
}
