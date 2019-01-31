package skynet.scheduler.common;

public interface ISemester 
{
    public ICourse[] getCoursesScheduled();
    public int getYear();
    public SemesterSeasons getSemester();
}
