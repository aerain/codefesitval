package io.gitlab.sslab.codefestival.compileservice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.Writer;

public class Compile {
    /**
     * compile
     * 
     * @param lang String 형. 사용할 언어 명시. Available(c, cpp, node, python)
     * @param createAuthor String 형. 
     * @return
     */
    public static String compile(final String lang, final String createAuthor, final String code) {
        StringBuilder resultStringBuilder = new StringBuilder();
        Compile compile = new Compile();

        // 파일 경로 형식 예제 : compile/이청길14/이청길14-2018-11-10.java
        String filePath = "compile/" + createAuthor;
        String fileName = createAuthor + "-" + new SimpleDateFormat("yy-MM-dd-kk-mm-ss").format(new Date()) + "." + lang;

        compile.hasFolder(filePath);
        compile.makeFile(filePath, fileName, code);
        
        try {
            switch(lang) {
                case "c":
                    resultStringBuilder = compile.cCompile(filePath, fileName);
                    break;
                case "cpp":
                    // resultStringBuilder = compile.cppCompile(filePath, fileName);
                    break;
                case "js":
                    resultStringBuilder = compile.runNode(filePath, fileName);
                    break;
                case "py":
                    break;
                case "java":
                    break;
                default:
                    resultStringBuilder.append("혼날래?");
            }
        } catch(RuntimeException ioe) { 
            resultStringBuilder.append("오류가 발생했습니다.");
        }
        
        System.out.println(resultStringBuilder.toString());

        return resultStringBuilder.toString();
    }
    
    public StringBuilder cCompile(final String filePath, final String fileName) {

        String executeFilePath = filePath + "/a.out";
        StringBuilder sb = new StringBuilder();
        Runnable cRun = new Runnable() {
            @Override
            public void run() {
                try {
                    final Process compile = 
                    new ProcessBuilder()
                        .command("clang", "-O2", filePath + "/" + fileName, "-o", executeFilePath)
                        .start();
                    
                    final Process execute =
                    new ProcessBuilder()
                        .command("bash", executeFilePath)
                        .start();
                    
                    getOutputStringBuilder(sb, execute);
                } catch (IOException io) {
                    throw new RuntimeException(io);
                }
            }
        };

        startSubProcess(cRun);
        return sb;
    }

    public String cppCompile(String filePath, String fileName) {
        return "c++였습니다.";
    }

    public StringBuilder runNode(final String filePath, final String fileName) {
        StringBuilder sb = new StringBuilder();
        
        Runnable jsRun = () -> {
            try {
                final Process execute = 
                new ProcessBuilder()
                .command("node", filePath + "/" + fileName).start();
                getOutputStringBuilder(sb, execute);
            } catch (IOException io) {
                throw new RuntimeException();
            }
        };

        startSubProcess(jsRun);

        return sb;
    }

    public void hasFolder(final String filePath) {
        File targetDir = new File(filePath);
        if(!targetDir.exists()) targetDir.mkdirs();
    }

    public void makeFile(final String filePath, final String fileName, final String code) {
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath + "/" + fileName), "UTF-8"));
            writer.write(code);
        } catch (IOException ex) {
            System.out.println("쓰기 문제 발생!");
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {}
        }
    }

    private static StringBuilder getOutputStringBuilder(StringBuilder sb, Process execute) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(execute.getInputStream()));
        String line;
        while((line=br.readLine()) != null) {
            sb.append(line);
            sb.append(System.getProperty("line.separator"));
        }

        return sb;
    }

    private static void startSubProcess(Runnable runnable) {
        Thread build = new Thread(runnable);
        build.start();
        try {
            build.join();
        } catch (InterruptedException io) {}
    }
}