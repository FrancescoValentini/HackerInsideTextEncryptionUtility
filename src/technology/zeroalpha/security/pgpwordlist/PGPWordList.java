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

import java.util.HashMap;
import java.util.Map;

/**
 * Representation of the PGP Word List, as defined at https://en.wikipedia.org/wiki/PGP_word_list. Each hex value
 * between 00 and FF (0 to 255 in decimal) has two phonetically distinct words associated with it.
 *
 * @author Chris Rowe
 */
public final class PGPWordList {

    /** Map used to look up words associated with a given hexadecimal value. */
    private static final Map<Integer, PGPWords> PGP_WORDS_BY_HEX_VALUE = new HashMap<>();

    /** Map used to look up hexadecimal value based on word. */
    private static final Map<String, Integer> HEX_VALUE_BY_PGP_WORD = new HashMap<>();

    /**
     * No-arg constructor used to build lookup maps for Hex Value <--> PGP word(s).
     */
    public PGPWordList() {
        addPGPWords(0x00, new PGPWords("aardvark", "adroitness"));
        addPGPWords(0x01, new PGPWords("absurd", "adviser"));
        addPGPWords(0x02, new PGPWords("accrue", "aftermath"));
        addPGPWords(0x03, new PGPWords("acme", "aggregate"));
        addPGPWords(0x04, new PGPWords("adrift", "alkali"));
        addPGPWords(0x05, new PGPWords("adult", "almighty"));
        addPGPWords(0x06, new PGPWords("afflict", "amulet"));
        addPGPWords(0x07, new PGPWords("ahead", "amusement"));
        addPGPWords(0x08, new PGPWords("aimless", "antenna"));
        addPGPWords(0x09, new PGPWords("Algol", "applicant"));
        addPGPWords(0x0A, new PGPWords("allow", "Apollo"));
        addPGPWords(0x0B, new PGPWords("alone", "armistice"));
        addPGPWords(0x0C, new PGPWords("ammo", "article"));
        addPGPWords(0x0D, new PGPWords("ancient", "asteroid"));
        addPGPWords(0x0E, new PGPWords("apple", "Atlantic"));
        addPGPWords(0x0F, new PGPWords("artist", "atmosphere"));
        addPGPWords(0x10, new PGPWords("assume", "autopsy"));
        addPGPWords(0x11, new PGPWords("Athens", "Babylon"));
        addPGPWords(0x12, new PGPWords("atlas", "backwater"));
        addPGPWords(0x13, new PGPWords("Aztec", "barbecue"));
        addPGPWords(0x14, new PGPWords("baboon", "belowground"));
        addPGPWords(0x15, new PGPWords("backfield", "bifocals"));
        addPGPWords(0x16, new PGPWords("backward", "bodyguard"));
        addPGPWords(0x17, new PGPWords("banjo", "bookseller"));
        addPGPWords(0x18, new PGPWords("beaming", "borderline"));
        addPGPWords(0x19, new PGPWords("bedlamp", "bottomless"));
        addPGPWords(0x1A, new PGPWords("beehive", "Bradbury"));
        addPGPWords(0x1B, new PGPWords("beeswax", "bravado"));
        addPGPWords(0x1C, new PGPWords("befriend", "Brazilian"));
        addPGPWords(0x1D, new PGPWords("Belfast", "breakaway"));
        addPGPWords(0x1E, new PGPWords("berserk", "Burlington"));
        addPGPWords(0x1F, new PGPWords("billiard", "businessman"));
        addPGPWords(0x20, new PGPWords("bison", "butterfat"));
        addPGPWords(0x21, new PGPWords("blackjack", "Camelot"));
        addPGPWords(0x22, new PGPWords("blockade", "candidate"));
        addPGPWords(0x23, new PGPWords("blowtorch", "cannonball"));
        addPGPWords(0x24, new PGPWords("bluebird", "Capricorn"));
        addPGPWords(0x25, new PGPWords("bombast", "caravan"));
        addPGPWords(0x26, new PGPWords("bookshelf", "caretaker"));
        addPGPWords(0x27, new PGPWords("brackish", "celebrate"));
        addPGPWords(0x28, new PGPWords("breadline", "cellulose"));
        addPGPWords(0x29, new PGPWords("breakup", "certify"));
        addPGPWords(0x2A, new PGPWords("brickyard", "chambermaid"));
        addPGPWords(0x2B, new PGPWords("briefcase", "Cherokee"));
        addPGPWords(0x2C, new PGPWords("Burbank", "Chicago"));
        addPGPWords(0x2D, new PGPWords("button", "clergyman"));
        addPGPWords(0x2E, new PGPWords("buzzard", "coherence"));
        addPGPWords(0x2F, new PGPWords("cement", "combustion"));
        addPGPWords(0x30, new PGPWords("chairlift", "commando"));
        addPGPWords(0x31, new PGPWords("chatter", "company"));
        addPGPWords(0x32, new PGPWords("checkup", "component"));
        addPGPWords(0x33, new PGPWords("chisel", "concurrent"));
        addPGPWords(0x34, new PGPWords("choking", "confidence"));
        addPGPWords(0x35, new PGPWords("chopper", "conformist"));
        addPGPWords(0x36, new PGPWords("Christmas", "congregate"));
        addPGPWords(0x37, new PGPWords("clamshell", "consensus"));
        addPGPWords(0x38, new PGPWords("classic", "consulting"));
        addPGPWords(0x39, new PGPWords("classroom", "corporate"));
        addPGPWords(0x3A, new PGPWords("cleanup", "corrosion"));
        addPGPWords(0x3B, new PGPWords("clockwork", "councilman"));
        addPGPWords(0x3C, new PGPWords("cobra", "crossover"));
        addPGPWords(0x3D, new PGPWords("commence", "crucifix"));
        addPGPWords(0x3E, new PGPWords("concert", "cumbersome"));
        addPGPWords(0x3F, new PGPWords("cowbell", "customer"));
        addPGPWords(0x40, new PGPWords("crackdown", "Dakota"));
        addPGPWords(0x41, new PGPWords("cranky", "decadence"));
        addPGPWords(0x42, new PGPWords("crowfoot", "December"));
        addPGPWords(0x43, new PGPWords("crucial", "decimal"));
        addPGPWords(0x44, new PGPWords("crumpled", "designing"));
        addPGPWords(0x45, new PGPWords("crusade", "detector"));
        addPGPWords(0x46, new PGPWords("cubic", "detergent"));
        addPGPWords(0x47, new PGPWords("dashboard", "determine"));
        addPGPWords(0x48, new PGPWords("deadbolt", "dictator"));
        addPGPWords(0x49, new PGPWords("deckhand", "dinosaur"));
        addPGPWords(0x4A, new PGPWords("dogsled", "direction"));
        addPGPWords(0x4B, new PGPWords("dragnet", "disable"));
        addPGPWords(0x4C, new PGPWords("drainage", "disbelief"));
        addPGPWords(0x4D, new PGPWords("dreadful", "disruptive"));
        addPGPWords(0x4E, new PGPWords("drifter", "distortion"));
        addPGPWords(0x4F, new PGPWords("dropper", "document"));
        addPGPWords(0x50, new PGPWords("drumbeat", "embezzle"));
        addPGPWords(0x51, new PGPWords("drunken", "enchanting"));
        addPGPWords(0x52, new PGPWords("Dupont", "enrollment"));
        addPGPWords(0x53, new PGPWords("dwelling", "enterprise"));
        addPGPWords(0x54, new PGPWords("eating", "equation"));
        addPGPWords(0x55, new PGPWords("edict", "equipment"));
        addPGPWords(0x56, new PGPWords("egghead", "escapade"));
        addPGPWords(0x57, new PGPWords("eightball", "Eskimo"));
        addPGPWords(0x58, new PGPWords("endorse", "everyday"));
        addPGPWords(0x59, new PGPWords("endow", "examine"));
        addPGPWords(0x5A, new PGPWords("enlist", "existence"));
        addPGPWords(0x5B, new PGPWords("erase", "exodus"));
        addPGPWords(0x5C, new PGPWords("escape", "fascinate"));
        addPGPWords(0x5D, new PGPWords("exceed", "filament"));
        addPGPWords(0x5E, new PGPWords("eyeglass", "finicky"));
        addPGPWords(0x5F, new PGPWords("eyetooth", "forever"));
        addPGPWords(0x60, new PGPWords("facial", "fortitude"));
        addPGPWords(0x61, new PGPWords("fallout", "frequency"));
        addPGPWords(0x62, new PGPWords("flagpole", "gadgetry"));
        addPGPWords(0x63, new PGPWords("flatfoot", "Galveston"));
        addPGPWords(0x64, new PGPWords("flytrap", "getaway"));
        addPGPWords(0x65, new PGPWords("fracture", "glossary"));
        addPGPWords(0x66, new PGPWords("framework", "gossamer"));
        addPGPWords(0x67, new PGPWords("freedom", "graduate"));
        addPGPWords(0x68, new PGPWords("frighten", "gravity"));
        addPGPWords(0x69, new PGPWords("gazelle", "guitarist"));
        addPGPWords(0x6A, new PGPWords("Geiger", "hamburger"));
        addPGPWords(0x6B, new PGPWords("glitter", "Hamilton"));
        addPGPWords(0x6C, new PGPWords("glucose", "handiwork"));
        addPGPWords(0x6D, new PGPWords("goggles", "hazardous"));
        addPGPWords(0x6E, new PGPWords("goldfish", "headwaters"));
        addPGPWords(0x6F, new PGPWords("gremlin", "hemisphere"));
        addPGPWords(0x70, new PGPWords("guidance", "hesitate"));
        addPGPWords(0x71, new PGPWords("hamlet", "hideaway"));
        addPGPWords(0x72, new PGPWords("highchair", "holiness"));
        addPGPWords(0x73, new PGPWords("hockey", "hurricane"));
        addPGPWords(0x74, new PGPWords("indoors", "hydraulic"));
        addPGPWords(0x75, new PGPWords("indulge", "impartial"));
        addPGPWords(0x76, new PGPWords("inverse", "impetus"));
        addPGPWords(0x77, new PGPWords("involve", "inception"));
        addPGPWords(0x78, new PGPWords("island", "indigo"));
        addPGPWords(0x79, new PGPWords("jawbone", "inertia"));
        addPGPWords(0x7A, new PGPWords("keyboard", "infancy"));
        addPGPWords(0x7B, new PGPWords("kickoff", "inferno"));
        addPGPWords(0x7C, new PGPWords("kiwi", "informant"));
        addPGPWords(0x7D, new PGPWords("klaxon", "insincere"));
        addPGPWords(0x7E, new PGPWords("locale", "insurgent"));
        addPGPWords(0x7F, new PGPWords("lockup", "integrate"));
        addPGPWords(0x80, new PGPWords("merit", "intention"));
        addPGPWords(0x81, new PGPWords("minnow", "inventive"));
        addPGPWords(0x82, new PGPWords("miser", "Istanbul"));
        addPGPWords(0x83, new PGPWords("Mohawk", "Jamaica"));
        addPGPWords(0x84, new PGPWords("mural", "Jupiter"));
        addPGPWords(0x85, new PGPWords("music", "leprosy"));
        addPGPWords(0x86, new PGPWords("necklace", "letterhead"));
        addPGPWords(0x87, new PGPWords("Neptune", "liberty"));
        addPGPWords(0x88, new PGPWords("newborn", "maritime"));
        addPGPWords(0x89, new PGPWords("nightbird", "matchmaker"));
        addPGPWords(0x8A, new PGPWords("Oakland", "maverick"));
        addPGPWords(0x8B, new PGPWords("obtuse", "Medusa"));
        addPGPWords(0x8C, new PGPWords("offload", "megaton"));
        addPGPWords(0x8D, new PGPWords("optic", "microscope"));
        addPGPWords(0x8E, new PGPWords("orca", "microwave"));
        addPGPWords(0x8F, new PGPWords("payday", "midsummer"));
        addPGPWords(0x90, new PGPWords("peachy", "millionaire"));
        addPGPWords(0x91, new PGPWords("pheasant", "miracle"));
        addPGPWords(0x92, new PGPWords("physique", "misnomer"));
        addPGPWords(0x93, new PGPWords("playhouse", "molasses"));
        addPGPWords(0x94, new PGPWords("Pluto", "molecule"));
        addPGPWords(0x95, new PGPWords("preclude", "Montana"));
        addPGPWords(0x96, new PGPWords("prefer", "monument"));
        addPGPWords(0x97, new PGPWords("preshrunk", "mosquito"));
        addPGPWords(0x98, new PGPWords("printer", "narrative"));
        addPGPWords(0x99, new PGPWords("prowler", "nebula"));
        addPGPWords(0x9A, new PGPWords("pupil", "newsletter"));
        addPGPWords(0x9B, new PGPWords("puppy", "Norwegian"));
        addPGPWords(0x9C, new PGPWords("python", "October"));
        addPGPWords(0x9D, new PGPWords("quadrant", "Ohio"));
        addPGPWords(0x9E, new PGPWords("quiver", "onlooker"));
        addPGPWords(0x9F, new PGPWords("quota", "opulent"));
        addPGPWords(0xA0, new PGPWords("ragtime", "Orlando"));
        addPGPWords(0xA1, new PGPWords("ratchet", "outfielder"));
        addPGPWords(0xA2, new PGPWords("rebirth", "Pacific"));
        addPGPWords(0xA3, new PGPWords("reform", "pandemic"));
        addPGPWords(0xA4, new PGPWords("regain", "Pandora"));
        addPGPWords(0xA5, new PGPWords("reindeer", "paperweight"));
        addPGPWords(0xA6, new PGPWords("rematch", "paragon"));
        addPGPWords(0xA7, new PGPWords("repay", "paragraph"));
        addPGPWords(0xA8, new PGPWords("retouch", "paramount"));
        addPGPWords(0xA9, new PGPWords("revenge", "passenger"));
        addPGPWords(0xAA, new PGPWords("reward", "pedigree"));
        addPGPWords(0xAB, new PGPWords("rhythm", "Pegasus"));
        addPGPWords(0xAC, new PGPWords("ribcage", "penetrate"));
        addPGPWords(0xAD, new PGPWords("ringbolt", "perceptive"));
        addPGPWords(0xAE, new PGPWords("robust", "performance"));
        addPGPWords(0xAF, new PGPWords("rocker", "pharmacy"));
        addPGPWords(0xB0, new PGPWords("ruffled", "phonetic"));
        addPGPWords(0xB1, new PGPWords("sailboat", "photograph"));
        addPGPWords(0xB2, new PGPWords("sawdust", "pioneer"));
        addPGPWords(0xB3, new PGPWords("scallion", "pocketful"));
        addPGPWords(0xB4, new PGPWords("scenic", "politeness"));
        addPGPWords(0xB5, new PGPWords("scorecard", "positive"));
        addPGPWords(0xB6, new PGPWords("Scotland", "potato"));
        addPGPWords(0xB7, new PGPWords("seabird", "processor"));
        addPGPWords(0xB8, new PGPWords("select", "provincial"));
        addPGPWords(0xB9, new PGPWords("sentence", "proximate"));
        addPGPWords(0xBA, new PGPWords("shadow", "puberty"));
        addPGPWords(0xBB, new PGPWords("shamrock", "publisher"));
        addPGPWords(0xBC, new PGPWords("showgirl", "pyramid"));
        addPGPWords(0xBD, new PGPWords("skullcap", "quantity"));
        addPGPWords(0xBE, new PGPWords("skydive", "racketeer"));
        addPGPWords(0xBF, new PGPWords("slingshot", "rebellion"));
        addPGPWords(0xC0, new PGPWords("slowdown", "recipe"));
        addPGPWords(0xC1, new PGPWords("snapline", "recover"));
        addPGPWords(0xC2, new PGPWords("snapshot", "repellent"));
        addPGPWords(0xC3, new PGPWords("snowcap", "replica"));
        addPGPWords(0xC4, new PGPWords("snowslide", "reproduce"));
        addPGPWords(0xC5, new PGPWords("solo", "resistor"));
        addPGPWords(0xC6, new PGPWords("southward", "responsive"));
        addPGPWords(0xC7, new PGPWords("soybean", "retraction"));
        addPGPWords(0xC8, new PGPWords("spaniel", "retrieval"));
        addPGPWords(0xC9, new PGPWords("spearhead", "retrospect"));
        addPGPWords(0xCA, new PGPWords("spellbind", "revenue"));
        addPGPWords(0xCB, new PGPWords("spheroid", "revival"));
        addPGPWords(0xCC, new PGPWords("spigot", "revolver"));
        addPGPWords(0xCD, new PGPWords("spindle", "sandalwood"));
        addPGPWords(0xCE, new PGPWords("spyglass", "sardonic"));
        addPGPWords(0xCF, new PGPWords("stagehand", "Saturday"));
        addPGPWords(0xD0, new PGPWords("stagnate", "savagery"));
        addPGPWords(0xD1, new PGPWords("stairway", "scavenger"));
        addPGPWords(0xD2, new PGPWords("standard", "sensation"));
        addPGPWords(0xD3, new PGPWords("stapler", "sociable"));
        addPGPWords(0xD4, new PGPWords("steamship", "souvenir"));
        addPGPWords(0xD5, new PGPWords("sterling", "specialist"));
        addPGPWords(0xD6, new PGPWords("stockman", "speculate"));
        addPGPWords(0xD7, new PGPWords("stopwatch", "stethoscope"));
        addPGPWords(0xD8, new PGPWords("stormy", "stupendous"));
        addPGPWords(0xD9, new PGPWords("sugar", "supportive"));
        addPGPWords(0xDA, new PGPWords("surmount", "surrender"));
        addPGPWords(0xDB, new PGPWords("suspense", "suspicious"));
        addPGPWords(0xDC, new PGPWords("sweatband", "sympathy"));
        addPGPWords(0xDD, new PGPWords("swelter", "tambourine"));
        addPGPWords(0xDE, new PGPWords("tactics", "telephone"));
        addPGPWords(0xDF, new PGPWords("talon", "therapist"));
        addPGPWords(0xE0, new PGPWords("tapeworm", "tobacco"));
        addPGPWords(0xE1, new PGPWords("tempest", "tolerance"));
        addPGPWords(0xE2, new PGPWords("tiger", "tomorrow"));
        addPGPWords(0xE3, new PGPWords("tissue", "torpedo"));
        addPGPWords(0xE4, new PGPWords("tonic", "tradition"));
        addPGPWords(0xE5, new PGPWords("topmost", "travesty"));
        addPGPWords(0xE6, new PGPWords("tracker", "trombonist"));
        addPGPWords(0xE7, new PGPWords("transit", "truncated"));
        addPGPWords(0xE8, new PGPWords("trauma", "typewriter"));
        addPGPWords(0xE9, new PGPWords("treadmill", "ultimate"));
        addPGPWords(0xEA, new PGPWords("Trojan", "undaunted"));
        addPGPWords(0xEB, new PGPWords("trouble", "underfoot"));
        addPGPWords(0xEC, new PGPWords("tumor", "unicorn"));
        addPGPWords(0xED, new PGPWords("tunnel", "unify"));
        addPGPWords(0xEE, new PGPWords("tycoon", "universe"));
        addPGPWords(0xEF, new PGPWords("uncut", "unravel"));
        addPGPWords(0xF0, new PGPWords("unearth", "upcoming"));
        addPGPWords(0xF1, new PGPWords("unwind", "vacancy"));
        addPGPWords(0xF2, new PGPWords("uproot", "vagabond"));
        addPGPWords(0xF3, new PGPWords("upset", "vertigo"));
        addPGPWords(0xF4, new PGPWords("upshot", "Virginia"));
        addPGPWords(0xF5, new PGPWords("vapor", "visitor"));
        addPGPWords(0xF6, new PGPWords("village", "vocalist"));
        addPGPWords(0xF7, new PGPWords("virus", "voyager"));
        addPGPWords(0xF8, new PGPWords("Vulcan", "warranty"));
        addPGPWords(0xF9, new PGPWords("waffle", "Waterloo"));
        addPGPWords(0xFA, new PGPWords("wallet", "whimsical"));
        addPGPWords(0xFB, new PGPWords("watchword", "Wichita"));
        addPGPWords(0xFC, new PGPWords("wayside", "Wilmington"));
        addPGPWords(0xFD, new PGPWords("willow", "Wyoming"));
        addPGPWords(0xFE, new PGPWords("woodlark", "yesteryear"));
        addPGPWords(0xFF, new PGPWords("Zulu", "Yucatan"));
    }

