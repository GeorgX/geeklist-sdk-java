package st.geekli.api.type;

public enum MicroType {

	CARD,
	MICRO;
	
	@Override
	public String toString()
	{
		return name().toLowerCase();
	}
}
