public class treeTest {
    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();

        // Test insert
        tree.insert("D");
        tree.insert("B");
        tree.insert("A");
        tree.insert("C");
        tree.insert("F");
        tree.insert("E");
        tree.insert("G");

        // Test inorder traversal
        System.out.print("Inorder traversal: ");
        tree.inorder();
        System.out.println();

        // Test preorder traversal
        System.out.print("Preorder traversal: ");
        tree.preorder();
        System.out.println();

        // Test postorder traversal
        System.out.print("Postorder traversal: ");
        tree.postorder();
        System.out.println();

        // Test countNodes
        System.out.println("Number of nodes: " + tree.countNodes());

        // Test search
        System.out.println("Search for 'E': " + tree.search("E"));
        System.out.println("Search for 'Z': " + tree.search("Z"));

        // Test remove
        tree.remove("B");
        System.out.print("Inorder traversal after removing 'B': ");
        tree.inorder();
        System.out.println();
    }
}
// A B C D E F G
// D B A C F E G
// A C B E G F D
// 7
// true
// false
// A C D E F G
