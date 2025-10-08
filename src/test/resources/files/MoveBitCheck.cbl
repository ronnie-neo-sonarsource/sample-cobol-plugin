       IDENTIFICATION DIVISION.
       PROCEDURE DIVISION.
           MOVE 'Repent, Harlequin!'  *> Noncompliant
                TO WS-ORIGINAL-VALUE
           MOVE  18 TO WS-ORIGINAL-LENGTH *> Noncompliant
           DISPLAY SPACE 
           DISPLAY 'Example 1' 
           DISPLAY 'Result of MOVE ''Repent, Harlequin!'' '
                'to item defined as PIC X(...)'
           DISPLAY 'Text value: ' 
                '"' WS-ORIGINAL-VALUE(1:WS-ORIGINAL-LENGTH) '"'   