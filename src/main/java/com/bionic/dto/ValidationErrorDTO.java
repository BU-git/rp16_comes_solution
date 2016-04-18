/**
 * Validation Rest controllers 400 error
 */
package com.bionic.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vitalii.levash
 */
public class ValidationErrorDTO {
    private List<FieldErrorDTO> fieldErrors = new ArrayList<>();

    public ValidationErrorDTO() {

    }

    public void addFieldError(String path, String message) {
        FieldErrorDTO error = new FieldErrorDTO(path, message);
        fieldErrors.add(error);
    }

    public List<FieldErrorDTO> getFieldErrors() {
        return fieldErrors;
    }
}
