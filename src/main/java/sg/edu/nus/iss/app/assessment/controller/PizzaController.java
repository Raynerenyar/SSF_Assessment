package sg.edu.nus.iss.app.assessment.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.edu.nus.iss.app.assessment.model.Order;
import sg.edu.nus.iss.app.assessment.model.Pizza;
import sg.edu.nus.iss.app.assessment.service.PizzaService;

import static java.util.Map.entry;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping
public class PizzaController {

    @Autowired
    PizzaService pizzaService;

    @GetMapping("/")
    public String landingPage(Pizza pizza, Model model) {
        model.addAttribute("pizza", pizza);
        return "landing";
    }

    @PostMapping("/pizza")
    public String getOrder(@Valid Pizza pizza, BindingResult result, Order order, Model model, HttpSession session) {
        if (result.hasErrors()) {
            return "landing";
        }
        session.setAttribute("pizza", pizza);
        session.setAttribute("order", order);
        model.addAttribute("pizza", pizza);
        model.addAttribute("order", order);
        return "orderDetails";
    }

    @PostMapping(path = "/pizza/order", params = "action=submit")
    public String deliveryDetails(@Valid Order order, BindingResult result, Model model,
            HttpSession session) {
        Pizza pizza = (Pizza) session.getAttribute("pizza");

        if (result.hasErrors()) {
            return "orderDetails";
        }
        order = pizzaService.saveToRedis(pizza, order);

        model.addAttribute("order", order);
        return "deliveryDetails";
    }

    // @GetMapping(path = "/order/{id}")
    // public String retrieveId(@PathVariable String id, Model model) throws
    // IOException {
    // List<Object> listOfSomething = pizzaService.retrieveFromRedis(id);
    // Order order = (Order) listOfSomething.get(0);
    // Pizza pizza = (Pizza) listOfSomething.get(1);
    // // Map<String, String> sizeMultiplierMap = Map.ofEntries(
    // // entry("orderId", order.getId()),
    // // entry("name", order.getName()),
    // // entry("address", order.getAddress()),
    // // entry("phone", order.getPhone()),
    // // entry("rush", order.getIsRush().toString()),
    // // entry("comments", order.getComments()),
    // // entry("pizza", pizza.getPizzaType()),
    // // entry("pizza", pizza.getPizzaSize()),
    // // entry("quantity", pizza.getNumOfPizzas().toString()),
    // // entry("total", order.getTotalCost().toString()));

    // model.addAttribute("order", order);
    // model.addAttribute("pizza", pizza);
    // return "fullDetails";
    // }

    @PostMapping(path = "/pizza/order", params = "action=reset")
    public String cancelOrder(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}
