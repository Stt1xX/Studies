package org.example.lab3;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class Attempt {

    private Integer coordinateX;
    private Double coordinateY;
    private Integer radius;
    private String isHit;
    private String time;

    public Attempt(Integer coordinateX, Double coordinateY, Integer radius){
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.radius = radius;
    }

    public Attempt(){

    }

    public Integer getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(Integer coordinateX) {
        this.coordinateX = coordinateX;
    }

    public Double getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(Double coordinateY) {
        this.coordinateY = coordinateY;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public String getIsHit() {
        return isHit;
    }

    public void setIsHit(String isHit) {
        this.isHit = isHit;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void calculateIsHit(){
        if (coordinateX > 0 && coordinateY <= 0 && coordinateY >= coordinateX - radius){
            this.isHit = "Yes";
            return;
        }
        if (coordinateX <= 0 && coordinateY <= 0 && Math.abs(coordinateX) <= radius && Math.abs(coordinateY) <= radius){
            this.isHit = "Yes";
            return;
        }
        if (coordinateX <= 0 && coordinateY > 0 && coordinateX * coordinateX + coordinateY * coordinateY <= radius * radius){
            this.isHit = "Yes";
            return;
        }
        this.isHit = "No";
    }
}
