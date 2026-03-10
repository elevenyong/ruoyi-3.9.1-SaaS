package com.ruoyi.hh.hhhz.DXF.entities;

public interface Entity {
    public final static String SOFT_POINTER = "330\n1F\n";
    public final static String SUBCLASS = "100\nAcDbEntity\n";
    public final static String ENTITYNAME = "100\nAcDbEntity\n";

    public String getEntityClass();

}
