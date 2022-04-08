<!doctype html>
<#include "../include/imports.ftl">
<#include "../include/template-resolver.ftl">
<html lang="en">
  <head>
    <meta charset="utf-8"/>
    <link rel="stylesheet" href="<@hst.webfile  path="/css/foundation.css"/>" type="text/css"/>
    <link rel="stylesheet" href="<@hst.webfile  path="/css/app.css"/>" type="text/css"/>
    <#if hstRequest.requestContext.channelManagerPreviewRequest>
      <link rel="stylesheet" href="<@hst.webfile  path="/css/cms-request.css"/>" type="text/css"/>  
    </#if>
    <#--  lets not put scripts in the head ok?  -->
    <@hst.headContributions categoryExcludes="htmlBodyEnd, scripts" xhtml=true/>
  </head>
  <body>
    <div class="container">
      <div class="row">
        <div class="medium-6  medium-offset-3">
          <@hst.include ref="top"/>
        </div>
      </div>
      <div class="row top-3x">
        <div class="medium-6 medium-offset-3">
          <@hst.include ref="menu"/>
        </div>
      </div>
      <div class="row small-12 large-8 columns">
        <@hst.include ref="main"/>


      </div>
      <div class="row">
        <@hst.include ref="footer"/>
      </div>
    </div>

    <@hst.headContribution category="htmlBodyEnd">
        <script type="text/javascript" src="<@hst.webfile path="/js/jquery-3.5.1.min.js"/>"></script>
    </@hst.headContribution>

    <@hst.headContribution category="htmlBodyEnd">
        <script type="text/javascript" src="<@hst.webfile path="/js/vendor.js"/>"></script>
    </@hst.headContribution>

     <@hst.headContribution category="htmlBodyEnd">
        <script type="text/javascript" src="<@hst.webfile path="/js/foundation.js"/>"></script>
    </@hst.headContribution>


    <@hst.headContributions categoryIncludes="htmlBodyEnd, scripts" xhtml=true/>
     <#if hstRequest.requestContext.channelManagerPreviewRequest>
    
        <script type="text/javascript" src="<@hst.webfile path="/js/cms-request.js"/>"></script>
   
    </#if>
//<#--  
  //   <script>
  //      jQuery(document).ready(function( $ ) {
       //   $.get("http://localhost:8080/site/api/banner", function(data, status){
                
        //       console.log(data);
      //    });
     //   });
   // </script>  -->
 

  </body>
</html>