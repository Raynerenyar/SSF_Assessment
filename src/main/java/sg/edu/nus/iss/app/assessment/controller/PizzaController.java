package sg.edu.nus.iss.app.assessment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.edu.nus.iss.app.assessment.model.Order;
import sg.edu.nus.iss.app.assessment.model.Pizza;
import sg.edu.nus.iss.app.assessment.service.PizzaService;

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
        order = pizzaService.saveToMySql(pizza, order);

        model.addAttribute("order", order);
        return "deliveryDetails";
    }

    @PostMapping(path = "/pizza/order", params = "action=reset")
    public String cancelOrder(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}
