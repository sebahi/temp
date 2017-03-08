package gov.loc.workflow;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/bagAddUpdate")
public class BagAddUpdate {

	@GET
    @Path("/{param}")
    public void bagAddUpdate(@PathParam("param") String bagId) {
		System.out.println("inside bagAddUpdate method, the bag " + bagId + " add / update should implement here!");
    }
	
	@POST
    public void bagAddUpdate(@FormParam("bagId") String bagId,@HeaderParam("Content-Type") String contentType) {
		System.out.println("The bag: "+bagId+" has been added/updated successfully!");
    }
}
