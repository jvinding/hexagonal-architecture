package com.jeremyvinding.hexagonal;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption.Predefined;
import com.tngtech.archunit.core.importer.ImportOptions;

import org.junit.jupiter.api.Test;

public class HexagonArchUnitTest {

  @Test
  void domainDoesNotDependOnOtherLayers() {
    var importedClasses = new ClassFileImporter(new ImportOptions().with(Predefined.DO_NOT_INCLUDE_TESTS))
        .importPackages("com.jeremyvinding.hexagonal");
    var rule = classes().that().resideInAPackage("..domain..").should().onlyDependOnClassesThat()
        .resideInAnyPackage("..domain..", "java..", "javax..", "lombok..");
    rule.check(importedClasses);
  }

  @Test
  void applicationLayerCanOnlyDependOnDomainAndPorts() {
    var importedClasses = new ClassFileImporter(new ImportOptions().with(Predefined.DO_NOT_INCLUDE_TESTS))
        .importPackages("com.jeremyvinding.hexagonal");
    var rule = classes().that().resideInAPackage("..application..").should().onlyDependOnClassesThat()
        .resideInAnyPackage("..domain..", "..application..", "..hexagonal.ports..", "java..", "javax..", "lombok..",
            "org.springframework.context.annotation", "org.springframework.stereotype");
    rule.check(importedClasses);
  }
}
