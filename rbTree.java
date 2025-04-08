public class rbTree extends BinaryTree {
    private static final int RED = 1;
    private static final int BLACK = 0;

    // Last inserted node
    private BinNode lastInserted;

    // Insert method
    @Override
    public void insert(String data) {
        this.root = insertNode(root, data, null);
        // Fix violations of rb rules
        fixInsert(lastInserted);

        // Set root to be black always
        root.setColor(BLACK);
    }

    // Insert helper function
    private BinNode insertNode(BinNode node, String data, BinNode parent) {
        if (node == null) {
            BinNode newNode = new BinNode(data);
            newNode.setParent(parent);
            if (parent != null) {
                newNode.setIsLeftChild(parent.getData().compareTo(data) > 0);
            }
            lastInserted = newNode;
            return newNode;
        }

        int cmp = data.compareTo(node.getData());
        if (cmp < 0) {
            node.setLeft(insertNode(node.getLeft(), data, node));
        } else if (cmp > 0) {
            node.setRight(insertNode(node.getRight(), data, node));
        }
        // Updates the height of the node
        updateHeight(node);
        return node;
    }

    // Fix rb rule violations
    private void fixInsert(BinNode node) {
        while (node != null && node != root && node.getParent().getColor() == RED) {
            BinNode parent = node.getParent();
            BinNode gParent = getGrandparent(node);
            if (gParent == null) {
                break;
            }

            // If parent is a left child
            if (parent == gParent.getLeft()) {
                BinNode uncle = gParent.getRight();

                // If uncle is red recolor
                if (uncle != null && uncle.getColor() == RED) {
                    parent.setColor(BLACK);
                    uncle.setColor(BLACK);
                    gParent.setColor(RED);
                    node = gParent;
                } else {
                    // If uncle is black rotate
                    if (node == parent.getRight()) {
                        node = parent;
                        rotateLeft(node);
                    }
                    parent.setColor(BLACK);
                    gParent.setColor(RED);
                    rotateRight(gParent);
                }
            } else {
                // If parent is a right child
                BinNode uncle = gParent.getLeft();

                // If uncle is red recolor
                if (uncle != null && uncle.getColor() == RED) {
                    parent.setColor(BLACK);
                    uncle.setColor(BLACK);
                    gParent.setColor(RED);
                    node = gParent;
                } else {
                    // If uncle is black rotate
                    if (node == parent.getLeft()) {
                        node = parent;
                        rotateRight(node);
                    }
                    parent.setColor(BLACK);
                    gParent.setColor(RED);
                    rotateLeft(gParent);
                }
            }
        }
        // Set root to black
        root.setColor(BLACK);
    }

    // Left rotation
    private void rotateLeft(BinNode node) {
        BinNode pivot = node.getRight();
        if (pivot == null) {
            return;
        }

        // Move pivots left subtree to nodes right
        node.setRight(pivot.getLeft());
        if (pivot.getLeft() != null) {
            pivot.getLeft().setParent(node);
        }

        // Link pivot to nodes parent
        pivot.setParent(node.getParent());
        if (node.getParent() == null) {
            root = pivot;
        } else if (node == node.getParent().getLeft()) {
            node.getParent().setLeft(pivot);
        } else {
            node.getParent().setRight(pivot);
        }

        // Put node on pivots left
        pivot.setLeft(node);
        node.setParent(pivot);
    }

    // Right rotate
    private void rotateRight(BinNode node) {
        BinNode pivot = node.getLeft();
        if (pivot == null) {
            return;
        }

        // Move pivots left subtree to nodes left
        node.setLeft(pivot.getRight());
        if (pivot.getRight() != null) {
            pivot.getRight().setParent(node);
        }

        // Link pivot to nodes parent
        pivot.setParent(node.getParent());
        if (node.getParent() == null) {
            root = pivot;
        } else if (node == node.getParent().getLeft()) {
            node.getParent().setLeft(pivot);
        } else {
            node.getParent().setRight(pivot);
        }

        // Put node on pivot right
        pivot.setRight(node);
        node.setParent(pivot);
    }

    // Delete method
    @Override
    public void remove(String data) {

        BinNode z = searchNode(root, data);
        if (z == null) {
            // Node doesnt exist
            return;
        }

        BinNode y = z;
        int yOriginalColor = y.getColor();
        BinNode x; // the child that replaces y
        BinNode xParent; // we need x's parent for fixRemove

        // If z only has one child remove it
        if (z.getLeft() == null) {
            x = z.getRight();
            xParent = z.getParent();
            transplant(z, z.getRight());
        } else if (z.getRight() == null) {
            x = z.getLeft();
            xParent = z.getParent();
            transplant(z, z.getLeft());
        } else {
            // Find the minmum on the right subtree
            BinNode successor = minimum(z.getRight());
            y = successor;
            yOriginalColor = y.getColor();
            x = y.getRight();

            // If min is not the ndoes child
            if (y.getParent() != z) {
                transplant(y, y.getRight());
                y.setRight(z.getRight());
                if (y.getRight() != null) {
                    y.getRight().setParent(y);
                }
                xParent = y.getParent();
            } else {
                xParent = y;
            }

            // Move y into z spot
            transplant(z, y);
            y.setLeft(z.getLeft());
            y.getLeft().setParent(y);
            // Copy z color
            y.setColor(z.getColor());
        }

        // If we removed a black node fix double black
        if (yOriginalColor == BLACK) {
            fixRemove(x, xParent);
        }
    }

    // Replace u with v
    private void transplant(BinNode u, BinNode v) {
        if (u.getParent() == null) {
            root = v;
        } else if (u == u.getParent().getLeft()) {
            u.getParent().setLeft(v);
        } else {
            u.getParent().setRight(v);
        }
        if (v != null) {
            v.setParent(u.getParent());
        }
    }

    // Searches for node
    private BinNode searchNode(BinNode root, String data) {
        BinNode curr = root;
        while (curr != null) {
            int cmp = data.compareTo(curr.getData());
            if (cmp < 0) {
                curr = curr.getLeft();
            } else if (cmp > 0) {
                curr = curr.getRight();
            } else {
                return curr;
            }
        }
        return null;
    }

    // Return the minimum
    private BinNode minimum(BinNode node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    // Fixes the propertys od rb tree
    private void fixRemove(BinNode x, BinNode xParent) {
        // We'll bubble 'x' up until it's no longer double black or until it becomes
        // root.
        while (x != root && (x == null || x.getColor() == BLACK)) {
            if (x == (xParent != null ? xParent.getLeft() : null)) {
                // Sibling
                BinNode w = (xParent != null) ? xParent.getRight() : null;

                // Sibilng is red
                if (w != null && w.getColor() == RED) {
                    w.setColor(BLACK);
                    xParent.setColor(RED);
                    rotateLeft(xParent);
                    w = xParent.getRight();
                }

                // Sibling and children are black
                if ((w == null || w.getLeft() == null || w.getLeft().getColor() == BLACK)
                        && (w == null || w.getRight() == null || w.getRight().getColor() == BLACK)) {
                    if (w != null) {
                        w.setColor(RED);
                    }
                    x = xParent;
                    xParent = xParent != null ? xParent.getParent() : null;
                } else {
                    // Sibling is black, siblings left child is red, sibling right is black
                    if (w != null) {
                        if (w.getRight() == null || w.getRight().getColor() == BLACK) {
                            if (w.getLeft() != null) {
                                w.getLeft().setColor(BLACK);
                            }
                            w.setColor(RED);
                            rotateRight(w);
                            w = xParent.getRight();
                        }
                        // Siblings right child is red
                        w.setColor(xParent.getColor());
                        xParent.setColor(BLACK);
                        if (w.getRight() != null) {
                            w.getRight().setColor(BLACK);
                        }
                        rotateLeft(xParent);
                    }
                    x = root;
                }
            } else {
                // Symmetric case if x is the right child
                BinNode w = (xParent != null) ? xParent.getLeft() : null;
                // CASE 1
                if (w != null && w.getColor() == RED) {
                    w.setColor(BLACK);
                    xParent.setColor(RED);
                    rotateRight(xParent);
                    w = xParent.getLeft();
                }
                // CASE 2
                if ((w == null || w.getLeft() == null || w.getLeft().getColor() == BLACK)
                        && (w == null || w.getRight() == null || w.getRight().getColor() == BLACK)) {
                    if (w != null) {
                        w.setColor(RED);
                    }
                    x = xParent;
                    xParent = xParent != null ? xParent.getParent() : null;
                } else {
                    // CASE 3
                    if (w != null) {
                        if (w.getLeft() == null || w.getLeft().getColor() == BLACK) {
                            if (w.getRight() != null) {
                                w.getRight().setColor(BLACK);
                            }
                            w.setColor(RED);
                            rotateLeft(w);
                            w = xParent.getLeft();
                        }
                        // CASE 4
                        w.setColor(xParent.getColor());
                        xParent.setColor(BLACK);
                        if (w.getLeft() != null) {
                            w.getLeft().setColor(BLACK);
                        }
                        rotateRight(xParent);
                    }
                    x = root; // done
                }
            }
        }
        // If x is non-null, repaint it black
        if (x != null) {
            x.setColor(BLACK);
        }
    }

    // Get grandparent helper
    private BinNode getGrandparent(BinNode node) {
        if (node == null || node.getParent() == null) {
            return null;
        }
        return node.getParent().getParent();
    }
}
