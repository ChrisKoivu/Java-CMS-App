/**
 * 
 */
package org.example.components;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.example.info.BasicDocumentInfo;

import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.core.request.HstRequestContext;

import org.example.components.classes.BasicDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
 
/**
 * @author chris
 *
 */

@ParametersInfo(type = BasicDocumentInfo.class)
public class BlogComponent extends BasicDocument{

    private static Logger log = LoggerFactory.getLogger(BlogComponent.class); 

    @Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) {        
        super.doBeforeRender(request, response);
    }
    
    public void doBeforeServeResource(final HstRequest request, final HstResponse response) {  
    	 log.debug("called dobefore served resource methid");
    	 log.debug("param value: " + getAnyParameter(request, "action") );
    }
    
   
}
