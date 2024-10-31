package com.od.library_management_system.utils;

import com.od.library_management_system.enums.ResponseCode;
import com.od.library_management_system.exception.ErrorDto;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.od.library_management_system.utils.Constants.*;

@Data
public class ResponseUtility {

    private ResponseUtility() {
        throw new IllegalStateException(UTILITY_CLASS_INVOCATION);
    }

    public static ResponseEntity<Response> buildSuccessResponseEntity(Object obj, HttpStatus httpStatus,
                                                                      int resultCount,
                                                                      long totalCount,
                                                                      List<ErrorDto> errors) {
        ResponseData responseData = new ResponseData(obj);
        return new ResponseEntity<>(new Response(responseData, ResponseCode.SUCCESS.getStatus(),
                ResponseCode.SUCCESS.getMessage(), errors), httpStatus);
    }

    public static Map<String, Object> getParamMapWithTypeAndId(String typeValue, Object idValue){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(TYPE, typeValue);
        paramMap.put(ID, idValue);

        return paramMap;
    }
}
