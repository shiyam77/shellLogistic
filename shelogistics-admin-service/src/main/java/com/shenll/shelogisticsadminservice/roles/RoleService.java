package com.shenll.shelogisticsadminservice.roles;

import com.shenll.shelogisticsadminservice.exception.AppException;
import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Value("${keycloak.realm}")
    private String realm;

    @Autowired
    Keycloak keycloak;

    @Autowired
    RolesRepository rolesRepository;

    public CommonResponse createRole(RolesDTO rolesDTO) {
        try {

            String keycloakRoleUUID = roleCreationInKeycloak(rolesDTO.getRole());
            Roles roles = Roles.builder()
                    .role(rolesDTO.getRole())
                    .status(rolesDTO.getStatus())
                    .keycloakRoleUUID(keycloakRoleUUID)
                    .build();

            rolesRepository.save(roles);

            return CommonResponse.builder().code(200).message("Role created successfully").build();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.builder().code(500).message("Internal Server Error").build();
        }
    }

    public CommonResponse getALlRole() {
        List<Roles> rolesList = rolesRepository.findAll();
        return CommonResponse.builder().code(200).data(rolesList).build();
    }

    public CommonResponse deleteRoleById(String id) {
        Optional<Roles> roles = rolesRepository.findById(id);
        if (roles.isPresent()) {
            String keycloakRoleId = deleteKeycloakRole(roles.get().getKeycloakRoleUUID());
            rolesRepository.deleteById(roles.get().getId());
            return CommonResponse.builder().code(200).data(roles.get().getId()).message("Deleted successfully").build();
        } else {
            return CommonResponse.builder().code(400).message("Invalid id").build();
        }
    }

    public CommonResponse disableRole(String id) {
        Optional<Roles> optionalRoles = rolesRepository.findById(id);
        if (optionalRoles.isPresent()) {
            optionalRoles.get().setStatus("InActive");
            rolesRepository.save(optionalRoles.get());
            return CommonResponse.builder().code(200).message("deleted Sucessfully Id").build();
        }
        return CommonResponse.builder().code(400).message("Invalid Id").build();

    }

    public CommonResponse updateRoles(String id, RolesDTO rolesDTO) {
        Optional<Roles> optionalRoles = rolesRepository.findById(id);
        if (optionalRoles.isPresent()) {
            updateKeycloakRole(optionalRoles.get().getKeycloakRoleUUID(), rolesDTO);
            optionalRoles.get().setRole(rolesDTO.getRole() == null ? optionalRoles.get().getRole() : rolesDTO.getRole());
            optionalRoles.get().setStatus(rolesDTO.getStatus() == null ? optionalRoles.get().getStatus() : rolesDTO.getStatus());
            rolesRepository.save(optionalRoles.get());
            return CommonResponse.builder().code(200).message("updated successfully").build();
        } else {
            return CommonResponse.builder().code(400).message("Invalid Id").build();
        }

    }

    private String roleCreationInKeycloak(String roleName) {
        RoleRepresentation newRole = new RoleRepresentation();
        newRole.setName(roleName);
        RolesResource rolesResource = keycloak.realm(realm).roles();
        rolesResource.create(newRole);
        RoleRepresentation createdRole = rolesResource.get(roleName).toRepresentation();
        return createdRole.getId();
    }

    private String deleteKeycloakRole(String id) {
        try {
            RoleRepresentation roleToAssign = keycloak.realm(realm).rolesById().getRole(id);
            if (roleToAssign != null) {
                keycloak.realm(realm).rolesById().deleteRole(id);
                return "Role deleted from Keycloak";
            } else {
                throw new AppException("Role not found in Keycloak", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new AppException("Failed to delete role from Keycloak", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private void updateKeycloakRole(String id, RolesDTO rolesDTO) {
        try {
            RoleRepresentation keycloakRole = keycloak.realm(realm).rolesById().getRole(id);
            if (keycloakRole != null) {
                if (rolesDTO.getRole() != null) {
                    keycloakRole.setName(rolesDTO.getRole());
                }
                keycloak.realm(realm).rolesById().updateRole(id, keycloakRole);
            } else {
                throw new AppException("Role not found in Keycloak", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new AppException("Failed to update role in Keycloak", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public CommonResponse findRolesById(String id) {
        Roles roles = rolesRepository.findById(id)
                .orElseThrow(() -> new AppException("role not found for ID: " + id, HttpStatus.BAD_REQUEST));
        return CommonResponse.builder().code(200).message("successfull").data(roles).build();
    }
}
