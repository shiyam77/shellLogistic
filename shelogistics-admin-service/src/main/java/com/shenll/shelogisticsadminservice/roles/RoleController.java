package com.shenll.shelogisticsadminservice.roles;

import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/shelogistics")
@RestController
public class RoleController {

    @Autowired
    RoleService roleService;

    @PostMapping("/create/role")
    public CommonResponse createKeycloakDbRole(@RequestBody RolesDTO rolesDTO) {
        return roleService.createRole(rolesDTO);
    }

    @GetMapping("/getAll/roles")
    public CommonResponse getAllRoles() {
        return roleService.getALlRole();
    }

    @GetMapping("/get/roles/{id}")
    public CommonResponse getRolesById(@PathVariable String id) {
        return roleService.findRolesById(id);
    }

    @DeleteMapping("/delete/roles/{id}")
    public CommonResponse removeRoles(@PathVariable String id) {
        return roleService.deleteRoleById(id);
    }

    @DeleteMapping("disable/role/{id}")
    public CommonResponse disableRole(@PathVariable String id) {
        return roleService.disableRole(id);
    }

    @PutMapping("/update/role/{id}")
    public CommonResponse updateRole(@PathVariable String id, @RequestBody RolesDTO rolesDTO) {
        return roleService.updateRoles(id, rolesDTO);
    }

}
