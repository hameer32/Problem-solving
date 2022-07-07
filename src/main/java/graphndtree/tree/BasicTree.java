package graphndtree.tree;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class BasicTree {
    public static void main(String[] args) {
        Node n1=new Node(1);
        Node n2=new Node(2);
        Node n3=new Node(3);
        Node n4=new Node(4);
        Node n5=new Node(5);
        Node n6=new Node(6);
        Node n7=new Node(7);
        n1.left=n5;
        n1.right=n6;
        n5.left=n2;
        n6.left=n3;
        n6.right=n4;
        n3.left=n7;
        findHeightOfTree(n1);
        System.out.println(findHeightUsingRecursion(n1));
    }

    private static int findHeightUsingRecursion(Node root) {
        if(root==null){
            return 1;
        } return 1;
    }

    private static void findHeightOfTree(Node temp) {
        Node root=temp;
        Set<Node> visited=new HashSet<>();
        Stack<Node> stack=new Stack<>();
        stack.push(root);
        int max=0;
        int count=1;
        while(!stack.isEmpty()){
            if(!visited.contains(root)){
                visited.add(root);
            }
            if(root.left!=null && !visited.contains(root.left)){
                root=root.left;
                stack.push(root);
                count++;
            }else if(root.right!=null && !visited.contains(root.right)){
                root=root.right;
                stack.push(root);
                count++;
            }else{
                stack.pop();
                if(stack.size()==0){
                    break;
                }
                root=stack.peek();
                count--;
            }
            if(max<count)max=count;
        }
        System.out.println(max);
    }
}
class Node{
    int value;
    Node left;
    Node right;
    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                ", left=" + left +
                ", right=" + right +
                '}';
    }

    public Node(int value) {
        this.value = value;
    }
}

