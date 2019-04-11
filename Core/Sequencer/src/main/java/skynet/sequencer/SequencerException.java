package skynet.sequencer;

public class SequencerException extends RuntimeException {

	public SequencerException() { super ("Error In Sequencer"); } 
	
	public SequencerException(String message) { super (message); } 
	
	public String getMessage() { return super.getMessage(); } 
}
