package com.hb.messenger.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class GenericResponse<T>{

    String status;
    String message;
    T data;

}
