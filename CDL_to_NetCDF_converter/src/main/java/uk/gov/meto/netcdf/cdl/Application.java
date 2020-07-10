package uk.gov.meto.netcdf.cdl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {
	
	private static final Logger LOG = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		System.out.println("Starting the main method");
		String command = "ncgen -o " + "C:/data/my/file.nc" + " " + "array_geo.cdl";
		LOG.debug("Command is: {}", command);
		Process process;
		try {
			process = Runtime.getRuntime()
				      .exec(command);
			int exitCode = process.waitFor();
		} catch (IOException e) {
			LOG.error("process creation failed");
		}
		

	}

}