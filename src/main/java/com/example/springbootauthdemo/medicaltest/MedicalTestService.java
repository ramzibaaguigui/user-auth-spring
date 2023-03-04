package com.example.springbootauthdemo.medicaltest;

import com.example.springbootauthdemo.lab.LabRepository;
import com.example.springbootauthdemo.lab.Laboratory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalTestService {

    private final MedicalTestFamilyRepository medicalTestFamilyRepository;
    private final MedicalSubTestRepository medicalSubTestRepository;
    private final LabRepository labRepository;

    @Autowired
    public MedicalTestService(MedicalSubTestRepository medicalSubTestRepository,
                              MedicalTestFamilyRepository medicalTestFamilyRepository,
                              LabRepository labRepository) {
        this.medicalSubTestRepository = medicalSubTestRepository;
        this.medicalTestFamilyRepository = medicalTestFamilyRepository;
        this.labRepository = labRepository;
    }

    public MedicalTestFamily createTestFamily(@NonNull String name,@NonNull List<MedicalSubTest> subTests) {
        MedicalTestFamily family = new MedicalTestFamily();
        family.setName(name);
        family.setSubTests(medicalSubTestRepository.saveAll(subTests));
        return medicalTestFamilyRepository.save(family);
    }

    public List<MedicalTestFamily> getAllMedicalTests() {
        return medicalTestFamilyRepository.findAll();
    }

    public void addTestFamilyToLab(Long testId, Laboratory authLab) throws Exception {
        Optional<MedicalTestFamily> family = medicalTestFamilyRepository.findById(testId);
        if (family.isEmpty()) {
            throw new Exception("id not found");
        }
        authLab.addTestFamily(family.get());
        labRepository.save(authLab);
    }

    public void removeTestFamilyFromLab(Long testId, Laboratory authLab) throws Exception {
        Optional<MedicalTestFamily> family = medicalTestFamilyRepository.findById(testId);
        if (family.isEmpty()) {
            throw new Exception("id not found");
        }
        authLab.removeTestFamily(family.get());
        labRepository.save(authLab);
    }
}