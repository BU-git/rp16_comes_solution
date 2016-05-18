package com.bionic.controllers.report;

import com.bionic.dao.UserDao;
import com.bionic.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Dima Budko
 */
@Controller
public class PdfReportContoller {

    @Autowired
    private UserDao dao;

    @RequestMapping(value = "/Flower.pdf",method = RequestMethod.GET)
    public ModelAndView generatePdfReport(ModelMap modelMap, ModelAndView modelAndView) {
        Object[][] data = {{"Berne", new Integer(22), "Bill Ott", "250 - 20th Ave."}};
        modelMap.put("datasource", data);
        modelMap.put("format", "xls");
        modelMap.put("testParameter","testtest");
        modelAndView = new ModelAndView("rpt_Flower", modelMap);
        return modelAndView;
    }
}
