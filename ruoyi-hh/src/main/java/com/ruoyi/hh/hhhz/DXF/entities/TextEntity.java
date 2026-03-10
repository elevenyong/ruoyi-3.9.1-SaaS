package com.ruoyi.hh.hhhz.DXF.entities;


import com.ruoyi.hh.hhhz.DXF.entities.base.Text;

public class TextEntity extends Text implements Entity {
    private final static String ENTITY_CLASS = "0\nTEXT\n";

    public TextEntity(Text text) {
        super(text.getLocation(), text.getText(), text.getHeight(), text.gethAlign(), text.getvAlign(), text.getColor(), text.getLayer());
    }

    /*public TextEntity(Point location, String text, double height, String layer) {
        super(location, text, height);
        this.layer = layer;
    }

    public TextEntity(Text text, String layer) {
        super(text.getLocation(), text.getText(), text.getHeight());
        this.layer = layer;
    }

    public TextEntity(Point location, String text, double height) {
        super(location, text, height);
    }*/

    @Override
    public String toDXF() {
        StringBuilder sb = new StringBuilder();
        sb.append(getEntityClass());
        sb.append(getTextSubClass());
        return sb.toString();
    }

    @Override
    public String getEntityClass() {
        StringBuilder sb = new StringBuilder();
        sb.append(ENTITY_CLASS);
        sb.append("5\n" + getHandle() + "\n");
        sb.append(SOFT_POINTER);
        sb.append(SUBCLASS);
        sb.append("8\n" + getLayer() + "\n");
        sb.append("62\n" + getColor() + "\n");
        return sb.toString();
    }

    /*public static void main(String[] args) {
        TextEntity e = new TextEntity(new Point(20, 20), "Hello", 0.6, "0");
        System.out.println(e.toDXF());
    }*/
}
