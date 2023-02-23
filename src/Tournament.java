import java.util.ArrayList;
import java.util.Comparator;
public class Tournament {
    private static ArrayList<Tournament> tournaments = new ArrayList<>();
    private String month;
    private final String lake;
    private boolean isTwoDay;
    private int anglers;
    private int fishTotal;
    private int boaterLimits;
    private int boaterZeros;
    private int coLimits;
    private int coZeros;
    private double weightTotal;
    private Angler bigBagAngler;
    private int bigBagFish;
    private double bigBagWeight;
    private Angler bigBassAngler;
    private double bigBassWeight;
    private ArrayList<TeamWeight> teamWeights;
    static final double DEAD_FISH_PENALTY = 0.25;

    /**
     * Class constructor for a 1-day tournament
     * @param monthNumber   an <code>int</code> in {1, 2, ..., 12} representing a month
     * @param lake          the location of the tournament, as a <code>String</code>
     */
    public Tournament(int monthNumber, String lake) {
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
        this.lake = lake;
        isTwoDay = false;
        tournaments.add(this);
        teamWeights = new ArrayList<>();
    }

    /**
     * Class constructor with <code>isTwoDay</code> specification
     * @param monthNumber   an <code>int</code> in {1, 2, ..., 12} representing a month
     * @param lake          the location of the tournament, as a <code>String</code>
     * @param isTwoDay      denotes a 2-day tournament if true; a 1-day tournament if false
     */
    public Tournament(int monthNumber, String lake, boolean isTwoDay){
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
        this.lake = lake;
        this.isTwoDay = isTwoDay;
        tournaments.add(this);
        teamWeights = new ArrayList<>();
    }

    /**
     * <code>weighIn</code> method for 1-day events, and for 2-person teams
     * each participating "boat" or "team" records one <code>weighIn</code> per <code>Tournament</code>
     * @param boaterNickname    the <code>Angler</code>'s key value in <code>Angler.memberMap</code>
     * @param boaterFish        an <code>int</code> number of fish weighed
     * @param boaterFishAlive   an <code>int</code> number of fish weighed alive
     * @param boaterWeight      a <code>double</code> bag weight
     * @param boaterBigBass     a <code>double</code> big bass weight
     * @param coAnglerNickname  the <code>Angler</code>'s key value in <code>Angler.memberMap</code>
     * @param coFish            an <code>int</code> number of fish weighed
     * @param coFishAlive       an <code>int</code> number of fish weighed alive
     * @param coWeight          a <code>double</code> bag weight
     * @param coBigBass         a <code>double</code> big bass weight
     */
    public void weighIn(String boaterNickname, int boaterFish, int boaterFishAlive, double boaterWeight, double boaterBigBass,
                        String coAnglerNickname, int coFish, int coFishAlive, double coWeight, double coBigBass) {
        Angler boater = Angler.getMemberMap().get(boaterNickname);
        Angler coAngler = Angler.getMemberMap().get(coAnglerNickname);
        anglers += 2;
        boater.addTourAttendance(2);
        coAngler.addTourAttendance(2);
        fishTotal += boaterFish + coFish;
        if (boaterFish == 5)
            boaterLimits++;
        if (boaterFish == 0)
            boaterZeros++;
        if (coFish == 5)
            coLimits++;
        if (coFish == 0)
            coZeros++;
        weightTotal += boaterWeight + coWeight;
        double boaterWeightAfterPenalties = boaterWeight - (boaterFish-boaterFishAlive)*DEAD_FISH_PENALTY;
        double coWeightAfterPenalties = coWeight - (coFish-coFishAlive)*DEAD_FISH_PENALTY;
        if (boaterWeightAfterPenalties > bigBagWeight) {
            bigBagAngler = boater;
            bigBagFish = boaterFish;
            bigBagWeight = boaterWeightAfterPenalties;
        }
        if (coWeightAfterPenalties > bigBagWeight) {
            bigBagAngler = coAngler;
            bigBagFish = coFish;
            bigBagWeight = coWeightAfterPenalties;
        }
        if (boaterBigBass > bigBassWeight) {
            bigBassAngler = boater;
            bigBassWeight = boaterBigBass;
        }
        if (coBigBass > bigBassWeight) {
            bigBassAngler = coAngler;
            bigBassWeight = coBigBass;
        }
        teamWeights.add(new TeamWeight(boater, boaterFish, boaterFishAlive, boaterWeight, boaterBigBass,
                coAngler, coFish, coFishAlive, coWeight, coBigBass));
    }

    /**
     * <code>weighIn</code> method for 1-day events, and for 1-person teams
     * each participating "boat" or "team" records one <code>weighIn</code> per <code>Tournament</code>
     * @param boaterNickname    the <code>Angler</code>'s key value in <code>Angler.memberMap</code>
     * @param boaterFish        an <code>int</code> number of fish weighed
     * @param boaterFishAlive   an <code>int</code> number of fish weighed alive
     * @param boaterWeight      a <code>double</code> bag weight
     * @param boaterBigBass     a <code>double</code> big bass weight
     */
    public void weighIn(String boaterNickname, int boaterFish, int boaterFishAlive, double boaterWeight, double boaterBigBass) {
        Angler boater = Angler.getMemberMap().get(boaterNickname);
        anglers += 1;
        boater.addTourAttendance(2);
        fishTotal += boaterFish;
        if (boaterFish == 5)
            boaterLimits++;
        if (boaterFish == 0)
            boaterZeros++;
        weightTotal += boaterWeight;
        double boaterWeightAfterPenalties = boaterWeight - (boaterFish-boaterFishAlive)*DEAD_FISH_PENALTY;
        if (boaterWeightAfterPenalties > bigBagWeight) {
            bigBagAngler = boater;
            bigBagFish = boaterFish;
            bigBagWeight = boaterWeightAfterPenalties;
        }
        if (boaterBigBass > bigBassWeight) {
            bigBassAngler = boater;
            bigBassWeight = boaterBigBass;
        }
        teamWeights.add(new TeamWeight(boater, boaterFish, boaterFishAlive, boaterWeight, boaterBigBass,
                null, 0, 0, 0, 0));
    }

