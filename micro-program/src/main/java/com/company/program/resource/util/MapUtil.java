package com.company.program.resource.util;

import java.util.Map;
import java.util.Set;

public class MapUtil {
    public static Map<String, Object> nullToEmpty(Map<String, Object> map) {
        Set<String> set = map.keySet();
        if(set != null && !set.isEmpty()) {
            for(String key : set) {
                if(map.get(key) == null) { map.put(key, ""); }
            }
        }
        return map;
    }
}

