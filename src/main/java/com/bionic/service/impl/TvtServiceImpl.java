package com.bionic.service.impl;

import com.bionic.dto.TvtBuildDTO;
import com.bionic.dto.TvtPaidDTO;
import com.bionic.service.TvtService;
import com.bionic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pavel Boiko
 */
@Service
public class TvtServiceImpl implements TvtService {

    @Autowired
    private UserService userService;

    @Override
    public List<TvtPaidDTO> getTvtPaid(int userId, int year, int period) {

        List<TvtPaidDTO> tvt = new ArrayList<>();
        tvt.add(new TvtPaidDTO());

        return tvt;
    }

    @Override
    public List<TvtBuildDTO> getTvtBuild(int userId, int year, int period) {

        List<TvtBuildDTO> tvt = new ArrayList<>();
        tvt.add(new TvtBuildDTO());

        return tvt;
    }
}
