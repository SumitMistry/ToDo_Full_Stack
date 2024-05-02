package springboot.ToDo.Controller.Health;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@RequestMapping("api/todo")
@Controller
public class Health_Controller {

    //private static Logger logger = LoggerFactory.getLogger(Health_Controller.class);

    @Value("${health_msg}")
    String health_msg;

    @RequestMapping(value = "/health",method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String healthCheck(){
        //logger.info("message is..." + health_msg);
        return health_msg;
    }
}
