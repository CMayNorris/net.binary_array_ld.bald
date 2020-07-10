package uk.gov.meto.netcdf.cdl;

import java.io.IOException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

	private static final Logger LOG = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		URL url = Application.class.getClass().getResource("/array_geo.cdl");
		System.out.println("Starting the main method: " + url.toString());
		String command = "ncgen -o " + "array_geo.nc" + " " + "array_geo.cdl";
		LOG.debug("Command is: {}", command);
		Process process;
		try {
			process = Runtime.getRuntime().exec(command);
			int exitCode = process.waitFor();
		} catch (Exception e) {
			LOG.error("process creation failed");
		}

	}

}