package org.sonarsource.plugins.cobolexample.rules;

import com.sonar.sslr.api.AstNode;
import com.sonarsource.cobol.api.ast.CobolCheck;
import java.util.List;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

// These attributes need to be updated to reflect your custom rule
// お客様のカスタムルールを反映するため、これらの属性を更新する必要があります。
@Rule(
    key = "MoveBitCheck",
    name = "Move statements should have same length source and destination", 
    priority = Priority.MINOR, 
    tags = {"bad-practice"}) 

public class MoveBitCheck extends CobolCheck {

    @Override
    public void init() {

        /* 
        This is inform this custom rule to only list to Move statements
        Visit this doc for full grammar list
        https://javadocs.sonarsource.org/cobol/apidocs/5.9.0.8697/com/sonarsource/cobol/api/CobolGrammar.html

        このカスタムルールは、「Move」ステートメントのみをリストアップします。
        すべての文法リストについては、こちらのドキュメントをご覧ください。
        https://javadocs.sonarsource.org/cobol/apidocs/5.9.0.8697/com/sonarsource/cobol/api/CobolGrammar.html
        */
        subscribeTo(getCobolGrammar().moveStatement);
    }

    @Override
    public void visitNode(AstNode evaluateNode) {
        /*
        This get executed when the parser found a Move statement
        パーサーが「Move」ステートメントを見つけたときに、これが実行されます。
        */
        AstNode moveFrom = evaluateNode.getChild(1);
        AstNode moveTo = evaluateNode.getLastChild();
        
        /* 
        This is a very simplistic check, you can use different methods available 
        in AstNode as well as just pulling the raw value to perform your own logic
        これは非常に単純なチェックです。AstNodeで利用可能なさまざまなメソッドを使用したり、
        生の値を引き出して独自のロジックを実行したりすることができます。    
        */
        if (moveFrom.getTokenValue().length() != moveTo.getTokenValue().length()) {
            reportIssue("Change this MOVE statement to have matching length on destination and source.").on(evaluateNode.getToken());
        }
    }

}
