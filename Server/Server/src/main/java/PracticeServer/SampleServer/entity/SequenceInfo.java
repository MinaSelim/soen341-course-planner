package PracticeServer.SampleServer.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize
public class SequenceInfo {

    String programCode;
    String [] coursesTaken;

    public SequenceInfo(){}

    public SequenceInfo(String programCode, String[] coursesTaken)
    {
        this.programCode = programCode;
        this.coursesTaken = coursesTaken;
    }

    public void setCoursesTaken(String[] coursesTaken) {
        this.coursesTaken = coursesTaken;
    }

    public String getProgramCode() {
        return programCode;
    }

    public String[] getCoursesTaken() {
        return coursesTaken;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }
}
