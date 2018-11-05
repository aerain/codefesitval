package io.gitlab.sslab.codefestival.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.tomcat.util.http.fileupload.IOUtils;
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
        StringBuilder sb = new StringBuilder();
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
            ProcessBuilder node = 
            new ProcessBuilder()
                .command("node", "compile/test.js");

            final Process p = node.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line;
            while((line=br.readLine()) != null) sb.append(line);

        } catch(Exception ex) {

        } finally {
            System.out.println(sb.toString());
            return sb.toString();
        }
    }

    @RequestMapping(value="/compile", method=RequestMethod.POST)
    @ResponseBody
    public String postCompile(@RequestBody String code) {
        return code;
    }
}