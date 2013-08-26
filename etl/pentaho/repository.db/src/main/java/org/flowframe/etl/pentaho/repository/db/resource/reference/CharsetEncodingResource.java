package org.flowframe.etl.pentaho.repository.db.resource.reference;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/15/13
 * Time: 6:32 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("encoding")
@Component
public class CharsetEncodingResource {
    @GET
    @Path("/getall")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAll(@QueryParam("callback") String callback) throws JSONException {
        JSONObject wrapper = new JSONObject();
        JSONArray types = new JSONArray();

        List<Charset> values = new ArrayList<Charset>(Charset.availableCharsets().values());
        for (int i=0;i<values.size();i++)
        {
            Charset charSet = (Charset)values.get(i);
            JSONObject obj = new JSONObject();
            obj.put("id", i);
            obj.put("code",charSet.name());
            obj.put("description",charSet.displayName());
            types.put(obj);
        }

        long totalCount = values.size();
        wrapper.put("totalCount",totalCount);
        wrapper.put("data",types);

        if (callback != null)
            return callback+"("+wrapper.toString()+");";
        else
            return wrapper.toString();
    }
}
