package com.ruoyi.hh.hhhz.DXF.entities;

import com.ruoyi.hh.hhhz.DXF.entities.base.BaseEntity;
import com.ruoyi.hh.hhhz.DXF.entities.base.Hatch;

import java.util.List;

public class HatchEntity extends Hatch implements Entity {

    private final static String ENTITY_CLASS = "0\nHATCH\n";


    public HatchEntity() {
    }

    public HatchEntity(List<BaseEntity>  pLine) {
        super(pLine);
    }

    @Override
    public String getEntityClass() {
        StringBuilder sb = new StringBuilder();
        sb.append(ENTITY_CLASS);
        //sb.append("5\n" + handle + "\n");
        sb.append("5\n" + getHandle() + "\n");
        sb.append(SOFT_POINTER);
        sb.append(SUBCLASS);
        //sb.append("8\n" + layer + "\n");
        //sb.append("62\n" + color + "\n");
        sb.append("8\n" + getLayer() + "\n");
        sb.append("62\n" + getColor() + "\n");
        return sb.toString();
    }

    @Override
    public String toDXF() {
        StringBuilder sb = new StringBuilder();
        sb.append(getEntityClass());
        sb.append(getHatchSubClass());
        return sb.toString();
    }
}