    /**
     * <code>weighIn</code> method for 2-day events, and for 2-person teams
     * @param day               an <code>int</code>: 1 or 2
     * @param boaterNickname    the <code>Angler</code>'s key value in <code>Angler.memberMap</code>
     * @param boaterFish        an <code>int</code> number of fish weighed
     * @param boaterFishAlive   an <code>int</code> number of fish weighed alive
     * @param boaterWeight      a <code>double</code> bag weight
     * @param boaterBigBass     a <code>double</code> big bass weight
     * @param coAnglerNickname  the <code>Angler</code>'s key value in <code>Angler.memberMap</code>
     * @param coFish            an <code>int</code> number of fish weighed
     * @param coFishAlive       an <code>int</code> number of fish weighed alive
     * @param coWeight          a <code>double</code> bag weight
     * @param coBigBass         a <code>double</code> big bass weight
     */
    public void weighIn(int day, String boaterNickname, int boaterFish, int boaterFishAlive, double boaterWeight, double boaterBigBass,
                        String coAnglerNickname, int coFish, int coFishAlive, double coWeight, double coBigBass) {
        Angler boater = Angler.getMemberMap().get(boaterNickname);
        Angler coAngler = Angler.getMemberMap().get(coAnglerNickname);
        // ensures that the number of anglers are not double-counted
        // and that participants don't receive double-points for participation
        // regardless of the order that weighIns are recorded
        boolean alreadyThere = false;
        for (TeamWeight entry : teamWeights) {
            if (entry.getBoater().equals(boater)) {
                alreadyThere = true;
                break;
            }
        }
        if (!alreadyThere) {
            anglers += 2;
            boater.addTourAttendance(3);
            coAngler.addTourAttendance(3);
        }
        fishTotal += boaterFish + coFish;
        if (boaterFish == 5)
            boaterLimits++;
        if (boaterFish == 0)
            boaterZeros++;
        if (coFish == 5)
            coLimits++;
        if (coFish == 0)
            coZeros++;
        weightTotal += boaterWeight + coWeight;
        double boaterWeightAfterPenalties = boaterWeight - (boaterFish - boaterFishAlive) * DEAD_FISH_PENALTY;
        double coWeightAfterPenalties = coWeight - (coFish - coFishAlive) * DEAD_FISH_PENALTY;
        if (boaterWeightAfterPenalties > bigBagWeight) {
            bigBagAngler = boater;
            bigBagFish = boaterFish;
            bigBagWeight = boaterWeightAfterPenalties;
        }
        if (coWeightAfterPenalties > bigBagWeight) {
            bigBagAngler = coAngler;
            bigBagFish = coFish;
            bigBagWeight = coWeightAfterPenalties;
        }
        if (boaterBigBass > bigBassWeight) {
            bigBassAngler = boater;
            bigBassWeight = boaterBigBass;
        }
        if (coBigBass > bigBassWeight) {
            bigBassAngler = coAngler;
            bigBassWeight = coBigBass;
        }
        teamWeights.add(new TeamWeight(day, boater, boaterFish, boaterFishAlive, boaterWeight, boaterBigBass,
                coAngler, coFish, coFishAlive, coWeight, coBigBass));
    }

    /**
     * <code>weighIn</code> method for 2-day events, and for 1-person teams
     * @param day               an <code>int</code>: 1 or 2
     * @param boaterNickname    the <code>Angler</code>'s key value in <code>Angler.memberMap</code>
     * @param boaterFish        an <code>int</code> number of fish weighed
     * @param boaterFishAlive   an <code>int</code> number of fish weighed alive
     * @param boaterWeight      a <code>double</code> bag weight
     * @param boaterBigBass     a <code>double</code> big bass weight
     */
    public void weighIn(int day, String boaterNickname, int boaterFish, int boaterFishAlive, double boaterWeight, double boaterBigBass) {
        Angler boater = Angler.getMemberMap().get(boaterNickname);
        boolean alreadyThere = false;
        for (TeamWeight entry : teamWeights) {
            if (entry.getBoater().equals(boater)) {
                alreadyThere = true;
                break;
            }
        }
        if (!alreadyThere) {
            anglers += 1;
            boater.addTourAttendance(3);
        }
        fishTotal += boaterFish;
        if (boaterFish == 5)
            boaterLimits++;
        if (boaterFish == 0)
            boaterZeros++;
        weightTotal += boaterWeight;
        double boaterWeightAfterPenalties = boaterWeight - (boaterFish - boaterFishAlive) * DEAD_FISH_PENALTY;
        if (boaterWeightAfterPenalties > bigBagWeight) {
            bigBagAngler = boater;
            bigBagFish = boaterFish;
            bigBagWeight = boaterWeightAfterPenalties;
        }
        if (boaterBigBass > bigBassWeight) {
            bigBassAngler = boater;
            bigBassWeight = boaterBigBass;
        }
        teamWeights.add(new TeamWeight(day, boater, boaterFish, boaterFishAlive, boaterWeight, boaterBigBass,
                null, 0, 0, 0, 0));
    }

    public static ArrayList<Tournament> getTournaments() { return tournaments; }
    public boolean getIsTwoDay() { return isTwoDay; }
    public String getLake() { return lake; }
    public String getMonth() { return month; }

    /**
     * @return  the <code>Angler</code> who weighed the biggest bass
     *          of the <code>Tournament</code>
     */
    public Angler getBigBagAngler() { return bigBagAngler; }

