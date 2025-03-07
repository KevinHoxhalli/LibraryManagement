package com.library.librarymanagement.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class SearchResponse {
    @JsonProperty("success")
    @Builder.Default
    boolean success = true;

    @JsonProperty("message")
    String message;

    @JsonProperty("total")
    long total;

    @JsonProperty("data")
    Set<String> data;

}
