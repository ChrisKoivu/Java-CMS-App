package org.example.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import java.util.List;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

@HippoEssentialsGenerated(internalName = "myproject:HtmlContent")
@Node(jcrType = "myproject:HtmlContent")
public class HtmlContent extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "myproject:html")
    public List<HippoHtml> getHtml() {
        return getChildBeansByName("myproject:html", HippoHtml.class);
    }
}
