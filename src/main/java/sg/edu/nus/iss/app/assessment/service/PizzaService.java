package sg.edu.nus.iss.app.assessment.service;

import java.security.SecureRandom;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import sg.edu.nus.iss.app.assessment.model.Order;
import sg.edu.nus.iss.app.assessment.model.Pizza;
import sg.edu.nus.iss.app.assessment.repository.PizzaOrderRepo;

import java.io.IOException;

@Service
public class PizzaService {

    @Autowired
    PizzaOrderRepo por;

    Integer rushCost = 2;

    private Integer numChars = 8;

    public synchronized String generateId() {
        SecureRandom rand = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < numChars; i++) {
            // generate number between 0 to 15
            Integer randNum = rand.nextInt();
            sb.append(Integer.toHexString(randNum));
        }

        return sb.toString().substring(0, numChars);
    }

    public Order calculateCost(Pizza pizza, Order order) {

        Integer pizzaPrice = por.getPizzaPrice(pizza.getType());
        Double pizzaMulti = por.getPizzaMultiplier(pizza.getPizzaSize());
        Integer quantity = pizza.getQuantity();
        Double pizzaCost = (Double) (pizzaPrice * quantity * pizzaMulti);
        order.setPizzaCost(pizzaCost);
        if (order.getRush() == true) {
            pizzaCost += rushCost;
        }
        order.setTotalCost(pizzaCost);
        return order;
    }

    public Order saveToMySql(Pizza pizza, Order order) {

        // generate id
        order.setId(generateId());

        // calculate cost
        order = calculateCost(pizza, order);

        // saving to db
        por.insertSelectionOfPizzas(pizza, order);
        por.insertOrder(order);

        return order;
    }

    public List<Object> retrieveFromMySql(String id) throws IOException {
        Order order = por.getOrder(id);
        List<Pizza> listOfPizzas = por.getPizzaSelections(id);
        List<Object> list = List.of(order, listOfPizzas.get(0));
        return list;
    }

    public JsonObject convertToJson(Pizza pizza, Order order) {
        JsonObjectBuilder job = Json.createObjectBuilder()
                .add("orderId", order.getId())
                .add("name", order.getFullName())
                .add("address", order.getAddress())
                .add("phone", order.getPhone())
                .add("rush", order.getRush())
                .add("comments", order.getComments())
                .add("pizza", pizza.getType())
                .add("size", pizza.getPizzaSize())
                .add("quantity", pizza.getQuantity())
                .add("total", order.getTotalCost());
        JsonObject jo = job.build();
        return jo;
    }

}
