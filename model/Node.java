package model;

import java.awt.Point;
import java.util.ArrayList;
import javafx.util.Pair;

public class Node {

    private byte n;                                       // დაფის სიგრძე
    private byte numbers[][] = new byte[20][20];           // დაფა
    private Node parent = null;                          // მშობელი წვერო
    private byte depth;                                   // წვეროს სიღრმე

    /**
     * უპარამეტრო კონსტრუქტორი - ავსებს 3X3 სწორ დაფას
     */
    public Node() {
        this.n = 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                numbers[i][j] = (byte) (i * 3 + j + 1);
            }
        }
        numbers[2][2] = 0;
    }

    /**
     * კონსტრუქტორი ავსებს n განზომილებიან ცხრილს. (სწორად)
     *
     * @param n დაფის ზომა
     */
    public Node(byte n) {
        this.n = n;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                numbers[i][j] = (byte) (i * n + j + 1);
            }
        }
        numbers[n - 1][n - 1] = 0;
    }

    public Node(byte n, byte numbers[][]) {
        this.n = n;
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[i].length; j++) {
                this.numbers[i][j] = numbers[i][j];
            }
        }
    }

    public byte[][] getNumbers() {
        return numbers;
    }

    public void setNumbers(byte[][] numbers) {
        this.numbers = numbers;
    }

    public byte getN() {
        return n;
    }

    public void setN(byte n) {
        this.n = n;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setElement(int i, int j, byte number) {
        numbers[i][j] = number;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(byte depth) {
        this.depth = depth;
    }

    @Override
    public boolean equals(Object obj) {
        Node node = (Node) obj;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (numbers[i][j] != node.getNumbers()[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.n;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                hash = hash + numbers[i][j] + 17;
                hash = hash * 29;
            }
        }
        return hash;
    }

    @Override
    public String toString() {
        //String s = "ევრისტიკული - " + heuristicFunction1() + ", სიღრმე - " + depth + ", ფუნქცია - " + algorithmAFunction1() + System.lineSeparator();
        String s = "";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s = s + " " + numbers[i][j];
            }
            s = s + System.lineSeparator();
        }
        return s;
    }

    /**
     * მეთოდი ამოწმებს დაფა არის თუ არა ამოხსნილი
     *
     * @return true - ემთხვევა სწორ დაფას, false - არ ემთხვევა
     */
    public boolean isSolved() {
        return this.equals(new Node(n));
    }

    /**
     * მეთოდი რომელიც ადგენს ყველა შესაძლო განვითარებას განვითარება შეიძლება
     * იყოს 2(კუთხიდან), 3(გვერდიდან) ან 4(შუიდან)
     *
     * @return განვითარებადი წვეროების სია
     */
    public ArrayList<Node> allPossiblePath() {
        ArrayList<Node> nodes = new ArrayList<>();

        int zeroI = 0, zeroJ = 0;                     // ცარიელის ინდექსები
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (numbers[i][j] == 0) {
                    zeroI = i;
                    zeroJ = j;
                    break;
                }
            }
        }

        if (zeroI != 0) {           // შეგვიძლია ჩამოვიდეთ ქვემოთ
            Node node = new Node(n, numbers);
            node.setElement(zeroI, zeroJ, numbers[zeroI - 1][zeroJ]);
            node.setElement(zeroI - 1, zeroJ, (byte) 0);
            nodes.add(node);
        }
        if (zeroI != n - 1) {           // შეგვიძლია ავიდეთ ზემოთ
            Node node = new Node(n, numbers);
            node.setElement(zeroI, zeroJ, numbers[zeroI + 1][zeroJ]);
            node.setElement(zeroI + 1, zeroJ, (byte) 0);
            nodes.add(node);
        }
        if (zeroJ != 0) {           // შეგვიძლია გავიდეთ მარჯვნივ
            Node node = new Node(n, numbers);
            node.setElement(zeroI, zeroJ, numbers[zeroI][zeroJ - 1]);
            node.setElement(zeroI, zeroJ - 1, (byte) 0);
            nodes.add(node);
        }
        if (zeroJ != n - 1) {           // შეგვიძლია გავიდეთ მარცხნივ
            Node node = new Node(n, numbers);
            node.setElement(zeroI, zeroJ, numbers[zeroI][zeroJ + 1]);
            node.setElement(zeroI, zeroJ + 1, (byte) 0);
            nodes.add(node);
        }
        return nodes;
    }

    /**
     * ევრისტიკული ფუნქცია, რომელიც ადგენს დაფის რამდენი ელემნტი არ ემთხვევა
     * სწორ ვარიანტს
     *
     * @return არ დამტხვეული ელემენტების რაოდენობა
     */
    public int heuristicFunction1() {
        int s = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (numbers[i][j] != i * n + j + 1 && numbers[i][j] != 0) {
                    s++;
                }
            }
        }
        return s + depth;
    }

    public int heuristicFunction2() {
        int s = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (numbers[i][j] != 0) {
                    int x = (numbers[i][j] - 1) / n;
                    int y = (numbers[i][j] - 1) % n;
                    s = s + Math.abs(i - x) + Math.abs(j - y);
                }
            }
        }

        for (int i = 0; i < n; i++) {
            int m = -1;
            for (int j = 0; j < n; j++) {
                int x = numbers[i][j];
                if (x != 0 && (x - 1) / n == i) {
                    if (x > m) {
                        m = x;
                    } else {
                        s = s + 3;
                    }
                }
            }
        }

        for (int j = 0; j < n; j++) {
            int m = -1;
            for (int i = 0; i < n; i++) {
                int x = numbers[i][j];
                if (x != 0 && (x - 1) % n == j) {
                    if (x > m) {
                        m = x;
                    } else {
                        s = s + 3;
                    }
                }
            }
        }

        return s + depth;
    }

    /**
     * მეთოდეი ეძებს 0-ს
     *
     * @return 0-ს ინდექსი დაფაზე
     */
    public Point getZeroIndex() {
        Point p = new Point();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (numbers[i][j] == 0) {
                    p.x = i;
                    p.y = j;
                }
            }
        }
        return p;
    }

}
