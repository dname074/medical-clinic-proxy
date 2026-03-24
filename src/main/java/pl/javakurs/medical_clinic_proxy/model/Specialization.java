package pl.javakurs.medical_clinic_proxy.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Specialization {
    PSYCHIATRIST,
    SURGEON,
    DERMATOLOGIST,
    CARDIOLOGIST
}
