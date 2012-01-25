package st.geekli.api.type;

public class MicroReply implements GeeklistType {

	private String inReplyTo;
	private Thread thread;
	
	public String getInReplyTo() {
		return inReplyTo;
	}
	
	public void setInReplyTo(String inReplyTo) {
		this.inReplyTo = inReplyTo;
	}
	
	public Thread getThread() {
		return thread;
	}
	
	public void setThread(Thread thread) {
		this.thread = thread;
	}
}
