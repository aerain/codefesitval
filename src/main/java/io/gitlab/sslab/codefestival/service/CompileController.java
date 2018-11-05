package io.gitlab.sslab.codefestival.service;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CompileController {

    private Writer writer = null;

    @RequestMapping(
        value="/compile", 
        method=RequestMethod.GET
    )
    @ResponseBody
    public String getCompile(@RequestParam String code) {
        String babo = "";
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("compile/test.js"), "UTF-8"));
            writer.write(code);
        } catch (IOException ex) {

        } finally {
            try {
                writer.close();
            } catch(Exception e) {}
        }
        try {
            Process node = 
            new ProcessBuilder()
                .command("node", "compile/test.js")
                .inheritIO()
                .start();
            System.out.println(node.getInputStream().toString() + node.getOutputStream().toString());
            
            node.waitFor();

        } catch(Exception ex) {

        } finally {
            return babo;
        }
    }

    @RequestMapping(value="/compile", method=RequestMethod.POST)
    @ResponseBody
    public String postCompile(@RequestBody String code) {
        return code;
    }
}