/**
 * 
 */
package org.example.components.classes;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.example.info.BasicDocumentInfo;
import org.example.info.BasicDocumentListInfo;
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
import org.hippoecm.hst.util.PathUtils;
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

    private static Logger log = LoggerFactory.getLogger(BasicDocument.class); 

    @Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) {
        super.doBeforeRender(request, response);
        
        final BasicDocumentInfo paramInfo = getComponentParametersInfo(request);
        final String documentPath = paramInfo.getDocument();
        
        // if we are in preview, view selected document in CMS, else,
        // we will view document selected by relative path, unless the
        // getDocumentByRelativePath value is false, then we will use the 
        // selected document
        if(paramInfo.getDocumentByRelativePath() && !isPreview(request)) {
        	HippoBean bean = getContentBeanByRelativePath(request,  paramInfo);
        	if(bean != null) { 
        		setDynamicContentBeanForPath(bean, request, response); 
        	} else {
        		request.setAttribute("contentNotFound", "404: Page is not found");  
        	}
        } else {
        	setContentBeanForPath(documentPath, request, response);  
        }
        // send parameters to freemarker
        request.setAttribute(REQUEST_ATTR_PARAM_INFO, paramInfo);
    }
    
    /**
     * sets the bean found from the relative path to the request object
     * @param bean the HippoBean returned from the relative path query
     * @param request the HstRequest object
     * @param response the HstResponse object
     */
    public void setDynamicContentBeanForPath(final HippoBean bean, HstRequest request, final HstResponse response) {
        final HstRequestContext context = request.getRequestContext();
        try{
        	if (bean != null) {
        		 request.setModel(REQUEST_ATTR_DOCUMENT, bean); 
        	} else {
        		throw new NullPointerException("Content bean of type: " + bean.getClass().getName() + " is null. Unable to set bean on request.");
        	}
        } catch (NullPointerException e) {
        	e.printStackTrace();
        } 
    }
    
    /**
     * get content bean by the relative path. the relative path will match the jcr node name, in the 
     * pattern of /[page]/[jcr-node-name]
     * @param request
     * @param response
     * @return hippobean for the selected document
     */
    private HippoBean getContentBeanByRelativePath(HstRequest request,  final BasicDocumentInfo paramInfo) {
    	final String requestUri = request.getRequestURI();
    	HippoBeanIterator iterator = doSpecializedSearch(request, paramInfo, requestUri);
    	while(iterator.hasNext()) {
    		HippoBean bean = iterator.next();
    		if(StringUtils.contains(requestUri, bean.getName())) {
    			return bean;
    		}
    	} 
    	return null;
    }
    
    
    /** 
     * searches for all beans found on the scope path
     * @param request
     * @param paramInfo
     * @return 
     * @return
     */
    protected HippoBeanIterator doSpecializedSearch(final HstRequest request,  final BasicDocumentInfo paramInfo, String relPath) {
    	final String scopePath = findScopePath(request, paramInfo, relPath);
    	final HippoBean scope = getSearchScope(request, scopePath);
        final String documentTypes = paramInfo.getDocumentTypes(); 
      	try {
			HstQuery hstQuery = request.getRequestContext().getQueryManager().createQuery(scope, documentTypes);
			hstQuery.setLimit(paramInfo.getQueryLimit());  
			HstQueryResult result = hstQuery.execute();
			
    		final HippoBeanIterator hippoIterator = result.getHippoBeans();
    		hstQuery = null;
    		log.info("Searching scope: " + scopePath + ", " + hippoIterator.getSize() + " records have been returned");
    		return hippoIterator;
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (QueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	    return null;
	} 

	/**
     * rerieves the scope bean from the scope path
     * @param request
     * @param path
     * @return
     */
    protected HippoBean getSearchScope(final HstRequest request, final String path) {
    	final HstRequestContext context = RequestContextProvider.get();
        final HippoBean siteBean = context.getSiteContentBaseBean();
        HippoBean scope = null;

        if (Strings.isNullOrEmpty(path)) {
            scope = context.getContentBean();
        }

        if (scope == null) {
            scope = siteBean.getBean(path); 
        }
        return scope;
    }
    
    /**
     * attempts to find the search scope path by the relative path (request uri)
     * if this can't be done, then it will return the path to the site content base 
     * bean
     * @param request
     * @param paramInfo
     * @param relPath corresponds to the request uri for the current page
     * @return
     */
    protected String findScopePath(final HstRequest request,  final BasicDocumentInfo paramInfo, String relPath) {
    	 final HstRequestContext context = request.getRequestContext();
    	 final HippoBean siteContentBaseBean = context.getSiteContentBaseBean();
    	 String newScopePath = null; 
    	 
    	 List<HippoBean> possibleFolders = siteContentBaseBean.getChildBeans(HippoBean.class);
     
    	 // if cant find relative path folder, check from site content base
         for(HippoBean folder: possibleFolders) {
    		 String folderName = folder.getName();
             if(StringUtils.contains(folder.getClass().getName(), "HippoFolder")) { 
            	 if(StringUtils.contains(relPath, folderName)) {
            		newScopePath =  folderName;  
            		return newScopePath;
            	 }
    		 } 
    	 } 
		 return context.getSiteContentBasePath(); 
    } 
}
