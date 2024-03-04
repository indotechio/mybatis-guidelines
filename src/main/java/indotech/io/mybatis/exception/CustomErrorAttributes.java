package indotech.io.mybatis.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
        errorAttributes.put("status", false);
        errorAttributes.put("message", errorAttributes.get("error"));
        errorAttributes.remove("locale");
        errorAttributes.remove("error");
        errorAttributes.remove("cause");
        errorAttributes.remove("trace");
        errorAttributes.remove("timestamp");
        errorAttributes.remove("path");

        return errorAttributes;
    }
}
