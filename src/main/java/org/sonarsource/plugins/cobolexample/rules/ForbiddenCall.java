package org.sonarsource.plugins.cobolexample.rules;

import com.sonar.sslr.api.AstNode;
import com.sonarsource.cobol.api.ast.CobolCheck;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

@Rule(
  key = "ForbiddenCall",
  name = "CALL statements should not use internal programs",
  priority = Priority.CRITICAL,
  tags = {"bug"})
public class ForbiddenCall extends CobolCheck {

    @Override
    public void init() {
        subscribeTo(getCobolGrammar().callStatement);
    }

    @Override
    public void visitNode(AstNode callStatementNode) {
        AstNode calledProgramNode = callStatementNode.getFirstChild(getCobolGrammar().literal);
        if (calledProgramNode != null && calledProgramNode.getTokenValue().indexOf("FORBIDDEN") > -1) {
            reportIssue("CALL to this program are forbidden.").on(calledProgramNode);
        }
    }

}