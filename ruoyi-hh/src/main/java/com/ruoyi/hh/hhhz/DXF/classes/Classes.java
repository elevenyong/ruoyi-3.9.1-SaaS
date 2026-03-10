package com.ruoyi.hh.hhhz.DXF.classes;

public class Classes {
    private String classes;

    public Classes(String classes) {
        this.classes = classes;
    }

    public Classes() {
        /*this.classes = "  0\n" +
                "SECTION\n" +
                "  2\n" +
                "CLASSES\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "ACDBDICTIONARYWDFLT\n" +
                "  2\n" +
                "AcDbDictionaryWithDefault\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "        0\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "ACDBPLACEHOLDER\n" +
                "  2\n" +
                "AcDbPlaceHolder\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "        0\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "LAYOUT\n" +
                "  2\n" +
                "AcDbLayout\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "        0\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "DICTIONARYVAR\n" +
                "  2\n" +
                "AcDbDictionaryVar\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "        0\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "TABLESTYLE\n" +
                "  2\n" +
                "AcDbTableStyle\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "     4095\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "MATERIAL\n" +
                "  2\n" +
                "AcDbMaterial\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "     1153\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "VISUALSTYLE\n" +
                "  2\n" +
                "AcDbVisualStyle\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "     4095\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "SCALE\n" +
                "  2\n" +
                "AcDbScale\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "     1153\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "MLEADERSTYLE\n" +
                "  2\n" +
                "AcDbMLeaderStyle\n" +
                "  3\n" +
                "ACDB_MLEADERSTYLE_CLASS\n" +
                " 90\n" +
                "     4095\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "CELLSTYLEMAP\n" +
                "  2\n" +
                "AcDbCellStyleMap\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "     1152\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "EXACXREFPANELOBJECT\n" +
                "  2\n" +
                "ExAcXREFPanelObject\n" +
                "  3\n" +
                "EXAC_ESW\n" +
                " 90\n" +
                "    33793\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "NPOCOLLECTION\n" +
                "  2\n" +
                "AcDbImpNonPersistentObjectsCollection\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "    33921\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "LAYER_INDEX\n" +
                "  2\n" +
                "AcDbLayerIndex\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "    32768\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "SPATIAL_INDEX\n" +
                "  2\n" +
                "AcDbSpatialIndex\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "    32768\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "IDBUFFER\n" +
                "  2\n" +
                "AcDbIdBuffer\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "    32768\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "ACDBSECTIONVIEWSTYLE\n" +
                "  2\n" +
                "AcDbSectionViewStyle\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "     1025\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "ACDBDETAILVIEWSTYLE\n" +
                "  2\n" +
                "AcDbDetailViewStyle\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "     1025\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "ENDSEC\n";*/

        /*this.classes = "  0\n" +
                "SECTION\n" +
                "  2\n" +
                "CLASSES\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "ACDBDICTIONARYWDFLT\n" +
                "  2\n" +
                "AcDbDictionaryWithDefault\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "        0\n" +
                " 91\n" +
                "        1\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "DICTIONARYVAR\n" +
                "  2\n" +
                "AcDbDictionaryVar\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "        0\n" +
                " 91\n" +
                "       11\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "TABLESTYLE\n" +
                "  2\n" +
                "AcDbTableStyle\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "     4095\n" +
                " 91\n" +
                "        1\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "MATERIAL\n" +
                "  2\n" +
                "AcDbMaterial\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "     1153\n" +
                " 91\n" +
                "        3\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "VISUALSTYLE\n" +
                "  2\n" +
                "AcDbVisualStyle\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "     4095\n" +
                " 91\n" +
                "       24\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "SCALE\n" +
                "  2\n" +
                "AcDbScale\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "     1153\n" +
                " 91\n" +
                "       33\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "MLEADERSTYLE\n" +
                "  2\n" +
                "AcDbMLeaderStyle\n" +
                "  3\n" +
                "ACDB_MLEADERSTYLE_CLASS\n" +
                " 90\n" +
                "     4095\n" +
                " 91\n" +
                "        2\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "CELLSTYLEMAP\n" +
                "  2\n" +
                "AcDbCellStyleMap\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "     1152\n" +
                " 91\n" +
                "        1\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "EXACXREFPANELOBJECT\n" +
                "  2\n" +
                "ExAcXREFPanelObject\n" +
                "  3\n" +
                "EXAC_ESW\n" +
                " 90\n" +
                "     1025\n" +
                " 91\n" +
                "        0\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "NPOCOLLECTION\n" +
                "  2\n" +
                "AcDbImpNonPersistentObjectsCollection\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "     1153\n" +
                " 91\n" +
                "        0\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "LAYER_INDEX\n" +
                "  2\n" +
                "AcDbLayerIndex\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "        0\n" +
                " 91\n" +
                "        0\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "SPATIAL_INDEX\n" +
                "  2\n" +
                "AcDbSpatialIndex\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "        0\n" +
                " 91\n" +
                "        0\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "IDBUFFER\n" +
                "  2\n" +
                "AcDbIdBuffer\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "        0\n" +
                " 91\n" +
                "        0\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "ACDBSECTIONVIEWSTYLE\n" +
                "  2\n" +
                "AcDbSectionViewStyle\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "     1025\n" +
                " 91\n" +
                "        1\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "ACDBDETAILVIEWSTYLE\n" +
                "  2\n" +
                "AcDbDetailViewStyle\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "     1025\n" +
                " 91\n" +
                "        1\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "RAPIDRTRENDERSETTINGS\n" +
                "  2\n" +
                "AcDbRapidRTRenderSettings\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "     1024\n" +
                " 91\n" +
                "        7\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "SORTENTSTABLE\n" +
                "  2\n" +
                "AcDbSortentsTable\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "        0\n" +
                " 91\n" +
                "        5\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "ENDSEC\n";*/
        /*this.classes = "  0\n" +
                "SECTION\n" +
                "  2\n" +
                "CLASSES\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "ACDBDICTIONARYWDFLT\n" +
                "  2\n" +
                "AcDbDictionaryWithDefault\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "        0\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "ACDBPLACEHOLDER\n" +
                "  2\n" +
                "AcDbPlaceHolder\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "        0\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "LAYOUT\n" +
                "  2\n" +
                "AcDbLayout\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "        0\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "DICTIONARYVAR\n" +
                "  2\n" +
                "AcDbDictionaryVar\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "        0\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "TABLESTYLE\n" +
                "  2\n" +
                "AcDbTableStyle\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "     4095\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "MATERIAL\n" +
                "  2\n" +
                "AcDbMaterial\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "     1153\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "VISUALSTYLE\n" +
                "  2\n" +
                "AcDbVisualStyle\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "     4095\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "SCALE\n" +
                "  2\n" +
                "AcDbScale\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "     1153\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "MLEADERSTYLE\n" +
                "  2\n" +
                "AcDbMLeaderStyle\n" +
                "  3\n" +
                "ACDB_MLEADERSTYLE_CLASS\n" +
                " 90\n" +
                "     4095\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "CELLSTYLEMAP\n" +
                "  2\n" +
                "AcDbCellStyleMap\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "     1152\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "EXACXREFPANELOBJECT\n" +
                "  2\n" +
                "ExAcXREFPanelObject\n" +
                "  3\n" +
                "EXAC_ESW\n" +
                " 90\n" +
                "    33793\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "NPOCOLLECTION\n" +
                "  2\n" +
                "AcDbImpNonPersistentObjectsCollection\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "    33921\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "LAYER_INDEX\n" +
                "  2\n" +
                "AcDbLayerIndex\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "    32768\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "SPATIAL_INDEX\n" +
                "  2\n" +
                "AcDbSpatialIndex\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "    32768\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "IDBUFFER\n" +
                "  2\n" +
                "AcDbIdBuffer\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "    32768\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "ACDBSECTIONVIEWSTYLE\n" +
                "  2\n" +
                "AcDbSectionViewStyle\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "     1025\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "ACDBDETAILVIEWSTYLE\n" +
                "  2\n" +
                "AcDbDetailViewStyle\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "     1025\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "SORTENTSTABLE\n" +
                "  2\n" +
                "AcDbSortentsTable\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "        0\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "ENDSEC\n";*/
        this.classes = "  0\n" +
                "SECTION\n" +
                "  2\n" +
                "CLASSES\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "ACDBDICTIONARYWDFLT\n" +
                "  2\n" +
                "AcDbDictionaryWithDefault\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "        0\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "ACDBPLACEHOLDER\n" +
                "  2\n" +
                "AcDbPlaceHolder\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "        0\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "LAYOUT\n" +
                "  2\n" +
                "AcDbLayout\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "        0\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "DICTIONARYVAR\n" +
                "  2\n" +
                "AcDbDictionaryVar\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "        0\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "TABLESTYLE\n" +
                "  2\n" +
                "AcDbTableStyle\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "     4095\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "MATERIAL\n" +
                "  2\n" +
                "AcDbMaterial\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "     1153\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "VISUALSTYLE\n" +
                "  2\n" +
                "AcDbVisualStyle\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "     4095\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "SCALE\n" +
                "  2\n" +
                "AcDbScale\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "     1153\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "MLEADERSTYLE\n" +
                "  2\n" +
                "AcDbMLeaderStyle\n" +
                "  3\n" +
                "ACDB_MLEADERSTYLE_CLASS\n" +
                " 90\n" +
                "     4095\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "CELLSTYLEMAP\n" +
                "  2\n" +
                "AcDbCellStyleMap\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "     1152\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "EXACXREFPANELOBJECT\n" +
                "  2\n" +
                "ExAcXREFPanelObject\n" +
                "  3\n" +
                "EXAC_ESW\n" +
                " 90\n" +
                "    33793\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "NPOCOLLECTION\n" +
                "  2\n" +
                "AcDbImpNonPersistentObjectsCollection\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "    33921\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "LAYER_INDEX\n" +
                "  2\n" +
                "AcDbLayerIndex\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "    32768\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "SPATIAL_INDEX\n" +
                "  2\n" +
                "AcDbSpatialIndex\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "    32768\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "IDBUFFER\n" +
                "  2\n" +
                "AcDbIdBuffer\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "    32768\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "ACDBSECTIONVIEWSTYLE\n" +
                "  2\n" +
                "AcDbSectionViewStyle\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "     1025\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "ACDBDETAILVIEWSTYLE\n" +
                "  2\n" +
                "AcDbDetailViewStyle\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "     1025\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "CLASS\n" +
                "  1\n" +
                "SORTENTSTABLE\n" +
                "  2\n" +
                "AcDbSortentsTable\n" +
                "  3\n" +
                "ObjectDBX Classes\n" +
                " 90\n" +
                "        0\n" +
                "280\n" +
                "     0\n" +
                "281\n" +
                "     0\n" +
                "  0\n" +
                "ENDSEC\n";
    }

    public String getClasses() {
        return classes;
    }


}