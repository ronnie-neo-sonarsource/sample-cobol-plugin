package org.sonarsource.plugins.cobolexample;

import org.sonar.api.Plugin;

public class CustomCobolCheck implements Plugin {
    @Override
    public void define(Context context) {
        context.addExtension(CustomCobolCheckRepository.class);
    }
}