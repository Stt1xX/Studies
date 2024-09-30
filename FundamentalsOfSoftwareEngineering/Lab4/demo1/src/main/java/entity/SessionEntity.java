package entity;


import jakarta.persistence.*;

@Entity
@NamedQuery(name="getSessionId.byValue", query = "SELECT e FROM SessionEntity e WHERE e.identifier = ?1")
@Table(name = "session", schema = "public")
public class SessionEntity {
    @Basic
    @Column(name = "identifier")
    private String identifier;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "session_id")
    private int sessionId;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SessionEntity that = (SessionEntity) o;

        if (sessionId != that.sessionId) return false;
        if (identifier != null ? !identifier.equals(that.identifier) : that.identifier != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = identifier != null ? identifier.hashCode() : 0;
        result = 31 * result + sessionId;
        return result;
    }

    @Override
    public String toString() {
        return "id: " + getSessionId() + ", identifier: " + getIdentifier();
    }
}
