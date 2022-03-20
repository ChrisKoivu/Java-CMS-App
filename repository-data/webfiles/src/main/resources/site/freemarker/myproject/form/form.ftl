<#include "../../include/imports.ftl">

<#-- @ftlvariable name="document" type="org.example.beans.Banner" -->
<#if document??>
   <div class="has-edit-button">
      <@hst.manageContent hippobean=document />
      <h4>${document.formTitle} </h4>
      <#-- all the available form fields that can be rendered
          TODO: add checkboxes for visibility, and a required fields parameter in the document 
      -->
      <@hst.actionURL var="actionLink"/>
      <form action="${actionLink}" method="post">
        <div class="grid-container">
          <div class="grid-x grid-padding-x">
            <div class="medium-6 cell">
              <label>First Name:
                <input name="firstName" type="text" placeholder="First Name">
              </label>
            </div>
            <div class="medium-6 cell">
              <label>Last Name:
                 <input name="lastName" type="text" placeholder="Last Name">
              </label>
            </div>
          </div>
          <div class="grid-x grid-padding-x">
            <div class="medium-6 cell">
              <label>Phone:
                <input name="phone" type="tel" placeholder="Phone">
              </label>
            </div>
            <div class="medium-6 cell">
              <label>Email:
                 <input name="email" type="email" placeholder="Email">
              </label>
            </div>
          </div>
          <div class="grid-x grid-padding-x">
            <div class="medium-12 cell">
              <label>Street Address:
                <input name="streetAddress" type="text" placeholder="Street Address">
              </label>
            </div>
           
          </div>
          <div class="grid-x grid-padding-x">
            <div class="medium-6 cell">
              <label>City:
                <input name="city" type="text" placeholder="City">
              </label>
            </div>
            <div class="medium-3 cell">
              <label>State:
                 <input name="state" type="text" placeholder="State">
              </label>
            </div>
             <div class="medium-3 cell">
              <label>Zipcode:
                 <input name="zipcode" type="text" placeholder="Zipcode">
              </label>
            </div>
          </div>
          <div class="input-group"> 
            <div class="input-group-button">
              <input type="submit" class="button" value="Submit" style="padding:12px;">
            </div>
         </div>
        </div>
     </form>  
  </div>

   
<#elseif editMode>
  <div class="has-edit-button">
    <img src="<@hst.link path="/images/essentials/catalog-component-icons/simple-content.svg" />"> Click to select your form
    <@hst.manageContent documentTemplateQuery="new-content-document" parameterName="document" rootPath="content"/>
  </div>
</#if>