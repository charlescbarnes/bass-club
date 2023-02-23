import java.util.ArrayList;

public class EmailTournamentResults extends Email {
    /**
     * Class constructor
     * @param recipients    an ArrayList of recipient email addresses
     * @param tournament    an instance of the <code>Tournament</code> class
     */
    public EmailTournamentResults(ArrayList<String> recipients, Tournament tournament) {
        super(recipients, "", tournament.toHTMLString());
        setSubject(tournament.getMonth()
                + " (" + tournament.getLake() + ") "
                + "tournament results! - Big Sandy Creek Bass Club");
    }

    public void send() {
        setSentMessage("Tournament results email sent!");
        send(true);
    }
}
