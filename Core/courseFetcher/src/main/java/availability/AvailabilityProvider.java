package availability;

import services.CourseService;
import skynet.scheduler.common.SemesterSeasons;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class AvailabilityProvider {

    private static HashMap<String, Season> lookup = new HashMap<>();
    private static int currentYear = 2019;

    private AvailabilityProvider(){}

    public static Season getSeason(String key){
        if(lookup != null) {
            return lookup.get(key);
        }else
            return null;
    }

    public static void setLookup(CourseService service) {
        try {
            List<Availability> sessions = service.getAvailablitity(currentYear);
            setLookup(sessions);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cannot set sessions");
        }
    }

    private static void setLookup(List<Availability> sessions){

        for(Availability a : sessions){
            String termCode = a.getTermCode();
            int year = getYear(a);
            SemesterSeasons s = getAvailable(a);
            Season season = new Season(termCode, s, year);
            lookup.put(termCode, season);
        }
    }

    private static int getYear(Availability avail){
        if(avail.getTermDescription().indexOf(currentYear + "") != -1)
            return currentYear;
        else if(avail.getTermDescription().indexOf((currentYear + 1)+ "") != -1)
            return currentYear + 1;
        else
            return currentYear + 2;
    }

    private static SemesterSeasons getAvailable(Availability avail) {

        String term = avail.getTermDescription();
        String sesDes = avail.getSessionDescription();

        if(term.indexOf("Fall") != -1){
            return SemesterSeasons.Fall;
        }
        else if(term.indexOf("Winter") != -1){
            return SemesterSeasons.Winter;
        }
        else {
            if(sesDes.indexOf("first") != -1)
                return SemesterSeasons.Summer1;
            else if(sesDes.indexOf("second") != -1)
                return SemesterSeasons.Summer2;
            else
                return SemesterSeasons.Summer;
        }

    }
}
