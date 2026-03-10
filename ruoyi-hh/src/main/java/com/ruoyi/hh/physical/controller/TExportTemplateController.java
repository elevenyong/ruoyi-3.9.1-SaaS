package com.ruoyi.hh.physical.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;

@RestController
@RequestMapping("/exportTemplate")
@Api(value = "导出数据模板接口", tags = "SYS导出数据模板接口")
public class TExportTemplateController {

    @ApiOperation("导出数据模板:工作面边界_模板.xlsx,导线点_模板.xlsx,煤层底板点_模板.xlsx")
    @GetMapping("/{fileName}")
    public void fileDownload(HttpServletResponse response, @PathVariable String fileName) throws IOException {

        File file = getFile(fileName);

        // 清空 response
        response.reset();
        response.setCharacterEncoding("UTF-8");

        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
        response.setContentType("application/octet-stream");

        // 将文件读到输入流中
        try (InputStream is = new BufferedInputStream(Files.newInputStream(file.toPath()))) {

            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());

            byte[] buffer = new byte[1024];
            int len;

            //从输入流中读取一定数量的字节，并将其存储在缓冲区字节数组中，读到末尾返回-1
            while ((len = is.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }

            outputStream.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static File getFile(String fileName) throws IOException {
        File file;
        ApplicationHome applicationHome = new ApplicationHome(TExportTemplateController.class);
        String path = applicationHome.getSource().getParentFile().toString();
        System.out.println("absolutePath:" + path);
        File file1 = new File(path + File.separator + "dataTemplate" + File.separator + fileName);
        if (!file1.exists()) {
            throw new FileNotFoundException("当前下载的文件不存在，请检查路径是否正确");
        } else {
            file = file1;
        }
        return file;
    }

}
