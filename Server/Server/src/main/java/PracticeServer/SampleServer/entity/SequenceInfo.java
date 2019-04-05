package PracticeServer.SampleServer.entity;

import javax.sound.midi.Sequence;
import java.util.ArrayList;
import java.util.List;

public class SequenceInfo {

    //Information required to create a sequence
    private String program;
    private List<Object> coursesTaken = new ArrayList<>();
    private List<Object> sequence = new ArrayList<>();

    public SequenceInfo() {}

    public SequenceInfo (String program, List<Object> coursesTaken, List<Object> sequence) {
        this.program = program;
        this.coursesTaken = coursesTaken;
        this.sequence = sequence;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public List<Object> getCoursesTaken() {
        return coursesTaken;
    }

    public void setCoursesTaken(List<Object> coursesTaken) {
        this.coursesTaken = coursesTaken;
    }

    public List<Object> getSequence() {
        return sequence;
    }

    public void setSequence(List<Object> sequence) {
        this.sequence = sequence;
    }
}
