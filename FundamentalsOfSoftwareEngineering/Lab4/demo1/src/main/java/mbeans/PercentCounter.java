package mbeans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

@Named("persentCounter")
@ApplicationScoped
public class PercentCounter implements SecondMXBean {

    private final Map<Integer, Double> sessionsPercentOfHit = new HashMap<>();

    @Inject
    private PointsChecker pointsChecker;

    public PercentCounter() throws MalformedObjectNameException, MBeanRegistrationException, NotCompliantMBeanException, InstanceAlreadyExistsException {
        ObjectName name = new ObjectName("org.example.lab3:name=PercentCounter");
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        try{
            server.unregisterMBean(name);
        } catch (InstanceNotFoundException ignored){}
        server.registerMBean(this, name);
        System.out.println("PersentCounter has been created");
    }

    public PointsChecker getPointsChecker() {
        return pointsChecker;
    }

    public void setPointsChecker(PointsChecker pointsChecker) {
        this.pointsChecker = pointsChecker;
    }

    @Override
    public Map<Integer, Double> getPercentOfHits(){
        setPercentOfHits();
        return sessionsPercentOfHit;
    }

    @PostConstruct
    private void setPercentOfHits() {
        for (Map.Entry<Integer, Integer> entry : pointsChecker.getSessionsPoints().entrySet()) {
            if (pointsChecker.getSessionsPointsBad().containsKey(entry.getKey())) {
                Double percentNumber = (1 - (double) pointsChecker.getSessionsPointsBad().get(entry.getKey()) / pointsChecker.getSessionsPoints().get(entry.getKey())) * 100;
                sessionsPercentOfHit.put(entry.getKey(), percentNumber);
            }
        }
    }
}
