package com.bionic.controllers.report;

import com.bionic.dao.UserKeyDao;
import com.bionic.dto.AllowancesDTO;
import com.bionic.dto.OvertimeDTO;
import com.bionic.exception.auth.impl.UserNotExistsException;
import com.bionic.model.User;
import com.bionic.model.UserKey;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.bionic.service.util.MonthCalculator.getMonthName;
import static com.bionic.service.util.PeriodCalculator.NUMBER_OF_WEEKS_IN_PERIOD;

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

    @Autowired
    private UserKeyDao userKeyDao;

    @RequestMapping(value = "/TVT.xlsx", method = RequestMethod.GET)
    public ModelAndView tvtExcelReport(ModelMap modelMap, ModelAndView modelAndView, @PathVariable("userId") int userId,
                                       @PathVariable("year") int year, @PathVariable("number") int number,
                                       @RequestParam("key") long key) throws UserNotExistsException {

        UserKey userKey = userKeyDao.findBySecretForReport(key);
        if (userKey != null) {



            modelAndView = new ModelAndView("rtp_TVT", modelMap);
            return modelAndView;

        } else {
         throw new UserNotExistsException();
        }
    }

    @RequestMapping(value = "/Allowances.xlsx",method = RequestMethod.GET)
    public ModelAndView allowancesExcelReport(ModelMap modelMap, ModelAndView modelAndView, @PathVariable("userId") int userId,
                                              @PathVariable("year") int year, @PathVariable("number") int number,
                                              @RequestParam("key") long key) throws UserNotExistsException {

        UserKey userKey = userKeyDao.findBySecretForReport(key);
        if (userKey != null) {
            User user = userService.findById(userId);
            List<AllowancesDTO> dataBeanList = reportService.getReportList(user, year, number);
            int startWeek = number * NUMBER_OF_WEEKS_IN_PERIOD + 1;
            int endWeek = startWeek + NUMBER_OF_WEEKS_IN_PERIOD - 1;
            Integer totalDays = 0;
            Integer totalTimes = 0;
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

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataBeanList, false);
            modelMap.put("datasource", beanColDataSource);
            modelMap.put("format", "xlsx");
            if (user.isFourWeekPayOff()) {
                modelMap.put("reportType", "PERIOD OVERVIEW");
                modelMap.put("periodName", "Period:");
                modelMap.put("period", "Week " + startWeek + "-" + endWeek);
            } else {
                modelMap.put("reportType", "MONTHLY OVERVIEW");
                modelMap.put("periodName", "Month:");
                modelMap.put("period", getMonthName(number));
            }
            modelMap.put("name", user.getFirstName());
            modelMap.put("contractHours", user.getContractHours());
            modelMap.put("totalDays", totalDays);
            modelMap.put("totalTimes", totalTimes);
            modelMap.put("allowances", allowances);
            modelAndView = new ModelAndView("rpt_Allowances", modelMap);
            userKeyDao.delete(userKey);
            return modelAndView;
        } else {
            throw new UserNotExistsException();
        }
    }

    @RequestMapping(value = "/Overtime.xlsx",method = RequestMethod.GET)
    public ModelAndView overtimeExcelReport(ModelMap modelMap, ModelAndView modelAndView,@PathVariable("userId") int userId,
                                            @PathVariable("year") int year,@PathVariable("number") int number,
                                            @RequestParam("key") long key) throws UserNotExistsException {
        UserKey userKey = userKeyDao.findBySecretForReport(key);
        if (userKey != null) {
            User user = userService.findById(userId);
            List<OvertimeDTO> overtimeWeeks;
            int startWeek = number * NUMBER_OF_WEEKS_IN_PERIOD + 1;
            int endWeek = startWeek + NUMBER_OF_WEEKS_IN_PERIOD - 1;

            if (user.isFourWeekPayOff()) {
                overtimeWeeks = overtimeService.getOvertimeForPeriod(userId, year, number);
            } else {
                overtimeWeeks = overtimeService.getOvertimeForMonth(userId, year, number);
            }

            System.out.println(overtimeWeeks);

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(overtimeWeeks, false);

            modelMap.put("datasource", beanColDataSource);
            modelMap.put("format", "xlsx");
            modelMap.put("name", user.getFirstName());
            modelMap.put("overviewType", "Overtime");
            modelMap.put("contractHours", user.getContractHours());

            if (user.isFourWeekPayOff()) {
                modelMap.put("reportType", "PERIOD OVERVIEW");
                modelMap.put("periodName", "Period:");
                modelMap.put("period", "Week " + startWeek + "-" + endWeek);
            } else {
                modelMap.put("reportType", "MONTHLY OVERVIEW");
                modelMap.put("periodName", "Month:");
                modelMap.put("period", getMonthName(number));
            }

            OvertimeDTO overtimeSum = overtimeService.getOvertimeSum(overtimeWeeks);
            System.out.println(overtimeSum);

            modelMap.put("sumUnpaidLeaveHours", overtimeSum.getUnpaidLeaveHours());
            modelMap.put("sumPaidLeaveHours", overtimeSum.getPaidLeaveHours());
            modelMap.put("sumAtvHours", overtimeSum.getAtvHours());
            modelMap.put("sumHolidayHours", overtimeSum.getHolidayHours());
            modelMap.put("sumSickdayHours", overtimeSum.getSickdayHours());
            modelMap.put("sumWaitingdayHours", overtimeSum.getWaitingdayHours());
            modelMap.put("sumPaid100", overtimeSum.getPaid100());
            modelMap.put("sumPaid130", overtimeSum.getPaid130());
            modelMap.put("sumPaid150", overtimeSum.getPaid150());
            modelMap.put("sumPaid200", overtimeSum.getPaid200());
            modelMap.put("sumTotal", overtimeSum.getTotalHours());

            modelAndView = new ModelAndView("rpt_Overtime", modelMap);

            return modelAndView;
        } else {
            throw new UserNotExistsException();
        }
    }
}
