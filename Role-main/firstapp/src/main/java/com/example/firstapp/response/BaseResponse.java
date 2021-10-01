package com.example.firstapp.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@Builder
public class BaseResponse<T>  {

    private static final long serialVersionUID = 5926468583005150707L;
    @Builder.Default
    private String StatusCode="200";

    @Builder.Default
    private String StatusMsg="SUCCESS";

    private T data;
    /*PaginationVO pagination;*/
}