    /**
     * @return  the <code>Angler</code> who weighed the biggest bag
     *          of the <code>Tournament</code>
     */
    public Angler getBigBassAngler() { return bigBassAngler; }

    /**
     * sorts all of the <code>Tournament</code>'s <code>TeamWeight</code>s
     * and assigns places
     * @return  the ArrayList of <code>TeamWeight</code>s
     */
    public ArrayList<TeamWeight> getTeamWeights() {
        teamWeights.sort(Comparator.comparing(TeamWeight::getTeamWeight).reversed());
        teamWeights.get(0).setPlace(1);
        int j = 1;
        for (int i = 2; i <= teamWeights.size(); i++) {
            if (teamWeights.get(i-2).getTeamWeight() != teamWeights.get(i-1).getTeamWeight())
                j = i;
            teamWeights.get(i-1).setPlace(j);
        }
        return teamWeights;
    }

    /**
     * sorts all of a 2-day <code>Tournament</code>'s <code>TwoDayTeamWeight</code>s
     * and assigns places
     * @return  the ArrayList of <code>TwoDayTeamWeight</code>s
     */
    public ArrayList<TwoDayTeamWeight> getTwoDayTeamWeights() {
        ArrayList<TeamWeight> dailyWeighIns = teamWeights;
        ArrayList<TwoDayTeamWeight> lst = new ArrayList<>();
        for (TeamWeight day1 : dailyWeighIns) {
            if (day1.getDay() == 1) {
                TwoDayTeamWeight newEntry = new TwoDayTeamWeight(
                        day1.getBoater(), day1.getBoaterFish(),
                        day1.getBoaterFishAlive(), day1.getBoaterWeight(),
                        day1.getBoaterBigBass(), 0, 0, 0,
                        "", day1.getCoAngler(), day1.getCoAnglerFish(),
                        day1.getCoAnglerFishAlive(), day1.getCoAnglerWeight(),
                        day1.getCoAnglerBigBass(), 0,0,0,"");
                newEntry.setBoaterFishedDay1(true);
                if (day1.getCoAngler() != null)
                    newEntry.setCoAnglerFishedDay1(true);
                lst.add(newEntry);
            }
        }
        for (TeamWeight day2 : dailyWeighIns) {
            if(day2.getDay() == 2) {
                TwoDayTeamWeight myDay1 = null;
                for (TwoDayTeamWeight entry : lst){
                    if (day2.getBoater().equals(entry.getBoater())){
                        myDay1 = entry;
                        break;
                    }
                }
                if (myDay1 != null) {
                    myDay1.setBoaterFishedDay2(true);
                    myDay1.setBoaterDay2Fish(day2.getBoaterFish());
                    myDay1.setBoaterDay2FishAlive(day2.getBoaterFishAlive());
                    myDay1.setBoaterDay2Weight(day2.getBoaterWeight());
                    myDay1.setBoaterDay2BigBass(day2.getBoaterBigBass());
                    myDay1.setCoAnglerFishedDay2(true);
                    myDay1.setCoAnglerDay2Fish(day2.getCoAnglerFish());
                    myDay1.setCoAnglerDay2FishAlive(day2.getCoAnglerFishAlive());
                    myDay1.setCoAnglerDay2Weight(day2.getCoAnglerWeight());
                    myDay1.setCoAnglerDay2BigBass(day2.getCoAnglerBigBass());
                    myDay1.setTotalCombinedWeight(myDay1.getTotalCombinedWeight()+day2.getTeamWeight());
                }
                else {
                    TwoDayTeamWeight newEntry = new TwoDayTeamWeight(
                            day2.getBoater(), 0,0,0,"",
                            day2.getBoaterFish(), day2.getBoaterFishAlive(),
                            day2.getBoaterWeight(), day2.getBoaterBigBass(),
                            day2.getCoAngler(), 0,0,0,"",
                            day2.getCoAnglerFish(), day2.getCoAnglerFishAlive(),
                            day2.getCoAnglerWeight(), day2.getCoAnglerBigBass());
                    newEntry.setBoaterFishedDay2(true);
                    if (day2.getCoAngler() != null)
                        newEntry.setCoAnglerFishedDay2(true);
                    lst.add(newEntry);
                }
            }
        }
        lst.sort(Comparator.comparing(TwoDayTeamWeight::getTotalCombinedWeight).reversed());
        lst.get(0).setPlace(1);
        int j = 1;
        for (int i = 2; i <= lst.size(); i++) {
            if (lst.get(i-2).getTotalCombinedWeight() != lst.get(i-1).getTotalCombinedWeight())
                j = i;
            lst.get(i-1).setPlace(j);
        }
        return lst;
    }

    /**
     * "BBOY" stands for "Big Bass of the Year", a club award
     * @return  a <code>String</code> containing the big bass of the year,
     *          together with the name of the <code>Angler</code>, and the
     *          month and lake of the catch
     */
    public static String getBBOY() {
        double bBOY = 0;
        int bigBassIndex = -1;
        for (int i = 0; i < tournaments.size(); i++) {
            if (tournaments.get(i).bigBassWeight > bBOY) {
                bBOY = tournaments.get(i).bigBassWeight;
                bigBassIndex = i;
            }
        }
        return tournaments.get(bigBassIndex).bigBassWeight + " ("
                + tournaments.get(bigBassIndex).bigBassAngler.getName() + ", "
                + tournaments.get(bigBassIndex).month + ", "
                + tournaments.get(bigBassIndex).lake + ")";
    }

    /**
     * method to get the Big Bag of the Year, a club award
     * @return  a <code>String</code> containing the big bag of the year,
     *          together with the name of the <code>Angler</code>, and the
     *          month and lake of the catch
     */
    public static String getBigBag() {
        double bigBag = 0;
        int bigBagIndex = -1;
        for (int i = 0; i < tournaments.size(); i++) {
            if (tournaments.get(i).bigBagWeight > bigBag) {
                bigBag = tournaments.get(i).bigBagWeight;
                bigBagIndex = i;
            }
        }
        return tournaments.get(bigBagIndex).bigBagWeight + " ("
                + tournaments.get(bigBagIndex).bigBagAngler.getName() + ", "
                + tournaments.get(bigBagIndex).month + ", "
                + tournaments.get(bigBagIndex).lake + ")";
    }

