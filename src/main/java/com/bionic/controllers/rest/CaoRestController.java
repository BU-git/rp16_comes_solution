package com.bionic.controllers.rest;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Pavel Boiko on 09.04.2016.
 */

@RestController
@RequestMapping("rest/api/cao")
public class CaoRestController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public FileSystemResource getCao() {
        return new FileSystemResource("D:\\Forsent\\TDA\\rp16_comes_solution\\src\\main\\resources\\files\\CAO.pdf");
    }
}
