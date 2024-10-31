package com.od.library_management_system.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.od.library_management_system.exception.ErrorDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private ResponseData data;

    private String status;

    private String message;

    private List<ErrorDto> errors;


    public Response addError(ErrorDto error) {
        if (this.errors == null)
            this.errors = new ArrayList<>();

        this.errors.add(error);
        return this;
    }

    public Response addAllErrors(List<ErrorDto> errors) {
        if (this.errors == null)
            this.errors = new ArrayList<>();

        this.errors.addAll(errors);
        return this;
    }

}
