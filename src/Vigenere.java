import java.io.*;
import java.util.Scanner;
import java.util.Vector;

public class Vigenere {
    private String file_name;
    private String password;
    private String outputFileName;

    private char[] tab;
    private Vector<String> inputs = new Vector<String>();
    private Vector<String> encodedWithPassword = new Vector<>();
    private Vector<String> encodedText = new Vector<>();

    public Vigenere(String file_name, String password) throws IOException {
        this.file_name = file_name;
        this.password = password;
        this.inputs.clear();
        this.encodedWithPassword.clear();
        this.encodedText.clear();

        this.tab = new char[36];
        this.tab[0] = '0';
        this.tab[1] = '1';
        this.tab[2] = '2';
        this.tab[3] = '3';
        this.tab[4] = '4';
        this.tab[5] = '5';
        this.tab[6] = '6';
        this.tab[7] = '7';
        this.tab[8] = '8';
        this.tab[9] = '9';
        this.tab[10] = 'A';
        this.tab[11] = 'B';
        this.tab[12] = 'C';
        this.tab[13] = 'D';
        this.tab[14] = 'E';
        this.tab[15] = 'F';
        this.tab[16] = 'G';
        this.tab[17] = 'H';
        this.tab[18] = 'I';
        this.tab[19] = 'J';
        this.tab[20] = 'K';
        this.tab[21] = 'L';
        this.tab[22] = 'M';
        this.tab[23] = 'N';
        this.tab[24] = 'O';
        this.tab[25] = 'P';
        this.tab[26] = 'Q';
        this.tab[27] = 'R';
        this.tab[28] = 'S';
        this.tab[29] = 'T';
        this.tab[30] = 'U';
        this.tab[31] = 'V';
        this.tab[32] = 'W';
        this.tab[33] = 'X';
        this.tab[34] = 'Y';
        this.tab[35] = 'Z';
    }

    //=========================================================================================
    // encode:

    public void algorithmEncode() throws IOException {
        readFile();
        readPassword();
        encodeWithPassword();
        showEncodedWithPassword();
        encodeSecond();
        showEncodedText();
        saveToFile(encodedText);
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    private void readFile() throws IOException {
        FileInputStream inputStream = null;
        Scanner sc = null;

        try {
            inputStream = new FileInputStream(file_name);
            sc = new Scanner(inputStream, "UTF-8");

            // read all the lines from input file and put them into vector inputs:
            while (sc.hasNextLine()) {
                // read next line:
                String line = sc.nextLine();
                // tmp string to convert signs to uppercase:
                String tmp = "";

                // convert all signs to uppercase:
                tmp = line.toUpperCase();

                System.out.println(tmp);

                // add converted string to vector inputs:
                inputs.add(tmp);
            }

            // note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (sc != null) {
                sc.close();
            }
        }
    }

    private void readPassword() throws IOException {
        // read next line:
        String line = password;
        // tmp string to convert signs to uppercase:
        String tmp = "";

        // convert all signs to uppercase:
        tmp = line.toUpperCase();

        // remove special characters from String:
        for (int i = 0; i < tmp.length(); i++) {
            // get tmp_char
            String tmp_char = String.valueOf(tmp.charAt(i));

            // if table contains special character:
            if (isInTable(tmp.charAt(i)) == false) {
                tmp =  tmp.replace(tmp_char, "");
                // decrease index to check once again after removing a character:
                i--;
            }
        }

        System.out.println("-> password: " + tmp);

        // finally set the password:
        password = tmp;
    }

    private boolean isInTable(char letter) {
        for (int i = 0; i < tab.length; i++) {
            // if there is this letter in tab, return true:
            if (tab[i] == letter) {
                return true;
            }
        }
        // if not:
        return false;
    }

    private void encodeWithPassword() {
        int counter = 0;

        // for all lines of input text:
        for (int i = 0 ; i < inputs.size(); i++) {
            String addToEncodedWithPasswordVector = "";

            // for every character in line:
            for (int j = 0; j < inputs.elementAt(i).length(); j++) {
                // catch the current character:
                char tmp = inputs.elementAt(i).charAt(j);

                // if it's not a special character (this character exists in table):
                if(isInTable(tmp) == true) {

                    // if reached last character of password, start from the beginning:
                    if(counter == password.length()){
                        counter = 0;
                    }

                    // encode with the current character from password:
                    char _char = password.charAt(counter);
                    String _string = String.valueOf(_char);

                    // add to string:
                    addToEncodedWithPasswordVector += _string;

                    // increment the counter:
                    counter++;
                }
                // if it is a special character, just add it without any changes:
                else {
                    String character = String.valueOf(tmp);
                    addToEncodedWithPasswordVector += character;
                }
            }
            // add the whole String after looping a row:
            encodedWithPassword.add(addToEncodedWithPasswordVector);
        }
    }

    public void showEncodedWithPassword() {
        System.out.println("-> showEncodedWithPassword:");

        for (int i = 0; i < encodedWithPassword.size(); i++) {
            System.out.println(encodedWithPassword.elementAt(i));
        }
        System.out.println();
    }

