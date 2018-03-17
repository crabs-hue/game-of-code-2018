package lu.arhs.hackathon.domain;

public class Parking {

    double lan;
    double lon;
    String name;
    double bboxX;
    double bboxY;
    double bboxW;
    double bboxZ;

    public double getLan() {
        return lan;
    }

    public void setLan(double lan) {
        this.lan = lan;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBboxX() {
        return bboxX;
    }

    public void setBboxX(double bboxX) {
        this.bboxX = bboxX;
    }

    public double getBboxY() {
        return bboxY;
    }

    public void setBboxY(double bboxY) {
        this.bboxY = bboxY;
    }

    public double getBboxW() {
        return bboxW;
    }

    public void setBboxW(double bboxW) {
        this.bboxW = bboxW;
    }

    public double getBboxZ() {
        return bboxZ;
    }

    public void setBboxZ(double bboxZ) {
        this.bboxZ = bboxZ;
    }
}
