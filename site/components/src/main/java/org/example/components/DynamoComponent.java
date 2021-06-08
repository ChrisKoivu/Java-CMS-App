package org.example.components;

import java.util.HashMap;
import java.util.Map;

import org.hippoecm.hst.component.support.bean.dynamic.BaseHstDynamicComponent;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.configuration.components.DynamicComponentInfo;


@ParametersInfo(type = DynamicComponentInfo.class)
public class DynamoComponent extends BaseHstDynamicComponent {

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);
        
        DynamicComponentInfo params = getComponentParametersInfo(request);
        Map<String,Object> residualParams = params.getResidualParameterValues();    
        HashMap<String,Object> parameters = new HashMap<>();
        
        for (Map.Entry<String,Object> entry : residualParams.entrySet()) {        	   
        	// creating a hash map for parameter values only to use in the template
        	if(!entry.getKey().equals("document")) {
        	   parameters.put(entry.getKey(), entry.getValue());
        	}
        }
        System.out.println("<== parameter values are: " + parameters + " ======>");
        request.setAttribute("parameters", parameters);
           
    }
}
