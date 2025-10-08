package org.sonarsource.plugins.cobolexample.rules;

import com.sonarsource.cobol.testing.checks.CobolCheckVerifier;
import java.io.File;
import org.junit.Test;

public class ForbiddenCallTest {

  @Test
  public void testVisitNode() {
    ForbiddenCall check = new ForbiddenCall();
    CobolCheckVerifier.verify(
      new File("src/test/resources/files/ForbiddenCall.cbl"),
      check);
  }

}
