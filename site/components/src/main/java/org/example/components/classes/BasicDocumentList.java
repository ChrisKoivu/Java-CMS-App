/**
 * 
 */
package org.example.components.classes;

 


import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.example.info.BasicDocumentListInfo; 
import org.hippoecm.hst.content.beans.query.HstQuery; 
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.builder.HstQueryBuilder; 
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.content.beans.standard.HippoDocumentIterator;
import org.hippoecm.hst.content.beans.standard.HippoFacetNavigationBean;
import org.hippoecm.hst.content.beans.standard.HippoResultSetBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.util.ContentBeanUtils; 
import org.onehippo.cms7.essentials.components.EssentialsListComponent; 
import org.onehippo.cms7.essentials.components.info.EssentialsPageable;
import org.onehippo.cms7.essentials.components.info.EssentialsSortable;
import org.onehippo.cms7.essentials.components.paging.DefaultPagination;
import org.onehippo.cms7.essentials.components.paging.Pageable;
import org.onehippo.cms7.essentials.components.utils.SiteUtils;
import org.onehippo.forge.selection.hst.contentbean.ValueList;
import org.onehippo.forge.selection.hst.util.SelectionUtil;
  
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

/**
 * HST component used for listing of documents.
 * 
 * This is different than the component class you get out of the box. This class allows 
 * parameters to be set for a standard HST component as well as a BaseHstDynamicComponent.
 * This is class is a hybrid of both worlds. HST Components require deployment of code to 
 * change a configuration set, while the residual parameters provided by the BaseHstDynamicComponent
 * do not. For dynamic parameters, it is simply a matter of adding the configuration item to the
 * catalog entry for the component using this class. But this class also allows you to use sort and
 * pagination, or do quick specialized searches without pagination where this functionality can be expanded 
 * to handle unique use cases, which uses the residualParameterValues for additional flexibility.
 *  
 *
 */
@ParametersInfo(type = BasicDocumentListInfo.class)
public class BasicDocumentList extends EssentialsListComponent {
    protected static final String REQUEST_ATTR_DYNAMIC_PARAMS = "dparams";
    protected static final String REQUEST_ATTR_COLLECTION = "collection";
    protected static final String REQUEST_ATTR_VALUELISTS = "valuelists";
    
    
    private static Logger log = LoggerFactory.getLogger(BasicDocumentList.class);

    public void doAction(HstRequest request, HstResponse response) throws HstComponentException {

    }
    
