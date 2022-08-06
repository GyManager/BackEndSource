package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.MicroPlanEntityToDtoConverter;
import org.gymanager.model.client.MicroPlanDto;
import org.gymanager.model.enums.MicroPlanSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.gymanager.repository.filters.MicroPlanSpecification;
import org.gymanager.repository.specification.MicroPlanRepository;
import org.gymanager.service.specification.MicroPlanService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MicroPlanServiceImpl implements MicroPlanService {

    @NonNull
    private MicroPlanRepository microPlanRepository;

    @NonNull
    private MicroPlanEntityToDtoConverter microPlanEntityToDtoConverter;

    @Override
    public GyManagerPage<MicroPlanDto> getMicroPlanes(String search, Integer page, Integer pageSize,
                                                                      MicroPlanSortBy sortBy, Sort.Direction direction) {
        var microPlanSpecification = new MicroPlanSpecification();
        microPlanSpecification.setSearch(search);

        var sort = sortBy.equals(MicroPlanSortBy.NONE) ? Sort.unsorted() : Sort.by(direction, sortBy.getField());
        var pageable = PageRequest.of(page, pageSize, sort);

        return new GyManagerPage<>(microPlanRepository.findAll(microPlanSpecification, pageable)
                .map(microPlanEntityToDtoConverter::convert));
    }
}
