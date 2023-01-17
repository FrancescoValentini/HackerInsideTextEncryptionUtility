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
 * Class representing the two possible words associated with a given hexadecimal value, as defined by the PGP Word List.
 *
 * @author Chris Rowe
 */
public final class PGPWords {

    /** Word for the given hex value, if the hex value is an even number of bytes into the network order. */
    private final String evenWord;

    /** Word for the given hex value, if the hex value is an odd number of bytes into the network order. */
    private final String oddWord;

    /**
     * @param evenWord Word if byte is even
     * @param oddWord Word if byte is odd
     */
    public PGPWords(final String evenWord, final String oddWord) {
        this.evenWord = evenWord;
        this.oddWord = oddWord;
    }

    /**
     * @return the even word
     */
    public String getEvenWord() {
        return evenWord;
    }

    /**
     * @return the odd word
     */
    public String getOddWord() {
        return oddWord;
    }
}
