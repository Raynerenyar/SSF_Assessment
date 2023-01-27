package sg.edu.nus.iss.app.assessment.model;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Pizza {

    private List<String> listPizzaTypes = List.of("bella", "margherita", "marinara", "spianatacalabrese",
            "trioformaggio");

    private List<String> listPizzaSizes = List.of("sm", "md", "lg");

    @NotEmpty(message = "Please select a pizza")
    private String pizzaType;

    // drop down defaults to small, no validation required
    private String pizzaSize;

    // Min and max validation done on html
    @NotNull(message = "Please input number of pizzas")
    private Integer numOfPizzas;

    public String getPizzaType() {
        return pizzaType;
    }

    public void setPizzaType(String pizzaType) {
        if (listPizzaTypes.contains(pizzaType)) {
            this.pizzaType = pizzaType;
        }

    }

    public String getPizzaSize() {
        return pizzaSize;
    }

    public void setPizzaSize(String pizzaSize) {
        if (listPizzaSizes.contains(pizzaSize)) {
            this.pizzaSize = pizzaSize;
        }
        // this.pizzaSize = pizzaSize;
    }

    public List<String> getListPizzaSizes() {
        return listPizzaSizes;
    }

    public Integer getNumOfPizzas() {
        return numOfPizzas;
    }

    public void setNumOfPizzas(Integer numOfPizzas) {
        this.numOfPizzas = numOfPizzas;
    }

}
