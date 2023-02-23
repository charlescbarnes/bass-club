import java.io.IOException;

public class Season2021 {
    public static void main(String[] args) throws IOException {
        // grabs all members from Members20XX.txt and places them in the memberMap
        // this also sets officers and list serves
        Angler.setMemberMap();

        // January
        Meeting janMeeting = new Meeting(1,
                new String[]{"cho", "charity", "katie", "kendra",
                        "harry", "lavender"});

        Tournament jan = new Tournament(1, "Lady Bird");

        jan.weighIn("harry", 5, 5, 11.2, 0,
                "colin", 5, 5, 10.7, 0);
        jan.weighIn("justin", 0, 0, 0, 0,
                "katie", 0, 0, 0, 0);
        jan.weighIn("wilkie", 0, 0, 0, 0);
        jan.weighIn("zacharias", 2, 2, 5.63, 0,
                "bellatrix", 0, 0, 0, 0);
        jan.weighIn("cho", 0, 0, 0, 0,
                "charity", 1, 1, 4.94, 4.94);
        jan.weighIn("millicent", 1, 1, 2.38, 0,
                "hannah", 0, 0, 0, 0);
        jan.weighIn("cedric", 0, 0, 0, 0,
                "theodore", 1, 1, 1.19, 0);
        jan.weighIn("seamus", 0, 0, 0, 0,
                "kendra", 0, 0, 0, 0);
        jan.weighIn("marietta", 0, 0, 0, 0,
                "lee", 0, 0, 0, 0);

        //System.out.println(jan);

        // February
        Meeting febMeeting = new Meeting(2,
                new String[]{"bellatrix", "colin", "cho", "charity",
                        "demelza", "justin", "katie", "kendra", "harry", "nick",
                        "seamus", "zacharias"});

        Tournament feb = new Tournament(2, "Stillhouse");

        feb.weighIn("kendra", 4, 4, 6.7, 0,
                "charity", 1, 1, 1.74, 0);
        feb.weighIn("justin", 2, 2, 3.15, 0,
                "katie", 1, 1, 1.46, 0);
        feb.weighIn("seamus", 2, 2, 4.66, 0);
        feb.weighIn("zacharias", 0, 0, 0, 0,
                "bellatrix", 0, 0, 0, 0);
        feb.weighIn("harry", 1, 1, 1.65, 0,
                "colin", 3, 3, 7.04, 0);
        feb.weighIn("cho", 0, 0, 0, 0);

        //System.out.println(feb);

        // March
        Meeting marMeeting = new Meeting(3,
                new String[]{"colin", "cho", "charity", "justin",
                        "katie", "kendra", "harry", "marietta", "peter",
                        "parvati", "seamus"});

        Tournament mar = new Tournament(3, "Limestone");

        mar.weighIn("seamus", 5, 5, 17.86, 6.33,
                "vincent", 5, 5, 24.99, 8.01);
        mar.weighIn("zacharias", 5, 5, 13.2, 0,
                "bellatrix", 5, 5, 16.56, 8.08);
        mar.weighIn("justin", 5, 3, 11.97, 0,
                "katie", 3, 3, 9.43, 0);
        mar.weighIn("colin", 5, 5, 18.82, 0,
                "eloise", 1, 1, 1.89, 0);
        mar.weighIn("kendra", 3, 3, 8.89, 0,
                "theodore", 1, 1, 2.02, 0);
        mar.weighIn("wilkie", 2, 2, 8.73, 0,
                "viktor", 0, 0, 0, 0);
        mar.weighIn("harry", 2, 2, 5.66, 0,
                "charity", 0, 0, 0, 0);

        //System.out.println(mar);

        // April
        Meeting aprilMeeting = new Meeting(4,
                new String[]{"colin", "cho", "charity", "dolores",
                        "kendra", "nick", "theodore", "zacharias"});

        Tournament april = new Tournament(4, "Decker");

        april.weighIn("wilkie", 5, 5, 19.99, 0,
                "viktor", 5, 5, 17.57, 0);
        april.weighIn("kendra", 5, 5, 16.32, 0,
                "molly", 5, 5, 19.69, 0);
        april.weighIn("harry", 5, 5, 18.31, 0,
                "parvati", 5, 5, 12.2, 0);
        april.weighIn("cedric", 5, 5, 16.44, 7.19,
                "theodore", 5, 5, 13.56, 0);
        april.weighIn("colin", 5, 5, 16.81, 0,
                "eloise", 3, 3, 7.94, 0);
        april.weighIn("cho", 5, 5, 15.01, 0,
                "peter", 3, 3, 5.07, 0);
        april.weighIn("zacharias", 3, 3, 7.76, 0,
                "bellatrix", 3, 3, 8.82, 0);
        april.weighIn("seamus", 5, 5, 15.82, 0);

        //System.out.println(april);

        // May
        Meeting mayMeeting = new Meeting(5,
                new String[]{"colin", "cho", "charity", "kendra",
                        "harry", "aurora"});

        Tournament may = new Tournament(5, "LBJ");

        may.weighIn("wilkie", 2, 2, 4.89, 0,
                "viktor", 1, 1, .84, 0);
        may.weighIn("zacharias", 2, 2, 3.64, 0,
                "bellatrix", 2, 2, 3.53, 0);
        may.weighIn("kendra", 2, 2, 3.94, 0);
        may.weighIn("cho", 5, 5, 8.55, 0,
                "charity", 3, 3, 4.76, 0);
        may.weighIn("lavender", 2, 2, 12.36, 6.87,
                "albus", 1, 1, 2.68, 0);
        may.weighIn("seamus", 2, 2, 2.5, 0);
        may.weighIn("harry", 3, 3, 5.98, 0,
                "colin", 5, 5, 9.14, 0);
        may.weighIn("justin", 0, 0, 0, 0,
                "katie", 0, 0, 0, 0);

        //System.out.println(may);

        // June
        Meeting juneMeeting = new Meeting(6,
                new String[]{"bellatrix", "colin", "cho", "charity",
                        "justin", "katie", "kendra", "aurora", "zacharias"});

        Tournament june = new Tournament(6, "Belton");

        june.weighIn("zacharias", 2, 2, 4.3, 0,
                "bellatrix", 0, 0, 0, 0);
        june.weighIn("justin", 1, 1, 2.44, 0,
                "katie", 3, 3, 5.46, 0);
        june.weighIn("harry", 1, 1, 1.12, 0,
                "colin", 5, 5, 9.84, 3.66);
        june.weighIn("wilkie", 0, 0, 0, 0,
                "viktor", 1, 1, 1.85, 0);
        june.weighIn("kendra", 3, 3, 4.09, 0,
                "charity", 1, 1, 1.9, 0);
        june.weighIn("draco", 0, 0, 0, 0,
                "cormac", 0, 0, 0, 0);
        june.weighIn("cho", 4, 4, 6.44, 0,
                "aurora", 2, 2, 3.42, 0);

        //System.out.println(june);

        // July
        Meeting julyMeeting = new Meeting(7,
                new String[]{"colin", "cho", "justin", "katie",
                        "kendra", "harry", "zacharias"});

        Tournament july = new Tournament(7, "Buchanan");

        july.weighIn("harry", 5, 5, 10.89, 0,
                "charity", 2, 2, 4.38, 0);
        july.weighIn("justin", 4, 4, 13.01, 6.90,
                "katie", 1, 1, 1.31, 0);
        july.weighIn("colin", 5, 5, 13.94, 0,
                "eloise", 0, 0, 0, 0);
        july.weighIn("kendra", 4, 4, 5.25, 0,
                "theodore", 4, 4, 5.13, 0);
        july.weighIn("zacharias", 0, 0, 0, 0,
                "bellatrix", 2, 2, 5.94, 0);

        //System.out.println(july);

        // August
        Meeting augMeeting = new Meeting(8,
                new String[]{"cho", "james", "justin", "katie",
                        "kendra", "harry"});

        Tournament aug = new Tournament(8, "Decker");

        aug.weighIn("demelza", 5, 5, 12.58, 0,
                "rubeus", 3, 3, 10, 0);
        aug.weighIn("harry", 5, 5, 14.5, 0,
                "parvati", 3, 3, 6.82, 0);
        aug.weighIn("cho", 3, 3, 8.5, 0,
                "theodore", 5, 5, 9.89, 0);
        aug.weighIn("kendra", 4, 4, 12.63, 0,
                "charity", 2, 2, 4.82, 0);
        aug.weighIn("justin", 1, 1, 2.38, 0,
                "katie", 1, 1, 1, 0);
        aug.weighIn("zacharias", 1, 1, 1.63, 0,
                "peter", 0, 0, 0, 0);

        //System.out.println(aug);

        // September
        Meeting sepMeeting = new Meeting(9,
                new String[]{"bellatrix", "colin", "cho", "demelza",
                        "dean", "kendra", "harry", "seamus"});

        Tournament sep = new Tournament(9, "Inks");

        sep.weighIn("harry", 4, 4, 9.44, 4.33,
                "ron", 2, 2, 3.66, 0);
        sep.weighIn("zacharias", 3, 3, 4.95, 0,
                "bellatrix", 0, 0, 0, 0);
        sep.weighIn("justin", 1, 1, 2.35, 0,
                "katie", 1, 1, 3.01, 0);
        sep.weighIn("cho", 2, 2, 4.27, 0,
                "colin", 5, 5, 9.24, 0);
        sep.weighIn("kendra", 1, 1, 1.73, 0,
                "charity", 3, 3, 4.88, 0);
        sep.weighIn("seamus", 3, 2, 4.77, 0);
        sep.weighIn("rubeus", 2, 2, 2.72, 0,
                "dolores", 0, 0, 0, 0);

        //System.out.println(sep);

        // October
        Meeting octMeeting = new Meeting(10,
                new String[]{"colin", "cho", "dean", "gilderoy",
                        "justin", "katie", "kendra", "harry", "seamus"});

        Tournament oct = new Tournament(10, "Austin");

        oct.weighIn("zacharias", 2, 2, 3.06, 0,
                "bellatrix", 4, 4, 6.32, 0);
        oct.weighIn("harry", 5, 5, 10.46, 0,
                "charity", 2, 2, 2.93, 0);
        oct.weighIn("seamus", 5, 5, 8.15, 0,
                "dean", 3, 3, 3.67, 0);
        oct.weighIn("cho", 2, 2, 7.29, 5.67,
                "gilderoy", 1, 1, 2.35, 0);
        oct.weighIn("kendra", 3, 3, 3.66, 0,
                "colin", 3, 3, 3.95, 0);

        //System.out.println(oct);

        Angler.addExtraPoints("cho", 8); // 1 BFL, 5 Media, 2 NCR
        Angler.addExtraPoints("charity", 2); // 1 NCR, 1 TBTT
        Angler.addExtraPoints("demelza", 3); // Media
        Angler.addExtraPoints("dolores", 1); // Media
        Angler.addExtraPoints("james", 3); // 2 BFL, 1 Toyota
        Angler.addExtraPoints("justin", 3); // TBN
        Angler.addExtraPoints("katie", 3); // TBN
        Angler.addExtraPoints("kendra", 2); // NCR
        Angler.addExtraPoints("harry", 9); // 5 Media, 2 NCR, 1 TBTT, 1 new member
        Angler.addExtraPoints("lavender", 1); // Toyota
        Angler.addExtraPoints("nick", 2); // 1 new member, 1 Media
        Angler.addExtraPoints("rubeus", 3); // Media
        Angler.addExtraPoints("viktor", 2); // NCR
        Angler.addExtraPoints("wilkie", 3); // 2 NCR, 1 new member
        Angler.addExtraPoints("zacharias", 1); // new member

        // November
        Meeting novMeeting = new Meeting(11,
                new String[]{"kendra", "harry", "dean", "demelza",
                        "cho", "zacharias", "seamus", "colin"});

        Tournament nov = new Tournament(11, "O.H. Ivie", true);

        nov.weighIn(1, "kendra", 2, 2, 4.53, 0,
                "theodore", 3, 3, 6.16, 0);
        nov.weighIn(1, "cho", 4, 4, 10.63, 4.66,
                "colin", 3, 3, 5.46, 0);
        nov.weighIn(1, "dean", 3, 3, 6.35, 0,
                "bill", 0, 0, 0, 0);
        nov.weighIn(1, "seamus", 2, 2, 3.38, 0,
                "vincent", 2, 2, 5.78, 0);
        nov.weighIn(1, "demelza", 2, 2, 5.54, 0);
        nov.weighIn(1, "harry", 3, 3, 13.67, 11.69,
                "charity", 1, 1, 1.27, 0);
        // day 2
        nov.weighIn(2, "kendra", 0, 0, 0, 0,
                "theodore", 2, 2, 1.74, 0);
        nov.weighIn(2, "cho", 1, 1, 2, 0,
                "colin", 3, 3, 8.89, 5.54);
        nov.weighIn(2, "demelza", 0, 0, 0, 0);
        nov.weighIn(2, "dean", 2, 2, 2.09, 0,
                "bill", 0, 0, 0, 0);
        nov.weighIn(2, "seamus", 0, 0, 0, 0,
                "vincent", 0, 0, 0, 0);
        nov.weighIn(2, "harry", 3, 3, 6.25, 0,
                "charity", 2, 2, 2.84, 0);

        //System.out.println(nov);

        // December
        Meeting decMeeting = new Meeting(12,
                new String[]{"kendra", "justin", "katie", "colin", "dean",
                        "cho", "demelza", "nick", "rubeus", "seamus",
                        "lavender", "harry"});

        Tournament dec = new Tournament(12, "Travis");

        dec.weighIn("colin", 5, 4, 11.03, 0,
                "eloise", 2, 2, 3.17, 0);
        dec.weighIn("cho", 3, 3, 7.87, 3.72,
                "dean", 1, 1, 2.77, 0);
        dec.weighIn("wilkie", 5, 5, 10.91, 0,
                "viktor", 2, 2, 4.88, 0);
        dec.weighIn("zacharias", 4, 4, 5.91, 0,
                "bellatrix", 4, 4, 8.33, 3.63);
        dec.weighIn("seamus", 5, 5, 8.46, 0,
                "kendra", 3, 3, 3.66, 0);
        dec.weighIn("harry", 5, 5, 10.74, 0,
                "charity", 3, 3, 4.30, 0);

        //System.out.println(dec);


        //System.out.println(Angler.getStandings());
        //System.out.println(Tournament.getAwards());
        //System.out.println(Angler.getMissingContacts());
        //System.out.println(Angler.getListServe());
        //System.out.println(Angler.getSeasonRecap("colin"));


        // Emails meeting attendance
        // to: Angler.getTestListServe() or Angler.getOfficerListServe()
//        EmailMeetingAttendance meetingEmail = new EmailMeetingAttendance(Angler.getTestListServe(), decMeeting);
//        System.out.println(meetingEmail);
//        meetingEmail.send();

        // Emails tournament results
        // to: Angler.getTestListServe() or Angler.getListServe()
//        EmailTournamentResults resultsEmail = new EmailTournamentResults(Angler.getTestListServe(), dec);
//        System.out.println(resultsEmail);
//        resultsEmail.send();

        // Runs and saves Roster.pdf
        //new RosterPDF();

        // Season recaps
        // emails just one angler's recap to Angler.getTestListServe()
//        EmailSeasonRecap recapEmail = new EmailSeasonRecap(Angler.getTestListServe(), "viktor");
//        System.out.println(recapEmail);
//        recapEmail.send();
        // emails all recaps to either Angler.getTestListServe() or to each angler
        //EmailSeasonRecap.sendAllRecaps(true);
    }
}