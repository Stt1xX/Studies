package lab3;


import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;

public class DataBaseUtils {
    public static String getSessionIdentifier(){
        FacesContext fCtx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
        return session.getId();
    }
}
