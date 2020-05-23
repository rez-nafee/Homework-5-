//Rezvan Nafee
//112936468
//Recitation 04

import java.util.InputMismatchException;
import java.util.Scanner;

public class AdventureDesigner {
    static SceneTree adventureTree = new SceneTree();
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String key = "";
        String title = "";
        String description = "";
        System.out.println("Creating a story...\n");
        System.out.print("Please enter a title: ");
        title = input.nextLine().trim();
        System.out.print("Please enter a scene: ");
        description = input.nextLine();
        try {
            adventureTree.addNewNode(title, description);
        } catch (FullSceneException ex) {
        }
        System.out.println("\nScene #" + adventureTree.getRoot().getSceneID() + " added!");
        while (!key.equals("q")) {
            printTable();
            System.out.print("\nPlease enter a selection: ");
            key = input.nextLine().trim().toLowerCase();
            switch (key) {
                case ("a"):
                    try {
                        String sceneTitle = "";
                        String scene = "";
                        System.out.print("\nPLease enter a title: ");
                        sceneTitle = input.nextLine().trim();
                        System.out.print("PLease enter a scene: ");
                        scene = input.nextLine().trim();
                        adventureTree.addNewNode(sceneTitle, scene);
                    } catch (FullSceneException ex) {
                        System.out.println(ex.getLocalizedMessage());
                        continue;
                    }
                    System.out.println("\nScene #" + adventureTree.getRecentlyAdded().getSceneID() + " added!");
                    break;
                case ("r"):
                    String option = "";
                    System.out.print("\nPLease enter an option to be removed: ");
                    option = input.nextLine().toLowerCase().trim();
                    try {
                        adventureTree.removeScene(option);
                    } catch (NoSuchNodeException ex) {
                        System.out.println(ex.getLocalizedMessage());
                        continue;
                    }
                    System.out.println("\n" + adventureTree.getRemovedNode().getTitle() + " removed!");
                    break;
                case ("s"):
                    adventureTree.getCursor().displayFullScene();
                    System.out.println();
                    break;
                case ("b"):
                    try {
                        adventureTree.moverCursorBackwards();
                        System.out.println("\nSuccessfully moved back to " + adventureTree.getCursor().getTitle() + "!");
                    } catch (NoSuchNodeException ex) {
                        System.out.println(ex.getLocalizedMessage());
                        continue;
                    }
                    break;
                case ("f"):
                    try {
                        String choice = "";
                        System.out.print("\nWhich option do you wish to go to?: ");
                        choice = input.nextLine().trim().toLowerCase();
                        adventureTree.moveCursorForwards(choice);
                    } catch (NoSuchNodeException ex) {
                        System.out.println(ex.getLocalizedMessage());
                        continue;
                    }
                    System.out.println("\nSuccessfully moved to " + adventureTree.getCursor().getTitle() + "!");
                    break;
                case ("g"):
                    playGame();
                    break;
                case ("n"):
                    System.out.println("\n" + adventureTree.pathToRoot());
                    break;
                case ("m"):
                    int moveTo = 0;
                    System.out.print("\nMove current scene to: ");
                    try {
                        moveTo = input.nextInt();
                        input.nextLine();
                    } catch (InputMismatchException ex) {
                        System.out.println("\nInvalid Entry: Please try again!");
                        continue;
                    }
                    try {
                        adventureTree.moveScene(moveTo);
                    } catch (NoSuchNodeException ex) {
                        System.out.println(ex.getLocalizedMessage());
                        continue;
                    } catch (FullSceneException x) {
                        System.out.println(x.getLocalizedMessage());
                        continue;
                    } catch (TreeRootException b) {
                        System.out.println(b.getLocalizedMessage());
                        continue;
                    } catch (TreeNodeError a) {
                        System.out.println(a.getLocalizedMessage());
                        continue;
                    }
                    System.out.println("\nSuccessfully moved scene!");
                    break;
                case ("q"):
                    System.out.println("\nProgram terminating normally...");
                    break;
                case ("p"):
                    System.out.println("\n" + adventureTree.toString());
                    break;
                default:
                    System.out.println("\nNot a valid input!");
            }
        }
    }

    private static void playGame() {
        Scanner askUser = new Scanner(System.in);
        System.out.println("\nNow beginning game...");
        SceneNode storyBuilder = adventureTree.getRoot();
        while (!storyBuilder.isEnding()){
            storyBuilder.displayScene();
            String input = " ";
            boolean valid = false;
            while (!valid){
                System.out.print("\nPlease enter an option: ");
                input = askUser.nextLine().toLowerCase().trim();
                switch (input){
                    case ("a"):
                        valid = true;
                        storyBuilder = storyBuilder.getLeft();
                        break;
                    case ("b"):
                        if(storyBuilder.getMiddle() == null){
                            System.out.println("\nInvalid Input: Option B doesn't exist!");
                        }
                        storyBuilder =  storyBuilder.getMiddle();
                        valid = true;
                        break;
                    case ("c"):
                        if(storyBuilder.getRight() == null){
                            System.out.println("\nInvalid Input: Option C doesn't exist");
                        }
                        storyBuilder = storyBuilder.getRight();
                        valid = true;
                        break;
                    default:
                        System.out.println("\nInvalid Input: Please try again!");
                }
            }
        }
        storyBuilder.displayScene();
        System.out.println("\nThe End!");
        System.out.println("\nReturning back to creation mode...");
    }


    public static void printTable() {
        System.out.println();
        System.out.println("A) Add Scene");
        System.out.println("R) Remove Scene");
        System.out.println("S) Show Current Scene");
        System.out.println("P) Print Adventure Tree");
        System.out.println("B) Go Back A Scene");
        System.out.println("F) Go Forward A Scene");
        System.out.println("N) Print Path to Cursor");
        System.out.println("M) Move Scene");
        System.out.println("Q) Quit");
    }

}
