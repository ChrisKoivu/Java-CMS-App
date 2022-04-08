/**
 * 
 */
package org.example.rest;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.example.components.classes.BasicDocumentList;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

 

/**
 * @author chris
 *
 */
public class RestServices {
	private String resourceUrl = null;
    private String basicAuthUser = null;
    private String basicAuthPassword = null;
    private boolean useBaseAuth = false;
    private  HttpURLConnection connection = null;
    private String encodedCredentials = null;
	
    private static Logger log = LoggerFactory.getLogger(RestServices.class);
	
	
    public void RestServices(String resourceUrl, String basicAuthUser, String basicAuthPassword) {
    	this.resourceUrl = resourceUrl;
        this.basicAuthUser = basicAuthUser;
        this.basicAuthPassword = basicAuthPassword;
        connect();
    }
    
    private void connect() { 
    	if(StringUtils.isNotBlank(basicAuthUser) && StringUtils.isNotBlank(basicAuthPassword)) {
    		String auth = this.basicAuthUser + ":" + this.basicAuthPassword; 
    		// encode without padding
    		encodedCredentials = Base64.getEncoder().withoutPadding().encodeToString(auth.getBytes());
            this.useBaseAuth = true; 
    	}
    	try {
			URL url = new URL(this.resourceUrl);
			this.connection = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    } 
    
    private boolean isConnected() {
    	return this.connection != null;
    }
    private HttpURLConnection setHeaders(String action) {
		return setHeaders(action,  this.useBaseAuth);
    }
    
    private HttpURLConnection setHeaders(String action, Boolean useBasicAuth) {
    	try {
    		action = StringUtils.upperCase(action);
			this.connection.setRequestMethod(action);
			this.connection.setRequestProperty("Accept", "application/json");
			if(useBasicAuth) {
				this.connection.setRequestProperty("Authorization", this.encodedCredentials);
			}
			return this.connection;
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
    public String getData() {
    	try {
    		if(!isConnected()) {
    			throw new IOException("could not connect to service");
    		}
    		HttpURLConnection conn = setHeaders("GET", this.useBaseAuth);
    		if(conn == null) {
    			throw new IllegalArgumentException("could not complete transaction");
    		}
    		int responseCode = conn.getResponseCode();
    		if(responseCode >= 200 && responseCode < 300) {
    			BufferedReader in = new BufferedReader(
    					new InputStreamReader(conn.getInputStream()));
    			String inputLine;
    			StringBuffer response = new StringBuffer();
    			while((inputLine = in.readLine()) != null) {
    				response.append(inputLine);
    			}
    			in.close();
    			conn = null;
    			return response.toString();    			
    		}else if(responseCode >= 400 && responseCode < 500) {
    			log.error(responseCode + ": Bad request");
    		}else if(responseCode >= 500) {
    			log.error(responseCode + ": System is not available");
    		} 
    	}catch (IOException e) {
    		log.error(e.getMessage());
    	} catch (IllegalArgumentException e) {
    		log.error(e.getMessage());
    	} finally {
    		disconnect();
    	}
    	return null;
    }
    
    public void postData() {
    	// TODO: Method stub
    	try {
    		if(!isConnected()) {
    			throw new IOException("could not connect to service");
    		}
    		
    		
    	}catch (IOException e) {
    		log.error(e.getMessage());
    	} catch (IllegalArgumentException e) {
    		
    	} finally {
    		disconnect();
    	} 
    }
    
    private void disconnect() {
    	this.connection = null;
    }
	
	
}
