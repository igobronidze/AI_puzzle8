package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public abstract class Solver {
    
    public static boolean stopBFS;
    public static boolean stopF1;
    public static boolean stopF2;

    public static ArrayList<Node> solveWithBFS(Node root) {
        
        HashSet<Node> closed = new HashSet<>();   // დახურული სია. წვეროებისთვის რომელიც უკვე განხილულია
        LinkedList<Node> opened = new LinkedList<>();   // ღია სია. წვეროებისთვის რომელიც განხილვას ელის

        opened.addLast(root);
        Node node = root;
        while (true) {
            if (opened.isEmpty() || stopBFS) {
                return null;
                //throw new IncorrectInputException("ამოცანას არ გააჩნია ამონახსნი!");
            }
            node = opened.getFirst();      // ავიღოთ პირველი ელემნტი სიიდან "ღია"
            opened.removeFirst();               // ამოვშალოთ პირველი ელემენტი სიიდან "ღია"
            if (node.isSolved()) {
                break;                          // თუ ამოხსნილია გავაჩეროთ ციკლი
            }
            ArrayList<Node> possiblePaths = node.allPossiblePath();        // ყველა შესაძლო განვითარება

            for (Node n : possiblePaths) {                    // დავამატოთ ყველა set-ში ღია
                if (closed.contains(n)) {
                    continue;
                }
                n.setParent(node);                            // მშობლის დაფიქსირება
                opened.addLast(n);
                closed.add(node);                 // ჩავამატოთ სიაში "დახურული"
            }

        }

        ArrayList<Node> ans = new ArrayList<>();
        // ამოვიღოთ გზა რომელიც გაიარა node-მა სწორ პასუხამდე
        while (true) {
            ans.add(node);
            if (node.getParent() == null) {
                break;
            }
            node = node.getParent();
        }
        Collections.reverse(ans);
        System.out.println(ans.size());
        return ans;

    }

    public static ArrayList<Node> solveWithAlgorithmA1(Node root) {
        HashSet<Node> closed = new HashSet<>();   // დახურული სია. წვეროებისთვის რომელიც უკვე განხილულია
        PriorityQueue<Node> opened = new PriorityQueue<Node>(100000, new NodeComparator1());   // ღია სია პრიორიტეტით
        
        opened.add(root);
        closed.add(root);
        root.setDepth((byte)0);
        Node node = root;
        while (true) {
            if (opened.isEmpty() || stopF1) {
                return null;
                //throw new IncorrectInputException("ამოცანას არ გააჩნია ამონახსნი!");
            }
            node = opened.poll();     // ავიღოთ და წავშალოთ პირველი ელემნტი სიიდან "ღია"
            if (node.isSolved()) {
                break;                          // თუ ამოხსნილია გავაჩეროთ ციკლი
            }
            ArrayList<Node> possiblePaths = node.allPossiblePath();        // ყველა შესაძლო განვითარება
            //System.out.println(node);
            //System.out.println(possiblePaths.size());
            for (Node n : possiblePaths) {
                //System.out.println("rame");
                n.setDepth((byte)(node.getDepth() + 1));
                if (closed.contains(n)) {
                    //System.out.println("რაღაცას ვურევ?");
                    //System.out.println(n);
                } else {
                    n.setParent(node);
                    opened.add(n);
                    closed.add(n);
                }
            }
            //System.out.println(opened);
        }

        ArrayList<Node> ans = new ArrayList<>();
        // ამოვიღოთ გზა რომელიც გაიარა node-მა სწორ პასუხამდე
        while (true) {
            ans.add(node);
            if (node.getParent() == null) {
                break;
            }
            node = node.getParent();
        }
        Collections.reverse(ans);
        System.out.println(ans.size());
        return ans;
    }
    
    public static ArrayList<Node> solveWithAlgorithmA2(Node root) {
        Set<Node> closed = new HashSet<>();   // დახურული სია. წვეროებისთვის რომელიც უკვე განხილულია
        Queue<Node> opened = new PriorityQueue<>(1000000, new NodeComparator2());   // ღია სია პრიორიტეტით

        opened.add(root);
        closed.add(root);
        root.setDepth((byte)0);
        Node node = root;
        while (true) {
            if (opened.isEmpty() || stopF2) {
                return null;
                //throw new IncorrectInputException("ამოცანას არ გააჩნია ამონახსნი!");
            }
            node = opened.poll();     // ავიღოთ და წავშალოთ პირველი ელემნტი სიიდან "ღია"
            closed.add(node);
            if (node.isSolved()) {
                break;                          // თუ ამოხსნილია გავაჩეროთ ციკლი
            }
            ArrayList<Node> possiblePaths = node.allPossiblePath();        // ყველა შესაძლო განვითარება
            for (Node n : possiblePaths) {
                //System.out.println("rame");
                n.setDepth((byte)(node.getDepth() + 1));
                if (closed.contains(n)) {
                    //System.out.println("რაღაცას ვურევ?");
                    //System.out.println(n);
                } else {
                    n.setParent(node);
                    opened.add(n);
                    closed.add(n);
                }
            }
            //System.out.println(opened);
        }

        ArrayList<Node> ans = new ArrayList<>();
        // ამოვიღოთ გზა რომელიც გაიარა node-მა სწორ პასუხამდე
        while (true) {
            ans.add(node);
            if (node.getParent() == null) {
                break;
            }
            node = node.getParent();
        }
        Collections.reverse(ans);
        System.out.println(ans.size());
        return ans;
    }

}
