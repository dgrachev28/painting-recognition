package com.company.crawler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.util.Map;

public class RestClient {

    public String get(String url) {
        try {
            String result = Request.Get(url)
                    .connectTimeout(1000)
                    .socketTimeout(1000)
                    .execute().returnContent().asString();
            System.out.println(result);
            Map<String, Object> map = jsonToMap(result);
            return result;
        } catch (IOException e) {
            // TODO: добавить логгер
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> jsonToMap(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Map.class);
    }

}
