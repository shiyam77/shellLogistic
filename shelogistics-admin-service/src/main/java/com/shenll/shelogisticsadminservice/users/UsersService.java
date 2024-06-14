package com.shenll.shelogisticsadminservice.users;

import com.shenll.shelogisticsadminservice.agent.Agent;
import com.shenll.shelogisticsadminservice.agent.AgentRepository;
import com.shenll.shelogisticsadminservice.designations.Designation;
import com.shenll.shelogisticsadminservice.designations.DesignationRepository;
import com.shenll.shelogisticsadminservice.exception.AppException;
import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;
import com.shenll.shelogisticsadminservice.roles.Roles;
import com.shenll.shelogisticsadminservice.roles.RolesRepository;
import jakarta.persistence.criteria.Predicate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
public class UsersService {

    private static final Logger logger = Logger.getLogger(UsersService.class.getName());

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    @Autowired
    AgentRepository agentRepository;

    @Autowired
    DesignationRepository designationRepository;


    public CommonResponse createUser(UserDTO userDTO) {
        logger.info("Enter into createTestUser service method");
        Users user = new Users();
        Roles dbRole = rolesRepository.findById(userDTO.getUserRole())
                .orElseThrow(() -> new AppException("Invalid Id", HttpStatus.BAD_GATEWAY));

        Agent agent = agentRepository.findById(userDTO.getCode())
                .orElseThrow(() -> new AppException("Agent not found for code: ", HttpStatus.BAD_REQUEST));

        Designation designation = designationRepository.findById(userDTO.getDesignation())
                .orElseThrow(() -> new AppException("Invalid designation Id", HttpStatus.BAD_REQUEST));

        try {
            String keycloakId = createOAuthUser(userDTO);
            logger.info("Keycloak user created with ID: " + keycloakId);

            if (StringUtils.isNotBlank(keycloakId)) {
                user = convertDtoToEntity(userDTO, keycloakId);
                user.setKeycloakId(keycloakId);
                user.setUserRole(dbRole);
                user.setCode(agent);
                user.setDesignation(designation);
                usersRepository.save(user);
                logger.info("User saved to the repository");
                return CommonResponse.builder().message("Success").code(200).build();
            } else {
                logger.warning("Failed to create Keycloak user");
                return CommonResponse.builder().message("Failure").code(500).build();
            }
        } catch (Exception e) {
            logger.severe("Exception in createTestUser: " + e.getMessage());
            return CommonResponse.builder().message("Exception: " + e.getMessage()).code(500).build();
        }
    }

