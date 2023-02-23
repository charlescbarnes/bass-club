import java.util.Arrays;

public class Meeting {
    private String month;
    private String email;

    /**
     * Class constructor
     * @param monthNumber
     * @param attendance    a string array of <code>Angler</code> nicknames
     *                      i.e., keys in <code>Angler.memberMap</code>
     */
    public Meeting(int monthNumber, String[] attendance) {
        month = "";
        if (monthNumber == 1) { month = "January"; }
        if (monthNumber == 2) { month = "February"; }
        if (monthNumber == 3) { month = "March"; }
        if (monthNumber == 4) { month = "April"; }
        if (monthNumber == 5) { month = "May"; }
        if (monthNumber == 6) { month = "June"; }
        if (monthNumber == 7) { month = "July"; }
        if (monthNumber == 8) { month = "August"; }
        if (monthNumber == 9) { month = "September"; }
        if (monthNumber == 10) { month = "October"; }
        if (monthNumber == 11) { month = "November"; }
        if (monthNumber == 12) { month = "December"; }

        // create HTML string for meeting attendance email body
        email = "<h2>" + month + " meeting attendance</h2><ol>";
        Angler[] anglersPresent = new Angler[attendance.length];
        for (int i = 0; i < attendance.length; i++) {
            anglersPresent[i] = Angler.getMemberMap().get(attendance[i]);
        }
        // sort anglers alphabetically
        Arrays.sort(anglersPresent, Angler::compareTo);
        for (Angler angler : anglersPresent) {
            // give angler meeting attendance points
            angler.addMeetingPoints();
            // and add the angler to an ordered list
            email += "<li>" + angler.getName() + "</li>";
        }
        email += "</ol><h2>Updated standings</h2>"
                + Angler.getHTMLStandings();
    }

    public String getMonth() { return month; }

    public String toString() { return email; }
}
