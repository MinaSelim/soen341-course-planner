package skynet.scheduler.common;

public interface ISemester 
{
    public ICourse[] getCoursesScheduled();	// Returns Courses scheduled in this Semester Object
    public int getYear();					// Returns Semester Year
    public SemesterSeasons getSemester();	// E.g. { Fall, Winter, Summer };
}
