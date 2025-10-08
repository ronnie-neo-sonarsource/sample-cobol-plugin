package org.sonarsource.plugins.cobolexample.rules;

import com.sonarsource.cobol.testing.checks.CobolCheckVerifier;
import java.io.File;
import org.junit.Test;

public class MoveBitCheckTest {

  @Test
  public void testVisitNode() {
    MoveBitCheck check = new MoveBitCheck();
    CobolCheckVerifier.verify(
      new File("src/test/resources/files/MoveBitCheck.cbl"),
      check);
  }

}
