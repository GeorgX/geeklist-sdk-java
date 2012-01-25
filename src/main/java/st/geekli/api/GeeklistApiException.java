package st.geekli.api;

public class GeeklistApiException extends Exception {

	private static final long serialVersionUID = 8498810365927383069L;

	public GeeklistApiException(Exception e) {
		super(e);
	}
	
	public GeeklistApiException(String message)
	{
		super(message);
	}

}
