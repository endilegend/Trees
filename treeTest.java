public class treeTest {
    public static void main(String[] args) {
        AvlTree tree = new AvlTree();

        // Test insert
        for (int i = 1; i <= 9; i++) {
            tree.insert(String.valueOf(i));
            System.out.print("Inorder traversal after inserting " + i + ": ");
            tree.inorder();
            System.out.println();
        }

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
        System.out.println("Search for '10': " + tree.search("10"));
        System.out.println("Search for '20': " + tree.search("20"));

        // Test remove
        tree.remove("10");
        System.out.print("Inorder traversal after removing '10': ");
        tree.inorder();
        System.out.println();
        tree.remove("8");
        System.out.print("Inorder traversal after removing '8': ");
        tree.inorder();
        System.out.println();
    }
}
