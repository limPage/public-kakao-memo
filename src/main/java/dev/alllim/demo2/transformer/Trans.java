package dev.alllim.demo2.transformer;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Trans {


	public static String postText(String text){
		 String default_msg_param = ""
				+ "template_object={\n"
				+ "        \"object_type\": \"text\",\n"
				+ "        \"text\": \" " + text+ " \",\n"
				+ "            \"link\": {\n"
				+ "                \"web_url\": \"http://daum.net\",\n"
				+ "                \"mobile_web_url\": \"http://dev.kakao.com\"\n"
				+ "            },\n"
				+ "        \"button_title\": \"카카오 메모 - 웹에서 보냄\"\n"
				+ "    }"
				+ "";

		return default_msg_param;
	}


	public static String token(String rtn, JsonParser parser) {
        JsonElement element = parser.parse(rtn);       
        return element.getAsJsonObject().get("access_token").getAsString();

    }
    
    
}
