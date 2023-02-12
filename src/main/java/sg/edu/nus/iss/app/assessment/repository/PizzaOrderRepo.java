package sg.edu.nus.iss.app.assessment.repository;

import java.util.List;

import sg.edu.nus.iss.app.assessment.model.Order;
import sg.edu.nus.iss.app.assessment.model.Pizza;

public interface PizzaOrderRepo {
    Integer getPizzaPrice(String type);

    Double getPizzaMultiplier(String size);

    Integer insertSelectionOfPizzas(Pizza pizza, Order order);

    Integer insertOrder(Order order);

    Order getOrder(String id);

    List<Pizza> getPizzaSelections(String id);

}
