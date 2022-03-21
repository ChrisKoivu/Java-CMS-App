<#include "../../include/imports.ftl">
 
<#-- @ftlvariable name="document" type="org.example.beans.Banner" -->
<#if document??>
   <div class="has-edit-button">
      <@hst.manageContent hippobean=document />
      <div class="row">
         <div class="medium-12 columns">
           <h4>${document.formTitle} </h4>
         </div>
      </div>
    <@hst.actionURL var="actionLink"/>
   
    <form action="${actionLink}" method="post">
        <div class="row">  
              <#if document.formFields.firstNameVisible>
                <div class="medium-6 large-6 columns">
                  <label>First Name:
                    <input name="firstName" type="text" placeholder="First Name" value="" <#if document.requiredFields.firstNameRequired>required</#if>>
                  </label>
                </div>
               </#if>
              <#if document.formFields.lastNameVisible>
                <div class="medium-6 large-6  columns">
                  <label>Last Name:
                    <input name="lastName" type="text" placeholder="Last Name" value="" <#if document.requiredFields.lastNameRequired>required</#if>>
                  </label>
                </div> 
              </#if> 
          </div>

          <div class="row">
            <#if document.formFields.phoneVisible>
              <div class="medium-6 large-6 columns">
                <label>Phone:
                  <input name="phone" type="tel" placeholder="Phone" value="" <#if document.requiredFields.phoneRequired>required</#if>>
                </label>
              </div>
            </#if>
            <#if document.formFields.emailVisible>
            <div class="medium-6  large-6 columns">
              <label>Email:
                 <input name="email" type="email" placeholder="Email" value="" <#if document.requiredFields.emailRequired>required</#if>>
              </label>
            </div>
            </#if>
          </div>
          <#if document.formFields.streetAddressVisible>
            <div  class="row">
              <div class="medium-12 columns ">
                <label>Street Address:
                  <input name="streetAddress" type="text" placeholder="Street Address" value="" <#if document.requiredFields.streetAddressRequired>required</#if>>
                </label>
              </div> 
            </div>
          </#if>

          <div class="row">
            <#if document.formFields.cityVisible>
              <div class="medium-6 large-6 columns">
                <label>City:
                  <input name="city" type="text" placeholder="City" value="" <#if document.requiredFields.cityRequired>required</#if>>
                </label>
              </div>
            </#if>
            <#if document.formFields.stateVisible>
              <div class="medium-3 large-3 columns">
                <label>State:
                  <input name="state" type="text" placeholder="State" value="" <#if document.requiredFields.stateRequired>required</#if>>
                </label>
              </div>
            </#if>
            <#if document.formFields.zipVisible>
              <div class="medium-3 large-3 columns">
                <label>Zipcode:
                  <input name="zipcode" type="text" placeholder="Zipcode" value="" <#if document.requiredFields.zipRequired>required</#if>>
                </label>
              </div>
            </#if>
          </div>

          <div class="row" >  
           <div class="medium-12 columns ">
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