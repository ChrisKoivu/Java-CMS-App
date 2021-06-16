<#include "../../include/imports.ftl">

<#-- @ftlvariable name="document" type="org.example.beans.Banner" -->
<#if document??>
   <div class="has-edit-button">
      <@hst.manageContent hippobean=document />
      <h4>${document.title} </h4>

      <#list essentialsglobal as k,v>
        ${"resource bundle key: " + k + ", resource bundle value: " + v}
      </#list>
  </div>

   
<#elseif editMode>
  <div class="has-edit-button">
    <img src="<@hst.link path="/images/essentials/catalog-component-icons/simple-content.svg" />"> Click to edit Simple Content
    <@hst.manageContent documentTemplateQuery="new-content-document" parameterName="document" rootPath="content"/>
  </div>
</#if>