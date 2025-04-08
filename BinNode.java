/**
 * Node for a binary tree of strings.
 *
 * @author (Endi Troqe)
 * @version (2025)
 */
public class BinNode {
    private String data;
    private BinNode left;
    private BinNode right;
    private BinNode parent;
    private int height = 1;
    private int color = 1;
    private boolean isLeftChild;

    public BinNode() {
        data = "";
        left = null;
        right = null;
        parent = null;
        height = 1;
        isLeftChild = false;
    }

    public BinNode(String d) {
        data = d;
        left = null;
        right = null;
        parent = null;
        height = 1;
        isLeftChild = false;
    }

    public void setData(String d) {
        this.data = d;
    }

    public String getData() {
        return this.data;
    }

    public void setLeft(BinNode l) {
        this.left = l;
    }

    public void setParent(BinNode p) {
        this.parent = p;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public BinNode getLeft() {
        return this.left;
    }

    public void setRight(BinNode r) {
        this.right = r;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return this.height;
    }

    public BinNode getRight() {
        return this.right;
    }

    public BinNode getParent() {
        return this.parent;
    }

    public int getColor() {
        return this.color;
    }

    public void setIsLeftChild(boolean isLeftChild) {
        this.isLeftChild = isLeftChild;
    }

    public boolean getIsLeftChild() {
        return this.isLeftChild;
    }

}
