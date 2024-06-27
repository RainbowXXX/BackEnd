package site.rainbowx.backend.utils;

import com.alibaba.fastjson2.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParameterUtils {
    private static final Logger logger = LoggerFactory.getLogger(ParameterUtils.class);

    /**
     * check the return val is valid.
     * @param ret return val to be checked.
     */
    public static void ReturnChecker(Object ret) {
        if(ret == null) throw new RuntimeException("Return value cannot be null");
        if(ret instanceof JSONObject retVal) {
            if(!retVal.containsKey("ok")) throw new RuntimeException("Return status cannot be null");
            if(!(retVal.get("ok") instanceof Boolean)) throw new RuntimeException("Return status must be boolean");
            if(!((boolean) retVal.get("ok"))) {
                if(!retVal.containsKey("reason")) throw new RuntimeException("When status is false, reason must be marked");
            }
        }
        else throw new RuntimeException("Return type must be JSONObject");
    }
}
