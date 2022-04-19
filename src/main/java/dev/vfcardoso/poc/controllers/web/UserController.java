package dev.vfcardoso.poc.controllers.web;

import com.fasterxml.jackson.databind.util.JSONPObject;
import dev.vfcardoso.poc.business.dao.UserDtDao;
import dev.vfcardoso.poc.helper.arch.datatables.DataTables;
import dev.vfcardoso.poc.helper.arch.datatables.OrderWrapper;
import dev.vfcardoso.poc.helper.datatables.DtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Named;
import javax.xml.crypto.Data;
import java.util.List;
import java.util.Map;


@Controller("WebUserController")
@RequestMapping("/dashboard/users")
public class UserController {








    @GetMapping("/list")
    public String list(Model model) { return "pages/dashboard/user/list"; }



}
