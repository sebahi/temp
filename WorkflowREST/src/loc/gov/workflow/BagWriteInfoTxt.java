package loc.gov.workflow;

import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/writeInfoTxt")
public class BagWriteInfoTxt {

	@POST
    public void bagWriteInfoTxt(@FormParam("bagId") String bagId,@HeaderParam("Content-Type") String contentType) {
		System.out.println("The bag: "+bagId+" write info implemented successfully!");
    }
}
