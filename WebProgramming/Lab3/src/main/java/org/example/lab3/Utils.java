package org.example.lab3;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
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