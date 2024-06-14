package com.shenll.shelogisticsadminservice.responseDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Builder
public class CommonResponse<T> {
    private int code;
    private String message;
    private T data;
    private Long totalCount;
}
