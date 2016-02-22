package com.ci.lotusFramework;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface FileIO 
{
	public InputStream readFile(String file) throws IOException;
	
	public OutputStream writeFile(String file) throws IOException;
	
	public InputStream readAssets(String file) throws IOException;
	
}
