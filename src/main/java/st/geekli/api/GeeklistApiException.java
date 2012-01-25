package st.geekli.api;

public class GeeklistApiException extends Exception {

	private static final long serialVersionUID = 8498810365927383069L;
	private int code = -1;
	
	public GeeklistApiException(Exception e) {
		super(e);
	}
	
	public GeeklistApiException(String message)
	{
		super(message);
	}
	
	public GeeklistApiException(String message, int code)
	{
		super(message);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
