package model;

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
