package lab3;

import entity.AttemptEntity;
import entity.SessionEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.*;
import mbeans.PercentCounter;
import mbeans.PointsChecker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Named("container")
@ApplicationScoped
public class Container {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    private final EntityManager entityManager = entityManagerFactory.createEntityManager();
    private final EntityTransaction transaction = entityManager.getTransaction();

    private final List<Attempt> list = new ArrayList<>();

    @Inject
    private PointsChecker pointsChecker;

    @Inject
    private PercentCounter percentCounter;

    public PointsChecker getPointsChecker() {
        return pointsChecker;
    }

    public PercentCounter getPercentCounter() {
        return percentCounter;
    }

    public void setPercentCounter(PercentCounter percentCounter) {
        this.percentCounter = percentCounter;
    }

    public void setPointsChecker(PointsChecker pointsChecker) {
        this.pointsChecker = pointsChecker;
    }

    public Container() {
        getAttempts();
    }

    public List<Attempt> getList() {
        getAttempts();
        List<Attempt> copy = new ArrayList<>(list);
        Collections.reverse(copy);
        return copy;
    }

    private void getAttempts(){
        TypedQuery<AttemptEntity> listOfAttempts = entityManager.createNamedQuery("getAttempts.bySessionId", AttemptEntity.class);
        listOfAttempts.setParameter(1, getSessionId());
        List<AttemptEntity> listOfAttemptsResultList = listOfAttempts.getResultList();
        list.clear();
        for (AttemptEntity attemptEntity : listOfAttemptsResultList){
            Attempt attempt = new Attempt();
            attempt.setCoordinateX(attemptEntity.getX());
            attempt.setCoordinateY(attemptEntity.getY());
            attempt.setRadius(attemptEntity.getR());
            attempt.setIsHit(attemptEntity.getIshit());
            attempt.setTime(attemptEntity.getTime());
            list.add(attempt);
        }
    }

    private void addAttemptToDb(Attempt attempt, int sessionId){
        try {
            transaction.begin();

            AttemptEntity attemptsEntity = new AttemptEntity();

            attemptsEntity.setX(attempt.getCoordinateX());
            attemptsEntity.setY(attempt.getCoordinateY());
            attemptsEntity.setR(attempt.getRadius());
            attemptsEntity.setIshit(attempt.getIsHit());
            attemptsEntity.setTime(attempt.getTime());
            attemptsEntity.setSessionId(sessionId);

            entityManager.persist(attemptsEntity);

            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    private void addSession(){
        try {
            transaction.begin();
            SessionEntity sessionEntity = new SessionEntity();
            sessionEntity.setIdentifier(DataBaseUtils.getSessionIdentifier());
            entityManager.persist(sessionEntity);
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    private int getSessionId(){
        TypedQuery<SessionEntity> listOfSession = entityManager.createNamedQuery("getSessionId.byValue", SessionEntity.class);
        listOfSession.setParameter(1, DataBaseUtils.getSessionIdentifier());
        List<SessionEntity> list = listOfSession.getResultList();
        return (list == null || list.isEmpty()) ? -1 : list.get(0).getSessionId();
    }

    public void addAttempt(Integer coordinateX, Double coordinateY, Integer radius){
        Attempt attempt = new Attempt(coordinateX, coordinateY, radius);
        attempt.calculateIsHit();
        attempt.setTime(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));

        int sessionId = getSessionId();
        if (sessionId == -1){
            System.out.println("Adding session...");
            addSession();
            sessionId = getSessionId();
        }

        addAttemptToDb(attempt, sessionId);

        pointsChecker.checkMissesInRow(attempt, sessionId);
        percentCounter.setPercentOfHits(pointsChecker);

    }
}
