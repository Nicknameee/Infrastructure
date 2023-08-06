package io.management.ua.utility.certification.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificationResultModel {
    private String identifier;
    private boolean certified;
}
