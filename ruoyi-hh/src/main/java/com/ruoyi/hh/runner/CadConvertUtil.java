package com.ruoyi.hh.runner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class CadConvertUtil {

    public static String convertToolPath;
    public static String convertOutputPath;

    @Value("${CADConvert.CADConvertToolPath}")
    public static void setConvertToolPath(String convertToolPath) {
        CadConvertUtil.convertToolPath = convertToolPath;
    }

    public static void setConvertOutputPath(String convertOutputPath) {
        CadConvertUtil.convertOutputPath = convertOutputPath;
    }

    // 后面java程序，调用我们exe程序转换dwg文件格式.
    public static String CallMxFileConvert(String sDwgFile) {
        // 我们转所程序路径.
        //String command = convertToolPath+"/mxcadassembly.exe";
        String command = "D:/cadmxconvert/mxcadassembly.exe";
        Runtime rn = Runtime.getRuntime();
        Process process = null;
        // 转换参数。
        String sJsonParam = "{\"srcpath\":\"" + sDwgFile + "\"}";
        String[] sRetJson = new String[1];

        try {
            // 启动一个进程序，调用转换程序。
            process = rn.exec(new String[]{
                    command, sJsonParam
            });
            final InputStream ins = process.getInputStream();
            final InputStream errs = process.getErrorStream();
            //确保子进程与主进程之间inputStream不阻塞
            new Thread() {
                @Override
                public void run() {
                    BufferedReader inb = null;
                    String line = null;
                    try {
                        inb = new BufferedReader(new InputStreamReader(ins, "gbk"));
                        while ((line = inb.readLine()) != null) {
                            sRetJson[0] = line;
                            //System.out.println("executeMxExe - InputStream : " + line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (null != inb)
                                inb.close();
                            if (null != ins) {
                                ins.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
            //确保子进程与主进程之间ErrorStream不阻塞
            new Thread() {
                @Override
                public void run() {
                    BufferedReader errb = null;
                    String line = null;
                    try {
                        errb = new BufferedReader(new InputStreamReader(errs, "gbk"));
                        while ((line = errb.readLine()) != null) {
                            System.out.println("executeMxExe - ErrorStream : " + line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (null != errb)
                                errb.close();
                            if (null != errs) {
                                errs.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
            int retCode = process.waitFor();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            if (null != process) {
                OutputStream out = process.getOutputStream();
                if (null != out) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                process.destroy();
            }
        }

        // 返回转换结果。
        return sRetJson[0];
    }

    public static void main(String[] args) {
        String sDwg = "D:/cadmxconvert/2.dwg";
        String sRetJson = CallMxFileConvert(sDwg);
        System.out.println(sRetJson);
    }
}
