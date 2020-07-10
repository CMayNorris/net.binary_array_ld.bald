package uk.gov.meto.netcdf.cdl;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

	private static final Logger LOG = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		URL url = Application.class.getClass().getResource("/array_geo.cdl");
		System.out.println("Starting the main method: " + url.getPath());
		String command = "ncgen -o " + "array_geo.nc" + " " + url.getPath();
		LOG.debug("Command is: {}", command);
		Process process;
		try {
			System.out.println(new File(url.getPath()).exists());
			process = Runtime.getRuntime().exec(command);
			int exitCode = process.waitFor();
			System.out.println(new File("array_geo.nc").exists());
		} catch (Exception e) {
			LOG.error("process creation failed");
		}

	}

}