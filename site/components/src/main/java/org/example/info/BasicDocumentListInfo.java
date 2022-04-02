package org.example.info;
 
 

import org.hippoecm.hst.configuration.components.DynamicComponentInfo;
import org.hippoecm.hst.core.parameters.FieldGroup;
import org.hippoecm.hst.core.parameters.FieldGroupList;
import org.hippoecm.hst.core.parameters.JcrPath;
import org.hippoecm.hst.core.parameters.Parameter;
import org.onehippo.cms7.essentials.components.info.EssentialsPageable;
import org.onehippo.cms7.essentials.components.info.EssentialsSortable;

@FieldGroupList({
	  @FieldGroup(value = {"background"}, titleKey = "Background"),
	  @FieldGroup(value = {"topmargin", "bottommargin", "toppadding", "bottompadding", "leftpadding","rightpadding"}, titleKey = "Padding/Margins"),
	  @FieldGroup(value = {"textalign", "verticalalign"}, titleKey = "Alignment"),
	  @FieldGroup(value = {"path", "includeSubtypes", "documentTypes"}, titleKey = "Content Selection"),
	  @FieldGroup(value = {"doSpecializedSearch"}, titleKey = "Specialized Search")
})
public interface BasicDocumentListInfo extends EssentialsSortable, EssentialsPageable, DynamicComponentInfo {
    @Parameter(name = "path")
    @JcrPath(
            isRelative = true,
            pickerSelectableNodeTypes = {"hippostd:folder"}
    )
    String getPath();

    @Parameter(name = "includeSubtypes", defaultValue = "true")
    Boolean getIncludeSubtypes();
    
    @Parameter(name = "doSpecializedSearch", defaultValue = "true", displayName = "Do specialized search")
    Boolean getDoSpecializedSearch();

    @Parameter(name = "documentTypes")
    String getDocumentTypes();
}

