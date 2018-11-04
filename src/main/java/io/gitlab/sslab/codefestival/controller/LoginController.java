package io.gitlab.sslab.codefestival.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    @RequestMapping(value="/login")
    @ResponseBody
    public ModelAndView login() {
        ModelAndView view = new ModelAndView();
        view.setViewName("login");
        view.addObject("name", "login");
        return view;
    }
}