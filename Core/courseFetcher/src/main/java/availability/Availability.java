package availability;

public class Availability {

    private String termCode;
    private String termDescription;

    private String sessionCode;
    private String sessionDescription;
    public Availability(String termCode, String termDescription, String ses, String sesDes) {
        this.termCode = termCode;
        this.termDescription = termDescription;
        this.sessionCode = ses;
        this.sessionDescription = sesDes;
    }

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public String getTermDescription() {
        return termDescription;
    }

    public void setTermDescription(String termDescription) {
        this.termDescription = termDescription;
    }

    public String getSessionCode() {
        return sessionCode;
    }

    public void setSessionCode(String sessionCode) {
        this.sessionCode = sessionCode;
    }

    public String getSessionDescription() {
        return sessionDescription;
    }

    public void setSessionDescription(String sessionDescription) {
        this.sessionDescription = sessionDescription;
    }
}
