import java.util.Scanner;

public class CipherTechniques {
    
    // Caesar Cipher
    public static String caesarEncrypt(String plaintext, int shift) {
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i++) {
            char ch = plaintext.charAt(i);
            if (Character.isLetter(ch)) {
                char shifted = (char) (((ch - 'A' + shift) % 26) + 'A');
                ciphertext.append(shifted);
            } else {
                ciphertext.append(ch);
            }
        }
        return ciphertext.toString();
    }
    
    public static String caesarDecrypt(String ciphertext, int shift) {
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i++) {
            char ch = ciphertext.charAt(i);
            if (Character.isLetter(ch)) {
                char shifted = (char) (((ch - 'A' - shift + 26) % 26) + 'A');
                plaintext.append(shifted);
            } else {
                plaintext.append(ch);
            }
        }
        return plaintext.toString();
    }
    
    // Playfair Cipher
    public static String playfairEncrypt(String plaintext, String key) {
        char[][] keySquare = generateKeySquare(key);
        StringBuilder ciphertext = new StringBuilder();
        
        plaintext = prepareText(plaintext);
        
        for (int i = 0; i < plaintext.length(); i += 2) {
            char first = plaintext.charAt(i);
            char second = (i + 1 < plaintext.length()) ? plaintext.charAt(i + 1) : 'X';
            
            int[] pos1 = findPosition(keySquare, first);
            int[] pos2 = findPosition(keySquare, second);
            
            if (pos1[0] == pos2[0]) {
                ciphertext.append(keySquare[pos1[0]][(pos1[1] + 1) % 5]);
                ciphertext.append(keySquare[pos2[0]][(pos2[1] + 1) % 5]);
            } else if (pos1[1] == pos2[1]) {
                ciphertext.append(keySquare[(pos1[0] + 1) % 5][pos1[1]]);
                ciphertext.append(keySquare[(pos2[0] + 1) % 5][pos2[1]]);
            } else {
                ciphertext.append(keySquare[pos1[0]][pos2[1]]);
                ciphertext.append(keySquare[pos2[0]][pos1[1]]);
            }
        }
        
        return ciphertext.toString();
    }
    
    public static String playfairDecrypt(String ciphertext, String key) {
        char[][] keySquare = generateKeySquare(key);
        StringBuilder plaintext = new StringBuilder();
        
        ciphertext = prepareText(ciphertext);
        
        for (int i = 0; i < ciphertext.length(); i += 2) {
            char first = ciphertext.charAt(i);
            char second = (i + 1 < ciphertext.length()) ? ciphertext.charAt(i + 1) : 'X';
            
            int[] pos1 = findPosition(keySquare, first);
            int[] pos2 = findPosition(keySquare, second);
            
            if (pos1[0] == pos2[0]) {
                plaintext.append(keySquare[pos1[0]][(pos1[1] + 4) % 5]);
                plaintext.append(keySquare[pos2[0]][(pos2[1] + 4) % 5]);
            } else if (pos1[1] == pos2[1]) {
                plaintext.append(keySquare[(pos1[0] + 4) % 5][pos1[1]]);
                plaintext.append(keySquare[(pos2[0] + 4) % 5][pos2[1]]);
            } else {
                plaintext.append(keySquare[pos1[0]][pos2[1]]);
                plaintext.append(keySquare[pos2[0]][pos1[1]]);
            }
        }
        
        return plaintext.toString();
    }
    
    private static char[][] generateKeySquare(String key) {
        String filteredKey = key.toUpperCase().replaceAll("[^A-Z]", "");
        String plaintext = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
        
        char[][] keySquare = new char[5][5];
        String keyString = filteredKey + plaintext;
        
        int index = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                keySquare[i][j] = keyString.charAt(index);
                index++;
            }
        }
        
        return keySquare;
    }
    
    private static String prepareText(String plaintext) {
        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");
        plaintext = plaintext.replaceAll("J", "I");
        StringBuilder sb = new StringBuilder(plaintext);
        
        for (int i = 1; i < sb.length(); i += 2) {
            if (sb.charAt(i) == sb.charAt(i - 1)) {
                sb.insert(i, 'X');
            }
        }
        
        if (sb.length() % 2 != 0) {
            sb.append('X');
        }
        
        return sb.toString();
    }
    
    private static int[] findPosition(char[][] keySquare, char ch) {
        int[] result = new int[2];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (keySquare[i][j] == ch) {
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }
        return result;
    }
    
    // Vigenère Cipher
    public static String vigenereEncrypt(String plaintext, String key) {
        StringBuilder ciphertext = new StringBuilder();
        int keyLength = key.length();
        for (int i = 0; i < plaintext.length(); i++) {
            char plainChar = plaintext.charAt(i);
            char keyChar = key.charAt(i % keyLength);
            char shifted = (char) (((plainChar - 'A' + keyChar - 'A') % 26) + 'A');
            ciphertext.append(shifted);
        }
        return ciphertext.toString();
    }
    
    public static String vigenereDecrypt(String ciphertext, String key) {
        StringBuilder plaintext = new StringBuilder();
        int keyLength = key.length();
        for (int i = 0; i < ciphertext.length(); i++) {
            char cipherChar = ciphertext.charAt(i);
            char keyChar = key.charAt(i % keyLength);
            char shifted = (char) (((cipherChar - keyChar + 26) % 26) + 'A');
            plaintext.append(shifted);
        }
        return plaintext.toString();
    }
    
    // Vernam Cipher
    public static String vernamEncrypt(String plaintext, String key) {
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i++) {
            char plainChar = plaintext.charAt(i);
            char keyChar = key.charAt(i % key.length());
            char shifted = (char) (plainChar ^ keyChar);
            ciphertext.append(shifted);
        }
        return ciphertext.toString();
    }
    
    public static String vernamDecrypt(String ciphertext, String key) {
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i++) {
            char cipherChar = ciphertext.charAt(i);
            char keyChar = key.charAt(i % key.length());
            char shifted = (char) (cipherChar ^ keyChar);
            plaintext.append(shifted);
        }
        return plaintext.toString();
    }
    
    // Rail Fence Cipher
    public static String railFenceEncrypt(String plaintext, int rails) {
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < rails; i++) {
            for (int j = i; j < plaintext.length(); j += rails) {
                ciphertext.append(plaintext.charAt(j));
            }
        }
        return ciphertext.toString();
    }
    
    public static String railFenceDecrypt(String ciphertext, int rails) {
        int len = ciphertext.length();
        int rows = rails;
        int cols = len / rails;
        char[][] railMatrix = new char[rows][cols];
        int k = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                railMatrix[i][j] = ciphertext.charAt(k++);
            }
        }

        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                plaintext.append(railMatrix[j][i]);
            }
        }
        return plaintext.toString();
    }
    
    // Row-Columnar Transposition Cipher
    public static String rowColumnarEncrypt(String plaintext, String key) {
        int[] order = getOrder(key);
        int numRows = key.length();
        int numCols = (int) Math.ceil((double) plaintext.length() / numRows);
        char[][] grid = new char[numRows][numCols];
        
        int index = 0;
        for (int col = 0; col < numCols; col++) {
            for (int row = 0; row < numRows; row++) {
                if (index < plaintext.length()) {
                    grid[row][col] = plaintext.charAt(index++);
                } else {
                    grid[row][col] = ' ';
                }
            }
        }
        
        StringBuilder ciphertext = new StringBuilder();
        for (int row : order) {
            for (int col = 0; col < numCols; col++) {
                ciphertext.append(grid[row][col]);
            }
        }
        
        return ciphertext.toString();
    }
    
    public static String rowColumnarDecrypt(String ciphertext, String key) {
        int[] order = getOrder(key);
        int numRows = key.length();
        int numCols = (int) Math.ceil((double) ciphertext.length() / numRows);
        char[][] grid = new char[numRows][numCols];
        
        int index = 0;
        for (int row : order) {
            for (int col = 0; col < numCols; col++) {
                grid[row][col] = ciphertext.charAt(index++);
            }
        }
        
        StringBuilder plaintext = new StringBuilder();
        for (int col = 0; col < numCols; col++) {
            for (int row = 0; row < numRows; row++) {
                plaintext.append(grid[row][col]);
            }
        }
        
        return plaintext.toString().trim(); // Trim trailing spaces
    }
    
    private static int[] getOrder(String key) {
        String uppercaseKey = key.toUpperCase();
        int[] order = new int[key.length()];
        Integer[] indexes = new Integer[key.length()];
        
        for (int i = 0; i < key.length(); i++) {
            indexes[i] = i;
        }
        
        Arrays.sort(indexes, (a, b) -> Character.compare(uppercaseKey.charAt(a), uppercaseKey.charAt(b)));
        
        for (int i = 0; i < key.length(); i++) {
            order[i] = indexes[i];
        }
        
        return order;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter plaintext: ");
        String plaintext = scanner.nextLine().toUpperCase().replaceAll("[^A-Z]", "");
        
        System.out.println("Choose encryption technique:");
        System.out.println("1. Caesar Cipher");
        System.out.println("2. Playfair Cipher");
        System.out.println("3. Vigenère Cipher");
        System.out.println("4. Vernam Cipher");
        System.out.println("5. Rail Fence Cipher");
        System.out.println("6. Row-Columnar Transposition Cipher");
        System.out.print("Enter your choice (1-6): ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
        
        switch (choice) {
            case 1:
                System.out.print("Enter shift value for Caesar Cipher: ");
                int caesarShift = scanner.nextInt();
                scanner.nextLine(); // Consume newline character
                
                String caesarEncrypted = caesarEncrypt(plaintext, caesarShift);
                String caesarDecrypted = caesarDecrypt(caesarEncrypted, caesarShift);
                
                System.out.println("Caesar Cipher:");
                System.out.println("Plaintext: " + plaintext);
                System.out.println("Encrypted: " + caesarEncrypted);
                System.out.println("Decrypted: " + caesarDecrypted);
                break;
                
            case 2:
                scanner.nextLine(); // Consume newline character
                
                System.out.print("Enter key for Playfair Cipher: ");
                String playfairKey = scanner.nextLine().toUpperCase().replaceAll("[^A-Z]", "");
                
                String playfairEncrypted = playfairEncrypt(plaintext, playfairKey);
                String playfairDecrypted = playfairDecrypt(playfairEncrypted, playfairKey);
                
                System.out.println("Playfair Cipher:");
                System.out.println("Plaintext: " + plaintext);
                System.out.println("Encrypted: " + playfairEncrypted);
                System.out.println("Decrypted: " + playfairDecrypted);
                break;
                
            case 3:
                scanner.nextLine(); // Consume newline character
                
                System.out.print("Enter key for Vigenère Cipher: ");
                String vigenereKey = scanner.nextLine().toUpperCase().replaceAll("[^A-Z]", "");
                
                String vigenereEncrypted = vigenereEncrypt(plaintext, vigenereKey);
                String vigenereDecrypted = vigenereDecrypt(vigenereEncrypted, vigenereKey);
                
                System.out.println("Vigenère Cipher:");
                System.out.println("Plaintext: " + plaintext);
                System.out.println("Encrypted: " + vigenereEncrypted);
                System.out.println("Decrypted: " + vigenereDecrypted);
                break;
                
            case 4:
                scanner.nextLine(); // Consume newline character
                
                System.out.print("Enter key for Vernam Cipher: ");
                String vernamKey = scanner.nextLine().toUpperCase().replaceAll("[^A-Z]", "");
                
                String vernamEncrypted = vernamEncrypt(plaintext, vernamKey);
                String vernamDecrypted = vernamDecrypt(vernamEncrypted, vernamKey);
                
                System.out.println("Vernam Cipher:");
                System.out.println("Plaintext: " + plaintext);
                System.out.println("Encrypted: " + vernamEncrypted);
                System.out.println("Decrypted: " + vernamDecrypted);
                break;
                
            case 5:
                System.out.print("Enter number of rails for Rail Fence Cipher: ");
                int railFenceRails = scanner.nextInt();
                scanner.nextLine(); // Consume newline character
                
                String railFenceEncrypted = railFenceEncrypt(plaintext, railFenceRails);
                String railFenceDecrypted = railFenceDecrypt(railFenceEncrypted, railFenceRails);
                
                System.out.println("Rail Fence Cipher:");
                System.out.println("Plaintext: " + plaintext);
                System.out.println("Encrypted: " + railFenceEncrypted);
                System.out.println("Decrypted: " + railFenceDecrypted);
                break;
                
            case 6:
                scanner.nextLine(); // Consume newline character
                
                System.out.print("Enter key for Row-Columnar Transposition Cipher: ");
                String rowColumnarKey = scanner.nextLine().toUpperCase().replaceAll("[^A-Z]", "");
                
                String rowColumnarEncrypted = rowColumnarEncrypt(plaintext, rowColumnarKey);
                String rowColumnarDecrypted = rowColumnarDecrypt(rowColumnarEncrypted, rowColumnarKey);
                
                System.out.println("Row-Columnar Transposition Cipher:");
                System.out.println("Plaintext: " + plaintext);
                System.out.println("Encrypted: " + rowColumnarEncrypted);
                System.out.println("Decrypted: " + rowColumnarDecrypted);
                break;
                
            default:
                System.out.println("Invalid choice. Please choose a number between 1 and 6.");
                break;
        }
        
        scanner.close();
    }
}
