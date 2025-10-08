package org.sonarsource.plugins.cobolexample;

import com.sonarsource.cobol.api.ast.CobolCheckRepository;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionAnnotationLoader;

import org.sonarsource.plugins.cobolexample.rules.ForbiddenCall;
import org.sonarsource.plugins.cobolexample.rules.MoveBitCheck;

/**
 * Extension point to list all your custom Cobol rules.
 */
public class CustomCobolCheckRepository implements CobolCheckRepository, RulesDefinition {

    // Change key and name to reflect your company rule repository.
    // Don't use "cobol" key, it's the core repository.
    private static final String REPOSITORY_KEY = "custom-cobol-rules";
    private static final String REPOSITORY_NAME = "My Custom Cobol Analyzer";

    // Must be "cobol"
    private static final String REPOSITORY_LANGUAGE = "cobol";

    @Override
    public String getRepositoryKey() {
        return REPOSITORY_KEY;
    }

    @Override
    public Collection<Object> getCheckClassesOrObjects() {
        return Arrays.asList(
        ForbiddenCall.class,
        MoveBitCheck.class);
    }

    @Override
    public void define(Context context) {
        // Create the custom rule repository
        NewRepository repository = context.createRepository(REPOSITORY_KEY, REPOSITORY_LANGUAGE).setName(REPOSITORY_NAME);

        // Load rule meta data from annotations
        RulesDefinitionAnnotationLoader annotationLoader = new RulesDefinitionAnnotationLoader();

        getCheckClassesOrObjects().stream()
        .map(Class.class::cast)
        .forEach(ruleClass -> annotationLoader.load(repository, ruleClass));

        // Optionally override html description from annotation with content from html files
        repository.rules().forEach(rule -> rule.setHtmlDescription(loadResource("/org/sonar/l10n/cobol/rules/cobol/" + rule.key() + ".html")));


        // Optionally define remediation costs
        Map<String,String> remediationCosts = new HashMap<>();
        remediationCosts.put("ForbiddenCall", "5min");
        remediationCosts.put("MoveBitCheck", "25min");
        repository.rules().forEach(rule -> rule.setDebtRemediationFunction(
        rule.debtRemediationFunctions().constantPerIssue(remediationCosts.get(rule.key()))));

        // Save changes
        repository.done();
    }

    private String loadResource(String path) {
        URL resource = getClass().getResource(path);
        if (resource == null) {
            throw new IllegalStateException("Resource not found: " + path);
        }
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        try (InputStream in = resource.openStream()) {
            byte[] buffer = new byte[1024];
            for (int len = in.read(buffer); len != -1; len = in.read(buffer)) {
                result.write(buffer, 0, len);
            }
            return new String(result.toByteArray(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read resource: " + path, e);
        }
    }

}
