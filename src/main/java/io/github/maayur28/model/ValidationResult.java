package io.github.maayur28.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationResult {
    private List<String> errors = new ArrayList<>();
    private boolean valid = true;

    public void addError(String error) {
        errors.add(error);
        valid = false;
    }
}
