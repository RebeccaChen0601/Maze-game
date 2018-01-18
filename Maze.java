import java.util.Scanner;
import javax.swing.JOptionPane;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Maze {
	public static void main(String args[]){
		try {
			// avoid the situation that the file is not found
			FileReader file = new FileReader("mazeseven.txt");
			Scanner input = new Scanner(file);
			//scan and read the file given by the user
			int rows = input.nextInt();
			int columns = input.nextInt();
			int arr[][] = new int[rows][columns];
			//set an 2d array to store all the numbers in the file
			for (int a = 0; a < rows; a++) {
				for (int b = 0; b < columns; b++) {
					arr[a][b] = input.nextInt();
					System.out.print(arr[a][b]);
				    }
				System.out.println();
			     }
			System.out.println("\r");
			//print the 2d array
			int arrnew[][] = new int[arr.length + 2][arr[0].length + 2];
			//set an new array with 2 more numbers in row and column than the original array
			for (int r = 0; r < arrnew.length; r++) {
				for (int c = 0; c < arrnew[r].length; c++) {
					if (r == 0 || c == 0 || r == arrnew.length - 1 || c == arrnew[0].length - 1) {
						arrnew[r][c] = 1;
						//set the outer layer of the new array as 1
					    } else
						arrnew[r][c] = arr[r - 1][c - 1];
					//set the rest of the new array as the original array
				     }
			     }
			
			ArrayList<String> arrtrack = new ArrayList<String>();
			//set an array to track the path of the mouse
			int rowcheesenew = 0;
			int colcheesenew = 0;
			boolean MouseNotFound = true;
			//set the boolean expressions to jump out from the loop when mouse finds the cheese
			boolean MouseNotExist = true;
			//set the boolean expression to jump out from the loop when mouse is not found
			for (int loop = 1; loop < arrnew.length * arrnew.length; loop++) {
				for (int r = 1; r < arrnew.length - 1; r++) {
					for (int c = 1; c < arrnew[r].length - 1; c++) {
						//set nested loop to find the location of the mouse
						if (MouseNotFound == true) {
							if (arrnew[r][c] == 3) {
								MouseNotExist = false;
								//change the boolean expression to differentiate the situation when mouse is found with mouse is not found
								if (arrnew[r][c + 1] == 9 || arrnew[r][c - 1] == 9 || arrnew[r + 1][c] == 9
										|| arrnew[r - 1][c] == 9) {
									//find out the situation when the cheese is at the top or at the bottom or at left or at right of the mouse
									for (int cheeserow = 1; cheeserow < arrnew.length; cheeserow++) {
										for (int cheesecolumn = 1; cheesecolumn < arrnew[cheeserow].length; cheesecolumn++) {
											//find out the location of the cheese
											if (arrnew[cheeserow][cheesecolumn] == 9) {
												arrnew[r][c] = 6;//set the location where mouse stands at right now as 6
												arrnew[cheeserow][cheesecolumn] = 3;
												//set the location where cheese is right now as 3
												colcheesenew = cheesecolumn;
												rowcheesenew = cheeserow;
												//set two coordinates for resetting the cheese position later
											   }
										    }
									    }
									print2dArray(arrnew);
									JOptionPane.showMessageDialog(null, "Mouse finds Cheese");
									//display the message that 'Mouse finds Cheese'
									MouseNotFound = false;
									//change the boolean expression to jump out of the loop
								} else if (arrnew[r][c + 1] == 0) {
									//find out the situation when there's a path at the right of the mouse 
									arrnew[r][c] = 6;
									//set the number on the present position of mouse as 6
									arrnew[r][c + 1] = 3;
									//set the position of path as the number of the mouse
									arrtrack.add("right");
									//store the direction in the arrtrack
								}  else if (arrnew[r + 1][c] == 0) {
									//find out the situation when there's a path at the bottom of the mouse 
									arrnew[r][c] = 6;
									arrnew[r + 1][c] = 3;		
									arrtrack.add("down");
								} else if (arrnew[r][c - 1] == 0) {
									//find out the situation when there's a path on the left of the mouse 
									arrnew[r][c] = 6;
									arrnew[r][c - 1] = 3;
									arrtrack.add("left");
								}else if (arrnew[r - 1][c] == 0) {
									//find out the situation when there's a path at the top of the mouse 
									arrnew[r][c] = 6;
									arrnew[r - 1][c] = 3;	
									arrtrack.add("up");
								} else if (arrnew[r][c + 1] == 1 && arrnew[r][c - 1] == 1 && arrnew[r + 1][c] == 1
										&& arrnew[r - 1][c] == 1) {
									//find out the situation when there’s full of 1 surrounding the mouse 
									System.err.println("There's no way to go. ");
									MouseNotFound = false;
								} else if (arrnew[r][c + 1] != 0 && arrnew[r][c - 1] != 0 && arrnew[r + 1][c] != 0
										&& arrnew[r - 1][c] != 0 && arrnew[r][c + 1] != 6 && arrnew[r][c - 1] != 6 && arrnew[r + 1][c] != 6
										&& arrnew[r - 1][c] != 6){
									//find out the situation when there’s neither 6 nor 0 on the four directions
									MouseNotFound = false;
									System.out.println("Cheese not found. ");
									//display the alert that 'Cheese not found. '
								} else {
									//the rest situations when the mouse goes to a corner
									if (MouseNotFound == true) {
										 if (arrtrack.get(arrtrack.size() - 1) == "right") {
											 //find out the direction of last movement 
											 arrtrack.remove (arrtrack.size() - 1);		
											 //remove the wrong direction that leads the mouse to the dead end
											arrnew[r][c] = 8;
											arrnew[r][c - 1] = 3;		
											//let the mouse go to the opposite direction
										} else if (arrtrack.get(arrtrack.size() - 1) == "left") {
											arrtrack.remove (arrtrack.size() - 1);
											arrnew[r][c] = 8;
											arrnew[r][c + 1] = 3;
										} else if (arrtrack.get(arrtrack.size() - 1) == "down") {
											arrtrack.remove (arrtrack.size() - 1);
											arrnew[r][c] = 8;
											arrnew[r - 1][c] = 3;
										} else if (arrtrack.get(arrtrack.size() - 1) == "up") {
											arrtrack.remove (arrtrack.size() - 1);
											arrnew[r][c] = 8;
											arrnew[r + 1][c] = 3;
										}
									}
								}
								if(MouseNotFound == true)
								print2dArray(arrnew);
							}
						}
					}
				}
			}
			if(MouseNotExist==true){
				//find out the situation when no mouse exist in the array
				System.err.println("Mouse not found. ");
				//print the alert that 'Mouse not found.'
			  }		
			
            System.out.println("Below is the direct path. \r\r");
			arrnew[rowcheesenew][colcheesenew] = 9;
			//reset position of the cheese
			for (int r = 0; r < arr.length; r++) {
			    for (int c = 0; c < arr[r].length; c++) {
					 if(arr[r][c] == 3){
						 //find out where the mouse is originally
						arrnew[r+1][c+1] = 3;
						//reset the position of the mouse on the arrnew
				       }
				    }
			    }
			
			MouseNotFound = true;
			for (int loop = 1; loop < arrnew.length * arrnew.length; loop++) {		
				for (int r = 1; r < arrnew.length - 1; r++) {
					for (int c = 1; c < arrnew[r].length - 1; c++) {
						//set nested loop to find the location of the mouse
						if (MouseNotFound == true) {
							if (arrnew[r][c] == 3) {
								MouseNotExist = false;
								if (arrnew[r][c + 1] == 9 || arrnew[r][c - 1] == 9 || arrnew[r + 1][c] == 9
										|| arrnew[r - 1][c] == 9) {
									//find out the situation when the cheese is at the top or at the bottom or at left or at right of the mouse
									for (int cheeserow = 1; cheeserow < arrnew.length; cheeserow++) {
										for (int cheesecolumn = 1; cheesecolumn < arrnew[cheeserow].length; cheesecolumn++) {
											//find out the location of the cheese
											if (arrnew[cheeserow][cheesecolumn] == 9) {
												arrnew[r][c] = 8;//set the location where mouse stands at right now as 6
												arrnew[cheeserow][cheesecolumn] = 3;
												//set the location where cheese is right now as 3
											   }
										    }
								       	 }
									print2dArray(arrnew);
									JOptionPane.showMessageDialog(null, "Mouse finds Cheese");
									//display the message that 'Mouse finds Cheese'
									MouseNotFound = false;
									//change the boolean expression to jump out of the loop
								} else if (arrnew[r][c + 1] == 6) {
									arrnew[r][c] = 8;
									arrnew[r][c + 1] = 3;
								} else if (arrnew[r + 1][c] == 6) {
									arrnew[r][c] = 8;
									arrnew[r + 1][c] = 3;
								} else if (arrnew[r][c - 1] == 6) {
									arrnew[r][c] = 8;
									arrnew[r][c - 1] = 3;
								} else if (arrnew[r - 1][c] == 6) {
									arrnew[r][c] = 8;
									arrnew[r - 1][c] = 3;
								} else if (arrnew[r][c + 1] == 1 && arrnew[r][c - 1] == 1 && arrnew[r + 1][c] == 1
											&& arrnew[r - 1][c] == 1) {
										System.err.println("There's no way to go. ");
										MouseNotFound = false;
								} else if (arrnew[r][c + 1] != 6 && arrnew[r][c - 1] != 6 && arrnew[r + 1][c] != 6
											&& arrnew[r - 1][c] != 6){
										MouseNotFound = false;
										System.out.println("Cheese not found. ");
								} 
								if(MouseNotFound == true)
								print2dArray(arrnew);
							}
						}
					}
				}
			}
			if(MouseNotExist==true){
				//find out the situation when no mouse exist in the array
				System.err.println("Mouse not found. ");
				//print the alert that 'Mouse not found.'
			  }		
		}

		catch (FileNotFoundException e) {
			// find out the situation when the file does not exist
			System.err.print("File not found. ");
			// print the alert that 'File not found.'
		}
	}

	public static void print2dArray(int[][] arrnew) {
		//set a new method for printing 2d array
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//set a timer to stop the thread for half second
		for (int r = 1; r < arrnew.length - 1; r++) {
			for (int c = 1; c < arrnew[r].length - 1; c++) {
				System.out.print(arrnew[r][c]);
			}
			System.out.println();
		}
		System.out.println("\r");
	}
}
