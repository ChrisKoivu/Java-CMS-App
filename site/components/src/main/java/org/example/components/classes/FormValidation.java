/**
 * 
 */
package org.example.components.classes;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
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
	
	/**
	 * validates the inputed data entries are not invalid
	 * are valid
	 * @param map form map
	 * @return arraylist of invalid entries or an empty list if all entries valid
	 */
	public ArrayList<String> validateSubmission(FormMap map) {
		System.out.println("validate submission method");
		ArrayList<String> emptyFields = checkForEmpty(map); 
		ArrayList<String> invalidFields = new ArrayList<String>(); 
		// check for empty fields
		if(emptyFields.size() > 0) {
			ArrayList<String> reqFields = checkForEmptyRequiredFields(emptyFields);
			// required fields are empty, submission is invalid
			if(reqFields.size() > 0) {
				return reqFields;
			} 
		} 
		// check non blank field values. if blank and required, validation check would 
		// have failed at this point
		String[] fields = map.getFieldNames();
		for (String field : fields) {
			String fieldValue = map.getField(field).getValue();
			if(StringUtils.isNotBlank(fieldValue)) {
				if(!isValueValid(field, fieldValue))    {
					invalidFields.add(field);
				}
	        }
		} 
		return invalidFields; 
	}
	
	private boolean isValueValid(String fieldName, String fieldValue) {
		HashMap<String, String> rules = getValidationRules();
		if(fieldValue.matches(rules.get(fieldName))) {
			return true;
		}
		return false;
		
	}
	private ArrayList<String> checkForEmpty(FormMap map) {
		ArrayList<String> emptyFields = new ArrayList<String>( );
		String[] fields = map.getFieldNames();
		
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
			// validate all non blank fields for valid entry
			if(!val.matches(rules.get(key)) && StringUtils.isNotBlank(key)) {
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
		rules.put("phone","^[0-9]{10}");
		rules.put("email","^[A-Z0-9+_.-]+@[A-Z0-9.-]+$");
		rules.put("streetAddress", "[A-Za-z0-9]");
		rules.put("city", "[A-Za-z]");
		rules.put("state", "[A-Za-z]");
		rules.put("zipcode", "\\s*\\d{5}\\s*");
		return rules; 
	}
	
	 

}
