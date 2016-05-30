package com.bionic.service;

import com.bionic.controllers.report.ReportDTO;
import com.bionic.model.Ride;
import com.bionic.model.Shift;
import com.bionic.model.User;

import java.util.List;

/**
 * @author Dima Budko
 */
public interface ReportService {
    List<ReportDTO> getReportList(User user, int year, int period);
    public double getAllowances(ReportDTO reportDTO,Shift shift);
}
