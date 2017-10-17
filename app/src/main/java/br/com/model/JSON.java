package br.com.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Fernando on 22/10/2016.
 */
public class JSON {
    //CONVERTE OBJETOS EM JSON

    public static JSONObject toJson(Object obj) throws JSONException {
        return new JSONObject(new Gson().toJson(obj));
    }

    public static JsonElement toJsonElement(Object obj) {
        return new Gson().toJsonTree(obj);
    }

    public static String toString(Object obj) throws JSONException {
        return toJson(obj).toString();
    }

    public static <T> T from(String jsonStr, Class<T> classe) {
        return new Gson().fromJson(jsonStr, classe);
    }

    public static <T> T from(JsonElement element, Class<T> classe) {
        return new Gson().fromJson(element, classe);
    }

}
