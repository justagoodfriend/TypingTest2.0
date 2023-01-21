package model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class WordUtils {
    //in the future make this more efficient in grabbing word lists
    public static ArrayList<ArrayList<String>> generateLists() {
        ArrayList<String> a = new ArrayList<String>(Arrays.asList("allow", "age", "awaken", "airport", "accept",
                "answer", "autumn", "ability", "action", "apple"));
        ArrayList<String> b = new ArrayList<String>(Arrays.asList("balloon", "baby", "based", "banana", "berth",
                "basket", "boiled", "bigger", "budget", "build"));
        ArrayList<String> c = new ArrayList<String>(Arrays.asList("colder", "crap", "corny", "chalk", "curtain",
                "champion", "chocolate", "create", "color", "children"));
        ArrayList<String> d = new ArrayList<String>(Arrays.asList("dinosaur", "donkey", "drink", "duck", "darker",
                "dance", "dapper", "daring", "dashing", "damage"));
        ArrayList<String> e = new ArrayList<String>(Arrays.asList("expand", "eagle", "end", "eight", "erase",
                "excerpt", "eleven", "every", "event", "emulate"));
        ArrayList<String> f = new ArrayList<String>(Arrays.asList("father", "folder", "farm", "flipped", "furtive",
                "frequent", "faction", "farted", "foundation", "fertile"));
        ArrayList<String> g = new ArrayList<String>(Arrays.asList("gobble", "goose", "ghosted", "glacier", "guitar",
                "gym", "gate", "gush", "gunfire", "girl"));
        ArrayList<String> h = new ArrayList<String>(Arrays.asList("hate", "home", "his", "hopeful", "horror",
                "habitat", "handshake", "healed", "halfway", "hurricane"));
        ArrayList<String> i = new ArrayList<String>(Arrays.asList("inked", "intense", "illegal", "ice", "igloo",
                "imply", "instant", "ignite", "itch", "issue"));
        ArrayList<String> j = new ArrayList<String>(Arrays.asList("jacket", "jail", "jam", "jealous", "jargon",
                "joint", "juice", "jersey", "jester", "judicial"));
        ArrayList<String> k = new ArrayList<String>(Arrays.asList("kiwi", "knit", "kitten", "kangaroo", "ketchup",
                "keyboard", "kale", "keeper", "kids", "keyed"));
        ArrayList<String> l = new ArrayList<String>(Arrays.asList("lunch", "luggage", "loyal", "landmark", "lowkey",
                "latest", "laugh", "loud", "listed", "location"));
        ArrayList<String> m = new ArrayList<String>(Arrays.asList("marry", "mountain", "medic", "moss", "melt",
                "money", "magic", "marched", "mouse", "must"));
        ArrayList<String> n = new ArrayList<String>(Arrays.asList("nature", "normal", "nurse", "now", "nail",
                "nerd", "notebook", "narration", "nautical", "near"));
        ArrayList<String> o = new ArrayList<String>(Arrays.asList("once", "ocean", "organs", "ointment", "object",
                "optimist", "opportune", "orange", "oasis", "oath"));
        ArrayList<String> p = new ArrayList<String>(Arrays.asList("pad", "paint", "paper", "paste", "peaked",
                "press", "priced", "package", "pacify", "punish"));
        ArrayList<String> q = new ArrayList<String>(Arrays.asList("quirky", "quit", "quill", "quest", "quarter",
                "quantum", "quack", "quiz", "qualm", "quilt"));
        ArrayList<String> r = new ArrayList<String>(Arrays.asList("raisin", "rice", "risked", "ration", "racist",
                "raise", "rapid", "rendezvous", "radio", "rummage"));
        ArrayList<String> s = new ArrayList<String>(Arrays.asList("swift", "sweet", "sets", "symbol", "sacrifice",
                "sword", "salted", "stable", "sacred", "sandwich"));
        ArrayList<String> t = new ArrayList<String>(Arrays.asList("tasty", "travel", "tripped", "time", "tip",
                "tomb", "table", "thanked", "tomato", "tailor"));
        ArrayList<String> u = new ArrayList<String>(Arrays.asList("ugly", "unable", "umpire", "under", "uncle",
                "utter", "usual", "utopia", "uplift", "uber"));
        ArrayList<String> v = new ArrayList<String>(Arrays.asList("varied", "very", "viral", "venom", "vocal",
                "viewed", "vulnerable", "vouch", "valley", "vegetable"));
        ArrayList<String> w = new ArrayList<String>(Arrays.asList("week", "where", "warmth", "worthy", "woke",
                "welcome", "watch", "worked", "wacky", "winner"));
        ArrayList<String> x = new ArrayList<String>(Arrays.asList("xray", "xenophobic", "xerox", "xylophone", "xenial",
                "xenon", "xylene", "sexy", "axed", "mixing"));
        ArrayList<String> y = new ArrayList<String>(Arrays.asList("yogurt", "yummy", "yards", "yelled", "you",
                "yahoo", "years", "yucky", "yawned", "yaks"));
        ArrayList<String> z = new ArrayList<String>(Arrays.asList("zapped", "zip", "zombie", "zest", "zone",
                "zero", "zodiac", "zoom", "zebra", "zygote"));

        return new ArrayList<ArrayList<String>>(Arrays.asList(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s,
                t, u, v, w, x, y, z));
    }

    public static RandCollection<ArrayList<String>> rcMaker(ArrayList<String> wrongLetters) {
        ArrayList<Integer> indexes = letterConverter(wrongLetters);
        RandomCollection<ArrayList<String>> rc = new RandomCollection<ArrayList<String>>();
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < indexes.size(); j++) {
                int w = 1;
                if (i == indexes.get(j)) {
                    w++;
                }
                rc.add(w, generateLists().get(i));
            }
        }
        return rc;
    }

    public static ArrayList<Integer> letterConverter(ArrayList<String> letters) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        for(int i = 0; i < letters.size(); i++) {
            indexes.add(alphabet.indexOf(letters.get(i)));
        }
        return indexes;
    }
}
