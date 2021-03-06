import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Object.*;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

public class json_compare {
	private String json1;
	private String json2;
	
	public json_compare(String json1, String json2) {
		this.json1 = json1;
		this.json2 = json2;
	}
	
	public String compareJson(String json1, String json2) {

		  
		  Gson g = new Gson();
		  
		  Type mapType = new TypeToken<Map<String, Object>>(){}.getType();
//		  Map<String, String[]> firstMap = g.fromJson(json1, mapType);
		  JsonElement root1 = this.getJson(json1);
		  JsonObject jOne = root1.getAsJsonObject();
//		  Map<String, String[]> secondMap = g.fromJson(json2, mapType);
		  JsonElement root2 = this.getJson(json2);
		  JsonObject jTwo = root2.getAsJsonObject();
		  

		  
		  return mapCompare(jOne, jTwo, "root");
//		  String outcome = (Maps.difference(firstMap, secondMap)).toString();
		  
//		  System.out.println(firstMap.toString());
//
//		  System.out.println("\n\n\n" + secondMap.toString());
//		  
		  
	
		  //System.out.println(outcome);
//		  return outcome;

	}
	
	public String compareJson() {
		  
		 return compareJson(this.json1, this.json2);
		  
	}
	
	public Map<String, String[]> getMap (String json) {
		Gson g = new Gson();
		Type mapType = new TypeToken<Map<String, Object>>(){}.getType();
		return g.fromJson(json, mapType);	 
	}
	
	public JsonElement getJson(String jsonString) {
		return new JsonParser().parse(jsonString);	
	}
	
//	private String mapCompare (JsonObject left, JsonObject right, String root) {
//		String diffLog = "";
//		if (left.toString().equals(right.toString())) {
//			return diffLog;
//		} else {
//			Set<Map.Entry<String, JsonElement>> entriesLeft = left.entrySet();
//			Set<Map.Entry<String, JsonElement>> entriesRight = right.entrySet();
//			for (Map.Entry<String, JsonElement> entry: entriesLeft) {
//			   if (right.has(entry.getKey())) {
//				   String s = entry.toString();
//				   String t = right.).toString();
//				   if (entry.toString().equals(right.get(entry.getKey()).toString())) {
//					  
//					   System.out.println();
//				   }
//				   diffLog+= this.mapCompare(left.getAsJsonObject(entry.getKey()),  right.getAsJsonObject(entry.getKey()),  root + ">" + entry.getKey());
//			   } else {
//				   System.out.println("ASD");
//			   }
//			}
//			
	private String mapCompare (JsonObject left, JsonObject right, String root) {
			String acc = "";
			if (left.toString().equals(right.toString())) { return acc; }
			else {
				Set<Map.Entry<String, JsonElement>> entriesLeft = left.entrySet();
				for (Map.Entry<String, JsonElement> entry: entriesLeft) {
					if (right.has(entry.getKey())) {
						if (left.get(entry.getKey()).toString().equals(right.get(entry.getKey()).toString())) {
							return acc;
						} else if ((left.get(entry.getKey()).isJsonArray() && right.get(entry.getKey()).isJsonArray()) || (left.get(entry.getKey()).isJsonObject() && right.get(entry.getKey()).isJsonObject())) {
							acc += this.mapCompare((JsonObject)left.get(entry.getKey()), (JsonObject)right.get(entry.getKey()), root + ">" + entry.getKey());
						} else {
							return acc + "\n" + root + ">" + entry.getKey() + ">" + left.get(entry.getKey()) 
										+ "\n"  + root + ">" + entry.getKey() + ">" + right.get(entry.getKey()); 
						}
					} else {
						return acc + "\n" + root + ">" + entry.getKey() + "exists only in the left JSON";
					}
				}
			}
			return acc;
			
	}
	
}
