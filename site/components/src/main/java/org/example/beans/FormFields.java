package org.example.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;

@HippoEssentialsGenerated(internalName = "myproject:formFields")
@Node(jcrType = "myproject:formFields")
public class FormFields extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "myproject:firstNameVisible")
    public Boolean getFirstNameVisible() {
        return getSingleProperty("myproject:firstNameVisible");
    }

    @HippoEssentialsGenerated(internalName = "myproject:lastNameVisible")
    public Boolean getLastNameVisible() {
        return getSingleProperty("myproject:lastNameVisible");
    }

    @HippoEssentialsGenerated(internalName = "myproject:phoneVisible")
    public Boolean getPhoneVisible() {
        return getSingleProperty("myproject:phoneVisible");
    }

    @HippoEssentialsGenerated(internalName = "myproject:emailVisible")
    public Boolean getEmailVisible() {
        return getSingleProperty("myproject:emailVisible");
    }

    @HippoEssentialsGenerated(internalName = "myproject:streetAddressVisible")
    public Boolean getStreetAddressVisible() {
        return getSingleProperty("myproject:streetAddressVisible");
    }

    @HippoEssentialsGenerated(internalName = "myproject:cityVisible")
    public Boolean getCityVisible() {
        return getSingleProperty("myproject:cityVisible");
    }

    @HippoEssentialsGenerated(internalName = "myproject:stateVisible")
    public Boolean getStateVisible() {
        return getSingleProperty("myproject:stateVisible");
    }

    @HippoEssentialsGenerated(internalName = "myproject:zipVisible")
    public Boolean getZipVisible() {
        return getSingleProperty("myproject:zipVisible");
    }
}
