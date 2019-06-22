package com.gojek.lib;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public interface ResourceLocator
{
	public String getResourceName(String name);

	public String getFullPathResourceName(String name)
		throws ScumberException;

	public URL getAsUrl(String resource);

	public InputStream getAsStream(String resource);

	public File getAsFile(String resource)
		throws ScumberException;

}