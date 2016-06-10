package com.bionic.service;

import com.bionic.dto.AllowancesDTO;
import com.bionic.dto.ConsigmentFeeDTO;
import com.bionic.model.Shift;
import com.bionic.model.User;

import java.util.List;

/**
 * @author Dima Budko
 */
public interface ReportService {
    List<AllowancesDTO> getReportList(User user, int year, int period);
    List<ConsigmentFeeDTO> getConsigmentList(User user, int year, int period);
    public double getAllowances(AllowancesDTO allowancesDTO, Shift shift);
}
