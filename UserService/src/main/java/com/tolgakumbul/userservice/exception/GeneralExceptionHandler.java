package com.tolgakumbul.userservice.exception;

import com.tolgakumbul.userservice.exception.model.GeneralErrorResponse;
import io.grpc.Metadata;
import io.grpc.Metadata.BinaryMarshaller;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;
import org.apache.commons.lang3.SerializationUtils;

import java.util.ResourceBundle;

@GrpcAdvice
public class GeneralExceptionHandler {

    private static final String DEFAULT_MESSAGE = "Oops! Something went wrong!";

    @GrpcExceptionHandler
    public StatusRuntimeException handleException(Exception exception) {
        if (exception instanceof UsersException) {
            GeneralErrorResponse generalErrorResponse;
            generalErrorResponse = handleErrorResponse((UsersException) exception);

            Metadata metadata = getMetadata(generalErrorResponse);

            return Status.INTERNAL.withDescription(generalErrorResponse.getErrorCode()).withCause(exception).augmentDescription(generalErrorResponse.getErrorMessage()).asRuntimeException(metadata);

        } else {
            GeneralErrorResponse generalErrorResponse = defaultErrorResponse();
            Metadata metadata = getMetadata(generalErrorResponse);
            return Status.UNKNOWN.withDescription(generalErrorResponse.getErrorCode()).withCause(exception).augmentDescription(generalErrorResponse.getErrorMessage()).asRuntimeException(metadata);
        }
    }

    private GeneralErrorResponse handleErrorResponse(UsersException usersException) {
        GeneralErrorResponse generalErrorResponse = new GeneralErrorResponse();
        generalErrorResponse.setErrorCode(usersException.getMessage());
        ResourceBundle resourceBundle = ResourceBundle.getBundle("message");
        generalErrorResponse.setErrorMessage(resourceBundle.getString(usersException.getMessage()));
        return generalErrorResponse;
    }

    private GeneralErrorResponse defaultErrorResponse() {
        GeneralErrorResponse generalErrorResponse = new GeneralErrorResponse();
        generalErrorResponse.setErrorCode("");
        generalErrorResponse.setErrorMessage(DEFAULT_MESSAGE);
        return generalErrorResponse;
    }

    private Metadata getMetadata(GeneralErrorResponse generalErrorResponse) {
        Metadata metadata = new Metadata();
        metadata.put(Metadata.Key.of("ERROR-bin", new BinaryMarshaller<GeneralErrorResponse>() {

            @Override
            public byte[] toBytes(GeneralErrorResponse generalErrorResponse) {
                return SerializationUtils.serialize(generalErrorResponse);
            }

            @Override
            public GeneralErrorResponse parseBytes(byte[] bytes) {
                return (GeneralErrorResponse) SerializationUtils.deserialize(bytes);
            }
        }), generalErrorResponse);
        return metadata;
    }
}
