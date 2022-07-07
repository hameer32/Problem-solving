public class Mentor1Test {

    public static void main(String[] args) {
        String input = "acc2[ce3[bd]]";
        System.out.println(solve(input, 0));
    }

    static String solve(String input, int index) {
        StringBuilder str1;
        int itr;
        int i = index;
        String semiResult = "";
        StringIndex si = parseInitialString(input, i);
        str1 = si.str;
        i = si.index;
        si = parseCountOfSubString(input, i);
        i = si.index;
        itr = si.str.length() == 0 ? 0 : Integer.parseInt(si.str.toString());
        if (input.charAt(i) != ']') semiResult = solve(input, i + 1);
        for (int k = 0; k < itr; k++) {
            str1.append(semiResult);
        }
        return str1.toString();
    }

    static class StringIndex {
        StringBuilder str;
        int index;
    }

    private static StringIndex parseCountOfSubString(String input, int i) {
        StringBuilder iteration = new StringBuilder();
        StringIndex si = new StringIndex();
        for (; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '[' || c == ']') break;
            iteration.append(c);
        }
        si.str = iteration;
        si.index = i;
        return si;
    }

    private static StringIndex parseInitialString(String input, int i) {
        StringBuilder str1 = new StringBuilder();
        StringIndex si = new StringIndex();
        for (; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c < 'z' + 1 && c > 'a' - 1) {
                str1.append(input.charAt(i));
            } else {
                break;
            }
        }
        si.str = str1;
        si.index = i;
        return si;
    }

}