    private void encodeSecond() {
        // for all lines of input text:
        for (int i = 0; i < inputs.size(); i++) {
            String addToEncoedTextVector = "";

            // for every character in line:
            for (int j = 0; j < inputs.elementAt(i).length(); j++) {
                // catch the current character:
                char tmp1 = inputs.elementAt(i).charAt(j);
                char tmp2 = encodedWithPassword.elementAt(i).charAt(j);

                // if it's not a special character (this character exists in table):
                if (isInTable(tmp1) == true) {
                    // get the number of this letter in pre-defined tab of characters:
                    int x = getPositionOfCharInTable(tab, tmp1);

                    // get the position (column and row):
                    int position1 = getPositionOfCharInTable(tab, tmp1);
                    int position2 = getPositionOfCharInTable(tab, tmp2);

                    // index to make a translation in Caesar's code:
                    int index = position1 + position2;

                    // just in case it didn't work:
                    while (index > tab.length) {
                        index -= tab.length;
                    }

                    //   use the Caesar's code:
                    // calculate the En - index of the encoded letter:
                    int En = index % tab.length; // mod 36

                    // add this encoded letter to output vector:
                    String _tmp = String.valueOf(tab[En]);

                    // add to string:
                    addToEncoedTextVector += _tmp;
                }
                // if it is a special character, just add it without any changes:
                else {
                    String character = String.valueOf(tmp1);
                    addToEncoedTextVector += character;
                }
            }
            // add the whole string after looping the row:
            encodedText.add(addToEncoedTextVector);
        }
    }

    private void saveToFile(Vector<String> tab) throws IOException {
        File fout = new File(outputFileName);
        FileOutputStream fos = new FileOutputStream(fout);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        for (int i = 0; i < tab.size(); i++) {
            bw.write(tab.elementAt(i));
            bw.newLine();
        }

        bw.close();
    }

    private int getPositionOfCharInTable(char[] tab, char letter) {
        int position = 0;

        // iterate through the tab to find the position of the current letter:
        for (int i = 0; i < tab.length; i++) {
            if (tab[i] == letter) {
                break;
            } else {
                position++;
            }
        }

        return position;
    }

    public void showEncodedText() {
        System.out.println("-> Encoded text:");
        for (int i = 0; i < encodedText.size(); i++) {
            System.out.println(encodedText.elementAt(i));
        }
    }

    //=========================================================================
    // decode:

    public void algorithDecode() throws IOException {
        readFile();
        readPassword();
        reversePassword();
        //showReversedPassword();
        encodeWithPassword();
        encodeSecond();
        showEncodedText();
        saveToFile(encodedText);
    }

    public void algorithDecode2() throws IOException {
        readFile();
        readPassword();
        encodeWithPassword();
        decode();
        saveToFile(encodedText);
    }

    private void reversePassword() {
        // initialize String output with password String:
        String output = password;
        StringBuilder stringBuilder = new StringBuilder(output);

        for (int i = 0; i < stringBuilder.toString().length(); i++) {
            // get the position in table of this current letter:
            int position = getPositionOfCharInTable(tab, stringBuilder.toString().charAt(i));

            // calculate index (K2[i]):
            int index = (tab.length - position) % tab.length;

            // get the position of searched letter in password:
            int positionInPassword = getPositionInPassword(stringBuilder.toString(), password.charAt(i));

            // replace this character of password:
            stringBuilder.setCharAt(positionInPassword, tab[index]);
        }
        //System.out.println("-> Reversed password: " + stringBuilder.toString());

        // change password with output of this function:
        password = stringBuilder.toString();

        //System.out.println("-> Reversed password: " + password);
    }

    public void showReversedPassword() {
        System.out.println("-> Reversed password:");
        for (int i = 0; i < password.length(); i++) {
            System.out.println(password.charAt(i));
        }
    }

    private int getPositionInPassword(String password, char letter){
        int position = 0;

        // iterate through the password to find the position of the searched letter:
        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) == letter) {
                break;
            } else {
                position++;
            }
        }

        return position;
    }

    private void decode() {
        // for all lines of input text:
        for (int i = 0; i < inputs.size(); i++) {
            String addToEncoedTextVector = "";

            // for every character in line:
            for (int j = 0; j < inputs.elementAt(i).length(); j++) {
                // catch the current character:
                char tmp1 = inputs.elementAt(i).charAt(j);
                char tmp2 = encodedWithPassword.elementAt(i).charAt(j);

                // if it's not a special character (this character exists in table):
                if (isInTable(tmp1) == true) {
                    // get the number of this letter in pre-defined tab of characters:
                    int x = getPositionOfCharInTable(tab, tmp1);

                    // get the position (column and row):
                    int position1 = getPositionOfCharInTable(tab, tmp1);
                    int position2 = getPositionOfCharInTable(tab, tmp2); // koluma slowa kluczowego:

                    int index = position1 - position2;

                    // just in case it didn't work:
                    while (index < 0) {
                        index += tab.length;
                    }

                    //   use the Caesar's code:
                    // calculate the En - index of the encoded letter:
                    int En = index % tab.length; // mod 36

                    // add this encoded letter to output vector:
                    String _tmp = String.valueOf(tab[En]);

                    // add to string:
                    addToEncoedTextVector += _tmp;

                }
                // if it is a special character, just add it without any changes:
                else {
                    String character = String.valueOf(tmp1);
                    addToEncoedTextVector += character;
                }
            }
            // add the whole string after looping the row:
            encodedText.add(addToEncoedTextVector);
        }
    }

}
