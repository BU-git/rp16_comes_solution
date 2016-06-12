package com.bionic.service;

import com.bionic.dto.TvtBuildDTO;
import com.bionic.dto.TvtPaidDTO;

import java.util.List;

/**
 * @author Pavel Boiko
 */
public interface TvtService {

    List<TvtPaidDTO> getTvtPaid(int userId, int year, int period);

    List<TvtBuildDTO> getTvtBuild(int userId, int year, int period);
}
