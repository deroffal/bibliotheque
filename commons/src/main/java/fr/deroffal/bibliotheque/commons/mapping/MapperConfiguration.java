package fr.deroffal.bibliotheque.commons.mapping;

import static org.mapstruct.ReportingPolicy.ERROR;

import org.mapstruct.MapperConfig;

@MapperConfig(componentModel = "spring", unmappedTargetPolicy = ERROR)
public interface MapperConfiguration {
}
