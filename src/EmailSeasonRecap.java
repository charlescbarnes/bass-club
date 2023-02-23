import java.util.ArrayList;

public class EmailSeasonRecap extends Email {

    /**
     * Class constructor specifying the intended <code>recipients</code>
     * @param recipients        an ArrayList of recipient email addresses
     * @param anglerNickname    the <code>Angler</code>'s key value in <code>memberMap</code>
     */
    public EmailSeasonRecap(ArrayList<String> recipients, String anglerNickname) {
        super(recipients,"", Angler.getSeasonRecapHTML(anglerNickname));
        setSubject("Your season recap! - Big Sandy Creek Bass Club");
    }

    /**
     * Class constructor
     * this sends the email to the <code>Angler</code> referenced by <code>anglerNickname</code>
     * @param anglerNickname    the <code>Angler</code>'s key value in <code>memberMap</code>
     */
    public EmailSeasonRecap(String anglerNickname) {
        super(new ArrayList<String>(){{
            add(Angler.getMemberMap().get(anglerNickname).getEmail());
        }},"", Angler.getSeasonRecapHTML(anglerNickname));
        setSubject("Your season recap! - Big Sandy Creek Bass Club");
    }

    public void send() {
        send(false);
    }

    /**
     * emails a season recap for every <code>Angler</code> with an email
     * address on file
     * @param toTestListServe   only <code>recipients</code> are
     *                          <code>Angler.getTestListServe()</code> if true;
     *                          otherwise, each <code>Angler</code> is emailed
     *                          their own season recap
     */
    public static void sendAllRecaps(boolean toTestListServe) {
        int totalEmails = Angler.getListServe().size();
        int currentEmail = 1;
        for (String anglerNickname : Angler.getMemberMap().keySet()) {
            Angler angler = Angler.getMemberMap().get(anglerNickname);
            if (!angler.getEmail().equals("")) {
                EmailSeasonRecap recapEmail;
                if (toTestListServe) {
                    recapEmail = new EmailSeasonRecap(Angler.getTestListServe(), anglerNickname);
                }
                else {
                    recapEmail = new EmailSeasonRecap(anglerNickname);
                }
                recapEmail.setSentMessage("");
                recapEmail.send();
                System.out.println("Season recap email " + currentEmail + "/" + totalEmails + " sent!");
                currentEmail++;
            }
        }
    }
}