    private String createOAuthUser(UserDTO userDTO) {
        logger.info("Enter into createOAuthUser service method");

        if (StringUtils.isBlank(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email cannot be blank");
        }

        String keycloakId = null;
        Users users = new Users().builder()
                .userName(userDTO.getUserName())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .city(userDTO.getCity())
                .state(userDTO.getState())
                .pincode(userDTO.getPincode())
                .address(userDTO.getAddress())
                .phone(userDTO.getPhone())
                .mobile(userDTO.getMobile())
                .status(userDTO.getStatus())
                .build();

        String uuid = createUser(users, userDTO.getUserRole());
        if (StringUtils.isNotBlank(uuid)) {
            keycloakId = uuid;
            logger.info("Keycloak ID generated: " + keycloakId);
        }
        return keycloakId;
    }

    private String createUser(Users users, String roleId) {
        logger.info("Enter into createUser method");
        String userId = "";

        UserRepresentation user = new UserRepresentation();
        user.setUsername(users.getUserName());
        user.setFirstName(users.getFirstName());
        user.setLastName(users.getLastName());
        user.setEmail(users.getEmail());
        user.setEnabled(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(users.getPassword());
        user.setCredentials(Arrays.asList(credential));

        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        Response response = usersResource.create(user);

        if (response.getStatus() == 201) {
            String locationHeader = response.getLocation().getPath();
            userId = locationHeader.replaceAll(".*/([^/]+)$", "$1");
            logger.info("User created in Keycloak with ID: " + userId);
            Roles dbRole = rolesRepository.findById(roleId)
                    .orElseThrow(() -> new AppException("Invalid Id", HttpStatus.BAD_GATEWAY));
            logger.info("role in Keycloak with ID: " + roleId);

            RoleRepresentation roleToAssign = realmResource.rolesById().getRole(dbRole.getKeycloakRoleUUID());
            if (roleToAssign == null) {
                throw new RuntimeException("Role not found: " + roleId);
            }
            UserResource userResource = usersResource.get(userId);
            userResource.roles().realmLevel().add(Collections.singletonList(roleToAssign));
        } else {
            logger.severe("Failed to create user in Keycloak. Status: " + response.getStatus());
            throw new RuntimeException("Failed to create user. Status: " + response.getStatus());
        }
        return userId;
    }


    private Users convertDtoToEntity(UserDTO userDTO, String keycloakId) {
        return Users.builder()
                .userName(userDTO.getUserName())
                .firstName(userDTO.getFirstName())
                .email(userDTO.getEmail())
                .lastName(userDTO.getLastName())
                .city(userDTO.getCity())
                .state(userDTO.getState())
                .pincode(userDTO.getPincode())
                .address(userDTO.getAddress())
                .phone(userDTO.getPhone())
                .mobile(userDTO.getMobile())
                .build();
    }

    public CommonResponse findAllData(Long page, Long size) {
        Pageable pageable = PageRequest.of(page == null ? 0 : page.intValue(), size == null ? Integer.MAX_VALUE : size.intValue(), Sort.Direction.DESC, "createdAt");
        Page<Users> users = usersRepository.findAll(pageable);
        if (users.hasContent()) {
            return CommonResponse.builder()
                    .code(200)
                    .message("Successfully fetched User's data")
                    .data(users.getContent())
                    .totalCount(users.getTotalElements())
                    .build();
        } else {
            return CommonResponse.builder()
                    .code(200)
                    .message("User data is empty")
                    .data(Collections.emptyList())
                    .totalCount(0L)
                    .build();
        }
    }

    public CommonResponse deleteUserById(String id) {
        Optional<Users> optionalUser = usersRepository.findById(id);

        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();

            if (user.getKeycloakId() != null) {
                try {
                    UserResource userResource = keycloak.realm(realm).users().get(user.getKeycloakId());
                    UserRepresentation userRepresentation = userResource.toRepresentation();
                    userRepresentation.setEnabled(false);
                    userResource.update(userRepresentation);
                } catch (Exception e) {
                    throw new AppException("Failed to disable Keycloak user", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                throw new AppException("Invalid Keycloak Id", HttpStatus.BAD_REQUEST);
            }
            usersRepository.deleteById(user.getId());
            return CommonResponse.builder()
                    .code(200)
                    .data(user.getUserName())
                    .message("User deleted successfully")
                    .build();

        } else {
            return CommonResponse.builder()
                    .code(400)
                    .message("Bad request: user not found")
                    .build();
        }
    }

    public CommonResponse updateUserById(String id, UserDTO userDTO) {

        Optional<Users> optionalUsers = usersRepository.findById(id);
        if (optionalUsers.isPresent()) {

            String key = updateKeycloakUser(optionalUsers.get().getKeycloakId(), userDTO);

            Users user = optionalUsers.get();
            user.setUserName(userDTO.getUserName() != null ? userDTO.getUserName() : user.getUserName());
            user.setFirstName(userDTO.getFirstName() != null ? userDTO.getFirstName() : user.getFirstName());
            user.setLastName(userDTO.getLastName() != null ? userDTO.getLastName() : user.getLastName());
            user.setAddress(userDTO.getAddress() != null ? userDTO.getAddress() : user.getAddress());
            user.setCity(userDTO.getCity() != null ? userDTO.getCity() : user.getCity());
            user.setState(userDTO.getState() != null ? userDTO.getState() : user.getState());
            user.setPincode(userDTO.getPincode() != null ? userDTO.getPincode() : user.getPincode());
            user.setPhone(userDTO.getPhone() != null ? userDTO.getPhone() : user.getPhone());
            user.setMobile(userDTO.getMobile() != null ? userDTO.getMobile() : user.getMobile());
            user.setEmail(userDTO.getEmail() != null ? userDTO.getEmail() : user.getEmail());
            user.setPassword(userDTO.getPassword() != null ? userDTO.getPassword() : user.getPassword());
            user.setConfirmPassword(userDTO.getConfirmPassword() != null ? userDTO.getConfirmPassword() : user.getConfirmPassword());
            user.setStatus(userDTO.getStatus() != null ? userDTO.getStatus() : user.getStatus());
            if (userDTO.getUserRole() != null) {
                Roles dbRole = rolesRepository.findById(userDTO.getUserRole())
                        .orElseThrow(() -> new AppException("Invalid role Id", HttpStatus.BAD_GATEWAY));
                user.setUserRole(dbRole);
            }
            if (userDTO.getDesignation() != null) {
                Designation designation = designationRepository.findById(userDTO.getDesignation())
                        .orElseThrow(() -> new AppException("Invalid designation Id", HttpStatus.BAD_REQUEST));
                user.setDesignation(designation);
            }
            if (userDTO.getCode() != null) {
                Agent agent = agentRepository.findById(userDTO.getCode())
                        .orElseThrow(() -> new AppException("Agent not found for code: " + userDTO.getCode(), HttpStatus.BAD_REQUEST));
                user.setCode(agent);
            }

            usersRepository.save(user);
            return CommonResponse.builder().code(200).message("successfully updated").build();
        } else {
            throw new AppException("User not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }

    String updateKeycloakUser(String id, UserDTO userDTO) {

        UsersResource usersResource = keycloak.realm(realm).users();
        UserResource userResource = usersResource.get(id);
        UserRepresentation keycloakUser = userResource.toRepresentation();

        if (userDTO.getUserName() != null) keycloakUser.setUsername(userDTO.getUserName());
        if (userDTO.getEmail() != null) keycloakUser.setEmail(userDTO.getEmail());
        if (userDTO.getFirstName() != null) keycloakUser.setFirstName(userDTO.getFirstName());
        if (userDTO.getLastName() != null) keycloakUser.setLastName(userDTO.getLastName());

        userResource.update(keycloakUser);
        if (userDTO.getPassword() != null) {
            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setTemporary(false);
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(userDTO.getPassword());
            userResource.resetPassword(credential);
        }
        if (userDTO.getUserRole() != null) {
            List<RoleRepresentation> currentRoles = userResource.roles().realmLevel().listAll();
            userResource.roles().realmLevel().remove(currentRoles);
            RoleRepresentation roleRep = keycloak.realm(realm).rolesById().getRole(userDTO.getUserRole());
            userResource.roles().realmLevel().add(Collections.singletonList(roleRep));
        }

        return keycloakUser.getId();
    }

    public CommonResponse findUsersById(String id) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new AppException("User not found for ID: " + id, HttpStatus.BAD_REQUEST));
        return CommonResponse.builder().code(200).message("successfull").data(user).build();
    }

    public CommonResponse getUserWithFilters(String name, String email, String phone,String id) {
        Specification<Users> spec = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                predicates.add(builder.like(root.get("userName"), "%" + name + "%"));
            }
            if (email != null && !email.isEmpty()) {
                predicates.add(builder.like(root.get("email"), "%" + email + "%"));
            }
            if (phone != null && !phone.isEmpty()) {
                predicates.add(builder.like(root.get("phone"), "%" + phone + "%"));
            }
            if (id != null && !id.isEmpty()) {
                predicates.add(builder.like(root.get("id"), "%" + id + "%"));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
        List<Users> users = usersRepository.findAll(spec);
        if (users.isEmpty()) {
            return CommonResponse.builder().message("No Users found").code(400).build();
        } else {
            return CommonResponse.builder().data(users).message("Users found").code(200).build();
        }
    }


    public CommonResponse getKeycloakId(String id) {
        Users user = usersRepository.findByKeycloakId(id)
                .orElseThrow(() -> new AppException("Keycloak Id not found : " + id, HttpStatus.BAD_REQUEST));
        return CommonResponse.builder().message("sucess").data(user).code(200).build();
    }

}

