package com.ruoyi.hh.demo.domain;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.*;


/**
 * (Personneinfo)表实体类
 *
 * @author Eleven
 * @since 2022-05-25 16:48:04
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "personneinfo")
public class Personneinfo extends Model<Personneinfo> {
    @TableField(value = "id")
    private Integer id;
    //人员类型    @TableField(value = "personneltype")
    private String personneltype;
    //人员总数    @TableField(value = "sum")
    private Integer sum;

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.id;
    }
}

