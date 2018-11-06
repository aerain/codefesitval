package io.gitlab.sslab.codefestival.compileservice;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Compile {
    private ProcessBuilder compileProcess;

    public static String compile(String lang, String codeCreator) {
        Compile compile = new Compile();
        String resultString = "";
        // 파일 경로 형식 예제 : compile/이청길14/이청길14-2018-11-10.java
        String filePath = "compile/" + codeCreator + "/";
        String fileName = codeCreator + "-" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        switch(lang) {
            case "c":
                resultString = compile.cCompile(filePath, fileName);
                break;
            case "cpp":
                compile.cppCompile(filePath, fileName);
                break;
            case "node":
                compile.node();
        }

        return resultString;
    }
    
    public String cCompile(String filePath, String fileName) {
        compileProcess = new ProcessBuilder().command("clang", "-O2", filePath + fileName, "-o", filePath + "a.out");

        return "";
    }

    public void cppCompile(String filePath, String fileName) {

    }

    public void node() {

    }
}