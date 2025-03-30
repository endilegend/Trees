public class AvlTree extends BinaryTree {

    @Override
    public void insert(String data) {
        root = insert(root, data, null);
        if (root != null) {
            root.setParent(null);
        }
    }

    private BinNode insert(BinNode node, String data, BinNode parent) {
        if (node == null) {
            BinNode newNode = new BinNode(data);
            newNode.setParent(parent);
            return newNode;
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(insert(node.getLeft(), data, node));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(insert(node.getRight(), data, node));
        } else {
            return node;
        }
        updateHeight(node);
        return balance(node);
    }

    @Override
    public void remove(String data) {
        root = remove(root, data);
        if (root != null) {
            root.setParent(null);
        }
    }

    private BinNode remove(BinNode node, String data) {
        if (node == null) {
            return null;
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(remove(node.getLeft(), data));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(remove(node.getRight(), data));
        } else {
            if (node.getLeft() == null || node.getRight() == null) {
                BinNode temp = (node.getLeft() != null) ? node.getLeft() : node.getRight();
                if (temp == null) {
                    node = null;
                } else {
                    node = temp;
                    node.setParent(temp.getParent());
                }
            } else {
                BinNode temp = minValueNode(node.getRight());
                node.setData(temp.getData());
                node.setRight(remove(node.getRight(), temp.getData()));
            }
        }
        if (node == null) {
            return null;
        }
        updateHeight(node);
        return balance(node);
    }

    private BinNode minValueNode(BinNode node) {
        BinNode current = node;
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current;
    }

    private int getBalanceFactor(BinNode node) {
        if (node == null) {
            return 0;
        }
        int leftHeight = (node.getLeft() != null) ? node.getLeft().getHeight() : 0;
        int rightHeight = (node.getRight() != null) ? node.getRight().getHeight() : 0;
        return leftHeight - rightHeight;
    }

    private BinNode balance(BinNode node) {
        int balanceFactor = getBalanceFactor(node);
        if (balanceFactor > 1) {
            if (getBalanceFactor(node.getLeft()) >= 0) {
                node = rotateRight(node);
            } else {
                node = leftRightRotate(node);
            }
        } else if (balanceFactor < -1) {
            if (getBalanceFactor(node.getRight()) <= 0) {
                node = rotateLeft(node);
            } else {
                node = rightLeftRotate(node);
            }
        }
        return node;
    }

    private BinNode rotateLeft(BinNode node) {
        BinNode newParent = node.getRight();
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

    public void updateHeight(BinNode node) {
        super.updateHeight(node);
    }
}
