package org.gymanager.model.client;

import javax.validation.constraints.NotBlank;

public record AvatarDto(@NotBlank String imagen) {
}
