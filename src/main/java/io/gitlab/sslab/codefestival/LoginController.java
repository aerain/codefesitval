package io.gitlab.sslab.codefestival;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

public class LoginController {
    @RequestMapping(value="/users")
    @ResponseBody
    public ModelAndView login() {
        ModelAndView view = new ModelAndView();
        view.setViewName("loginPage");
        view.addObject("name", "login");
        return view;
    }
}