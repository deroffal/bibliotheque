package fr.deroffal.bibliotheque.commons.mapping;

import static org.mapstruct.ReportingPolicy.WARN;

import org.mapstruct.MapperConfig;

//TODO Erreur de compilation sur le passage en java 17 si unmappedTargetPolicy = ERROR
@MapperConfig(componentModel = "spring", unmappedTargetPolicy = WARN)
public interface MapperConfiguration {
}
