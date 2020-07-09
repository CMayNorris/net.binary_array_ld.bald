package uk.gov.meto.netcdf.integration.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.gov.meto.netcdf.cdl.Application;
import ucar.nc2.NetcdfFile;

import java.io.IOException;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

public class IT_CDLConversion {
	
	private static final Logger LOG = LoggerFactory.getLogger(IT_CDLConversion.class);

/*	public static void main(String[] args) {
		LOG.debug("integration test running");

	}*/
	@Test
	public void shouldConvertToReadableNetCDF() {
		Application app = new Application();
		app.main(null);
		  String filename = "C:/data/my/file.nc";
		  NetcdfFile ncfile = null;
		  try {
			  assertNull(ncfile);
		    ncfile = NetcdfFile.open(filename);
		   // process( ncfile);
		  } catch (IOException ioe) {
			fail("Unexpected exception: " + ioe.getMessage());
		    LOG.error("trying to open " + filename, ioe);
		  } finally { 
		    if (null != ncfile) try {
		      ncfile.close();
		      System.out.println(ncfile.toString() );
		      LOG.debug(ncfile.toString());
		      assertNotNull(ncfile);
		    } catch (IOException ioe) {
		      fail("Unexpected exception: " + ioe.getMessage());
		      LOG.error("trying to close " + filename, ioe);
		    }
		  }
	}

}
