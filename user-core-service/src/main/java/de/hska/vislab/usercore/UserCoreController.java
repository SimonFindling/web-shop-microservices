package de.hska.vislab.usercore;

//import de.hska.iwi.vslab.usrv.model.dao.impl.UserDAOImpl;
//import de.hska.iwi.vslab.usrv.model.database.dataAccessObjects.UserDAO;
//import de.hska.iwi.vslab.usrv.model.database.dataobjects.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
 

@RestController
public class UserCoreController {
    
    //@Autowired
    //private UserRepo repo;
    ApplicationContext context = 
    		new ClassPathXmlApplicationContext("Spring-Module.xml");
//    private UserDAO repo = (UserDAO) context.getBean("userDAO");
    
    @RequestMapping(value = "/users/{userName}", method = RequestMethod.GET)
    
    public ResponseEntity<String> getUser(@PathVariable String userName) {
        
        //User user = repo.findOne(userName);
        return new ResponseEntity<>("Penner", HttpStatus.OK);
    }

}

