package com.ruoyi.hh.util;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.DateUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @ClassName WordUtil
 * @Description 使用Freemarker生成Word文档工具类
 * @Author ruoyi
 * @Date 2022/6/15 10:06
 **/


public class WordUtil {
    /**
     * 使用Freemarker自动生成Word文档
     * @param dataMap   保存Word文档中所需要的数据
     * @param templatePath  模板文件的绝对路径
     * @param templateFile  模板文件的名称
     * @param generateFile  生成文件的路径+名称
     * @throws Exception
     */

/** 原版生成word方法 */
    public static void generateWord(Map<String, Object> dataMap, String templatePath,String templateFile, String generateFile) {
        // 设置FreeMarker的版本
        Configuration configuration = new Configuration(new Version("2.3.28"));
        // 设置Freemarker的编码格式
        configuration.setDefaultEncoding("UTF-8");
        Writer out = null;
        try{
            // 设置FreeMarker生成Word文档所需要的模板的路径
            configuration.setDirectoryForTemplateLoading(new File(templatePath));
            // 设置FreeMarker生成Word文档所需要的模板名称
            Template t = configuration.getTemplate(templateFile, "UTF-8");
            // 创建一个Word文档的输出流
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(generateFile)), "UTF-8"));
            //FreeMarker使用Word模板和数据生成Word文档
            t.process(dataMap, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (out != null) {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /** 新的生成word方法 */
    public static void CreateWord(HttpServletResponse resp, HttpServletRequest req,Map<String, Object> dataMap, String templatePath,String templateFile, String generateFile) {
        // 设置FreeMarker的版本
        Configuration configuration = new Configuration(new Version("2.3.28"));
        // 设置Freemarker的编码格式
        configuration.setDefaultEncoding("UTF-8");

        String str = DateUtils.dateTimeNow() + ".doc";
        Writer out = null;
        try{
            // 设置FreeMarker生成Word文档所需要的模板的路径
            configuration.setDirectoryForTemplateLoading(new File(templatePath));
            // 设置FreeMarker生成Word文档所需要的模板名称
            Template t = configuration.getTemplate(templateFile, "UTF-8");
            // 创建一个Word文档的输出流
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(generateFile)), "UTF-8"));
            //FreeMarker使用Word模板和数据生成Word文档
            t.process(dataMap, out);
            //下载生成好的Word文档
            downloadFile(generateFile,str,resp,req);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (out != null) {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /** 新的生成word方法 */
    public static void CreateWordWithPath(HttpServletResponse resp, HttpServletRequest req,Map<String, Object> dataMap, String templateFile, String generateFile) {
        // 设置FreeMarker的版本
        Configuration configuration = new Configuration(new Version("2.3.28"));
        String templatePath=RuoYiConfig.getwordTemplatePath();
        // 设置Freemarker的编码格式
        configuration.setDefaultEncoding("UTF-8");

        String str = DateUtils.dateTimeNow() + ".doc";
        Writer out = null;
        try{
            // 设置FreeMarker生成Word文档所需要的模板的路径
            configuration.setDirectoryForTemplateLoading(new File(templatePath));
            // 设置FreeMarker生成Word文档所需要的模板名称
            Template t = configuration.getTemplate(templateFile, "UTF-8");
            // 创建一个Word文档的输出流
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(generateFile)), "UTF-8"));
            //FreeMarker使用Word模板和数据生成Word文档
            t.process(dataMap, out);
            //下载生成好的Word文档
            downloadFile(generateFile,str,resp,req);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (out != null) {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /********************************************/
    /**
     * 下载文件
     * @param path 文件的位置
     * @param fileName 自定义下载文件的名称
     * @param resp http响应
     * @param req http请求
     */
    public static void downloadFile(String path, String fileName, HttpServletResponse resp, HttpServletRequest req){
        System.out.println("开始下载到本地");
        try {
            File file = new File(path);
            /**
             * 中文乱码解决
             */
            String type = req.getHeader("User-Agent").toLowerCase();
            if(type.indexOf("firefox")>0 || type.indexOf("chrome")>0){
                /**
                 * 谷歌或火狐
                 */
                fileName = new String(fileName.getBytes("utf-8"), "iso8859-1");
            }else{
                /**
                 * IE
                 */
                fileName = URLEncoder.encode(fileName, "utf-8");
            }
            // 设置响应的头部信息
            resp.setHeader("content-disposition", "attachment;filename=" + fileName);
            // 设置响应内容的类型
            resp.setContentType(getFileContentType(fileName)+"; charset= utf-8");
            // 设置响应内容的长度
            resp.setContentLength((int) file.length());
            // 输出
            outStream(new FileInputStream(file), resp.getOutputStream());
        } catch (Exception e) {
            System.out.println("执行downloadFile发生了异常：" + e.getMessage());
        }
    }

    /**
     * 文件的内容类型
     */
    private static String getFileContentType(String name){
        String result = "";
        String fileType = name.toLowerCase();
        if (fileType.endsWith(".png")) {
            result = "image/png";
        } else if (fileType.endsWith(".gif")) {
            result = "image/gif";
        } else if (fileType.endsWith(".jpg") || fileType.endsWith(".jpeg")) {
            result = "image/jpeg";
        } else if(fileType.endsWith(".svg")){
            result = "image/svg+xml";
        }else if (fileType.endsWith(".doc")) {
            result = "application/msword";
        } else if (fileType.endsWith(".xls")) {
            result = "application/x-excel";
        } else if (fileType.endsWith(".zip")) {
            result = "application/zip";
        } else if (fileType.endsWith(".pdf")) {
            result = "application/pdf";
        } else {
            result = "application/octet-stream";
        }
        return result;
    }


    /**
     * 基础字节数组输出
     */
    private static void outStream(InputStream is, OutputStream os) {
        try {
            byte[] buffer = new byte[10240];
            int length = -1;
            while ((length = is.read(buffer)) != -1) {
                os.write(buffer, 0, length);
                os.flush();
            }
        } catch (Exception e) {
            System.out.println("执行 outStream 发生了异常：" + e.getMessage());
        } finally {
            try {
                os.close();
            } catch (IOException e) {
            }
            try {
                is.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 检查存储生成文件的路径是否存在，如果不存在则新建路径.
     * @param directory the directory name, like '\dir-name'
     */
    public static void CheckDownloadPath(String directory){
        File path=new File(RuoYiConfig.getDownloadPath()+directory);
        if(!path.exists()){
            path.mkdirs();
        }
    }


}

