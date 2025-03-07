package com.library.librarymanagement.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class SearchRequest {

    @NotEmpty(message = "Error, no tags provided.")
    @JsonProperty("categories")
    Set<String> categories;
}
