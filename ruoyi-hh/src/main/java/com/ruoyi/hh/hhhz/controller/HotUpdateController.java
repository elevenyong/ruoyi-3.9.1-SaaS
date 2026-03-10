package com.ruoyi.hh.hhhz.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.hh.hhhz.domain.Version;
import com.ruoyi.hh.hhhz.service.IVersionService;
import com.ruoyi.hh.hhhz.tools.MinioUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/U3DRes")
public class HotUpdateController extends BaseController {
    /*@Autowired
    private MinIoUtil minIoUtil;*/
    @Autowired
    private MinioUtils minioUtils;
    @Value("${minio.endpoint}")
    private String address;
    @Value("${minio.bucketName}")
    private String bucketName;
    @Autowired
    private IVersionService versionService;

    @PostMapping("/upload/{path}")
    public Object upload(@PathVariable String path, MultipartFile file) {
        List<String> upload;
        String[] sp = path.split("-");
        String id = sp[0];
        String p = sp[1];
        if (sp.length != 2) {
            throw new RuntimeException("文件上传异常，请稍后重试。");
        }
        try {
            System.out.println("path:" + path);
            upload = minioUtils.upload(new MultipartFile[]{file}, p);
        } catch (Exception exception) {
            return AjaxResult.error(exception.getMessage());
        }
        // 更新文件资源地址
        Version version = new Version();
        version.setId(Long.valueOf(id));
        version.setUpdatePrefixUri(address + "/" + bucketName + "/" + p + "/");
        versionService.updateVersion(version);
        return address + "/" + bucketName + "/" + path + "/" + upload.get(0);
    }


    @GetMapping("/current")
    public AjaxResult getCurrentVersion() {

        return AjaxResult.success();
    }

    /*@PostMapping("/version")
    public AjaxResult addVersion(@RequestBody Version version) {

        return address + "/" + bucketName + "/" + path + "/" + upload.get(0);
    }*/

}
