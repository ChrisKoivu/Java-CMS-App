<#include "../../include/imports.ftl">

 <#if cparam.getResidualParameterValues()?has_content>
    <#assign dparams = cparam.getResidualParameterValues()>
 </#if>
 
 <#if collection?has_content> 
    <#-- if content is not a single document but a collection of documents, 
    display them, but the collection of documents is not directly editable 
    in the cms -->

    <#list collection as item>
        ${item.getTitle()}
    </#list>

<#else>
      <#-- @ftlvariable name="document" type="org.example.beans.Banner" -->
      <#if editMode>
         <div style="background:yellow;"><p>This template allows you to edit a selected document in the cms</p></div>
      </#if>
      <#if contentNotFound?has_content>
        <div class="row">
           <div class="small-12 columns">
              <p>${contentNotFound}</p>
           </div>
        </div> 
      </#if>


      <#if document??>
         <div class="has-edit-button">
             <@hst.manageContent hippobean=document />
            
             <h4>${document.title} </h4> 
             <p>${document.introduction}</p>
            
         </div>

        
      <#elseif editMode>  
        <div class="has-edit-button">
          <img src="<@hst.link path="/images/essentials/catalog-component-icons/simple-content.svg" />"> Click to edit Simple Content
          <@hst.manageContent documentTemplateQuery="new-content-document" parameterName="document" rootPath="content"/>
        </div>
      </#if> 
</#if>
   <script>
      window.addEventListener("load", function(){ 
         (function ($){
            var url='<@hst.resourceURL />'.replace("amp;","");
            jQuery(document).ready(function($) {
               $.post(url, {action:"query"}, function(data){ 
                     console.log(data);
               });
            }); 
         })(jQuery);
      }, false); 
   </script>   