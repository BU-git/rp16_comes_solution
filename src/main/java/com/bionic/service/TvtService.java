package com.bionic.service;

import com.bionic.dto.TvtBuildDTO;
import com.bionic.dto.TvtPaidDTO;

import java.util.List;

/**
 * @author Pavel Boiko
 */
public interface TvtService {

    List<TvtPaidDTO> getTvtPaidForYear(int userId, int year, int endingPeriod);

    TvtPaidDTO getTvtPaidForPeriod(int userId, int year, int period);

    List<TvtBuildDTO> getTvtBuildForYear(int userId, int year, int endingPeriod);

    TvtBuildDTO getTvtBuildForPeriod(int userId, int year, int period);
}
