package com.example.demo.utils;

import com.example.demo.model.Constant.Switcher;
import com.example.demo.model.Enum.SubMenu;

import java.io.*;

public class LogUtil {
    public static String read(String filepath) {
        File file = new File(filepath);

        try {
            InputStreamReader input = new InputStreamReader(new FileInputStream(file));

            BufferedReader bufferedReader = new BufferedReader(input);

            String line = null;
            line = bufferedReader.readLine();
            String result = "";
            while (line != null) {
                result += line + "\n";
                line = bufferedReader.readLine();
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 文件尾部追加内容
     *
     * @param filepath
     * @param content
     */
    public static void write(String filepath, String content) {
        FileWriter fw = null;
        try {
            //如果文件存在，则追加内容；如果文件不存在，则创建文件
            File f = new File(filepath);
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println(content);
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void errorLog(Exception e,String location) {
        errorLog(e, location, -100);
    }

    public static void errorLog(Exception e,String location, int code) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String info = sw.toString(); // stack trace as a string
        String time = "[" + TimeUtil.getTime() + "]\n";
        String strCode = "";
        if(code != -100){
            strCode = code + "\n";
        }
        location += "\n";
        String exception = time + location + strCode + info;
        String digestException = time + location + strCode + e.getMessage() + "\n";
        write(Switcher.FilePathSwitcher.exception_log_path, exception);
        write(Switcher.FilePathSwitcher.exception_digest_log_path, digestException);
    }

    public static void log(String location, String info) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        String time = "[" + TimeUtil.getTime() + "]\n";
        location += "\n";
        String out = time + location + info;
        write(Switcher.FilePathSwitcher.log_path, out);
    }
}