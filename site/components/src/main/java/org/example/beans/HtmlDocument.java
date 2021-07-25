package org.example.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import java.util.List;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

@HippoEssentialsGenerated(internalName = "myproject:HtmlDocument")
@Node(jcrType = "myproject:HtmlDocument")
public class HtmlDocument extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "myproject:htmlContent")
    public List<HippoHtml> getHtmlContent() {
        return getChildBeansByName("myproject:htmlContent", HippoHtml.class);
    }
}
