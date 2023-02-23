public class TeamWeight {
    private int place = 0;
    private Angler boater;
    private int boaterFish;
    private int boaterFishAlive;
    private double boaterWeight;
    private String boaterBigBass;
    private Angler coAngler;
    private int coAnglerFish;
    private int coAnglerFishAlive;
    private double coAnglerWeight;
    private String coAnglerBigBass;
    private double teamWeight;
    private int day = 0;

    /**
     * Class constructor
     * @param boater            an <code>Angler</code>
     * @param boaterFish        an <code>int</code> number of fish weighed
     * @param boaterFishAlive   an <code>int</code> number of fish weighed alive
     * @param boaterWeight      a <code>double</code> bag weight
     * @param boaterBigBass     a <code>double</code> big bass weight
     * @param coAngler          an <code>Angler</code>
     * @param coFish            an <code>int</code> number of fish weighed
     * @param coFishAlive       an <code>int</code> number of fish weighed alive
     * @param coWeight          a <code>double</code> bag weight
     * @param coBigBass         a <code>double</code> big bass weight
     */
    public TeamWeight(Angler boater, int boaterFish, int boaterFishAlive, double boaterWeight, double boaterBigBass,
                      Angler coAngler, int coFish, int coFishAlive, double coWeight, double coBigBass) {
        this.boater = boater;
        this.boaterFish = boaterFish;
        this.boaterFishAlive = boaterFishAlive;
        this.boaterWeight += boaterWeight - (boaterFish - boaterFishAlive)*Tournament.DEAD_FISH_PENALTY;
        if (boaterBigBass == 0)
            this.boaterBigBass = "";
        else
            this.boaterBigBass = String.format("%.2f", boaterBigBass);
        this.coAngler = coAngler;
        coAnglerFish = coFish;
        coAnglerFishAlive = coFishAlive;
        coAnglerWeight += coWeight - (coAnglerFish - coAnglerFishAlive)*Tournament.DEAD_FISH_PENALTY;
        if (coBigBass == 0)
            coAnglerBigBass = "";
        else
            coAnglerBigBass = String.format("%.2f", coBigBass);
        teamWeight += boaterWeight + coAnglerWeight;

        boater.addFish(boaterFish);
        boater.addWeight(boaterWeight);
        if (coAngler != null) {
            coAngler.addFish(coAnglerFish);
            coAngler.addWeight(coAnglerWeight);
        }
    }

    /**
     * Class constructor specifying a <code>dayNum</code>
     * @param dayNum            an <code>int</code> (specifically 1 or 2)
     *                          used for two-day tournaments
     * @param boater            an <code>Angler</code>
     * @param boaterFish        an <code>int</code> number of fish weighed
     * @param boaterFishAlive   an <code>int</code> number of fish weighed alive
     * @param boaterWeight      a <code>double</code> bag weight
     * @param boaterBigBass     a <code>double</code> big bass weight
     * @param coAngler          an <code>Angler</code>
     * @param coFish            an <code>int</code> number of fish weighed
     * @param coFishAlive       an <code>int</code> number of fish weighed alive
     * @param coWeight          a <code>double</code> bag weight
     * @param coBigBass         a <code>double</code> big bass weight
     */
    public TeamWeight(int dayNum, Angler boater, int boaterFish, int boaterFishAlive, double boaterWeight, double boaterBigBass,
                      Angler coAngler, int coFish, int coFishAlive, double coWeight, double coBigBass) {
        day = dayNum;
        this.boater = boater;
        this.boaterFish = boaterFish;
        this.boaterFishAlive = boaterFishAlive;
        this.boaterWeight += boaterWeight - (boaterFish - boaterFishAlive)*Tournament.DEAD_FISH_PENALTY;
        if (boaterBigBass == 0)
            this.boaterBigBass = "";
        else
            this.boaterBigBass = String.format("%.2f", boaterBigBass);
        this.coAngler = coAngler;
        coAnglerFish = coFish;
        coAnglerFishAlive = coFishAlive;
        coAnglerWeight += coWeight - (coAnglerFish - coAnglerFishAlive)*Tournament.DEAD_FISH_PENALTY;
        if (coBigBass == 0)
            coAnglerBigBass = "";
        else
            coAnglerBigBass = String.format("%.2f", coBigBass);
        teamWeight += boaterWeight + coAnglerWeight;

        boater.addFish(boaterFish);
        boater.addWeight(boaterWeight);
        if (coAngler != null) {
            coAngler.addFish(coAnglerFish);
            coAngler.addWeight(coAnglerWeight);
        }
    }

    public void setPlace(int j) { place = j; }
    public int getPlace() { return place; }
    public double getTeamWeight() { return teamWeight; }
    public Angler getBoater() { return boater; }
    public int getBoaterFish() { return boaterFish; }
    public int getBoaterFishAlive() { return boaterFishAlive; }
    public String getBoaterBigBass() { return boaterBigBass; }
    public double getBoaterWeight() { return boaterWeight; }
    public Angler getCoAngler() { return coAngler; }
    public int getCoAnglerFish() { return coAnglerFish; }
    public int getCoAnglerFishAlive() { return coAnglerFishAlive; }
    public String getCoAnglerBigBass() { return coAnglerBigBass; }
    public double getCoAnglerWeight() { return coAnglerWeight; }
    public int getDay() { return day; }

    /**
     * @return a string consisting of HTML code
     */
    public String toHTMLString() {
        String partner = "";
        String partnerFish = "";
        String partnerBigBass = "";
        String partnerWeight = "";
        if (coAngler != null) {
            partner = coAngler.getName();
            partnerFish = "" + coAnglerFish + "(" + coAnglerFishAlive + ")";
            partnerBigBass = coAnglerBigBass;
            partnerWeight += String.format("%.2f", coAnglerWeight);
        }

        String str = "<tr>";
        str += "<td style=\"text-align: center\">" + place + "</td>"
                + "<td>" + boater.getName() + "</td>"
                + "<td style=\"text-align: center\">" + boaterFish+"("+boaterFishAlive+")" + "</td>"
                + "<td style=\"text-align: right\">" + boaterBigBass + "</td>"
                + "<td style=\"text-align: right\">" + String.format("%.2f", boaterWeight) + "</td><td></td>"
                + "<td>" + partner + "</td>"
                + "<td style=\"text-align: center\">" + partnerFish + "</td>"
                + "<td style=\"text-align: right\">" + partnerBigBass + "</td>"
                + "<td style=\"text-align: right\">" + partnerWeight + "</td>"
                + "<td style=\"text-align: right\">" + String.format("%.2f", teamWeight) + "</td>";
        str += "</tr>";
        return str;
    }

    public String toString() {
        String partner = "";
        String partnerFish = "";
        String partnerBigBass = "";
        String partnerWeight = "";
        if (coAngler != null) {
            partner = coAngler.getName();
            partnerFish = "" + coAnglerFish + "(" + coAnglerFishAlive + ")";
            partnerBigBass = coAnglerBigBass;
            partnerWeight += String.format("%.2f", coAnglerWeight);
        }

        return String.format("%3d%3s%-19s%6s%7s%8.2f%4s%-19s%6s%7s%8s%8.2f",
                place, "",
                boater.getName(),
                "" + boaterFish + "(" + boaterFishAlive + ")",
                boaterBigBass,
                boaterWeight, "",
                partner,
                partnerFish,
                partnerBigBass,
                partnerWeight,
                teamWeight
                );
    }
}