package com.bionic.service;

import com.bionic.dto.AllowancesDTO;
import com.bionic.dto.ConsignmentFeeDTO;
import com.bionic.model.Shift;
import com.bionic.model.User;

import java.util.List;

/**
 * @author Dima Budko
 */
public interface ReportService {
    List<AllowancesDTO> getReportList(User user, int year, int period);
    List<ConsignmentFeeDTO> getConsigmentList(User user, int year, int period);
}
