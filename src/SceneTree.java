//Rezvan Nafee
//112935468
//Recitation 04
public class SceneTree {
    private SceneNode root;
    private SceneNode cursor;
    private SceneNode recentlyAdded;
    private SceneNode removedNode;

    public SceneTree() {
    }

    public SceneNode getCursor() {
        return cursor;
    }

    public SceneNode getRoot() {
        return root;
    }

    public void setCursor(SceneNode cursor) {
        this.cursor = cursor;
    }

    public void setRoot(SceneNode root) {
        this.root = root;
    }

    public void moverCursorBackwards() throws NoSuchNodeException {
        if (cursor.getParent() != null) {
            cursor = cursor.getParent();
        } else {
            throw new NoSuchNodeException("\nMoving Backward Exception: There is scene to back to!");
        }
    }

    public void moveCursorForwards(String option) throws NoSuchNodeException {
        switch (option) {
            case ("a"):
                if (cursor.getLeft() == null) {
                    throw new NoSuchNodeException("\nMoving Forward Exception: The option doesn't exist!");
                } else {
                    cursor = cursor.getLeft();
                }
                break;
            case ("b"):
                if (cursor.getMiddle() == null) {
                    throw new NoSuchNodeException("\nMoving Forward Exception: The option doesn't exist!");
                } else {
                    cursor = cursor.getMiddle();
                }
                break;
            case ("c"):
                if (cursor.getRight() == null) {
                    throw new NoSuchNodeException("\nMoving Forward Exception: The option doesn't exist!");
                } else {
                    cursor = cursor.getRight();
                }
                break;
            default:
                throw new NoSuchNodeException("\nMoving Forward Exception: The option doesn't exist!");
        }
    }

    public void addNewNode(String tittle, String sceneDescription) throws FullSceneException {
        SceneNode node = new SceneNode(tittle, sceneDescription);
        recentlyAdded = node;
        if (root == null) {
            this.root = node;
            this.cursor = node;
            return;
        } else {
            try {
                cursor.addSceneNode(node);
                node.setParent(cursor);
            } catch (FullSceneException ex) {
                SceneNode.setNumScenes(SceneNode.getNumScenes() - 1);
                throw new FullSceneException("\nAdding Scene Error: This scene if already full!");
            }
        }
    }

    public void removeScene(String option) throws NoSuchNodeException {
        option.toLowerCase();
        switch (option) {
            case ("a"):
                if (cursor.getLeft() == null)
                    throw new NoSuchNodeException("Removing Node Error: Node cannot be removed because it " +
                            "does not exist!");
                else {
                    removedNode = cursor.getLeft();
                    cursor.setLeft(cursor.getMiddle());
                    cursor.setMiddle(cursor.getRight());
                    cursor.setRight(null);
                }
                break;
            case ("b"):
                if (cursor.getMiddle() == null)
                    throw new NoSuchNodeException("Removing Node Error: Node cannot be removed because it " +
                            "does not exist!");
                else {
                    removedNode = cursor.getMiddle();
                    cursor.setMiddle(cursor.getRight());
                    cursor.setRight(null);
                }
                break;
            case ("c"):
                if (cursor.getRight() == null)
                    throw new NoSuchNodeException("Removing Node Error: Node cannot be removed because it " +
                            "does not exist!");
                else {
                    removedNode = cursor.getRight();
                    cursor.setRight(null);
                }
                break;
            default:
                throw new NoSuchNodeException("Removing Node Error: Invalid option given!");
        }
    }

    public String pathToRoot() {
        if (root == null)
            return "There are no Scenes!";
        String path = cursorPath(cursor);
        return path.substring(0, path.length() - 1);
    }

    public String cursorPath(SceneNode cursor) {
        String path = "";
        if (cursor == null) {
            return "";
        } else
            path += cursorPath(cursor.getParent()) + cursor.getTitle() + ", ";
        return path.trim();
    }


    public void moveScene(int sceneIDToMoveTo) throws NoSuchNodeException, FullSceneException, TreeRootException,
            TreeNodeError {
        if (cursor.equals(root)) {
            throw new TreeRootException("\nMoving Node Error: Cannot move the root of the tree!");
        }
        if (sceneIDToMoveTo <= 0) {
            throw new NoSuchNodeException("\nMoving Node Error: Invalid SceneID number given!");
        }
        if (root == null || cursor == null) {
            throw new NoSuchNodeException("\nMoving Node Error: There is nothing to move!");
        }
        SceneNode target = searchTree(this.root, sceneIDToMoveTo);
        if (target == null)
            throw new NoSuchNodeException("\nMoving Node Error: The target node does not exist!");
        else if (target.getLeft() != null && target.getRight() != null && target.getMiddle() != null)
            throw new FullSceneException("\nMoving Node Error: The target node is already full!");
        else if (target.getParent().equals(cursor))
            throw new TreeNodeError("\nMoving Node Error: Cannot move parent node to child node!");
        else {
            target.addSceneNode(cursor);
            SceneNode cursorsParent = cursor.getParent();
            if (cursor.getParent().getLeft().equals(cursor)) {
                cursor.getParent().setLeft(null);
            } else if (cursor.getParent().getMiddle().equals(cursor)) {
                cursor.getParent().setMiddle(null);
            } else {
                cursor.getRight().setRight(null);
            }
            cursor.setParent(target);
            if (cursorsParent.getLeft() == null) {
                cursorsParent.setLeft(cursorsParent.getMiddle());
                cursorsParent.setMiddle(cursorsParent.getRight());
                cursorsParent.setRight(null);
            } else if (cursorsParent.getMiddle() == null) {
                cursorsParent.setMiddle(cursorsParent.getRight());
                cursorsParent.setRight(null);
            }
        }
    }

    public SceneNode searchTree(SceneNode root, int sceneID) {
        if (root == null)
            return null;
        if (root.getSceneID() == sceneID)
            return root;
        SceneNode targetNode = searchTree(root.getLeft(), sceneID);
        if (targetNode != null)
            return targetNode;
        else {
            targetNode = searchTree(root.getMiddle(), sceneID);
            if (targetNode != null)
                return targetNode;
        }
        return searchTree(root.getRight(), sceneID);
    }

    public SceneNode getRecentlyAdded() {
        return recentlyAdded;
    }

    public void setRecentlyAdded(SceneNode recentlyAdded) {
        this.recentlyAdded = recentlyAdded;
    }

    public SceneNode getRemovedNode() {
        return removedNode;
    }

    public void setRemovedNode(SceneNode removedNode) {
        this.removedNode = removedNode;
    }

    public String toString() {
        return toStringHelper(root, 0, "");
    }

    public String toStringHelper(SceneNode node, int counter, String letter) {
        String answer = "";
        if (node == null) {
            return "";
        }
        answer += "\t".repeat(counter) + letter + (node.equals(cursor) ? node.toString() + "*" : node.toString())
                + "\n";
        answer +=  toStringHelper(node.getLeft(), counter+1, "A)");
        answer +=  toStringHelper(node.getMiddle(), counter+1, "B)");
        answer +=  toStringHelper(node.getRight(), counter+1, "C)");
        return answer;
    }
}
