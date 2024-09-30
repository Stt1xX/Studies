package mbeans;

import entity.AttemptEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;
import lab3.Attempt;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Named("pointsChecker")
@ApplicationScoped
public class PointsChecker extends NotificationBroadcasterSupport implements FirstMXBean {

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    private final EntityManager entityManager = entityManagerFactory.createEntityManager();
    private final EntityTransaction transaction = entityManager.getTransaction();

    private final Map<Integer, Integer> sessionsPoints = new HashMap<>();
    private final Map<Integer, Integer> sessionsPointsBad = new HashMap<>();
    private final Map<Integer, Integer> sessionsRowOfMisses = new HashMap<>();

    @Override
    public Map<Integer, Integer> getSessionsPoints() {
        setSessionPoints();
        return sessionsPoints;
    }

    @Override
    public Map<Integer, Integer> getSessionsPointsBad() {
        setSessionPointsBad();
        return sessionsPointsBad;
    }

    public PointsChecker() throws NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, MalformedObjectNameException {
        ObjectName name = new ObjectName("org.example.lab3:name=PointsChecker");
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        try{
            server.unregisterMBean(name);
        } catch (InstanceNotFoundException ignored){}
        server.registerMBean(this, name);
        System.out.println("Point Checker has been created");
    }

    private void setSessionPoints () {
        sessionsPoints.clear();
        TypedQuery<AttemptEntity> listOfSession = entityManager.createNamedQuery("getAttempts", AttemptEntity.class);
        for (AttemptEntity attempt : listOfSession.getResultList()) {
            int currentNumber = sessionsPoints.get(attempt.getSessionId()) == null ? 0 : sessionsPoints.get(attempt.getSessionId());
            sessionsPoints.put(attempt.getSessionId(), currentNumber + 1);
        }
    }

    private void setSessionPointsBad () {
        sessionsPointsBad.clear();
        TypedQuery<AttemptEntity> listOfSession = entityManager.createNamedQuery("getAttempts", AttemptEntity.class);
        for (AttemptEntity attempt : listOfSession.getResultList()) {
            int currentNumberBad = sessionsPointsBad.get(attempt.getSessionId()) == null ? 0 : sessionsPointsBad.get(attempt.getSessionId());
            if (Objects.equals(attempt.getIshit(), "No")){
                sessionsPointsBad.put(attempt.getSessionId(), currentNumberBad + 1);
            }
        }
    }

    @Override
    public void checkMissesInRow (Attempt attempt, int sessionId) {
        final int NOTIFY_LIMIT = 3;
        if (!sessionsRowOfMisses.containsKey(sessionId)){
            sessionsRowOfMisses.put(sessionId, 0);
        }
        if (attempt.getIsHit().equals("No")){
            sessionsRowOfMisses.put(sessionId, sessionsRowOfMisses.get(sessionId) + 1);
        } else {
            sessionsRowOfMisses.put(sessionId, 0);
        }
        if (sessionsRowOfMisses.get(sessionId) >= NOTIFY_LIMIT) {
            System.out.println("Missed in a row");
            Notification n = new Notification("Bad Accuracy", this, 0,
                    String.format("User with Session id%d reached %d miss in a row", sessionId, NOTIFY_LIMIT));
            sendNotification(n);
            sessionsRowOfMisses.put(sessionId, 0);
        }
    }
}
