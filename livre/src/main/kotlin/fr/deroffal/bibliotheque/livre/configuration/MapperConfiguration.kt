package fr.deroffal.bibliotheque.livre.configuration

import org.mapstruct.MapperConfig
import org.mapstruct.ReportingPolicy

//TODO Erreur de compilation sur le passage en java 17 si unmappedTargetPolicy = ERROR
@MapperConfig(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
interface MapperConfiguration {
}
