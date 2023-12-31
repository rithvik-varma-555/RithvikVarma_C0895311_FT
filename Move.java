import java.util.ArrayList;
import java.util.List;
class Move {
	/* *************************************** */
	// write your code here
	private final List<Box> boxes;

	public Move(int numBoxes) {
		this.boxes = new ArrayList<>(numBoxes);
	}

	public void addBox(Box box) {
		boxes.add(box);
	}

	public void print() {
		System.out.println("The objects of my move are:");
		for (Box box : boxes) {
			box.printContents();
		}
	}

	public int find(String itemName) {
        for (Box box : boxes) {
            int boxNumber = box.find(itemName);
            if (boxNumber >= 0) {
                return boxNumber + 1;
            }
        }
		return -1;
	}

	/* *************************************** */

	public static void main(String[] args) {
		// We create a move that will hold 2 main boxes
		Move move = new Move(2);

		/*
		 * We create and then fill 3 boxes
		 * Arguments of the constructor of Box:
		 * argument 1: number of items (simple items/objects or box) that the box can hold
		 * argument 2: box number
		 */

		// box 1 contains scissors
		Box box1 = new Box(1, 1);
		box1.addItem(new SingleObject("scissors"));

		// box 2 contains one book
		Box box2 = new Box(1, 2);
		box2.addItem(new SingleObject("book"));

		// box 3 contains one compass
		// and one box containing one scarf
		Box box3 = new Box(2, 3);
		box3.addItem(new SingleObject("compass"));
		Box box4 = new Box(1, 4);
		box4.addItem(new SingleObject("scarf"));
		box3.addItem(box4);

		// We add the three boxes to the first box of move - see below
		Box box5 = new Box(3, 5);
		box5.addItem(box1);
		box5.addItem(box2);
		box5.addItem(box3);

		// We add one box containing 3 objects to move
		Box box6 = new Box(3, 6);
		box6.addItem(new SingleObject("pencils"));
		box6.addItem(new SingleObject("pens"));
		box6.addItem(new SingleObject("rubber"));

		// We add the two most external boxes to the move
		move.addBox(box5);
		move.addBox(box6);

		// We print all the contents of the move
		move.print();

		// We print the number of the outermost cardboard containing the item "scarf"
		System.out.println("The sarf is in the cardboard number " + move.find("scarf"));
	}
}

class Box {
	private final List<Object> contents;
	private final int boxNum;

	public Box(int capacity,int boxNum) {
		this.contents = new ArrayList<>(capacity);
		this.boxNum = boxNum;
	}

	public void addItem(Object item) {
		contents.add(item);
	}

	public void printContents() {
		for (Object item : contents) {
			if (item instanceof SingleObject) {
				System.out.println(((SingleObject) item).getName());
			} else if (item instanceof Box) {
				((Box) item).printContents();
			}
		}
	}

	public int find(String itemName) {
		for (Object item : contents) {
			if (item instanceof SingleObject && ((SingleObject) item).getName().equals(itemName)) {
				return boxNum;
			} else if (item instanceof Box) {
				int foundInBox = ((Box) item).find(itemName);
				if (foundInBox >= 0) {
					return foundInBox;
				}
			}
		}
		return -1; // Item not found in this box
	}
}

class SingleObject {
	private final String name;
	public SingleObject(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}