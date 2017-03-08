package gov.loc.workflow;

import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/bagInPlace")
public class BagInPlace {

	@POST
    public void bagInPlace(@FormParam("bagId") String bagId,@HeaderParam("Content-Type") String contentType) {
		System.out.println("The bag: "+bagId+" has been placed-in successfully!");
    }
}
