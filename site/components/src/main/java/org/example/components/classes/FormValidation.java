/**
 * 
 */
package org.example.components.classes;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.example.components.classes.FormModel;
import org.hippoecm.hst.component.support.forms.FormMap;
import org.hippoecm.hst.component.support.forms.FormUtils;



/**
 * @author chris
 *
 */



public class FormValidation {
	private FormMap map = null;
	private ArrayList<String> requiredFields = null;

	public FormValidation(FormMap map, ArrayList<String> requiredFields) { 
		this.map = map;
		this.requiredFields = requiredFields;
	}
	
	public boolean validateSubmission() {
		ArrayList<String> emptyFields = checkForEmpty(); 
		if(emptyFields.size() > 0) {
			ArrayList<String> reqFields = checkForEmptyRequiredFields(emptyFields);
			// required fields are empty, submission is invalid
			if(reqFields.size() > 0) {
				return false;
			} else {
				// we have empty fields, but they are not required, so verify input is valid
				
			} 
		} 
		return true; 
	}
	
	
	private ArrayList<String> checkForEmpty() {
		ArrayList<String> emptyFields = new ArrayList<String>( );
		String[] fields = this.map.getFieldNames();
		
		for (String field : fields) {
			if(StringUtils.isBlank(this.map.getField(field).getValue())) {
				emptyFields.add(field);		          
	        }
		} 
		return emptyFields;
	}
	
	private ArrayList<String> checkForEmptyRequiredFields(ArrayList<String> emptyFields) {
		ArrayList<String> emptyRequiredFields = new ArrayList<String>( );
		ArrayList<String> reqFields = this.requiredFields;
		
		for (String field : emptyFields) {
			if(reqFields.contains(field)) {
				emptyRequiredFields.add(field);				
			} 
		} 
		return emptyRequiredFields;
	}
	
	private ArrayList<String> validateEntries(FormMap map) {
		String[] fields = map.getFieldNames();
		ArrayList<String> invalidEntries = new ArrayList<String>(); 
		HashMap<String, String> rules = getValidationRules();
		
		for (String field : fields) {
			String val = map.getField(field).getValue();
			String key = map.getField(field).getName(); 
			if(!val.matches(rules.get(key))) {
				System.out.println("field for  " + key + ", with value : " + val + ", does not match rule: " + rules.get(key));
				invalidEntries.add(key);
			}
		}
		return invalidEntries;
	}
	
	private HashMap<String, String> getValidationRules(){
		HashMap<String, String> rules = new HashMap<String, String>();
		rules.put("firstName", "/^[a-z ,.'-]+$/i");
		rules.put("lastName", "/^[a-z ,.'-]+$/i");
		rules.put("streetAddress", "/\\d{1,}(\\s{1}\\w{1,})(\\s{1}?\\w{1,})+)/g");
		rules.put("email","^[A-Z0-9+_.-]+@[A-Z0-9.-]+$");
	    
		
		
	    
		return null;
		
	}
	
	 

}
