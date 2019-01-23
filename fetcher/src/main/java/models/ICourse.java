package models;

import java.util.List;

public interface ICourse {

    public List<ICourse> getPrerequisites();
    public String getCourseName();
    public String getCourseTitle();
    public int getCourseID();
    public String getCourseSubject();
    public int getCourseCatalog();
    public int getCreditUnits();
    public String getAcademicCareer();
    public SSObject.Seasons[] getCourseAvailability();
    public SSObject.Seasons[] getEngineerAvailability();

}
