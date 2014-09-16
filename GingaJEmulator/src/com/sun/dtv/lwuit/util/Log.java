package com.sun.dtv.lwuit.util;

import com.sun.dtv.lwuit.Command;
import com.sun.dtv.lwuit.Display;
import com.sun.dtv.lwuit.Form;
import com.sun.dtv.lwuit.TextArea;
import com.sun.dtv.lwuit.events.ActionEvent;
import com.sun.dtv.lwuit.layouts.BorderLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.io.FileInputStream;

public class Log
{
	/**
	 * Constant indicating the logging level Debug is the default and the
	 * lowest level followed by info, warning and error.
	 * 
	 */
	public static final int DEBUG = 1;

	/**
	 * Constant indicating the logging level Debug is the default and the lowest
	 * level followed by info, warning and error.
	 * 
	 */
	public static final int INFO = 2;

	/**
	 * Constant indicating the logging level Debug is the default and the lowest
	 * level followed by info, warning and error.
	 * 
	 */
	public static final int WARNING = 3;

	/**
	 * Constant indicating the logging level Debug is the default and the lowest
	 * level followed by info, warning and error.
	 *
	 */
	public static final int ERROR = 4;

	private static Log instance = new Log();

	private Writer output;
	private long zeroTime;
	private int level;
	private boolean fileWriteEnabled = false;

	/**
	 * Constructor 
	 * 
	 */
	Log()
	{
		level = DEBUG;
		zeroTime = System.currentTimeMillis();
	}

	/**
	 * Installs a log subclass that can replace the logging destination/behavior.
	 *
	 * 
	 * @param newInstance - the new instance for the Log object
	 */
	public static void install(Log newInstance)
	{
		instance = newInstance;
	}

	/**
	 * Default println method invokes the print instance method, uses DEBUG level.
	 *
	 * 
	 * @param text - the text to print
	 */
	public static void p(String text)
	{
		instance.p(text, DEBUG);
	}

	/**
	 * Default println method invokes the print instance method, uses given level.
	 *
	 * 
	 * @param text - the text to print
	 * @param level - one of DEBUG, INFO, WARNING, ERROR
	 */
	public static void p(String text, int level)
	{
		instance.print(text, level);
	}

	/**
	 * Sets the logging level for printing log details, the lower the value
	 * the more verbose would the printouts be.
	 *
	 * 
	 * @param level - one of DEBUG, INFO, WARNING, ERROR
	 * @see getLevel()
	 */
	public static void setLevel(int level)
	{
		instance.level = level;
	}

	/**
	 * Returns the logging level for printing log details, the lower the value
	 * the more verbose would the printouts be.
	 *
	 * 
	 * 
	 * @return one of DEBUG, INFO, WARNING, ERROR
	 * @see setLevel(int)
	 */
	public static int getLevel()
	{
		return instance.level;
	}

	/**
	 * Returns the contents of the log as a single long string to be displayed by
	 * the application any way it sees fit
	 * 
	 * @return string containing the whole log
	 */
	public static String getLogContent() {
		try {
			String text = "";
			if(instance.fileWriteEnabled) {
				FileInputStream con = new FileInputStream("/tmp/jwt.log");
				Reader r = new InputStreamReader(con);
				char[] buffer = new char[1024];
				int size = r.read(buffer);
				while(size > -1) {
					text += new String(buffer, 0, size);
					size = r.read(buffer);
				}
				r.close();
			}
			return text;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
			
		return "";
	}

	/**
	 * Places a form with the log as a TextArea on the screen, this method can
	 * be attached to appear at a given time or using a fixed global key. Using
	 * this method might cause a problem with further log output.
	 *
	 * 
	 */
	public static void showLog()
	{
		try {
			String text = getLogContent();
			TextArea area = new TextArea(text, 5, 20);
			Form f = new Form("Log");
			f.setScrollable(false);
			final Form current = Display.getInstance().getCurrent();
			Command back = new Command("Back") {
				public void actionPerformed(ActionEvent ev) {
					current.show();
				}
			};
			f.addCommand(back);
			f.setBackCommand(back);
			f.setLayout(new BorderLayout());
			f.addComponent(BorderLayout.CENTER, area);
			f.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Default log implementation prints to the console and the file connector
	 * if applicable. Also prepends the thread information and time before 
	 * 
	 * @param text the text to print
	 * @param level one of DEBUG, INFO, WARNING, ERROR
	 */
	protected void print(String text, int level) {
		if (this.level > level) {
			return;
		}
		text = getThreadAndTimeStamp() + " - " + text;
		System.out.println(text);
		
		String slevel = "DEBUG";

		if (level == INFO) {
			slevel = "INFO";
		} else if (level == WARNING) {
			slevel = "WARNING";
		} else if (level == ERROR) {
			slevel = "ERROR";
		}

		if (fileWriteEnabled) {
			try {
				getWriter().write(slevel + ": " + text);
			} catch (Throwable err) {
				err.printStackTrace();
				fileWriteEnabled = false;
			}
		}

		System.out.println(slevel + ": " + text);
	}

	/**
	 * Returns a simple string containing a timestamp and thread name.
	 * 
	 * @return timestamp string for use in the log
	 */
	protected String getThreadAndTimeStamp() {
		long time = System.currentTimeMillis() - zeroTime;
		long milli = time % 1000;
		time /= 1000;
		long sec = time % 60;
		time /= 60;
		long min = time % 60; 
		time /= 60;
		long hour = time % 60; 

		return "[" + Thread.currentThread().getName() + "] " + hour  + ":" + min + ":" + sec + "," + milli;
	}

	private Writer getWriter() throws IOException {
		if(output == null) {
			output = createWriter();
		}
		return output;
	}

	/**
	 * Default method for creating the output writer into which we write, this method
	 * creates a simple log file using the file connector
	 * 
	 * @return writer object
	 * @throws IOException when thrown by the connector
	 */
	protected Writer createWriter() throws IOException {
		return new OutputStreamWriter(new ByteArrayOutputStream());
	}


}

