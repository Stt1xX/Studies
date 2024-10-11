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

    public PercentCounter() throws MalformedObjectNameException, MBeanRegistrationException, NotCompliantMBeanException, InstanceAlreadyExistsException {
        ObjectName name = new ObjectName("org.example.lab3:name=PercentCounter");
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        try{
            server.unregisterMBean(name);
        } catch (InstanceNotFoundException ignored){}
        server.registerMBean(this, name);
        System.out.println("PersentCounter has been created");
    }

    @Override
    public Map<Integer, Double> getSessionsPercentOfHit(){
        return sessionsPercentOfHit;
    }

    public void setPercentOfHits(PointsChecker pointsChecker) {
        Map<Integer, Integer> allMap = pointsChecker.getSessionsPoints();
        Map<Integer, Integer> badMap = pointsChecker.getSessionsPointsBad();
//        for (Map.Entry<Integer, Integer> entry : allMap.entrySet()){
//            System.out.println(entry.getKey() + ": " + entry.getValue());
//        }
//        for (Map.Entry<Integer, Integer> entry : badMap.entrySet()){
//            System.out.println(entry.getKey() + ": " + entry.getValue());
//        }
        for(Map.Entry<Integer, Integer> entry : allMap.entrySet()){
            Double percentNumber = (1 - (double) badMap.get(entry.getKey()) / allMap.get(entry.getKey())) * 100;
            sessionsPercentOfHit.put(entry.getKey(), percentNumber);
        }
    }
}
