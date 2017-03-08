package gov.loc.workflow;

import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/deleteStagingCopy")
public class BagDeleteStagingCopy {

	@POST
    public void bagDeleteStagingCopy(@FormParam("bagId") String bagId,@HeaderParam("Content-Type") String contentType) {
		System.out.println("The bag: "+bagId+" delete staging copy has been implemented successfully!");
    }
}
