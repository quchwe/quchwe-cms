package com.quchwe.gd.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@SpringBootApplication
@Controller

public class QuchweCmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuchweCmsApplication.class, args);
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)

    public String index(Model model) {


        model.addAttribute("singlePerson", "ope");
        return "index";
    }
}
