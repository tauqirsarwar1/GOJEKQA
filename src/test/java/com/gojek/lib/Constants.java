package com.gojek.lib;

public final class Constants
{
	public static final int	WAIT_TIMEOUT_DEFAULT	= 80;
	public static final int	WAIT_TIMEOUT_NOWAIT	= 0;
	public static final int	RETRY_COUNT		= 3;
	public static final long ONE_SECOND_WAIT= 1000L;


	private Constants()
	{
		throw new UnsupportedOperationException("This class cannot be instantiated");
	}
}