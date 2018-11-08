package io.gitlab.sslab.codefestival.compileservice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
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
        String filePath = "compile/" + createAuthor + "/" + lang;
        String fileName = createAuthor + "-" + new SimpleDateFormat("yy-MM-dd-kk-mm-ss").format(new Date()) + "." + lang;

        compile.hasFolder(filePath);
        compile.makeFile(filePath, fileName, code);

        // 자바 클래스명 맞춰주기 위해서
        if(lang.equals("java")) compile.makeFile(filePath, "Main.java", code);
        
        try {
            switch(lang) {
                case "c":
                    resultStringBuilder = compile.cCompile(filePath, fileName);
                    break;
                case "cpp":
                    resultStringBuilder = compile.cppCompile(filePath, fileName);
                    break;
                case "js":
                    resultStringBuilder = compile.runNode(filePath, fileName);
                    break;
                case "py":
                    resultStringBuilder = compile.runPython(filePath, fileName);
                    break;
                case "java":
                    resultStringBuilder = compile.javaCompile(filePath, "Main.java");
                    break;
                default:
                    resultStringBuilder.append("혼날래?");
            }
        } catch(RuntimeException ioe) { 
            resultStringBuilder.append("오류가 발생했습니다.");
        }
        
        System.out.println(resultStringBuilder.toString());

        return resultStringBuilder.toString().trim() != "" ? resultStringBuilder.toString() : "오류 발생!";
    }
    
    public StringBuilder cCompile(final String filePath, final String fileName) {

        String executeFilePath = filePath + "/a.out";
        StringBuilder sb = new StringBuilder();
        Runnable cRun = () -> {
            try {
                final Process compile = 
                new ProcessBuilder()
                    .command("gcc", "-O2", "-Wno-unused-result", filePath + "/" + fileName, "-o", executeFilePath)
                    .inheritIO()
                    .start();
                compile.waitFor();

                final Process execute =
                new ProcessBuilder()
                    .command("bash", "-c", executeFilePath)
                    .start();
                
                getOutputStringBuilder(sb, execute);

                execute.waitFor();

                deleteFile(executeFilePath);
            } catch (Exception io) {
                throw new RuntimeException(io);
            }
        };

        startSubProcess(cRun);
        return sb;
    }

    public StringBuilder cppCompile(String filePath, String fileName) {
        String executeFilePath = filePath + "/a.out";
        StringBuilder sb = new StringBuilder();
        Runnable cRun = () -> {
            try {
                final Process compile = 
                new ProcessBuilder()
                    .command("g++", "-std=c++11", "-O2", filePath + "/" + fileName, "-o", executeFilePath)
                    .inheritIO()
                    .start();
                compile.waitFor();

                final Process execute =
                new ProcessBuilder()
                    .command("bash", "-c", executeFilePath)
                    .start();
                
                getOutputStringBuilder(sb, execute);

                execute.waitFor();

                deleteFile(executeFilePath);
            } catch (Exception io) {
                throw new RuntimeException(io);
            }
        };

        startSubProcess(cRun);
        return sb;
    }

    public StringBuilder javaCompile(final String filePath, final String fileName) {
        StringBuilder sb = new StringBuilder();

        Runnable javaRun = () -> {
            try {
                final Process compile = 
                new ProcessBuilder()
                .command("javac", "-encoding", "UTF-8", "-Xstdout", filePath + "/errorCode.txt", filePath + "/" + fileName)
                // .inheritIO()
                .start();

                compile.waitFor();
                // System.out.println(sb.toString());
                File errorCapture = new File(filePath + "/errorCode.txt");
                if(errorCapture.exists()) {
                    FileReader fileReader = new FileReader(errorCapture);
                    BufferedReader bufReader = new BufferedReader(fileReader);
                    String line = "";
                    while((line = bufReader.readLine()) != null) {
                        sb.append(line);
                    }

                    if(errorCapture.delete()) {
                        System.out.println("파일 제거");
                    }
                    return;
                }

                final Process execute =
                new ProcessBuilder()
                .command("java", "-cp", filePath, "-Dfile.encoding=utf-8", "Main")
                .start();

                getOutputStringBuilder(sb, execute);

                execute.waitFor();

                deleteFile(filePath + "/" + "Main.class");
    
            } catch (Exception io){
                throw new RuntimeException();
            }
        };

        startSubProcess(javaRun);
        System.out.println(sb.toString());
        return sb;
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

    public StringBuilder runPython(final String filePath, final String fileName) {
        StringBuilder sb = new StringBuilder();
        
        Runnable pyRun = () -> {
            try {
                final Process execute = 
                new ProcessBuilder()
                .command("python3", filePath + "/" + fileName).start();
                getOutputStringBuilder(sb, execute);
            } catch (IOException io) {
                throw new RuntimeException();
            }
        };

        startSubProcess(pyRun);

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

    private static void deleteFile(String filePath) {
        File classFile = new File(filePath);
        if(classFile.exists()) {
            if(classFile.delete()) {
                System.out.println("제거");
            }
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