<#include "../../include/imports.ftl">

<#-- @ftlvariable name="document" type="org.example.beans.Banner" -->
<#if document??>
   <div class="has-edit-button">
      <@hst.manageContent hippobean=document />
      <h1>Html Template </h1>
      
      <#list document.htmlContent as block>
      <#--  ${block.content}  -->
<#--       
       <hst:html hippohtml="${block.content}" fullyQualifiedLinks="true" />  -->
       <hst:html formattedhtml="${block.content}" /> 
       <hst:html text="${block.getContent()}" />
      </#list>
  </div>

   
<#elseif editMode>
  <div class="has-edit-button">
    <img src="<@hst.link path="/images/essentials/catalog-component-icons/simple-content.svg" />"> Click to edit Simple Content
    <@hst.manageContent documentTemplateQuery="new-content-document" parameterName="document" rootPath="content"/>
  </div>
</#if>