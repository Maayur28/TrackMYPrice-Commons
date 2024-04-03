package io.github.maayur28.response;

import io.github.maayur28.model.alert.AlertModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertResponse {

    private List<AlertModel> alerts;

    private int count;

    private int limit;
}
