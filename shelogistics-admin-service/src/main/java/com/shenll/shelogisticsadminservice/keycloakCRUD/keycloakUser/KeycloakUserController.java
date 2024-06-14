package com.shenll.shelogisticsadminservice.keycloakCRUD.keycloakUser;

import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/keycloak")
public class KeycloakUserController {

    @Value("${keycloak.realm}")
    private String realm;

    @Autowired
    Keycloak keycloak;

    @PostMapping("create/update/user")
    public CommonResponse createAndUpdate(@RequestBody KeycloakUserDTO keycloakUserDTO) {
        try {
            RealmResource realmResource = keycloak.realm(realm);
            UsersResource usersResource = realmResource.users();

            if (keycloakUserDTO.getId() == null) {
                UserRepresentation user = createUser(keycloakUserDTO);
                usersResource.create(user);
                return CommonResponse.builder().message("User created successfully").data(keycloakUserDTO).build();
            } else {
                UserResource userResource = usersResource.get(keycloakUserDTO.getId());
                UserRepresentation userRepresentation = updateUser(keycloakUserDTO, userResource);
                userResource.update(userRepresentation);
                return CommonResponse.builder().message("User updated successfully").data(keycloakUserDTO).build();
            }
        } catch (NotFoundException e) {
            return CommonResponse.builder().code(400).message("Invalid  ID or role").build();
        } catch (Exception e) {
            System.out.println(e);
            return CommonResponse.builder().message("Failed to update/create user: " + e.getMessage()).code(500).build();
        }
    }


    UserRepresentation updateUser(KeycloakUserDTO keycloakUserDTO, UserResource userResource) {

        UserRepresentation existingUser = userResource.toRepresentation();
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(keycloakUserDTO.getFirstName() == null ? userResource.toRepresentation().getFirstName() : keycloakUserDTO.getFirstName());
        userRepresentation.setLastName(keycloakUserDTO.getLastName() == null ? userResource.toRepresentation().getLastName() : keycloakUserDTO.getLastName());
        userRepresentation.setUsername(keycloakUserDTO.getUserName() == null ? userResource.toRepresentation().getUsername() : keycloakUserDTO.getUserName());
        userRepresentation.setEmail(keycloakUserDTO.getEmail() == null ? userResource.toRepresentation().getEmail() : keycloakUserDTO.getEmail());
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("userRole", keycloakUserDTO.getUserRole() != null ? Collections.singletonList(keycloakUserDTO.getUserRole()) : userResource.toRepresentation().getAttributes().get("userRole"));
        attributes.put("designation", keycloakUserDTO.getDesignation() != null ? Collections.singletonList(keycloakUserDTO.getDesignation()) : userResource.toRepresentation().getAttributes().get("designation"));
        attributes.put("code", keycloakUserDTO.getCode() != null ? Collections.singletonList(keycloakUserDTO.getCode()) : userResource.toRepresentation().getAttributes().get("code"));
        attributes.put("address", keycloakUserDTO.getAddress() != null ? Collections.singletonList(keycloakUserDTO.getAddress()) : userResource.toRepresentation().getAttributes().get("address"));
        attributes.put("city", keycloakUserDTO.getCity() != null ? Collections.singletonList(keycloakUserDTO.getCity()) : userResource.toRepresentation().getAttributes().get("city"));
        attributes.put("state", keycloakUserDTO.getState() != null ? Collections.singletonList(keycloakUserDTO.getState()) : userResource.toRepresentation().getAttributes().get("state"));
        attributes.put("pincode", keycloakUserDTO.getPincode() != null ? Collections.singletonList(keycloakUserDTO.getPincode()) : userResource.toRepresentation().getAttributes().get("pincode"));
        attributes.put("phone", keycloakUserDTO.getPhone() != null ? Collections.singletonList(keycloakUserDTO.getPhone()) : userResource.toRepresentation().getAttributes().get("phone"));
        attributes.put("mobile", keycloakUserDTO.getMobile() != null ? Collections.singletonList(keycloakUserDTO.getMobile()) : userResource.toRepresentation().getAttributes().get("mobile"));
        attributes.put("confirmPassword", keycloakUserDTO.getConfirmPassword() != null ? Collections.singletonList(keycloakUserDTO.getConfirmPassword()) : userResource.toRepresentation().getAttributes().get("confirmPassword"));
        attributes.put("status", keycloakUserDTO.getStatus() != null ? Collections.singletonList(keycloakUserDTO.getStatus()) : userResource.toRepresentation().getAttributes().get("status"));

        String password = keycloakUserDTO.getPassword() != null ? keycloakUserDTO.getPassword() : userResource.toRepresentation().getCredentials().get(0).getValue();
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setValue(password);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setTemporary(false);
        List<CredentialRepresentation> credentials = new ArrayList<>();
        credentials.add(credential);

        if (keycloakUserDTO.getUserRole() != null) {
            RealmResource realmResource = keycloak.realm(realm);
            UsersResource usersResource = realmResource.users();
            RoleResource oldRoleResource = realmResource.roles().get(existingUser.getAttributes().get("userRole").get(0));
            RoleResource newRoleResource = realmResource.roles().get(keycloakUserDTO.getUserRole());
            if (oldRoleResource != null) {
                usersResource.get(userResource.toRepresentation().getId()).roles().realmLevel().remove(Collections.singletonList(oldRoleResource.toRepresentation()));
            }
            if (newRoleResource != null) {
                usersResource.get(userResource.toRepresentation().getId()).roles().realmLevel().add(Collections.singletonList(newRoleResource.toRepresentation()));
            } else {
                throw new RuntimeException("New role not found: " + keycloakUserDTO.getUserRole());
            }
        }
        userRepresentation.setAttributes(attributes);
        userRepresentation.setCredentials(credentials);
        return userRepresentation;
    }

