package availability;

import skynet.scheduler.common.SemesterSeasons;

public class Season {

    private String termCode;
    private SemesterSeasons season;
    private int year;

    public Season(String termCode, SemesterSeasons season, int year) {
        this.termCode = termCode;
        this.season = season;
        this.year = year;
    }

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public SemesterSeasons getSeason() {
        return season;
    }

    public void setSeason(SemesterSeasons season) {
        this.season = season;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
