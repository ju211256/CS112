package kindergarten;
/**
 * This class represents a Classroom, with:
 * - an SNode instance variable for students in line,
 * - an SNode instance variable for musical chairs, pointing to the last student in the list,
 * - a boolean array for seating availability (eg. can a student sit in a given seat), and
 * - a Student array parallel to seatingAvailability to show students filed into seats 
 * --- (more formally, seatingAvailability[i][j] also refers to the same seat in studentsSitting[i][j])
 * 
 * @author Ethan Chou
 * @author Kal Pandit
 * @author Maksims Kurjanovics Kravcenko
 */
public class Classroom {
    private SNode studentsInLine;             // when students are in line: references the FIRST student in the LL
    private SNode musicalChairs;              // when students are in musical chairs: references the LAST student in the CLL
    private boolean[][] seatingAvailability;  // represents the classroom seats that are available to students
    private Student[][] studentsSitting;      // when students are sitting in the classroom: contains the students

    /**
     * Constructor for classrooms. Do not edit.
     * @param l passes in students in line
     * @param m passes in musical chairs
     * @param a passes in availability
     * @param s passes in students sitting
     */
    public Classroom ( SNode l, SNode m, boolean[][] a, Student[][] s ) {
		studentsInLine      = l;
        musicalChairs       = m;
		seatingAvailability = a;
        studentsSitting     = s;
	}
    /**
     * Default constructor starts an empty classroom. Do not edit.
     */
    public Classroom() {
        this(null, null, null, null);
    }

    /**
     * This method simulates students coming into the classroom and standing in line.
     * 
     * Reads students from input file and inserts these students in alphabetical 
     * order to studentsInLine singly linked list.
     * 
     * Input file has:
     * 1) one line containing an integer representing the number of students in the file, say x
     * 2) x lines containing one student per line. Each line has the following student 
     * information separated by spaces: FirstName LastName Height
     * 
     * @param filename the student information input file
     */
    public void makeClassroom ( String filename ) 
    {
        StdIn.setFile(filename);

        int numOfStudents = StdIn.readInt();

        //instantiates a pointer
        SNode ptr = null;


        //creating the LL
        for (int i = 0; i < numOfStudents; i++)
        {
            //read next line of student info, constructs it into a student object
            Student s = new Student(StdIn.readString(), StdIn.readString(), StdIn.readInt());

            if (studentsInLine == null) 
            {
                //uses student object to construct a new Node
                SNode firstStudent = new SNode(s, null);

                //sets studentsInLine to point to the first student on LL
                studentsInLine = firstStudent;

                ptr = studentsInLine;
            } else
            {
                //uses student object to construct a new node
                SNode nextStudent = new SNode(s, null);

                //links the node that the pointer is on with thew newly created node
                ptr.setNext(nextStudent);

                //moves pointer to the newly created node
                ptr = nextStudent;
            }
        }
        
        //sorting the LL, places pointer back to the front of the LL
        ptr = studentsInLine;

        while(ptr.getNext() != null)
        {
            //instantiates an index to compare to pointer
            SNode comparer = ptr.getNext();

            while (comparer != null)
            {
                Student s1 = ptr.getStudent();
                Student s2 = comparer.getStudent();

                if (s1.compareNameTo(s2) > 0)
                {
                    Student holder = s1;
                    s1 = s2;
                    s2 = holder;

                    ptr.setStudent(s1);
                    comparer.setStudent(s2);
                } 

                comparer = comparer.getNext();
            }

            ptr = ptr.getNext();
        }
    // WRITE YOUR CODE HERE
    }

