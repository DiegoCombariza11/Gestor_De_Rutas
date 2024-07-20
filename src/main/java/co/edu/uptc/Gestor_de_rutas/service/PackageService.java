package co.edu.uptc.Gestor_de_rutas.service;

import co.edu.uptc.Gestor_de_rutas.model.Package;
import co.edu.uptc.Gestor_de_rutas.repository.PackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PackageService {
    private final PackageRepository packageRepository;
    public void deletePackage(int id) {
        packageRepository.deleteById(id);
    }
    public void savePackage(Package p) {
        packageRepository.save(p);
    }
    public void updatePackage(Package p) {
        packageRepository.save(p);
    }
    public List<Package> getAllPackages() {
        return packageRepository.findAll();
    }
    public Optional<Package> getPackageById(int id) {
        return packageRepository.findById(id);
    }
}
