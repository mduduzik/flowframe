package org.flowframe.erp.remote.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.flowframe.erp.remote.services.FieldNameTransformer;
import org.flowframe.erp.remote.services.IERPPartnerService;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.flowframe.kernel.common.utils.StringUtil;
import org.flowframe.kernel.common.utils.Validator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;

@Transactional
@Service
public class OBPartnerServicesImpl extends BaseOBJSONWSServicesImpl implements IERPPartnerService {

	@Override
	public Boolean isAvailable()    throws Exception {
		return Validator.isNotNull(createConnection(getOpenbravoURL(), "GET"));
	}

	
	
	/**
	 * 
	 * Services
	 * 
	 */
	@Override
	public Organization getOrganizationById(String orgId) throws Exception {
		JSONObject jsonResponse = get("BusinessPartner", orgId);
		Organization rec = new JSONDeserializer<Organization>()
			    .deserialize(jsonResponse.toString());
		
		return rec;		
	}



	@Override
	public List<Organization> getAllOrganizations() throws Exception {
		JSONArray jsonResponse = getAll("BusinessPartner");
		ArrayList<Organization> recs = new JSONDeserializer<ArrayList<Organization>>()
			    .use("values", Organization.class)
			    .deserialize(jsonResponse.toString());
		
		return recs;
	}	
}