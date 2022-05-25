package dev.vfcardoso.poc.controllers.api;

import dev.vfcardoso.poc.business.exceptions.AppException;
import dev.vfcardoso.poc.business.models.User;
import dev.vfcardoso.poc.business.repositories.base.UserRepository;
import dev.vfcardoso.poc.helper.arch.datatables.DataTables;
import dev.vfcardoso.poc.helper.datatables.DtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserApiController {
    @Autowired private UserRepository userRepository;

    @GetMapping
    public Iterable findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User findOne(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(AppException::new);
    }

    //@Named("order[0][column]") int orderColumn,
    //@Named("order[0][dir]") String orderDirection

    @PostMapping("/list.json")
    public DataTables<DtUser> listDtJson(Integer draw, DtUser dtUser,@RequestParam("order[0][column]") int orderColumn,
                                         @RequestParam("order[0][dir]") String orderDirection, int start, int length ) {
        try {
            Long associacaoId = 0L;
            DataTables<DtUser> dataTables = userRepository.listJson(dtUser, associacaoId,start, length, orderColumn, orderDirection);
            dataTables.setDraw(draw);
            return dataTables;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new DataTables<>();
        }
    }



}
