package controller;

import model.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.CustomerServiceImpl;

import java.util.List;

@Controller
public class TestThymeLeafController {
    private CustomerServiceImpl customerService = new CustomerServiceImpl();

    @GetMapping(value = {"/", "/list"})
    public String index(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "/list";
    }

    @GetMapping("/create")
    public String getFormCreateCustomer(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customerForm", customer);
        return "/addcustomer";
    }

    @PostMapping("/create/customer")
    public String createCustomer(Model model, @ModelAttribute("customerForm") Customer customer) {
        List<Customer> customerList = customerService.findAll();
        int id = customerList.size() + 1;
        String name = customer.getName();
        String email = customer.getEmail();
        String address = customer.getAdress();
        if (name != null && name.length() > 0 && email != null && email.length() > 0 && address != null && address.length() > 0) {
            Customer customer1 = new Customer(id, name, email, address);
            customerService.save(customer1);
            return "redirect:/list";
        }
        String errorMessenger = "Please fill field in the form!";
        model.addAttribute("errorMessenger", errorMessenger);
        return "/addcustomer";
    }
    @GetMapping("/customer/{id}")
    public String view(@PathVariable int id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        return "/view";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model){
        Customer customer1 = customerService.findById(id);
        model.addAttribute("customer", customer1);
        return "/edit";
    }
    @PostMapping("/edit")
    public String editCustomer(Customer customer, Model model){
        System.out.println("id c"+customer.getId());
        Customer customer1 = customerService.findById(customer.getId());
        System.out.println("cu1"+customer1);
        System.out.println("name"+customer.getName().length());
        if(customer1==null||customer.getName()==null||customer.getName().equals("")||customer.getAdress()==null||customer.getAdress().length()==0
        ||customer.getEmail()==null||customer.getEmail().length()==0){
            System.out.println("vô đây!");
            String messenger = "error";
            model.addAttribute("messenger",messenger);
            return "/edit";
        } else {
            System.out.println("id"+customer.getId());
            customerService.update(customer.getId(),customer);
            return "redirect:/list";
        }
    }
@GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable int id, Model model){
    Customer customer1 = customerService.findById(id);
    model.addAttribute("customer", customer1);
        return "/delete";
}
@PostMapping("/delete")
    public String delete(Customer customer){
        Customer customer1 = customerService.findById(customer.getId());
        System.out.println("cu1"+customer1);
        if(customer1!=null){
            customerService.delete(customer.getId());
            return "redirect:/list";
        }
  return "/delete";
}
}