    /**
     * each 1-day <code>Tournament</code> awards an <code>Angler</code>
     * 2 participation points; 2-days award 3 points
     * @return  the total (<code>int</code>) number of points possible
     *          during the season
     */
    public static int getTournamentPointsPossible() {
        int pointsPossible = 0;
        for (Tournament tournament: tournaments) {
            if (tournament.isTwoDay)
                pointsPossible += 3;
            else
                pointsPossible += 2;
        }
        return pointsPossible;
    }

    /**
     * @return an ArrayList of <code>Angler</code>s who participated in
     * a majority of the <code>Tournament</code>s in a season
     */
    public static ArrayList<Angler> inMajorityOfTournaments() {
        ArrayList<Angler> sticks = new ArrayList<>();
        int pointsPossible = getTournamentPointsPossible();
        for (Angler angler : Angler.getMembers()) {
            if (angler.getTourAttendance() >= 0.5 * pointsPossible) {
                sticks.add(angler);
            }
        }
        return sticks;
    }

    /**
     * the Consistency Award recognizes anglers for the number of fish per day
     * they weighed over the course of a season
     * @param places    an <code>int</code> number of places to display for
     *                  consistency rankings
     * @return          a <code>String</code> with the top <code>places</code>
     *                  of anglers in the consistency rankings
     */
    public static String getConsistencyAward(int places) {
        ArrayList<Angler> sticks = inMajorityOfTournaments();
        sticks.sort(Comparator.comparing(Angler::getFishPerDay).reversed());
        String top = "Consistency award (min. 50% participation)\n";
        top += String.format("%-6s%-24s%-13s", "Rank", "Angler", "Fish per day") + "\n";
        top += String.format("%3s%3s%-24s%8.2f",
                "1", "", sticks.get(0).getName(), sticks.get(0).getFishPerDay()) + "\n";
        int j = 1;
        for (int i = 1; i < places; i++) {
            if (sticks.get(i).getFishPerDay() != sticks.get(i-1).getFishPerDay())
                j = i;
            top += String.format("%3d%3s%-24s%8.2f",
                    j + 1, "",
                    sticks.get(i).getName(),
                    sticks.get(i).getFishPerDay()) + "\n";
        }
        return top;
    }


    public static String getHTMLConsistencyAward(int places) {
        ArrayList<Angler> sticks = inMajorityOfTournaments();
        sticks.sort(Comparator.comparing(Angler::getFishPerDay).reversed());
        String top = "<table>" +
                "<tr><td style=\"text-align: center; padding-top: 10px;\"colspan=\"3\"><b>Consistency award</b> (min. 50% participation)</td></tr>";
        top += "<tr><th style=\"text-align: left\">Rank</th>"
                +"<th style=\"text-align: left\">Angler</th>"
                +"<th style=\"text-align: right\">Fish per day</th></tr>";
        top += "<tr>"
                + "<td style=\"text-align: center\">1</td>"
                + "<td>" + sticks.get(0).getName() + "</td>"
                + "<td style=\"text-align: right\">" + String.format("%.2f", sticks.get(0).getFishPerDay())+ "</td>"
                + "</tr>";
        int j = 1;
        for (int i = 1; i < places; i++) {
            if (sticks.get(i).getFishPerDay() != sticks.get(i-1).getFishPerDay())
                j = i;
            top += "<tr>"
                    + "<td style=\"text-align: center\">" + (j+1) + "</td>"
                    + "<td>" + sticks.get(i).getName() + "</td>"
                    + "<td style=\"text-align: right\">" + String.format("%.2f", sticks.get(i).getFishPerDay())+ "</td>"
                    + "</tr>";
        }
        top += "</table>";
        return top;
    }

    /**
     * the Quality Award recognizes anglers for their weight per day
     * over the course of a season
     * @param places    an <code>int</code> number of places to display for
     *                  consistency rankings
     * @return          a <code>String</code> with the top <code>places</code>
     *                  of anglers in the quality rankings
     */
    public static String getQualityAward(int places) {
        ArrayList<Angler> sticks = inMajorityOfTournaments();
        sticks.sort(Comparator.comparing(Angler::getWeightPerDay).reversed());
        String top = "Quality award (min. 50% participation)\n";
        top += String.format("%-6s%-18s%-13s", "Rank", "Angler", "Weight per day") + "\n";
        top += String.format("%3s%3s%-18s%9.2f",
                "1", "", sticks.get(0).getName(), sticks.get(0).getWeightPerDay()) + "\n";
        int j = 1;
        for (int i = 1; i < places; i++) {
            if (sticks.get(i).getWeightPerDay() != sticks.get(i-1).getWeightPerDay())
                j = i;
            top += String.format("%3d%3s%-18s%9.2f",
                    j + 1, "",
                    sticks.get(i).getName(),
                    sticks.get(i).getWeightPerDay()) + "\n";
        }
        return top;
    }

