#!/bin/python

"""
CSC320 Project
Jonah Rankin (VOO808910)
Cameron Long (V00748439)
"""

import sys


"""
The following function are small helpers for converting
from readable format to SAT encoded (dcmi)
"""


def minus1_all(triple):
    if triple == "0":
        return "0"
    conv_triple = ""
    for char in triple:
        conv_triple = conv_triple + str((int(char)-1))
    return conv_triple


def nonal2dec_triple(triple):
    if(triple == "0"):
        return "0"
    return str(int(triple, 9)).zfill(3)


def plus1(triple):
    if(triple == "0"):
        return "0"
    return str(int(triple)+1).zfill(3)


def minus1(triple):
    if(triple == "0"):
        return "0"
    return str(int(triple)-1).zfill(3)


def base9(decimal):
    if decimal == 0:
        return 0
    sign = 1
    if decimal < 0:
        sign = -1
        decimal *= -1
    digits = []
    while decimal > 0:
        digits.append(decimal % 9)
        decimal /= 9

    digits.reverse()
    ret = "".join(map(str, digits))
    return int(ret) * sign


def dec2nonal_triple(triple):
    if(triple == "0"):
        return "0"
    return str(base9(int(triple))).zfill(3)


def plus1_all(triple):
    if triple == "0":
        return "0"
    conv_triple = ""
    for char in triple:
        conv_triple = conv_triple + str((int(char)+1))
    return conv_triple


def stringify(splits):
    return " ".join(splits)


# Applied dcmi notation negation to each variable in a clause
# 123 414 222 0 ==> -123 -414 -222 0
# NOTE this is not the logical negation of a clause. It is just the negation of each term.
def negate_clause(clause):
    st = clause.split(" ")
    cl = []
    for split in st:
        if split == "0":
            break
        cl.append("-"+split)
    cl.append("0")
    return stringify(cl)


"""
Conversion Functions
"""


# converts values from readable notation to base9 notation
# 1. each digit reduced by one
# 2. values are converted form base 9 to decimal
# 3. add one. Solver's notation starts at 1
# eg.  Cell (4,5) has value 6 =[encoded as]=> 456   NOTE: each digit range is between 1 and 9
#           1. 456 => 345                           NOTE: digit range now between 0 and 8
#           2. 345 base nine in base 10 is: 3*81 + 4*9 + 5 = 284
#           3. Add one: 285  this is the value used by the SAT solver
def dcmi_format(line):
    splits = line.split(" ")
    splits = [minus1_all(split) for split in splits]
    splits = [nonal2dec_triple(split) for split in splits]
    splits = [plus1(split) for split in splits]
    return stringify(splits)


# Converts from dcmi format to readable format
# reverse process as dmci_format
def readable_format(line):
    splits = line.split(" ")
    splits = [minus1(split) for split in splits]
    splits = [dec2nonal_triple(split) for split in splits]
    splits = [plus1_all(split) for split in splits]
    return stringify(splits)


"""
SAT variable printing functions
"""


# The header of a SAT file.
# the file type (cnf), the number of variables and the number of clauses
def print_header():
    print "c cnf is file type, first num is variables, second num is clauses"
    print "p cnf 729 8577"


# Prints the clauses for a problems given cells
def print_given_cells_requirement():
    # Encoding for input cells

    try:
        # Open a file
        fo = open(str(sys.argv[1]), "rw+")
    except:
        print "No Arguments: provide a puzzle file"
        sys.exit(1)

    line = fo.read()
    encoding = ""
    i = 1
    j = 1
    line = line.replace('\n', '')
    line = line.replace(' ', '')
    line = line.replace('-', '')
    line = line.replace('+', '')
    line = line.replace('|', '')
    for elem in line:
        if elem in "123456789":
            encoding += "{}{}{} ".format(i, j, elem)
        j += 1
        if j > 9:
            i += 1
            j = 1

    line = encoding[0:len(encoding)-1]
    print "\nc Given cells"
    # line is the values of the cells in readable format for a particular sudoku problem
    # line = "146 164 179 197 225 237 253 323 341 392 412 438 469 491 521 587 616 645 674 693 711 765 782 851 876 889 915 934 947 962"
    converted_line = dcmi_format(line)
    splits = converted_line.split(" ")
    for split in splits:
        print split + " 0"


# Prints the clauses for the requirement of each cell needing a value
def print_cell_requirement():
    print "c Output for first criteria. Each square needs a number."
    term = []
    for i in range(1, 10):
        for j in range(1, 10):
            clause = []
            for k in range(1, 10):
                clause.append("{}{}{}".format(i, j, k))
            clause.append("0")
            term.append(clause)

    for clause in term:
        print dcmi_format(stringify(clause))


# Prints the clauses for each row needing a unique value
def print_row_requirement():
    print ""
    print "c Output for second criteria. Each row is unique."
    term = []
    for i in range(1, 10):
        for k in range(1, 10):
            for j in range(1, 9):
                for l in range(j+1, 10):
                    clause = []
                    clause.append("{}{}{}".format(i, j, k))
                    clause.append("{}{}{}".format(i, l, k))
                    clause.append("0")
                    term.append(clause)

    for clause in term:
        st = dcmi_format(stringify(clause))
        print negate_clause(st)


# Prints the clauses for each column needing a unique value
def print_col_requirement():
    print ""
    print "c Output for the third criteria. Each column is unique"
    term = []
    for j in range(1, 10):
        for k in range(1, 10):
            for i in range(1, 9):
                for l in range(i+1, 10):
                    clause = []
                    clause.append("{}{}{}".format(i, j, k))
                    clause.append("{}{}{}".format(l, j, k))
                    clause.append("0")
                    term.append(clause)

    for clause in term:
        st = dcmi_format(stringify(clause))
        print negate_clause(st)


# Prints the clauses for each subgrid needing a unique value
def print_subgrid_requirement():
    print ""
    print "c Output for the fourth criteria. Each 3x3 cell is unique"
    term = []
    for k in range(1, 10):
        for a in range(0, 3):
            for b in range(0, 3):
                for u in range(1, 4):
                    for v in range(1, 3):
                        for w in range(v+1, 4):
                            clause = []
                            clause.append("{}{}{}".format(3*a+u, 3*b+v, k))
                            clause.append("{}{}{}".format(3*a+u, 3*b+w, k))
                            clause.append("0")
                            term.append(clause)

    for clause in term:
        st = dcmi_format(stringify(clause))
        print negate_clause(st)

    term = []
    for k in range(1, 10):
        for a in range(0, 3):
            for b in range(0, 3):
                for u in range(1, 3):
                    for v in range(1, 4):
                        for w in range(u+1, 4):
                            for t in range(1, 4):
                                clause = []
                                clause.append("{}{}{}".format(3*a+u, 3*b+v, k))
                                clause.append("{}{}{}".format(3*a+w, 3*b+t, k))
                                clause.append("0")
                                term.append(clause)

    for clause in term:
        st = dcmi_format(stringify(clause))
        print negate_clause(st)


def main():
    print_header()

    print_given_cells_requirement()

    print_cell_requirement()

    print_row_requirement()

    print_col_requirement()

    print_subgrid_requirement()


if __name__ == '__main__':
    main()
