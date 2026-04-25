package model;

public record SimpleDoctor(
        SimpleUser user,
        Specialization specialization
) {
}
