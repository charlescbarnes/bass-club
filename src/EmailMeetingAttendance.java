import java.util.ArrayList;

public class EmailMeetingAttendance extends Email {
    /**
     * Class constructor
     * @param recipients    an ArrayList of recipient email addresses
     * @param meeting       an instance of the <code>Meeting</code> class
     */
    public EmailMeetingAttendance(ArrayList<String> recipients, Meeting meeting) {
        super(recipients, "", meeting.toString());
        setSubject(meeting.getMonth()
                + " meeting attendance - Big Sandy Creek Bass Club");
    }

    public void send() {
        setSentMessage("Meeting attendance email sent!");
        send(false);
    }
}
