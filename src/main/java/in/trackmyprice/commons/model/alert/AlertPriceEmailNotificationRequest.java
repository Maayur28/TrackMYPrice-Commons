package in.trackmyprice.commons.model.alert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertPriceEmailNotificationRequest {

    private String url;

    private String image;

    private String title;

    private String domain;

    private List<AlertPriceEmailModel> alertPriceEmailModelList;

}
