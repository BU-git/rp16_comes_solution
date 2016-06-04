package com.bionic.controllers.report;

import com.bionic.dto.AllowancesDTO;
import com.bionic.dto.OvertimeDTO;
import com.bionic.exception.shift.impl.ShiftsNotFoundException;
import com.bionic.model.User;
import com.bionic.service.OvertimeService;
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
import java.util.Date;
import java.util.List;

/**
 * @author Dima Budko
 */


@Controller
@RequestMapping(value = "summary/{userId}/{year}/{number}")
public class ReportController {

    @Autowired
    private UserService userService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private OvertimeService overtimeService;


    private static final int NUMBER_OF_WEEKS_IN_PERIOD = 4;

    @RequestMapping(value = "/Allowances.xlsx",method = RequestMethod.GET)
    public ModelAndView allowancesExcelReport(ModelMap modelMap, ModelAndView modelAndView,
                                              @PathVariable("userId") int userId, @PathVariable("year") int year, @PathVariable("number") int number) {

        User user = userService.findById(userId);
        List<AllowancesDTO> dataBeanList = reportService.getReportList(user,year,number);
        int startWeek = number * NUMBER_OF_WEEKS_IN_PERIOD + 1;
        int endWeek = startWeek + NUMBER_OF_WEEKS_IN_PERIOD - 1;
        Integer totalDays = 0;
        Integer totalTimes = 0 ;
        Double allowances = 0.0;
        for (AllowancesDTO allowancesDTO : dataBeanList) {
            if (allowancesDTO.getTotalDays() != null) {
                totalDays = totalDays + allowancesDTO.getTotalDays();
            }
            if (allowancesDTO.getTotalTimes() != null) {
                totalTimes = totalTimes + allowancesDTO.getTotalTimes();
            }
            if (allowancesDTO.getAllowances() != null) {
                allowances = allowances + allowancesDTO.getAllowances();
            }
        }

        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataBeanList,false);
        //  Object[][] data = {{"Berne", new Integer(22), "Bill Ott", "250 - 20th Ave."}};
        modelMap.put("datasource", beanColDataSource);
        modelMap.put("format", "xlsx");
        modelMap.put("period","Week " +  startWeek + "-" +  endWeek);
        modelMap.put("name",user.getFirstName());
        modelMap.put("contractHours",user.getContractHours());
        modelMap.put("totalDays",totalDays);
        modelMap.put("totalTimes",totalTimes);
        modelMap.put("allowances",allowances);
        modelAndView = new ModelAndView("rpt_Period", modelMap);
        return modelAndView;
    }

    @RequestMapping(value = "/Overtime.xlsx",method = RequestMethod.GET)
    public ModelAndView overtimeExcelReport(ModelMap modelMap, ModelAndView modelAndView,@PathVariable("userId") int userId, @PathVariable("year") int year,@PathVariable("number") int number) throws ShiftsNotFoundException {
        User user = userService.findById(userId);
        List<OvertimeDTO> dataBeanList = overtimeService.getOvertimeForPeriod(userId, year, number);
        System.out.println(dataBeanList);
        int startWeek = number * NUMBER_OF_WEEKS_IN_PERIOD + 1;
        int endWeek = startWeek + NUMBER_OF_WEEKS_IN_PERIOD - 1;
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataBeanList, false);

        modelMap.put("datasource", beanColDataSource);
        modelMap.put("format", "xlsx");
        modelMap.put("period","Week " +  startWeek + "-" +  endWeek);
        modelMap.put("name", user.getFirstName());
        modelMap.put("overviewType", "Overtime");
        modelMap.put("contractHours", user.getContractHours());

        modelMap.put("sumUnpaidLeaveHours", 0.0);
        modelMap.put("sumPaidLeaveHours", 0.0);
        modelMap.put("sumAtvHours", 0.0);
        modelMap.put("sumSickdayHours", 0.0);
        modelMap.put("sumWaitingdayHours", 0.0);
        modelMap.put("sumPaid100", 0.0);
        modelMap.put("sumPaid130", 0.0);
        modelMap.put("sumPaid150", 0.0);
        modelMap.put("sumPaid200", 0.0);
        modelMap.put("sumTotal", 0);

        modelAndView = new ModelAndView("rpt_Overtime", modelMap);

        return modelAndView;
    }
}
