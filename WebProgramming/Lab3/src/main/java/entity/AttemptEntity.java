package entity;

import javax.persistence.*;

@Entity
@NamedQuery(name = "getAttempts.bySessionId", query = "SELECT a FROM AttemptEntity a WHERE a.sessionId = ?1")
@Table(name = "attempt", schema = "public", catalog = "Lab3DataBase")
public class AttemptEntity {
    @Basic
    @Column(name = "ishit")
    private String ishit;
    @Basic
    @Column(name = "x")
    private int x;
    @Basic
    @Column(name = "y")
    private double y;
    @Basic
    @Column(name = "r")
    private int r;
    @Basic
    @Column(name = "time")
    private String time;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "attempt_id")
    private int attemptId;
    @Basic
    @Column(name = "session_id" )
    private Integer sessionId;
//    @ManyToOne
//    @JoinColumn(name = "session_id", referencedColumnName = "session_id")
//    private SessionEntity sessionBySessionId;

    public String getIshit() {
        return ishit;
    }

    public void setIshit(String ishit) {
        this.ishit = ishit;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(int attemptId) {
        this.attemptId = attemptId;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttemptEntity that = (AttemptEntity) o;

        if (x != that.x) return false;
        if (Double.compare(y, that.y) != 0) return false;
        if (r != that.r) return false;
        if (attemptId != that.attemptId) return false;
        if (ishit != null ? !ishit.equals(that.ishit) : that.ishit != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (sessionId != null ? !sessionId.equals(that.sessionId) : that.sessionId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = ishit != null ? ishit.hashCode() : 0;
        result = 31 * result + x;
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + r;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + attemptId;
        result = 31 * result + (sessionId != null ? sessionId.hashCode() : 0);
        return result;
    }

//    public SessionEntity getSessionBySessionId() {
//        return sessionBySessionId;
//    }
//
//    public void setSessionBySessionId(SessionEntity sessionBySessionId) {
//        this.sessionBySessionId = sessionBySessionId;
//    }
}
