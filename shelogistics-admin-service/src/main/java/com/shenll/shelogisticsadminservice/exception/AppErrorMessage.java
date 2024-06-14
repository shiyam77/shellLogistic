package com.shenll.shelogisticsadminservice.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)

public class AppErrorMessage {
    private int code = HttpStatus.BAD_REQUEST.value();
    private String error = "";
    private List<String> errorMsg_list = new ArrayList<>();
    private Map<String, String> errorMsg_map = new TreeMap<>();

    public AppErrorMessage(String message) {
        error = message;
        this.code = code;

    }

}
