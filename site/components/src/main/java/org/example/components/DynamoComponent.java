package org.example.components;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.example.info.SectionInfo;
import org.hippoecm.hst.component.support.bean.dynamic.BaseHstDynamicComponent;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.Parameter;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.onehippo.forge.selection.hst.contentbean.ValueList;
import org.onehippo.forge.selection.hst.util.SelectionUtil;
import org.onehippo.repository.l10n.LocalizationService;
import org.hippoecm.hst.configuration.components.DynamicComponentInfo;
import org.hippoecm.hst.configuration.components.DynamicParameter;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.resourcebundle.ResourceBundleUtils;


@ParametersInfo(type = SectionInfo.class)
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
        
        if(parameters.get("resourcebundle")!=null) {
        	Locale locale = null;
        	if(parameters.get("resourcebundlelocale")!=null) {
        		locale = (Locale) parameters.get("resourcebundlelocale");
        	}
        	
        	String resourcebundleKey = (String) parameters.get("resourcebundle");
        	HashMap<String, String> bundle = getResourceBundle(resourcebundleKey, locale);
            request.setAttribute(resourcebundleKey.replace(".",""), bundle);
        }
        
        if(parameters.get("valuelist")!=null) {
            String valuelistKey = (String) parameters.get("valuelist");
            request.setAttribute(valuelistKey, getValueList(valuelistKey));
        }

        request.setAttribute("parameters", parameters);             
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
    private static HashMap<String, String> getResourceBundle(String identifier, Locale locale) {
    	if(identifier !=null) {
    		ResourceBundle rb = ResourceBundleUtils.getBundle(identifier, locale);
    	    Enumeration<String> e = rb.getKeys();
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