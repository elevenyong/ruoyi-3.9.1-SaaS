package com.ruoyi.hh.hhhz.DXF.blocks;

public class Blocks {

    private String blocks;

    public Blocks(String blocks) {
        this.blocks = blocks;
    }

    public Blocks(){
        this.blocks = "  0\n" +
                "SECTION\n" +
                "  2\n" +
                "BLOCKS\n" +
                "  0\n" +
                "BLOCK\n" +
                "  5\n" +
                "20\n" +
                "330\n" +
                "1F\n" +
                "100\n" +
                "AcDbEntity\n" +
                "  8\n" +
                "0\n" +
                "100\n" +
                "AcDbBlockBegin\n" +
                "  2\n" +
                "*Model_Space\n" +
                " 70\n" +
                "     0\n" +
                " 10\n" +
                "0.0\n" +
                " 20\n" +
                "0.0\n" +
                " 30\n" +
                "0.0\n" +
                "  3\n" +
                "*Model_Space\n" +
                "  1\n" +
                "\n" +
                "  0\n" +
                "ENDBLK\n" +
                "  5\n" +
                "21\n" +
                "330\n" +
                "1F\n" +
                "100\n" +
                "AcDbEntity\n" +
                "  8\n" +
                "0\n" +
                "100\n" +
                "AcDbBlockEnd\n" +
                "  0\n" +
                "BLOCK\n" +
                "  5\n" +
                "5A\n" +
                "330\n" +
                "58\n" +
                "100\n" +
                "AcDbEntity\n" +
                " 67\n" +
                "     1\n" +
                "  8\n" +
                "0\n" +
                "100\n" +
                "AcDbBlockBegin\n" +
                "  2\n" +
                "*Paper_Space\n" +
                " 70\n" +
                "     0\n" +
                " 10\n" +
                "0.0\n" +
                " 20\n" +
                "0.0\n" +
                " 30\n" +
                "0.0\n" +
                "  3\n" +
                "*Paper_Space\n" +
                "  1\n" +
                "\n" +
                "  0\n" +
                "ENDBLK\n" +
                "  5\n" +
                "5B\n" +
                "330\n" +
                "58\n" +
                "100\n" +
                "AcDbEntity\n" +
                " 67\n" +
                "     1\n" +
                "  8\n" +
                "0\n" +
                "100\n" +
                "AcDbBlockEnd\n" +
                "  0\n" +
                "BLOCK\n" +
                "  5\n" +
                "5F\n" +
                "330\n" +
                "5D\n" +
                "100\n" +
                "AcDbEntity\n" +
                " 67\n" +
                "     1\n" +
                "  8\n" +
                "0\n" +
                "100\n" +
                "AcDbBlockBegin\n" +
                "  2\n" +
                "*Paper_Space0\n" +
                " 70\n" +
                "     0\n" +
                " 10\n" +
                "0.0\n" +
                " 20\n" +
                "0.0\n" +
                " 30\n" +
                "0.0\n" +
                "  3\n" +
                "*Paper_Space0\n" +
                "  1\n" +
                "\n" +
                "  0\n" +
                "ENDBLK\n" +
                "  5\n" +
                "60\n" +
                "330\n" +
                "5D\n" +
                "100\n" +
                "AcDbEntity\n" +
                " 67\n" +
                "     1\n" +
                "  8\n" +
                "0\n" +
                "100\n" +
                "AcDbBlockEnd\n" +
                "  0\n" +
                "ENDSEC\n";
    }

    /*public Blocks() {
        this.blocks = "  0\n" +
                "SECTION\n" +
                "  2\n" +
                "BLOCKS\n" +
                "  0\n" +
                "BLOCK\n" +
                "  5\n" +
                "20\n" +
                "330\n" +
                "1F\n" +
                "100\n" +
                "AcDbEntity\n" +
                "  8\n" +
                "0\n" +
                "100\n" +
                "AcDbBlockBegin\n" +
                "  2\n" +
                "*Model_Space\n" +
                " 70\n" +
                "     0\n" +
                " 10\n" +
                "0.0\n" +
                " 20\n" +
                "0.0\n" +
                " 30\n" +
                "0.0\n" +
                "  3\n" +
                "*Model_Space\n" +
                "  1\n" +
                "\n" +
                "  0\n" +
                "ENDBLK\n" +
                "  5\n" +
                "21\n" +
                "330\n" +
                "1F\n" +
                "100\n" +
                "AcDbEntity\n" +
                "  8\n" +
                "0\n" +
                "100\n" +
                "AcDbBlockEnd\n" +
                "  0\n" +
                "BLOCK\n" +
                "  5\n" +
                "5A\n" +
                "330\n" +
                "58\n" +
                "100\n" +
                "AcDbEntity\n" +
                " 67\n" +
                "     1\n" +
                "  8\n" +
                "0\n" +
                "100\n" +
                "AcDbBlockBegin\n" +
                "  2\n" +
                "*Paper_Space\n" +
                " 70\n" +
                "     0\n" +
                " 10\n" +
                "0.0\n" +
                " 20\n" +
                "0.0\n" +
                " 30\n" +
                "0.0\n" +
                "  3\n" +
                "*Paper_Space\n" +
                "  1\n" +
                "\n" +
                "  0\n" +
                "ENDBLK\n" +
                "  5\n" +
                "5B\n" +
                "330\n" +
                "58\n" +
                "100\n" +
                "AcDbEntity\n" +
                " 67\n" +
                "     1\n" +
                "  8\n" +
                "0\n" +
                "100\n" +
                "AcDbBlockEnd\n" +
                "  0\n" +
                "BLOCK\n" +
                "  5\n" +
                "5F\n" +
                "330\n" +
                "5D\n" +
                "100\n" +
                "AcDbEntity\n" +
                " 67\n" +
                "     1\n" +
                "  8\n" +
                "0\n" +
                "100\n" +
                "AcDbBlockBegin\n" +
                "  2\n" +
                "*Paper_Space0\n" +
                " 70\n" +
                "     0\n" +
                " 10\n" +
                "0.0\n" +
                " 20\n" +
                "0.0\n" +
                " 30\n" +
                "0.0\n" +
                "  3\n" +
                "*Paper_Space0\n" +
                "  1\n" +
                "\n" +
                "  0\n" +
                "ENDBLK\n" +
                "  5\n" +
                "60\n" +
                "330\n" +
                "5D\n" +
                "100\n" +
                "AcDbEntity\n" +
                " 67\n" +
                "     1\n" +
                "  8\n" +
                "0\n" +
                "100\n" +
                "AcDbBlockEnd\n" +
                "  0\n" +
                "ENDSEC\n";
    }*/

    public String getBlocks() {
        return blocks;
    }
}
