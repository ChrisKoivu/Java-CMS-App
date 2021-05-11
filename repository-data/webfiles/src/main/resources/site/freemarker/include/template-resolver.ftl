<#--  functions and macros to handle the resolution and rendering of freemarker templates.
handles many use cases when a template may need to be imported into another template
and when you need to do some custom rendering  -->

<#--  handle printing of headings
how to use:  <@printHeading level="h2" id="" class="" headingSize="hl-small" heading="Some Fancy Heading">  -->
<#macro printHeading id class heading headingSize="" level="h3">
   <#--  handle null and empty cases for level argument  -->
   <#if level?trim?length==0><#assign level="h3"></#if>
   <#if level?has_content><#assign level=level><#else><#assign level="h3"></#if>
   <#--  allow css classes to be nullable  -->
   <#if class?trim?has_content><#assign cssClass = class><#else><#assign cssClass=""></#if>
   <#if headingSize?trim?has_content><#assign cssClass = headingSize + " " + cssClass></#if>

   <#switch level?trim>
      <#case "h1">
        <h1 <#if cssClass?trim?has_content>class="${cssClass?trim}" </#if><#if id?trim?has_content>id="${id}"</#if>>
          ${heading}
        </h1>
        <#break>
      <#case "h2">
        <h2 <#if cssClass?trim?has_content>class="${cssClass?trim}" </#if><#if id?trim?has_content>id="${id}"</#if>>
          ${heading}
        </h2>
        <#break>
      <#case "h3">
        <h3 <#if cssClass?trim?has_content>class="${cssClass?trim}" </#if><#if id?trim?has_content>id="${id}"</#if>>
          ${heading}
        </h3>
        <#break>
      <#case "h4">
        <h4 <#if cssClass?trim?has_content>class="${cssClass?trim}" </#if><#if id?trim?has_content>id="${id}"</#if>>
          ${heading}
        </h4>
        <#break>
      <#case "h5">
        <h5 <#if cssClass?trim?has_content>class="${cssClass?trim}" </#if><#if id?trim?has_content>id="${id}"</#if>>
          ${heading}
        </h5>     
        <#break>
      <#case "h1">
        <h6 <#if cssClass?trim?has_content>class="${cssClass?trim}" </#if><#if id?trim?has_content>id="${id}"</#if>>
          ${heading}
        </h6>
        <#break>
      <#default>
        <p <#if cssClass?trim?has_content>class="${cssClass?trim}" </#if> <#if id?trim?has_content>id="${id}"</#if>>
           ${heading}
        </p>
   </#switch>
</#macro>

<#function getColSizes colSizeSelected="4-4-4">
   <#local sizes = []>
   <#list colSizeSelected?split("-") as x>
      <#assign classes="small-12 medium-${x} large-${x}">
      <#--  trick to append to a freemarker array for older versions of freemarker  -->
      <#assign sizes = sizes + [classes]>
   </#list>
   <#return sizes>
</#function>

<#function getTemplateName selectedTemplate="panel">
  <#assign trimTemplateName = selectedTemplate?trim>
  <#assign name = trimTemplateName?lower_case?replace(" ","-") + ".ftl">
  <#return name>
</#function>