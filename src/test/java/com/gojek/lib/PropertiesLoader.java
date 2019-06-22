package com.gojek.lib;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader
{
	private static final PropertiesLoader INSTANCE = createInstance();

	private static PropertiesLoader createInstance()
	{
		return new PropertiesLoader(new ClassLoaderResourceLocator());
	}

	public static PropertiesLoader getInstance()
	{
		return INSTANCE;
	}

	private final ResourceLocator itsResourceLocator;

	private PropertiesLoader(final ResourceLocator resourceLocator)
	{
		itsResourceLocator = resourceLocator;
	}

	public Properties load(final String propertyFile)
		throws ScumberException
	{
		final InputStream s = itsResourceLocator.getAsStream(propertyFile);
		final Properties p = new Properties();
		try
		{
			p.load(s);
		}
		catch (final IOException e)
		{
			throw new ScumberException("Failed to load property file : " + propertyFile, e);
		}
		return p;
	}

}