    /**
     * 
     * This method creates and initializes the seatingAvailability (2D array) of 
     * available seats inside the classroom. Imagine that unavailable seats are broken and cannot be used.
     * 
     * Reads seating chart input file with the format:
     * An integer representing the number of rows in the classroom, say r
     * An integer representing the number of columns in the classroom, say c
     * Number of r lines, each containing c true or false values (true denotes an available seat)
     *  
     * This method also creates the studentsSitting array with the same number of
     * rows and columns as the seatingAvailability array
     * 
     * This method does not seat students on the seats.
     * 
     * @param seatingChart the seating chart input file
     */
    public void setupSeats(String seatingChart) {

        StdIn.setFile(seatingChart);

        int rows = StdIn.readInt();
        int columns = StdIn.readInt();

        seatingAvailability = new boolean[rows][columns];
        
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < columns; j++)
            {
                seatingAvailability[i][j] = StdIn.readBoolean();
            }
        }

        studentsSitting = new Student[rows][columns];
	// WRITE YOUR CODE HERE
    }

    /**
     * 
     * This method simulates students taking their seats in the classroom.
     * 
     * 1. seats any remaining students from the musicalChairs starting from the front of the list
     * 2. starting from the front of the studentsInLine singly linked list
     * 3. removes one student at a time from the list and inserts them into studentsSitting according to
     *    seatingAvailability
     * 
     * studentsInLine will then be empty
     */
    public void seatStudents () {
    
    for (int i = 0; i < seatingAvailability.length; i++)
    {
        for (int j = 0; j < seatingAvailability[0].length; j++)
        {
            if (seatingAvailability[i][j])
            {
                if (musicalChairs != null)
                {
                    studentsSitting[i][j] = musicalChairs.getStudent();

                    seatingAvailability[i][j] = false;

                    musicalChairs = null;
                } else if (studentsInLine != null)
                {
                studentsSitting[i][j] = studentsInLine.getStudent();

                seatingAvailability[i][j] = false;

                studentsInLine = studentsInLine.getNext();
                }
            }
        }
    }
	// WRITE YOUR CODE HERE
    }

    /**
     * Traverses studentsSitting row-wise (starting at row 0) removing a seated
     * student and adding that student to the end of the musicalChairs list.
     * 
     * row-wise: starts at index [0][0] traverses the entire first row and then moves
     * into second row.
     */
    public void insertMusicalChairs () {

        for (int i = 0; i < studentsSitting.length; i++)
        {
            for (int j = 0; j < studentsSitting[0].length; j++)
            {     
                if (musicalChairs == null && studentsSitting[i][j] != null) //CLL is empty
                {
                    SNode firstStudent = new SNode(studentsSitting[i][j], musicalChairs);

                    musicalChairs = firstStudent;

                    musicalChairs.setNext(firstStudent);

                    studentsSitting[i][j] = null;
                    seatingAvailability[i][j] = true;
                }
                else if (studentsSitting[i][j] != null) //CLL is not empty, Add a student to the last of CLL
                {                    
                    //uses student object to construct a new node to add to last of CLL, then link it to the first node of CLL
                    SNode newLast = new SNode(studentsSitting[i][j], musicalChairs.getNext());

                    musicalChairs.setNext(newLast);

                    musicalChairs = newLast;

                    studentsSitting[i][j] = null;
                    seatingAvailability[i][j] = true;
                }
            }
        }
        // WRITE YOUR CODE HERE
     }

    private SNode insertionSort(SNode input)
    {
        //A null Node that comes before input's head (Static reference node)
        SNode nullNodeBeforeFront = new SNode();
        // nullNodeBeforeFront.setNext(input);

        //A Node reference to the head of the input, will serve as a reference for walker, will move through the LL after each iteration through next
        SNode ptr = input;

        //A Node reference to the node that comes before the input's head, and will be the main checker to traverse through the entire LL
        SNode walker = nullNodeBeforeFront;

        //A Node reference to the node that comes after the input's head, and will traverse through the LL ONLY AFTER 1 ITERATION OF INSERTION
        SNode next = null;

        //While the current node ptr is on is not null and has height value
        while (ptr != null)
        {
            //Refresh next to save a reference point to the Node that comes after ptr, ready for next iteration of insertion
            next = ptr.getNext();

            //Check every single node in the LL INCLUDING head until walker finds a student with height greater than the current Node that the pointer is on
            if (walker.getNext() != null) //Account for edge case
            {
                while (walker.getNext() != null && walker.getNext().getStudent().getHeight() < ptr.getStudent().getHeight())
                {
                    //Moving the walker across the entire LL
                    walker = walker.getNext();
                }
                //Will break out of while loop once walker lands on a node that is right before a node that has a higher height student than that of ptr
            }
           
            //Once found, connect so that ptr can be inserted between walker and walker.next
            ptr.setNext(walker.getNext());

            // walker (height-) -> ptr (height) -> walker.next (height+)
            walker.setNext(ptr);

            //Reset walker so walker can traverse through the entire LL again, ready for next iteration
            walker = nullNodeBeforeFront;

            //Recall the saved node to pointer, so that we can check every single node in the LL 
            ptr = next;
        }

        //return reference to the head node in the SORTED LL as ptr is at the end of the LL
        return nullNodeBeforeFront.getNext();
    }
    
    /**
     * 
     * This method repeatedly removes students from the musicalChairs until there is only one
     * student (the winner).
     * 
     * Choose a student to be elimnated from the musicalChairs using StdRandom.uniform(int b),
     * where b is the number of students in the musicalChairs. 0 is the first student in the 
     * list, b-1 is the last.
     * 
     * Removes eliminated student from the list and inserts students back in studentsInLine 
     * in ascending height order (shortest to tallest).
     * 
     * The last line of this method calls the seatStudents() method so that students can be seated.
     */
    public void playMusicalChairs() {

        //Counting the number of students in musicalChairs
        int size = 1;

        SNode ptr = musicalChairs;
        SNode ptrRun = musicalChairs.getNext();

        while (ptrRun != ptr)
        {
            size += 1;
            ptrRun = ptrRun.getNext();
        }
        ////////////////////////////////////////////////////////////////

        SNode current = studentsInLine;

        for (int i = size; i > 0; i--)
        {
            int removal = StdRandom.uniform(i);
            int counter = 0;

            SNode holder = musicalChairs;
            SNode walker = musicalChairs.getNext();

            while (counter != removal)
            {
                holder = holder.getNext();
                walker = walker.getNext();
                counter += 1;
            }

            if(walker.getStudent().equals(musicalChairs.getStudent()))
            {

                if (studentsInLine == null)
                {
                SNode firstDead = new SNode(walker.getStudent(), null);

                studentsInLine = firstDead;

                current = studentsInLine;
                } else if (i > 1)
                {
                SNode nextStudent = new SNode(walker.getStudent(), null);

                current.setNext(nextStudent);

                current = nextStudent;
                }


                musicalChairs = holder;
                holder.setNext(walker.getNext());
            }
            else
            {
                if (studentsInLine == null)
                {
                SNode firstDead = new SNode(walker.getStudent(), null);

                studentsInLine = firstDead;

                current = studentsInLine;
                } else if (i > 1)
                {
                SNode nextStudent = new SNode(walker.getStudent(), null);

                current.setNext(nextStudent);

                current = nextStudent;
                }
                
                holder.setNext(walker.getNext());
            }
        }
       
        studentsInLine = insertionSort(studentsInLine);

        seatStudents();
    } 

    /**
     * Insert a student to wherever the students are at (ie. whatever activity is not empty)
     * Note: adds to the end of either linked list or the next available empty seat
     * @param firstName the first name
     * @param lastName the last name
     * @param height the height of the student
     */
    public void addLateStudent ( String firstName, String lastName, int height ) {
        
        Student s = new Student (firstName, lastName, height);

        if (studentsInLine != null)
        {
            SNode ptr = studentsInLine;

            while (ptr.getNext() != null)
            {
                ptr = ptr.getNext();
            }
            
            SNode lateStudent = new SNode(s, null);

            ptr.setNext(lateStudent);            


        } else if (musicalChairs != null)
        {          
            //uses student object to construct a new node to add to last of CLL, then link it to the first node of CLL
            SNode newLast = new SNode(s, musicalChairs.getNext());

            musicalChairs.setNext(newLast);

            musicalChairs = newLast;
        } else
        {
            boolean checker = false; 

            for (int i = 0; i < seatingAvailability.length; i++)
            {
                for (int j = 0; j < seatingAvailability[0].length; j++)
                {
                    if (seatingAvailability[i][j])
                    {
                        studentsSitting[i][j] = s;
                        seatingAvailability[i][j] = false;
                        checker = true;
                        break;
                    }

                    if (checker == true)
                    break;
                }
                if (checker == true)
                break;
            }
        }
        // WRITE YOUR CODE HERE
    }

    /**
     * A student decides to leave early
     * This method deletes an early-leaving student from wherever the students 
     * are at (ie. whatever activity is not empty)
     * 
     * Assume the student's name is unique
     * 
     * @param firstName the student's first name
     * @param lastName the student's last name
     */
    public void deleteLeavingStudent ( String firstName, String lastName ) {

        if (studentsInLine != null)
        {
            SNode ptr = studentsInLine;

            if (ptr == studentsInLine && (studentsInLine.getStudent().getFirstName().toLowerCase().equals(firstName.toLowerCase()) && studentsInLine.getStudent().getLastName().toLowerCase().equals(lastName.toLowerCase())))
            {
                studentsInLine = ptr.getNext();
                return;
            }

            while (ptr != null)
            {
                if (ptr.getNext() == null)
                {
                    System.out.println("There is no such student in this classroom!");
                    return;
                }

                if (ptr.getNext().getStudent().getFirstName().toLowerCase().equals(firstName.toLowerCase()) && ptr.getNext().getStudent().getLastName().toLowerCase().equals(lastName.toLowerCase()))
                {
                    ptr.setNext(ptr.getNext().getNext());
                    return;
                }

                ptr = ptr.getNext();
            }

        } else if (musicalChairs != null)
        {          
            SNode ptr = musicalChairs.getNext();

            if (ptr.getStudent().getFirstName().toLowerCase().equals(firstName.toLowerCase()) && ptr.getStudent().getLastName().toLowerCase().equals(lastName.toLowerCase()))
            {
                musicalChairs.setNext(musicalChairs.getNext().getNext());
                return;
            }

            while (ptr != musicalChairs)
            {
                if (ptr.getNext() == musicalChairs && (musicalChairs.getStudent().getFirstName().toLowerCase().equals(firstName.toLowerCase()) && musicalChairs.getStudent().getLastName().toLowerCase().equals(lastName.toLowerCase())))
                {
                    musicalChairs = ptr;
                    ptr.setNext(ptr.getNext().getNext());
                    break;
                }

                if (ptr.getNext().getStudent().getFirstName().toLowerCase().equals(firstName.toLowerCase()) && ptr.getNext().getStudent().getLastName().toLowerCase().equals(lastName.toLowerCase()))
                {
                    ptr.setNext(ptr.getNext().getNext());
                    break;
                }

                ptr = ptr.getNext();
            }

        } else
        {
            for (int i = 0; i < seatingAvailability.length; i++)
            {
                for (int j = 0; j < seatingAvailability[0].length; j++)
                {
                    if (seatingAvailability[i][j] == false && studentsSitting[i][j] != null && studentsSitting[i][j].getFirstName().toLowerCase().equals(firstName.toLowerCase()) && studentsSitting[i][j].getLastName().toLowerCase().equals(lastName.toLowerCase()))
                    {
                        studentsSitting[i][j] = null;
                        seatingAvailability[i][j] = true;
                        return;
                    }
                }
            }
        }
        // WRITE YOUR CODE HERE
    }

    /**
     * Used by driver to display students in line
     * DO NOT edit.
     */
    public void printStudentsInLine () {

        //Print studentsInLine
        StdOut.println ( "Students in Line:" );
        if ( studentsInLine == null ) { StdOut.println("EMPTY"); }

        for ( SNode ptr = studentsInLine; ptr != null; ptr = ptr.getNext() ) {
            StdOut.print ( ptr.getStudent().print() );
            if ( ptr.getNext() != null ) { StdOut.print ( " -> " ); }
        }
        StdOut.println();
        StdOut.println();
    }

    /**
     * Prints the seated students; can use this method to debug.
     * DO NOT edit.
     */
    public void printSeatedStudents () {

        StdOut.println("Sitting Students:");

        if ( studentsSitting != null ) {
        
            for ( int i = 0; i < studentsSitting.length; i++ ) {
                for ( int j = 0; j < studentsSitting[i].length; j++ ) {

                    String stringToPrint = "";
                    if ( studentsSitting[i][j] == null ) {

                        if (seatingAvailability[i][j] == false) {stringToPrint = "X";}
                        else { stringToPrint = "EMPTY"; }

                    } else { stringToPrint = studentsSitting[i][j].print();}

                    StdOut.print ( stringToPrint );
                    
                    for ( int o = 0; o < (10 - stringToPrint.length()); o++ ) {
                        StdOut.print (" ");
                    }
                }
                StdOut.println();
            }
        } else {
            StdOut.println("EMPTY");
        }
        StdOut.println();
    }

    /**
     * Prints the musical chairs; can use this method to debug.
     * DO NOT edit.
     */
    public void printMusicalChairs () {
        StdOut.println ( "Students in Musical Chairs:" );

        if ( musicalChairs == null ) {
            StdOut.println("EMPTY");
            StdOut.println();
            return;
        }
        SNode ptr;
        for ( ptr = musicalChairs.getNext(); ptr != musicalChairs; ptr = ptr.getNext() ) {
            StdOut.print(ptr.getStudent().print() + " -> ");
        }
        if ( ptr == musicalChairs) {
            StdOut.print(musicalChairs.getStudent().print() + " - POINTS TO FRONT");
        }
        StdOut.println();
    }

    /**
     * Prints the state of the classroom; can use this method to debug.
     * DO NOT edit.
     */
    public void printClassroom() {
        printStudentsInLine();
        printSeatedStudents();
        printMusicalChairs();
    }

    /**
     * Used to get and set objects.
     * DO NOT edit.
     */

    public SNode getStudentsInLine() { return studentsInLine; }
    public void setStudentsInLine(SNode l) { studentsInLine = l; }

    public SNode getMusicalChairs() { return musicalChairs; }
    public void setMusicalChairs(SNode m) { musicalChairs = m; }

    public boolean[][] getSeatingAvailability() { return seatingAvailability; }
    public void setSeatingAvailability(boolean[][] a) { seatingAvailability = a; }

    public Student[][] getStudentsSitting() { return studentsSitting; }
    public void setStudentsSitting(Student[][] s) { studentsSitting = s; }

}