    public static String getHTMLQualityAward(int places) {
        ArrayList<Angler> sticks = inMajorityOfTournaments();
        sticks.sort(Comparator.comparing(Angler::getWeightPerDay).reversed());
        String top = "<table>" +
                "<tr><td style=\"text-align: center; padding-top: 10px;\" colspan=\"3\"><b>Quality award</b> (min. 50% participation)</td></tr>";
        top += "<tr><th style=\"text-align: left\">Rank</th>"
                +"<th style=\"text-align: left; padding-left: 5px;\">Angler</th>"
                +"<th style=\"text-align: right\">Weight per day</th></tr>";
        top += "<tr>"
                + "<td style=\"text-align: center\">1</td>"
                + "<td  style=\"text-align: left; padding-left: 5px;\">" + sticks.get(0).getName() + "</td>"
                + "<td style=\"text-align: right\">" + String.format("%.2f", sticks.get(0).getWeightPerDay())+ "</td>"
                + "</tr>";
        int j = 1;
        for (int i = 1; i < places; i++) {
            if (sticks.get(i).getWeightPerDay() != sticks.get(i-1).getWeightPerDay())
                j = i;

            top += "<tr>"
                    + "<td style=\"text-align: center\">" + (j+1) + "</td>"
                    + "<td  style=\"text-align: left; padding-left: 5px;\">" + sticks.get(i).getName() + "</td>"
                    + "<td style=\"text-align: right\">" + String.format("%.2f", sticks.get(i).getWeightPerDay())+ "</td>"
                    + "</tr>";
        }
        top += "</table>";
        return top;
    }

    public static String getMostBigBags(int places) {
        ArrayList<Angler> sticks = Angler.getMembers();
        for (Angler stick: sticks)
            stick.setBigBags();
        sticks.sort(Comparator.comparing(Angler::getBigBags).reversed());
        String top = String.format("%-10s%-15s", "", "Most big bags")+"\n";
        top += String.format("%-6s%-19s%-13s", "Rank", "Angler", "Big bags")+"\n";
        top += String.format("%3s%3s%-19s%5d%5s%-70s",
                "1", "",
                sticks.get(0).getName(),
                sticks.get(0).getBigBags(), "",
                sticks.get(0).getBigBagLakes()) + "\n";
        int i = 1;
        int j = 1;
        if (sticks.get(i).getBigBags() == sticks.get(i-1).getBigBags()){
            top += String.format("%3s%3s%-19s%3d%3s%-70s",
                    j, "",
                    sticks.get(i).getName(),
                    sticks.get(i).getBigBags(), "",
                    sticks.get(i).getBigBagLakes()) + "\n";
            i++;
        }
        while (j < places) {
            if (sticks.get(i).getBigBags() != sticks.get(i-1).getBigBags())
                j = i;
            if (j >= places || sticks.get(i).getBigBags() == 0)
                break;
            top += String.format("%3d%3s%-19s%5d%5s%-70s",
                    j + 1, "",
                    sticks.get(i).getName(),
                    sticks.get(i).getBigBags(), "",
                    sticks.get(i).getBigBagLakes()) + "\n";
            i++;
        }
        return top;
    }

    public static String getMostWins(int places) {
        ArrayList<Angler> sticks = Angler.getMembers();
        for (Angler stick : sticks) {
            stick.setWins();
        }
        sticks.sort(Comparator.comparing(Angler::getWins).reversed());
        String top = String.format("%-10s%-10s","","Most wins")+"\n";
        top += String.format("%-6s%-19s%-4s", "Rank", "Angler", "Wins")+"\n";
        top += String.format("%3s%3s%-19s%3d%3s%-70s",
                "1", "",
                sticks.get(0).getName(),
                sticks.get(0).getWins(), "",
                sticks.get(0).getWinLakes()) + "\n";
        int i = 1; //index
        int j = 1; //place
        if (sticks.get(i).getWins() == sticks.get(i-1).getWins()){
            top += String.format("%3s%3s%-19s%3d%3s%-70s",
                    j, "",
                    sticks.get(i).getName(),
                    sticks.get(i).getWins(), "",
                    sticks.get(i).getWinLakes()) + "\n";
            i++;
        }
        while (j < places) {
            //int place = j;
            if (sticks.get(i).getWins() != sticks.get(i-1).getWins()) {
                j = i;
                //place = j+1;
            }
            if (j >= places || sticks.get(i).getWins() == 0)
                break;
            top += String.format("%3s%3s%-19s%3d%3s%-70s",
                    j + 1, "",
                    sticks.get(i).getName(),
                    sticks.get(i).getWins(), "",
                    sticks.get(i).getWinLakes()) + "\n";
            i++;
        }
        return top;
    }

