package uk.gov.meto.netcdf.cdl;

import java.io.IOException;

import uk.gov.meto.netcdf.integration.test.Logger;

public class Application {
	
	private static final Logger LOG = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		System.out.println("Starting the main method");
		String command = "ncgen -o " + "C:/data/my/file.nc" + " " + "array_geo.cdl";
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