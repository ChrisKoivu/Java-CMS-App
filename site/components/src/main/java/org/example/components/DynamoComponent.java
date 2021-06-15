package org.example.components;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.hippoecm.hst.component.support.bean.dynamic.BaseHstDynamicComponent;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.onehippo.forge.selection.hst.contentbean.ValueList;
import org.onehippo.forge.selection.hst.util.SelectionUtil;
import org.onehippo.repository.l10n.LocalizationService;
import org.hippoecm.hst.configuration.components.DynamicComponentInfo;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.resourcebundle.ResourceBundleUtils;


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
        
        HashMap<String, String> bundle = retrieveResourceBundle("essentials.global", null);
        request.setAttribute("resourceBundle", bundle);
        
        request.setAttribute("parameters", parameters);
        
        String valuelistKey = (String) parameters.get("valuelist");
        request.setAttribute(valuelistKey, getValueList(valuelistKey));        
    }
    
    
    /**
     * allows for adding a value list to the request object from the delivery
     * tier based on the key set in the value list manager
     * @param identifier
     * @return Map
     */
    
    private Map<String, String> getValueList(String identifier) {        
        final ValueList chosenValueList = 
                SelectionUtil.getValueListByIdentifier(identifier, RequestContextProvider.get());
            if (chosenValueList != null) {
            	return SelectionUtil.valueListAsMap(chosenValueList);
            }
			return null;
    }  
    
    /**
     * returns resource bundle based on identifier and locale
     * @param identifier
     * @param locale
     * @return
     */
    private static HashMap<String, String> retrieveResourceBundle(String identifier, Locale locale) {
    	if(identifier !=null) {
    		ResourceBundle rb = ResourceBundleUtils.getBundle(identifier, locale);
    		Enumeration<String>	bundleKeys = rb.getKeys();
    	    Enumeration<String> e = bundleKeys;
    	    HashMap<String, String> rbMap = new HashMap<String, String>();
    	    
    	    while (e.hasMoreElements()) {
    	        String param = e.nextElement();
    	        rbMap.put(param, rb.getString(param));
    	    }
    	    return rbMap;
    	}
    	return null;
    }
    
    
}
