package com.bionic.controllers.report;

import com.bionic.dao.UserDao;
import com.bionic.model.User;
import com.bionic.service.ReportService;
import com.bionic.service.UserService;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dima Budko
 */


@Controller
@RequestMapping(value = "summary/{year}/{number}")
public class ReportController {

    @Autowired
    private UserService userService;

    @Autowired
    private ReportService reportService;

//    @RequestMapping(value = "/Period.xls",method = RequestMethod.GET)
//    public ModelAndView generateExcelReport(ModelMap modelMap, ModelAndView modelAndView, @PathVariable("year") int year,@PathVariable("number") int number) {
//        ArrayList<ReportDTO> dataBeanList = new ArrayList<>();
//        ReportDTO data1 = new ReportDTO();
//        ReportDTO total = new ReportDTO();
//        data1.setRides("First ride");
//        data1.setTotalDays(10);
//        Integer totalDays = 0;
//        Integer totalTimes = 0 ;
//        Double allowences = 0.0;
//        totalDays = totalDays + data1.getTotalDays();
//        data1.setTotalTimes(45);
//        totalTimes = totalTimes + data1.getTotalTimes();
//        data1.setAllowences(500.0);
//        allowences = allowences + data1.getAllowences();
//        dataBeanList.add(data1);
//        data1 = new ReportDTO();
//        data1.setRides("Second ride");
//        data1.setTotalDays(5);
//        totalDays = totalDays + data1.getTotalDays();
//        data1.setTotalTimes(30);
//        totalTimes = totalTimes + data1.getTotalTimes();
//        data1.setAllowences(400.0);
//        allowences = allowences + data1.getAllowences();
//        dataBeanList.add(data1);
//        data1 = new ReportDTO();
//        data1.setRides("Third ride");
//        data1.setTotalDays(13);
//        totalDays = totalDays + data1.getTotalDays();
//        data1.setTotalTimes(40);
//        totalTimes = totalTimes + data1.getTotalTimes();
//        data1.setAllowences(450.0);
//        allowences = allowences + data1.getAllowences();
//        dataBeanList.add(data1);
//        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataBeanList,false);
//       //  Object[][] data = {{"Berne", new Integer(22), "Bill Ott", "250 - 20th Ave."}};
//        modelMap.put("datasource", beanColDataSource);
//        modelMap.put("format", "xls");
//        modelMap.put("period","week 1-4");
//        modelMap.put("name","John Doe");
//        modelMap.put("contractHours",40);
//        modelMap.put("totalDays",totalDays);
//        modelMap.put("totalTimes",totalTimes);
//        modelMap.put("allowences",allowences);
//        modelAndView = new ModelAndView("rpt_Period", modelMap);
//        return modelAndView;
//    }

    private static final int NUMBER_OF_WEEKS_IN_PERIOD = 4;

    @RequestMapping(value = "/Period.xls",method = RequestMethod.GET)
    public ModelAndView generateExcelReport(ModelMap modelMap, ModelAndView modelAndView, @PathVariable("year") int year,@PathVariable("number") int number) {
        User user = userService.findById(35);
        List<ReportDTO> dataBeanList = reportService.getReportList(user,year,number);
        int startWeek = number * NUMBER_OF_WEEKS_IN_PERIOD + 1;
        int endWeek = startWeek + NUMBER_OF_WEEKS_IN_PERIOD - 1;
        Integer totalDays = 0;
        Integer totalTimes = 0 ;
        Double allowences = 0.0;
        for (ReportDTO reportDTO : dataBeanList) {
            if (reportDTO.getTotalDays() != null) {
                totalDays = totalDays + reportDTO.getTotalDays();
            }
            if (reportDTO.getTotalTimes() != null) {
                totalTimes = totalTimes + reportDTO.getTotalTimes();
            }
            if (reportDTO.getAllowences() != null) {
                allowences = allowences + reportDTO.getAllowences();
            }
        }

        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataBeanList,false);
        //  Object[][] data = {{"Berne", new Integer(22), "Bill Ott", "250 - 20th Ave."}};
        modelMap.put("datasource", beanColDataSource);
        modelMap.put("format", "xls");
        modelMap.put("period","Week " +  startWeek + "-" +  endWeek);
        modelMap.put("name",user.getFirstName());
        modelMap.put("contractHours",user.getContractHours());
        modelMap.put("totalDays",totalDays);
        modelMap.put("totalTimes",totalTimes);
        modelMap.put("allowences",allowences);
        modelAndView = new ModelAndView("rpt_Period", modelMap);
        for (ReportDTO reportDTO :dataBeanList) {
            System.out.println(reportDTO.getRides());
        }
        return modelAndView;
    }
}
