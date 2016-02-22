package com.ci.game;

import com.ci.lotusFramework.implementation.LotusRenderView;

public class Main 
{

	public static void main(String[] args)
	{
		try 
		{
			startSecondJVM();
		} catch (Exception e) 
		{
			e.printStackTrace();

		}
	}
	
	public static void startSecondJVM() throws Exception 
	{
	    String separator = System.getProperty("file.separator");
	    String classpath = System.getProperty("java.class.path");
	    String path = System.getProperty("java.home")
	            + separator + "bin" + separator + "java";
	   // ProcessBuilder processBuilder = new ProcessBuilder(path, "-Xms1025m", "-cp", classpath, LotusRenderView.class.getName());
	    ProcessBuilder processBuilder = new ProcessBuilder(path, "-Xmx1512m", "-cp", classpath, LotusRenderView.class.getName());

	    Process process = processBuilder.start();
	    //System.exit(0);
	}

}
