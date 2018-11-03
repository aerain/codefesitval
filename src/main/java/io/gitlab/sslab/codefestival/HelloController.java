package io.gitlab.sslab.codefestival;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;    

@Controller
public class HelloController {
    @RequestMapping(value="/")
    public String index() {
        return "index";
    }

    @RequestMapping(value="/hello1")
    public String hello(Model model) {
        model.addAttribute("name", "swag");
        return "hello";
    }

    @RequestMapping(value="/hello2")
    public ModelAndView hello2() {
        ModelAndView view = new ModelAndView();
        view.setViewName("hello");
        view.addObject("name", "Hello2");
        return view;
    }
}
