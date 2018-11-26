package pl.sugl.common.base;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;



public class QueryObjectConverter {

    private final Gson gson;


    public QueryObjectConverter(Gson gson) {
        this.gson = gson;
    }

    /**
     * Converts an object to serialized URI parameters.
     *
     * @param value The value to be converted (can be an object or collection).
     * @return Serialized URI parameters for use with {@literal @}{@link retrofit2.http.QueryMap}(encoded=true).
     */
    public Map<String, String> convert(Object value) {
        return convertTree(gson.toJsonTree(value));
    }

    /**
     * Converts the given JSON tree to serialized URI parameters. This is equivalent to helpers.js/encodeParams.
     *
     * @param tree The JSON tree (can be an object or array).
     * @return Serialized URI parameters for use with {@literal @}{@link retrofit2.http.QueryMap}(encoded=false).
     */
    public Map<String, String> convertTree(JsonElement tree) {
        ParamsMap params = new ParamsMap();
        if (tree.isJsonArray()) {
            int i = 0;
            for (JsonElement element : tree.getAsJsonArray()) {
                buildObjectParams(Integer.toString(i), element, params);
                i++;
            }
        } else if (tree.isJsonObject()) {
            for (Map.Entry<String, JsonElement> entry : tree.getAsJsonObject().entrySet()) {
                buildObjectParams(entry.getKey(), entry.getValue(), params);
            }
        } else if (!tree.isJsonNull()) {
            throw new IllegalArgumentException("Cannot convert " + tree.toString());
        }
        return params;
    }

    /**
     * Recursive helper method for {@link #convertTree(JsonElement)}. This is equivalent to helpers.js/buildObjectParams.
     *
     * @param prefix The prefix for the parameter names.
     * @param tree   The remaining JSON tree.
     * @param params The params object to write to.
     */
    private void buildObjectParams(String prefix, JsonElement tree, ParamsMap params) {
        if (tree.isJsonArray()) {
            int i = 0;
            for (JsonElement element : tree.getAsJsonArray()) {
                buildObjectParams(prefix + "[" + i + "]", element, params);
                i++;
            }
        } else if (tree.isJsonObject()) {
            for (Map.Entry<String, JsonElement> entry : tree.getAsJsonObject().entrySet()) {
                buildObjectParams(prefix + "[" + entry.getKey() + "]", entry.getValue(), params);
            }
        } else if (tree.isJsonPrimitive()) {
            params.put(prefix, tree.getAsJsonPrimitive().getAsString());
        }
    }

    /**
     * A map class that allows multiple entries per key.
     */
    private static class ParamsMap implements Map<String, String> {

        private final Set<Entry<String, String>> entries = new LinkedHashSet<>();

        @Override
        public int size() {
            return entries.size();
        }

        @Override
        public boolean isEmpty() {
            return entries.isEmpty();
        }

        @Override
        public boolean containsKey(Object key) {
            final Iterator<Entry<String, String>> iterator = entries.iterator();
            if (iterator != null) {
                while (iterator.hasNext()) {
                    final Entry<String, String> next = iterator.next();
                    final String key1 = next.getKey();
                    if (key.equals(key1)) {
                        return true;
                    }
                }
            }
            return false;
//            return entries.stream().anyMatch(entry -> entry.getKey().equals(key));
        }

        @Override
        public boolean containsValue(Object value) {
            final Iterator<Entry<String, String>> iterator = entries.iterator();
            if (iterator != null) {
                while (iterator.hasNext()) {
                    final Entry<String, String> next = iterator.next();
                    final String value1 = next.getValue();
                    if (value.equals(value1)){
                        return true;
                    }
                }
            }
            return false;


        }

        /**
         * @param key The key to look for.
         * @return The value of the FIRST matching entry or null if none matches.
         */
        @Override
        public String get(Object key) {
            final Iterator<Entry<String, String>> iterator = entries.iterator();
            while (iterator.hasNext()) {
                final Entry<String, String> next = iterator.next();
                if (key.equals(next.getKey())) {
                    return next.getValue();
                }
            }
            return null;

        }

        @Override
        public String put(String key, String value) {
            entries.add(new ParamEntry(key, value));
            return null;
        }

        @Override
        public String remove(Object key) {

            return null;
        }

        @Override
        public void putAll(@NonNull Map<? extends String, ? extends String> m) {
            for (Entry<? extends String, ? extends String> entry : m.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }

        @Override
        public void clear() {
            entries.clear();
        }

        @NonNull
        @Override
        public Set<String> keySet() {
            final LinkedHashSet<String> strings = new LinkedHashSet<>();

            final Iterator<Entry<String, String>> iterator = entries.iterator();
            while (iterator.hasNext()) {
                final Entry<String, String> next = iterator.next();
                strings.add(next.getKey());
            }
            return strings;

        }

        @NonNull
        @Override
        public Collection<String> values() {
            final ArrayList<String> strings = new ArrayList<>();

            final Iterator<Entry<String, String>> iterator = entries.iterator();
            while (iterator.hasNext()) {
                final Entry<String, String> next = iterator.next();
                strings.add(next.getValue());
            }
            return strings;
        }

        @NonNull
        @Override
        public Set<Entry<String, String>> entrySet() {
            return entries;
        }
    }

    private static class ParamEntry implements Map.Entry<String, String> {
        private final String key;
        private final String value;

        public ParamEntry(String key, String value) {
            this.key = Objects.requireNonNull(key);
            this.value = Objects.requireNonNull(value);
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public String setValue(String value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ParamEntry that = (ParamEntry) o;
            return key.equals(that.key) && value.equals(that.value);

        }

        @Override
        public int hashCode() {
            int result = key.hashCode();
            result = 31 * result + value.hashCode();
            return result;
        }
    }

}