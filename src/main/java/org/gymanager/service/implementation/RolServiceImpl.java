package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.model.domain.Rol;
import org.gymanager.repository.specification.RolRepository;
import org.gymanager.service.specification.RolService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@RequiredArgsConstructor
@Slf4j
public class RolServiceImpl implements RolService {

    private final static String ROLES_INEXISTENTES = "Los roles buscados (%s)no existen";

    @NonNull
    private RolRepository rolRepository;

    @Override
    public List<String> getRoles() {
        return getRolEntities().stream().map(Rol::getNombreRol).toList();
    }

    @Override
    public List<Rol> getRolEntities() {
        return rolRepository.findAll();
    }

    @Override
    public List<Rol> getRolEntitiesByRolNames(List<String> roles) {
        if(isEmpty(roles)){
            return new ArrayList<>();
        }

        var rolEntities = rolRepository.findAllByNombreRolIn(roles);
        if(rolEntities.isEmpty()){
            log.error(String.format(ROLES_INEXISTENTES, String.join(",", roles)));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format(ROLES_INEXISTENTES, String.join(",", roles)));
        }
        return rolEntities;
    }
}
