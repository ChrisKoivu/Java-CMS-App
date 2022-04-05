/**
 * 
 */
package org.example.info;

import org.hippoecm.hst.configuration.components.DynamicComponentInfo;
import org.hippoecm.hst.core.parameters.FieldGroup;
import org.hippoecm.hst.core.parameters.FieldGroupList;
import org.hippoecm.hst.core.parameters.JcrPath;
import org.hippoecm.hst.core.parameters.Parameter;
import org.onehippo.cms7.essentials.components.info.EssentialsDocumentComponentInfo;

/**
 * @author chris
 *
 *  Using 2 different sets of component params. One from the DynamicComponentParams,
 *  the other from the standard params (cparams)
 *  
 *  
 *  the pickerSelectableNodeTypes can override the default setting of hippo:document to the 
 *  document types you want to use, and can be comma separated for more than one document type
 */

@FieldGroupList({ 
	  @FieldGroup(value = {"document"}, titleKey = "Select Document"),
	  @FieldGroup(value = {"background"}, titleKey = "Background"),
	  @FieldGroup(value = {"topmargin", "bottommargin", "toppadding", "bottompadding", "leftpadding","rightpadding"}, titleKey = "Padding/Margins"),
	  @FieldGroup(value = {"textalign", "verticalalign"}, titleKey = "Alignment"),
	  @FieldGroup(value = {"documentByRelativePath", "documentTypes", "queryLimit"}, titleKey = "Document by path"),
})

public interface BasicDocumentInfo extends DynamicComponentInfo {
    @Parameter(name = "document", required = true)
    @JcrPath(
            isRelative = true,
            pickerSelectableNodeTypes = {"hippo:document"}
    )
    String getDocument();
    
    @Parameter(name = "documentByRelativePath", defaultValue = "false", displayName = "Retrieve documents by relative path on page")
    Boolean getDocumentByRelativePath();
    
    @Parameter(name = "documentTypes", displayName = "Enter document type to retrieve")
    String getDocumentTypes();
    
    @Parameter(name = "queryLimit", required = true, defaultValue = "100", displayName = "Base query limit")
    int getQueryLimit();
}
