<#include "../../include/imports.ftl">

<#-- @ftlvariable name="document" type="org.example.beans.Banner" -->
<#if document??>
   <div class="has-edit-button">
   <h4>${document.title} </h4>

   <#list resourceBundle as k,v>
     ${"resource bundle key: " + k + ", resource bundle value: " + v}
   </#list>
   <#list formSettings as k, v>
     ${"Key: " + k + ", Value: " + v}
   </#list>

   ${resourceBundle["footer.text"]}

   
<#elseif editMode>
  <div class="has-edit-button">
    <img src="<@hst.link path="/images/essentials/catalog-component-icons/simple-content.svg" />"> Click to edit Simple Content
    <@hst.manageContent documentTemplateQuery="new-content-document" parameterName="document" rootPath="content"/>
  </div>
</#if>