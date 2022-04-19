package dev.vfcardoso.poc.api.controllers;

import dev.vfcardoso.poc.business.dao.UserDtDao;
import dev.vfcardoso.poc.business.exceptions.AppException;
import dev.vfcardoso.poc.business.models.User;
import dev.vfcardoso.poc.business.repositories.UserRepository;
import dev.vfcardoso.poc.helper.arch.datatables.DataTables;
import dev.vfcardoso.poc.helper.arch.datatables.OrderWrapper;
import dev.vfcardoso.poc.helper.datatables.DtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Named;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired private UserRepository userRepository;

    @Autowired
    private UserDtDao userDtDao;


    @GetMapping
    public Iterable findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User findOne(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(AppException::new);
    }



    @PostMapping("/list.json")
    public DataTables<DtUser> listDtJson(Integer draw, DtUser dtUser,
                                         int start, int length ) {
        try {
            Long associacaoId = 0L;
            DataTables<DtUser> dataTables = userDtDao.listJson(dtUser, associacaoId,start, length, 2, "asc");
            dataTables.setDraw(draw);
            //result.use(Results.json()).withoutRoot().from(dataTables).include("data").serialize();

            return dataTables;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new DataTables<>();
        }
    }



}
