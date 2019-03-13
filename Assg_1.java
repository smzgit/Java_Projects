import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

class Student {
    private int token;
    private String fname;
    private double cgpa;

    public Student(int id, String fname, double cgpa) {
        super();
        this.token = id;
        this.fname = fname;
        this.cgpa = cgpa;
    }

    public int getToken() {
        return token;
    }

    public String getName() {
        return fname;
    }

    public double getCgpa() {
        return cgpa;
    }
}

class Priorities {
    public List < Student > getStudents(List < String > events) {
        PriorityQueue < Student > student_queue = new PriorityQueue(Comparator.comparing(Student::getCgpa).reversed().thenComparing(Student::getName).thenComparing(Student::getToken));
        List < Student > students = new ArrayList < Student > ();
        for (String e: events) {
            Scanner in =new Scanner(e);
            String event = in.next();
            if (event.equals("ENTER")) {
                String name = in.next();
                float cgpa = in.nextFloat();
                int token = in.nextInt();

                Student student = new Student(token, name, cgpa);
                student_queue.add(student);
            } else if (event.equals("SERVED")) {
                Student first = student_queue.poll();
            } in .close();
        }
        Student first = student_queue.poll();
        if (first == null) {
            return students;
        } else {
            while (first != null) {

                students.add(first);
                first = student_queue.poll();

            }
            return students;
        }

    }
}

public class Assg_1 {
    
    
    public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Priorities priorities = new Priorities();
        int Events = Integer.parseInt(scanner.nextLine());    
        List<String> events = new ArrayList<>();
        
        while (Events-- != 0) {
            String event = scanner.nextLine();
            events.add(event);
        }
        
        List<Student> students = priorities.getStudents(events);
        System.out.println("\nOrder of preference for remaining Students");
        if (students.isEmpty()) {
            System.out.println("EMPTY");
        } else {
            for (Student st: students) {
                System.out.println(st.getName());
            }
        }
    }
}