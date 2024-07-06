package co.edu.uptc.Gestor_de_rutas.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends MongoRepository<Package, Integer>{
}
