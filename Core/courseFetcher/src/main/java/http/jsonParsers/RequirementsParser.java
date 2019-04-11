package http.jsonParsers;

import java.util.ArrayList;
import java.io.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;



public class RequirementsParser{

    static Pattern neverTakenPattern = Pattern.compile("NT:(?:[A-Za-z]{4}(?:[0-9]{3})+(?:or)*)+");
    static Pattern coreqPattern = Pattern.compile("C:(?:[A-Za-z]{4}(?:[0-9]{3})+(?:or)*)+");
    static Pattern prereqPattern = Pattern.compile("P:(?:[A-Za-z]{4}(?:[0-9]{3})+(?:or)*)+");
    static Pattern courseSeriePattern = Pattern.compile("(?<!or)[A-Za-z]{4}(?:[0-9]{3})+(?!or)");
    static Pattern testPattern = Pattern.compile("[A-Za-z]{4}[0-9]{3}");
    public static String reformat(String content){

        content = content.replaceAll("-","");
        content = content.replaceAll("\\s|;|:|/|and|\\." , "");
        content = content.replaceAll("(?i)Prerequisite","P:");
        content = content.replaceAll("(?i)Corequisite", "C:");
        content = content.replaceAll("(?i)notregistered" , "");
        content = content.replaceAll("(?i)NeverTaken" , "NT:");
        content = content.replaceAll("(?i)Course" , "");
        content = content.replaceAll("(?i)previouslyorconcurrently" , "POC");
        String[] contentArr = content.split("(?i)Youmustcomplete1ofthefollowingrules");
       

        if(contentArr.length == 2){
        StringBuilder result = new StringBuilder(contentArr[0]);
        String content2 = contentArr[1].replaceAll("(?i)You must complete 1 of the following rules" , "");
        content2 = content2.replaceAll(",|;" , "or");
        result.append(content2);
        return result.toString();
        }

        else{
            content = content.replaceAll("," , "");
            System.out.println(content);
             //check for previously or concorently
            Matcher pocMacther = Pattern.compile("(?:[A-Za-z]{4}(?:[0-9]{3})+|[A-Za-z]{4}[0-9]{3}or[A-Za-z]{4}[0-9]{3})POC").matcher(content);
            
            if(pocMacther.find()){
                String part2 = content.substring(pocMacther.start());
                StringBuilder sb = new StringBuilder(content.substring(0, pocMacther.start()));
                sb.append("C:");
                sb.append(part2);
                content = sb.toString();
                content = content.replaceAll("(?i)POC","");
            }

            return content;
        }
}
            

    
    /**
     *Only method required to communicate with the Requirements Parser
	 *@param content. Takes a  requirements string from the Concordia Open Data API 
	 *@return HashMap with three string keys('prereqs' , 'reqs' , neverTaken) and ArrayList type values.
	 */

    public static HashMap<String,ArrayList<String>> getRequirements(String content){

            HashMap<String , ArrayList<String>> requirements = new  HashMap<String , ArrayList<String>>();

            content = reformat(content);

                //Create Matchers
                Matcher neverTakenMatcher = neverTakenPattern.matcher(content);
                Matcher  coreqMatcher = coreqPattern.matcher(content);
                Matcher  prereqMatcher = prereqPattern.matcher(content);
                Matcher testMatcher = testPattern.matcher(content);

                while(neverTakenMatcher.find()){

                    String ntreqs = neverTakenMatcher.group(0);
                    //System.out.println("Never Taken requirements = " + ntreqs);
                   
                    ArrayList<String> neverTaken = addRequirementsToList(ntreqs);
                    requirements.put("never taken" , neverTaken);

                   //System.out.println(Arrays.toString(neverTaken.toArray()));
                }

                while(prereqMatcher.find()){

                	//System.out.println("in prereqMatcher.find()");
                    String prereqs  = prereqMatcher.group(0);
                    //System.out.println("Prerequisite requirements = " + prereqs);
                    ArrayList<String> prerequisites = new ArrayList<String>(addRequirementsToList(prereqs));
                    //prerequisites = addRequirementsToList(prereqs);

                    requirements.put("prereqs", prerequisites);

                   //System.out.println("PREREQUISITES = " + Arrays.toString(prerequisites.toArray()));

                }

                while (coreqMatcher.find()){
                    String coreqs  = coreqMatcher.group(0);
                   //System.out.println("Corequisite Requirements = " + coreqs);
                    ArrayList<String> corequisites = new ArrayList<String>();
                    corequisites = addRequirementsToList(coreqs);
                    requirements.put("coreqs",corequisites);       
            }

            return requirements;
        }


    public static ArrayList<String> addRequirementsToList(String reqs ){

    				ArrayList<String> list= new ArrayList<String>();

                    String courseLetterCode = new String();

                    Matcher courseSerieMatcher = courseSeriePattern.matcher(reqs);

                    while(courseSerieMatcher.find()){
                        String courseSerie = courseSerieMatcher.group(0);

                        Matcher letterCodeMatcher = Pattern.compile("[A-Za-z]{4}").matcher(courseSerie);

                        if(letterCodeMatcher.find())
                            courseLetterCode = letterCodeMatcher.group(0);

                        Matcher numericCodeMatcher =Pattern.compile("[0-9]{3}").matcher(courseSerie);

                        while(numericCodeMatcher.find()){
                            list.add(courseLetterCode + numericCodeMatcher.group(0));
                        }

                        reqs = reqs.replaceAll(courseSerie , "");

                    }

                    reqs = reqs.replaceAll("C:" , "");
                    reqs = reqs.replaceAll("P:" , "");
                    reqs = reqs.replaceAll("NT:" , "");


                    Matcher splitMatcher = Pattern.compile("(?:[A-Za-z]{4}[0-9]{3}){2}").matcher(reqs);

                        
                    if(splitMatcher.find()){
                        
                        StringBuilder sb = new StringBuilder(reqs);
                        sb.insert((splitMatcher.end()+splitMatcher.start())/2,"#");
                        reqs = sb.toString();
                    } 

                   ArrayList<String> alternativereqs = new ArrayList<String>(Arrays.asList(reqs.split("#")));

                   list.addAll(alternativereqs);

                   return list;

                   //System.out.println("PREREQUISITES = " + Arrays.toString(prerequisites.toArray()));

    }

 }
