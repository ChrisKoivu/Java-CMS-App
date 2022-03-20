/**
 * 
 */
package org.example.components.classes;

import javax.servlet.http.HttpSession;

import org.example.info.BasicDocumentInfo;
import org.hippoecm.hst.component.support.forms.FormMap;
import org.hippoecm.hst.component.support.forms.FormUtils;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.onehippo.cms7.essentials.components.EssentialsDocumentComponent;
import org.onehippo.cms7.essentials.components.info.EssentialsDocumentComponentInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author chris
 *
 */

@ParametersInfo(type = BasicDocumentInfo.class)
public class Webform extends EssentialsDocumentComponent{
	
	 private static Logger log = LoggerFactory.getLogger(EssentialsDocumentComponent.class);
     
	 // doAction is called on form post, where logic can be performed, and then flow goes to 
	 // doBeforeRender. If no post, or post has already been completed (ie, a page refresh)
	 // the flow continues to doBeforeRender
	 @Override
	 public void doAction(HstRequest request, HstResponse response) throws HstComponentException {

		 
		 HttpSession session = request.getSession();
		 // clear previous state if set, will set if valid submission
		 session.setAttribute("formState", null);
		 
			
		 FormMap map = new FormMap(request, new String[]{"firstName", "lastName","phone","email","streetAddress","city", "state", "zipcode"}); 
		 
		 
		// save the form state to the session for processing after validation
		 session.setAttribute("formState", map);
		 log.debug("do action method");
		 
		 
		   
	 }
	 
	 
	 // override doBeforeRender method in parent 
	 @Override
	 public void doBeforeRender(final HstRequest request, final HstResponse response) {
	    super.doBeforeRender(request, response);
	    final EssentialsDocumentComponentInfo paramInfo = getComponentParametersInfo(request);
	    final String documentPath = paramInfo.getDocument();
	    log.debug("Calling setContentBeanForPath for document path: [{}]", documentPath);
	        
	    // set the 'document' on the template
	    setContentBeanForPath(documentPath, request, response);
	    // pass the cparam attribute to the template
	    request.setAttribute(REQUEST_ATTR_PARAM_INFO, paramInfo);
	 }
	 
	  

}
