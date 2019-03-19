package optimization;

import services.Attach;
import services.AttachSeason;
import services.CourseService;
import skynet.scheduler.common.Course;
import skynet.scheduler.common.ICourse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ConcurrentCourseFetcher {

    //This coursefetcher will at max utilize a total of 10 threads
    private static ExecutorService executor;

    //Set the pool size to 20
    private static final int NUM_THREADS = 20;

    private ConcurrentCourseFetcher(){}

    private static void setPool(){
        //set the number of threads
        executor = Executors.newFixedThreadPool(NUM_THREADS);
    }

    public static List<ICourse> getCourses(List<String> courses, CourseService service){

        setPool();

        //Create Shared List
        List<ICourse> sharedList = new ArrayList<>();

        //set the list all AddCourse Threads will write to
        AddCourse.setSharedList(sharedList);


        for(int i =0; i < courses.size(); i++){
            String code = courses.get(i);

            //Create 1 of the 10 threads to start retrieving course information
            AddCourse task = new AddCourse(code, service);
            executor.submit(task);
        }

        waitUntilComplete();
        return AddCourse.getSharedList();
    }

    private static void waitUntilComplete(){

        //stop accepting more tasks
        executor.shutdown();

        while(!executor.isTerminated()){
            //wait until all threads have completed their task
        }
    }

}

/**
 * This class is EXPLICITLY used by the CourseFetcher and NO OTHER class.
 */
class AddCourse implements Runnable{

    //All instances of the AddCourse object will share this 1 list
    private static List<ICourse> sharedList;

    //Instance specific, each instance of AddCourse, will read the API to getInfo
    private String code;
    private CourseService service;

    /**
     * Before running a concurrent task, set the shared medium all threads will write to.
     * This medium will be protected accordingly to ensure atomicity.
     * @param shared
     */
    public static void setSharedList(List<ICourse> shared){
        sharedList = shared;
    }

    public static List<ICourse> getSharedList(){
        return sharedList;
    }


    /**
     * Pass all information required by 1 specific thread to perform it's task.
     * @param courseCode
     */
    public AddCourse(String courseCode, CourseService service){
        code = courseCode;
        this.service = service;
    }


    @Override
    public void run() {

        //Preform concurrent task.

        System.out.println("THREAD: Getting info for " + code);
        String[] info = getInfo(code);
        try {
            ICourse c = service.getCourse(info[0], info[1]);
            attachPreq(c);
            AttachSeason.attachSeasons(c, service);
            recordInfo(c);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to get course info for " + code);
        }
        System.out.println("Recorded: " + code);
    }

    private String[] getInfo(String code){
        int index = 0;
        while(!Character.isDigit(code.charAt(index)) && index < code.length())
            index++;
        String[] info = {code.substring(0, index), code.substring(index)};
        return info;
    }

    private void attachPreq(ICourse c) throws IOException {
        Course course = (Course)c;
        Course[] courseArray = {course};

        ArrayList<String> filter = new ArrayList<String>();
        Attach attach = new Attach(courseArray, service, filter);
        attach.attachPrerequisites();
    }

    /**
     *   static synchronized will ensure all instances of the task will respect mutual exclusion.
     */
    private static synchronized void recordInfo(ICourse data){
        sharedList.add(data);
    }
}