    public static String getHTMLMostBigBagsAndWins(int places) {
        ArrayList<Angler> sticks = Angler.getMembers();
        for (Angler stick: sticks)
            stick.setBigBags();
        sticks.sort(Comparator.comparing(Angler::getBigBags).reversed());
        String top = "<center><table>" +
                "<tr><td style=\"text-align: center; padding-top: 10px; padding-bottom: 0px;\" colspan=\"4\"><b>Most big bags</b></td></tr>";
        top += "<tr><th style=\"text-align: left\">Rank</th>"
                +"<th style=\"text-align: left; padding-left: 5px;\">Angler</th>"
                +"<th>Big bags</th></tr>";
        top += "<tr>"
                + "<td style=\"text-align: center\">1</td>"
                + "<td style=\"padding-left: 5px;\">" + sticks.get(0).getName() + "</td>"
                + "<td style=\"text-align: center\">" + sticks.get(0).getBigBags() + "</td>"
                + "<td>" + sticks.get(0).getBigBagLakes() + "</td>"
                + "</tr>";
        int i = 1;
        int j = 1;
        if (sticks.get(i).getBigBags() == sticks.get(i-1).getBigBags()){
            top += "<tr>"
                    + "<td style=\"text-align: center\">" + j + "</td>"
                    + "<td style=\"padding-left: 5px;\">" + sticks.get(i).getName() + "</td>"
                    + "<td style=\"text-align: center\">" + sticks.get(i).getBigBags() + "</td>"
                    + "<td>" + sticks.get(i).getBigBagLakes() + "</td>"
                    + "</tr>";
            i++;
        }
        while (j<places) {
            if (sticks.get(i).getBigBags() != sticks.get(i-1).getBigBags())
                j = i;
            if (j >= places || sticks.get(i).getBigBags() == 0)
                break;
            top += "<tr>"
                    + "<td style=\"text-align: center\">" + (j+1) + "</td>"
                    + "<td style=\"padding-left: 5px;\">" + sticks.get(i).getName() + "</td>"
                    + "<td style=\"text-align: center\">" + sticks.get(i).getBigBags() + "</td>"
                    + "<td>" + sticks.get(i).getBigBagLakes() + "</td>"
                    + "</tr>";
            i++;
        }

        for (Angler stick : sticks)
            stick.setWins();
        sticks.sort(Comparator.comparing(Angler::getName));
        sticks.sort(Comparator.comparing(Angler::getWins).reversed());
        top += "<tr><td style=\"text-align: center; padding-top: 15px; padding-bottom: 0px;\" colspan=\"4\"><b>Most wins</b></td></tr>";
        top += "<tr><th style=\"text-align: left\">Rank</th>"
                +"<th style=\"text-align: left; padding-left: 5px;\">Angler</th>"
                +"<th>Wins</th></tr>";
        top += "<tr>"
                + "<td style=\"text-align: center\">1</td>"
                + "<td style=\"padding-left: 5px;\">" + sticks.get(0).getName() + "</td>"
                + "<td style=\"text-align: center\">" + sticks.get(0).getWins() + "</td>"
                + "<td>" + sticks.get(0).getWinLakes() + "</td>"
                + "</tr>";
        int a = 1; //index
        int b = 1; //place
        if (sticks.get(a).getWins() == sticks.get(a-1).getWins()){
            top += "<tr>"
                    + "<td style=\"text-align: center\">" + b + "</td>"
                    + "<td style=\"padding-left: 5px;\">" + sticks.get(a).getName() + "</td>"
                    + "<td style=\"text-align: center\">" + sticks.get(a).getWins() + "</td>"
                    + "<td>" + sticks.get(a).getWinLakes() + "</td>"
                    + "</tr>";
            a++;
        }
        while (b < places) {
            //int place = b;
            if (sticks.get(a).getWins() != sticks.get(a-1).getWins()) {
                b = a;
                //place = b+1;
            }
            if (b >= places || sticks.get(a).getWins() == 0)
                break;
            top += "<tr>"
                    + "<td style=\"text-align: center\">" + (b+1) + "</td>"
                    + "<td style=\"padding-left: 5px;\">" + sticks.get(a).getName() + "</td>"
                    + "<td style=\"text-align: center\">" + sticks.get(a).getWins() + "</td>"
                    + "<td>" + sticks.get(a).getWinLakes() + "</td>"
                    + "</tr>";
            a++;
        }
        top += "</table></center>";
        return top;
    }

    public static String getAwards() {
        return "Big bass of the year: " + getBBOY() + "\n"
        + "Big bag of the year: " + getBigBag() + "\n\n"
        + getConsistencyAward(3) + "\n"
        + getQualityAward(3) + "\n"
        + getMostBigBags(3) + "\n"
        + getMostWins(3);
    }

    public static String getHTMLAwards() {
        String awards = "<center><table><tr>" +
                "<td><b>Big bass of the year:</b></td>" +
                "<td>" + getBBOY() + "</td></tr>";
        awards += "<tr><td><b>Big bag of the year:</b></td>" +
                "<td>" + getBigBag() + "</td></tr>" +
                "</table></center>";
        awards += "<center><table style=\"width:45em\"><tr><td>"
                + getHTMLConsistencyAward(3)
                + "</td><td style=\"text-align: right\">"
                + getHTMLQualityAward(3)
                + "</td></tr></table></center>";
        awards += getHTMLMostBigBagsAndWins(3);
        return awards;
    }

    public String results() {
        String standings = "";
        if (!isTwoDay) {
            standings += String.format("%-6s%-19s%6s%7s%8s%4s%-19s%6s%7s%8s%8s",
                    "Place", "Boater", "Fish", "Big", "Weight", "",
                    "Co-angler", "Fish", "Big", "Weight", "Total")
                    + "\n";
            ArrayList<TeamWeight> finishes = new ArrayList<>(getTeamWeights());
            for (TeamWeight teamWeight : finishes)
                standings += teamWeight + "\n";
        }
        else { // two-day standings
            standings += String.format("%-6s%-18s%-40s%-19s%-40s", "", "",
                    "|        Day 1        |        Day 2        |", "",
                    "|        Day 1        |        Day 2        |") + "\n";
            standings += String.format("%-6s%-18s%-22s%-23s%1s%-18s%-22s%-23s%6s",
                    "Place", "Boater", "| Fish   Big   Weight ",
                    "| Fish   Big   Weight |", "",
                    "Co-angler", "| Fish   Big   Weight",
                    "| Fish   Big   Weight |", "Total")
                    + "\n";
            ArrayList<TwoDayTeamWeight> finishes = new ArrayList<>(getTwoDayTeamWeights());
            for (TwoDayTeamWeight teamWeight : finishes)
                standings += teamWeight + "\n";
        }
        return standings;
    }

