package com.shenll.shelogisticsadminservice.keycloakCRUD.keycloakRole;

import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("keycloak/role")
public class KeycloakRoleController {


    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${keycloak.urls.auth}")
    private String authUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Value("${keycloak.admin.secret}")
    private String adminSecret;

    @Value("${keycloak.admin.clientid}")
    private String adminClientId;

    @Autowired
    Keycloak keycloak;

    @GetMapping("/role")
    public CommonResponse roleFindAll() {
        List<RoleRepresentation> roles = keycloak.realm(realm).roles().list();
        return CommonResponse.builder()
                .message("Roles fetched successfully")
                .data(roles)
                .code(200)
                .build();
    }


    @PostMapping("/assign/role")
    public CommonResponse assignRoleToUser(@RequestParam String roleName, @RequestParam String userId) {
        try {
            RoleRepresentation roleToAssign = keycloak.realm(realm).roles().get(roleName).toRepresentation();
            if (roleToAssign == null) {
                return CommonResponse.builder()
                        .message("Role not found: " + roleName)
                        .code(404)
                        .build();
            }
            UserResource userResource = keycloak.realm(realm).users().get(userId);
            userResource.roles().realmLevel().add(Collections.singletonList(roleToAssign));
            return CommonResponse.builder()
                    .message("Role assigned to user successfully")
                    .data(roleToAssign)
                    .code(200)
                    .build();
        } catch (Exception e) {
            return CommonResponse.builder()
                    .message("Failed to assign role to user: " + e.getMessage())
                    .code(500)
                    .build();
        }
    }

    @PostMapping("/create/role")
    public CommonResponse createRole(@RequestBody KeycloakRoleDTO keycloakRoleDTO) {
        if (keycloakRoleDTO.getRole() == null || keycloakRoleDTO.getRole().trim().isEmpty()) {
            return CommonResponse.builder()
                    .message("Role name cannot be empty")
                    .code(400)
                    .build();
        }
        try {
            RoleRepresentation newRole = new RoleRepresentation();
            newRole.setName(keycloakRoleDTO.getRole());
            RolesResource rolesResource = keycloak.realm(realm).roles();
            rolesResource.create(newRole);
            return CommonResponse.builder()
                    .message("Role created successfully")
                    .data(newRole)
                    .code(200)
                    .build();
        } catch (Exception e) {
            return CommonResponse.builder()
                    .message("Failed to create role: " + e.getMessage())
                    .code(500)
                    .build();
        }
    }

    @GetMapping("getRoleById")
    public CommonResponse getAllRole(@RequestParam String id) {
        RoleRepresentation roleToAssign;
        roleToAssign = keycloak.realm(realm).rolesById().getRole(id);
        return CommonResponse.builder().code(200).data(roleToAssign).build();
    }

}
