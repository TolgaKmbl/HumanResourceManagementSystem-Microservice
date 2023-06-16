package com.tolgakumbul.customcontext;

import io.grpc.Metadata;
import io.grpc.Metadata.Key;
import org.apache.commons.lang3.StringUtils;

public class CustomSecurityContextMapper {

    public static Metadata toMetadata(CustomSecurityContext context, Metadata metadata) {
        if (StringUtils.isNotEmpty(context.getToken())) {
            metadata.put(Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER), context.getToken());
        }
        if (StringUtils.isNotEmpty(context.getGuid())) {
            metadata.put(Key.of("Guid", Metadata.ASCII_STRING_MARSHALLER), context.getGuid());
        }
        return metadata;
    }

}