    /**
     * Populate the {@link Map}s with the appropriate lookup values.
     *
     * @param hexValue The hexadecimal value
     * @param pgpWords The words associated with the hex value
     */
    private void addPGPWords(final int hexValue, final PGPWords pgpWords) {
        PGP_WORDS_BY_HEX_VALUE.put(hexValue, pgpWords);

        // Lower case the words so that we can do a case-insensitive reverse lookup
        HEX_VALUE_BY_PGP_WORD.put(pgpWords.getEvenWord().toLowerCase(), hexValue);
        HEX_VALUE_BY_PGP_WORD.put(pgpWords.getOddWord().toLowerCase(), hexValue);
    }

    /**
     * Retrieve the hexadecimal value associated with the word provided. Word comparison is NOT case sensitive.
     *
     * @param pgpWord Word from PGP list
     * @return Hex value associated with provided word
     */
    public Integer getHexValueByPGPWord(final String pgpWord) {
        if (pgpWord == null) {
            return null;
        }

        // Lower-case the word to perform the lookup
        return HEX_VALUE_BY_PGP_WORD.get(pgpWord.toLowerCase());
    }

    /**
     * Retrieve pair of PGP words associated with given hexadecimal value.
     *
     * @param hexValue Hex value
     * @return Words associated with hex value
     */
    public PGPWords getPGPWordsForHexValue(final int hexValue) {
        return PGP_WORDS_BY_HEX_VALUE.get(hexValue);
    }
}
