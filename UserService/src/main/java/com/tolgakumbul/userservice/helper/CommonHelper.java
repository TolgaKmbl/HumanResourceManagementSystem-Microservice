package com.tolgakumbul.userservice.helper;

import com.tolgakumbul.userservice.constants.Constants;
import com.tolgakumbul.userservice.model.common.CommonResponseDTO;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

public class CommonHelper {

    public static CommonResponseDTO getCommonResponseDTO(Object obj) {
        boolean isList = obj instanceof List;
        boolean isEmpty = isList ? CollectionUtils.isEmpty((List<?>) obj) : ObjectUtils.isEmpty(obj);

        int statusCode = isEmpty ? Constants.STATUS_INTERNAL_ERROR : Constants.STATUS_OK;
        String message = isEmpty ? (isList ? "List is empty" : "Could not fetch data!") : Constants.OK;

        return new CommonResponseDTO(statusCode, message);
    }
}
