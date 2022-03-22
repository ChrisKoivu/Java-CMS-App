/**
 * 
 */
package org.example.components.classes;

import org.example.info.BasicDocumentInfo;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.onehippo.cms7.essentials.components.CommonComponent;
import org.onehippo.cms7.essentials.components.EssentialsDocumentComponent;
import org.onehippo.cms7.essentials.components.info.EssentialsDocumentComponentInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
 
/**
 * @author chris
 *
 */

@ParametersInfo(type = BasicDocumentInfo.class)
public class BasicDocument extends CommonComponent{

    private static Logger log = LoggerFactory.getLogger(EssentialsDocumentComponent.class);

    @Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) {
        super.doBeforeRender(request, response);
        
        final BasicDocumentInfo paramInfo = getComponentParametersInfo(request);
        final String documentPath = paramInfo.getDocument();
        
        log.debug("Calling setContentBeanForPath for document path: [{}]", documentPath);
        // sets the bean on the template with the variable name 'document' 
        setContentBeanForPath(documentPath, request, response);
        // sets the component params from the common component class to the template under
        // the variable name 'cparam'
        request.setAttribute(REQUEST_ATTR_PARAM_INFO, paramInfo);
    }
    
    /**
     * returns hippobean based on selected path 
     * @param request HstRequest object
     * @param response HstResponse object
     * @return hippobean for the selected document
     */
    private HippoBean getContentBean(HstRequest request, final HstResponse response) {
	    final BasicDocumentInfo paramInfo = getComponentParametersInfo(request);
	    final String documentPath = paramInfo.getDocument();
	    
        final HstRequestContext context = request.getRequestContext();
        HippoBean bean = null;

        if (!Strings.isNullOrEmpty(documentPath)) {
            final HippoBean root = context.getSiteContentBaseBean();
            bean = root.getBean(documentPath); 
        }  
        return bean;
   }

}
