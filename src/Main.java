import java.io.*;
import java.lang.reflect.Array;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Main {

    public static void main(String[] args) throws ScriptException, IOException {

        //sample expression
        //String input = "(a >= 1 and b = 2) or (a = 2 and b = 1)";

        System.out.println("Enter expression:");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();

        input = input.replaceAll("and", "&&")
                .replaceAll("or", "||")
                .replaceAll(" =", " ==")
                .replaceAll("\\(", " \\( ")
                .replaceAll("\\)", " \\) ");

        String[] sSplit = input.trim().split("\\s+");

        Solution solution = new Solution();
        ArrayList<String> rpnData = solution.convertToRPN(sSplit);
        sSplit = rpnData.toArray(new String[rpnData.size()]);
        System.out.println(Arrays.toString(sSplit));

        // tree builder
        Stack stk = new Stack<Node>();

        for(String value : sSplit) {
            Boolean digit = Character.isLetterOrDigit(value.charAt(0));
            String s = value;

            if (digit) {
                Node node = new Node(s);
                stk.push(node);
            } else {
                Node node = new Node(s);
                node.setLeft((Node) stk.pop());
                node.setRight((Node) stk.pop());
                stk.push(node);
            }
        }

        treePrinter(System.out, (Node)stk.get(0));

        System.out.println("Press any key to continue...");
        System.in.read();

        final String evalInput = input
                .replaceAll("a", "{0}")
                .replaceAll("b", "{1}")
                .replaceAll("c", "{2}")
                .replaceAll("d", "{3}")
                .replaceAll("e", "{4}")
                .replaceAll("f", "{5}")
                .replaceAll("g", "{6}")
                .replaceAll("h", "{7}");

        ScriptEngineManager sem = new ScriptEngineManager();
        final ScriptEngine se = sem.getEngineByName("JavaScript");

        List<DataRecord> exampleData = dataGenerator(10);

        List<DataRecord> result = exampleData.stream().filter(c -> evalDataRecord(se, evalInput, c)).collect(Collectors.toList());
        System.out.println(result);

    }

    public static boolean evalDataRecord(ScriptEngine se, String exp, DataRecord dr){
        exp = MessageFormat.format(exp, dr.a, dr.b, dr.c, dr.d, dr.e, dr.f, dr.g, dr.h);
        try {
            return (boolean)se.eval(exp);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<DataRecord> dataGenerator(int howMany) {
        List<DataRecord> data = new ArrayList<>();
        Random r = new Random();
        for(int i=0;i<howMany;i++) {
            DataRecord dr = new DataRecord(r.nextInt(20),r.nextInt(20),r.nextInt(20),r.nextInt(20),r.nextInt(20),r.nextInt(20),r.nextInt(20),r.nextInt(20));
            data.add(dr);
        }
        return data;
    }

    //source:
    //https://www.baeldung.com/java-print-binary-tree-diagram
    public static String traversePreOrder(Node root) {

        if (root == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(root.getValue());

        String pointerRight = "└──";
        String pointerLeft = (root.getRight() != null) ? "├──" : "└──";

        traverseNodes(sb, "", pointerLeft, root.getLeft(), root.getRight() != null);
        traverseNodes(sb, "", pointerRight, root.getRight(), false);

        return sb.toString();
    }

    //source:
    //https://www.baeldung.com/java-print-binary-tree-diagram
    public static void traverseNodes(StringBuilder sb, String padding, String pointer, Node node,
                              boolean hasRightSibling) {
        if (node != null) {
            sb.append("\n");
            sb.append(padding);
            sb.append(pointer);
            sb.append(node.getValue());

            StringBuilder paddingBuilder = new StringBuilder(padding);
            if (hasRightSibling) {
                paddingBuilder.append("│  ");
            } else {
                paddingBuilder.append("   ");
            }

            String paddingForBoth = paddingBuilder.toString();
            String pointerRight = "└──";
            String pointerLeft = (node.getRight() != null) ? "├──" : "└──";

            traverseNodes(sb, paddingForBoth, pointerLeft, node.getLeft(), node.getRight() != null);
            traverseNodes(sb, paddingForBoth, pointerRight, node.getRight(), false);
        }
    }

    //source:
    //https://www.baeldung.com/java-print-binary-tree-diagram
    public static void treePrinter(PrintStream os, Node root) {
        os.print(traversePreOrder(root));
        System.out.println();
    }
}
