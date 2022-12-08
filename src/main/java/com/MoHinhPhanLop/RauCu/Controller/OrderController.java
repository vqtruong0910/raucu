package com.MoHinhPhanLop.RauCu.Controller;

import com.MoHinhPhanLop.RauCu.Entity.Order;
import com.MoHinhPhanLop.RauCu.Entity.OrderDetail;
import com.MoHinhPhanLop.RauCu.Entity.User;
import com.MoHinhPhanLop.RauCu.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/addOrder")
    public String addOrder(HttpSession session){
        Order order = new Order();
        Double amount = 0.0;
        Set<OrderDetail> orderDetails = new HashSet<>((ArrayList<OrderDetail> )session.getAttribute("cart"));
        if(orderDetails == null) return "redirect:/cart";
        Iterator<OrderDetail> itr = orderDetails.iterator();
        while(itr.hasNext()){
            amount += itr.next().getAmount();
        }
        User user = (User) session.getAttribute("user");
        if(user == null) return "redirect:/login";
        order.setUser(user);
        order.setOrderDetails(orderDetails);
        order.setAmount(amount);
        orderService.addOrder(order);
        session.setAttribute("cart", null);
        return "redirect:/cart";
    }
}