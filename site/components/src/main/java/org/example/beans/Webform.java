package org.example.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.example.beans.FormFields;

@HippoEssentialsGenerated(internalName = "myproject:Webform")
@Node(jcrType = "myproject:Webform")
public class Webform extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "myproject:formTitle")
    public String getFormTitle() {
        return getSingleProperty("myproject:formTitle");
    }

    @HippoEssentialsGenerated(internalName = "myproject:formFields")
    public FormFields getFormFields() {
        return getBean("myproject:formFields", FormFields.class);
    }
}
