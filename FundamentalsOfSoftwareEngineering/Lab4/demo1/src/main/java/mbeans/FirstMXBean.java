package mbeans;

import lab3.Attempt;

import java.util.Map;

public interface FirstMXBean {
    Map<Integer, Integer> getSessionsPoints();

    Map<Integer, Integer> getSessionsPointsBad();

    void checkMissesInRow(Attempt attempt, int sessionId);
}