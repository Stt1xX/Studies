package org.example.lab3;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class DataBaseUtils {
    public static String getSessionIdentifier(){
        FacesContext fCtx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
        return session.getId();
    }
}