    @Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) { 
        final BasicDocumentListInfo paramInfo = getComponentParametersInfo(request);
         
        
        final String path = getScopePath(paramInfo);
        log.debug("Calling BasicDocumentListComponent for documents path:  [{}]", path);
        final HippoBean scope = getSearchScope(request, path);

        if (scope == null) {
            log.warn("Search scope was null");
            handleInvalidScope(request, response);
            return;
        }
        
        if(paramInfo.getDoSpecializedSearch()) {
        	 HippoBeanIterator iterator = doSpecializedSearch(request, paramInfo, scope);
        	 populateRequestSpecialized(request, paramInfo, iterator);
        } else {
        	final Pageable<? extends HippoBean> pageable;
            if (scope instanceof HippoFacetNavigationBean) {
                pageable = doFacetedSearch(request, paramInfo, scope);
            } else {
                pageable = doSearch(request, paramInfo, scope);
            }
            populateRequest(request, paramInfo, pageable); 
        } 
        
        final Map<String, Object> dparams = getDynamicParamValues(request);
        if(getValueLists(request, dparams) != null) {
        	request.setAttribute(REQUEST_ATTR_VALUELISTS, getValueLists(request, dparams));
        }
    }
    
    public void doBeforeServeResource(HstRequest request, HstResponse response) throws HstComponentException {
      
    }

    protected Map<String, Object> getDynamicParamValues(final HstRequest request) {
    	final BasicDocumentListInfo paramInfo = getComponentParametersInfo(request);  
    	final Map<String, Object> dparams = paramInfo.getResidualParameterValues();
    	return dparams;    	
    }
    
    /** 
     * search without filters or ordering, just searching by defined scope path
     * @param request
     * @param paramInfo
     * @return
     */
    protected HippoBeanIterator doSpecializedSearch(final HstRequest request,  final BasicDocumentListInfo paramInfo) {
    	final String scopePath = getScopePath(paramInfo);
    	final HippoBean scope = getSearchScope(request, scopePath);
    	final String documentTypes = paramInfo.getDocumentTypes(); 
    	
    	try {
			HstQuery hstQuery = request.getRequestContext().getQueryManager().createQuery(scope, documentTypes);
			HstQueryResult result = hstQuery.execute();
    		final HippoBeanIterator hippoIterator = result.getHippoBeans();
    		hstQuery = null;
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
     * search without limits, which can cause an infinite number of content items. should be used with discretion
     * @param request
     * @param paramInfo
     * @param scope
     * @return
     */
    protected HippoBeanIterator doSpecializedSearch(final HstRequest request, final BasicDocumentListInfo paramInfo,
			final HippoBean scope) { 
		return doSpecializedSearch(request, paramInfo, scope, false);
	}
    
    /**
     * Search with a defined limit. targeted for cases where we need to do something outside the 
     * pageable search results. Should use true for useLimit
     * @param request
     * @param paramInfo
     * @param scope
     * @param useLimit
     * @return
     */
    protected HippoBeanIterator doSpecializedSearch(final HstRequest request, final BasicDocumentListInfo paramInfo,
			final HippoBean scope, boolean useLimit) {    	

    	final String documentTypes = paramInfo.getDocumentTypes(); 
    	
    	try {
    		HstQuery hstQuery = request.getRequestContext().getQueryManager().createQuery(scope, documentTypes);
    		 
    		String query = getSearchQuery(request);
    		if(query != null) {
    			log.info("search param: " + query);
			    Filter filter = hstQuery.createFilter();
			    hstQuery.setFilter(filter);
			    filter.addContains(".", query); 
    		}
    		if(useLimit) {
    			int limit = getQueryLimit(paramInfo);
    			hstQuery.setLimit(limit); 
    		}
    		if(paramInfo.getSortField() != null) {
    			applyOrdering(request, hstQuery, paramInfo);
    		}
    		log.info("begin specialized query");
    		HstQueryResult result = hstQuery.execute();
    		final HippoBeanIterator hippoIterator = result.getHippoBeans();
    		log.info("end specialized query"); 
    		log.info(hippoIterator.getSize() + " records have been found.");
    		hstQuery = null;
    		return hippoIterator;    		
    	} catch (QueryException e) {
    		log.error("specialized query failed");
    		e.printStackTrace();
    	} 
		return null;
	} 
    
	private int getQueryLimit(BasicDocumentListInfo paramInfo) {
		// TODO Auto-generated method stub
		final int limit = paramInfo.getPageSize();
		return limit;
	}

	protected HashMap<String, Object> getValueLists(HstRequest request, Map<String, Object> residualParams ) {
    	HashMap<String, Object> valueListMap = new HashMap<>(); 
    	Object valuelistCsvString = residualParams.get("valuelists");
    	if(valuelistCsvString != null) { 
    		String [] keys = getValueListKeys((String) valuelistCsvString);
    		if(keys != null) {
    			for(String  key : keys) {
    				if(getValueList(request, key) != null) {
    					valueListMap.put(key, getValueList(request, key));
    				} else {
    					log.error("valuelist with key: <== " + key + " ==> is not registered");
    					log.error ("To register a valuelist, use the Selections tool in Essentials");
    				}
    			} 
    		} 
    	} 
    	return valueListMap;
    }
    
    private String [] getValueListKeys(String csvString) {;
		return StringUtils.split(csvString, ",");
    }
    
    protected Map <String, String> getValueList(HstRequest request, String key){
    	final HstRequestContext context = request.getRequestContext();
    	ValueList selectedList = SelectionUtil.getValueListByIdentifier(key, context);
    	if(selectedList != null) {
    		return SelectionUtil.valueListAsMap(selectedList);
    	}
    	return null;
    } 
    
    protected void populateRequestSpecialized(final HstRequest request, final BasicDocumentListInfo paramInfo, final HippoBeanIterator hippoIterator) {
    	request.setAttribute(REQUEST_ATTR_QUERY, getSearchQuery(request));
        request.setModel(REQUEST_ATTR_COLLECTION, hippoIterator);
        request.setAttribute(REQUEST_ATTR_PARAM_INFO, paramInfo);
    }
   
    /**
     * Apply ordering (if order field name is provided)
     * using this method over the essentials list implementation
     *
     * @param request       instance of  HstRequest
     * @param query         instance of  HstQuery
     * @param componentInfo instance of EssentialsDocumentListComponentInfo
     * @param <T>           component info class.
     */
     
    protected <T extends BasicDocumentListInfo> void applyOrdering(final HstRequest request, final HstQuery query, final T componentInfo) {
        final String sortField = componentInfo.getSortField();
        if (Strings.isNullOrEmpty(sortField)) {
            return;
        }
        final String sortOrder = Strings.isNullOrEmpty(componentInfo.getSortOrder()) ? EssentialsSortable.DESC : componentInfo.getSortOrder();
        if (sortOrder.equals(EssentialsSortable.DESC)) {
            query.addOrderByDescending(sortField);
        } else {
            query.addOrderByAscending(sortField);
        }
    } 
    
    protected <T extends BasicDocumentListInfo>
    Pageable<? extends HippoBean> doSearch(final HstRequest request, final T paramInfo, final HippoBean scope) {
        try {
            final HstQuery build = buildQuery(request, paramInfo, scope);
            if (build != null) {
                return executeQuery(request, paramInfo, build);
            }
        } catch (QueryException e) {
            log.error("Error running query", e.getMessage());
            log.debug("Query exception: ", e);
        }
        return null;
    }
    
    /**
     * Execute the search given a facet navigation scope.
     *
     * @param request   current HST request
     * @param paramInfo component parameters
     * @param scope     bean representing search scope
     * @param <T>       type of component info interface
     * @return pageable search results, or null if search failed.
     */
    protected <T extends BasicDocumentListInfo>
    Pageable<HippoBean> doFacetedSearch(final HstRequest request, final T paramInfo, final HippoBean scope) {

        Pageable<HippoBean> pageable = DefaultPagination.emptyCollection();
        final String relPath = SiteUtils.relativePathFrom(scope, request.getRequestContext());
        final HippoFacetNavigationBean facetBean = ContentBeanUtils.getFacetNavigationBean(relPath, getSearchQuery(request));
        if (facetBean != null) {
            final HippoResultSetBean resultSet = facetBean.getResultSet();
            if (resultSet != null) {
                final HippoDocumentIterator<HippoBean> iterator = resultSet.getDocumentIterator(HippoBean.class);
                pageable = getPageableFactory().createPageable(iterator, resultSet.getCount().intValue(), paramInfo.getPageSize(), getCurrentPage(request));
            }
        }
        return pageable;
    }

    
    /**
     * Build the) HST query for the list.
     *
     * @param request   the current request
     * @param paramInfo the parameter info
     * @param scope     the scope of the query
     * @return the HST query to execute
     */
    protected <T extends BasicDocumentListInfo>
    HstQuery buildQuery(final HstRequest request, final T paramInfo, final HippoBean scope) {
        final String documentTypes = paramInfo.getDocumentTypes();
        final String[] types = SiteUtils.parseCommaSeparatedValue(documentTypes);
        if (log.isDebugEnabled()) {
            log.debug("Searching for document types:  {}, and including subtypes: {}", documentTypes, paramInfo.getIncludeSubtypes());
        }

        HstQueryBuilder builder = HstQueryBuilder.create(scope);
        return paramInfo.getIncludeSubtypes() ? builder.ofTypes(types).build() : builder.ofPrimaryTypes(types).build();
    }

    /**
     * Execute the list query.
     *
     * @param request   the current request
     * @param paramInfo the parameter info
     * @param query     the query to execute
     * @return the pageable result
     * @throws QueryException query exception when query fails
     */
    protected <T extends BasicDocumentListInfo>
    Pageable<HippoBean> executeQuery(final HstRequest request, final T paramInfo, final HstQuery query) throws QueryException {
        final int pageSize = getPageSize(request, paramInfo);
        final int page = getCurrentPage(request);
        query.setLimit(pageSize);
        query.setOffset((page - 1) * pageSize);
        applyOrdering(request, query, paramInfo);
        applyExcludeScopes(request, query, paramInfo);
        buildAndApplyFilters(request, query);

        final HstQueryResult execute = query.execute();
        return getPageableFactory().createPageable(
                execute.getHippoBeans(),
                execute.getTotalSize(),
                pageSize,
                page);
    }


    protected <T extends BasicDocumentListInfo>
    void applyExcludeScopes(final HstRequest request, final HstQuery query, final T paramInfo) {
        // just an extension point for time being
    } 
    
    /**
     * find bean by relative path, the concept is to use a relative path containing the jcr node name
     * @param request 
     * @param paramInfo
     * @param scope
     * @return
     */
    protected HippoBean getBeanByPath(final HstRequest request, final BasicDocumentListInfo paramInfo,
			final HippoBean scope) { 
    	final HippoBeanIterator iterator = doSpecializedSearch(request, paramInfo);
    	final String path = request.getRequestURI();
    	try {
    		while(iterator.hasNext()) {
        		HippoBean bean = iterator.next();
        		if(applyPathFilter(path, bean.getName())){
        			return bean;
        		}
        	}
    		throw new NullPointerException("no content beans match the selected path.");
    	} catch (NullPointerException e) {
    		e.printStackTrace();
    	}
		return null;
    }    
    
    protected boolean applyPathFilter(String path, String jcrNodeName) {     
    	// if path and node name is null, whitespace, or empty then there is no match to the filter condition
    	if( StringUtils.isNotBlank(path) && StringUtils.isNotBlank(jcrNodeName)) {
 		   return StringUtils.contains(path, jcrNodeName);
 		}
    	return false;
    }

    /**
     * Determine the path to use for the scope of the query. Returns null
     * when no path is defined.
     *
     * @param paramInfo the settings of the component
     * @return the scope of the query
     */
    protected String getScopePath(final BasicDocumentListInfo paramInfo) {
        if (paramInfo == null) {
            log.warn("Component parameter was null for:  {}", getClass().getName());
            return null;
        }
        return paramInfo.getPath();
    }
    
    protected void handleInvalidScope(final HstRequest request, final HstResponse response) {
        // TODO determine what to do with invalid scope
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        if (log.isDebugEnabled()) {
            throw new HstComponentException("EssentialsListComponent needs a valid scope to display documents");
        }
    }
}

