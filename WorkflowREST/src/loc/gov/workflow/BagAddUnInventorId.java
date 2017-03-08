package loc.gov.workflow;

import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/bagAddUniventorId")
public class BagAddUnInventorId {

	@POST
    public void bagAddUniventorId(@FormParam("bagId") String bagId,@HeaderParam("Content-Type") String contentType) {
		System.out.println("The bag: "+bagId+" has been uninventored successfully!");
    }
}
