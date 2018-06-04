import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class json_get {

	public static void main (String[] args) {
		String json1 = "", json2 = "";
		
		json1 = readJsonFile(args[0]);
		json2 = readJsonFile(args[1]);
		
		json1 = stripWhiteSpace(json1);
		json2 = stripWhiteSpace(json2);
		
		
		String[] temp = removePretext(json1, json2);
		json1 = temp[0];
		json2 = temp[1];

		
		json_compare jscomp = new json_compare(json1, json2);
		jscomp.compareJson();
		JsonElement root1 = jscomp.getJson(json1);
		JsonObject obj1 = root1.getAsJsonObject();
		
		
		Map<String, String[]> jsMapOne = jscomp.getMap(json1);
				
		System.out.println(jsMapOne.toString());
		
		
		
		/*
		
		if (!(jscomp.compareJson().equals("equal"))) {
			String j1 = json1.toString();
			String j2 = json2.toString();
			if (j1.length() > j2.length()) {
				String jT = j2;
				j2 = j1;
				j1 = jT;
			}

			for (int i = 0; i < j1.length(); i++) {
				if (j1.charAt(i) != j2.charAt(i)) {
					if ((j1.length() - i) < 30) {
						if (i > 20) {
							System.out.println("JSON 1: " + j1.substring(i - 20, i) + "  >  " + j1.substring(i));
						} else {
							System.out.println("JSON 1: " + j1.substring(0,  i) + "  >  " + j1.substring(i));
						}
					} else {
						if (i > 20) {
							System.out.println("JSON 1: " + j1.substring(i - 20,  i) + "  >  " + j1.substring(i, i+15));
						} else {
							System.out.println("JSON 1: " + j1.substring(0,  i) + "  >  " + j1.substring(i, i+15));
						}
					}
					
					if ((j2.length() - i) < 30) {
						if (i > 20) {
							System.out.println("JSON 2: " + j2.substring(i - 20, i) + "  >  " + j2.substring(i));
						} else {
							System.out.println("JSON 2: " + j2.substring(0,  i) + "  >  " + j2.substring(i));
						}
					} else {
						if (i > 20) {
							System.out.println("JSON 2: " + j2.substring(i - 20,  i) + "  >  " + j2.substring(i, i+15));
						} else {
							System.out.println("JSON 2: " + j2.substring(0,  i) + "  >  " + j2.substring(i, i+15));
						}
					}
					break;
				}
			}
			
		} else {
			System.out.println(jscomp.compareJson());
		}
		//System.out.println);

		*/
			
	/*
	 * STUB FOR API CALL 
	 * 
	 * 
	 */
	/* HERE WE WOULD MAKE A NICE API CALL BUT CERTS ARE DUMB SO BELOW IS CODE THAT DOES NOT WORK EXACTLY

		  
  		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
		  .url("https://dev-605887-admin.oktapreview.com/api/v1/meta/schemas/user/default")
		  .get()
		  .addHeader("Accept", "application/json")
		  .addHeader("Content-Type", "application/json")
		  .addHeader("Authorization", "SSWS 00oVTE27UzzkZDbMX428TlvNOyciz-E1S_JqpdYoqr")
		  .addHeader("Cache-Control", "no-cache")
		  .addHeader("Postman-Token", "6ff00214-c54f-4011-afea-6a6063b80846")
		  .build();

		try {
			Response response = client.newCall(request).execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  
		 */
	}
	
	private static String stripWhiteSpace(String s) {
		s = s.replaceAll("\\s", "");
		return s;
	}
	
	private static String readJsonFile(String s) {
		try {
			File file = new File(s);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
			}
			fileReader.close();
			return stringBuffer.toString();
		} catch (IOException e) {
			return e.getMessage();
		}
	}
	
	//function gets rid of meta data at top of json that will never be the same as long as the field exists in both
	private static String[] removePretext(String left, String right) {
		String[] keywords = new String[]{"id", "description", "lastUpdated", "created"};
		String newLeft = left;
		String newRight = right;
		for (int i = 0; i < keywords.length; i++) {
			if (left.contains(keywords[i]) && right.contains(keywords[i])) {
				newLeft = newLeft.substring(0, newLeft.indexOf(keywords[i]) - 1) + (newLeft.substring(newLeft.indexOf(",", newLeft.indexOf(keywords[i])) + 1));
				newRight = newRight.substring(0, newRight.indexOf(keywords[i]) - 1) + (newRight.substring(newRight.indexOf(",", newRight.indexOf(keywords[i])) + 1));
			}
		}
	String[] retVal = new String[2];
	retVal[0] = newLeft;
	retVal[1] = newRight;
	return retVal;
	}
	
	private static String filter(String json, String id) {
		String rework = "{" + json.substring(json.indexOf(id) -1);
		int nextOpen = rework.indexOf("{", 2);
		int nextClose = rework.indexOf("}");
		if (nextClose < nextOpen) {
			return rework.substring(0, nextOpen) + "}";
		} else {
			return "hello";
		}
		
	}
}
