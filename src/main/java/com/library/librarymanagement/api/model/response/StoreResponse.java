package com.library.librarymanagement.api.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class StoreResponse {
    @JsonProperty("success")
    @Builder.Default
    boolean success = true;

    @JsonProperty("message")
    String message;

}
