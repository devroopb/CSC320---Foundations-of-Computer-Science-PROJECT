CC320 Sudoku Project
by Jonah Rankin and Cameron Long

==== Description ====
There are two python programs:
    1. generation.py - takes a puzzle file and outputs the 
                       logical statements.
    2. generation-alt.py - A more efficeint encoding of generation.py
    3. results_processing.py - converts the results of the miniSat 
                               solver to a human readable format.

==== Puzzle Formats ====
The ganeration program takes one argument, the puzzle file. The puzzle file
is a plain text file. The digits 1 to 9 are recognized as given cells.
Spaces, new lines, -, | and +  are ignored. All other characters are valid as unfilled
cells. A puzzle file can only contain one puzzle

The following four encodings are valid and equivalent. 
Note:   - The characters . j x ? * 0 are all interpreted as blank cells 
        - The spaces newlines - + | are all ignored


Encoding 1:
85...24..72...x..9..4..?......1.7..23.5...9...4...j.......80.7..17..*....?..36.4.

Encoding 2:
85...24..
72...x..9
..4..?...
...1.7..2
3.5...9..
.4...j...
....80.7.
.17..*...
.?..36.4.

Encoding 3:
85.  ..2  4..
72.  ..x  ..9
..4  ..?  ...

...  1.7  ..2
3.5  ...  9..
.4.  ..j  ...

...  .80  .7.
.17  ..*  ...
.?.  .36  .4.

Encoding 4:
85. | ..2 | 4..
72. | ..x | ..9
..4 | ..? | ...
--- + --- + ---
... | 1.7 | ..2
3.5 | ... | 9..
.4. | ..j | ...
--- + --- + ---
... | .80 | .7.
.17 | ..* | ...
.?. | .36 | .4.

==== Usage ====
There are two steps to solving a puzzle, generating the input for miniSat 
and formatting the output in a human readable format.

The generation program prints to stdout. To use this with miniSat it is 
necessary to either create temporary files or pipe the output into miniSat.

Option 1: Temporary Files

    python generation.py > input_clauses.txt
    minisat input_clauses.txt results.txt

Option 2: Piping into miniSat 
    Note:   The odd appearance of the piping is due miniSat requiring the 
            output file, results.txt, to also be supplied as an argument.
            This may not work in some shells.

    minisat <(python generation.py) results.txt


To interpret the output of miniSat use the results_processing.py program.
The program outputs to stdout.

    python result_processing.py results.txt


==== Alternate Encoding ====
The program generation-alt.py is a modification to the "Minimal Encoding" scheme introduced in class. There are several criteria specified in the sub-grid encoding that were deemed redundant due to the presence of the row and column requirements. See the attached report for further details.  Usage of the alternative version is identical to the above description.
