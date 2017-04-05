package com.quchwe.gd.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by quchwe on 2017/3/30 0030.
 */
@Controller
@RequestMapping(value = "/index")
public class IndexController {

    public String index(){
        return "/index";
    }
}
