package sg.edu.nus.iss.app.assessment.service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.app.assessment.model.Order;
import sg.edu.nus.iss.app.assessment.model.Pizza;

import static java.util.Map.entry;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

@Service
public class PizzaService {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    Map<String, Integer> priceMap = Map.ofEntries(
            entry("bella", 30),
            entry("marinara", 30),
            entry("spianatacalabrese", 30),
            entry("margherita", 22),
            entry("trioformaggio", 25));

    Map<String, String> sizeMultiplierMap = Map.ofEntries(
            entry("sm", "1"),
            entry("md", "1.2"),
            entry("lg", "1.5"));

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
        Integer pizzaCost = priceMap.get(pizza.getPizzaType());
        Integer numOfPizzas = pizza.getNumOfPizzas();
        Double multiplier = Double.valueOf(sizeMultiplierMap.get(pizza.getPizzaSize()));
        Double totalCost = (Double) (pizzaCost * numOfPizzas * multiplier);
        // Double totalCost = (Double) Math.floor(((pizzaCost * numOfPizzas *
        // multiplier) * 10) / 10);
        order.setPizzaCost(totalCost);
        return order;
    }

    public Order saveToRedis(Pizza pizza, Order order) {

        // generate cost and bind to order
        order.setId(generateId());
        order = calculateCost(pizza, order);
        if (order.getIsRush()) {
            order.setTotalCost(order.getPizzaCost() + 2f);
        } else {
            order.setTotalCost(order.getPizzaCost() + 0f);
        }

        // save to redis
        JsonObject jo = convertToJson(pizza, order);
        redisTemplate.opsForValue().set(order.getId(), jo.toString());

        return order;
    }

    public List<Object> retrieveFromRedis(String id) throws IOException {
        String jsonString = (String) redisTemplate.opsForValue().get(id);
        if (jsonString != null) {
            Order order = new Order();
            Pizza pizza = new Pizza();
            try (InputStream is = new ByteArrayInputStream(jsonString.getBytes())) {
                JsonReader r = Json.createReader(is);
                JsonObject o = r.readObject();
                o.getOrDefault(r, o);
                order.setId(o.getString("orderId"));
                order.setName(o.getString("name"));
                order.setAddress(o.getString("address"));
                order.setPhone(o.getString("phone"));
                order.setIsRush(o.getBoolean("rush"));
                order.setComments(o.getString("comments"));
                pizza.setPizzaType(o.getString("pizza"));
                pizza.setPizzaSize(o.getString("size"));
                pizza.setNumOfPizzas(o.getInt("quantity"));
                order.setTotalCost(Double.valueOf(o.getInt("total")));
                List<Object> listOfObjects = List.of(order, pizza);
                return listOfObjects;
            }
        }
        return null;
    }

    public JsonObject convertToJson(Pizza pizza, Order order) {
        JsonObjectBuilder job = Json.createObjectBuilder()
                .add("orderId", order.getId())
                .add("name", order.getName())
                .add("address", order.getAddress())
                .add("phone", order.getPhone())
                .add("rush", order.getIsRush())
                .add("comments", order.getComments())
                .add("pizza", pizza.getPizzaType())
                .add("size", pizza.getPizzaSize())
                .add("quantity", pizza.getNumOfPizzas())
                .add("total", order.getTotalCost());
        JsonObject jo = job.build();
        return jo;
    }

}
