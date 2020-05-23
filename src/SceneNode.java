//Rezvan Nafee
//112936468
//Recitation 04
public class SceneNode {
    private static int numScenes = 1;
    private String title;
    private String sceneDescription;
    private int sceneID;
    private SceneNode parent;
    private SceneNode left;
    private SceneNode middle;
    private SceneNode right;

    public SceneNode() {
    }

    public SceneNode(String title, String sceneDescription) {
        this.title = title;
        this.sceneDescription = sceneDescription;
        sceneID = numScenes++;
        left = null;
        middle = null;
        right = null;

    }

    public void addSceneNode(SceneNode scene) throws FullSceneException {
        if (left != null && middle != null & right != null) {
            throw new FullSceneException("Adding Scene Error: This scene if already full!");
        }
        while (true) {
            if (left == null) {
                this.left = scene;
                break;
            }
            if (middle == null) {
                this.middle = scene;
                break;
            }
            if (right == null) {
                this.right = scene;
                break;
            }
        }
    }

    public boolean isEnding() {
        return (this.left == null && this.right == null && this.middle == null);
    }

    public static int getNumScenes() {
        return numScenes;
    }

    public void displayScene() {
        System.out.println("\n"+getTitle()+"\n"+getSceneDescription());
        if(left != null){
            System.out.println("A)" + left.getTitle());
        }
        if(middle != null){
            System.out.println("B)" + middle.getTitle());
        }
        if(right != null){
            System.out.println("C)" + right.getTitle());
        }
    }

    public void displayFullScene() {
        SceneNode[] sceneNodes = {left, middle, right};
        String path = "";
        for (int i = 0; i < sceneNodes.length; i++) {
            if (sceneNodes[i] == null) {
                path += "";
            } else {
                path += "\'" + sceneNodes[i].getTitle() + "\' (#" + sceneNodes[i].getSceneID() + ")";
                path += ", ";
            }
        }
        System.out.println("\nScene ID #" + this.getSceneID());
        System.out.println("Title: " + this.getTitle());
        System.out.println("Scene: " + this.getSceneDescription());
        System.out.print("Leads to: ");
        if (left == null && right == null && middle == null) {
            System.out.print("NONE");
        } else {
            for (int i = 0; i < sceneNodes.length; i++) {
                if (sceneNodes[i] == null)
                    continue;
                System.out.print(((i > 0) ? ", " : "") + "\'" + sceneNodes[i].getTitle() + "\' " +
                        "(#" + sceneNodes[i].getSceneID() + ")");
            }
        }
    }

    public int getSceneID() {
        return sceneID;
    }

    public SceneNode getLeft() {
        return left;
    }

    public SceneNode getRight() {
        return right;
    }

    public String getTitle() {
        return title;
    }

    public void setLeft(SceneNode left) {
        this.left = left;
    }

    public static void setNumScenes(int numScenes) {
        SceneNode.numScenes = numScenes;
    }

    public void setRight(SceneNode right) {
        this.right = right;
    }

    public void setSceneID(int sceneID) {
        this.sceneID = sceneID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SceneNode getMiddle() {
        return middle;
    }

    public SceneNode getParent() {
        return parent;
    }

    public String getSceneDescription() {
        return sceneDescription;
    }

    public void setMiddle(SceneNode middle) {
        this.middle = middle;
    }

    public void setParent(SceneNode parent) {
        this.parent = parent;
    }

    public void setSceneDescription(String sceneDescription) {
        this.sceneDescription = sceneDescription;
    }

    @Override
    public boolean equals(Object o) {
        return this.sceneID == ((SceneNode)o).getSceneID();
    }

    public String toString() {
        return title + " (#" + sceneID +")";
    }
}
