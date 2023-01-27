package sg.edu.nus.iss.app.assessment.model;

import java.security.SecureRandom;
import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class Order {

    private String id;
    private Pizza pizza;

    @NotEmpty(message = "Please input your name")
    @Size(min = 3, message = "Please input minimum 3 characters")
    private String name;

    @NotEmpty(message = "Please input your address")
    private String address;
    @NotEmpty(message = "Please input your phone number")
    @Size(min = 8, max = 8, message = "Please input your 8 digits phone number")
    private String phone;

    @Positive(message = "\"-\" not allowed")
    @NotNull(message = "Only digits allowed")
    private Integer phoneNumInt;

    private String comments;

    private Boolean isRush = false;

    private Double pizzaCost;
    private Double totalCost;

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Double getPizzaCost() {
        return pizzaCost;
    }

    public void setPizzaCost(Double pizzaCost) {
        this.pizzaCost = pizzaCost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        try {
            if (!phone.isEmpty()) {
                this.setPhoneNumInt(Integer.parseInt(phone));
            }
        } catch (NumberFormatException e) {
        }
        this.phone = phone;
    }

    public Integer getPhoneNumInt() {
        return phoneNumInt;
    }

    public void setPhoneNumInt(Integer phoneNumInt) {
        this.phoneNumInt = phoneNumInt;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Boolean getIsRush() {
        return isRush;
    }

    public void setIsRush(Boolean isRush) {
        this.isRush = isRush;
    }

}
