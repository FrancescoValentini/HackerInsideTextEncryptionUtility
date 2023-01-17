/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016  Zero Alpha Technology Limited
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package technology.zeroalpha.security.pgpwordlist;

/**
 * @author Chris Rowe
 */
public class PGPWordListConverter {

    /** PGP Words and their associated hexadecimal values. */
    private static final PGPWordList PGP_WORD_LIST = new PGPWordList();

    /**
     * Retrieve the even PGP word for the given hexadecimal value.
     *
     * @param hexString String representation of hexadecimal value (should NOT include a leading '0x'
     * @return Even PGP word associated with hex value
     *
     * @throws InvalidHexValueException If provided hex value is not valid
     */
    public String getEvenWordForHexValue(final String hexString) throws InvalidHexValueException {
        final int hexValue = convertHexStringToInteger(hexString);
        final PGPWords pgpWords = PGP_WORD_LIST.getPGPWordsForHexValue(hexValue);

        return pgpWords.getEvenWord();
    }

    /**
     * Retrieve the odd PGP word for the given hexadecimal value.
     *
     * @param hexString String representation of hexadecimal value (should NOT include a leading '0x'
     * @return Odd PGP word associated with hex value
     *
     * @throws InvalidHexValueException If provided hex value is not valid
     */
    public String getOddWordForHexValue(final String hexString) throws InvalidHexValueException {
        final int hexValue = convertHexStringToInteger(hexString);
        final PGPWords pgpWords = PGP_WORD_LIST.getPGPWordsForHexValue(hexValue);

        return pgpWords.getOddWord();
    }

    /**
     * Retrieve the hexadecimal value associated with the provided PGP Word. Method returns a String representation of
     * the hex number, without a leading 0x.
     *
     * @param pgpWord PGP Word to get hex value for
     * @return Hexadecimal value associated with word, represented as a String
     *
     * @throws InvalidPGPWordException If word is not valid/recognised
     */
    public String getHexValueForPGPWord(final String pgpWord) throws InvalidPGPWordException {
        if (pgpWord == null) {
            throw new InvalidPGPWordException("Cannot convert null to hexadecimal value");
        }

        final Integer hexValue = PGP_WORD_LIST.getHexValueByPGPWord(pgpWord);
        if (hexValue == null) {
            throw new InvalidPGPWordException(String.format("PGP Word not recognised: %s", pgpWord));
        } else {
        	//System.out.println("DecodificaLib: " + Integer.toHexString(hexValue).toUpperCase());
            return String.format("%02X", hexValue);//Integer.toHexString(hexValue).toUpperCase();
        }
    }

    /**
     * Convert a provided String representation of a hexadecimal value into an integer. If the String cannot be
     * converted, or the value is too large (i.e. > 0xFF), an {@link InvalidHexValueException} is thrown.
     *
     * @param hexString String representation of hex value
     * @return Integer representation of hex value
     *
     * @throws InvalidHexValueException If provided hex value is not valid
     */
    private int convertHexStringToInteger(final String hexString) throws InvalidHexValueException {
        if (hexString == null) {
            throw new InvalidHexValueException(
                    "Cannot convert to PGP Word - null passed in rather than hexadecimal value.");
        }

        final String trimmedHexValue = hexString.trim();
        if (trimmedHexValue.isEmpty()) {
            throw new InvalidHexValueException(
                    "Cannot convert to PGP Word - empty string passed in rather than hexadecimal value.");
        } else if (trimmedHexValue.length() > 2) {
            throw new InvalidHexValueException(
                    "Cannot convert to PGP Word - provided hexadecimal value is too large.");
        }

        try {
            return Integer.decode(String.format("0x%s", trimmedHexValue));
        } catch (final NumberFormatException nfe) {
            throw new InvalidHexValueException(
                    "Cannot convert to PGP Word - provided string was not a valid hexadecimal value.", nfe);
        }
    }
    
    public String hexToPGPWordlist(String hex) throws InvalidHexValueException { // Funzione che codifica in pgpWordlist una stringa di esadecimali
        String str = "";
        int bytecnt = 0;
        for (int i = 0; i < hex.length(); i+=2,bytecnt++) {
            String hexPair = hex.substring(i, i + 2);
            int hexValue = convertHexStringToInteger(hexPair);
            PGPWords pgpWords = PGP_WORD_LIST.getPGPWordsForHexValue(hexValue);
            
            
            if(bytecnt % 2 == 0) {
            	str += pgpWords.getEvenWord() + " ";
            }else {
            	str += pgpWords.getOddWord() + " ";
            }
            
            
        }
        return str;
    }
    public String pgpWordlistToHex(String wordList) throws InvalidHexValueException, InvalidPGPWordException { // Decodifica
    	String[] words = wordList.split(" ");
    	String hex = "";
    	for(String word : words) {
    		if(!words.equals(" ")) {
    			hex += getHexValueForPGPWord(word);
    		}
    	}
        return hex;
    }

}