    public String HTMLResults() {
        String standings = "";
        if (!isTwoDay) {
            standings += "<center><table style=\"width:50em\"><tr>"
                            + "<th style=\"text-align: left\">Place</th>"
                            + "<th style=\"text-align: left\">Boater</th>"
                            + "<th>Fish</th>"
                            + "<th style=\"text-align: right\">Big</th>"
                            + "<th style=\"text-align: right\">Weight</th><th style=\"width:1em\"></th>"
                            + "<th style=\"text-align: left\">Co-angler</th>"
                            + "<th>Fish</th>"
                            + "<th style=\"text-align: right\">Big</th>"
                            + "<th style=\"text-align: right\">Weight</th>"
                            + "<th style=\"text-align: right\">Total</th>"
                        + "</tr>";
            ArrayList<TeamWeight> finishes = new ArrayList<>(getTeamWeights());
            for (TeamWeight teamWeight : finishes)
                standings += teamWeight.toHTMLString();
        }
        else { // two-day standings
            standings += "<center><table style=\"width:62em\"><tr>"
                        + "<th colspan=\"2\"></th>"
                        + "<th colspan=\"3\" style=\"border-left: 1px dashed gray;\">Day 1</th>"
                            // style="border-collapse: collapse;" isn't working
                        + "<th colspan=\"3\" style=\"border-left: 1px dashed gray;\">Day 2</th>"
                        + "<th style=\"border-left: 1px dashed gray;\"></th>"
                        + "<th colspan=\"3\" style=\"border-left: 1px dashed gray;\">Day 1</th>"
                        + "<th colspan=\"3\" style=\"border-left: 1px dashed gray;\">Day 2</th>"
                        + "<th style=\"border-left: 1px dashed gray;\"></th>"
                    + "</tr>";
            standings += "<tr>"
                            + "<th style=\"text-align: left\">Place</th>"
                            + "<th style=\"text-align: left\">Boater</th>"
                            + "<th style=\"border-left: 1px dashed gray;\">Fish</th>"
                            + "<th style=\"text-align: right\">Big</th>"
                            + "<th style=\"text-align: right\">Weight</th>"
                            + "<th style=\"border-left: 1px dashed gray;\">Fish</th>"
                            + "<th style=\"text-align: right\">Big</th>"
                            + "<th style=\"text-align: right\">Weight</th>" //<th style="width:1em"></th>
                            + "<th style=\"text-align: left; border-left: 1px dashed gray; padding-left: 5px;\">Co-angler</th>"
                            + "<th style=\"border-left: 1px dashed gray;\">Fish</th>"
                            + "<th style=\"text-align: right\">Big</th>"
                            + "<th style=\"text-align: right\">Weight</th>"
                            + "<th style=\"border-left: 1px dashed gray;\">Fish</th>"
                            + "<th style=\"text-align: right\">Big</th>"
                            + "<th style=\"text-align: right\">Weight</th>"
                            + "<th style=\"text-align: right; border-left: 1px dashed gray;\">Total</th>"
                        + "</tr>";
            ArrayList<TwoDayTeamWeight> finishes = new ArrayList<>(getTwoDayTeamWeights());
            for (TwoDayTeamWeight teamWeight : finishes)
                standings += teamWeight.toHTMLString();
        }
        standings += "</table></center>";
        return standings;
    }

    public String toString() {
        String title = month + ": " + lake;
        int half = 50;
        if (isTwoDay)
            half = 68;
        int offset = half - title.length()/2;
        String bBWeight = "none   ";
        String bBAngler = "";
        int spac2 = 4;
        String colSpac2 = "";
        if (bigBassWeight > 0) {
            bBWeight = String.format("%5.2f", bigBassWeight) + " lbs";
            bBAngler = " (" + bigBassAngler.getName() + ")";
            spac2 = 7 + bigBassAngler.getName().length();
            colSpac2 = "%-"+spac2+"s";
        }
        int spac1 = 7 + bigBagAngler.getName().length();
        String output = String.format("%-"+offset+"s%-50s", "", title) + "\n\n";
        if (!isTwoDay) {
            String gap = "%"+ 18 +"s";
            String gap2 = "%"+ (51-spac1-spac2) +"s";
            output += String.format("%-9s"+gap+"%11s"+gap+"%9s%2.0f%%"+gap+"%8s%2.0f%%",
                    "Teams: " + teamWeights.size(), "",
                    "Anglers: " + anglers, "",
                    " Limited: ", (double) 100 * (boaterLimits + coLimits) / anglers, "",
                    "Zeroed: ", (double) 100 * (boaterZeros + coZeros) / anglers) + "\n\n"
                    + String.format("%-13s%-4.1f%5s%6.2f%-"+spac1+"s"+gap2+"%-14s%5.2f%-4s",
                    "Average bag:", (double) (fishTotal) / anglers, "fish,", weightTotal / anglers, " lbs", "",
                    "Average bass: ", weightTotal / fishTotal, " lbs") + "\n"
                    + String.format("%-13s%-4d%5s%6.2f%-"+spac1+"s"+gap2+"%-14s%8s"+colSpac2,
                    "Big bag:", bigBagFish, "fish,", bigBagWeight, " lbs (" + bigBagAngler.getName() + ")", "",
                    "Big bass:", bBWeight, bBAngler) + "\n\n";
        }
        else { // two-day printing
            String gap = "%"+ (70-spac1-spac2)/3 +"s";
            int indivWeighIns = 0;
                for (TeamWeight teamWeight : teamWeights) {
                    if (teamWeight.getCoAngler() != null)
                        indivWeighIns += 2;
                    else
                        indivWeighIns += 1;
                }

            output += String.format("%-9s%2d"+gap+"%-9s%2.0f%%"+gap
                            +"%-23s%5.2f%-"+spac1+"s"+gap+"%-13s%5.2f%4s",
                    "Teams: ", getTwoDayTeamWeights().size(), "",
                    "Limited: ",
                    (double) 100 * (boaterLimits + coLimits) / indivWeighIns, "",
                    "Average bag: "
                      + String.format("%-3.1f", (double) (fishTotal) / indivWeighIns)
                      + " fish, ",
                    weightTotal / indivWeighIns, " lbs", "",
                    "Average bass:", weightTotal / fishTotal, " lbs") + "\n"

                    + String.format("%-9s%2d"+gap+"%-9s%2.0f%%"+gap
                            +"%-23s%5.2f%-"+spac1+"s"+gap+"%-13s%8s"+colSpac2,
                    "Anglers: ", anglers, "",
                    "Zeroed: ",
                    (double) 100 * (boaterZeros + coZeros) / indivWeighIns, "",
                    "Big bag:     " + bigBagFish + "   fish, ",
                    bigBagWeight, " lbs (" + bigBagAngler.getName() + ")", "",
                    "Big bass:", bBWeight, bBAngler) + "\n\n";
        }
        output += results() + "\n";
        return output;
    }

