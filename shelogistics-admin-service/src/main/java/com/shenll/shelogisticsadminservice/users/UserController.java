package com.shenll.shelogisticsadminservice.users;


import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shelogistics")
public class UserController {
    @Autowired
    UsersService usersService;

    @PostMapping("/create/user")
    public CommonResponse addUser(@RequestBody UserDTO userDTO) {
        return usersService.createUser(userDTO);
    }

    @GetMapping("/getAll/users")
    public CommonResponse getAll(@RequestParam(required = false) Long page,
                                 @RequestParam(required = false) Long size) {
        return usersService.findAllData(page, size);
    }

    @GetMapping("/get/users/{id}")
    public CommonResponse getUsersById(@PathVariable String id) {
        return usersService.findUsersById(id);
    }

    @GetMapping("/get/user/filters")
    public CommonResponse getRoleFilters(@RequestParam(required = false) String name,
                                         @RequestParam(required = false) String email,
                                         @RequestParam(required = false) String phone,
                                         @RequestParam(required = false) String id ) {
        return usersService.getUserWithFilters(name, email, phone,id);
    }

    @DeleteMapping("/delete/user/{id}")
    public CommonResponse deleteUse(@PathVariable String id) {
        return usersService.deleteUserById(id);
    }

    @PutMapping("/update/user/{id}")
    public CommonResponse updateUser(@PathVariable String id, @RequestBody UserDTO userDTO) {
        return usersService.updateUserById(id, userDTO);
    }

    @GetMapping("/getUserByKeycloakId/{id}")
    public CommonResponse getUserKeycloakId(@PathVariable String id) {
        return usersService.getKeycloakId(id);
    }


}