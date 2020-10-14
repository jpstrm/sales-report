package br.com.sales.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.io.File;
import java.util.Objects;

public class FolderCondition implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String path = System.getProperty(
                Objects.requireNonNull(conditionContext.getEnvironment().getProperty("config.path")))
                + conditionContext.getEnvironment().getProperty("config.path-in");
        File folder = new File(path);
        return folder.exists();
    }
}
