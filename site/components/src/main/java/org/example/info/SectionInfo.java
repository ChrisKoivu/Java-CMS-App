package org.example.info;

import org.hippoecm.hst.configuration.components.DynamicComponentInfo;
import org.hippoecm.hst.core.parameters.FieldGroup;
import org.hippoecm.hst.core.parameters.FieldGroupList;
import org.hippoecm.hst.core.parameters.Parameter;


@FieldGroupList({
  
    @FieldGroup(titleKey = "layout", value = { "pageSize", "background" })
})

public interface SectionInfo extends DynamicComponentInfo{
	@Parameter(name = "pageSize",
            defaultValue = "10",
            displayName = "Page Size")
   int getPageSize();
}
