package com.od.library_management_system.enums;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openide.util.MapFormat;

import java.util.Map;

@AllArgsConstructor
@Getter
public enum ResponseCode {

    SUCCESS("200", "Success"),

    ERR_LIB_0001("ERR-LIB-0001", "Failed to save, {type} {id} is already exists."),

    ERR_LIB_0002("ERR-LIB-0002", "Record does not exist for given {type} {id}"),

    ERR_LIB_0003("ERR-LIB-0003", "Record already exist for given {type} {id}"),
    ERR_LIB_0004("ERR-LIB-0004", "Failed to delete, {type} {id}"),

    ERR_LIB_0400("ERR-LIB-0400", "Invalid Request"),

    ERR_LIB_0404("ERR-LIB-0404", "Record not found"),

    ERR_LIB_0500("ERR-LIB-0500", "Internal Server Error");


    private final String status;
    private final String message;


    public String getMessage(@Nullable Map<String, Object> param) {
        return MapFormat.format(this.message, param);
    }
}
