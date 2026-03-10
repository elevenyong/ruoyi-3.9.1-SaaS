package com.ruoyi.hh.physical.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.apache.arrow.flatbuf.Int;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("DZZDB项目文件")
public class TProjectFile extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty("项目文件ID")
    private Integer id;
    /**
     * 元素文件名称
     */
    @ApiModelProperty("元素文件名称")
    private String fileName;
    /**
     * 关联工作面ID
     */
    @ApiModelProperty("关联工作面ID")
    private Integer workfaceId;
    /**
     * 文件资源路径
     */
    @ApiModelProperty("文件资源路径")
    private String filePath;
    /**
     * 文件大小（Bytes）
     */
    @ApiModelProperty("文件大小（Bytes）")
    private Long fileSize;
    /**
     * 文件类型
     */
    @ApiModelProperty("文件类型")
    private String fileType;
    /**
     * 文件MD5
     */
    @ApiModelProperty("文件MD5")
    private String md5;
}
