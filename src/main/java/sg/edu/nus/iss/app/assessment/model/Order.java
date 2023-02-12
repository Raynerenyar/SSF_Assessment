package sg.edu.nus.iss.app.assessment.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Order {

    private String id;
    private Pizza pizzaType;

    @NotEmpty(message = "Please input your name")
    @Size(min = 3, message = "Please input minimum 3 characters")
    private String fullName;

    @NotEmpty(message = "Please input your address")
    private String address;
    @NotEmpty(message = "Please input your phone number")
    @Size(min = 8, max = 8, message = "Please input your 8 digits phone number")
    private String phone;

    @Positive(message = "\"-\" not allowed")
    @NotNull(message = "Only digits allowed")
    private Integer phoneNumInt;

    private String comments;
    private Boolean rush = false;
    private Double pizzaCost;
    private Double totalCost;

    public void setPhone(String phone) {
        try {
            if (!phone.isEmpty()) {
                this.setPhoneNumInt(Integer.parseInt(phone));
            }
        } catch (NumberFormatException e) {
        }
        this.phone = phone;
    }
}
