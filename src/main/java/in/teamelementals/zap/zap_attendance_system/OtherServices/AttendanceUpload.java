package in.teamelementals.zap.zap_attendance_system.OtherServices;

import in.teamelementals.zap.zap_attendance_system.Repositories.*;
import in.teamelementals.zap.zap_attendance_system.dao.EgovTimetableStoreDAO;
import in.teamelementals.zap.zap_attendance_system.model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AttendanceUpload{

    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private main_attendanceRepositories mainAttendanceRepositories;

    @Autowired
    private Retry1AttendanceRepositories retry1AttendanceRepositories;

    @Autowired
    private Retry2AttendanceRepositories retry2AttendanceRepositories;

    @Autowired
    private ErrorAttendanceRepositories errorAttendanceRepositories;

    @Autowired
    private viewFacultiesRepositories viewFacultiesRepositories;

    @Autowired
    private viewEgovFacultyListRepositories viewEgovFacultyListRepositories;

    @Autowired
    private viewmainattendanceRepositories viewmainattendanceRepositories;

    @Autowired
    private uploadedAttendanceRepositories uploadedAttendanceRepositories1;

    @Autowired
    private in_out_attendanceRepositories inOutAttendanceRepositories;

    @Autowired
    private in0utFacultyAttendanceRepositories in0utFacultyAttendanceRepositories;

    @Autowired
    private EgovTimetableStoreDAO egovTimetableStoreDAO;

    @Autowired
    private MailService mailService;

    ModelMapper modelMapper = new ModelMapper();

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    static int[] parseIntArray(String[] arr) {
        return Stream.of(arr).mapToInt(Integer::parseInt).toArray();
    }

    //Egov Faculty TimetableId Fetch Api Data Function
    private JSONArray TimeTableFetchApi(viewEgovFacultyList viewEgovFacultyList){

        //EGov Api to get TimeTableId and Expected Students
        String apiUrl = "https://demo.api/MobAppService/service.asmx?op=DEMOMETHOD_2";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("text/xml; charset=utf-8"));

//        LocalDate currentDate = LocalDate.now();

//                +currentDate.format(formatter)+
        String requestBody = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <DEMOMETHOD_2 xmlns=\"http://tempuri.org/\">\n" +
                "      <EPara1>DEMOAPI_KEY_123</EPara1>\n" +
                "      <EPara2>"+viewEgovFacultyList.getFaculty()+"</EPara2>\n" +
                "      <EPara3>"+viewEgovFacultyList.getStartDate().toLocalDate().format(formatter)+"</EPara3>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>";

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        JSONArray rawDataToInsert = new JSONArray();

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);
        String xmlResponse = response.getBody();
        System.out.println(xmlResponse);
        JSONObject jsonObject = XML.toJSONObject(xmlResponse);
        try {
            rawDataToInsert = jsonObject.getJSONObject("soap:Envelope")
                    .getJSONObject("soap:Body")
                    .getJSONObject("DEMOMETHOD_2Response")
                    .getJSONObject("DEMOMETHOD_2Result")
                    .getJSONObject("FacSchedule")
                    .getJSONArray("tblActualTimeTable");
        } catch (JSONException e) {
            rawDataToInsert = new JSONArray().put(
                    jsonObject.getJSONObject("soap:Envelope")
                            .getJSONObject("soap:Body")
                            .getJSONObject("DEMOMETHOD_2Response")
                            .getJSONObject("DEMOMETHOD_2Result")
                            .getJSONObject("FacSchedule")
                            .getJSONObject("tblActualTimeTable"));
        }
        return rawDataToInsert;
    }

    //EGov data upload api function
    public String EGovDataUpload(String ActualTimeTableID,List<String> studentLists){
        //EGov api to upload student attendance
        String apiUrl2 = "https://demo.api/MobAppService/service.asmx?op=DEMOMETHOD";

        HttpHeaders headers2 = new HttpHeaders();
        headers2.setContentType(MediaType.valueOf("text/xml; charset=utf-8"));

        String requestBody2;
        //If there is no student in ZAP for that lecture then pass 0.
        if (studentLists.isEmpty()){
            requestBody2 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                    "  <soap12:Body>\n" +
                    "    <DEMOMETHOD xmlns=\"http://tempuri.org/\">\n" +
                    "      <EPara1>DEMOAPI_KEY_123</EPara1>\n" +
                    "      <EPara2>"+ActualTimeTableID+"</EPara2>\n" +
                    "      <EPara3>1</EPara3>\n" +
                    "      <EPara4>0</EPara4>\n" +
                    "    </DEMOMETHOD>\n" +
                    "  </soap12:Body>\n" +
                    "</soap12:Envelope>";
        }
        else {
            requestBody2 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                    "  <soap12:Body>\n" +
                    "    <DEMOMETHOD xmlns=\"http://tempuri.org/\">\n" +
                    "      <EPara1>DEMOAPI_KEY_123</EPara1>\n" +
                    "      <EPara2>" + ActualTimeTableID + "</EPara2>\n" +
                    "      <EPara3>" + studentLists.toString().replace("[", "").replace("]", "") + "</EPara3>\n" +
                    "    </DEMOMETHOD>\n" +
                    "  </soap12:Body>\n" +
                    "</soap12:Envelope>";
        }
//        System.out.println(requestBody2);

        HttpEntity<String> entity2 = new HttpEntity<>(requestBody2, headers2);

        ResponseEntity<String> response2 = restTemplate.exchange(apiUrl2, HttpMethod.POST, entity2, String.class);
        String xmlResponse = response2.getBody();
        JSONObject jsonObject = XML.toJSONObject(xmlResponse);
        JSONObject rawDataToReturn = new JSONObject();
            rawDataToReturn = jsonObject.getJSONObject("soap:Envelope")
                    .getJSONObject("soap:Body")
                    .getJSONObject("DEMOMETHODResponse")
                    .getJSONObject("DEMOMETHODResult")
                    .getJSONObject("StudentAttPost")
                    .getJSONObject("tblStudentAttendace");

       String returnMessage = (String) rawDataToReturn.get("Message");
       System.out.println(returnMessage);
       return returnMessage;
    }

    public void ZapToEgovRetryService(String tableFrom){
        System.out.println("********" + tableFrom + " Egovernance Data Uploading Service started **********");

        //Fetching FacultyId of Todays Faculty Lecture
        List<viewEgovFacultyList> viewEgovFacultyListList = viewEgovFacultyListRepositories.findByTableFrom(tableFrom);

        //Uploading data to Egov for each facultyId
        for(viewEgovFacultyList viewEgovFacultyList : viewEgovFacultyListList){

            System.out.println("Fetching TimeTable for FacultyId:- "+viewEgovFacultyList.getFaculty());
            JSONArray rawDataToInsert;
            try {
                // TimeTableFetchApi Function Call
                rawDataToInsert = TimeTableFetchApi(viewEgovFacultyList);
            }
            catch (Exception e){
                System.out.println("EGov Faculty TimeTable Api fetching error.");
                continue;
            }

            //Checking API Status Data
            int a = (int) rawDataToInsert.getJSONObject(0).get("Status");
            if (a==1) {
                List<EGovFetchDataObject> eGovFetchDataObjects = new ArrayList<>();
                List<EgovTimetableStore> egovTimetableStoreList = new ArrayList<>();

                for (int i = 0; i < rawDataToInsert.length(); i++) {
                    EGovFetchDataObject eGovFetchDataObject = new EGovFetchDataObject();
                    eGovFetchDataObject.setActualTimeTableID( ""+rawDataToInsert.getJSONObject(i).get("ActualTimeTableID"));
                    eGovFetchDataObject.setDate((String) rawDataToInsert.getJSONObject(i).get("Date"));
                    eGovFetchDataObject.setTimeStart(((String) rawDataToInsert.getJSONObject(i).get("Time")).substring(0, 5));
                    eGovFetchDataObject.setTimeEnd(((String) rawDataToInsert.getJSONObject(i).get("Time")).substring(8, 13));
                    eGovFetchDataObject.setLecDetails((String) rawDataToInsert.getJSONObject(i).get("LecDetails"));
                    eGovFetchDataObject.setSubject((String) rawDataToInsert.getJSONObject(i).get("Subject"));
                    eGovFetchDataObject.setStudentID_All(List.of(((String) rawDataToInsert.getJSONObject(i).get("StudentID_All")).split(",")));
                    eGovFetchDataObjects.add(eGovFetchDataObject);

                    EgovTimetableStore egovTimetableStore = new EgovTimetableStore();
                    egovTimetableStore.setActualTimetableId(""+rawDataToInsert.getJSONObject(i).get("ActualTimeTableID"));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar calendar = Calendar.getInstance();
                    Date date = calendar.getTime();
                    try {
                        date =  dateFormat.parse((String)rawDataToInsert.getJSONObject(i).get("Date"));
                    }
                    catch (ParseException pe){
                        System.out.println("Error: String not converted to date.");
                    }
                    egovTimetableStore.setDate(date);
                    egovTimetableStore.setTime(""+rawDataToInsert.getJSONObject(i).get("Time"));
                    egovTimetableStore.setParentSubjectId(""+rawDataToInsert.getJSONObject(i).get("ParentSubjectID"));
                    egovTimetableStore.setSubjectName(""+rawDataToInsert.getJSONObject(i).get("SubjectName"));
                    egovTimetableStore.setFacultyId(""+viewEgovFacultyList.getFaculty());
                    egovTimetableStore.setFacultyName(""+rawDataToInsert.getJSONObject(i).get("FacultyName"));
                    egovTimetableStore.setStudentIdAll(List.of((""+rawDataToInsert.getJSONObject(i).get("StudentID_All")).split(",")));
                    egovTimetableStore.setLectureDetails(""+rawDataToInsert.getJSONObject(i).get("LecDetails"));
                    egovTimetableStoreList.add(egovTimetableStore);
                }
                egovTimetableStoreDAO.addDataList(egovTimetableStoreList);

                //For Mail Service
                List<String> lacDetails = new ArrayList<>();
                List<String> date = new ArrayList<>();
                List<String> time = new ArrayList<>();
                List<String> totalStudent = new ArrayList<>();
                List<String> status = new ArrayList<>();
                List<String> subject = new ArrayList<>();

                //Uploading data on EGov for each faculty lecture
                for (EGovFetchDataObject eGovFetchDataObject : eGovFetchDataObjects) {

                    List<String> expectedStudents = eGovFetchDataObject.getStudentID_All();
                    System.out.println("\n\nUploading attendance of TimeTableId "+eGovFetchDataObject.getActualTimeTableID()+" and Students:");

//                            DateTimeFormatter timeStampFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    int[] dty = parseIntArray( eGovFetchDataObject.getDate().split("/"));
                    String Date1  = dty[2]+ "-"+dty[1]+ "-"+dty[0] + " "+eGovFetchDataObject.getTimeStart()+":00.000";
                    String Date2 = dty[2]+ "-"+dty[1]+ "-"+dty[0] + " "+eGovFetchDataObject.getTimeEnd()+":59.999";

                    System.out.println(Date1);
                    System.out.println(Date2);
//                            String Date1 = String.valueOf(LocalDate.now()) + " "+"17:30"+":00.000";
//                            String Date2 = String.valueOf(LocalDate.now()) + " "+"18:30"+":59.999";

                    List<String> studentLists;
                    List<Retry1Attendance> attendedStudentRetry1 = null;
                    List<Retry2Attendance> attendedStudentRetry2 = null;
                    //Today for each lecture Student fetch
                    if  (Objects.equals(tableFrom, "retry_1")){
                        attendedStudentRetry1 = retry1AttendanceRepositories.findByFacultyAndCreatedDateBetweenAndStudentIn(String.valueOf(viewEgovFacultyList.getFaculty()), Timestamp.valueOf(Date1), Timestamp.valueOf(Date2), expectedStudents);
                        studentLists = attendedStudentRetry1.stream().map(Retry1Attendance::getStudent).collect(Collectors.toList());
                    } else if (Objects.equals(tableFrom, "retry_2")) {
                        attendedStudentRetry2 = retry2AttendanceRepositories.findByFacultyAndCreatedDateBetweenAndStudentIn(String.valueOf(viewEgovFacultyList.getFaculty()), Timestamp.valueOf(Date1), Timestamp.valueOf(Date2), expectedStudents);
                        studentLists = attendedStudentRetry2.stream().map(Retry2Attendance::getStudent).collect(Collectors.toList());
                    }else {
                        continue;
                    }

                    System.out.println("Total Student Present Count: "+studentLists.size());
                    System.out.println(studentLists);

                    //For mail service
                    lacDetails.add(eGovFetchDataObject.getLecDetails());
                    date.add(eGovFetchDataObject.getDate());
                    time.add(eGovFetchDataObject.getTimeStart() + " - "+eGovFetchDataObject.getTimeEnd());
                    totalStudent.add(String.valueOf(studentLists.size()));
                    subject.add(eGovFetchDataObject.getSubject());
                    if (!studentLists.isEmpty()) {
                        try {
                            //EGovDataUpload Function Call
                            String returnMessage = EGovDataUpload(eGovFetchDataObject.getActualTimeTableID(), studentLists);
                            if  (Objects.equals(tableFrom, "retry_1")){
                                List<uploadedAttendance> uploadedAttendanceList = attendedStudentRetry1.stream().map(student -> modelMapper.map(student, uploadedAttendance.class)).collect(Collectors.toList());
                                uploadedAttendanceRepositories1.saveAll(uploadedAttendanceList);
                                retry1AttendanceRepositories.deleteAll(attendedStudentRetry1);
                            } else if (Objects.equals(tableFrom, "retry_2")) {
                                List<uploadedAttendance> uploadedAttendanceList = attendedStudentRetry2.stream().map(student -> modelMapper.map(student, uploadedAttendance.class)).collect(Collectors.toList());
                                uploadedAttendanceRepositories1.saveAll(uploadedAttendanceList);
                                retry2AttendanceRepositories.deleteAll(attendedStudentRetry2);
                            }

                            //For mail service
                            if (Objects.equals(returnMessage, "Attendance is already taken...")) {
                                status.add("Attendance was taken manually");
                            } else {
                                status.add(returnMessage);
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                            System.out.println("EGov data upload api error.");
                            //For mail service
                            status.add("Not Uploaded. Something went wrong.");
                        }
                    }
                    else {
                        status.add("Zero student present. Hense not uploaded to EGovernance");
                    }
                }
            }
            else{
                System.out.println("Today's data isn't available for facultyID: "+viewEgovFacultyList.getFaculty()+" in EGov fetch Api");
                // TODO : Add email service. to notify system admin.
            }
        }
        System.out.println("********" + tableFrom + " Egovernance Data Uploading Service Ended *********");
    }



  /* Best time to fetch faculty lecture details is 20 seconds and worst case its 30 seconds mesaursed so far.
     On an average considering 100 faculty taking attendance everyday
     The best case would be 33.33 minutes to fetch the data
     The worst case would be 50 minutes to fetch the data

     Considering each faculty to ~3 lectures uploading total 300 entries will take 25 minutes in total
     at 5 second per EGov api call for data uploading.
     Total time is ~ 75 minutes in the worst case.
     And ~58 minutes in best case.
   */

    //Main EGov service handling function
//    @Scheduled(cron = "0 18 18 * * *")
    // @Scheduled(fixedRate = 10000000,initialDelay = 5000)
    public void ZapToEgovAttendance(){
        int uploadedAttendanceCount = 0;
        System.out.println("******** Egovernance Data Uploading Service started **********");

        //Fetching FacultyId of Todays Faculty Lecture
        List<viewEgovFacultyList> viewEgovFacultyListList = viewEgovFacultyListRepositories.findByTableFrom("main");

        //Uploading data to Egov for each facultyId
        for(viewEgovFacultyList viewEgovFacultyList : viewEgovFacultyListList){

            //For mail serivce
            AttendanceMailObject attendanceMailObject = new AttendanceMailObject();
            attendanceMailObject.setFacultyId(viewEgovFacultyList.getFaculty());
            viewFaculties viewFaculties = viewFacultiesRepositories.findByEnrollmentNo(String.valueOf(viewEgovFacultyList.getFaculty()));
            attendanceMailObject.setName(viewFaculties.getName());
            attendanceMailObject.setEmail(viewFaculties.getEmail());

                System.out.println("Fetching TimeTable for FacultyId:- "+viewEgovFacultyList.getFaculty());
                JSONArray rawDataToInsert;
                try {
                   // TimeTableFetchApi Function Call
                   rawDataToInsert = TimeTableFetchApi(viewEgovFacultyList);
                }
                catch (Exception e){
                    System.out.println("EGov Faculty TimeTable Api fetching error.");
                    continue;
                }

                    //Checking API Status Data
                    int a = (int) rawDataToInsert.getJSONObject(0).get("Status");
                    if (a==1) {
                        List<EGovFetchDataObject> eGovFetchDataObjects = new ArrayList<>();
                        List<EgovTimetableStore> egovTimetableStoreList = new ArrayList<>();

                        for (int i = 0; i < rawDataToInsert.length(); i++) {
                            EGovFetchDataObject eGovFetchDataObject = new EGovFetchDataObject();
                            eGovFetchDataObject.setActualTimeTableID( ""+rawDataToInsert.getJSONObject(i).get("ActualTimeTableID"));
                            eGovFetchDataObject.setDate((String) rawDataToInsert.getJSONObject(i).get("Date"));
                            eGovFetchDataObject.setTimeStart(((String) rawDataToInsert.getJSONObject(i).get("Time")).substring(0, 5));
                            eGovFetchDataObject.setTimeEnd(((String) rawDataToInsert.getJSONObject(i).get("Time")).substring(8, 13));
                            eGovFetchDataObject.setLecDetails((String) rawDataToInsert.getJSONObject(i).get("LecDetails"));
                            eGovFetchDataObject.setSubject((String) rawDataToInsert.getJSONObject(i).get("Subject"));
                            eGovFetchDataObject.setStudentID_All(List.of(((String) rawDataToInsert.getJSONObject(i).get("StudentID_All")).split(",")));
                            eGovFetchDataObjects.add(eGovFetchDataObject);

                            EgovTimetableStore egovTimetableStore = new EgovTimetableStore();
                            egovTimetableStore.setActualTimetableId(""+rawDataToInsert.getJSONObject(i).get("ActualTimeTableID"));
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Calendar calendar = Calendar.getInstance();
                            Date date = calendar.getTime();
                            try {
                                date =  dateFormat.parse((String)rawDataToInsert.getJSONObject(i).get("Date"));
                            }
                            catch (ParseException pe){
                                System.out.println("Error: String not converted to date.");
                            }
                            egovTimetableStore.setDate(date);
                            egovTimetableStore.setTime(""+rawDataToInsert.getJSONObject(i).get("Time"));
                            
                            egovTimetableStore.setParentSubjectId(""+rawDataToInsert.getJSONObject(i).get("ParentSubjectID"));
                            egovTimetableStore.setSubjectName(""+rawDataToInsert.getJSONObject(i).get("SubjectName"));
                            egovTimetableStore.setFacultyId(""+viewEgovFacultyList.getFaculty());
                            egovTimetableStore.setFacultyName(""+rawDataToInsert.getJSONObject(i).get("FacultyName"));
                            egovTimetableStore.setStudentIdAll(List.of((""+rawDataToInsert.getJSONObject(i).get("StudentID_All")).split(",")));
                            egovTimetableStore.setLectureDetails(""+rawDataToInsert.getJSONObject(i).get("LecDetails"));
                            egovTimetableStoreList.add(egovTimetableStore);
                        }
                        egovTimetableStoreDAO.addDataList(egovTimetableStoreList);

                        //For Mail Service
                        List<String> lacDetails = new ArrayList<>();
                        List<String> date = new ArrayList<>();
                        List<String> time = new ArrayList<>();
                        List<String> totalStudent = new ArrayList<>();
                        List<String> status = new ArrayList<>();
                        List<String> subject = new ArrayList<>();

                        //Uploading data on EGov for each faculty lecture
                        for (EGovFetchDataObject eGovFetchDataObject : eGovFetchDataObjects) {

                            List<String> expectedStudents = eGovFetchDataObject.getStudentID_All();
                            System.out.println("\n\nUploading attendance of TimeTableId "+eGovFetchDataObject.getActualTimeTableID()+" and Students:");

//                            DateTimeFormatter timeStampFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                           int[] dty = parseIntArray( eGovFetchDataObject.getDate().split("/"));
                           String Date1  = dty[2]+ "-"+dty[1]+ "-"+dty[0] + " "+eGovFetchDataObject.getTimeStart()+":00.000";
                           String Date2 = dty[2]+ "-"+dty[1]+ "-"+dty[0] + " "+eGovFetchDataObject.getTimeEnd()+":59.999";

                            // The following implements a 15 minute gap
                           Calendar cal = Calendar.getInstance();
                           cal.setTimeInMillis( Timestamp.valueOf(Date1).getTime());
                           cal.add(Calendar.MINUTE, -15);

                           Calendar cal2 = Calendar.getInstance();
                           cal2.setTimeInMillis( Timestamp.valueOf(Date2).getTime());
                           cal2.add(Calendar.MINUTE, -15);

                           System.out.println(Date1);
                           System.out.println(Date2);
//                            String Date1 = String.valueOf(LocalDate.now()) + " "+"17:30"+":00.000";
//                            String Date2 = String.valueOf(LocalDate.now()) + " "+"18:30"+":59.999";

                            //Today for each lecture Student fetch
                            List<main_attendance> attendedStudent = mainAttendanceRepositories.findByFacultyAndCreatedDateBetweenAndStudentIn(String.valueOf(viewEgovFacultyList.getFaculty()),new Timestamp(cal.getTime().getTime()), new Timestamp(cal2.getTime().getTime()), expectedStudents);
                            List<String> studentLists = attendedStudent.stream().map(main_attendance::getStudent).collect(Collectors.toList());
                            System.out.println("Total Student Present Count: "+studentLists.size());
                            System.out.println(studentLists);

                            //For mail service
                            lacDetails.add(eGovFetchDataObject.getLecDetails());
                            date.add(eGovFetchDataObject.getDate());
                            time.add(eGovFetchDataObject.getTimeStart() + " - "+eGovFetchDataObject.getTimeEnd());
                            totalStudent.add(String.valueOf(studentLists.size()));
                            subject.add(eGovFetchDataObject.getSubject());
                            if (!studentLists.isEmpty()) {
                               try {
                                   //EGovDataUpload Function Call
                                   String returnMessage = EGovDataUpload(eGovFetchDataObject.getActualTimeTableID(), studentLists);
                                   uploadedAttendanceCount += studentLists.size();
                                   List<uploadedAttendance> uploadedAttendanceList = attendedStudent.stream().map(student -> modelMapper.map(student, uploadedAttendance.class)).collect(Collectors.toList());
                                   uploadedAttendanceRepositories1.saveAll(uploadedAttendanceList);
                                   mainAttendanceRepositories.deleteAll(attendedStudent);

                                   //TODO: uplaod data to retry1

                                   //For mail service
                                   if (Objects.equals(returnMessage, "Attendance is already taken...")) {
                                       status.add("Attendance was taken manually");
                                   } else {
                                       status.add(returnMessage);
                                   }
                               } catch (Exception e) {
                                   System.out.println(e);
                                   System.out.println("EGov data upload api error.");
                                   //For mail service
                                   status.add("Not Uploaded. Something went wrong.");
                               }
                                // status.add("Just Checking");
                            }
                            else {
                                status.add("Zero student present. Hense not uploaded to EGovernance");
                            }
                        }
                        attendanceMailObject.setDate(date);
                        attendanceMailObject.setTime(time);
                        attendanceMailObject.setLacDetails(lacDetails);
                        attendanceMailObject.setTotalStudent(totalStudent);
                        attendanceMailObject.setSubject(subject);
                        attendanceMailObject.setStatus(status);
                        mailService.attendanceMailService(attendanceMailObject);
                    }
                    else{
                        System.out.println("Today's data isn't available for facultyID: "+viewEgovFacultyList.getFaculty()+" in EGov fetch Api");
                        // TODO : Add email service. to notify system admin.
                    }
            }
        System.out.println("******** Egovernance Data Uploading Service Ended *********");
        mailService.statasticMailService(uploadedAttendanceCount);
    }


    // eGovernance Data upload function that takes the entries made in the entire day to upload the attendance
    @Scheduled(cron = "0 18 * * *")
    // The following will ensure that the 
    @Scheduled(fixedDelay=Long.MAX_VALUE, timeUnit = TimeUnit.NANOSECONDS ,initialDelay = 5000)
    public void ZapToEgovAttendanceDayBased(){
        int uploadedAttendanceCount = 0;
        System.out.println("******** Egovernance Data Uploading Service started **********");

        //Fetching FacultyId of Todays Faculty Lecture
        List<viewEgovFacultyList> viewEgovFacultyListList = viewEgovFacultyListRepositories.findByTableFrom("main");

        //Uploading data to Egov for each facultyId
        for(viewEgovFacultyList viewEgovFacultyList : viewEgovFacultyListList){

            //For mail serivce
            AttendanceMailObject attendanceMailObject = new AttendanceMailObject();
            attendanceMailObject.setFacultyId(viewEgovFacultyList.getFaculty());
            viewFaculties viewFaculties = viewFacultiesRepositories.findByEnrollmentNo(String.valueOf(viewEgovFacultyList.getFaculty()));
            attendanceMailObject.setName(viewFaculties.getName());
            attendanceMailObject.setEmail(viewFaculties.getEmail());

                System.out.println("Fetching TimeTable for FacultyId:- "+viewEgovFacultyList.getFaculty());
                System.out.println("Dates: "+viewEgovFacultyList.getStartDate() + " - "+viewEgovFacultyList.getEndDate());
                JSONArray rawDataToInsert;
                try {
                   // TimeTableFetchApi Function Call
                   rawDataToInsert = TimeTableFetchApi(viewEgovFacultyList);
                }
                catch (Exception e){

                    //TODO Add notification mailservice for ZAP admin

                    System.out.println("EGov Faculty TimeTable Api fetching error.");
                    continue;

                }

                    //Checking API Status Data
                    int a = (int) rawDataToInsert.getJSONObject(0).get("Status");
                    if (a==1) {
                        List<EGovFetchDataObject> eGovFetchDataObjects = new ArrayList<>();
                        List<EgovTimetableStore> egovTimetableStoreList = new ArrayList<>();

                        for (int i = 0; i < rawDataToInsert.length(); i++) {
                            EGovFetchDataObject eGovFetchDataObject = new EGovFetchDataObject();
                            eGovFetchDataObject.setActualTimeTableID( ""+rawDataToInsert.getJSONObject(i).get("ActualTimeTableID"));
                            eGovFetchDataObject.setDate((String) rawDataToInsert.getJSONObject(i).get("Date"));
                            eGovFetchDataObject.setTimeStart(((String) rawDataToInsert.getJSONObject(i).get("Time")).substring(0, 5));
                            eGovFetchDataObject.setTimeEnd(((String) rawDataToInsert.getJSONObject(i).get("Time")).substring(8, 13));
                            eGovFetchDataObject.setLecDetails((String) rawDataToInsert.getJSONObject(i).get("LecDetails"));
                            eGovFetchDataObject.setSubject((String) rawDataToInsert.getJSONObject(i).get("Subject"));
                            eGovFetchDataObject.setStudentID_All(List.of(((String) rawDataToInsert.getJSONObject(i).get("StudentID_All")).split(",")));
                            eGovFetchDataObjects.add(eGovFetchDataObject);

                            EgovTimetableStore egovTimetableStore = new EgovTimetableStore();
                            egovTimetableStore.setActualTimetableId(""+rawDataToInsert.getJSONObject(i).get("ActualTimeTableID"));
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Calendar calendar = Calendar.getInstance();
                            Date date = calendar.getTime();
                            try {
                                date =  dateFormat.parse((String)rawDataToInsert.getJSONObject(i).get("Date"));
                            }
                            catch (ParseException pe){
                                System.out.println("Error: String not converted to date.");
                            }
                            egovTimetableStore.setDate(date);
                            egovTimetableStore.setTime(""+rawDataToInsert.getJSONObject(i).get("Time"));
                            egovTimetableStore.setParentSubjectId(""+rawDataToInsert.getJSONObject(i).get("ParentSubjectID"));
                            egovTimetableStore.setSubjectName(""+rawDataToInsert.getJSONObject(i).get("SubjectName"));
                            egovTimetableStore.setFacultyId(""+viewEgovFacultyList.getFaculty());
                            egovTimetableStore.setFacultyName(""+rawDataToInsert.getJSONObject(i).get("FacultyName"));
                            egovTimetableStore.setStudentIdAll(List.of((""+rawDataToInsert.getJSONObject(i).get("StudentID_All")).split(",")));
                            egovTimetableStore.setLectureDetails(""+rawDataToInsert.getJSONObject(i).get("LecDetails"));
                            egovTimetableStoreList.add(egovTimetableStore);
                        }
                        egovTimetableStoreDAO.addDataList(egovTimetableStoreList);

                        //For Mail Service
                        List<String> lacDetails = new ArrayList<>();
                        List<String> date = new ArrayList<>();
                        List<String> time = new ArrayList<>();
                        List<String> totalStudent = new ArrayList<>();
                        List<String> status = new ArrayList<>();
                        List<String> subject = new ArrayList<>();
                        
                        
                        List<main_attendance> mainAttendanceEntriesTouched = new ArrayList<>();
                        
                        
                        //Uploading data on EGov for each faculty lecture
                        for (EGovFetchDataObject eGovFetchDataObject : eGovFetchDataObjects) {

                            List<String> expectedStudents = eGovFetchDataObject.getStudentID_All();
                            System.out.println("\n\nUploading attendance of TimeTableId "+eGovFetchDataObject.getActualTimeTableID()+" and Students:");

//                            DateTimeFormatter timeStampFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                           int[] dty = parseIntArray( eGovFetchDataObject.getDate().split("/"));
                           String Date1  = dty[2]+ "-"+dty[1]+ "-"+dty[0] + " 00:00:00.000";
                           String Date2 = dty[2]+ "-"+dty[1]+ "-"+dty[0] + " 59:59:59.999";

                           System.out.println(Date1);
                           System.out.println(Date2);
//                            String Date1 = String.valueOf(LocalDate.now()) + " "+"17:30"+":00.000";
//                            String Date2 = String.valueOf(LocalDate.now()) + " "+"18:30"+":59.999";

                            //Today for each lecture Student fetch
                            List<main_attendance> attendedStudent = mainAttendanceRepositories.findByFacultyAndCreatedDateBetweenAndStudentIn(String.valueOf(viewEgovFacultyList.getFaculty()),Timestamp.valueOf(Date1), Timestamp.valueOf(Date2), expectedStudents);
                            List<String> studentLists = attendedStudent.stream().map(main_attendance::getStudent).collect(Collectors.toList());
                            System.out.println("Total Student Present Count: "+studentLists.size());
                            System.out.println(studentLists);

                            //For mail service
                            lacDetails.add(eGovFetchDataObject.getLecDetails());
                            date.add(eGovFetchDataObject.getDate());
                            time.add(eGovFetchDataObject.getTimeStart() + " - "+eGovFetchDataObject.getTimeEnd());
                            totalStudent.add(String.valueOf(studentLists.size()));
                            subject.add(eGovFetchDataObject.getSubject());
                            if (!studentLists.isEmpty()) {
                               try {
                                   //EGovDataUpload Function Call
                                //    String returnMessage = "TEST";
                                   String returnMessage = EGovDataUpload(eGovFetchDataObject.getActualTimeTableID(), studentLists);
                                   uploadedAttendanceCount += studentLists.size();
                                   List<uploadedAttendance> uploadedAttendanceList = attendedStudent.stream().map(student -> modelMapper.map(student, uploadedAttendance.class)).collect(Collectors.toList());
                                   uploadedAttendanceRepositories1.saveAll(uploadedAttendanceList);
                                   mainAttendanceEntriesTouched.addAll(attendedStudent);

                                   //For mail service
                                   if (Objects.equals(returnMessage, "Attendance is already taken...")) {
                                       status.add("Attendance was taken manually");
                                   } else {
                                       status.add(returnMessage);
                                   }
                               } catch (Exception e) {
                                   System.out.println(e);
                                   System.out.println("EGov data upload api error.");
                                   //For mail service
                                   status.add("Not Uploaded. Something went wrong.");
                               }
                                System.out.println("STUDENTS LIST: "+studentLists);
                                // status.add("Just Checking");
                            }
                            else {
                                status.add("Zero students present. Hence not uploaded to EGovernance");
                            }
                        }
                        mainAttendanceRepositories.deleteAll(mainAttendanceEntriesTouched);

                        attendanceMailObject.setDate(date);
                        attendanceMailObject.setTime(time);
                        attendanceMailObject.setLacDetails(lacDetails);
                        attendanceMailObject.setTotalStudent(totalStudent);
                        attendanceMailObject.setSubject(subject);
                        attendanceMailObject.setStatus(status);
                        mailService.attendanceMailService(attendanceMailObject);
                    
                    }
                    else{
                        String msg = "Data for facultyID: "+viewEgovFacultyList.getFaculty()+" is not available in eGov";
                        System.out.println(msg);

                        //TODO fetch the emails from the database

                        List<String> emails = new ArrayList<>();
                        emails.add("ceo@teamelementals.in"); 
                        mailService.notifyMailService(emails, MailService.NOTIFICATION_LEVEL_WARNING, msg);
                    }
            }
        System.out.println("******** Egovernance Data Uploading Service Ended *********");
        mailService.statasticMailService(uploadedAttendanceCount);
    }



}
