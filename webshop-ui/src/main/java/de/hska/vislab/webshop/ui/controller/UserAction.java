package de.hska.vislab.webshop.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
 
import com.opensymphony.xwork2.ActionSupport;

import de.hska.vislab.webshop.ui.model.User;
 
@Component
public class UserAction extends ActionSupport {
 
    private static final long serialVersionUID = 1L;
    private List<User> users;
          
    @SuppressWarnings("unchecked")
    public String execute() throws Exception {
        users = new ArrayList<>();
        User user = new User();
        user.id = 1l;
        user.firstname = "sven";
        user.lastname = "bauer";
        user.username = "svebau";
        user.password = "lo";
        return SUCCESS;
    }
 
    public List<User> getUsers(){
        return users;
    }
}