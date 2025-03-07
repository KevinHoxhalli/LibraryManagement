package com.library.librarymanagement.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Set;

import static com.library.librarymanagement.enums.ResponseMessageEnum.MISSING_CATEGORIES_REQ;

@Data
public class StoreRequest {
    @NotNull(message = "Specify isbn.")
    @Pattern(regexp = "\\d{13}", message = "Error isbn number is in wrong format.")
    @JsonProperty("isbn")
    String isbn;

    @NotEmpty(message = "Error, no tags provided.")
    @JsonProperty("categories")
    Set<String> categories;
}