    UserRepresentation createUser(KeycloakUserDTO keycloakUserDTO) {
        try {
            UserRepresentation userRepresentation = new UserRepresentation();
            userRepresentation.setUsername(keycloakUserDTO.getUserName());
            userRepresentation.setFirstName(keycloakUserDTO.getFirstName());
            userRepresentation.setLastName(keycloakUserDTO.getLastName());
            userRepresentation.setEmail(keycloakUserDTO.getEmail());
            userRepresentation.setEnabled(true);
            userRepresentation.setEmailVerified(false);

            RealmResource realmResource = keycloak.realm(realm);
            UsersResource usersResource = realmResource.users();
            Response response = usersResource.create(userRepresentation);

            if (response.getStatus() == 201) {
                String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
                RoleRepresentation roleToAssign = keycloak.realm(realm).roles().get(keycloakUserDTO.getUserRole()).toRepresentation();
                if (roleToAssign == null) {
                    throw new RuntimeException("Role not found: " + keycloakUserDTO.getUserRole());
                }
                UserResource userResource = usersResource.get(userId);
                userResource.roles().realmLevel().add(Collections.singletonList(roleToAssign));
                userResource.sendVerifyEmail();

                Map<String, List<String>> attributes = new HashMap<>();
                attributes.put("userRole", Collections.singletonList(keycloakUserDTO.getUserRole()));
                attributes.put("designation", Collections.singletonList(keycloakUserDTO.getDesignation()));
                attributes.put("code", Collections.singletonList(keycloakUserDTO.getCode()));
                attributes.put("address", Collections.singletonList(keycloakUserDTO.getAddress()));
                attributes.put("city", Collections.singletonList(keycloakUserDTO.getCity()));
                attributes.put("state", Collections.singletonList(keycloakUserDTO.getState()));
                attributes.put("pincode", Collections.singletonList(keycloakUserDTO.getPincode()));
                attributes.put("phone", Collections.singletonList(keycloakUserDTO.getPhone()));
                attributes.put("mobile", Collections.singletonList(keycloakUserDTO.getMobile()));
                attributes.put("confirmPassword", Collections.singletonList(keycloakUserDTO.getConfirmPassword()));
                attributes.put("status", Collections.singletonList(keycloakUserDTO.getStatus()));
                userRepresentation.setAttributes(attributes);
                CredentialRepresentation credential = new CredentialRepresentation();
                credential.setValue(keycloakUserDTO.getPassword());
                credential.setType(CredentialRepresentation.PASSWORD);
                credential.setTemporary(false);
                List<CredentialRepresentation> credentials = new ArrayList<>();
                credentials.add(credential);
                userRepresentation.setCredentials(credentials);

                return userRepresentation;
            } else {
                throw new RuntimeException("Failed to create user. Status: " + response.getStatus());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to create user: " + e.getMessage(), e);
        }
    }


    @DeleteMapping("/disable/{id}")
    public CommonResponse disableUser(@PathVariable String id) {
        UserResource userResource = keycloak.realm(realm).users().get(id);
        userResource.toRepresentation().setEnabled(false);
        return CommonResponse.builder().message("successfully disabled").code(200).build();

    }

    @DeleteMapping("/delete/{id}")
    public CommonResponse deleteUser(@PathVariable String id) {
        UserResource userResource = keycloak.realm(realm).users().get(id);
        try {
            userResource.remove();
            return CommonResponse.builder().message("User successfully deleted").data(id).code(200).build();
        } catch (Exception e) {
            return CommonResponse.builder().message("Error deleting user").data(null).code(500).build();
        }
    }

    @GetMapping("/findAllUsers")
    public CommonResponse getKeycloak() {
        RealmResource realm1 = keycloak.realm(realm);
        UsersResource usersResource = realm1.users();
        List<UserRepresentation> users = usersResource.list();
        return CommonResponse.builder()
                .code(200)
                .data(users)
                .build();
    }
    @GetMapping("/findUserById")
    public CommonResponse getUserById(@RequestParam(required = false) String id,
                                      @RequestParam(required = false) String name) {
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        if (id == null && name == null) {
            List<UserRepresentation> allUsers = usersResource.list();
            return CommonResponse.builder()
                    .code(200)
                    .data(allUsers)
                    .build();
        } else if (id != null) {
            UserResource userResource = usersResource.get(id);
            UserRepresentation userRepresentation = userResource.toRepresentation();

            if (name == null || userRepresentation.getUsername().equals(name)) {
                return CommonResponse.builder()
                        .code(200)
                        .data(userRepresentation)
                        .build();
            } else {
                return CommonResponse.builder()
                        .code(404)
                        .message("User not found")
                        .build();
            }
        } else {
            List<UserRepresentation> allUsers = usersResource.search(name);
            return CommonResponse.builder()
                    .code(200)
                    .data(allUsers)
                    .build();
        }
    }

}
