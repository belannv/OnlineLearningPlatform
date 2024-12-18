import java.util.ArrayList;
import java.util.List;

// Абстрактний клас користувача
abstract class User {
    private String name;
    private String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public abstract void login();
    
    // Фінальний метод реєстрації
    public final void register() {
        System.out.println("Користувач " + name + " зареєстрований");
    }

    public String getName() {
        return name;
    }
}

// Інтерфейс курсу
interface Course {
    void addMaterial(String material);
    void removeMaterial(String material);
}

// Клас онлайн-курсу
class OnlineCourse implements Course {
    private String courseName;
    private List<String> materials;
    private List<Student> enrolledStudents;

    public OnlineCourse(String courseName) {
        this.courseName = courseName;
        this.materials = new ArrayList<>();
        this.enrolledStudents = new ArrayList<>();
    }

    // Перевизначення методу інтерфейсу
    @Override
    public void addMaterial(String material) {
        materials.add(material);
        System.out.println("Додано матеріал: " + material + " до курсу " + courseName);
    }

    // Перевантаження методу
    public void addMaterial(String material, boolean isImportant) {
        addMaterial(material);
        if (isImportant) {
            System.out.println("Позначено як важливий матеріал");
        }
    }

    @Override
    public void removeMaterial(String material) {
        materials.remove(material);
        System.out.println("Видалено матеріал: " + material + " з курсу " + courseName);
    }

    public void enrollStudent(Student student) {
        enrolledStudents.add(student);
        System.out.println(student.getName() + " записаний на курс " + courseName);
    }
}

// Клас студента
class Student extends User {
    private List<OnlineCourse> courses;
    private List<Assignment> assignments;

    public Student(String name, String email) {
        super(name, email);
        this.courses = new ArrayList<>();
        this.assignments = new ArrayList<>();
    }

    @Override
    public void login() {
        System.out.println("Студент " + getName() + " увійшов у систему");
    }

    public void enrollCourse(OnlineCourse course) {
        courses.add(course);
        course.enrollStudent(this);
    }

    public void submitAssignment(Assignment assignment) {
        assignments.add(assignment);
        assignment.submit(this);
    }
}

// Клас викладача
class Teacher extends User {
    private List<OnlineCourse> teachingCourses;

    public Teacher(String name, String email) {
        super(name, email);
        this.teachingCourses = new ArrayList<>();
    }

    @Override
    public void login() {
        System.out.println("Викладач " + getName() + " увійшов у систему");
    }

    public OnlineCourse createCourse(String courseName) {
        OnlineCourse course = new OnlineCourse(courseName);
        teachingCourses.add(course);
        return course;
    }

    public void evaluateAssignment(Assignment assignment, int grade) {
        assignment.evaluate(grade);
    }
}

// Клас завдання
class Assignment {
    private String title;
    private AssignmentStatus status;
    private Student student;
    private int grade;

    public Assignment(String title) {
        this.title = title;
        this.status = AssignmentStatus.CREATED;
    }

    public void submit(Student student) {
        this.student = student;
        this.status = AssignmentStatus.SUBMITTED;
        System.out.println("Завдання \"" + title + "\" подане студентом " + student.getName());
    }

    public void evaluate(int grade) {
        this.grade = grade;
        this.status = grade >= 60 ? AssignmentStatus.PASSED : AssignmentStatus.FAILED;
        System.out.println("Завдання \"" + title + "\" оцінене на " + grade + " балів");
    }

    // Enum для статусів завдання
    enum AssignmentStatus {
        CREATED, SUBMITTED, PASSED, FAILED
    }
}

// Клас адміністратора
class Administrator extends User {
    public Administrator(String name, String email) {
        super(name, email);
    }

    @Override
    public void login() {
        System.out.println("Адміністратор " + getName() + " увійшов у систему");
    }

    public void generateReport(OnlineCourse course) {
        System.out.println("Звіт по курсу: " + course);
    }
}

// Демонстраційний клас
public class OnlineLearningPlatform {
    public static void main(String[] args) {
        // Створення користувачів
        Teacher teacher = new Teacher("Іван Петров", "ivan@school.com");
        Student student = new Student("Марія Іванова", "maria@school.com");
        Administrator admin = new Administrator("Олена Сидорова", "olena@school.com");

        // Створення курсу
        OnlineCourse javaCourse = teacher.createCourse("Основи Java");
        javaCourse.addMaterial("Вступ до Java", true);
        javaCourse.addMaterial("ООП в Java");

        // Реєстрація студента
        student.register();
        student.enrollCourse(javaCourse);

        // Створення та подання завдання
        Assignment assignment = new Assignment("Перша програма на Java");
        student.submitAssignment(assignment);

        // Оцінювання завдання
        teacher.evaluateAssignment(assignment, 85);

        // Логін користувачів
        teacher.login();
        student.login();
        admin.login();
    }
}