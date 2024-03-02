package itmo.spring.meeting.back.model.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity(name = "attempts")
@Component
public class Attempt {
    @Column(nullable = false)
    private String x;
    @Column(nullable = false)
    private String y;
    @Column(nullable = false)
    private String radius;
    @Column(nullable = false)
    private String time;
    @Column(nullable = false)
    private String isHit;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User owner;

    public static Attempt ERROR_ATTEMPT = new Attempt("null", "null", "null", "null", "null", null);

    @Id
    @GeneratedValue
    private Long id;

    public Attempt(String x, String y, String radius, String time, String isHit, User owner) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.time = time;
        this.isHit = isHit;
        this.owner = owner;
    }

    public Attempt(String x, String y, String radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public Attempt(){

    }

    public boolean checkIsValid(){
        try{
            float x = Float.parseFloat(this.x);
            float y = Float.parseFloat(this.y);
            float radius = Float.parseFloat(this.radius);
            if (x > 2 || x < -2 || y > 3 || y < -5 || radius < 0 || radius > 2) return false;
        } catch(NumberFormatException | NullPointerException  ex){
            return false;
        }
        return true;
    }

    public void setValues(){
        this.time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        float x = Float.parseFloat(this.x);
        float y = Float.parseFloat(this.y);
        float radius = Float.parseFloat(this.radius);

        if(x <= 0 && y > 0 && radius >= y && radius >= -2*x
                || x >= 0 && y <= 0 && radius*radius >= x*x + y*y
                || x < 0 && y <= 0 && radius >= -x - 2*y)
            this.isHit = "Yes";
        else
            this.isHit = "No";
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIsHit() {
        return isHit;
    }

    public void setIsHit(String isHit) {
        this.isHit = isHit;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
