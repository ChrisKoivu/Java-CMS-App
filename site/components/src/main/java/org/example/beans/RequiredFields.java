package org.example.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;

@HippoEssentialsGenerated(internalName = "myproject:requiredFields")
@Node(jcrType = "myproject:requiredFields")
public class RequiredFields extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "myproject:firstNameRequired")
    public Boolean getFirstNameRequired() {
        return getSingleProperty("myproject:firstNameRequired");
    }

    @HippoEssentialsGenerated(internalName = "myproject:lastNameRequired")
    public Boolean getLastNameRequired() {
        return getSingleProperty("myproject:lastNameRequired");
    }

    @HippoEssentialsGenerated(internalName = "myproject:phoneRequired")
    public Boolean getPhoneRequired() {
        return getSingleProperty("myproject:phoneRequired");
    }

    @HippoEssentialsGenerated(internalName = "myproject:emailRequired")
    public Boolean getEmailRequired() {
        return getSingleProperty("myproject:emailRequired");
    }

    @HippoEssentialsGenerated(internalName = "myproject:streetAddressRequired")
    public Boolean getStreetAddressRequired() {
        return getSingleProperty("myproject:streetAddressRequired");
    }

    @HippoEssentialsGenerated(internalName = "myproject:cityRequired")
    public Boolean getCityRequired() {
        return getSingleProperty("myproject:cityRequired");
    }

    @HippoEssentialsGenerated(internalName = "myproject:stateRequired")
    public Boolean getStateRequired() {
        return getSingleProperty("myproject:stateRequired");
    }

    @HippoEssentialsGenerated(internalName = "myproject:zipRequired")
    public Boolean getZipRequired() {
        return getSingleProperty("myproject:zipRequired");
    }
}
