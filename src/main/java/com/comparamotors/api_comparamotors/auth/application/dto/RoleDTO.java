package com.comparamotors.api_comparamotors.auth.application.dto;

import java.util.Set;
import lombok.Data;

@Data
public class RoleDTO {
    private Long id;
    private String name;
    private String description;
    private Set<PermissionDTO> permissions;
}
