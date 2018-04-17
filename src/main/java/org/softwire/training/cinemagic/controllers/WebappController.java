package org.softwire.training.cinemagic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebappController {

    /**
     * These routes need to match the routes used by the react router
     */
    @RequestMapping(value = {"/", "/admin", "/admin/**", "/booking", "/booking/**"})
    public String index() {
        return "/index.html";
    }
}
