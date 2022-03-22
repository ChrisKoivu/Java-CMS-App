/**
 * 
 */
package org.example.components.classes;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.example.info.BasicDocumentInfo;
import org.hippoecm.hst.component.support.forms.FormMap;
import org.hippoecm.hst.component.support.forms.FormUtils;
import org.hippoecm.hst.content.beans.standard.HippoBean; 
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.onehippo.cms7.essentials.components.CommonComponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
 
/**
 * @author chris
 *
 */

@ParametersInfo(type = BasicDocumentInfo.class)
public class Webform extends CommonComponent{
	
	 private static Logger log = LoggerFactory.getLogger(Webform.class);
     
	 // doAction is called on form post, where logic can be performed, and then flow goes to 
	 // doBeforeRender. If no post, or post has already been completed (ie, a page refresh)
	 // the flow continues to doBeforeRender
	 @Override
	 public void doAction(HstRequest request, HstResponse response) throws HstComponentException {
         log.debug("do action method in the webform component class");
         ArrayList<String> requiredFields = null;
         
         if(getFormBean(request, response) != null) {
        	 Webform form = getFormBean(request, response);
     	     requiredFields = form.getRequiredFields();
         }
		 
		 HttpSession session = request.getSession(); 
		 // clear previous state if set, will set if valid submission
		 if( session.getAttribute("formState") != null) {
			 session.removeAttribute("formState");
		 } 
			
		 FormMap map = new FormMap(request, new String[]{"firstName", "lastName","phone","email","streetAddress","city", "state", "zipcode"}); 
		 FormValidation validation = new FormValidation(map, requiredFields);
		 
		 // returns an arraylist of invalid entries. a size of zero means all entries
		 // validated 
		 ArrayList<String> result = validation.validateSubmission(map); 
		 if(result.size() == 0) {
			// save the form state to the session for processing after validation
			// right now saving the whole form map
			// TODO: save state 
			session.setAttribute("formState", map);			 
		 } else {
			 throw new HstComponentException("form submission is invalid");
		 } 
		 log.debug("do action method in webform class");
		  
	 }
	 
	 
	 // override doBeforeRender method in parent 
	 @Override
	 public void doBeforeRender(final HstRequest request, final HstResponse response) {
	    super.doBeforeRender(request, response);
	    
	    log.debug("do before render method");
	   
	    final BasicDocumentInfo paramInfo = getComponentParametersInfo(request);
	    final String documentPath = paramInfo.getDocument();
	    
	    log.debug("document path is: " + documentPath);
	   
	   
			   
	    // set the 'document' on the template
	    setContentBeanForPath(documentPath, request, response);
	    // pass the cparam attribute to the template
	    request.setAttribute(REQUEST_ATTR_PARAM_INFO, paramInfo);
	    
	 }
	 
	 private ArrayList<String> getRequiredFields(){
		 ArrayList<String> temp = new ArrayList<String>();
		 
		return null;
		 
	 }
	 
	 private Webform getFormBean(HstRequest request, final HstResponse response) {
		    final BasicDocumentInfo paramInfo = getComponentParametersInfo(request);
		    final String documentPath = paramInfo.getDocument();
		    
	        final HstRequestContext context = request.getRequestContext();
	        Webform form = null;

	        if (!Strings.isNullOrEmpty(documentPath)) {
	            final HippoBean root = context.getSiteContentBaseBean();
	            form = root.getBean(documentPath); 
	        }  
	        return form;
	 }
	 
	

}