    public String toHTMLString() {
        String limited = String.format("%.0f%%",(double) 100 * (boaterLimits + coLimits) / anglers);
        String zeroed = String.format("%.0f%%",(double) 100 * (boaterZeros + coZeros) / anglers);
        String avNum = String.format("%.1f",(double) (fishTotal) / anglers) + " fish,";
        String avTot = String.format("%.2f", weightTotal / anglers) + " lbs";
        String avInd = String.format("%.2f", weightTotal / fishTotal) + " lbs";
        String bBWeight = "none";
        String bBAngler = "";
        if (bigBassWeight > 0) {
            bBWeight = String.format("%.2f", bigBassWeight) + " lbs";
            bBAngler = " (" + bigBassAngler.getName() + ")";
        }
        String p1 = "<p>Here are the results from our "
                + month + " tournament on " + lake
                + ":</p>";
        String t1, t2 = "";
        if (!isTwoDay) {
            t1 = "<center><table style=\"width:50em\">"
                    + "<tr>"
                    + "<td>Teams: " + teamWeights.size() + "</td>"
                    + "<td style=\"text-align: center\">Anglers: " + anglers + "</td>"
                    + "<td style=\"text-align: center\">Limited: " + limited + "</td>"
                    + "<td style=\"text-align: right\">Zeroed: " + zeroed + "</td>"
                    + "</tr>"
                    + "</table></center>";
            t2 = "<center><table style=\"width:50em\">"
                    + "<tr>"
                    + "<td>Average bag:</td>"
                    + "<td style=\"text-align: right\">" + avNum + "</td>"
                    + "<td style=\"text-align: right\">" + avTot + "</td>"
                    + "<td></td>"
                    + "<td style=\"width:3em\"></td>"
                    + "<td>Average bass:</td>"
                    + "<td style=\"text-align: right\">" + avInd + "</td>"
                    + "<td></td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td>Big bag:</td>"
                    + "<td style=\"text-align: right\">" + bigBagFish + " fish," + "</td>"
                    + "<td style=\"text-align: right\">" + bigBagWeight + " lbs" + "</td>"
                    + "<td>(" + bigBagAngler.getName() + ")</td>"
                    + "<td></td>"
                    + "<td>Big bass:</td>"
                    + "<td style=\"text-align: right\">" + bBWeight + "</td>"
                    + "<td style=\"text-align: right\">" + bBAngler + "</td>"
                    + "</tr>"
                    + "</table></center>";
        }
        else {
            int indivWeighIns = 0;
            for (TeamWeight teamWeight : teamWeights) {
                if (teamWeight.getCoAngler() != null)
                    indivWeighIns += 2;
                else
                    indivWeighIns += 1;
            }
            t1 = "<center><table style=\"width:62em\">"
                    + "<tr>"
                    + "<td>Teams:</td>"
                    + "<td style=\"text-align: right\">" + getTwoDayTeamWeights().size() + "</td>"
                    + "<td style=\"width:2em\"></td>"
                    + "<td>Limited:</td>"
                    + "<td style=\"text-align: right\">" + String.format("%.0f%%",(double) 100 * (boaterLimits + coLimits) / indivWeighIns) + "</td>"
                    + "<td style=\"width:2em\"></td>"
                    + "<td>Average bag:</td>"
                    + "<td style=\"text-align: right\">" + String.format("%.1f", (double) (fishTotal) / indivWeighIns) + " fish,</td>"
                    + "<td style=\"text-align: right\">" + String.format("%.2f", weightTotal / indivWeighIns) + " lbs</td>"
                    + "<td></td>"
                    + "<td style=\"width:1em\"></td>"
                    + "<td>Average bass:</td>"
                    + "<td style=\"text-align: right\">" + avInd + "</td>"
                    + "<td></td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td>Anglers:</td>"
                    + "<td style=\"text-align: right\">" + anglers + "</td>"
                    + "<td></td>"
                    + "<td>Zeroed:</td>"
                    + "<td style=\"text-align: right\">" + String.format("%.0f%%",(double) 100 * (boaterZeros + coZeros) / indivWeighIns) + "</td>"
                    + "<td></td>"
                    + "<td>Big bag:</td>"
                    + "<td style=\"text-align: right\">" + bigBagFish + " fish,</td>"
                    + "<td style=\"text-align: right\">" + String.format("%.2f", bigBagWeight) + " lbs</td>"
                    + "<td>(" + bigBagAngler.getName() + ")</td>"
                    + "<td></td>"
                    + "<td>Big bass:</td>"
                    + "<td style=\"text-align: right\">" + bBWeight + "</td>"
                    + "<td style=\"text-align: right\">" + bBAngler +"</td>"
                    + "</tr>"
                    + "</table></center>";
        }
        String p2 = "<p>Congratulations, guys! "
                + "Here are our updated standings:</p>";
        String p4 = "<p>Check out our "
                + Email.WEBSITE_HTML_HYPERLINK
                + " for info about our next meeting and tournament, and find us on "
                + Email.FACEBOOK_HTML_HYPERLINK
                + " and "
                + Email.INSTAGRAM_HTML_HYPERLINK
                + ".</p>"
                + "<p>Tight lines!<br>Big Sandy Creek Bass Club</p>"
                + Email.getUnsubscribeHTML();
        return p1 + t1 + t2
                + HTMLResults()
                + p2 + Angler.getHTMLStandings()
                + Angler.getMissingContactsHTML()
                + p4;
    }
}