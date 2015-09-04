package com.snapdeal.gohack.test;

import javax.ws.rs.core.MediaType;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Controller
@EnableAutoConfiguration
public class SampleController {
	
	private static final String SERVER_ROOT_URI = "http://localhost:7474/db/data/";
    
	@RequestMapping(value="/submitQuery", method=RequestMethod.POST,headers = 
			"content-type=application/json" ,
			produces={"application/json"}, consumes={"text/xml","application/json"})
	public ResponseEntity<String> executeQuery(@RequestBody QueryRequest query){
		System.out.println(query);
		return new ResponseEntity<String>(sendTransactionalCypherQuery(query.getQuery()), HttpStatus.OK); 
	}
	
    private String sendTransactionalCypherQuery(String query) {
    	
    	//query = "MATCH (n) WHERE has(n.name) RETURN n.name AS name";
        final String txUri = SERVER_ROOT_URI + "transaction/commit"; 
        WebResource resource = Client.create().resource( txUri );

        String payload = "{\"statements\" : [ {\"statement\" : \"" +query + "\"} ]}";
        ClientResponse response = resource
                .accept( MediaType.APPLICATION_JSON )
                .type( MediaType.APPLICATION_JSON )
                .entity( payload )
                .post( ClientResponse.class );
        
        String res = response.getEntity(String.class);
        
        
        return res;
    }

}
