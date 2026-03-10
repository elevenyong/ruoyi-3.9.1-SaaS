package com.ruoyi.hh.physical.controller;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.framework.config.ServerConfig;
import com.ruoyi.hh.physical.domain.TProjectFile;
import com.ruoyi.hh.physical.service.ITProjectFileService;
import com.ruoyi.hh.runner.CadConvertUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/fileupload")
public class TCADServerController {
    @Resource
    private ServerConfig serverConfig;

    @Resource
    private ITProjectFileService tProjectFileService;

    @PostMapping("/cad/{workfaceId}")
    public AjaxResult CADUploadFile(@PathVariable("workfaceId") Integer workfaceId, MultipartFile file) throws Exception {
        try {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath() + "/dwg";
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file, true);
            String url = serverConfig.getUrl() + fileName;
            ///profile/upload/dwg/2025/08/15/1701工作面钻孔施工竣工图(1)_20250815152543A001.dwg
            String dwgPath = RuoYiConfig.getUploadPath() + "/dwg/" + fileName.substring(fileName.lastIndexOf("/dwg/") + 5);
            System.out.println();
            String s = CadConvertUtil.CallMxFileConvert(dwgPath);
            System.out.println(s);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", url);
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());
            TProjectFile projectFile = new TProjectFile();
            projectFile.setFileName(file.getOriginalFilename());
            projectFile.setFilePath(fileName);
            projectFile.setFileType(file.getContentType());
            projectFile.setFileSize(file.getSize());
            projectFile.setWorkfaceId(workfaceId);
            int i = tProjectFileService.insertTProjectFile(projectFile);
            if (i > 0) {
                return ajax;
            } else {
                return AjaxResult.error("保存失败");
            }
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    @PostMapping("/doc/{workfaceId}")
    public AjaxResult DocumentUploadFile(@PathVariable Integer workfaceId, MultipartFile file) throws Exception {
        try {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath() + "/document";
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file, true);
            String url = serverConfig.getUrl() + fileName;
            ///profile/upload/dwg/2025/08/15/1701工作面钻孔施工竣工图(1)_20250815152543A001.dwg
            String documentPath = RuoYiConfig.getUploadPath() + "/document/" + fileName.substring(fileName.lastIndexOf("/document/") + 10);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", url);
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());
            TProjectFile projectFile = new TProjectFile();
            projectFile.setFileName(file.getOriginalFilename());
            projectFile.setFilePath(fileName);
            projectFile.setFileType(file.getContentType());
            projectFile.setFileSize(file.getSize());
            projectFile.setWorkfaceId(workfaceId);
            int i = tProjectFileService.insertTProjectFile(projectFile);
            if (i > 0) {
                return ajax;
            } else {
                return AjaxResult.error("保存失败");
            }
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }
}
