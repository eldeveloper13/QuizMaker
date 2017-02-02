package eldeveloper13.quizmaker.db;

import com.activeandroid.serializer.TypeSerializer;
import com.google.gson.Gson;

import java.util.List;

public class StringArraySerializer extends TypeSerializer {

    @Override
    public Class<?> getDeserializedType() {
        return List.class;
    }

    @Override
    public Class<?> getSerializedType() {
        return String.class;
    }

    @Override
    public String serialize(Object data) {
        if (data == null) {
            return null;
        }
        Gson gson = new Gson();
        List<String> list = (List<String>) data;
        String json = gson.toJson(list);
        return json;
    }

    @Override
    public List deserialize(Object data) {
        if (data == null) {
            return null;
        }
        Gson gson = new Gson();
        String jsonString = (String) data;
        List<String> list = gson.fromJson(jsonString, List.class);
        return list;
    }
}
