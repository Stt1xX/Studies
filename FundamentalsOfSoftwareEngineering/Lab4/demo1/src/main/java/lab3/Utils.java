package lab3;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named("utils")
@ApplicationScoped
public class Utils  {
    private final int XMAX = 4;
    private final int XMIN = -4;

    public int getXMAX() {
        return XMAX;
    }

    public int getXMIN() {
        return XMIN;
    }
}