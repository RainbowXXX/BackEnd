package site.rainbowx.backend.utils;

import com.alibaba.fastjson2.JSONObject;

public class ReturnVal {
    private final JSONObject jsonObject;

    public static class ReturnValFac {
        private Boolean flag;
        private String failureReason;
        private final JSONObject jsonObject;
        private JSONObject successObject;

        public ReturnValFac() {
            this.jsonObject = new JSONObject();
        }
        private ReturnValFac(JSONObject object) {
            this.jsonObject = object;
        }

        public ReturnValFac ok(boolean flag) {
            this.flag = flag;
            return this;
        }
        public ReturnValFac put(String key, Object value) {
            this.successObject.put(key, value);
            return this;
        }
        public ReturnValFac success(JSONObject object) {
            this.successObject = object;
            return this;
        }
        public ReturnValFac failure(String reason) {
            this.failureReason = reason;
            return this;
        }

        public ReturnVal build() {
            if(flag == null) {
                if((successObject == null) == (failureReason == null)) {
                    throw new RuntimeException("Success or failureReason will not be null or not null both simultaneously.");
                }
                flag = (successObject != null);
            }

            this.jsonObject.put("ok", this.flag);
            if(this.flag) {
                if(this.successObject != null) {
                    this.jsonObject.put("data", this.successObject);
                }
            }
            else {
                if(this.failureReason != null) {
                    this.jsonObject.put("reason", this.failureReason);
                }
                else {
                    throw new RuntimeException("When status is false, reason field is required.");
                }
            }

            return new ReturnVal(this.jsonObject);
        }
    }

    private ReturnVal(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JSONObject getVal() {
        return jsonObject;
    }

}
