import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;

public class Angler {
    private static HashMap<String, Angler> memberMap;
    private static ArrayList<Angler> members;
    private static ArrayList<String> officerListServe;
    private static ArrayList<String> testListServe;
    private String name;
    private String email;
    private String phoneNumber;
    private int rank;
    private int totalFish;
    private double totalWeight;
    private int meetingPoints;
    private int tourAttendance;
    private int extraPoints;
    private Vector<Integer> fishWeighed;
    private int bigBags;
    private int wins;
    private boolean isOfficer;
    private double dropWeight;

    /**
     * Class constructor
     * @param name          the angler's name
     * @param email         the angler's email address
     * @param phoneNumber   the angler's phone number
     * @param isOfficer     records whether the angler is a club officer
     */
    public Angler(String name, String email, String phoneNumber, boolean isOfficer) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.isOfficer = isOfficer;
        members.add(this);
        fishWeighed = new Vector<>();
    }

    /**
     * adds an ArrayList entry (a number of fish) to an Angler's total number of fishWeighed
     * @param fish  a number of fish
     */
    public void addFish(int fish) {
        totalFish += fish;
        fishWeighed.add(fish);
    }

    public void addWeight(double weight) {
        totalWeight += weight;
    }

    /**
     * Reads Members20XX.txt, placing each member in the memberMap (as a nickname-Angler
     * instance key-value entry), as well as placing appropriate members in the officer
     * and test list serves
     */
    public static void setMemberMap() {
        memberMap = new HashMap<>();
        members = new ArrayList<>();

        // gets the appropriate year when Angler.setMemberMap() is called
        // in the main method
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        StackTraceElement main = stack[stack.length - 1];
        String mainClass = main.getClassName();
        int year = Integer.parseInt(mainClass.substring(mainClass.length()-4));

        officerListServe = new ArrayList<>();
        testListServe = new ArrayList<>();
        try(BufferedReader in = new BufferedReader(new FileReader("Members" + year + ".txt"))) {
            String str;
            while ((str = in.readLine()) != null) {
                String[] entry = str.split(",");
                String nickname = entry[0];
                String name = entry[1];
                String email = "";
                String phoneNumber = "";
                boolean isOfficer = false;
                if (entry.length >= 3 && isValidEmailAddress(entry[2])) {
                    email = entry[2];
                }
                if (entry.length >= 4) {
                    phoneNumber = entry[3];
                }
                if (entry.length >= 5) {
                    if (entry[4].equals("*")) {
                        isOfficer = true;
                    }
                }
                memberMap.put(nickname, new Angler(name, email, phoneNumber, isOfficer));
                if (isOfficer) {
                    officerListServe.add(email);
                }
                if (entry.length >= 6) {
                    if (entry[5].equals("@")) {
                        testListServe.add(email);
                    }
                }
            }
        }
        catch (IOException e) {
            System.out.println("File Read Error");
        }
    }

    /**
     * email validation method
     * @param email a proposed email address
     * @return  <code>true</code> if this is a valid email address;
     *          <code>false</code> otherwise.
     */
    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();
        }
        catch (AddressException e) {
            result = false;
        }
        return result;
    }
    public void addMeetingPoints() { meetingPoints += 2; }
    public static void addExtraPoints(String anglerNickname, int numPoints) {
        getMemberMap().get(anglerNickname).extraPoints += numPoints;
    }
    public void addTourAttendance(int i) { tourAttendance += i; }

    public void setRank(int i) { rank = i; }

    public void setWins() {
        int count = 0;
        for (Tournament tournament: Tournament.getTournaments()) {
            if (!tournament.getIsTwoDay()) {
                if (this.equals(tournament.getTeamWeights().get(0).getBoater())
                        || this.equals(tournament.getTeamWeights().get(0).getCoAngler())) {
                    count++;
                }
            }
            else {
                if (this.equals(tournament.getTwoDayTeamWeights().get(0).getBoater())
                        || this.equals(tournament.getTwoDayTeamWeights().get(0).getCoAngler())) {
                    count++;
                }
            }
        }
        wins = count;
    }

    public void setBigBags() {
        int count = 0;
        for (Tournament tournament: Tournament.getTournaments()) {
            if (this.equals(tournament.getBigBagAngler())) {
                count++;
            }
        }
        bigBags = count;
    }

    /**
     * Each angler's worst performance of the season is dropped from the AOY ranking calculations
     * This determined the weight to drop (either 0 if the angler didn't attend every event,
     * or the angler's lowest weight if they did)
     */
    public void setDropWeight() {
        dropWeight = 0;
        if (Tournament.getTournaments().size() == 12) {
            if (tourAttendance == Tournament.getTournamentPointsPossible()) {
                double lowestWeight = Double.MAX_VALUE;
                for (Tournament tournament : Tournament.getTournaments()) {
                    for (TeamWeight result : tournament.getTeamWeights()) {
                        if (this.equals(result.getBoater()) && result.getBoaterWeight() < lowestWeight) {
                            lowestWeight = result.getBoaterWeight();
                        }
                        if (this.equals(result.getBoater()) && result.getBoaterWeight() < lowestWeight) {
                            lowestWeight = result.getBoaterWeight();
                        }
                    }
                }
                dropWeight = lowestWeight;
            }
        }
    }

    public static ArrayList<Angler> getMembers() {
        members.sort(Comparator.comparing(Angler::getName));
        return members;
    }

    public static HashMap<String, Angler> getMemberMap() {
        return memberMap;
    }

    public static ArrayList<String> getOfficerListServe() {
        return officerListServe;
    }

    public static ArrayList<String> getTestListServe() {
        return testListServe;
    }

    public String getName() { return name; }

    public boolean getIsOfficer() { return isOfficer; }

    public String getEmail() { return email; }

    public String getPhoneNumber() { return phoneNumber; }
    public int getTourAttendance() { return tourAttendance; }
    public int getRank() { return rank; }

    /**
     * @return the average number of fish caught per tournament day
     */
    public double getFishPerDay() {
        return (double) (totalFish) / fishWeighed.size();
    }

    /**
     * @return the average weight an angler weighed per tournament day
     */
    public double getWeightPerDay() {
        return totalWeight / fishWeighed.size();
    }

    /**
     * @return the number of events in which the angler had the big bag of the tournament
     */
    public int getBigBags() {
        this.setBigBags();
        return bigBags;
    }

    /**
     * @return  A string containing the list of lakes (i.e. <code>
     *     Tournament</code>s) for which the <code>Angler</code> was
     *     awarded Big Bag of the Tournament.
     */
    public String getBigBagLakes() {
        this.setBigBags();
        if (bigBags == 0)
            return "";
        String[] bigBagLakes = new String[bigBags];
        int i = 0;
        for (Tournament tournament : Tournament.getTournaments()) {
            if (this.equals(tournament.getBigBagAngler())) {
                bigBagLakes[i] = tournament.getLake();
                i++;
            }
        }
        if (bigBags == 1)
            return bigBagLakes[0];
        else if (bigBags == 2)
            return bigBagLakes[0] + " and " + bigBagLakes[1];
        else {
            StringBuilder str = new StringBuilder("and " + bigBagLakes[bigBags-1]);
            for (int j = 0; j < bigBags-1; j++)
                str.insert(0, bigBagLakes[bigBags-2-j] + ", ");
            return str.toString();
        }
    }

    /**
     * @return  A string containing the list of lakes (i.e. <code>
     *     Tournament</code>s) for which the <code>Angler</code> was
     *     awarded Big Bass of the Tournament.
     */
    public String getBigBassLakes() {
        ArrayList<Tournament> bigBassEvents = new ArrayList<>();
        for (Tournament tournament : Tournament.getTournaments()) {
            if (this.equals(tournament.getBigBassAngler())) {
                bigBassEvents.add(tournament);
            }
        }
        int bigBass = bigBassEvents.size();
        if (bigBass == 0) {
            return "";
        }
        else if (bigBass == 1) {
            return bigBassEvents.get(0).getLake();
        }
        else if (bigBass == 2) {
            return bigBassEvents.get(0).getLake() + " and " + bigBassEvents.get(1).getLake();
        }
        else {
            StringBuilder str = new StringBuilder("and " + bigBassEvents.get(bigBass-1).getLake());
            for (int j = 0; j < bigBass-1; j++) {
                str.insert(0, bigBassEvents.get(bigBass - 2 - j).getLake() + ", ");
            }
            return str.toString();
        }
    }

    /**
     * @return  A string containing the list of lakes (i.e. <code>
     *     Tournament</code>s) that the <code>Angler</code> won
     */
    public String getWinLakes() {
        this.setWins();
        if (wins == 0)
            return "";
        String[] winLakes = new String[wins];
        int i = 0;
        ArrayList<Tournament> events = new ArrayList<>(Tournament.getTournaments());
        for (Tournament event : events) {
            if (!event.getIsTwoDay()) {
                ArrayList<TeamWeight> finishes = new ArrayList<>(event.getTeamWeights());
                if (this.equals(finishes.get(0).getBoater()) || this.equals(finishes.get(0).getCoAngler())) {
                    winLakes[i] = event.getLake();
                    i++;
                }
            }
            else {
                ArrayList<TwoDayTeamWeight> finishes = new ArrayList<>(event.getTwoDayTeamWeights());
                if (this.equals(finishes.get(0).getBoater()) || this.equals(finishes.get(0).getCoAngler())) {
                    winLakes[i] = event.getLake();
                    i++;
                }
            }
        }
        if (wins == 1)
            return winLakes[0];
        else if (wins == 2)
            return winLakes[0] + " and " + winLakes[1];
        else {
            StringBuilder str = new StringBuilder("and " + winLakes[wins-1]);
            for (int j = 0; j < wins-1; j++) {
                str.insert(0, winLakes[wins - 2 - j] + ", ");
            }
            return str.toString();
        }
    }

    public int getWins() {
        this.setWins();
        return wins;
    }

    /**
     * @return  A string containing every TeamWeight (or TwoDayTeamWeight) entry
     *          for a given <code>Angler</code>
     */
    public String getAllFinishes() {
        StringBuilder finishes = new StringBuilder(
                String.format("%-10s%-13s%-7s%-19s%6s%7s%8s%4s%-19s%6s%7s%8s%8s",
                "Month", "Lake", "Place", "Boater", "Fish", "Big", "Weight", "",
                "Co-angler", "Fish", "Big", "Weight", "Total")+"\n");
        ArrayList<Tournament> events = new ArrayList<>(Tournament.getTournaments());
        for (Tournament event : events) {
            ArrayList<TeamWeight> weighIns = new ArrayList<>(event.getTeamWeights());
            if (!event.getIsTwoDay()) {
                for (TeamWeight teamWeight : weighIns) {
                    if (this.equals(teamWeight.getBoater()) || this.equals(teamWeight.getCoAngler())) {
                        int i = weighIns.indexOf(teamWeight);
                        boolean tiedAbove;
                        boolean tiedBelow;
                        try {
                            tiedAbove = weighIns.get(i-1).getPlace() == teamWeight.getPlace();
                        }
                        catch(Exception e) {
                            tiedAbove = false;
                        }
                        try {
                            tiedBelow = weighIns.get(i+1).getPlace() == teamWeight.getPlace();
                        }
                        catch(Exception e) {
                            tiedBelow = false;
                        }
                        String newPlace = "";
                        if (tiedAbove || tiedBelow)
                            newPlace += "T";
                        newPlace += teamWeight.getPlace() + "/";
                        finishes.append(String.format("%-10s%-12s%4s%-4s",
                                event.getMonth(),
                                event.getLake(),
                                newPlace,
                                weighIns.size()))
                                .append(teamWeight.toString().substring(6))
                                .append("\n");
                    }
                }
            }
            else {
                ArrayList<TeamWeight> day1WeighIns = new ArrayList<>();
                ArrayList<TeamWeight> day2WeighIns = new ArrayList<>();
                for (TeamWeight weighIn : weighIns) {
                    if (weighIn.getDay() == 1)
                        day1WeighIns.add(weighIn);
                    else
                        day2WeighIns.add(weighIn);
                }
                day1WeighIns.sort(Comparator.comparing(TeamWeight::getTeamWeight).reversed());
                day2WeighIns.sort(Comparator.comparing(TeamWeight::getTeamWeight).reversed());
                for (ArrayList<TeamWeight> dayNWeighIns : new ArrayList[]{day1WeighIns, day2WeighIns}) {
                    for (TeamWeight teamWeight : dayNWeighIns) {
                        if (this.equals(teamWeight.getBoater()) || this.equals(teamWeight.getCoAngler())) {
                            int i = dayNWeighIns.indexOf(teamWeight);
                            int tempPlace = 1;
                            if (i > 0) {
                                for (int k = 2; k <= i+1; k++) {
                                    if (dayNWeighIns.get(k-2).getTeamWeight()!=dayNWeighIns.get(k-1).getTeamWeight())
                                        tempPlace = k;
                                }
                            }
                            boolean tiedAbove;
                            boolean tiedBelow;
                            try {
                                tiedAbove = dayNWeighIns.get(i-1).getTeamWeight() == teamWeight.getTeamWeight();
                            }
                            catch (Exception e) {
                                tiedAbove = false;
                            }
                            try {
                                tiedBelow = dayNWeighIns.get(i+1).getTeamWeight() == teamWeight.getTeamWeight();
                            }
                            catch (Exception e) {
                                tiedBelow = false;
                            }
                            String newPlace = "";
                            if (tiedAbove || tiedBelow)
                                newPlace += "T";
                            newPlace += tempPlace + "/";
                            finishes.append(String.format("%-10s%-12s%4s%-4s",
                                    event.getMonth(),
                                    event.getLake(),
                                    newPlace,
                                    dayNWeighIns.size()))
                                    .append(teamWeight.toString().substring(6))
                                    .append("\n");
                        }
                    }
                }
            }
        }
        return finishes.toString();
    }

    /**
     * @param anglerNickname the <code>Angler</code>'s key value in <code>memberMap</code>
     * @return  A string consisting of HTML code containing every <code>TeamWeight</code>
     *          (or <code>TwoDayTeamWeight</code>) entry for a given <code>Angler</code>
     */
    public static String getHTMLAllFinishes(String anglerNickname) {
        Angler angler = getMemberMap().get(anglerNickname);
        StringBuilder finishes = new StringBuilder("""
                <center>
                    <table style="width:60em">
                        <tr>
                            <th style="text-align: left">Month</th>
                            <th style="text-align: left">Lake</th>
                            <th>Place</th><th style="text-align: left">Boater</th>
                            <th>Fish</th><th style="text-align: right">Big</th>
                            <th style="text-align: right">Weight</th>
                            <th style="width:1em"></th>
                            <th style="text-align: left">Co-angler</th>
                            <th>Fish</th>
                            <th style="text-align: right">Big</th>
                            <th style="text-align: right">Weight</th>
                            <th style="text-align: right">Total</th>
                        </tr>
                """);
        ArrayList<Tournament> events = new ArrayList<>(Tournament.getTournaments());
        for (Tournament event : events) {
            ArrayList<TeamWeight> weighIns = new ArrayList<>(event.getTeamWeights());
            if (!event.getIsTwoDay()) {
                for (TeamWeight teamWeight : weighIns) {
                    if (angler.equals(teamWeight.getBoater()) || angler.equals(teamWeight.getCoAngler())) {
                        int i = weighIns.indexOf(teamWeight);
                        boolean tiedAbove;
                        boolean tiedBelow;
                        try {
                            tiedAbove = weighIns.get(i-1).getPlace() == teamWeight.getPlace();
                        }
                        catch(Exception e) {
                            tiedAbove = false;
                        }
                        try {
                            tiedBelow = weighIns.get(i+1).getPlace() == teamWeight.getPlace();
                        }
                        catch(Exception e) {
                            tiedBelow = false;
                        }
                        String newPlace = "";
                        if (tiedAbove || tiedBelow)
                            newPlace += "T";
                        newPlace += teamWeight.getPlace() + "/" + weighIns.size();
                        finishes.append("<tr><td>")
                                .append(event.getMonth())
                                .append("</td><td>")
                                .append(event.getLake())
                                .append("</td><td  style=\"text-align: center\">")
                                .append(newPlace)
                                .append("</td>")
                                .append(teamWeight.toHTMLString().substring(teamWeight.toHTMLString().indexOf("/") + 4));
                    }
                }
            }
            else {
                ArrayList<TeamWeight> day1WeighIns = new ArrayList<>();
                ArrayList<TeamWeight> day2WeighIns = new ArrayList<>();
                for (TeamWeight weighIn : weighIns) {
                    if (weighIn.getDay() == 1)
                        day1WeighIns.add(weighIn);
                    else
                        day2WeighIns.add(weighIn);
                }
                day1WeighIns.sort(Comparator.comparing(TeamWeight::getTeamWeight).reversed());
                day2WeighIns.sort(Comparator.comparing(TeamWeight::getTeamWeight).reversed());
                for (ArrayList<TeamWeight> dayNWeighIns : new ArrayList[]{day1WeighIns, day2WeighIns}) {
                    for (TeamWeight teamWeight : dayNWeighIns) {
                        if (angler.equals(teamWeight.getBoater()) || angler.equals(teamWeight.getCoAngler())) {
                            int i = dayNWeighIns.indexOf(teamWeight);
                            int tempPlace = 1;
                            if (i > 0) {
                                for (int k = 2; k <= i+1; k++) {
                                    if (dayNWeighIns.get(k-2).getTeamWeight()!=dayNWeighIns.get(k-1).getTeamWeight())
                                        tempPlace = k;
                                }
                            }
                            boolean tiedAbove;
                            boolean tiedBelow;
                            try {
                                tiedAbove = dayNWeighIns.get(i-1).getTeamWeight() == teamWeight.getTeamWeight();
                            }
                            catch (Exception e) {
                                tiedAbove = false;
                            }
                            try {
                                tiedBelow = dayNWeighIns.get(i+1).getTeamWeight() == teamWeight.getTeamWeight();
                            }
                            catch (Exception e) {
                                tiedBelow = false;
                            }
                            String newPlace = "";
                            if (tiedAbove || tiedBelow)
                                newPlace += "T";
                            newPlace += tempPlace + "/" + dayNWeighIns.size();
                            finishes.append("<tr><td>")
                                    .append(event.getMonth())
                                    .append("</td><td>")
                                    .append(event.getLake())
                                    .append("</td><td  style=\"text-align: center\">")
                                    .append(newPlace)
                                    .append("</td>")
                                    .append(teamWeight.toHTMLString().substring(teamWeight.toHTMLString().indexOf("/") + 4));
                        }
                    }
                }
            }
        }
        finishes.append("</table></center>");
        return finishes.toString();
    }

    public double getDropWeight() {
        this.setDropWeight();
        return dropWeight;
    }

    /**
     * @param anglerNickname the <code>Angler</code>'s key value in <code>memberMap</code>
     * @return  A string summarizing the <code>Angler</code>'s season
     */
    public static String getSeasonRecap(String anglerNickname) {
        Angler angler = getMemberMap().get(anglerNickname);
        StringBuilder str = new StringBuilder("Here are your tournament finishes from this year:\n\n");
        str.append(angler.getAllFinishes());
        String accolades = "";
        if (angler.getBigBags() >= 1) {
            accolades += "You had big bag of the tournament on "
                    + angler.getBigBagLakes();
            if (!angler.getBigBassLakes().equals("")) {
                accolades += ", and you had big bass of the tournament on "
                        + angler.getBigBassLakes() + "! ";
            }
            else {
                accolades += "! ";
            }
        }
        else if (!angler.getBigBassLakes().equals("")) {
            accolades += "You had big bass of the tournament on "
                    + angler.getBigBassLakes() + "! ";
        }
        if (!accolades.equals("")) {
            str.append("\n").append(accolades).append("\n");
        }
        angler.setDropWeight();
        if (Tournament.getTournaments().size() == 12) {
            str.append("\n");
            if (angler.tourAttendance == Tournament.getTournamentPointsPossible()) {
                String month = "";
                String lake = "";
                for (Tournament tournament : Tournament.getTournaments()) {
                    for (TeamWeight result : tournament.getTeamWeights()) {
                        if ((angler.equals(result.getBoater()) && angler.dropWeight == result.getBoaterWeight())
                                || (angler.equals(result.getCoAngler()) && angler.dropWeight == result.getCoAnglerWeight())) {
                            month = tournament.getMonth();
                            lake = tournament.getLake();
                            break;
                        }
                    }
                    if (!month.equals(""))
                        break;
                }
                str.append("You fished every tournament this year, so your worst finish (")
                        .append(month)
                        .append(", ")
                        .append(lake)
                        .append(", ")
                        .append(angler.getDropWeight())
                        .append(" lbs) was dropped from your total points in the AOY standings.");
            }
            else {
                int missedTourneys = 12;
                for (Tournament tournament : Tournament.getTournaments()) {
                    for (TeamWeight result : tournament.getTeamWeights()) {
                        if (angler.equals(result.getBoater()) || angler.equals(result.getCoAngler())) {
                            missedTourneys--;
                            break;
                        }
                    }
                }
                str.append("You missed ").append(missedTourneys).append(" tournament");
                if (missedTourneys > 1) {
                    str.append("s");
                }
                str.append(" this year, so no weight was dropped from your total points in the AOY standings.");
            }
        }
        return str.toString();
    }

    /**
     * @param anglerNickname the <code>Angler</code>'s key value in <code>memberMap</code>
     * @return  A string consisting of HTML code summarizing the <code>Angler</code>'s season
     */
    public static String getSeasonRecapHTML(String anglerNickname) {
        Angler angler = getMemberMap().get(anglerNickname);
        boolean fishedThisYear = angler.getTourAttendance() > 0;
        StringBuilder str = new StringBuilder("<p>Hey " + angler.getName().split(" ")[0] + "!</p><p>"
                    + "Here's your BSCBC season recap");
        if (fishedThisYear) {
            str.append(", starting with your tournament finishes from this year:</p>")
                    .append(getHTMLAllFinishes(anglerNickname));
        }
        else {
            str.append(".</p>");
        }
        String accolades = "";
        if (angler.getBigBags() >= 1) {
            accolades += "You had big bag of the tournament on "
                    + angler.getBigBagLakes();
            if (!angler.getBigBassLakes().equals("")) {
                accolades += ", and you had big bass of the tournament on "
                        + angler.getBigBassLakes() + "! ";
            }
            else {
                accolades += "! ";
            }
        }
        else if (!angler.getBigBassLakes().equals("")) {
            accolades += "You had big bass of the tournament on "
                    + angler.getBigBassLakes() + "! ";
        }
        if (!accolades.equals("")) {
            str.append("<p>").append(accolades).append(" Congrats!</p>");
        }
        angler.setDropWeight();
        if (Tournament.getTournaments().size() == 12) {
            str.append("\n");
            if (angler.tourAttendance == Tournament.getTournamentPointsPossible()) {
                String month = "";
                String lake = "";
                for (Tournament tournament : Tournament.getTournaments()) {
                    for (TeamWeight result : tournament.getTeamWeights()) {
                        if ((angler.equals(result.getBoater()) && angler.dropWeight == result.getBoaterWeight())
                                || (angler.equals(result.getCoAngler()) && angler.dropWeight == result.getCoAnglerWeight())) {
                            month = tournament.getMonth();
                            lake = tournament.getLake();
                            break;
                        }
                    }
                    if (!month.equals("")) {
                        break;
                    }
                }
                str.append("<p>You fished every tournament this year, so your worst finish (")
                        .append(month).append(", ").append(lake).append(", ")
                        .append(angler.getDropWeight())
                        .append(" lbs) was dropped from your total points in the AOY standings.</p>");
            }
            else {
                int missedTourneys = 12;
                for (Tournament tournament : Tournament.getTournaments()) {
                    for (TeamWeight result : tournament.getTeamWeights()) {
                        if (angler.equals(result.getBoater()) || angler.equals(result.getCoAngler())) {
                            missedTourneys--;
                            break;
                        }
                    }
                }
                str.append("<p>You missed ").append(missedTourneys).append(" tournament");
                if (missedTourneys > 1) {
                    str.append("s");
                }
                str.append(" this year, so no weight was dropped "
                        + "from your total points in the AOY standings.</p>");
            }
        }
        str.append(getHTMLStandings());
        if (angler.getRank() == 1) {
            str.append("<p>Congratulations on winning AOY!</p>");
        }
        else {
            str.append("<p>Congratulations to our AOY, ").append(members.get(0).getName()).append("! ");
        }
        str.append("Further congratulations to our winners of other accolades:</p>");
        str.append(Tournament.getHTMLAwards());
        if (fishedThisYear) {
            String timeOrTimes = " times";
            if (angler.getBigBags() == 1)
                timeOrTimes = " time";
            String eventOrEvents = " events";
            if (angler.getWins() == 1)
                eventOrEvents = " event";
            str.append("<p>See how you stacked up! This year you:<ul><li>averaged ")
                    .append(String.format("%.2f", angler.getFishPerDay()))
                    .append(" fish and ")
                    .append(String.format("%.2f", angler.getWeightPerDay()))
                    .append(" pounds per day,</li><li>had big bag of the tournament ")
                    .append(angler.getBigBags()).append(timeOrTimes)
                    .append(", and</li><li>won ")
                    .append(angler.getWins())
                    .append(eventOrEvents)
                    .append(".</li></ul></p>");
        }
        str.append("<p>Another year in the books! Start next year off right at")
                .append(" our January meeting and tournament. Keep up with us on our ")
                .append(Email.WEBSITE_HTML_HYPERLINK)
                .append(", ")
                .append(Email.FACEBOOK_HTML_HYPERLINK)
                .append(", and ")
                .append( Email.INSTAGRAM_HTML_HYPERLINK)
                .append(".</p>")
                .append("<p>Happy holidays!<br>Big Sandy Creek Bass Club</p>")
                .append(Email.getUnsubscribeHTML());
        return str.toString();
    }

    public double getTotalPoints() {
        this.setDropWeight();
        return totalWeight + meetingPoints + tourAttendance + extraPoints - dropWeight;
    }

    /**
     * @return a string containing the AOY standings
     */
    public static String getStandings() {
        members.sort(Comparator.comparing(Angler::getName));
        members.sort(Comparator.comparing(Angler::getTotalPoints).reversed());
        StringBuilder standings = new StringBuilder(
                String.format("%-4s%2s%-20s%6s%10s%16s%17s%15s",
                "Rank", "", "Angler", "Points", "Weight", "Meeting points", "Tour attendance", "Extra points\n")
        );
        members.get(0).setRank(1);
        standings.append(members.get(0)).append("\n");
        int j = 1;
        for (int i = 2; i <= members.size(); i++) {
            if (members.get(i-2).getTotalPoints() != members.get(i-1).getTotalPoints()) {
                j = i;
            }
            members.get(i-1).setRank(j);
            standings.append(members.get(i-1)).append("\n");
        }
        return standings.toString();
    }

    /**
     * @return a string consisting of HTML code containing the AOY standings
     */
    public static String getHTMLStandings() {
        members.sort(Comparator.comparing(Angler::getName));
        members.sort(Comparator.comparing(Angler::getTotalPoints).reversed());
        StringBuilder standings = new StringBuilder("""
                <center>
                    <table style="width:45em">
                    <tr>
                        <th style="text-align: left">Rank</th>
                        <th style="text-align: left">Angler</th>
                        <th style="text-align: right">Points</th>
                        <th style="text-align: right">Weight</th>
                        <th>Meeting points</th><th>Tour attendance</th>
                        <th>Extra points</th>
                    </tr>
                """);
        members.get(0).setRank(1);
        standings.append(members.get(0).toHTMLString());
        int j = 1;
        for (int i = 2; i <= members.size(); i++) {
            if (members.get(i-2).getTotalPoints() != members.get(i-1).getTotalPoints()) {
                j = i;
            }
            members.get(i-1).setRank(j);
            // only add active participants to standings printed in emails
            if (members.get(i-1).getTotalPoints() != 0)
                standings.append(members.get(i-1).toHTMLString());
        }
        standings.append("</table></center>");
        return standings.toString();
    }

    /**
     * @return an ArrayList of member email addresses
     */
    public static ArrayList<String> getListServe() {
        ArrayList<String> listServe = new ArrayList<>();
        for (Angler angler : members) {
            if (!angler.email.equals("")) {
                listServe.add(angler.email);
            }
        }
        return listServe;
    }

    /**
     * @return  an ArrayList of <code>Angler</code>s for whom we are
     *          missing a phone number or email address
     */
    public static ArrayList<Angler> getAnglersWithMissingContacts() {
        ArrayList<Angler> lst = new ArrayList<>();
        for (Angler angler : members) {
            if (angler.email.equals("") || angler.phoneNumber.equals(""))
                lst.add(angler);
        }
        lst.sort(Comparator.comparing(Angler::getName));
        return lst;
    }

    /**
     * @return  a string containing the list of <code>Angler</code>s
     *          for whom we are missing contact info, as well as the
     *          type of missing contact info
     */
    public static String getMissingContacts() {
        ArrayList<Angler> lst = getAnglersWithMissingContacts();
        StringBuilder print = new StringBuilder(
                String.format("%-19s%4s%-16s", "     Angler", "", "Missing info") + "\n"
        );
        if (!lst.isEmpty()) {
            for (Angler angler : lst) {
                String email = "";
                String number = "";
                if (angler.email.equals("")) {
                    email = "email";
                }
                if (angler.phoneNumber.equals("")) {
                    number = "phone number";
                }
                print.append(String.format("%-19s%-8s%-12s",
                        angler.name,
                        email,
                        number))
                        .append("\n");
            }
        }
        return print.toString();
    }

    /**
     * @return  a string consisting of HTML code containing the list of
     *          <code>Angler</code>s for whom we are missing contact info,
     *          as well as the type of missing contact info
     */
    public static String getMissingContactsHTML() {
        ArrayList<Angler> lst = getAnglersWithMissingContacts();
        StringBuilder print = new StringBuilder();
        if (!lst.isEmpty()) {
            print.append("<p>")
                    .append("<a href=\"mailto:bigsandycreek@gmail.com?subject=BSCBC member contact info&")
                    .append("body=I have contact info for...\">Help us</a>")
                    .append(" get a hold of the missing contact info listed below. Thanks!</p>")
                    .append("<center><table style=\"width:22em\">")
                    .append("<tr><th>Angler</th><th colspan=\"2\">Missing info</th></tr>");
            for (Angler angler : lst) {
                String email = "<td></td>";
                String number = "<td></td>";
                if (angler.email.equals("")) {
                    email = "<td style=\"text-align: center\">email</td>";
                }
                if (angler.phoneNumber.equals("")) {
                    number = "<td style=\"text-align: center\">phone number</td>";
                }
                print.append("<tr><td>")
                        .append(angler.name)
                        .append("</td>")
                        .append(email).append(number)
                        .append("</tr>");
            }
            print.append("</table></center>");
        }
        return print.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Angler a = (Angler) obj;
        return this.getName().equals(a.getName())
                && this.getEmail().equals(a.getEmail())
                && this.getPhoneNumber().equals(a.getPhoneNumber());
    }

    public int compareTo(Angler otherAngler) {
        return this.getName().compareTo(otherAngler.getName());
    }

    public String toString() {
        return String.format("%3d%3s%-20s%6.2f%10.2f%10d%17d%15d",
                rank, "",
                name,
                this.getTotalPoints(),
                totalWeight,
                meetingPoints,
                tourAttendance,
                extraPoints
        );
    }

    public String toHTMLString() {
        return  "<tr>"
                    +"<td style=\"text-align: center\">" + rank + "</td>"
                    + "<td>" + name + "</td>"
                    + "<td style=\"text-align: right\">" + String.format("%.2f", this.getTotalPoints()) + "</td>"
                    + "<td style=\"text-align: right\">" + String.format("%.2f", totalWeight) + "</td>"
                    + "<td style=\"text-align: center\">" + meetingPoints + "</td>"
                    + "<td style=\"text-align: center\">" + tourAttendance + "</td>"
                    + "<td style=\"text-align: center\">" + extraPoints + "</td>"
                + "</tr>";
    }
}
