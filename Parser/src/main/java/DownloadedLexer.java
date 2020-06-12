import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class DownloadedLexer {
    public int currentLexemesIndex = 0;
    String STRING_LITERAL_REGEX = "\".*?\"";
    String VARIABLE_REGEX = "([a-zA-Z]+[\\w]*[ ])";
    String NUMBER_REGEX = "[+\\-]?[\\d]+";
    private List<String> lexemesArray = new ArrayList<>();
    private char[] SYMBOLS = {
            '#', '=', ';', '{', '}', '(', ')', '<', '>', '%', '\\', '+',
    };
    private String[] KEYWORDS = {
            "int", "char", "return", "include", "stdio.h", "main", "printf", "while", "do", "if", "else", "double", "float", "bool", "for"
    };

    private List<Token> tokens = new ArrayList<>();

    public DownloadedLexer() {


         //System.out.println("\n\n");

        populateLexemesArray();
        readFile();

         //System.out.println("Char Id = " + indexOfID("x1"));
        printLexemesArray();
    }

    public List<Token> getTokens() {
        return tokens;
    }

    private void populateLexemesArray() {

        for (String s : KEYWORDS) {
            String[] stringArray = s.split("");
            lexemesArray.addAll(Arrays.asList(stringArray));
            lexemesArray.add("\\s");
        }
    }

    private String addVariableToLexemes(String s) {
        String[] stringArray = s.replaceAll(" ", "").split("");
        lexemesArray.addAll(Arrays.asList(stringArray));
        lexemesArray.add("\\s");

        return String.valueOf(lexemesArray.size() - (stringArray.length + 1));
    }

    private int indexOfID(String id) {
        String[] idArray = id.split("");
        int initialIndex = 0;
        boolean found = false;

        for (int i = 0; i < idArray.length; i++) {
            String x = idArray[i];
            for (int j = 0; j < lexemesArray.size(); j++) {
                String y = lexemesArray.get(j);
                if (x.equals(y)) {
                    found = true;
                    initialIndex = j;
                    for (int a = j + 1; a < lexemesArray.size(); a++) {
                        if (idArray[i + 1].equals(lexemesArray.get(j + 1))) {
                            return initialIndex;
                        } else {
                            found = false;

                        }
                    }
                }

            }

        }
        if (!found) {
            return -1;
        } else {
            return initialIndex;
        }

    }


    private void readFile() {
        Scanner sc;
        String text;
        try {
            sc = new Scanner(new File("src/main/resources/text.txt"));
            int line = 1;
            while (sc.hasNextLine()) {
                text = sc.nextLine();
                tokenize(text, line);
                line++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void printLexemesArray() {
         //System.out.println("\n\nLexemes Array");
        for (String lexeme : lexemesArray) {
             //System.out.printf(" %s ,", lexeme);
        }
    }


    private void tokenize(String s, int line_number) {

        String line = String.valueOf(line_number);
        char[] charArray = s.toCharArray();

        String currentString = "";
        for (char c : charArray) {
            //After a token has been identified, the space should be ignored.
            if (c == ' ') {
                if (!currentString.equals("")) currentString = currentString.concat(String.valueOf(c));
            } else
                currentString = currentString.concat(String.valueOf(c));

            if (currentString.length() == 1) {
                if (contains(SYMBOLS, currentString.charAt(0))) {
                    printToken(currentString, "OPERATOR", line);
                    currentString = "";
                }
            } else {
                if (contains(KEYWORDS, currentString)) {
                    printToken(currentString, "KEYWORD", line);
                    currentString = "";
                } else {
                    if ((Pattern.compile(STRING_LITERAL_REGEX).matcher(currentString).matches())) {
                        printToken(currentString, "String", line);
                        currentString = "";
                    } else if ((Pattern.compile(NUMBER_REGEX).matcher(currentString).matches())) {
                        printToken(currentString, "NUMBER", line);
                        currentString = "";
                    } else if (Pattern.compile(VARIABLE_REGEX).matcher(currentString).matches()) {
                        int id = indexOfID(currentString);
                        if (id == -1) {
                            printToken(currentString, "VARIABLE", addVariableToLexemes(currentString));
                        } else {
                            printToken(currentString, "VARIABLE", String.valueOf(id));
                        }

                        currentString = "";
                    }
                }
            }

        }

    }


    private boolean contains(String[] array, String character) {
        boolean found = false;
        for (String string : array) {
            if (string.equals(character)) {
                found = true;
            }
        }
        return found;
    }


    private boolean contains(char[] array, char character) {
        boolean found = false;
        for (char string : array) {
            if (string == character) {
                found = true;
            }
        }
        return found;
    }

    private void printToken(String token, String type, String line) {

        token = token.replaceAll("\\s+","");

        if (type.equals("NUMBER") || type.equals("String"))
            tokens.add(new Token(token, Token.lexemes.LITERAL));
        if (token.equals("main"))
            tokens.add(new Token(token, Token.lexemes.IDENTIFIER));
        else if (token.equals("int") || token.equals("double") || token.equals("char") || token.equals("float") || token.equals("bool"))
            tokens.add(new Token(token, Token.lexemes.TYPE));
        else if (contains(KEYWORDS, token))
            tokens.add(new Token(token));
        else if (type.equals("VARIABLE"))
            tokens.add((new Token(token, Token.lexemes.IDENTIFIER)));
        else tokens.add(new Token(token));

    }


}