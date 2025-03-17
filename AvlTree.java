public class AvlTree extends BinaryTree {

    @Override
    public void insert(String data) {
        super.insert(data);
        root = balance(root);
        root.setParent(null);
    }

    @Override
    public void remove(String data) {
        super.remove(data);
        root = balance(root);
        if (root != null) {
            root.setParent(null);
        }
    }

    private BinNode balance(BinNode node) {
        if (node == null) {
            return null;
        }

        int balanceFactor = getBalanceFactor(node);

        if (balanceFactor > 1) {
            if (getBalanceFactor(node.getLeft()) >= 0) {
                node = rotateRight(node);
            } else {
                node = leftRightRotate(node);
            }
        }

        if (balanceFactor < -1) {
            if (getBalanceFactor(node.getRight()) <= 0) {
                node = rotateLeft(node);
            } else {
                node = rightLeftRotate(node);
            }
        }

        updateHeight(node);

        return node;
    }

    private int getBalanceFactor(BinNode node) {
        if (node == null) {
            return 0;
        }
        int leftHeight = (node.getLeft() != null) ? node.getLeft().getHeight() : 0;
        int rightHeight = (node.getRight() != null) ? node.getRight().getHeight() : 0;
        return leftHeight - rightHeight;
    }

    private BinNode rotateLeft(BinNode node) {
        BinNode newParent = node.getRight();
        if (newParent == null) {
            return node;
        }

        node.setRight(newParent.getLeft());
        if (newParent.getLeft() != null) {
            newParent.getLeft().setParent(node);
        }

        newParent.setLeft(node);
        newParent.setParent(node.getParent());
        node.setParent(newParent);

        if (newParent.getParent() != null) {
            if (newParent.getParent().getLeft() == node) {
                newParent.getParent().setLeft(newParent);
            } else {
                newParent.getParent().setRight(newParent);
            }
        }

        updateHeight(node);
        updateHeight(newParent);

        return newParent;
    }

    private BinNode rotateRight(BinNode node) {
        BinNode newParent = node.getLeft();
        if (newParent == null) {
            return node;
        }

        node.setLeft(newParent.getRight());
        if (newParent.getRight() != null) {
            newParent.getRight().setParent(node);
        }

        newParent.setRight(node);
        newParent.setParent(node.getParent());
        node.setParent(newParent);

        if (newParent.getParent() != null) {
            if (newParent.getParent().getLeft() == node) {
                newParent.getParent().setLeft(newParent);
            } else {
                newParent.getParent().setRight(newParent);
            }
        }

        updateHeight(node);
        updateHeight(newParent);

        return newParent;
    }

    private BinNode leftRightRotate(BinNode node) {
        node.setLeft(rotateLeft(node.getLeft()));
        return rotateRight(node);
    }

    private BinNode rightLeftRotate(BinNode node) {
        node.setRight(rotateRight(node.getRight()));
        return rotateLeft(node);
    }

}