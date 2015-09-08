package com.snapdeal.gohack.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.abego.treelayout.internal.util.java.lang.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

import com.google.gson.JsonObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Controller
@EnableAutoConfiguration
public class SampleController {
	
	//private static final String SERVER_ROOT_URI = "http://localhost:7474/db/data/";
	
	private static final String SERVER_ROOT_URI = "http://127.0.0.1:7474/db/data/";
    
	@RequestMapping(value="/submitQuery", method=RequestMethod.POST,headers = 
			"content-type=application/json" ,
			produces={"application/json"}, consumes={"text/xml","application/json"})
	public ResponseEntity<String> executeQuery(@RequestBody QueryRequest query){
		System.out.println(query);
		return new ResponseEntity<String>(sendTransactionalCypherQuery(query.getQuery()), HttpStatus.OK); 
	}
	
	@RequestMapping(value="/searchSeller", method=RequestMethod.POST,headers = 
			"content-type=application/json" ,
			produces={"application/json"}, consumes={"text/xml","application/json"})
	public ResponseEntity<Output> searchSeller(@RequestBody SellerRequest query) throws JSONException{
		if(StringUtils.isNotBlank(query.getProduct())){
			//execute product call
			String bb = "MATCH (sellers:SELLER)-[:SELLS_PRODUCT]->(prod:PRODUCT{name:'"+query.getProduct()+"'}) return distinct sellers.name,sellers.state,sellers.fbId;";
			bb = sendTransactionalCypherQuery(bb);
			
			return new ResponseEntity<Output>(myResponse(bb), HttpStatus.OK);
		}
		if(StringUtils.isNotBlank(query.getSubCat())){
			//execute product call
			String bb = "MATCH (sellers:SELLER)-[:SELLS_SUB_CAT]->(prod:SUB_CATEGORY{name:'"+query.getSubCat()+"'}) return distinct sellers.name,sellers.state,sellers.fbId;";
			bb = sendTransactionalCypherQuery(bb);
			return new ResponseEntity<Output>(myResponse(bb), HttpStatus.OK);
		}
		if(StringUtils.isNotBlank(query.getCat())){
			String bb = "MATCH (sellers:SELLER)-[:SELLS_CAT]->(prod:CATEGORY{name:'"+query.getCat()+"'}) return distinct sellers.name,sellers.state,sellers.fbId;";
			bb = sendTransactionalCypherQuery(bb);
			return new ResponseEntity<Output>(myResponse(bb), HttpStatus.OK);
		}
		return null;
	}
	
	private Output myResponse(String bb) throws JSONException{
		
		HashMap<String, List<String>> stateMap = new HashMap<String, List<String>>();
		
		JSONObject obj = new JSONObject(bb);
		JSONArray myArray = obj.getJSONArray("results");
		JSONArray dataArray = myArray.getJSONObject(0).getJSONArray("data");
		
		for(int i=0; i<dataArray.length(); i++){
			JSONArray array = dataArray.getJSONObject(i).getJSONArray("row");
			String name = array.get(0).toString();
			String state = array.get(1).toString();
			String fbId = array.get(2).toString();
			
			
			List<String> out = stateMap.get(state);
			if(out == null){
				out = new ArrayList<String>();
			}
			out.add(name);
			stateMap.put(state, out);
			
			
			System.out.println();
		}
		System.out.println(stateMap.toString());
		
		Output outputMain = new Output();
		outputMain.setName("flare");
		List<Output> outputListMain = new ArrayList<Output>();
		outputMain.setChildren(outputListMain);
		
		Output State = new Output();
		State.setName("state");
		List<Output> stateList  = new ArrayList<Output>();
		for(String str: stateMap.keySet()){
			Output MP = new Output();
			MP.setName(str);
			
			List<String> xyz = stateMap.get(str);
			List<Output> hello = new ArrayList<Output>();
			for(String x : xyz){
				Output o = new Output();
				o.setName(x);
				//o.setSize(123);
				
				List<Output> oChild = new ArrayList<Output>();
				Output oChild1 = new Output();
				oChild1.setSize(2345);
				oChild1.setName(x);
				oChild.add(oChild1);
				
				o.setChildren(oChild);
				
				
				hello.add(o);
			}
			MP.setChildren(hello);
			stateList.add(MP);
		}
		State.setChildren(stateList);
		
		outputListMain.add(State);
		
		
		Output college = new Output();
		college.setName("college");
		
		List<Output> collegeList = new ArrayList<Output>();
		Output o1 = new Output();
		o1.setName("Kapil Minda");
		//o1.setSize(123);
		addSomething(o1);
		
		Output o2 = new Output();
		o2.setName("Vikas Gupta");
		//o2.setSize(123);
		addSomething(o2);
		
		Output o3 = new Output();
		o3.setName("Saloni Jain");
		//o3.setSize(123);
		addSomething(o3);
		
		collegeList.add(o1);
		collegeList.add(o2);
		collegeList.add(o3);
		
		college.setChildren(collegeList);
		
		outputListMain.add(college);
		
		System.out.println(outputMain.toString());
		
		return outputMain;
	}
	
	private void addSomething(Output o1){
		String name = o1.getName();
		
		List<Output> out = new ArrayList<Output>();
		Output o = new Output();
		o.setName(name);
		o.setSize(2345);
		
		out.add(o);
		
		o1.setChildren(out);
		
	}
	
/*	@RequestMapping(value="{}/registerFB/", method=RequestMethod.POST,headers = 
			"content-type=application/json" ,
			produces={"application/json"}, consumes={"text/xml","application/json"})
	public ResponseEntity<String> registerFBFriends(@RequestBody RegisterFBRequest request){
		return null;
	}*/
	
    private String sendTransactionalCypherQuery(String query) {
    	
    	//query = "MATCH (n) WHERE has(n.name) RETURN n.name AS name";
        final String txUri = SERVER_ROOT_URI + "transaction/commit"; 
        WebResource resource = Client.create().resource( txUri );

        String payload = "{\"statements\" : [ {\"resultDataContents\" : [ \"row\" ],\"statement\" : \"" +query + "\" } ]}";
        ClientResponse response = resource
                .accept( MediaType.APPLICATION_JSON )
                .type( MediaType.APPLICATION_JSON )
                .entity( payload )
                .post( ClientResponse.class );
        
        String res = response.getEntity(String.class);
        
        
        return res;
    }

}
