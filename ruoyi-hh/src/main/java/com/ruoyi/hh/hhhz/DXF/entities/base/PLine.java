package com.ruoyi.hh.hhhz.DXF.entities.base;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

//public class PLine extends Line {
//public class PLine extends PLineBase {
public class PLine extends BaseEntity {
    @JsonProperty("points")
    private List<Point> points;
    private final String ENTITY_SUBCLASS = "100\nAcDbPolyline\n";

    @JsonProperty("isClose")
    private String isClose = "0";

    @JsonProperty("hatch")
    private boolean hatch = false;

    @JsonProperty("hatchId")
    private int hatchId = -1;


    /*public PLine() {
        this.points = new ArrayList<>();
    }*/

    public PLine() {
    }

    //@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public PLine(List<Point> points, String isClose, int color, String layer) throws Exception {
        if (points.size() >= 2) {
            this.points = points;
        } else {
            throw new Exception("Line must have at least 2 points.");
        }
        setIsClose(isClose);
        setColor(color);
        setLayer(layer);
    }

    public boolean isHatch() {
        return hatch;
    }

    public void setHatch(boolean hatch) {
        this.hatch = hatch;
    }

    /*public PLine(Point point1, Point point2) throws Exception {
        this(Arrays.asList(point1, point2));
    }*/

    public String getIsClose() {
        return isClose;
    }

    public void setIsClose(String isClose) {
        this.isClose = isClose;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public void addPoint(Point point) {
        this.points.add(point);
    }

    public void removePoint(Point point) {
        this.points.remove(point);
    }

    public int getHatchId() {
        return hatchId;
    }

    public void setHatchId(int hatchId) {
        this.hatchId = hatchId;
    }

    public String getPLineSubClass() {
        StringBuilder sb = new StringBuilder();
        if (getPoints().size() < 2) {
            throw new IllegalArgumentException("PLine must have at least 2 points.");
        }

        sb.append(ENTITY_SUBCLASS);
        sb.append("90\n" + getPoints().size() + "\n");
        if (isClose == null) {
            sb.append("70\n" + "0" + "\n");
        } else {
            sb.append("70\n" + isClose + "\n");
        }
        sb.append("43\n0.0\n");
        for (int i = 0; i < getPoints().size(); i++) {
            sb.append("10\n" + getPoints().get(i).getX() + "\n");
            sb.append("20\n" + getPoints().get(i).getY() + "\n");
            sb.append("42\n" + getPoints().get(i).getBulge() + "\n");
        }
        return sb.toString();
    }
}
/**
 * AcDbPolyline
 * 90
 * 4
 * 70
 * 1
 * 43
 * 0.0
 * 10
 * 10.0
 * 20
 * 10.0
 * 10
 * 21.0
 * 20
 * 21.0
 * 42
 * -0.5223894874633081
 * 10
 * 25.04220020536527
 * 20
 * 23.81315103254991
 * 42
 * -0.7231350770814795
 * 10
 * 28.64966568463582
 * 20
 * 22.62317005246933
 * 42
 * -0.5306754601577078
 */
