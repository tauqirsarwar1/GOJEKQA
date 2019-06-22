package com.gojek.lib;

public class ScumberException extends Exception
{

	public ScumberException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public ScumberException(final String message)
	{
		super(message);
	}
}
