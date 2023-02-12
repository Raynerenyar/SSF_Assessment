package sg.edu.nus.iss.app.assessment.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import sg.edu.nus.iss.app.assessment.model.Order;
import sg.edu.nus.iss.app.assessment.model.Pizza;
import sg.edu.nus.iss.app.assessment.service.PizzaService;

@RestController
@RequestMapping
public class PizzaRestController {

    @Autowired
    PizzaService pizzaService;

    @GetMapping(path = "/order/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> retrieveId(@PathVariable String id, Model model) throws IOException {
        List<Object> listOfSomething = pizzaService.retrieveFromMySql(id);

        if (listOfSomething != null) {
            Order order = (Order) listOfSomething.get(0);
            Pizza pizza = (Pizza) listOfSomething.get(1);
            JsonObject jo = pizzaService.convertToJson(pizza, order);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(jo.toString());
        }
        JsonObjectBuilder job = Json.createObjectBuilder()
                .add("message", "Order " + id + " not found");
        JsonObject jo = job.build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jo.toString());

    }
}
