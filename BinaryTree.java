/**
 * An ordered binary tree class.
 *
 * @author (Endi Troqe)
 * @version (2025)
 */
public class BinaryTree {
     BinNode root;

     /**
      * default constructor
      */
     public BinaryTree() {
          root = null;
     }

     public BinNode getRoot() {
          return this.root;
     }

     /* Function to check if tree is empty */
     public boolean isEmpty() {
          return this.root == null;
     }

     /* Functions to insert data */
     public void insert(String data) {
          this.root = insert(this.root, data, null);
     }

     /* Function to insert data recursively */
     private BinNode insert(BinNode node, String data, BinNode parent) {
          if (node == null) {
               BinNode newNode = new BinNode();
               newNode.setData(data);
               newNode.setParent(parent);
               if (parent != null) {
                    newNode.setIsLeftChild(parent.getData().compareTo(data) > 0);
               }
               return newNode;
          }
          if (node.getData().compareTo(data) > 0) {
               node.setLeft(insert(node.getLeft(), data, node));
          } else {
               node.setRight(insert(node.getRight(), data, node));
          }
          updateHeight(node);
          return node;
     }

     /* Function to count number of nodes */
     public int countNodes() {
          return countNodes(this.root);
     }

     /* Function to count number of nodes recursively */
     private int countNodes(BinNode r) {
          if (r == null) {
               return 0;
          }
          return countNodes(r.getLeft()) + countNodes(r.getRight()) + 1;
     }

     /* Function to search for an element */
     public boolean search(String val) {
          return search(this.root, val);
     }

     /* Function to search for an element recursively */
     private boolean search(BinNode r, String val) {
          if (r == null) {
               return false;
          }
          if (r.getData().compareTo(val) > 0) {
               return search(r.getLeft(), val);
          } else if (r.getData().compareTo(val) < 0) {
               return search(r.getRight(), val);
          } else {
               return true;
          }
     }

     /* Function for inorder traversal */
     public void inorder() {
          inorder(this.root);
     }

     private void inorder(BinNode r) {
          if (r != null) {
               inorder(r.getLeft());
               System.out.print(r.getData() + " ");
               inorder(r.getRight());
          }
     }

     /* Function for preorder traversal */
     public void preorder() {
          preorder(root);
     }

     private void preorder(BinNode r) {
          if (r != null) {
               System.out.print(r.getData() + " ");
               preorder(r.getLeft());
               preorder(r.getRight());
          }
     }

     /* Function for postorder traversal */
     public void postorder() {
          postorder(root);
     }

     private void postorder(BinNode r) {
          if (r != null) {
               postorder(r.getLeft());
               postorder(r.getRight());
               System.out.print(r.getData() + " ");
          }
     }

     public void remove(String val) {
          this.root = remove(this.root, val);
     }

     private BinNode remove(BinNode r, String val) {
          if (r == null) {
               return null;
          }
          if (r.getData().compareTo(val) > 0) {
               r.setLeft(remove(r.getLeft(), val));
          } else if (r.getData().compareTo(val) < 0) {
               r.setRight(remove(r.getRight(), val));
          } else {
               if (r.getLeft() == null) {
                    if (r.getRight() != null) {
                         r.getRight().setParent(r.getParent());
                         if (r.getParent() != null) {
                              r.getRight().setIsLeftChild(r.getParent().getLeft() == r);
                         }
                    }
                    return r.getRight();
               } else if (r.getRight() == null) {
                    if (r.getLeft() != null) {
                         r.getLeft().setParent(r.getParent());
                         if (r.getParent() != null) {
                              r.getLeft().setIsLeftChild(r.getParent().getLeft() == r);
                         }
                    }
                    return r.getLeft();
               } else {
                    BinNode curr = r.getRight();
                    while (curr.getLeft() != null) {
                         curr = curr.getLeft();
                    }
                    r.setData(curr.getData());
                    r.setRight(remove(r.getRight(), curr.getData()));
               }
          }
          updateHeight(r);
          return r;
     }

     public int getHeight() {
          if (root == null) {
               return 0;
          } else {
               return root.getHeight();
          }
     }

     public void updateHeight(BinNode node) {
          if (node != null) {
               int leftHeight = 0;
               if (node.getLeft() != null) {
                    leftHeight = node.getLeft().getHeight();
               }
               int rightHeight = 0;
               if (node.getRight() != null) {
                    rightHeight = node.getRight().getHeight();
               }
               node.setHeight(Math.max(leftHeight, rightHeight) + 1);
          }
     }

}
