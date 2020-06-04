package com.jeremyvinding.hexagonal;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.jeremyvinding.hexagonal.application.handlers.PostUseCaseHandler;
import com.jeremyvinding.hexagonal.domain.model.Author;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption.Predefined;
import com.tngtech.archunit.core.importer.ImportOptions;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

import org.junit.jupiter.api.Test;

public class HexagonArchUnitTest {
  private static final String AUTHOR_CLASS = Author.class.getName();
  ArchCondition<JavaClass> mustContainAParameterOfTypeAuthor = new ArchCondition<JavaClass>(
      "must have parameter of type '" + AUTHOR_CLASS + "'") {

    @Override
    public void check(JavaClass item, ConditionEvents events) {
      item.getMethods().stream().filter(method -> !method.getRawParameterTypes().getNames().contains(AUTHOR_CLASS))
          .forEach(method -> events
              .add(SimpleConditionEvent.violated(method, "Method " + method.getFullName() + " must accept an Author")));
    }
  };

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

  @Test
  void postUseCasesMustHaveAuthorParam() {
    var importedClasses = new ClassFileImporter(new ImportOptions().with(Predefined.DO_NOT_INCLUDE_TESTS))
        .importPackages("com.jeremyvinding.hexagonal");
    var rule = classes().that().implement(PostUseCaseHandler.class).should(mustContainAParameterOfTypeAuthor);
    rule.check(importedClasses);
  }
}
