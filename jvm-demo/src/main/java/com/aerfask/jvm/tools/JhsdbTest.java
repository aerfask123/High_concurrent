package com.aerfask.jvm.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JhsdbTest {

    static class Test {
        static ObjectHolder staticObj = new ObjectHolder();
        ObjectHolder instanceObj = new ObjectHolder();

        void foo() {
            ObjectHolder localObj = new ObjectHolder();
            System.out.println("done");
        }
    }


    private static class ObjectHolder {
    }

    public static void main(String[] args) {
        Test test = new JhsdbTest.Test();
        test.foo();

        String catalinaHome = "D:\\Program Files\\apache-tomcat-9.0.7";
        String startupPath = catalinaHome + "\\bin\\startup.bat";
        try {
            ProcessBuilder pb = new ProcessBuilder("cmd", "/c", startupPath);
            // 假设我们已经有了上面的 Process 对象
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();

            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = errorReader.readLine()) != null) {
                System.err.println(line);
            }
            errorReader.close();
        } catch (IOException e) {
            System.out.println("error occured:" + e.getMessage());
            e.printStackTrace();
        }
    }
}
